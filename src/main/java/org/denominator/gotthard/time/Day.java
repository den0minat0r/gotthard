package org.denominator.gotthard.time;

import org.denominator.gotthard.lang.Assertions;

import java.util.*;

/**
 * Fast implementation of a day
 */
public final class Day implements Comparable<Day> {
    static final ThreadLocal<Calendar> sharedCalendar = new ThreadLocal<Calendar>() {
        @Override
        protected Calendar initialValue() {
            Calendar calendar = new GregorianCalendar();
            calendar.setLenient(false);
            return calendar;
        }
    };

    static final ThreadLocal<TimeZone> defaultTimeZone = new ThreadLocal<TimeZone>() {
        @Override
        protected TimeZone initialValue() {
            return TimeZone.getDefault();
        }
    };

    public static final int YEARS_BUFFER = 100;
    public static final int START_YEAR = Calendar.getInstance().get(Calendar.YEAR) - YEARS_BUFFER;
    public static final int END_YEAR   = Calendar.getInstance().get(Calendar.YEAR) + YEARS_BUFFER;

    public static final  int   MAX_DAYS_IN_MONTH  = 31;
    public static final  int   MAX_MONTHS_IN_YEAR = 12;
    public static final  int   MAX_DAYS_IN_YEAR   = MAX_DAYS_IN_MONTH * MAX_MONTHS_IN_YEAR;
    private static final Day[] daysCache          = new Day[(END_YEAR - START_YEAR) * MAX_DAYS_IN_YEAR];

    private static int dayIndex(int year, int month, int day) {
        if (year < START_YEAR || year >= END_YEAR) {
            return -1;
        }
        return (year - START_YEAR) * MAX_DAYS_IN_YEAR + (month - 1) * MAX_DAYS_IN_MONTH + (day - 1);
    }

    static {
        Arrays.fill(daysCache, null);
        final Calendar calendar = new GregorianCalendar(START_YEAR, 0, 1);
        while (calendar.get(Calendar.YEAR) < END_YEAR) {
            final Day day = fromCalendar(calendar);
            final int index = dayIndex(day.year(), day.month(), day.day());
            Assertions.isTrue(index != -1, "Invalid index for {}", day);
            daysCache[index] = day;
            calendar.add(Calendar.DAY_OF_MONTH, 1);
        }
    }

    public static final Day MINIMUM_DAY = new Day(1, 1, 1);
    public static final Day MAXIMUM_DAY = new Day(9999, 12, 31);

    private final int     year;
    private final int     month;
    private final int     day;
    private final int     date8;
    private final Weekday weekday;

    public static Day today() {
        return Day.today(defaultTimeZone.get());
    }

    public static Day today(TimeZone timeZone) {
        return fromTimeStamp(System.currentTimeMillis(), timeZone);
    }

