package com.hiberus.uster.model.comparator;

import com.hiberus.uster.model.Vehicle;
import com.hiberus.uster.model.paging.Direction;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

public final class VehicleComparator {

    @EqualsAndHashCode
    @AllArgsConstructor
    @Getter
    static class Key {
        String name;
        Direction dir;
    }

    static Map<Key, Comparator<Vehicle>> map = new HashMap<>();

    static {
        map.put(new Key("branch", Direction.asc), Comparator.comparing(Vehicle::getBrand));
        map.put(new Key("branch", Direction.desc), Comparator.comparing(Vehicle::getBrand)
                .reversed());

        map.put(new Key("model", Direction.asc), Comparator.comparing(Vehicle::getModel));
        map.put(new Key("model", Direction.desc), Comparator.comparing(Vehicle::getModel)
                .reversed());

        map.put(new Key("plate", Direction.asc), Comparator.comparing(Vehicle::getPlate));
        map.put(new Key("plate", Direction.desc), Comparator.comparing(Vehicle::getPlate)
                .reversed());

        map.put(new Key("license", Direction.asc), Comparator.comparing(Vehicle::getLicense));
        map.put(new Key("license", Direction.desc), Comparator.comparing(Vehicle::getLicense)
                .reversed());
    }

    public static Comparator<Vehicle> getComparator(String name, Direction dir) {
        return map.get(new Key(name, dir));
    }

    private VehicleComparator() {
    }
}
