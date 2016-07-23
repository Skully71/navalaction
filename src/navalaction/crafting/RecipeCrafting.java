package navalaction.crafting;

import navalaction.json.JsonWorldBuilder;
import navalaction.model.*;

import java.io.IOException;
import java.util.stream.Stream;

/**
 * Show what you need to craft a certain recipe.
 */
public class RecipeCrafting {
    static CraftRequirements craftRequirements(final String prefix, final World world, final int num, final AbstractRecipeTemplate recipe, final boolean buy) {
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
                    process(prefix, world, num, r, craftRequirements, buy);
                });
        return craftRequirements;
    }

    static CraftRequirements craftShipRequirements(final World world, final RecipeShipTemplate recipe, final boolean buy) {
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
                    process("", world, 1, r, craftRequirements, buy);
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
        final CraftRequirements craftRequirements = craftShipRequirements(world, itemTemplate, false);
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

        System.out.println();
        {
            final AbstractRecipeTemplate recipe = world.itemTemplates.values().stream()
                    .filter(t -> t.type == ItemTemplateType.RECIPE && t.name.indexOf("Medkit Large") != -1)
                    .map(AbstractRecipeTemplate.class::cast)
                    .findFirst()
                    .orElseThrow(() -> new IllegalArgumentException("Can't find an item template for " + args[0]));
            System.out.println(recipe.name);
            print(world, recipe, false);
            System.out.println();
            System.out.println(recipe.name);
            print(world, recipe, true);
        }
    }

    static void print(final World world, final AbstractRecipeTemplate recipe, final boolean buy) {
        final CraftRequirements craftRequirements = craftRequirements("", world, 1, recipe, buy);
        System.out.println("===");
        System.out.println("Total Gold: " + craftRequirements.getGoldRequirements());
        System.out.println("Total Labor: " + craftRequirements.getLaborPrice());
    }

    static void process(final String prefix, final World world, final int num, final Requirement r, final CraftRequirements craftRequirements, final boolean buy) {
        final ItemTemplate item = world.itemTemplatesById.get(r.template);
        System.out.println(prefix + "\\ " + item.name + ": " + (r.amount * num));
        // TODO: lol, temporary hack
        /*
        if (r.template == 620) {
            // Fish Meat
            craftRequirements.addGoldRequirements(99 * num);
            return;
        }
        */
        if (buy && item instanceof ResourceTemplate) {
            final int basePrice = ((ResourceTemplate) item).basePrice;
            System.out.println(prefix + "  \\ Gold: " + (basePrice * num * r.amount));
            craftRequirements.addGoldRequirements(basePrice * num * r.amount);
            return;
        }
        final AbstractRecipeTemplate subRecipe = world.byResult(r.template);
        if (subRecipe == null) {
            final int basePrice = ((ResourceTemplate) item).basePrice;
            System.out.println(prefix + "  \\ Gold: " + (basePrice * num * r.amount));
            craftRequirements.addGoldRequirements(basePrice * num * r.amount);
            return;
        }
        final int numCrafts = (int) Math.ceil((r.amount * num) / (double) subRecipe.results.get(r.template).amount);
        craftRequirements.add(craftRequirements(prefix + "| ", world, numCrafts, world.byResult(r.template), buy));
    }
}
