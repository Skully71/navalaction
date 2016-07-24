package navalaction.model;

/**
 *
 */
public class ResourceTemplate<A> extends ItemTemplate<A> {
    public final int basePrice;

    public ResourceTemplate(final int id, final String name, final ItemTemplateType type, final int basePrice, final A attachment) {
        super(id, name, type, null, attachment);
        this.basePrice = basePrice;
    }
}
