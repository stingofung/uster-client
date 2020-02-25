package com.hiberus.uster.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
public class Trip {
    private Long id;
    @JsonFormat(pattern = "yyyy-MM-dd")
    @JsonProperty("date")
    private LocalDate date;

    private Long driverId;
    private String driverName;
    private String driverSurname;
    private String driverLicense;

    private Long vehicleId;
    private String vehicleBranch;
    private String vehicleModel;
    private String vehiclePlate;
    private String vehicleLicense;
}
