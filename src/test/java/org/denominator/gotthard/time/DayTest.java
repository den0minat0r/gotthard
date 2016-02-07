package org.denominator.gotthard.time;

import org.denominator.gotthard.collection.Creator;
import org.denominator.gotthard.junit.ObjectAssert;
import org.junit.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

import static org.junit.Assert.*;

public class DayTest {
    @Test
    public void equality () {
        ObjectAssert.assertEquality(Day.valueOf(90990101), Day.valueOf("90990101"));
    }

    @Test
    public void workingDays() {
        assertEquals(Creator.sortedSet(20121204, 20121203, 20121130), Day.workingDays(Day.valueOf(20121205), 5)
                .stream().map(Day::date8).collect(Collectors.toCollection(TreeSet::new)));

        assertEquals(Creator.sortedSet(20121203, 20121130), Day.workingDays(Day.valueOf(20121130), Day.valueOf(20121203))
                .stream().map(Day::date8).collect(Collectors.toCollection(TreeSet::new)));

        assertEquals(Creator.<Integer>sortedSet(), Day.workingDays(Day.valueOf(20121201), Day.valueOf(20121202))
                        .stream().map(Day::date8).collect(Collectors.toCollection(TreeSet::new)));
    }

    @Test
    public void nextMonth() {
        assertEquals(Day.valueOf(20010201), Day.valueOf(20010101).addMonth());
        assertEquals(Day.valueOf(20020101), Day.valueOf(20011215).addMonth());
    }

    @Test
    public void dayOfWeek() {
        assertNull(Weekday.valueOf(-1));
        assertEquals(Weekday.Mon, Day.valueOf(20121126).weekDay());
        assertEquals(Weekday.Tue, Day.valueOf(20121127).weekDay());
        assertEquals(Weekday.Wed, Day.valueOf(20121128).weekDay());
        assertEquals(Weekday.Thu, Day.valueOf(20121129).weekDay());
        assertEquals(Weekday.Fri, Day.valueOf(20121130).weekDay());
        assertEquals(Weekday.Sat, Day.valueOf(20121201).weekDay());
        assertEquals(Weekday.Sun, Day.valueOf(20121202).weekDay());
    }

    @Test
    public void calendar() {
        final Day today = Day.today(TimeZone.getDefault());

        assertEquals(today.asCalendar().getTimeInMillis() + 12 * 3600 * 1000
                , today.getDayMiddle(TimeZone.getDefault()));
    }

    @Test
    public void prevWorkingDay() {
        assertEquals(20120907, Day.valueOf(20120909).previousWorkingDay().date8());
        assertEquals(20120907, Day.valueOf(20120908).previousWorkingDay().date8());
        assertEquals(20120907, Day.valueOf(20120910).previousWorkingDay().date8());
        assertEquals(20120906, Day.valueOf(20120907).previousWorkingDay().date8());
    }

    @Test
    public void nextWorkingDay() {
        assertEquals(20120910, Day.valueOf(20120909).nextWorkingDay().date8());
        assertEquals(20120910, Day.valueOf(20120908).nextWorkingDay().date8());
        assertEquals(20120911, Day.valueOf(20120910).nextWorkingDay().date8());
        assertEquals(20120910, Day.valueOf(20120907).nextWorkingDay().date8());
    }

    @Test
    public void parseDate() throws ParseException {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm");

        final Day actual = Day.fromTimeStamp(format.parse("2008-02-10 23:00").getTime(), TimeZone.getDefault());

        assertEquals(2008, actual.year());
        assertEquals(2, actual.month());
        assertEquals(10, actual.day());
        assertEquals(20080210, actual.date8());

        assertEquals(Day.valueOf(20080210), actual);
        assertEquals(Day.valueOf(20080210).hashCode(), actual.hashCode());
        assertEquals("10.02.2008", actual.toString());
    }

    @Test
    public void differentTimeZones() {
        Day d1 = Day.valueOf(20110101);

        assertEquals(3600 * 1000, d1.start(TimeZone.getTimeZone("GMT+1")) - d1.start(TimeZone.getTimeZone("GMT+2")));
    }

    @Test
    public void comparable() {
        List<Day> unsorted = Arrays.asList(Day.valueOf(20100101), Day.valueOf(20090101), Day.valueOf(20100101), Day.valueOf(20080101));
        Collections.sort(unsorted);

        assertEquals(Arrays.asList(Day.valueOf(20080101), Day.valueOf(20090101), Day.valueOf(20100101), Day.valueOf(20100101)), unsorted);
    }

    @Test
    public void day8() {
        assertEquals(20011009, Day.valueOf(20011009).date8());
    }

