package navalaction.crafting;

import navalaction.json.JsonWorldBuilder;
import navalaction.model.*;

import java.io.IOException;
import java.util.stream.Stream;

/**
 * Show what you need to craft a certain recipe.
 */
public class RecipeCrafting {
    static CraftRequirements craftRequirements(final String prefix, final World world, final int num, final AbstractRecipeTemplate recipe) {
        final CraftRequirements craftRequirements = new CraftRequirements();
        //System.out.println(recipe.name);

        int goldRequirements = recipe.goldRequirements * num;
        if (goldRequirements != 0) System.out.println(prefix + "\\ Gold: " + goldRequirements);
        craftRequirements.addGoldRequirements(goldRequirements);

        int laborPrice = recipe.laborPrice * num;
        System.out.println(prefix + "\\ Labor: " + laborPrice);
        craftRequirements.addLaborPrice(laborPrice);

        recipe.fullRequirements.stream()
                .sorted((r1, r2) -> world.itemTemplatesById.get(r1.template).name.compareTo(world.itemTemplatesById.get(r2.template).name))
                .forEach(r -> {
                    System.out.println(prefix + "\\ " + world.itemTemplatesById.get(r.template).name + ": " + (r.amount * num));
                    final AbstractRecipeTemplate subRecipe = world.byResult(r.template);
                    final int numCrafts = (int) Math.ceil((r.amount * num) / (double) subRecipe.results.get(r.template).amount);
                    craftRequirements.add(craftRequirements(prefix + "| ", world, numCrafts, world.byResult(r.template)));
                });
        return craftRequirements;
    }

    static CraftRequirements craftShipRequirements(final World world, final RecipeShipTemplate recipe) {
        final CraftRequirements craftRequirements = new CraftRequirements();
        System.out.println(recipe.name);

        int goldRequirements = recipe.goldRequirements;
        if (goldRequirements != 0) System.out.println("\\ Gold: " + goldRequirements);
        craftRequirements.addGoldRequirements(goldRequirements);

        int laborPrice = recipe.laborPrice;
        System.out.println("\\ Labor: " + laborPrice);
        craftRequirements.addLaborPrice(laborPrice);

        Stream.concat(recipe.woodTypeDescs.get(WoodType.LIVE_OAK).requirements.stream(), recipe.fullRequirements.stream())
                .sorted((r1, r2) -> world.itemTemplatesById.get(r1.template).name.compareTo(world.itemTemplatesById.get(r2.template).name))
                .forEach(r -> {
                    System.out.println("\\ " + world.itemTemplatesById.get(r.template).name + ": " + r.amount);
                    final AbstractRecipeTemplate subRecipe = world.byResult(r.template);
                    final int numCrafts = (int) Math.ceil(r.amount / subRecipe.results.get(r.template).amount);
                    craftRequirements.add(craftRequirements("| ", world, numCrafts, world.byResult(r.template)));
                });
        return craftRequirements;
    }

    public static void main(final String[] args) throws IOException {
        final World world = JsonWorldBuilder.create("res/20160718");
        final RecipeShipTemplate itemTemplate = world.itemTemplates.values().stream()
                .filter(t -> t.type == ItemTemplateType.RECIPE_SHIP && t.name.indexOf(args[0]) != -1)
                .map(RecipeShipTemplate.class::cast)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Can't find an item template for " + args[0]));
        final CraftRequirements craftRequirements = craftShipRequirements(world, itemTemplate);
        /*
        Stream.concat(itemTemplate.woodTypeDescs.get(WoodType.LIVE_OAK).requirements.stream(), itemTemplate.fullRequirements.stream())
                .sorted((r1, r2) -> world.itemTemplatesById.get(r1.template).name.compareTo(world.itemTemplatesById.get(r2.template).name))
                .forEach(r -> {
                    System.out.println("  " + world.itemTemplatesById.get(r.template).name + ": " + r.amount);
                    //System.out.println("* " + world.itemTemplatesById.get(r.template));
                });
        */
        System.out.println("===");
        System.out.println("Total Gold: " + craftRequirements.getGoldRequirements());
        System.out.println("Total Labor: " + craftRequirements.getLaborPrice());
    }
}
