package com.hiberus.uster.service.temp;

import com.hiberus.uster.dto.DriverDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class DriverService2 {

    @Value("${resource.drivers}")
    private String resource;
    @Autowired
    private RestTemplate restTemplate;

    public List<DriverDTO> findAll() {
        return Arrays.stream(restTemplate.getForObject(resource, DriverDTO[].class)).collect(Collectors.toList());
    }
}
