package navalaction.model;

import java.util.Collection;
import java.util.Collections;

/**
 *
 */
public class ItemTemplate<A> {
    public final int id;
    public final String name;
    public final ItemTemplateType type;
    public final Collection<Item> items;
    public final A attachment;

    public ItemTemplate(final int id, final String name, final ItemTemplateType type, final Collection<Item> items, final A attachment) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.items = items != null ? Collections.unmodifiableCollection(items) : null;
        this.attachment = attachment;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", type='" + type + '\'' +
                '}';
    }
}
