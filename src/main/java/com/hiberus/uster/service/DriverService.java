package com.hiberus.uster.service;

import com.hiberus.uster.model.Driver;
import com.hiberus.uster.model.Trip;
import com.hiberus.uster.model.comparator.DriverComparator;
import com.hiberus.uster.model.paging.Column;
import com.hiberus.uster.model.paging.Order;
import com.hiberus.uster.model.paging.Page;
import com.hiberus.uster.model.paging.PagingRequest;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Slf4j
@Service
public class DriverService {

    @Value("${resource.drivers}")
    private String resource;
    @Value("${resource.drivers}/{id}")
    private String resourceById;
    @Value("${resource.drivers.add}")
    private String resourceAdd;
    @Autowired
    private RestTemplate restTemplate;

    private static final Comparator<Driver> EMPTY_COMPARATOR = (e1, e2) -> 0;

    public Page<Driver> getDrivers(PagingRequest pagingRequest) {
        try {
            ResponseEntity<Driver[]> forEntity = restTemplate.getForEntity(resource, Driver[].class);
            Driver[] body = forEntity.getBody();
            return getPage(Arrays.asList(body), pagingRequest);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }

        return new Page<>();
    }

    public Driver[] getDriversAvailabilityByDateAndLicense(@RequestBody Trip trip, HttpServletRequest request, HttpServletResponse response) {
        HttpHeaders headers = new HttpHeaders();

        try {
            String driverName = trip.getDriverName();
            String vehicleBranch = trip.getVehicleBranch();
            LocalDate date = trip.getDate();


            ResponseEntity<Driver[]> forEntity = restTemplate.getForEntity(resource, Driver[].class);
            Driver[] body = forEntity.getBody();
            response.setStatus(HttpServletResponse.SC_OK);
            return body;
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }

        return null;
    }

    public void addDriver(Driver driver) {
        restTemplate.postForObject(resourceAdd, driver, Driver.class);
    }

    public void updateDriver(Long id, Driver driver) {
        restTemplate.put(resourceById, driver, id);
    }

    public void deleteDriver(Long id) {
        restTemplate.delete(resourceById, id);
    }

    private Page<Driver> getPage(List<Driver> driver, PagingRequest pagingRequest) {
        List<Driver> filtered = driver.stream()
                .sorted(sortDrivers(pagingRequest))
                .filter(filterDrivers(pagingRequest))
                .skip(pagingRequest.getStart())
                .limit(pagingRequest.getLength())
                .collect(Collectors.toList());

        Page<Driver> page = new Page<>(filtered);
        page.setRecordsFiltered(driver.size());
        page.setRecordsTotal(driver.size());
        page.setDraw(pagingRequest.getDraw());
        return page;
    }

    private Predicate<Driver> filterDrivers(PagingRequest pagingRequest) {
        if (pagingRequest.getSearch() == null || StringUtils.isEmpty(pagingRequest.getSearch()
                .getValue())) {
            return driver -> true;
        }

        String value = pagingRequest.getSearch()
                .getValue();

        return driver -> driver.getName()
                .toLowerCase()
                .contains(value)
                || driver.getSurName()
                .toLowerCase()
                .contains(value)
                || driver.getLicense()
                .toLowerCase()
                .contains(value);
    }

    private Comparator<Driver> sortDrivers(PagingRequest pagingRequest) {
        if (pagingRequest.getOrder() == null) {
            return EMPTY_COMPARATOR;
        }

        try {
            Order order = pagingRequest.getOrder()
                    .get(0);

            int columnIndex = order.getColumn();
            Column column = pagingRequest.getColumns()
                    .get(columnIndex);

            Comparator<Driver> comparator = DriverComparator.getComparator(column.getData(), order.getDir());
            if (comparator == null) {
                return EMPTY_COMPARATOR;
            }

            return comparator;

        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }

        return EMPTY_COMPARATOR;
    }
}
