package com.test.decathlon.util;

import com.test.decathlon.exception.InvalidDataException;

import java.util.Arrays;

/**
 * CSV file utilities.
 */
public class CsvUtil {
    private CsvUtil() {}

    /**
     * @param line
     * @param delimiter must not be empty
     * @return parsed cells
     * @throws InvalidDataException if delimiter is empty
     */
    public static String[] parseCells(String line, String delimiter) throws InvalidDataException{
        if (delimiter.isEmpty()) {
            throw new InvalidDataException("Delimiter must not be empty");
        }
        return Arrays.stream(line
                        .trim()
                        .split(delimiter))
                .map(String::trim)
                .toArray(String[]::new);
    }
}
