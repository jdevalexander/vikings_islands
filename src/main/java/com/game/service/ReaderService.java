package com.game.service;

import com.game.data.Island;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

/**
 * Service provides work with outer sources.
 * Various data from the console or files.
 */
public class ReaderService {


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





}
