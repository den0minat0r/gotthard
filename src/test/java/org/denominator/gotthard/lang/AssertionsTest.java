package org.denominator.gotthard.lang;

import org.denominator.gotthard.collection.Creator;
import org.denominator.gotthard.junit.LangAssert;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

public class AssertionsTest {
    @Test
    public void utilityConstructor() {
        LangAssert.assertUtilityClass(Assertions.class);
    }

    @Test
    public void nonEmpty() {
        Assertions.notEmpty(Creator.list(""), "test");
    }

    @Test
    public void nonEmptyMap() {
        Assertions.notEmpty(Creator.map("", ""), "test");
    }

    @Test(expected = IllegalArgumentException.class)
    public void nonEmptyFailed() {
        Assertions.notEmpty(Creator.list(), "test");
    }

    @Test(expected = IllegalArgumentException.class)
    public void nonEmptyMapFailed() {
        Assertions.notEmpty(Creator.map(), "test");
    }

    @Test
    public void containsAll() {
        Assertions.containsOnly("test", new char[]{'t', 'e', 's'});
    }

    @Test(expected = IllegalArgumentException.class)
    public void containsNotAll() {
        Assertions.containsOnly("test", new char[]{'t', 'e'});
    }

    @Test(expected = IllegalArgumentException.class)
    public void validateToTrue() {
        Assertions.isTrue(false, "");
    }

    @Test
    public void validateToFalse() {
        Assertions.isTrue(true, "");
    }

    @Test(expected = IllegalArgumentException.class)
    public void isNull() {
        //noinspection NullableProblems
        Assertions.notNull(null, "test");
    }

    @Test
    public void notNull() {
        Assertions.notNull("", "");
        Assertions.notNull(Creator.map("", new Object()));
    }

    @Test
    public void isFile() throws IOException {
        File file = File.createTempFile("temp", "temp");
        try {
            Assertions.isFile(file);
        } finally {
            file.deleteOnExit();
        }
    }

    @Test(expected = IllegalArgumentException.class)
    public void unknownFile() {
        Assertions.isFile(new File("unknown"));
    }

    @Test(expected = IllegalArgumentException.class)
    public void directoryFile() throws IOException {
        File tempDir = Files.createTempDirectory("dir").toFile();
        try {
            Assertions.isFile(tempDir);
        } finally {
            tempDir.deleteOnExit();
        }
    }

    @Test
    public void isDirectory() throws IOException {
        File file = Files.createTempDirectory("dir").toFile();
        try {
            Assertions.isDirectory(file);
        } finally {
            file.deleteOnExit();
        }
    }

    @Test(expected = IllegalArgumentException.class)
    public void unknownDirectory() {
        Assertions.isDirectory(new File("unknown"));
    }

    @Test(expected = IllegalArgumentException.class)
    public void fileDirectory() throws IOException {
        File tempDir = File.createTempFile("temp", "temp");
        try {
            Assertions.isDirectory(tempDir);
        } finally {
            tempDir.deleteOnExit();
        }
    }

    @Test(expected = IllegalArgumentException.class)
    public void fail() {
        Assertions.fail();
    }

    @Test
    public void nonEmptyCollections() {
        //noinspection NullableProblems
        Assertions.notEmpty(Arrays.asList(1L, 2L), "");
    }

    @Test(expected = IllegalArgumentException.class)
    public void nullString() {
        //noinspection NullableProblems
        Assertions.notEmpty((String) null, "");
    }

    @Test(expected = IllegalArgumentException.class)
    public void emptyCollections() {
        //noinspection NullableProblems
        Assertions.notEmpty(new ArrayList<>(), "");
    }

    @Test(expected = IllegalArgumentException.class)
    public void nullCollections() {
        //noinspection NullableProblems
        Assertions.notEmpty((Collection) null, "");
    }

    @Test
    public void string() {
        Assertions.notEmpty("notEmpty", "");
    }

    @Test(expected = IllegalArgumentException.class)
    public void emptyString() {
        Assertions.notEmpty("", "");
    }

    @Test(expected = IllegalArgumentException.class)
    public void isPositive() {
        Assertions.isPositive(1, "number");
        Assertions.isPositive(0, "number");
    }

    @Test(expected = IllegalArgumentException.class)
    public void nonZero() {
        Assertions.nonZero(1, "number");
        Assertions.nonZero(0, "number");
    }

    @Test(expected = IllegalArgumentException.class)
    public void nan() {
        Assertions.notNan(Double.NaN, "");
    }

    @Test
    public void notNan() {
        Assertions.notNan(1.0, "");
    }

    @Test(expected = IllegalArgumentException.class)
    public void assertion() {
        Assertions.isTrue(() -> false, "message");
    }
}
