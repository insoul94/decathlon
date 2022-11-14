package com.test.decathlon.model;

import javax.xml.bind.annotation.*;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(propOrder = {"participant", "place", "totalScore", "eventList"})
public class ScoreBoard {

    private Participant participant;

    @XmlElementWrapper(name = "events")
    @XmlElement(name = "event")
    private List<Event> eventList;

    @XmlElement(name = "total_score")
    private int totalScore;

    private String place;

    private ScoreBoard() {
    }

    /**
     * @param participant must not be <code>null</code>
     * @param eventList must be of size 10
     */
    public ScoreBoard(Participant participant, List<Event> eventList) {
        this.participant = Objects.requireNonNull(participant);
        if (eventList == null || eventList.size() != 10) {
            throw new IllegalArgumentException("In Decathlon must be 10 events.");
        }
        this.eventList = Collections.unmodifiableList(eventList);
    }

    public Participant getParticipant() {
        return participant;
    }

    public List<Event> getEventList() {
        return eventList;
    }

    public int getTotalScore() {
        return totalScore;
    }

    public void setTotalScore(int totalScore) {
        this.totalScore = totalScore;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

}