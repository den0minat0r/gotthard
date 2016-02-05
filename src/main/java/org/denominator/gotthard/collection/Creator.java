package org.denominator.gotthard.collection;

import java.util.*;

public final class Creator {
    private Creator() {
        throw new AssertionError();
    }

    public static <K, V> Map<K, V> map() {
        return new HashMap<>();
    }

    public static <K, V> Map<K, V> map(K k1, V v1) {
        Map<K, V> map = map();
        map.put(k1, v1);
        return map;
    }

    public static <K, V> Map<K, V> map(K k1, V v1, K k2, V v2) {
        Map<K, V> map = map(k1, v1);
        map.put(k2, v2);
        return map;
    }

    public static <K, V> Map<K, V> map(K k1, V v1, K k2, V v2, K k3, V v3) {
        Map<K, V> map = map(k1, v1, k2, v2);
        map.put(k3, v3);
        return map;
    }

    public static <K, V> Map<K, V> map(K k1, V v1, K k2, V v2, K k3, V v3, K k4, V v4) {
        Map<K, V> map = map(k1, v1, k2, v2, k3, v3);
        map.put(k4, v4);
        return map;
    }

    public static <K, V> Map<K, V> map(K k1, V v1, K k2, V v2, K k3, V v3, K k4, V v4, K k5, V v5) {
        Map<K, V> map = map(k1, v1, k2, v2, k3, v3, k4, v4);
        map.put(k5, v5);
        return map;
    }

    public static <K, V> Map<K, V> linkedMap() {
        return new LinkedHashMap<>();
    }

    public static <K, V> Map<K, V> linkedMap(K k1, V v1) {
        Map<K, V> map = linkedMap();
        map.put(k1, v1);
        return map;
    }

    public static <K, V> Map<K, V> linkedMap(K k1, V v1, K k2, V v2) {
        Map<K, V> map = linkedMap(k1, v1);
        map.put(k2, v2);
        return map;
    }

    public static <K, V> Map<K, V> linkedMap(K k1, V v1, K k2, V v2, K k3, V v3) {
        Map<K, V> map = linkedMap(k1, v1, k2, v2);
        map.put(k3, v3);
        return map;
    }

    public static <K, V> Map<K, V> linkedMap(K k1, V v1, K k2, V v2, K k3, V v3, K k4, V v4) {
        Map<K, V> map = linkedMap(k1, v1, k2, v2, k3, v3);
        map.put(k4, v4);
        return map;
    }

    public static <K, V> Map<K, V> linkedMap(K k1, V v1, K k2, V v2, K k3, V v3, K k4, V v4, K k5, V v5) {
        Map<K, V> map = linkedMap(k1, v1, k2, v2, k3, v3, k4, v4);
        map.put(k5, v5);
        return map;
    }

    public static <K, V> SortedMap<K, V> sortedMap() {
        return new TreeMap<>();
    }

    public static <K, V> SortedMap<K, V> sortedMap(K k1, V v1) {
        SortedMap<K, V> map = sortedMap();
        map.put(k1, v1);
        return map;
    }

    public static <K, V> SortedMap<K, V> sortedMap(K k1, V v1, K k2, V v2) {
        SortedMap<K, V> map = sortedMap(k1, v1);
        map.put(k2, v2);
        return map;
    }

    public static <K, V> SortedMap<K, V> sortedMap(K k1, V v1, K k2, V v2, K k3, V v3) {
        SortedMap<K, V> map = sortedMap(k1, v1, k2, v2);
        map.put(k3, v3);
        return map;
    }

    public static <K, V> SortedMap<K, V> sortedMap(K k1, V v1, K k2, V v2, K k3, V v3, K k4, V v4) {
        SortedMap<K, V> map = sortedMap(k1, v1, k2, v2, k3, v3);
        map.put(k4, v4);
        return map;
    }

    public static <K, V> SortedMap<K, V> sortedMap(K k1, V v1, K k2, V v2, K k3, V v3, K k4, V v4, K k5, V v5) {
        SortedMap<K, V> map = sortedMap(k1, v1, k2, v2, k3, v3, k4, v4);
        map.put(k5, v5);
        return map;
    }

    @SafeVarargs
    @SuppressWarnings("varargs")
    public static <T> Set<T> set(T... values) {
        return new HashSet<>(Arrays.asList(values));
    }

    @SafeVarargs
    @SuppressWarnings("varargs")
    public static <T> Set<T> linkedSet(T... values) {
        return new LinkedHashSet<>(Arrays.asList(values));
    }

    @SafeVarargs
    @SuppressWarnings("varargs")
    public static <T extends Comparable<T>> SortedSet<T> sortedSet(T... values) {
        return new TreeSet<>(Arrays.asList(values));
    }

    @SafeVarargs
    @SuppressWarnings("varargs")
    public static <T> List<T> list(T... values) {
        return Arrays.asList(values);
    }
}
