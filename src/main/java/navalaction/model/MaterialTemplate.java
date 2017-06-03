package navalaction.model;

/**
 *
 */
// TODO: really extends ResourceTemplate?
public class MaterialTemplate<A> extends ResourceTemplate<A> {
    public MaterialTemplate(final int id, final String name, final ItemTemplateType type, final int basePrice, final double itemWeight, final A attachment) {
        super(id, name, type, basePrice, itemWeight, attachment);
    }
}
