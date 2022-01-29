package com.foxminded.java8api;

import java.io.IOException;

import java.util.*;


public class Application {
    public static void main(String[] args) throws IOException {

        Parser parser = new Parser();
        Formatter formatter = new Formatter();

        String pathToDirectory = "./src/textDocuments/testDocuments/sortingTest/";

         Map<List<String>,String> racersTime = parser.getRacersTime(pathToDirectory + "start.txt",
                 pathToDirectory + "finish.txt",  "./src/textDocuments/testDocuments/abbreviation.txt");

        //System.out.println(racersTime);
        System.out.println(formatter.tableFormatter(racersTime));
    }
}


