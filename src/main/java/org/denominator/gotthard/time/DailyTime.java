package org.denominator.gotthard.time;

import org.denominator.gotthard.lang.Assertions;
import org.denominator.gotthard.lang.Strings;

import java.util.TimeZone;

/**
 * Represents a time point within a day from 00:00:00 up to 23:59:59
 */
public final class DailyTime implements Comparable<DailyTime> {
    public static final int SECONDS_IN_HOUR   = 3600;
    public static final int SECONDS_IN_MINUTE = 60;
    public static final int MS_IN_SECOND      = 1000;
    public static final int MS_IN_HOUR        = 3600000;
    public static final int MS_IN_MINUTE      = 60000;

    private final int milliseconds;
    private final int hour;
    private final int minute;
    private final int second;

    public DailyTime(String s) {
        String[] split = Strings.split(s, ':');
        Assertions.isTrue(split.length == 3, "Wrong format.");

        this.hour = Integer.valueOf(split[0]);
        this.minute = Integer.valueOf(split[1]);
        this.second = Integer.valueOf(split[2]);

        Assertions.isTrue(this.hour >= 0 && this.hour <= 23, "wrong hour");
        Assertions.isTrue(this.minute >= 0 && this.minute <= 59, "wrong minute");
        Assertions.isTrue(this.second >= 0 && this.second <= 59, "wrong second");

        this.milliseconds = (hour * SECONDS_IN_HOUR + minute * SECONDS_IN_MINUTE + second) * MS_IN_SECOND;
    }

    private DailyTime(int hhmmss) {
        this(hhmmss / 10000, (hhmmss / 100) % 100, hhmmss % 100);
    }

    public DailyTime(int hour, int minute, int second) {
        this(hour + ":" + minute + ":" + second);
    }

    public int msSinceMidnight() {
        return milliseconds;
    }

    @Override
    public int compareTo(DailyTime o) {
        return Integer.compare(milliseconds, o.milliseconds);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        DailyTime dailyTime = (DailyTime) o;

        return milliseconds == dailyTime.milliseconds;

    }

    @Override
    public int hashCode() {
        return milliseconds;
    }

    @Override
    public String toString() {
        return String.format("%02d:%02d:%02d", hour, minute, second);
    }

    public static DailyTime fromString(String string) {
        return new DailyTime(string);
    }

    public static DailyTime fromTime6(int time6) {
        return new DailyTime(time6);
    }

    public static DailyTime fromTimestamp(long timeStamp, TimeZone timeZone) {
        final Day day = Day.fromTimeStamp(timeStamp, timeZone);
        return fromMs((int) (timeStamp - day.start(timeZone)));
    }

    public static DailyTime fromMs(int ms) {
        Assertions.isTrue(ms < 24 * MS_IN_HOUR, "ms < 24*MS_IN_HOUR, {}", ms);
        final int hour = (int) Math.floor(ms / MS_IN_HOUR);
        final int minute = (int) Math.floor((ms - hour * MS_IN_HOUR) / MS_IN_MINUTE);
        final int second = (int) Math.floor((ms - minute * MS_IN_MINUTE - hour * MS_IN_HOUR) / MS_IN_SECOND);

        final int crossCheck = (hour * SECONDS_IN_HOUR + minute * SECONDS_IN_MINUTE + second) * MS_IN_SECOND;
        Assertions.isTrue(Math.abs(ms - crossCheck) <= MS_IN_SECOND
                , "ms == (hour * 3600 + minute * 60 + second) * 1000, {} != {}", ms, crossCheck);
        return new DailyTime(hour, minute, second);
    }

    public int time6() {
        return hour * 10000 + minute * 100 + second;
    }

    public DailyTime add(TimeInterval interval) {
        return fromMs(msSinceMidnight() + (int) interval.milliseconds());
    }
}

