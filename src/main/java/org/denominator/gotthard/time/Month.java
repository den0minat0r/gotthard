package org.denominator.gotthard.time;

public enum Month {
    January,
    February,
    March,
    April,
    May,
    June,
    July,
    August,
    September,
    October,
    November,
    December;

    public static Month valueOf (int value) {
        return Month.values()[value - 1];
    }
}
