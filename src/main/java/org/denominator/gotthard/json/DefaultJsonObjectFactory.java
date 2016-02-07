package org.denominator.gotthard.json;

import java.util.*;


final class DefaultJsonObjectFactory implements JsonObjectFactory {
    @Override
    public List<Object> listInstance() {
        return new ArrayList<>();
    }

    @Override
    public Map<String, Object> mapInstance() {
        return new HashMap<>();
    }
}

final class OrderedJsonObjectFactory implements JsonObjectFactory {
    @Override
    public Map<String, Object> mapInstance() {
        return new LinkedHashMap<>();
    }

    @Override
    public List<Object> listInstance() {
        return new ArrayList<>();
    }
}

final class SortedJsonObjectFactory implements JsonObjectFactory {
    @Override
    public Map<String, Object> mapInstance() {
        return new TreeMap<>();
    }

    @Override
    public List<Object> listInstance() {
        return new ArrayList<>();
    }
}