package com.hiberus.uster.controller.temp;

import com.hiberus.uster.service.temp.VehicleService2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/vehicles")
public class VehicleController2 {

    @Autowired
    private VehicleService2 vehicleService;

    @RequestMapping(method = RequestMethod.GET)
    public String findAll(Model model) {
        model.addAttribute("vehicles", vehicleService.findAll());
        return "vehicles";
    }
}
