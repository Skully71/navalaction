package navalaction.model;

/**
 *
 */
public class ItemTemplate {
    public final int id;
    public final String name;
    public final ItemTemplateType type;

    public ItemTemplate(final int id, final String name, final ItemTemplateType type) {
        this.id = id;
        this.name = name;
        this.type = type;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "ItemTemplate{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", type='" + type + '\'' +
                '}';
    }
}
