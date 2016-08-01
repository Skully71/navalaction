package navalaction.model;

/**
 *
 */
public class ResourceTemplate<A> extends ItemTemplate<A> {
    public final int basePrice;
    public final double itemWeight;

    public ResourceTemplate(final int id, final String name, final ItemTemplateType type, final int basePrice, final double itemWeight, final A attachment) {
        super(id, name, type, null, attachment);
        this.basePrice = basePrice;
        this.itemWeight = itemWeight;
    }
}
