package com.game;

import com.game.data.Island;
import com.game.data.Viking;
import com.game.service.CreateService;
import com.game.service.ReaderService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Application {

    public static void main(String[] args) {

        List<String> fileLines = ReaderService
                .readFile("D:\\Developing\\java-projects\\vikings_islands\\vikings_examples\\1.txt");

        List<Island> gameIslands = CreateService.createIslands(fileLines);

        List<Viking> gameVikings = CreateService.createVikings(10, gameIslands);

        List<Island> islandsForRemove = gameVikings.stream()
                .collect(Collectors.groupingBy(Viking::getIsland, Collectors.counting()))
                .entrySet().stream()
                .filter(islandLongEntry -> islandLongEntry.getValue() > 1)
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());

        Map<Island, List<Viking>> islandVikingForRemove = new HashMap<>();
        for (Island island : islandsForRemove) {
            islandVikingForRemove.put(island, gameVikings
                    .stream()
                    .filter(viking -> viking.getIsland().equals(island))
                    .collect(Collectors.toList()));
        }


        islandVikingForRemove.forEach((island, vikings) -> {
            String destroyerVikings = vikings.stream().map(Viking::getName).collect(Collectors.joining(", "));
            System.out.println("AGR!!! On the " + island.getName() + ", the lighthouse was destroyed, thanks to " + destroyerVikings);
        });


    }


}