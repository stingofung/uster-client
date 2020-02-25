package com.hiberus.uster.service.temp;

import com.hiberus.uster.dto.VehicleDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class VehicleService2 {

    @Value("${resource.vehicles}")
    private String resource;
    @Autowired
    private RestTemplate restTemplate;

    public List<VehicleDTO> findAll() {
        return Arrays.stream(restTemplate.getForObject(resource, VehicleDTO[].class)).collect(Collectors.toList());
    }
}
