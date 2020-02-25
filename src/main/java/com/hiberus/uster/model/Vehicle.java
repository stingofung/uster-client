package com.hiberus.uster.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class Vehicle {
    private Long id;
    private String brand;
    private String model;
    private String plate;
    private String license;
}
