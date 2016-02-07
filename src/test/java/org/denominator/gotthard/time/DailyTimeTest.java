package org.denominator.gotthard.time;

import org.denominator.gotthard.junit.ObjectAssert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.TimeZone;
import java.util.TreeSet;

import static org.junit.Assert.assertEquals;

public class DailyTimeTest {
    @Test
    public void sorting() {
        final DailyTime d1 = DailyTime.fromString("10:00:00");
        final DailyTime d2 = DailyTime.fromString("10:05:00");
        assertEquals(new ArrayList<>(new TreeSet<>(Arrays.asList(d2, d1))), Arrays.asList(d1, d2));
    }

    @Test
    public void manipulations() {
        final DailyTime dailyTime = DailyTime.fromString("10:00:00");
        assertEquals(100000, dailyTime.time6());
        ObjectAssert.assertEquality(DailyTime.fromString("10:05:00"), dailyTime.add(TimeInterval.fromString("5min")));
        ObjectAssert.assertEquality(DailyTime.fromString("10:05:00"), dailyTime.add(TimeInterval.fromString("300sec")));

        ObjectAssert.assertEquality(dailyTime, DailyTime.fromTime6(dailyTime.time6()));
    }

    @Test(expected = IllegalArgumentException.class)
    public void overflow() {
        DailyTime.fromString("23:59:59").add(TimeInterval.fromString("1sec"));
    }

    @Test
    public void fromInt() {
        for (int hour = 0; hour < 24; hour++) {
            for (int minute = 0; minute < 60; minute++) {
                for (int second = 0; second < 60; second++) {
                    final DailyTime dt = DailyTime.fromMs(hour * DailyTime.MS_IN_HOUR + minute * DailyTime.MS_IN_MINUTE + second * DailyTime.MS_IN_SECOND);
                    assertEquals(new DailyTime(hour, minute, second), dt);
                }
            }
        }
    }

    @Test
    public void dayTimeFromHHMMSS() {
        assertEquals(new DailyTime(17, 10, 42), DailyTime.fromTime6(171042));
    }

    @Test
    public void fromTimestamp() {
        TimeZone gmt = TimeZone.getTimeZone("GMT");
        assertEquals(DailyTime.fromTime6(184515)
                , DailyTime.fromTimestamp(Day.valueOf(20010101).start(gmt) + 18 * 3600 * 1000 + 45 * 1000 * 60 + 15000, gmt));
    }
}
