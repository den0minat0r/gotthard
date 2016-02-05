package org.denominator.gotthard.json;

import java.util.List;
import java.util.Map;

public interface JsonObjectFactory {
    Map<String, Object> mapInstance();

    List<Object> listInstance();
}
