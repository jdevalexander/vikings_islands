package com.game.service;

import com.game.data.DirectionEnum;
import com.game.data.Island;
import com.game.data.Viking;

import java.util.*;
import java.util.stream.Stream;

public class CreateService {


    /**
     * create Islands
     */
    public static List<Island> createIslands(List<String> fileLines) {

        //List of islands
        List<Island> islands = new ArrayList<>();
        //pre map of Islands
        Map<String, Map<String, String>> mapIslands = new HashMap<>();


        //Creation new Islands with names, and Map of Names islands and directions
        fileLines.forEach(s -> {

            String islandName = s.substring(0, s.indexOf(" "));

            Map<String, String> directionsMap = new HashMap<>();

            Stream.of(s.substring(s.indexOf(" ")).split(" "))
                    .filter(s1 -> !s1.equals(""))
                    .forEach(s1 -> {
                        directionsMap.put(s1.substring(0, s1.indexOf("=")), s1.substring(s1.indexOf("=") + 1));
                    });
            mapIslands.put(islandName, directionsMap);

            Island island = new Island().setName(islandName);
            islands.add(island);
        });

        //Take directions of islands
        islands.forEach(island -> {
            Map<String, String> islandDirection = mapIslands.get(island.getName());
            if (islandDirection != null) {
                Map<DirectionEnum, String> directions = new HashMap<>();
                islandDirection.forEach((s, s2) ->
                        islands.stream().filter(island1 -> island1.getName().equals(s2))
                                .findAny().ifPresent(value -> directions.put(getDirection(s), value.getName()))
                );

                island.setDirection(directions);
            }

        });

//        System.out.println(mapIslands);
//        System.out.println(islands);

        return islands;
    }


    private static DirectionEnum getDirection(String fileDir) {
        switch (fileDir.toLowerCase()) {
            case "north":
                return DirectionEnum.NORTH;
            case "south":
                return DirectionEnum.SOUTH;
            case "west":
                return DirectionEnum.WEST;
            case "east":
                return DirectionEnum.EAST;
            default:
                return null;
        }
    }


    public static List<Viking> createVikings(int numberVikings, List<Island> islands) {
        List<Viking> landedVikings = new ArrayList<>();
        //Add input from console
        for (int i = 1; i < numberVikings + 1; i++) {
            Viking viking = new Viking();
            viking.setName("Viking" + i);
            viking.setIsland(islands.get(new Random().nextInt(islands.size())));
            landedVikings.add(viking);
        }
        landedVikings.forEach(System.out::println);
        return landedVikings;
    }

}
