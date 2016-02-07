package org.denominator.gotthard.time;

import org.denominator.gotthard.lang.Assertions;

public final class TimeInterval {
    private final TimeUnit timeUnit;
    private final long     interval;

    private enum TimeUnit {
        Hours("h") {
            public long toMilliseconds(long interval) {
                return interval * 3600 * 1000;
            }
        },
        Minutes("min") {
            public long toMilliseconds(long interval) {
                return interval * 60 * 1000;
            }
        },
        Seconds("sec") {
            public long toMilliseconds(long interval) {
                return interval * 1000;
            }
        };

        private final String shortName;

        TimeUnit(String name) {
            this.shortName = name;
        }

        public abstract long toMilliseconds(long interval);
    }

    private TimeInterval(String string) {
        final int length = string.length();

        TimeUnit detected = null;
        for (TimeUnit tu : TimeUnit.values()) {
            if (string.endsWith(tu.shortName)) {
                detected = tu;
                break;
            }
        }

        Assertions.notNull(detected, "Unknown format.");
        this.timeUnit = detected;
        this.interval = Long.valueOf(string.substring(0, length - timeUnit.shortName.length()));
    }

    public long milliseconds() {
        return timeUnit.toMilliseconds(interval);
    }

    public static TimeInterval fromString(String string) {
        return new TimeInterval(string);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TimeInterval that = (TimeInterval) o;

        return interval == that.interval && timeUnit == that.timeUnit;
    }

    @Override
    public int hashCode() {
        int result = timeUnit.hashCode();
        result = 31 * result + (int) (interval ^ (interval >>> 32));
        return result;
    }

    @Override
    public String toString() {
        return interval + timeUnit.shortName;
    }
}
