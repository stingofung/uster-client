package com.hiberus.uster.controller;

import com.hiberus.uster.model.Driver;
import com.hiberus.uster.model.Trip;
import com.hiberus.uster.model.Vehicle;
import com.hiberus.uster.model.paging.Page;
import com.hiberus.uster.model.paging.PagingRequest;
import com.hiberus.uster.service.DriverService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/drivers2")
public class DriverController {

    private final DriverService driverService;

    @Autowired
    public DriverController(DriverService driverService) {
        this.driverService = driverService;
    }

    @PostMapping
    public Page<Driver> getRecord(@RequestBody PagingRequest pagingRequest) {
        return driverService.getDrivers(pagingRequest);
    }

    @RequestMapping(value = "/checkAvailabilityByDateAndLicense", method = RequestMethod.POST)
    public @ResponseBody Driver[] checkAvailabilityByDateAndLicense(@RequestBody Trip trip, HttpServletRequest request, HttpServletResponse response) {
        return driverService.getDriversAvailabilityByDateAndLicense(trip, request, response);
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public void addRecord(@RequestBody Driver driver) {
        driverService.addDriver(driver);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public void deleteRecord(@PathVariable(value = "id") Long id) {
        driverService.deleteDriver(id);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public void updateRecord(@PathVariable(value = "id") Long id, @RequestBody Driver driver) {
        driverService.updateDriver(id, driver);
    }
}
