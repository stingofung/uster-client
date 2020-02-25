package com.hiberus.uster.model.comparator;

import com.hiberus.uster.model.Driver;
import com.hiberus.uster.model.paging.Direction;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

public final class DriverComparator {

    @EqualsAndHashCode
    @AllArgsConstructor
    @Getter
    static class Key {
        String name;
        Direction dir;
    }

    static Map<Key, Comparator<Driver>> map = new HashMap<>();

    static {
        map.put(new Key("name", Direction.asc), Comparator.comparing(Driver::getName));
        map.put(new Key("name", Direction.desc), Comparator.comparing(Driver::getName)
                .reversed());

        map.put(new Key("surName", Direction.asc), Comparator.comparing(Driver::getSurName));
        map.put(new Key("surName", Direction.desc), Comparator.comparing(Driver::getSurName)
                .reversed());

        map.put(new Key("license", Direction.asc), Comparator.comparing(Driver::getLicense));
        map.put(new Key("license", Direction.desc), Comparator.comparing(Driver::getLicense)
                .reversed());
    }

    public static Comparator<Driver> getComparator(String name, Direction dir) {
        return map.get(new Key(name, dir));
    }

    private DriverComparator() {
    }
}
