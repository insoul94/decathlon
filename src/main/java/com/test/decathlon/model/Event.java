package com.test.decathlon.model;


import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlTransient;
import java.util.Objects;

@XmlAccessorType(XmlAccessType.FIELD)
public class Event {

    @XmlElement(name = "properties")
    private EventProperties eventProperties;

    @XmlTransient
    private float performance;

    private int score;

    private Event() {
    }

    /**
     * @param eventProperties event properties for score calculation; must not be <code>null</code>
     * @param performance all metric performances must be stored as metres, temporal - as seconds.
     */
    public Event(EventProperties eventProperties, float performance) {
        this.eventProperties = Objects.requireNonNull(eventProperties);
        this.performance = performance;
    }

    public EventProperties getEventProperties() {
        return eventProperties;
    }

    public float getPerformance() {
        return performance;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    /**
     * Some performances are calculated as centimeters in decathlon formula. Display them accordingly.
     * @return performance in centimeters
     */
    @XmlElement(name = "performance")
    public String getFormattedPerformance() {
        return String.valueOf(
                eventProperties.getUnit() == Unit.CENTIMETRES
                        ? performance * 100
                        : performance);
    }
}
