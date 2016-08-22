package navalaction.model;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;

/**
 *
 */
public class RecipeShipTemplate<A> extends AbstractRecipeTemplate<A> {
    public final Map<WoodType, WoodTypeDesc> woodTypeDescs;

    public RecipeShipTemplate(final int id, final String name, final ItemTemplateType type, final Map<WoodType, WoodTypeDesc> woodTypeDescs, final int givesXP, final int laborPrice, final Collection<Requirement> fullRequirements, final int goldRequirements, final Map<Integer, Requirement> results, final A attachment) {
        super(id, name, type, givesXP, laborPrice, fullRequirements, goldRequirements, results, attachment);
        this.woodTypeDescs = Collections.unmodifiableMap(woodTypeDescs);
    }
}
