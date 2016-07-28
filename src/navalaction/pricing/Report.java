package navalaction.pricing;

import com.google.gson.Gson;
import navalaction.json.JsonWorldBuilder;
import navalaction.model.*;

import java.io.IOException;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

/**
 *
 */
public class Report {
    private static final double SALES_TAX = 0.05;

    private static void export(final Map<Integer, Need> needs) {
        final Gson gson = new Gson();
        System.out.println(gson.toJson(needs));
    }

    public static void main(final String[] args) throws IOException {
        final Map<Integer, Need> needs = new HashMap<>();
        final World world = JsonWorldBuilder.create("res/20160723");
        System.out.println("+-----------------------+---------+---------+---------+");
        System.out.println("| Resource              | Price   | Tax     | Total   |");
        System.out.println("+-----------------------+---------+---------+---------+");
        world.itemTemplates.values().stream().filter(t -> t.type == ItemTemplateType.RESOURCE).sorted(Comparator.comparing(ItemTemplate::getName)).map(ResourceTemplate.class::cast).forEach(t -> {
            final double tax = salesTax(t.basePrice);
            final Need need = new Need(t.basePrice, t.basePrice + tax, 0);
            needs.put(t.id, need);
            System.out.format("| %-21s | %7.2f | %7.2f | %7.2f |%n", t(t.name, 21), (double) t.basePrice, tax, (double) (t.basePrice + tax));
        });
        System.out.println("+-----------------------+---------+---------+---------+");
        System.out.println();
        System.out.println("+-----------------------+---------+---------+-----------+----------+");
        System.out.println("| Recipe                | Craft   | Labor   | Resources | Res+Tax  |");
        System.out.println("+-----------------------+---------+---------+-----------+----------+");
        world.itemTemplates.values().stream().filter(t -> t.type == ItemTemplateType.RECIPE).sorted(Comparator.comparing(ItemTemplate::getName)).map(RecipeResourceTemplate.class::cast).forEach(t -> {
//        world.itemTemplates.values().stream().filter(t -> t.type == ItemTemplateType.RECIPE && t.name.startsWith("Knees")).sorted(Comparator.comparing(ItemTemplate::getName)).map(RecipeResourceTemplate.class::cast).forEach(t -> {
            //System.out.println(t.results.values().iterator().next());
            final Requirement result = (Requirement) t.results.values().iterator().next();
            final Need need = need(world, t, 1 / (double) result.amount);
            needs.put(result.template, need);
            System.out.format("| %-21s | %7.2f | %7.2f |  %8.2f | %8.2f |%n", t(t.name.substring(0, t.name.length() - " Blueprint".length()), 21), (double) t.goldRequirements, need.laborPrice, need.basePrice, need.priceIncTax);
        });
        System.out.println("+-----------------------+---------+---------+-----------+----------+");

        export(needs);
    }

    private static Need need(final World world, final AbstractRecipeTemplate<?> recipe, final double num) {
        // you do not pay sales tax when you craft :)
        final double price = recipe.goldRequirements * num;
        final Need need = new Need(price, price, recipe.laborPrice * num);
        need.add(need(world, recipe.fullRequirements, num));
        // because the sales tax is added at the end
        need.add(new Need(0, salesTax(need.priceIncTax), 0));
        return need;
    }

    private static Need need(final World world, final Collection<Requirement> requirements, final double num) {
        final Need need = new Need(0, 0, 0);
        requirements.stream().forEach(r -> {
            final ItemTemplate<?> item = world.itemTemplatesById.get(r.template);
            final Need requirementNeed = need(world, r, num);
            //System.out.println((num * r.amount) + "x " + item.name + ": " + requirementNeed);
            need.add(requirementNeed);
        });
        return need;
    }

    private static Need need(final World world, final Requirement requirement, final double num) {
        final ItemTemplate<?> item = world.itemTemplatesById.get(requirement.template);
        final AbstractRecipeTemplate<?> subRecipe = world.byResult(requirement.template);
        // do not craft resources
        if (subRecipe != null && subRecipe.fullRequirements.size() == 0) {
            return need(world, (ResourceTemplate<?>) item, requirement.amount * num);
        } else if (subRecipe != null) {
            final double numCrafs = requirement.amount * num / (double) subRecipe.results.get(requirement.template).amount;
            return need(world, subRecipe, numCrafs);
        }
        else if (item instanceof ResourceTemplate) {
            return need(world, (ResourceTemplate<?>) item, requirement.amount * num);
        } else
            throw new RuntimeException("NYI " + item);
    }

    private static Need need(final World world, final ResourceTemplate<?> resource, final double num) {
        // simply buy of port
        return new Need(resource.basePrice * num, (resource.basePrice + salesTax(resource.basePrice)) * num, 0);
    }

    private static double salesTax(final double basePrice) {
        return basePrice * SALES_TAX;
    }

    private static String t(final String s, final int maxLength) {
        if (s.length() > maxLength) {
            return s.substring(0, maxLength - 2) + "..";
        }
        return s;
    }
}
