package com.game.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/**
 * Service provides work with outer sources.
 * Various data from the console or files.
 */
public class ReaderService {


    /**
     * Read file from user's filesystem and create list of string lines from file
     *
     * @param filePath File path in the filesystem of user
     * @return List of string lines from file
     */
    public static List<String> readFile(String filePath) {

        List<String> fileLines = new ArrayList<>();

        try {
            //Read file and add each line to the List
            Files.lines(Paths.get(filePath))
                    .forEach(fileLines::add);

        } catch (IOException | InvalidPathException e) {
            System.out.println("Error while reading file " + filePath);
        }

        return fileLines;
    }

    /**
     * Read user input to console and return the file path of map
     *
     * @return File path of map from user enter to console
     */
    public static String readConsoleFilePath() {

        String filePath = null;
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        //waiting while user enter something similar to file path
        while (filePath == null || filePath.equals("")) {
            System.out.println("Please write full file path of the map to start the game:");
            System.out.println("Example: C:\\Program Files\\vikings_examples\\1.txt");

            try {
                filePath = reader.readLine();
            } catch (IOException e) {
                System.out.println("Error while reading input from console");
            }
        }
        return filePath;
    }

    /**
     * Read user input from console and return the number of vikings ready for war.
     *
     * @return Number of vikings for war
     */
    public static int readConsoleNumberVikings() {

        int numberVikings = 0;
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        //waiting while user enter something similar to file path
        while (numberVikings == 0) {
            System.out.println("Please write number of vikings to continue the game:");

            try {
                numberVikings = Integer.parseInt(reader.readLine());
            } catch (IOException e) {
                System.out.println("Error while reading number vikings input from console");
            }catch (NumberFormatException e){
                System.out.println("Please write a number (just a number) less than 2147483646");
            }
        }
        return numberVikings;
    }

}
