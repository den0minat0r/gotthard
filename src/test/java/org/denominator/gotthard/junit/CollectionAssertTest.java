package org.denominator.gotthard.junit;

import org.junit.Test;

import static java.util.Arrays.asList;

public class CollectionAssertTest {
    @Test
    public void utilityConstructor() throws NoSuchMethodException, InstantiationException, IllegalAccessException {
        LangAssert.assertUtilityClass(CollectionAssert.class);
    }

    @Test(expected = AssertionError.class)
    public void noEpsilonWrongSize() {
        CollectionAssert.assertEquals(asList(1.0, 2.0), asList(1.0));
    }

    @Test(expected = AssertionError.class)
    public void noEpsilonWrongSize2() {
        CollectionAssert.assertEquals(asList(1.0), asList(1.0, 2.0));
    }

    @Test(expected = AssertionError.class)
    public void epsilonWrongSize() {
        CollectionAssert.assertEquals(asList(1.0, 2.0), asList(1.0), 0.0);
    }

    @Test(expected = AssertionError.class)
    public void epsilonWrongSize2() {
        CollectionAssert.assertEquals(asList(1.0), asList(1.0, 2.0), 0.0);
    }

    @Test
    public void noEpsilon() {
        CollectionAssert.assertEquals(asList(1.0, 2.0), asList(1.0, 2.0));
    }

    @Test
    public void epsilon() {
        CollectionAssert.assertEquals(asList(1.09, 2.0), asList(1.0, 2.05), 0.1);
    }
}
