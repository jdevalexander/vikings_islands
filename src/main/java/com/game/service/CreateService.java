package com.game.service;

import com.game.data.DirectionEnum;
import com.game.data.Island;
import com.game.data.Viking;

import java.util.*;
import java.util.stream.Stream;


/**
 * Service for creation instance of different entities
 */
public class CreateService {


    /**
     * Read each line from file with map of islands. Create list of Islands.
     * Read directions from file and put it to the each island in the list.
     *
     * @param fileLines List of string lines from file with map of islands
     * @return Created list of islands on the map for game
     */
    public static List<Island> createIslands(List<String> fileLines) {

        //List of islands
        List<Island> islands = new ArrayList<>();
        //pre map of Islands (name of islands = direction)
        Map<String, Map<String, String>> mapIslands = new HashMap<>();


        //Create new Islands with names, and Map of Names islands and directions
        try {
            fileLines.forEach(s -> {

                String islandName = s.substring(0, s.indexOf(" "));

                Map<String, String> directionsMap = new HashMap<>();

                Stream.of(s.substring(s.indexOf(" ")).split(" "))
                        .filter(s1 -> !s1.equals(""))
                        .forEach(s1 -> directionsMap.put(s1.substring(0, s1.indexOf("=")), s1.substring(s1.indexOf("=") + 1)));

                mapIslands.put(islandName, directionsMap);

                Island island = new Island().setName(islandName);
                islands.add(island);
            });
        } catch (StringIndexOutOfBoundsException e) {
            System.out.println("Error while reading lines in file");
            return new ArrayList<>();
        }

        //Take directions of islands
        islands.forEach(island -> {
            Map<String, String> islandDirection = mapIslands.get(island.getName());
            if (islandDirection != null) {
                Map<DirectionEnum, String> directions = new HashMap<>();
                islandDirection.forEach((s, s2) ->
                        //We must find the same island on the map (in list of islands)
                        islands.stream().filter(island1 -> island1.getName().equals(s2))
                                .findAny().ifPresent(value -> directions.put(getDirection(s), value.getName()))
                );

                island.setDirection(directions);
            }

        });

        //Write to the console created map
        System.out.println("Created Map of Islands :");
        islands.forEach(System.out::println);
        System.out.println();
        return islands;
    }

    /**
     * Return Enum depends on string of direction from file
     *
     * @param fileDir string of direction from file
     * @return Enum depends on string of direction from file
     */
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


    /**
     * Create list of Vikings on the map for game
     *
     * @param numberVikings Number of viking will be created
     * @param islands       List of Islands on the map
     * @return Created list of Vikings on the map
     */
    public static List<Viking> createVikings(int numberVikings, List<Island> islands) {
        List<Viking> landedVikings = new ArrayList<>();
        //Add input from console
        for (int i = 1; i < numberVikings + 1; i++) {
            Viking viking = new Viking();
            viking.setName("Viking" + i);
            //Land Viking on the random island
            viking.setIsland(islands.get(new Random().nextInt(islands.size())));
            landedVikings.add(viking);
        }
        return landedVikings;
    }

}
