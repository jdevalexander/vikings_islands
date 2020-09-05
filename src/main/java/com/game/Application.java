package com.game;

import com.game.data.Island;
import com.game.data.Viking;
import com.game.service.ActionService;
import com.game.service.CreateService;
import com.game.service.ReaderService;

import java.util.List;

public class Application {

    public static void main(String[] args) {

        //Start the program and wait user enter file path
        String filePath = ReaderService.readConsoleFilePath();

        //read map from file
        List<String> fileLines = ReaderService.readFile(filePath);

        if (fileLines.size() == 0) {
            System.out.println("File is empty. Try again.");
            return;
        }

        //Create islands depends on the map from file
        List<Island> gameIslands = CreateService.createIslands(fileLines);

        if (gameIslands.size() == 0) {
            System.out.println("Map of islands was not built. Try again.");
            return;
        }

        //Read number of vikings from user enter
        int numberVikings = ReaderService.readConsoleNumberVikings();

        //Create random (or not random) number of vikings
        List<Viking> gameVikings = CreateService.createVikings(numberVikings, gameIslands);

        if (gameVikings.size() == 0) {
            System.out.println("There are not enough vikings for war. Try again.");
            return;
        }

        //remove unnecessary stuff after first landing of vikings
        ActionService.removeIslandsVikings(gameIslands, gameVikings);

        //Run the cycle of war and write the GameOver message
        System.out.println(ActionService.warCycle(gameIslands, gameVikings));

        //Write to the console rest of the islands on the map
        System.out.println();
        if (gameIslands.size() == 0) {
            System.out.println("The map is empty. All the islands were destroyed.");
        } else {
            System.out.println("The Map after the invasion of the Vikings :");
            gameIslands.forEach(System.out::println);
        }

    }


}