package com.jlyang.hadoop.util;


import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ExcelParserTest {

    @Test
    void parse() {
        String fileName = "data.xlsx";
        String filePath = Objects.requireNonNull(this.getClass().getClassLoader().getResource(fileName)).getPath();
        StringBuilder builder = new StringBuilder();
        try (InputStream inputStream = new FileInputStream(new File(filePath))) {
            for (String line : ExcelParser.parse(inputStream)) {
                builder.append(line).append('\n');
            }
            builder.deleteCharAt(builder.length() - 1);
        } catch (IOException e) {
            e.printStackTrace();
        }
        assertEquals(getExpect(), builder.toString());
    }

    private String getExpect() {
        return "1.0 101.0 5.0\n" +
                "1.0 102.0 3.0\n" +
                "1.0 103.0 2.5\n" +
                "2.0 101.0 2.0\n" +
                "2.0 102.0 2.5\n" +
                "2.0 103.0 5.0\n" +
                "2.0 104.0 2.0\n" +
                "3.0 101.0 2.0\n" +
                "3.0 104.0 4.0\n" +
                "3.0 105.0 4.5\n" +
                "3.0 107.0 5.0\n" +
                "4.0 101.0 5.0\n" +
                "4.0 103.0 3.0\n" +
                "4.0 104.0 4.5\n" +
                "4.0 106.0 4.0\n" +
                "5.0 101.0 4.0\n" +
                "5.0 102.0 3.0\n" +
                "5.0 103.0 2.0\n" +
                "5.0 104.0 4.0\n" +
                "5.0 105.0 3.5\n" +
                "5.0 106.0 4.0";
    }
}