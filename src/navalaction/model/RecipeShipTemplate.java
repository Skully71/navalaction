package navalaction.model;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;

/**
 *
 */
public class RecipeShipTemplate extends AbstractRecipeTemplate {
    public final Map<WoodType, WoodTypeDesc> woodTypeDescs;

    public RecipeShipTemplate(final int id, final String name, final ItemTemplateType type, final Map<WoodType, WoodTypeDesc> woodTypeDescs, final int laborPrice, final Collection<Requirement> fullRequirements, final int goldRequirements, final Map<Integer, Requirement> results) {
        super(id, name, type, laborPrice, fullRequirements, goldRequirements, results);
        this.woodTypeDescs = Collections.unmodifiableMap(woodTypeDescs);
    }
}
