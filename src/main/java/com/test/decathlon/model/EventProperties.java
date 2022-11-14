package com.test.decathlon.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import java.util.Objects;

@XmlAccessorType(XmlAccessType.NONE)
public class EventProperties {

    @XmlElement
    private String name;
    private float a;
    private float b;
    private float c;
    private Unit unit;

    private EventProperties() {
    }

    /**
     * @param name event name
     * @param a parameter of formula
     * @param b parameter of formula
     * @param c parameter of formula
     * @param unit of formula calculations
     */
    public EventProperties(String name, float a, float b, float c, Unit unit) {
        this.name = Objects.requireNonNull(name);
        this.a = a;
        this.b = b;
        this.c = c;
        this.unit = Objects.requireNonNull(unit);
    }

    public String getName() {
        return name;
    }

    public float getA() {
        return a;
    }

    public float getB() {
        return b;
    }

    public float getC() {
        return c;
    }

    public Unit getUnit() {
        return unit;
    }

    /**
     * @return <code>true</code> if the event is track event (performance measured in seconds),
     *         <code>false</code> if the event is field event (performance measured in metres)
     */
    public boolean isTrack() {
        return unit == Unit.SECONDS || unit == Unit.MINUTES_SECONDS;
    }

    @XmlElement(name = "unit")
    public String getFormattedUnit() {
        return unit.name().toLowerCase();
    }
}