package org.denominator.gotthard.json;

import org.denominator.junit.LangAssert;
import org.junit.Test;

public class StaticJsonSerializerTest {
    @Test
    public void utilityConstructor() throws NoSuchMethodException, IllegalAccessException, InstantiationException {
        LangAssert.assertUtilityClass(StaticJsonSerializer.class);
    }
}
