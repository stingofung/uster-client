package com.hiberus.uster.controller;

import com.hiberus.uster.model.Trip;
import com.hiberus.uster.model.Vehicle;
import com.hiberus.uster.model.paging.Page;
import com.hiberus.uster.model.paging.PagingRequest;
import com.hiberus.uster.service.VehicleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/vehicles2")
public class VehicleController {

    private final VehicleService vehicleService;

    @Autowired
    public VehicleController(VehicleService vehicleService) {
        this.vehicleService = vehicleService;
    }

    @PostMapping
    public Page<Vehicle> getRecord(@RequestBody PagingRequest pagingRequest) {
        return vehicleService.getVehicles(pagingRequest);
    }

    @RequestMapping(value = "/checkAvailabilityByDate", method = RequestMethod.POST)
    public @ResponseBody Vehicle[] checkAvailabilityByDate(@RequestBody Trip trip, HttpServletRequest request, HttpServletResponse response) {
        return vehicleService.getVehiclesAvailabilityByDate(trip, request, response);
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public void addRecord(@RequestBody Vehicle vehicle) {
        vehicleService.addVehicle(vehicle);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public void deleteRecord(@PathVariable(value = "id") Long id) {
        vehicleService.deleteVehicle(id);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public void updateRecord(@PathVariable(value = "id") Long id, @RequestBody Vehicle vehicle) {
        vehicleService.updateVehicle(id, vehicle);
    }
}
