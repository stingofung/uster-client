package com.hiberus.uster.controller.temp;

import com.hiberus.uster.service.temp.DriverService2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/drivers")
public class DriverController2 {

    @Autowired
    private DriverService2 driverService;

    @RequestMapping(method = RequestMethod.GET)
    public String findAll(Model model) {
        model.addAttribute("drivers", driverService.findAll());
        return "drivers";
    }
}
