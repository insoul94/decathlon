package com.test.decathlon.util;

import com.test.decathlon.exception.InvalidDataException;
import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.*;

class CsvUtilTest {

    @Test
    void givenStringAndDelimiter_whenParseCells_thenReturnsStringArray() throws InvalidDataException {
        String line = "a :b   b:c:d  :   ee";

        String[] actual = CsvUtil.parseCells(line, ":");

        assertThat(actual, equalTo(new String[]{"a", "b   b", "c", "d", "ee"}));
    }

    @Test
    void givenEmptyDelimiter_whenParseCells_thenThrowsInvalidDataException() {
        assertThrows(InvalidDataException.class, () -> CsvUtil.parseCells("abc", ""));
    }
}