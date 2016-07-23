package navalaction.model;

import navalaction.Port;

import java.util.Collections;
import java.util.Map;
import java.util.stream.Collectors;

/**
 *
 */
public class World {
    public final Map<Integer, ItemTemplate> itemTemplates;
    public final Map<Integer, Port> ports;
    public final Map<Integer, Shop> shops;

    public final Map<Integer, ItemTemplate> itemTemplatesById;
    public final Map<String, Port> portsByName;

    public World(final Map<Integer, ItemTemplate> itemTemplates, final Map<Integer, Port> ports, final Map<Integer, Shop> shops) {
        this.itemTemplates = Collections.unmodifiableMap(itemTemplates);
        this.ports = Collections.unmodifiableMap(ports);
        this.shops = Collections.unmodifiableMap(shops);
        this.itemTemplatesById = Collections.unmodifiableMap(itemTemplates.values().stream().collect(Collectors.toMap(t -> t.id, t -> t)));
        this.portsByName = Collections.unmodifiableMap(ports.values().stream().collect(Collectors.toMap(p -> p.name, p -> p)));
    }

    public AbstractRecipeTemplate byResult(final int id) {
        return itemTemplates.values().stream()
                .filter(t -> t.type == ItemTemplateType.RECIPE || t.type == ItemTemplateType.RECIPE_RESOURCE)
                .map(AbstractRecipeTemplate.class::cast).filter(r -> r.results.containsKey(id)).findFirst().orElse(null);
    }

    public <T extends ItemTemplate>  T itemTemplate(final int id, Class<T> cls) {
        System.out.println(id);
        return cls.cast(itemTemplatesById.get(id));
    }
}
