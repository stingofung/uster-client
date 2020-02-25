package com.hiberus.uster.controller.error;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class CustomErrorController implements ErrorController {
    @RequestMapping("/error")
    //@ResponseStatus(HttpStatus.NOT_FOUND) //404
    public String handleError() {
        return "not-found";
    }

    @Override
    public String getErrorPath() {
        return "/error";
    }
}
