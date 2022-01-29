package com.foxminded.java8api;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

public class Formatter {

    public static final String NEW_LINE = "\n";
    public static final String VERTICAL_LINE = "|";
    public static final String DOT = ".";
    public static final String TABLE_SEPARATOR = "------------------------------------------";

    public String tableFormatter(Map<List<String>, String> racersData) {
        int[] longestNameAndTeam= getLongestNameAndTeam(racersData.keySet());
        AtomicInteger counterOfRacers = new AtomicInteger(1);
        StringBuilder buffer = new StringBuilder();

        racersData.forEach((nameTeam, time) -> {

            if (counterOfRacers.get() == 16) {
                buffer.append(TABLE_SEPARATOR + NEW_LINE);
            }
            String nameTeamBest_time = lineFormatter(nameTeam, longestNameAndTeam, time);

            buffer.append(counterOfRacers + DOT + nameTeamBest_time + NEW_LINE);
            counterOfRacers.getAndIncrement();
        });

        return buffer.toString();
    }

    private int[] getLongestNameAndTeam(Set<List<String>> lines) {

        int [] nameAndTeamLength = new int[2];

        for(int i = 0;i<2;i++) {

            int finalI1 = i;
            Optional<Integer> length = lines.stream().map(el -> {
                String name = el.get(finalI1);
                return name.length();
            }).max(Integer::compare);
            nameAndTeamLength[i] = length.orElse(0);
        }
        return nameAndTeamLength;
    }

    private String lineFormatter(List<String> nameAndTeam, int[] longestNamesAndTeams, String time) {
        int longestName = longestNamesAndTeams[0];
        int longestTeam = longestNamesAndTeams[1];
        String name = nameAndTeam.get(0);
        String team = nameAndTeam.get(1);
        String nameIndent = spaceGen(longestName - name.length());
        String teamIndent = spaceGen(longestTeam - team.length());

        return name + nameIndent + VERTICAL_LINE + team + teamIndent + VERTICAL_LINE + time;
    }

    private String spaceGen(int quantity) {

        StringBuilder sb = new StringBuilder();
        int i = 0;
        while (i < quantity) {
            sb.append(" ");
            i++;
        }
        return sb.toString();
    }

}

