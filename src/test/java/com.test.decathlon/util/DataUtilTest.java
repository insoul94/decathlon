package com.test.decathlon.util;

import com.test.decathlon.exception.InvalidDataException;

import com.test.decathlon.model.EventProperties;
import com.test.decathlon.model.Participant;
import com.test.decathlon.model.ScoreBoard;
import com.test.decathlon.model.Unit;
import org.junit.jupiter.api.Test;

import java.util.List;

import static com.test.decathlon.testutil.MockModel.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertThrows;

class DataUtilTest {

    @Test
    void givenMetres_whenParsePerformance_thenReturnsMetresInFloat() throws InvalidDataException {
        float actual = DataUtil.parsePerformance("1.23", Unit.METRES);
        assertThat(actual, is(1.23f));
    }

    @Test
    void givenCentimetres_whenParsePerformance_thenReturnsMetresInFloat() throws InvalidDataException {
        float actual = DataUtil.parsePerformance("123", Unit.CENTIMETRES);
        assertThat(actual, is(1.23f));
    }

    @Test
    void givenSeconds_whenParsePerformance_thenReturnsSecondsInFloat() throws InvalidDataException {
        float actual = DataUtil.parsePerformance("12.345", Unit.SECONDS);
        assertThat(actual, is(12.345f));
    }

    @Test
    void givenMinutesSeconds_whenParsePerformance_thenReturnsSecondsInFloat() throws InvalidDataException {
        float actual = DataUtil.parsePerformance("1:23.456", Unit.MINUTES_SECONDS);
        assertThat(actual, is(83.456f));
    }

    @Test
    void givenNullUnit_whenParsePerformance_thenThrowsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> DataUtil.parsePerformance("123", null));
    }

    @Test
    void givenInvalidMinutesSecondsFormat_whenParsePerformance_thenThrowsInvalidDataException() {
        assertThrows(InvalidDataException.class, () -> DataUtil.parsePerformance("123", Unit.MINUTES_SECONDS));
    }




    @Test
    void givenMetresDescriptor_whenParseUnit_thenReturnsUnitMetres() throws InvalidDataException {
        Unit actual = DataUtil.parseUnit("m");
        assertThat(actual, is(Unit.METRES));
    }

    @Test
    void givenCentimetresDescriptor_whenParseUnit_thenReturnsUnitCentimetres() throws InvalidDataException {
        Unit actual = DataUtil.parseUnit("cm");
        assertThat(actual, is(Unit.CENTIMETRES));
    }

    @Test
    void givenSecondsDescriptor_whenParseUnit_thenReturnsUnitSeconds() throws InvalidDataException {
        Unit actual = DataUtil.parseUnit("sec");
        assertThat(actual, is(Unit.SECONDS));
    }

    @Test
    void givenMetresDescriptor_whenParseUnit_thenReturnsUnitMinutesSeconds() throws InvalidDataException {
        Unit actual = DataUtil.parseUnit("min:sec");
        assertThat(actual, is(Unit.MINUTES_SECONDS));
    }

    @Test
    void givenInvalidDescriptor_whenParseUnit_thenThrowsInvalidDataException() {
        assertThrows(InvalidDataException.class, () -> DataUtil.parseUnit("seconds"));
    }




    @Test
    void givenStringArray_whenParseEventProperties_thenReturnsEventProperties() throws InvalidDataException {
        String[] strArr = new String[]{"Event Name", "1.2", "3.4", "5.6", "m"};
        EventProperties ep = DataUtil.parseEventProperties(strArr);
        assertAll(
                () -> assertThat(ep.getName(), is("Event Name")),
                () -> assertThat(ep.getA(), is(1.2f)),
                () -> assertThat(ep.getB(), is(3.4f)),
                () -> assertThat(ep.getC(), is(5.6f)),
                () -> assertThat(ep.getUnit(), is(Unit.METRES))
        );
    }

    @Test
    void givenInvalidSizeStringArray_whenParseEventProperties_thenThrowsInvalidDataException() {
        String[] strArr = new String[]{"Event Name"};
        assertThrows(InvalidDataException.class, () -> DataUtil.parseEventProperties(strArr));
    }


    @Test
    void givenParticipantAndPerformanceListAndEventPropertiesList_whenMapToScoreBoard_thenReturnsScoreBoard() {
        Participant participant = mockParticipant();
        List<Float> performanceList = mockPerformanceList();
        List<EventProperties> epList = mockEventPropertiesList();

        ScoreBoard actual = DataUtil.mapToScoreBoard(participant, performanceList, epList);

        assertAll(
                () -> assertThat(actual, notNullValue()),
                () -> assertThat(actual.getEventList().size(), is(performanceList.size())),
                () -> assertThat(actual.getEventList().size(), is(epList.size()))
        );
    }
}