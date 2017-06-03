package navalaction.model;

/**
 *
 */
public class Item {
    public final int template;
    public final double chance;

    public Item(final int template, final double chance) {
        this.template = template;
        this.chance = chance;
    }

    @Override
    public String toString() {
        return "Item{" +
                "template=" + template +
                ", chance=" + chance +
                '}';
    }
}