    @Test(expected = IllegalArgumentException.class)
    public void wrongDay8() {
        Day.valueOf(25648546);
    }

    @Test
    public void nextDay() {
        assertEquals(Day.valueOf(20100101), Day.valueOf(20091231).next());
        assertEquals(Day.valueOf(20100102), Day.valueOf(20100101).next());
    }

    @Test
    public void prevousDay() {
        assertEquals(Day.valueOf(20091231), Day.valueOf(20100101).previous());
        assertEquals(Day.valueOf(20100101), Day.valueOf(20100102).previous());

        assertEquals(Day.valueOf(20100101), Day.valueOf(20100101).next().previous());
        assertEquals(Day.valueOf(20100101), Day.valueOf(20100101).previous().next());
    }

    @Test
    public void isWeekend() {
        assertTrue(Day.valueOf(20100717).isWeekend());
        assertTrue(Day.valueOf(20100718).isWeekend());
        assertFalse(Day.valueOf(20100716).isWeekend());
        assertFalse(Day.valueOf(20100720).isWeekend());
    }

    @Test
    public void now() {
        Day d = Day.today(TimeZone.getDefault());
        assertEquals(24 * 3600 * 1000 - 1, d.end(TimeZone.getDefault()) - d.start(TimeZone.getDefault()));
    }

    @Test
    public void add() {
        assertEquals(Day.valueOf(20100104), Day.valueOf(20100104).add(0));
        assertEquals(Day.valueOf(20100101), Day.valueOf(20100104).add(-3));
        assertEquals(Day.valueOf(20100104), Day.valueOf(20100101).add(3));
    }

    @Test
    public void remove() {
        assertEquals(Day.valueOf(20100104), Day.valueOf(20100104).remove(0));
        assertEquals(Day.valueOf(20100101), Day.valueOf(20100104).remove(3));
        assertEquals(Day.valueOf(20100104), Day.valueOf(20100101).remove(-3));
    }

    @Test
    public void addWorkingDays() {
        //2013.03.07 is Thursday
        assertEquals(Day.valueOf(20130307), Day.valueOf(20130307).addWorkingDays(0));
        assertEquals(Day.valueOf(20130308), Day.valueOf(20130307).addWorkingDays(1));
        //2013.03.11 is Monday
        assertEquals(Day.valueOf(20130311), Day.valueOf(20130307).addWorkingDays(2));
        assertEquals(Day.valueOf(20130312), Day.valueOf(20130307).addWorkingDays(3));

        assertEquals(Day.valueOf(20130306), Day.valueOf(20130307).addWorkingDays(-1));
        assertEquals(Day.valueOf(20130304), Day.valueOf(20130307).addWorkingDays(-3));
        //2013.03.01 is Friday
        assertEquals(Day.valueOf(20130301), Day.valueOf(20130307).addWorkingDays(-4));
        assertEquals(Day.valueOf(20130228), Day.valueOf(20130307).addWorkingDays(-5));
    }

    @Test
    public void removeWorkingDays() {
        //2013.03.07 is Thursday
        assertEquals(Day.valueOf(20130307), Day.valueOf(20130307).removeWorkingDays(0));
        assertEquals(Day.valueOf(20130307), Day.valueOf(20130308).removeWorkingDays(1));
        //2013.03.11 is Monday
        assertEquals(Day.valueOf(20130307), Day.valueOf(20130311).removeWorkingDays(2));
        assertEquals(Day.valueOf(20130307), Day.valueOf(20130312).removeWorkingDays(3));

        assertEquals(Day.valueOf(20130307), Day.valueOf(20130306).removeWorkingDays(-1));
        assertEquals(Day.valueOf(20130307), Day.valueOf(20130304).removeWorkingDays(-3));
        //2013.03.01 is Friday
        assertEquals(Day.valueOf(20130307), Day.valueOf(20130301).removeWorkingDays(-4));
        assertEquals(Day.valueOf(20130307), Day.valueOf(20130228).removeWorkingDays(-5));
    }

    @Test
    public void before() {
        Day d = Day.valueOf(20100104);
        assertTrue(d.before(Day.valueOf(20100105)));
        assertFalse(d.before(Day.valueOf(20100104)));
        assertFalse(d.before(Day.valueOf(20100103)));

        assertTrue(d.beforeOrEqual(Day.valueOf(20100105)));
        assertTrue(d.beforeOrEqual(Day.valueOf(20100104)));
        assertFalse(d.beforeOrEqual(Day.valueOf(20100103)));
    }

