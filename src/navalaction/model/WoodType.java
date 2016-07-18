package navalaction.model;

import java.util.HashMap;
import java.util.Map;

/**
 *
 */
public enum WoodType {
    FIR(0),
    TEAK(1),
    OAK(2),
    LIVE_OAK(3),
    ;

    public static final Map<Integer, WoodType> WOOD_TYPES;

    public final int type;

    static {
        WOOD_TYPES = new HashMap<>();
        for (final WoodType t : WoodType.values()) {
            WOOD_TYPES.put(t.type, t);
        }
    }

    WoodType(final int type) {
        this.type = type;
    }
}
