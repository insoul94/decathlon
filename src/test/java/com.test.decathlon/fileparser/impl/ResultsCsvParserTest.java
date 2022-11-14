package com.test.decathlon.fileparser.impl;

import com.test.decathlon.exception.InvalidDataException;
import com.test.decathlon.fileparser.UnitsFileParser;
import com.test.decathlon.model.Participant;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static com.test.decathlon.testutil.MockModel.*;

@ExtendWith(MockitoExtension.class)
class ResultsCsvParserTest {

    @Mock
    private UnitsFileParser unitsFileParserMock;

    private ResultsCsvParser resultsCsvParser;

    @Test
    void givenResultsCsv_whenParse_thenReturnHashMapOfParticipantAndFloatListEachOfSizeTen() throws InvalidDataException, IOException {
        Path testSource = Paths.get("src/test/resources/results.csv");
        resultsCsvParser = new ResultsCsvParser(testSource, unitsFileParserMock);
        when(unitsFileParserMock.parse()).thenReturn(mockCalculationUnitList());

        Map<Participant, List<Float>> actual = resultsCsvParser.parse();

        assertAll(
                () -> assertThat(actual.size(), is(4)),
                () -> assertThat(new ArrayList<>(actual.values()), everyItem(hasSize(10)))
        );

        verify(unitsFileParserMock).parse();
    }

    @Test
    void givenInvalidCellCountInPropertiesCsv_whenParse_thenThrowInvalidDataException() {
        Path testSource = Paths.get("src/test/resources/results-invalid-cell-count.csv");
        resultsCsvParser = new ResultsCsvParser(testSource, unitsFileParserMock);

        assertThrows(InvalidDataException.class, () -> resultsCsvParser.parse());
    }
}