package org.denominator.gotthard.json;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public final class SortedJsonObjectFactory implements JsonObjectFactory {
    @Override
    public Map<String, Object> mapInstance() {
        return new TreeMap<>();
    }

    @Override
    public List<Object> listInstance() {
        return new ArrayList<>();
    }
}
