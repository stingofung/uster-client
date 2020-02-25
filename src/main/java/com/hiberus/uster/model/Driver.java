package com.hiberus.uster.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class Driver {
    private Long id;
    private String name;
    private String surName;
    private String license;
}
