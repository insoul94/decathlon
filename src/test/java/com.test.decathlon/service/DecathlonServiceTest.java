package com.test.decathlon.service;

import com.test.decathlon.fileparser.PropertiesFileParser;
import com.test.decathlon.fileparser.ResultsFileParser;
import com.test.decathlon.model.Decathlon;
import com.test.decathlon.model.Event;
import com.test.decathlon.model.ScoreBoard;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import com.test.decathlon.serializer.DecathlonSerializer;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static com.test.decathlon.testutil.MockModel.*;

@ExtendWith(MockitoExtension.class)
class DecathlonServiceTest {

    @InjectMocks
    private DecathlonService decathlonService;
    @Mock
    private PropertiesFileParser propsFileParser;
    @Mock
    private ResultsFileParser resultsFileParser;
    @Mock
    private DecathlonSerializer serializer;

    @Test
    void givenPropertiesAndResults_whenRun_thenCreateDecathlon() throws Exception {
        when(propsFileParser.parse()).thenReturn(mockEventPropertiesList());
        when(resultsFileParser.parse()).thenReturn(mockResultsMap());
        decathlonService = new DecathlonService(propsFileParser, resultsFileParser, serializer);

        decathlonService.run();

        Decathlon decathlon = decathlonService.getDecathlon();
        assertNotNull(decathlon);
    }

    @Test
    void givenEvent_whenCalculateScore_thenSetScore() {
        Event event = mockEvent();

        DecathlonService.calculateScore(event);

        assertThat(event.getScore(), is(185));
    }

    @Test
    void givenScoreBoard_whenCalculateTotalScore_thenSetTotalScore() {
        ScoreBoard scoreBoard = mockScoreBoard();
        scoreBoard.getEventList().stream()
                .peek(event -> event.setScore(100))
                .count();

        DecathlonService.calculateTotalScore(scoreBoard);

        assertThat(scoreBoard.getTotalScore(), is(1_000));
    }

    @Test
    void givenDecathlon_whenCalculatePlaces_thenSetPlaces() {
        Decathlon decathlon = mockDecathlon();
        List<ScoreBoard> sbList = decathlon.getScoreBoardList();
        sbList.get(0).setTotalScore(1_000);
        sbList.get(1).setTotalScore(1_000);
        sbList.get(2).setTotalScore(2_000);
        sbList.get(3).setTotalScore(3_000);
        sbList.get(4).setTotalScore(3_000);
        sbList.get(5).setTotalScore(3_000);

        DecathlonService.calculatePlaces(decathlon);

        assertAll(
                () -> assertThat(sbList.get(0).getPlace(), is("1-2-3")),
                () -> assertThat(sbList.get(1).getPlace(), is("1-2-3")),
                () -> assertThat(sbList.get(2).getPlace(), is("1-2-3")),
                () -> assertThat(sbList.get(3).getPlace(), is("4")),
                () -> assertThat(sbList.get(4).getPlace(), is("5-6")),
                () -> assertThat(sbList.get(5).getPlace(), is("5-6"))

        );
    }

    @Test
    void givenDecathlonWithOneScoreBoard_whenCalculatePlaces_thenSetFirstPlace() {
        ScoreBoard sb = mockScoreBoard();
        List<ScoreBoard> sbList = new ArrayList<>();
        sbList.add(sb);
        Decathlon decathlon = new Decathlon(sbList);

        DecathlonService.calculatePlaces(decathlon);

        assertThat(sbList.get(0).getPlace(), is("1"));
    }
}