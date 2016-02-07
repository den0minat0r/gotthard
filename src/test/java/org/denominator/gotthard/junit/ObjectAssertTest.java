package org.denominator.gotthard.junit;

import org.junit.Test;

import java.io.IOException;
import java.io.NotSerializableException;

@SuppressWarnings("RedundantStringConstructorCall")
public class ObjectAssertTest {
    @Test
    public void utilityConstructor() {
        LangAssert.assertUtilityClass(ObjectAssert.class);
    }

    @Test
    public void equalityAssert() {
        ObjectAssert.assertEquality(new String("test"), new String("test"));
    }

    @Test(expected = AssertionError.class)
    public void equalityAssertFail() {
        ObjectAssert.assertEquality(new String("test"), new String("test1"));
    }

    @Test
    public void serializationTest() throws ClassNotFoundException, IOException {
        ObjectAssert.assertSerialization(new String("test"), new String("test"));
    }

    @Test(expected = NotSerializableException.class)
    public void serializationTestFail() throws ClassNotFoundException, IOException {
        ObjectAssert.assertSerialization(new Object());
    }
}
