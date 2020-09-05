package com.game.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
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

        } catch (IOException e) {
            System.out.println(" Error while reading file " + filePath);
        }

        return fileLines;
    }


    public static void readConsole(){

        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));


    }


}
