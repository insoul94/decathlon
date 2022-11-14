package com.test.decathlon.model;


import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;

import static org.hamcrest.Matchers.is;

class EventPropertiesTest {

    @Test
    void givenSeconds_whenIsTrack_thenReturnsTrue() {
        EventProperties ep = new EventProperties("Event Name", 1f, 2f, 3f, Unit.SECONDS);
        assertThat(ep.isTrack(), is(true));
    }

    @Test
    void givenMetres_whenIsTrack_thenReturnsFalse() {
        EventProperties ep = new EventProperties("Event Name", 1f, 2f, 3f, Unit.METRES);
        assertThat(ep.isTrack(), is(false));
    }
}