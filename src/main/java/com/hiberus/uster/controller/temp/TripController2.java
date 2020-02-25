package com.hiberus.uster.controller.temp;

import com.hiberus.uster.service.temp.TripService2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/trips")
public class TripController2 {

    @Autowired
    private TripService2 tripService;

    @RequestMapping(method = RequestMethod.GET)
    public String findAll(Model model) {
        model.addAttribute("trips", tripService.findAll());
        return "trips";
    }
}
