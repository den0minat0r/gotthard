package org.denominator.gotthard.json;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public final class OrderedJsonObjectFactory implements JsonObjectFactory {
    @Override
    public Map<String, Object> mapInstance() {
        return new LinkedHashMap<>();
    }

    @Override
    public List<Object> listInstance() {
        return new ArrayList<>();
    }
}
