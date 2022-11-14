package com.test.decathlon.service;

import com.test.decathlon.model.*;
import com.test.decathlon.fileparser.*;
import com.test.decathlon.serializer.DecathlonSerializer;
import com.test.decathlon.util.DataUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Decathlon application business logic implementation.
 */
public class DecathlonService {

    private final PropertiesFileParser propsFileParser;
    private final ResultsFileParser resultsFileParser;
    private final DecathlonSerializer serializer;

    private Decathlon decathlon;


    /**
     * Can accept various interfaces implementations to configure parsing/serialization of/to different file types,
     * input/output formats.
     * @param propsFileParser must not be <code>null</code>
     * @param resultsFileParser must not be <code>null</code>
     * @param serializer must not be <code>null</code>
     */
    public DecathlonService(PropertiesFileParser propsFileParser,
                            ResultsFileParser resultsFileParser,
                            DecathlonSerializer serializer) {
        if (propsFileParser == null || resultsFileParser == null || serializer == null) {
            throw new IllegalArgumentException("DecathlonService members must not be null.");
        }
        this.propsFileParser = propsFileParser;
        this.resultsFileParser = resultsFileParser;
        this.serializer = serializer;
    }

    public Decathlon getDecathlon() {
        return decathlon;
    }

    public PropertiesFileParser getPropsFileParser() {
        return propsFileParser;
    }

    public ResultsFileParser getResultsFileParser() {
        return resultsFileParser;
    }

    public DecathlonSerializer getSerializer() {
        return serializer;
    }


    /**
     * Only this method needed to be invoked to run the entire application logic.
     * @throws Exception while parsing or serialization
     */
    public void run() throws Exception {
        List<EventProperties> epList = propsFileParser.parse();
        Map<Participant, List<Float>> resultsMap = resultsFileParser.parse();
        List<ScoreBoard> sbList = new ArrayList<>();

        resultsMap.entrySet().stream()
                .peek(res -> {
                    ScoreBoard sb = DataUtil.mapToScoreBoard(res.getKey(), res.getValue(), epList);
                    sbList.add(sb);
                })
                .count(); // Finish with terminal operation which runs through all elements

        sbList.stream()
                .flatMap(sb -> sb.getEventList().stream())
                .peek(DecathlonService::calculateScore)
                .count();

        sbList.stream()
                .peek(DecathlonService::calculateTotalScore)
                .count();

        decathlon = new Decathlon(sbList);
        calculatePlaces(decathlon);

        serializer.serialize(decathlon);
    }


    public static void calculateScore(Event event) {
        int score;
        float a = event.getEventProperties().getA();
        float b = event.getEventProperties().getB();
        float c = event.getEventProperties().getC();
        Unit unit = event.getEventProperties().getUnit();
        boolean isTrack = event.getEventProperties().isTrack();
        float performance = event.getPerformance();

        // All performance metrical values stored as metres, but some formulas use centimetres.
        if (unit == Unit.CENTIMETRES) {
            performance *= 100;
        }
        score = (int) Math.round(a * Math.pow(isTrack ? (b - performance) : (performance - b), c));
        event.setScore(score);
    }


    public static void calculateTotalScore(ScoreBoard scoreBoard) {
        int totalScore = 0;
        for (Event event : scoreBoard.getEventList()) {
            totalScore += event.getScore();
        }
        scoreBoard.setTotalScore(totalScore);
    }


    public static void calculatePlaces(Decathlon decathlon) {
        List<ScoreBoard> sbList = decathlon.getScoreBoardList();
        sbList.sort((r1, r2) -> r2.getTotalScore() - r1.getTotalScore());

        if (sbList.size() == 0) {
            return;
        } else if (sbList.size() == 1) {
            sbList.get(0).setPlace("1");
        }

        int[] marks = new int[sbList.size()];
        marks[0] = 1;

        for (int i = 1; i < sbList.size(); i++) {
            int prevScore = sbList.get(i - 1).getTotalScore();
            int currScore = sbList.get(i).getTotalScore();
            if (prevScore == currScore) {
                marks[i] = marks[i - 1];
            } else {
                marks[i] = i + 1;
            }
        }

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < marks.length; i++) {
            if (i == marks.length - 1 || marks[i] != marks[i+1]) {
                sb.append(i + 1);
                for (int j = Integer.parseInt(sb.substring(0, 1)) - 1; j < i + 1; j++) {
                    sbList.get(j).setPlace(sb.toString());
                }
                sb = new StringBuilder();
            } else {
                sb.append(i + 1).append("-");
            }
        }
    }

}
