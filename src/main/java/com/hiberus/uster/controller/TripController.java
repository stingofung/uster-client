package com.hiberus.uster.controller;

import com.hiberus.uster.model.Trip;
import com.hiberus.uster.model.paging.Page;
import com.hiberus.uster.model.paging.PagingRequest;
import com.hiberus.uster.service.TripService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/trips2")
public class TripController {

    private final TripService tripService;

    @Autowired
    public TripController(TripService tripService) {
        this.tripService = tripService;
    }

    @PostMapping
    public Page<Trip> getRecord(@RequestBody PagingRequest pagingRequest) {
        return tripService.getTrips(pagingRequest);
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public void addRecord(@RequestBody Trip trip) {
        tripService.addTrip(trip);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public void deleteRecord(@PathVariable(value = "id") Long id) {
        tripService.deleteTrip(id);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public void updateRecord(@PathVariable(value = "id") Long id, @RequestBody Trip trip) {
        tripService.updateTrip(id, trip);
    }
}
