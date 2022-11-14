package com.test.decathlon.util;

import com.test.decathlon.model.Unit;
import com.test.decathlon.exception.InvalidDataException;
import com.test.decathlon.model.Event;
import com.test.decathlon.model.EventProperties;
import com.test.decathlon.model.Participant;
import com.test.decathlon.model.ScoreBoard;

import java.util.ArrayList;
import java.util.List;

import static com.test.decathlon.model.Unit.*;
import static java.lang.Float.parseFloat;

/**
 * Data parsing amd mapping utilities.
 */
public class DataUtil {
    private DataUtil() {}

    /**Parse performance into float from string according to input unit.
     * @param str raw performance string value
     * @param unit of measurement
     * @return field performances parsed into metres, track performances - into seconds.
     * @throws InvalidDataException if invalid input data format or if unit is not <code>METRES</code>,
     * <code>CENTIMETRES</code>, <code>SECONDS</code> nor <code>MINUTES_SECONDS</code>;
     */
    public static float parsePerformance(String str, Unit unit) throws InvalidDataException {
        if (unit == Unit.SECONDS || unit == Unit.MINUTES_SECONDS) {
            return DataUtil.parseSeconds(str, unit);
        } else if (unit == Unit.CENTIMETRES || unit == Unit.METRES) {
            return DataUtil.parseMetres(str, unit);
        } else {
            throw new IllegalArgumentException("Unknown unit. Was: " + unit);
        }
    }


    private static float parseSeconds(String str, Unit unit) throws InvalidDataException {
        float seconds = 0f;
        if (unit == Unit.SECONDS) {
            seconds = parseFloat(str);
        } else {
            String[] parts = str.split(Unit.getMinutesSecondsDelimiter());
            if (parts.length == 2) {
                seconds += parseFloat(parts[0]) * 60;
                seconds += parseFloat(parts[1]);
            } else {
                throw new InvalidDataException(String.format(
                        "Invalid time input data. Must be in format [%s], was: [%s]",
                        Unit.MINUTES_SECONDS.getDescriptor(), str));
            }
        }
        return seconds;
    }


    private static float parseMetres(String str, Unit unit) {
        float metres = parseFloat(str);
        if (unit == Unit.CENTIMETRES) {
            metres /= 100;
        }
        return metres;
    }


    /**
     * @param str must be a descriptor of <code>Unit</code>, i.e. ['m', 'cm', 'sec' or 'min:sec']
     * @return parsed unit
     * @throws InvalidDataException if doesn't match <code>Unit</code> descriptor
     */
    public static Unit parseUnit(String str) throws InvalidDataException {
        if (str.equals(METRES.getDescriptor()))
            return METRES;
        else if (str.equals(CENTIMETRES.getDescriptor()))
            return CENTIMETRES;
        else if (str.equals(SECONDS.getDescriptor()))
            return SECONDS;
        else if (str.equals(MINUTES_SECONDS.getDescriptor()))
            return MINUTES_SECONDS;
        else
            throw new InvalidDataException(String.format(
                    "Unit descriptor must be '%s', '%s', '%s' or '%s'. Was: '%s'",
                    CENTIMETRES.getDescriptor(), METRES.getDescriptor(),
                    SECONDS.getDescriptor(), MINUTES_SECONDS.getDescriptor(), str)
            );
    }


    /**
     * Input string array contract:
     *  <p> #1 - event name; #2, #3, #4 - A, B, C decathlon score formula parameters respectively, #5 - units of
     *  performance used in formula
     * @param arr
     * @return event properties
     * @throws InvalidDataException if cells number is not 5
     */
    public static EventProperties parseEventProperties(String[] arr) throws InvalidDataException {
        if (arr.length != 5) {
            throw new InvalidDataException(
                    "Invalid event properties input data format. Must be 5 cells/containers per line. Was: "
                            + arr.length);
        }
        String name = arr[0];
        float a = parseFloat(arr[1]);
        float b = parseFloat(arr[2]);
        float c = parseFloat(arr[3]);
        Unit unit = parseUnit(arr[4]);
        return new EventProperties(name, a, b, c, unit);
    }


    /**
     * @param participant
     * @param performanceList must be size of 10
     * @param epList must be size of 10
     * @return
     */
    public static ScoreBoard mapToScoreBoard(Participant participant,
                                             List<Float> performanceList,
                                             List<EventProperties> epList) {
        List<Event> eventList = new ArrayList<>();
        for (int i = 0; i < epList.size(); i++) {
            Event e = new Event(epList.get(i), performanceList.get(i));
            eventList.add(e);
        }
        return new ScoreBoard(participant, eventList);
    }
}
