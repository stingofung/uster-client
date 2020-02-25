package com.hiberus.uster.service;

import com.hiberus.uster.model.Trip;
import com.hiberus.uster.model.comparator.TripComparator;
import com.hiberus.uster.model.paging.Column;
import com.hiberus.uster.model.paging.Order;
import com.hiberus.uster.model.paging.Page;
import com.hiberus.uster.model.paging.PagingRequest;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Slf4j
@Service
public class TripService {

    @Value("${resource.trips}")
    private String resource;
    @Value("${resource.trips}/{id}")
    private String resourceById;
    @Value("${resource.trips}/{date}")
    private String resourceByDate;
    @Value("${resource.trips.add}")
    private String resourceAdd;
    @Autowired
    private RestTemplate restTemplate;

    private static final Comparator<Trip> EMPTY_COMPARATOR = (e1, e2) -> 0;

    public Page<Trip> getTrips(PagingRequest pagingRequest) {
        try {
            ResponseEntity<Trip[]> forEntity = restTemplate.getForEntity(resource, Trip[].class);
            Trip[] body = forEntity.getBody();
            return getPage(Arrays.asList(body), pagingRequest);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }

        return new Page<>();
    }

    public void addTrip(Trip trip) {
        restTemplate.postForObject(resourceAdd, trip, Trip.class);
    }

    public void updateTrip(Long id, Trip trip) {
        restTemplate.put(resourceById, trip, id);
    }

    public void deleteTrip(Long id) {
        restTemplate.delete(resourceById, id);
    }

    private Page<Trip> getPage(List<Trip> trip, PagingRequest pagingRequest) {
        List<Trip> filtered = trip.stream()
                .sorted(sortTrips(pagingRequest))
                .filter(filterTrips(pagingRequest))
                .skip(pagingRequest.getStart())
                .limit(pagingRequest.getLength())
                .collect(Collectors.toList());

        Page<Trip> page = new Page<>(filtered);
        page.setRecordsFiltered(trip.size());
        page.setRecordsTotal(trip.size());
        page.setDraw(pagingRequest.getDraw());
        return page;
    }

    private Predicate<Trip> filterTrips(PagingRequest pagingRequest) {
        if (pagingRequest.getSearch() == null || StringUtils.isEmpty(pagingRequest.getSearch()
                .getValue())) {
            return trip -> true;
        }

        String value = pagingRequest.getSearch()
                .getValue();

        return trip -> trip.getDriverName()
                .toLowerCase()
                .contains(value)
                || trip.getDriverSurname()
                .toLowerCase()
                .contains(value)
                || trip.getDriverLicense()
                .toLowerCase()
                .contains(value)
                || trip.getVehicleBranch()
                .toLowerCase()
                .contains(value)
                || trip.getVehicleModel()
                .toLowerCase()
                .contains(value)
                || trip.getVehiclePlate()
                .toLowerCase()
                .contains(value)
                || trip.getVehicleLicense()
                .toLowerCase()
                .contains(value);
    }

    private Comparator<Trip> sortTrips(PagingRequest pagingRequest) {
        if (pagingRequest.getOrder() == null) {
            return EMPTY_COMPARATOR;
        }

        try {
            Order order = pagingRequest.getOrder()
                    .get(0);

            int columnIndex = order.getColumn();
            Column column = pagingRequest.getColumns()
                    .get(columnIndex);

            Comparator<Trip> comparator = TripComparator.getComparator(column.getData(), order.getDir());
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
