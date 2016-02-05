package org.denominator.gotthard.json;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

final class MapBuilder {
    private final Map<String, Object> theMap = new HashMap<>();

    public MapBuilder() {
    }

    public MapBuilder put(String name, Object object) {
        theMap.put(name, object);
        return this;
    }

    /**
     * @return Immutable snapshot copy of the underlying map.
     */
    public Map<String, Object> build() {
        return Collections.unmodifiableMap(new HashMap<>(theMap));
    }
}
