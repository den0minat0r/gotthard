package org.denominator.gotthard.time;

import org.denominator.gotthard.junit.ObjectAssert;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class TimeIntervalTest {
    @Test
    public void intervals() {
        assertEquals(TimeInterval.fromString("10h").milliseconds(), TimeInterval.fromString("600min").milliseconds());
        assertEquals(TimeInterval.fromString("1min").milliseconds(), TimeInterval.fromString("60sec").milliseconds());
        ObjectAssert.assertEquality(TimeInterval.fromString("1min"), TimeInterval.fromString("1min"));
    }
}
