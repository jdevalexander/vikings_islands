package com.game.data;

/**
 * Class Viking provides work with structure of Viking.
 * Contains name, directions
 */
public class Viking {

    private String name;
    private Island island;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Island getIsland() {
        return island;
    }

    public void setIsland(Island island) {
        this.island = island;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Viking viking = (Viking) o;

        if (name != null ? !name.equals(viking.name) : viking.name != null) return false;
        return island != null ? island.equals(viking.island) : viking.island == null;
    }

    @Override
    public int hashCode() {
        int result = name != null ? name.hashCode() : 0;
        result = 31 * result + (island != null ? island.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Viking{" +
                "name='" + name + '\'' +
                ", island=" + island +
                '}';
    }
}
