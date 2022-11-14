package com.test.decathlon.fileparser.impl;

import com.test.decathlon.exception.InvalidDataException;
import com.test.decathlon.model.EventProperties;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;

class PropertiesCsvParserTest {

    private PropertiesCsvParser parser;

    @Test
    void givenPropertiesCsv_whenParse_thenReturnEventPropertiesListOfSizeTen() throws InvalidDataException, IOException {
        Path testSource = Paths.get("src/test/resources/properties.csv");
        parser = new PropertiesCsvParser(testSource);

        List<EventProperties> actual = parser.parse();

        assertThat(actual.size(), is(10));
    }

    @Test
    void givenInvalidRowCountInPropertiesCsv_whenParse_thenThrowInvalidDataException() {
        Path testSource = Paths.get("src/test/resources/properties-invalid-row-count.csv");
        parser = new PropertiesCsvParser(testSource);

        assertThrows(InvalidDataException.class, () -> parser.parse());
    }
}