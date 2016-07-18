package navalaction.crafting;

import navalaction.model.AbstractRecipeTemplate;

/**
 *
 */
public class Craft {
    public final int amount;
    public final AbstractRecipeTemplate recipe;

    public Craft(final int amount, final AbstractRecipeTemplate recipe) {
        this.amount = amount;
        this.recipe = recipe;
    }
}
