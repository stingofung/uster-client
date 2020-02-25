package com.hiberus.uster.dto;

public class VehicleDTO {
    private Long id;
    private String brand;
    private String model;
    private String plate;
    private String license;

    public VehicleDTO(){

    }

    public VehicleDTO(Long id, String brand, String model, String plate, String license) {
        this.id = id;
        this.brand = brand;
        this.model = model;
        this.plate = plate;
        this.license = license;
    }

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }

    public String getBrand() {
        return brand;
    }
    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getModel() {
        return model;
    }
    public void setModel(String model) {
        this.model = model;
    }

    public String getPlate() {
        return plate;
    }
    public void setPlate(String plate) {
        this.plate = plate;
    }

    public String getLicense() {
        return license;
    }
    public void setLicense(String license) {
        this.license = license;
    }
}
