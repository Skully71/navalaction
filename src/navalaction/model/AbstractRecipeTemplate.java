package navalaction.model;

import java.util.Collection;
import java.util.Map;

/**
 *
 */
public abstract class AbstractRecipeTemplate<A> extends ItemTemplate<A> {
    public final int givesXP;
    public final int laborPrice;
    public final Collection<Requirement> fullRequirements;
    public final int goldRequirements;
    public final Map<Integer, Requirement> results;

    public AbstractRecipeTemplate(final int id, final String name, final ItemTemplateType type, final int givesXP, final int laborPrice, final Collection<Requirement> fullRequirements, final int goldRequirements, final Map<Integer, Requirement> results, final A attachment) {
        super(id, name, type, null, attachment);
        this.givesXP = givesXP;
        this.laborPrice = laborPrice;
        this.fullRequirements = fullRequirements;
        this.goldRequirements = goldRequirements;
        this.results = results;
    }
}
