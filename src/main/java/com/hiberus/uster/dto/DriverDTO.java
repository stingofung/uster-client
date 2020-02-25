package com.hiberus.uster.dto;

public class DriverDTO {
    private Long id;
    private String name;
    private String surName;
    private String license;

    public DriverDTO(){

    }

    public DriverDTO(Long id, String name, String surName, String license) {
        this.id = id;
        this.name = name;
        this.surName = surName;
        this.license = license;
    }

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public String getSurName() {
        return surName;
    }
    public void setSurName(String surName) {
        this.surName = surName;
    }

    public String getLicense() {
        return license;
    }
    public void setLicense(String license) {
        this.license = license;
    }
}
