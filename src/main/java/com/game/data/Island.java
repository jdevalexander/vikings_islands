package com.game.data;

import java.util.Map;

/**
 * Class Island provides work with structure of Island.
 * Contains name, directions
 */
public class Island {

    private String name;
    private Map<DirectionEnum, String> direction;

    public String getName() {
        return name;
    }

    public Island setName(String name) {
        this.name = name;
        return this;
    }

    public Map<DirectionEnum, String> getDirection() {
        return direction;
    }

    public Island setDirection(Map<DirectionEnum, String> direction) {
        this.direction = direction;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Island island = (Island) o;

        if (name != null ? !name.equals(island.name) : island.name != null) return false;
        return direction != null ? direction.equals(island.direction) : island.direction == null;
    }

    @Override
    public int hashCode() {
        int result = name != null ? name.hashCode() : 0;
        result = 31 * result + (direction != null ? direction.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Island{" +
                "name='" + name + '\'' +
                ", direction=" + direction.keySet() +
                '}';
    }
}
