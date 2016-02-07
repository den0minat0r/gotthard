package org.denominator.gotthard.junit;

import java.io.*;

public final class ObjectAssert {
    private ObjectAssert() {
        throw new AssertionError();
    }

    public static void assertEquality(Object o1, Object o2) {
        Assert.assertTrue(o1 != o2);
        Assert.assertEquals(o1, o2);
        Assert.assertEquals(o1.hashCode(), o2.hashCode());
        Assert.assertEquals(o1.toString(), o2.toString());
        Assert.assertTrue(!o1.equals(new Object()));
    }

    public static void assertSerialization(Object... objects) throws IOException, ClassNotFoundException {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        ObjectOutputStream out = new ObjectOutputStream(bytes);

        for (Object obj : objects) {
            out.writeObject(obj);
        }

        out.close();

        ObjectInputStream in = new ObjectInputStream(new ByteArrayInputStream(bytes.toByteArray()));
        for (Object obj : objects) {
            Object loaded = in.readObject();
            assertEquality(loaded, obj);
        }
        in.close();
    }
}
