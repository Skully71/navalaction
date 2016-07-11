package navalaction.model;

import navalaction.Port;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

/**
 *
 */
public class World {
    public final Map<Integer, ItemTemplate> itemTemplates;
    public final Map<Integer, Port> ports;
    public final Map<Integer, Shop> shops;

    public final Map<String, Port> portsByName;

    public World(final Map<Integer, ItemTemplate> itemTemplates, final Map<Integer, Port> ports, final Map<Integer, Shop> shops) {
        this.itemTemplates = Collections.unmodifiableMap(itemTemplates);
        this.ports = Collections.unmodifiableMap(ports);
        this.shops = Collections.unmodifiableMap(shops);
        portsByName = Collections.unmodifiableMap(ports.values().stream().collect(Collectors.toMap(p -> p.name, p -> p)));
    }
}
