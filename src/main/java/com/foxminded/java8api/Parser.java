package com.foxminded.java8api;


import java.io.*;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

import static java.time.temporal.ChronoUnit.*;

public class Parser {

    public Map<List<String>, String> getRacersTime(String pathToStartLog, String pathToFinishLog, String pathToAbbreviationDecoding) throws IOException {

        Map<String, Long> bestLaps = getBestTime(pathToStartLog, pathToFinishLog);
        LinkedHashMap<List<String>, String> result = new LinkedHashMap<>();

        bestLaps.forEach((key1, value) -> {
            try {
                List<String> nameAndTeam = getNameAndTeam(pathToAbbreviationDecoding, key1);
                String time = convertTime(value);

                result.put(nameAndTeam, time);

            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        return result;

    }

    public Map<String, Long> getBestTime(String pathToStartLog, String pathToFinishLog) throws IOException {

        Map<String, LocalTime> startLog = getTimeDataFromFile(pathToStartLog);
        Map<String, LocalTime> finishLog = getTimeDataFromFile(pathToFinishLog);

        Map<String, Long> bestLaps = new LinkedHashMap<>();

        if (finishLog.size() != 0) {
            startLog.keySet().forEach(team -> {

                LocalTime start = startLog.get(team);
                LocalTime finish = finishLog.get(team);
                long bestLap = MILLIS.between(start, finish);

                bestLaps.put(team, bestLap);
            });

            bestLaps.entrySet().stream().sorted(Map.Entry.comparingByValue()).forEach(bestLap -> {

                bestLaps.remove(bestLap.getKey());
                bestLaps.put(bestLap.getKey(), bestLap.getValue());
            });
        }
        return bestLaps;
    }

    public Map<String, LocalTime> getTimeDataFromFile(String pathName) throws IOException {

        File timeLog = new File(pathName);
        HashMap<String, LocalTime> timeData = new HashMap<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(timeLog))) {

            String nameDateTime = reader.readLine();

            while (nameDateTime != null) {

                ArrayList<Character> nameAndTimeCharacters = nameDateTime.chars().mapToObj(e -> (char) e).collect(Collectors.toCollection(ArrayList::new));

                StringJoiner buffer = new StringJoiner("");
                nameAndTimeCharacters.stream().filter(Character::isLetter).forEach(el -> buffer.add(el.toString()));

                String name = buffer.toString();
                String dateAndTime = nameDateTime.substring(name.length());

                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-LL-dd_HH:mm:ss.SSS");
                LocalTime time = LocalTime.parse(dateAndTime, formatter);

                timeData.put(name, time);
                nameDateTime = reader.readLine();
            }
        }
        return timeData;
    }

    public List<String> getNameAndTeam(String pathName, String abbreviation) throws IOException {

        File abbreviationDecoding = new File(pathName);
        ArrayList<String> nameAndTeam = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(abbreviationDecoding))) {

            String abbreviationNameTeam = reader.readLine();

            while (abbreviationNameTeam != null) {
                if (abbreviationNameTeam.contains(abbreviation)) {
                    String name = abbreviationNameTeam.split("_")[1];
                    String team = abbreviationNameTeam.split("_")[2];

                    nameAndTeam.add(name);
                    nameAndTeam.add(team);
                }
                abbreviationNameTeam = reader.readLine();
            }
        }
        return nameAndTeam;
    }

    public String convertTime(long time) {

        final String ZERO = "0";
        final String DOT = ".";
        final String COLON = ":";

        long min = time / (1000 * 60);
        long sec = (time - min * 60 * 1000) / 1000;
        long milliSec = (time - min * 60 * 1000 - sec * 1000);

        String secFormat = "";
        if (sec < 10) {
            secFormat = ZERO;
        }

        String milliSecFormat = "";
        if (milliSec < 100) {
            milliSecFormat = ZERO;

            if (milliSec < 10) {
                milliSecFormat = ZERO + ZERO;
            }
        }
        return min + COLON + secFormat + sec + DOT + milliSecFormat + milliSec;
    }
}

