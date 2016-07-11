package navalaction.model;

import java.util.Collections;
import java.util.Map;

/**
 *
 */
public class Shop {
    public final int id;
    public final Map<Integer, RegularItem> regularItems;
    public final Map<Integer, Resource> resourcesProduced;
    public final Map<Integer, Resource> resourcesConsumed;

    public Shop(final int id, final Map<Integer, RegularItem> regularItems, final Map<Integer, Resource> resourcesProduced, final Map<Integer, Resource> resourcesConsumed) {
        this.id = id;
        this.resourcesProduced = Collections.unmodifiableMap(resourcesProduced);
        this.resourcesConsumed = Collections.unmodifiableMap(resourcesConsumed);
        this.regularItems = Collections.unmodifiableMap(regularItems);
    }
}
