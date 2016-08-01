package navalaction.model;

/**
 *
 */
public class ShipRepairItemTemplate<A> extends ResourceTemplate<A> {
    public ShipRepairItemTemplate(final int id, final String name, final ItemTemplateType type, final int basePrice, final double itemWeight, final A attachment) {
        super(id, name, type, basePrice, itemWeight, attachment);
    }
}
