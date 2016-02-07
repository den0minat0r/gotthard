package org.denominator.gotthard.junit;

import java.util.Iterator;

public final class CollectionAssert {
    private CollectionAssert() {
        throw new AssertionError();
    }

    public static void assertEquals(Iterable<Double> expected, Iterable<Double> actuals) {
        Iterator<Double> e = expected.iterator();
        Iterator<Double> a = actuals.iterator();

        while (e.hasNext() && a.hasNext()) {
            ArrayAssert.assertDoubles(e.next(), a.next());
        }

        if (e.hasNext() || a.hasNext()) {
            throw new AssertionError("Collections of different size.");
        }
    }

    public static void assertEquals(Iterable<Double> expected, Iterable<Double> actuals, double epsilon) {
        Iterator<Double> e = expected.iterator();
        Iterator<Double> a = actuals.iterator();

        while (e.hasNext() && a.hasNext()) {
            Assert.assertEquals(e.next(), a.next(), epsilon);
        }

        if (e.hasNext() || a.hasNext()) {
            throw new AssertionError("Collections of different size.");
        }
    }
}
