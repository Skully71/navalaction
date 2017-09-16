package navalaction.model;

/**
 *
 */
public class CannonTemplate<A> extends ResourceTemplate<A> {
    public CannonTemplate(final int id, final String name, final ItemTemplateType type, final int basePrice, final double itemWeight, final A attachment) {
        super(id, name, type, basePrice, itemWeight, attachment);
    }
}
