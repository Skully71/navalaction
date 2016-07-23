package navalaction.model;

/**
 *
 */
public class ResourceTemplate extends ItemTemplate {
    public final int basePrice;

    public ResourceTemplate(final int id, final String name, final ItemTemplateType type, final int basePrice) {
        super(id, name, type, null);
        this.basePrice = basePrice;
    }
}