    public static Day fromTimeStamp(long timeStamp, TimeZone timeZone) {
        Calendar calendar = sharedCalendar.get();
        calendar.setTimeZone(timeZone);
        calendar.setTimeInMillis(timeStamp);

        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH) + 1;
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        return Day.newDay(year, month, day);
    }

    public static Day valueOf(double day) {
        return valueOf((int) day);
    }

    public static Day valueOf(int day) {
        return newDay(day / 10000, (day / 100) % 100, day % 100);
    }

    public static Day valueOf(String day) {
        return valueOf(Integer.valueOf(day));
    }

    public static Day newDay(int year, int month, int day) {
        final int cacheIndex = dayIndex(year, month, day);
        if (cacheIndex != -1) {
            return daysCache[cacheIndex];
        }
        return new Day(year, month, day);
    }

    static Day fromCalendar(Calendar calendar) {
        return new Day(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH) + 1, calendar.get(Calendar.DAY_OF_MONTH));
    }

    private Day(int year, int month, int day) {
        this.year = year;
        this.month = month;
        this.day = day;
        this.date8 = 10000 * year + 100 * month + day;

        Calendar calendar = sharedCalendar.get();
        //noinspection MagicConstant
        calendar.set(year, month - 1, day, 0, 0, 0);

        Assertions.isTrue(year == calendar.get(Calendar.YEAR), "year");
        Assertions.isTrue(month == 1 + calendar.get(Calendar.MONTH), "month");
        Assertions.isTrue(day == calendar.get(Calendar.DAY_OF_MONTH), "day");

        weekday = Weekday.valueOf(calendar.get(Calendar.DAY_OF_WEEK));
    }

    public static SortedSet<Day> workingDays(Day start, Day end) {
        Assertions.isTrue(end.date8() >= start.date8(), "end.date8() >= start.date8()");
        Day day = start;
        SortedSet<Day> days = new TreeSet<>();
        do {
            if (!day.isWeekend()) {
                days.add(day);
            }
            day = day.next();
        } while (day.date8() <= end.date8());
        return days;
    }

    public static SortedSet<Day> workingDays(Day start, int daysBack) {
        final SortedSet<Day> days = new TreeSet<>();
        Day day = start;
        for (int i = 0; i < daysBack; i++) {
            day = day.previous();
            if (day.isWeekend()) {
                continue;
            }
            days.add(day);
        }
        return days;
    }

    public Calendar asCalendar() {
        return new GregorianCalendar(year, month - 1, day, 0, 0, 0);
    }

    public boolean isWeekend() {
        return weekday == Weekday.Sat || weekday == Weekday.Sun;
    }

    public int date8() {
        return date8;
    }

    public int year() {
        return year;
    }

    public int month() {
        return month;
    }

    public int day() {
        return day;
    }

    private Calendar prepareCalendar(TimeZone timeZone) {
        Calendar calendar = sharedCalendar.get();
        calendar.setTimeZone(timeZone);
        //noinspection MagicConstant
        calendar.set(year, month - 1, day, 0, 0, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar;
    }

    public long start(TimeZone timeZone) {
        Calendar calendar = prepareCalendar(timeZone);
        return calendar.getTimeInMillis();
    }

    public long end(TimeZone timeZone) {
        Calendar calendar = prepareCalendar(timeZone);
        calendar.add(Calendar.DAY_OF_MONTH, 1);
        return calendar.getTimeInMillis() - 1;
    }

    public Day next() {
        final int cacheIndex = dayIndex(year, month, day);
        if (cacheIndex != -1) {
            for (int i = cacheIndex + 1; i < daysCache.length; i++) {
                if (daysCache[i] != null) {
                    return daysCache[i];
                }
            }
        }
        Calendar calendar = prepareCalendar(defaultTimeZone.get());
        calendar.add(Calendar.DAY_OF_MONTH, 1);
        return fromCalendar(calendar);
    }

    public Day previous() {
        final int cacheIndex = dayIndex(year, month, day);
        if (cacheIndex != -1) {
            for (int i = cacheIndex - 1; i >= 0; i--) {
                if (daysCache[i] != null) {
                    return daysCache[i];
                }
            }
        }
        Calendar calendar = prepareCalendar(defaultTimeZone.get());
        calendar.add(Calendar.DAY_OF_MONTH, -1);
        return fromCalendar(calendar);
    }

    public Day previousWorkingDay() {
        Day day = this.previous();
        while (day.isWeekend()) {
            day = day.previous();
        }
        return day;
    }

    public Day nextWorkingDay() {
        Day day = this.next();
        while (day.isWeekend()) {
            day = day.next();
        }
        return day;
    }

    public Day addMonth() {
        Day day = this;
        while (day.month() == month()) {
            day = day.next();
        }
        return day;
    }

    private enum DayIterator {

        DayIncrement {
            Day iterate(Day day) {return day.next();}
        },
        DayDecrement {
            Day iterate(Day day) {return day.previous();}
        },
        WorkingDayIncrement {
            Day iterate(Day day) {return day.nextWorkingDay();}
        },
        WorkingDayDecrement {
            Day iterate(Day day) {return day.previousWorkingDay();}
        };

        abstract Day iterate(Day day);
    }

    private Day iterateDay(DayIterator iterator, int numberOfIterations) {
        Day result = this;
        for (int i = 0; i < numberOfIterations; i++) {
            result = iterator.iterate(result);
        }
        return result;
    }

    private Day iterateDay(DayIterator incrementIterator, DayIterator decrementIterator, int numberOfIterations) {
        DayIterator iterator = numberOfIterations > 0 ? incrementIterator : decrementIterator;
        return iterateDay(iterator, Math.abs(numberOfIterations));
    }

    public Day add(int days) {
        return iterateDay(DayIterator.DayIncrement, DayIterator.DayDecrement, days);
    }

    public Day remove(int days) {
        return add(-days);
    }

    public Day addWorkingDays(int days) {
        return iterateDay(DayIterator.WorkingDayIncrement, DayIterator.WorkingDayDecrement, days);
    }

    public Day removeWorkingDays(int days) {
        return addWorkingDays(-days);
    }

    public boolean after(Day day) {
        return compareTo(day) == 1;
    }

    public boolean before(Day day) {
        return compareTo(day) == -1;
    }

    public boolean beforeOrEqual(Day day) {
        final int res = compareTo(day);
        return res == -1 || res == 0;
    }

    public boolean afterOrEqual(Day day) {
        final int res = compareTo(day);
        return res == 1 || res == 0;
    }

    public Weekday weekDay() {
        return weekday;
    }

    @Override
    public int compareTo(Day that) {
        if (date8 < that.date8) {
            return -1;
        }
        if (date8 > that.date8) {
            return 1;
        }
        return 0;
    }

    /**
     * @return number of working days passed excluding today
     */
    public int workingDaysInMonthPassed() {
        Day day = previousWorkingDay();
        int passed = 0;
        while (day.month() == month()) {
            day = day.previousWorkingDay();
            passed++;
        }
        return passed;
    }

    /**
     * @return number of working days left in this month excluding today
     */
    public int workingDaysInMonthLeft() {
        Day day = nextWorkingDay();
        int passed = 0;
        while (day.month() == month()) {
            day = day.nextWorkingDay();
            passed++;
        }
        return passed;
    }

    /**
     * Returns number of full working days from this day till the input day. The result includes this day but excludes
     * the input day.
     *
     * @param day until which the number of working days is computed
     * @return number of working days between this and the input day.
     * @throws IllegalArgumentException if this or the input day is a weekend.
     */
    public int workingDaysTo(Day day) {
        Assertions.isTrue(!day.isWeekend() && !this.isWeekend(), "Works only for working days. {} {}->{} {}", this, this.weekday, day, day.weekday);
        if (beforeOrEqual(day)) {
            return this.positiveWorkingDaysTo(day);
        } else {
            return -day.positiveWorkingDaysTo(this);
        }
    }

    public int positiveWorkingDaysTo(Day day) {
        Assertions.isTrue(beforeOrEqual(day), "The receiver day has to be before or equal to the paremeter day.");
        int count = 0;
        Day d = this;
        while (d.before(day)) {
            d = d.nextWorkingDay();
            count++;
        }
        return count;
    }

    public Day firstMonthDay() {
        return Day.newDay(year, month, 1);
    }

    public Day firstYearDay() {
        return Day.newDay(year, 1, 1);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Day day1 = (Day) o;

        return day == day1.day && month == day1.month && year == day1.year;
    }

    @Override
    public int hashCode() {
        return date8;
    }

    @Override
    public String toString() {
        return String.format("%02d.%02d.%04d", day, month, year);
    }

    public String toLongString() {
        return String.format("%s %d, %d", Month.valueOf(month).name(), day, year);
    }

    public long getDayMiddle(TimeZone timeZone) {
        Calendar calendar = prepareCalendar(timeZone);
        calendar.add(Calendar.HOUR_OF_DAY, 12);
        return calendar.getTimeInMillis();
    }
}
