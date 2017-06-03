package navalaction.crafting;

import navalaction.model.Requirement;

import java.util.HashMap;
import java.util.Map;

/**
 *
 */
public class CraftRequirements {
    private int goldRequirements = 0;
    private int laborPrice = 0;
    private Map<Integer, Requirement> resourceRequirements = new HashMap<>();

    public CraftRequirements() {
    }

    void add(final CraftRequirements other) {
        this.goldRequirements += other.goldRequirements;
        this.laborPrice += other.laborPrice;
    }

    void addGoldRequirements(final int goldRequirements) {
        this.goldRequirements += goldRequirements;
    }

    void addLaborPrice(final int laborPrice) {
        this.laborPrice += laborPrice;
    }

    public int getGoldRequirements() {
        return goldRequirements;
    }

    public int getLaborPrice() {
        return laborPrice;
    }
}