    @Test
    public void after() {
        Day d = Day.valueOf(20100104);

        assertFalse(d.after(Day.valueOf(20100105)));
        assertFalse(d.after(Day.valueOf(20100104)));
        assertTrue(d.after(Day.valueOf(20100103)));

        assertFalse(d.afterOrEqual(Day.valueOf(20100105)));
        assertTrue(d.afterOrEqual(Day.valueOf(20100104)));
        assertTrue(d.afterOrEqual(Day.valueOf(20100103)));
    }

    @Test
    public void monthWorkingDays() {
        Day day = Day.valueOf(20130123);
        assertEquals(6, day.workingDaysInMonthLeft());
        assertEquals(16, day.workingDaysInMonthPassed());

        day = Day.valueOf(20130126);
        assertEquals(4, day.workingDaysInMonthLeft());
        day = Day.valueOf(20130105);
        assertEquals(4, day.workingDaysInMonthPassed());

        day = Day.valueOf(20130101);
        assertEquals(0, day.workingDaysInMonthPassed());
        assertEquals(22, day.workingDaysInMonthLeft());

        day = Day.valueOf(20130131);
        assertEquals(0, day.workingDaysInMonthLeft());
    }

    @Test
    public void firstDays() {
        assertEquals(20130101, Day.valueOf("20130123").firstMonthDay().date8());
        assertEquals(20130101, Day.valueOf("20130123").firstYearDay().date8());
    }

    @Test
    public void workingDaysTo() {
        Day thursday = Day.valueOf(20130228);
        Day friday = Day.valueOf(20130301);
        Day mondayNextWeek = Day.valueOf(20130304);
        Day tuesdayNextWeek = Day.valueOf(20130305);

        assertEquals(0, friday.workingDaysTo(friday));
        assertEquals(1, friday.workingDaysTo(mondayNextWeek));
        assertEquals(-1, mondayNextWeek.workingDaysTo(friday));
        assertEquals(2, friday.workingDaysTo(tuesdayNextWeek));
        assertEquals(-1, friday.workingDaysTo(thursday));
        assertEquals(3, thursday.workingDaysTo(tuesdayNextWeek));
        assertEquals(-3, tuesdayNextWeek.workingDaysTo(thursday));
    }

    @Test(expected = IllegalArgumentException.class)
    public void workingDaysToExceptionReceiver() {
        Day friday = Day.valueOf(20130301);
        Day saturday = Day.valueOf(20130302);

        saturday.workingDaysTo(friday);
    }

    @Test(expected = IllegalArgumentException.class)
    public void workingDaysToExceptionParameter() {
        Day friday = Day.valueOf(20130301);
        Day saturday = Day.valueOf(20130302);

        friday.workingDaysTo(saturday);
    }

    @Test
    public void weekendCached() {
        assertTrue(Day.valueOf(20990502).isWeekend());
        assertFalse(Day.valueOf(20990501).isWeekend());
        assertTrue(Day.valueOf(19000106).isWeekend());
        assertTrue(Day.valueOf(19991204).isWeekend());
        assertFalse(Day.valueOf(19991206).isWeekend());
    }

    @Test
    public void testCacheUp() {
        Day day = Day.MINIMUM_DAY;
        Calendar calendar = new GregorianCalendar(day.year(), day.month() - 1, day.day());
        while (calendar.get(Calendar.YEAR) <= Day.MAXIMUM_DAY.year()) {
            assertEquals(day.year(), calendar.get(Calendar.YEAR));
            assertEquals(day.month(), calendar.get(Calendar.MONTH) + 1);
            assertEquals(day.day(), calendar.get(Calendar.DAY_OF_MONTH));

            calendar.add(Calendar.DAY_OF_MONTH, 1);
            day = day.next();
        }
    }

    @Test
    public void testCacheDown() {
        Day day = Day.MAXIMUM_DAY;
        Calendar calendar = new GregorianCalendar(day.year(), day.month() - 1, day.day());
        while (calendar.get(Calendar.YEAR) > 1) {
            assertEquals(day.year(), calendar.get(Calendar.YEAR));
            assertEquals(day.month(), calendar.get(Calendar.MONTH) + 1);
            assertEquals(day.day(), calendar.get(Calendar.DAY_OF_MONTH));

            calendar.add(Calendar.DAY_OF_MONTH, -1);
            day = day.previous();
        }
    }

    @Test
    public void border() {
        assertEquals(Day.valueOf(19521230), Day.valueOf(19521231).previous());
    }

    @Test
    public void constructor() {
        final Day day = Day.today();
        assertEquals(day, Day.newDay(day.year(), day.month(), day.day()));
    }

    @Test
    public void formatting() {
        assertEquals("January 1, 2011", Day.newDay(2011, 1, 1).toLongString());
    }

    @Test
    public void doubleValue() {
        assertEquals(Day.valueOf(20110101.0034), Day.valueOf(20110101));
    }
}
