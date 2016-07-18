package navalaction.model;

import java.util.Collection;
import java.util.Map;

/**
 *
 */
public class RecipeResourceTemplate extends AbstractRecipeTemplate {
    public RecipeResourceTemplate(final int id, final String name, final ItemTemplateType type, final int laborPrice, final Collection<Requirement> fullRequirements, final int goldRequirements, final Map<Integer, Requirement> results) {
        super(id, name, type, laborPrice, fullRequirements, goldRequirements, results);
    }
}
