package org.denominator.gotthard.time;

import java.util.Calendar;

public enum Weekday {
    Mon(Calendar.MONDAY), Tue(Calendar.TUESDAY), Wed(Calendar.WEDNESDAY), Thu(Calendar.THURSDAY), Fri(Calendar.FRIDAY), Sat(Calendar.SATURDAY), Sun(Calendar.SUNDAY);

    private final int index;

    Weekday(int index) {
        this.index = index;
    }

    public int index() {
        return index;
    }

    public static Weekday valueOf(int index) {
        for (Weekday weekday : values()) {
            if (weekday.index() == index) {
                return weekday;
            }
        }
        return null;
    }
}
