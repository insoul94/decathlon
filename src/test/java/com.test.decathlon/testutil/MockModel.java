package com.test.decathlon.testutil;

import com.test.decathlon.model.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.test.decathlon.model.Unit.*;

public class MockModel {
    public static List<Unit> mockCalculationUnitList() {
        return Stream.of(
                SECONDS, METRES, METRES, METRES, SECONDS, SECONDS, METRES, METRES, METRES, MINUTES_SECONDS
        ).collect(Collectors.toList());
    }

    public static float mockPerformance() {
        return 12f;
    }

    public static Participant mockParticipant() {
        return new Participant("Mr. Mock");
    }

    public static EventProperties mockEventProperties() {
        return new EventProperties("Mock EventProperties", 10f, 5f, 1.5f, METRES);
    }

    public static List<EventProperties> mockEventPropertiesList() {
        List<EventProperties> epList = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            epList.add(mockEventProperties());
        }
        return epList;
    }

    public static Event mockEvent() {
        return new Event(mockEventProperties(), mockPerformance());
    }

    public static List<Float> mockPerformanceList(){
        Participant participant = mockParticipant();
        List<Float> performanceList = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            performanceList.add(1f);
        }
        return performanceList;
    }

    public static Map<Participant, List<Float>> mockResultsMap() {
        Map<Participant, List<Float>> resultsMap = new HashMap<>();
        resultsMap.put(mockParticipant(), mockPerformanceList());
        return resultsMap;
    }

    public static ScoreBoard mockScoreBoard() {
        List<Event> eventList = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            eventList.add(mockEvent());
        }
        return new ScoreBoard(mockParticipant(), eventList);
    }

    public static Decathlon mockDecathlon() {
        List<ScoreBoard> sbList = new ArrayList<>();
        for (int i = 0; i < 6; i++) {
            sbList.add(mockScoreBoard());
        }
        return new Decathlon(sbList);
    }
}
