package org.denominator.gotthard.json;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public final class DefaultJsonObjectFactory implements JsonObjectFactory {
    @Override
    public List<Object> listInstance() {
        return new ArrayList<>();
    }

    @Override
    public Map<String, Object> mapInstance() {
        return new HashMap<>();
    }
}