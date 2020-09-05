package com.game;

import com.game.data.Island;
import com.game.data.Viking;
import com.game.service.ActionService;
import com.game.service.CreateService;
import com.game.service.ReaderService;

import java.util.List;

public class Application {

    public static void main(String[] args) {

        //read map from file
        List<String> fileLines = ReaderService
                .readFile("D:\\Developing\\java-projects\\vikings_islands\\vikings_examples\\1.txt");

        //Create islands depends on the map from file
        List<Island> gameIslands = CreateService.createIslands(fileLines);

        //Create random (or not random) number of vikings
        List<Viking> gameVikings = CreateService.createVikings(3, gameIslands);

        //remove unnecessary stuff after first landing of vikings
        ActionService.removeIslandsVikings(gameIslands, gameVikings);

        //Run the cycle of war and write the GameOver message
        System.out.println(ActionService.warCycle(gameIslands, gameVikings));

        //Write to the console rest of the islands on the map
        System.out.println();
        System.out.println("The Map after the invasion of the Vikings :");
        gameIslands.forEach(System.out::println);

    }


}