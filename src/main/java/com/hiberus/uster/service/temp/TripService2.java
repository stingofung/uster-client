package com.hiberus.uster.service.temp;

import com.hiberus.uster.dto.TripDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TripService2 {

    @Value("${resource.trips}")
    private String resource;
    @Autowired
    private RestTemplate restTemplate;

    public List<TripDTO> findAll() {
        return Arrays.stream(restTemplate.getForObject(resource, TripDTO[].class)).collect(Collectors.toList());
    }
}
