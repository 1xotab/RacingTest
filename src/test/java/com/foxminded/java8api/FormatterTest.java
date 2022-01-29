package com.foxminded.java8api;

import org.junit.jupiter.api.Test;

import java.util.*;

import static com.foxminded.java8api.Formatter.NEW_LINE;
import static com.foxminded.java8api.Formatter.VERTICAL_LINE;
import static org.junit.jupiter.api.Assertions.assertEquals;

class FormatterTest {

    Formatter formatter = new Formatter();

    @Test
    void tableFormatter_shouldReturnTable_whenInputMapContainsCorrectElements() {

        Map<List<String>, String> info = new LinkedHashMap<>();
        info.put(new ArrayList<>(Arrays.asList("Brendon Hartley", "SCUDERIA TORO ROSSO HONDA")), "0:00.001");
        info.put(new ArrayList<>(Arrays.asList("Sebastian Vettel", "FERRARI")), "0:00.002");
        info.put(new ArrayList<>(Arrays.asList("Daniel Ricciardo", "RED BULL RACING TAG HEUER")), "0:00.003");

        String actual = formatter.tableFormatter(info);

        String expected =
                "1.Brendon Hartley " + VERTICAL_LINE + "SCUDERIA TORO ROSSO HONDA" + VERTICAL_LINE + "0:00.001" + NEW_LINE +
                "2.Sebastian Vettel" + VERTICAL_LINE + "FERRARI                  " + VERTICAL_LINE + "0:00.002" + NEW_LINE +
                "3.Daniel Ricciardo" + VERTICAL_LINE + "RED BULL RACING TAG HEUER" + VERTICAL_LINE + "0:00.003" + NEW_LINE;

        assertEquals(expected,actual);
    }
}
