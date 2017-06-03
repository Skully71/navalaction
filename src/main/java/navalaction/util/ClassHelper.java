package navalaction.util;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 *
 */
public class ClassHelper {
    private static final Map<Class<?>, Class<?>> privatePrimitiveMap = new HashMap<>();
    public static final Map<Class<?>, Class<?>> primitiveMap = Collections.unmodifiableMap(privatePrimitiveMap);

    static {
        privatePrimitiveMap.put(double.class, Double.class);
        privatePrimitiveMap.put(int.class, Integer.class);
    }
}
