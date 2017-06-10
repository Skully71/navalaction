package navalaction.model;

import java.util.Collection;
import java.util.Map;

/**
 *
 */
public class RecipeModuleTemplate<A> extends AbstractRecipeTemplate<A> {
    public RecipeModuleTemplate(final int id, final String name, final ItemTemplateType type, final int givesXP, final int laborPrice, final Collection<Requirement> fullRequirements, final int goldRequirements, final Map<Integer, Requirement> results, final A attachment) {
        super(id, name, type, givesXP, laborPrice, fullRequirements, goldRequirements, results, attachment);
    }
}
