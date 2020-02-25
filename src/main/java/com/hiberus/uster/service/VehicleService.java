package com.hiberus.uster.service;

import com.hiberus.uster.model.Trip;
import com.hiberus.uster.model.Vehicle;
import com.hiberus.uster.model.comparator.VehicleComparator;
import com.hiberus.uster.model.paging.Column;
import com.hiberus.uster.model.paging.Order;
import com.hiberus.uster.model.paging.Page;
import com.hiberus.uster.model.paging.PagingRequest;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.net.URI;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Slf4j
@Service
public class VehicleService {

    @Value("${resource.vehicles}")
    private String resource;
    @Value("${resource.vehicles}/{id}")
    private String resourceById;
    @Value("${resource.vehicles.add}")
    private String resourceAdd;

    @Value("${resource.vehicles.filter.byDate}/{date}")
    private String resourceFilterByDate;

    private DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    @Autowired
    private RestTemplate restTemplate;

    private static final Comparator<Vehicle> EMPTY_COMPARATOR = (e1, e2) -> 0;

    public Page<Vehicle> getVehicles(PagingRequest pagingRequest) {
        try {
            ResponseEntity<Vehicle[]> forEntity = restTemplate.getForEntity(resource, Vehicle[].class);
            Vehicle[] body = forEntity.getBody();
            return getPage(Arrays.asList(body), pagingRequest);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }

        return new Page<>();
    }

    public Vehicle[] getVehiclesAvailabilityByDate(@RequestBody Trip trip, HttpServletRequest request, HttpServletResponse response) {
        HttpHeaders headers = new HttpHeaders();

        try {
            LocalDate date = trip.getDate();
//final String dateStr = DateTimeFormatter;
            /*ResponseEntity<Vehicle[]> forEntity = restTemplate.getForEntity(resourceFilterByDate.replace("{date}",dateStr), Vehicle[].class);
            Vehicle[] body = forEntity.getBody();
            response.setStatus(HttpServletResponse.SC_OK);
            return body;*/

            Map<String, String> params = new HashMap<String, String>();
            params.put("date", date.toString());

            URI uri = UriComponentsBuilder
                    .fromUriString(resourceFilterByDate).buildAndExpand(params)
                    .toUri();
            ResponseEntity<Vehicle[]> forEntity = restTemplate.exchange(uri.toString(), HttpMethod.GET, null, Vehicle[].class);
            Vehicle[] body = forEntity.getBody();
            response.setStatus(HttpServletResponse.SC_OK);
            return body;
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }

        return null;
    }

    public void addVehicle(Vehicle vehicle) {
        restTemplate.postForObject(resourceAdd, vehicle, Vehicle.class);
    }

    public void updateVehicle(Long id, Vehicle vehicle) {
        restTemplate.put(resourceById, vehicle, id);
    }

    public void deleteVehicle(Long id) {
        restTemplate.delete(resourceById, id);
    }

    private Page<Vehicle> getPage(List<Vehicle> vehicle, PagingRequest pagingRequest) {
        List<Vehicle> filtered = vehicle.stream()
                .sorted(sortVehicles(pagingRequest))
                .filter(filterVehicles(pagingRequest))
                .skip(pagingRequest.getStart())
                .limit(pagingRequest.getLength())
                .collect(Collectors.toList());

        Page<Vehicle> page = new Page<>(filtered);
        page.setRecordsFiltered(vehicle.size());
        page.setRecordsTotal(vehicle.size());
        page.setDraw(pagingRequest.getDraw());
        return page;
    }

    private Predicate<Vehicle> filterVehicles(PagingRequest pagingRequest) {
        if (pagingRequest.getSearch() == null || StringUtils.isEmpty(pagingRequest.getSearch()
                .getValue())) {
            return vehicle -> true;
        }

        String value = pagingRequest.getSearch()
                .getValue();

        return vehicle -> vehicle.getBrand()
                .toLowerCase()
                .contains(value)
                || vehicle.getModel()
                .toLowerCase()
                .contains(value)
                || vehicle.getPlate()
                .toLowerCase()
                .contains(value)
                || vehicle.getLicense()
                .toLowerCase()
                .contains(value);
    }

    private Comparator<Vehicle> sortVehicles(PagingRequest pagingRequest) {
        if (pagingRequest.getOrder() == null) {
            return EMPTY_COMPARATOR;
        }

        try {
            Order order = pagingRequest.getOrder()
                    .get(0);

            int columnIndex = order.getColumn();
            Column column = pagingRequest.getColumns()
                    .get(columnIndex);

            Comparator<Vehicle> comparator = VehicleComparator.getComparator(column.getData(), order.getDir());
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