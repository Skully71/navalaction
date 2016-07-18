package navalaction.crafting;

import navalaction.json.JsonWorldBuilder;
import navalaction.model.ItemTemplateType;
import navalaction.model.RecipeShipTemplate;
import navalaction.model.WoodType;
import navalaction.model.World;

import java.io.IOException;
import java.util.stream.Stream;

/**
 * Show what you need to craft a certain recipe.
 */
public class RecipeCrafting {
    public static void main(final String[] args) throws IOException {
        final World world = JsonWorldBuilder.create("res/20160715");
        final RecipeShipTemplate itemTemplate = world.itemTemplates.values().stream()
                .filter(t -> t.type == ItemTemplateType.RECIPE_SHIP && t.name.indexOf(args[0]) != -1)
                .map(RecipeShipTemplate.class::cast)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Can't find an item template for " + args[0]));
        System.out.println(itemTemplate.name);
        System.out.println("  Labor: " + itemTemplate.laborPrice);
        Stream.concat(itemTemplate.woodTypeDescs.get(WoodType.LIVE_OAK).requirements.stream(), itemTemplate.fullRequirements.stream())
                .sorted((r1, r2) -> world.itemTemplatesById.get(r1.template).name.compareTo(world.itemTemplatesById.get(r2.template).name))
                .forEach(r -> {
                    System.out.println("  " + world.itemTemplatesById.get(r.template).name + ": " + r.amount);
                });
    }
}
