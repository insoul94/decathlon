package com.test.decathlon.fileparser.impl;

import com.test.decathlon.model.Unit;
import com.test.decathlon.exception.InvalidDataException;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.*;

class UnitsCsvParserTest {

    private UnitsCsvParser unitsCsvParser;

    @Test
    void givenInputUnitsCsv_whenParse_thenReturnListOfUnitsOfSizeTen() throws InvalidDataException, IOException {
        Path testSource = Paths.get("src/test/resources/input-units.csv");
        unitsCsvParser = new UnitsCsvParser(testSource);

        List<Unit> actual = unitsCsvParser.parse();

        assertThat(actual.size(), is(10));
    }

    @Test
    void givenInvalidCellCountInPropertiesCsv_whenParse_thenThrowInvalidDataException() {
        Path testSource = Paths.get("src/test/resources/input-units-invalid-cell-count.csv");
        unitsCsvParser = new UnitsCsvParser(testSource);

        assertThrows(InvalidDataException.class, () -> unitsCsvParser.parse());
    }
}