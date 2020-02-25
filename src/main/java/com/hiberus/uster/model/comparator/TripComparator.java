package com.hiberus.uster.model.comparator;

import com.hiberus.uster.model.Trip;
import com.hiberus.uster.model.paging.Direction;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

public final class TripComparator {

    @EqualsAndHashCode
    @AllArgsConstructor
    @Getter
    static class Key {
        String name;
        Direction dir;
    }

    static Map<Key, Comparator<Trip>> map = new HashMap<>();

    static {
        map.put(new Key("tripDate", Direction.asc), Comparator.comparing(Trip::getDate));
        map.put(new Key("tripDate", Direction.desc), Comparator.comparing(Trip::getDate)
                .reversed());

        map.put(new Key("driverName", Direction.asc), Comparator.comparing(Trip::getDriverName));
        map.put(new Key("driverName", Direction.desc), Comparator.comparing(Trip::getDriverName)
                .reversed());

        map.put(new Key("driverSurname", Direction.asc), Comparator.comparing(Trip::getDriverSurname));
        map.put(new Key("driverSurname", Direction.desc), Comparator.comparing(Trip::getDriverSurname)
                .reversed());

        map.put(new Key("driverLicense", Direction.asc), Comparator.comparing(Trip::getDriverLicense));
        map.put(new Key("driverLicense", Direction.desc), Comparator.comparing(Trip::getDriverLicense)
                .reversed());

        map.put(new Key("vehicleBranch", Direction.asc), Comparator.comparing(Trip::getVehicleBranch));
        map.put(new Key("vehicleBranch", Direction.desc), Comparator.comparing(Trip::getVehicleBranch)
                .reversed());

        map.put(new Key("vehicleModel", Direction.asc), Comparator.comparing(Trip::getVehicleModel));
        map.put(new Key("vehicleModel", Direction.desc), Comparator.comparing(Trip::getVehicleModel)
                .reversed());

        map.put(new Key("vehiclePlate", Direction.asc), Comparator.comparing(Trip::getVehiclePlate));
        map.put(new Key("vehiclePlate", Direction.desc), Comparator.comparing(Trip::getVehiclePlate)
                .reversed());

        map.put(new Key("vehicleLicense", Direction.asc), Comparator.comparing(Trip::getVehicleLicense));
        map.put(new Key("vehicleLicense", Direction.desc), Comparator.comparing(Trip::getVehicleLicense)
                .reversed());
    }

    public static Comparator<Trip> getComparator(String name, Direction dir) {
        return map.get(new Key(name, dir));
    }

    private TripComparator() {
    }
}
