package com.foxminded.java8api;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

public class ParserTest {

    Parser parser = new Parser();

    @Test
    void getRacersTime_shouldThrowAnException_whenOneOfInputFilesNotFound() {

        assertThrows(IOException.class, () -> parser.getRacersTime("./src/textDocuments/indexxxx.txt",
                "./src/textDocuments/finish.txt", "./src/textDocuments/abbreviation.txt"));

    }

    @Test
    void getRacersTime_shouldReturnSortedTable_whenInputFilesConsistOfUnsortedRecords() throws IOException {

        String pathToDirectory = "./src/textDocuments/testDocuments/sortingTest/";
        Map<List<String>, String> actual = parser.getRacersTime(pathToDirectory + "start.txt",
                pathToDirectory + "finish.txt",  "./src/textDocuments/testDocuments/abbreviation.txt");

        Map<List<String>, String> expected = new LinkedHashMap<>();
        expected.put(new ArrayList<>(Arrays.asList("Brendon Hartley", "SCUDERIA TORO ROSSO HONDA")), "0:00.001");
        expected.put(new ArrayList<>(Arrays.asList("Sebastian Vettel", "FERRARI")), "0:00.002");
        expected.put(new ArrayList<>(Arrays.asList("Daniel Ricciardo", "RED BULL RACING TAG HEUER")), "0:00.003");

        assertEquals(actual, expected);
    }

    @Test
    void getRacersTime_shouldReturnNothing_whenInputTimeLogFilesEmpty() throws IOException {

        String pathToDirectory = "./src/textDocuments/testDocuments/emptyFileTest/";
        Map<List<String>, String> actual = parser.getRacersTime(pathToDirectory + "start.txt",
                pathToDirectory + "finish.txt",  "./src/textDocuments/testDocuments/abbreviation.txt");

        Map<List<String>, String> expected = new LinkedHashMap<>();

        assertEquals(expected, actual);
    }

    @Test
    void getRacersTime_shouldReturnNothing_whenInputAbbreviationFileIsEmpty() throws IOException {

        String pathToDirectory = "./src/textDocuments/testDocuments/sortingTest/";
        Map<List<String>, String> actual = parser.getRacersTime(pathToDirectory + "start.txt",
                pathToDirectory + "finish.txt",  "./src/textDocuments/testDocuments/emptyTestFile.txt");

        Map<List<String>, String> expected = new LinkedHashMap<>();
        expected.put(new ArrayList<>(),"0:00.003");

        assertEquals(expected, actual);
    }
}
