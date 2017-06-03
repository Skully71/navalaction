package navalaction.model;

/**
 *
 */
public class Requirement {
    public final int template;
    public final int amount;
    public final double chance;

    public Requirement(final int template, final int amount, final double chance) {
        this.template = template;
        this.amount = amount;
        this.chance = chance;
    }

    @Override
    public String toString() {
        return "Requirement{" +
                "template=" + template +
                ", amount=" + amount +
                ", chance=" + chance +
                '}';
    }
}
