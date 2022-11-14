package com.test.decathlon.model;

import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

class UnitTest {

    @Test
    void givenUnitClass_whenGetMinutesSecondsDelimiter_thenReturnDelimiter() {
        String actual = Unit.getMinutesSecondsDelimiter();
        assertThat(actual, is(":"));
    }
}