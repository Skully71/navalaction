package navalaction.pricing;

import com.google.gson.Gson;
import navalaction.json.JsonWorldBuilder;
import navalaction.model.*;

import java.io.IOException;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

import static navalaction.pricing.Util.t;

/**
 *
 */
public class Report {
    private static final double SALES_TAX = 0.05;
    private static final double SHIP_RESALE_TAX = 0.10;

    private static double consumptionPrice(final double basePrice) {
        return (int) (basePrice * 1.5);
    }

    private static void export(final Map<Integer, Need> needs) {
        final Gson gson = new Gson();
        System.out.println(gson.toJson(needs));
    }

    public static void main(final String[] args) throws IOException {
        final Map<Integer, Need> needs = new HashMap<>();
        final World world = JsonWorldBuilder.create("res/20170125");
        System.out.println("+-----------------------+---------+-----------+-----------+-----------+-----------+-----------+");
        System.out.println("| Resource              | Weight  | Price     | Tax       | Total     | Consump   | C&C Cst   |");
        System.out.println("+-----------------------+---------+-----------+-----------+-----------+-----------+-----------+");
        world.itemTemplates.values().stream().filter(t -> t.type == ItemTemplateType.RESOURCE).sorted(Comparator.comparing(ItemTemplate::getName)).map(ResourceTemplate.class::cast).forEach(t -> {
            final double tax = salesTax(t.basePrice);
            final Need need = new Need(t.basePrice, t.basePrice + tax, 0, consumptionPrice(t.basePrice), priceIncSalesTax(consumptionPrice(t.basePrice)));
            needs.put(t.id, need);
            System.out.format("| %-21s | %7.2f | %9.2f | %9.2f | %9.2f | %9.2f | %9.2f |%n", t(t.name, 21), t.itemWeight, (double) t.basePrice, tax, t.basePrice + tax, need.consumptionPrice, need.consumptionIncCCostPrice);
        });
        System.out.println("+-----------------------+---------+-----------+-----------+-----------+-----------+-----------+");
        System.out.println();
        System.out.println("+-----------------------+---------+---------+---------+-----------+----------+----------+----------+");
        System.out.println("| Recipe                | Weight  | Craft   | Labor   | Resources | Res+Tax  | Consump  | C&C Cost | ");
        System.out.println("+-----------------------+---------+---------+---------+-----------+----------+----------+----------+");
        world.itemTemplates.values().stream().filter(t -> t.type == ItemTemplateType.RECIPE).sorted(Comparator.comparing(ItemTemplate::getName)).map(RecipeResourceTemplate.class::cast).forEach(t -> {
//        world.itemTemplates.values().stream().filter(t -> t.type == ItemTemplateType.RECIPE && t.name.startsWith("Knees")).sorted(Comparator.comparing(ItemTemplate::getName)).map(RecipeResourceTemplate.class::cast).forEach(t -> {
            //System.out.println(t.results.values().iterator().next());
            // TODO: pick the right result :)
            final Requirement result = (Requirement) t.results.values().iterator().next();
            final ResourceTemplate<?> item = world.itemTemplate(result.template, ResourceTemplate.class);
            final Need need = need(world, t, 1 / (double) result.amount, SALES_TAX);
            needs.put(result.template, need);
            System.out.format("| %-21s | %7.2f | %7.2f | %7.2f |  %8.2f | %8.2f | %8.2f | %8.2f |%n", t(t.name.substring(0, t.name.length() - " Blueprint".length()), 21), item.itemWeight, (double) t.goldRequirements, need.laborPrice, need.basePrice, need.priceIncTax, need.consumptionPrice, need.consumptionIncCCostPrice);
        });
        System.out.println("+-----------------------+---------+---------+---------+-----------+----------+----------+----------+");
        System.out.println();
        System.out.println("+-----------------------+---------+---------+--------+------------+-----------+-----------+-----------+");
        System.out.println("| Recipe                | Craft   | Labor   | Frames | Resources  | Res+Tax   | Consump   | C&C Cost  |");
        System.out.println("+-----------------------+---------+---------+--------+------------+-----------+-----------+-----------+");
        world.itemTemplates.values().stream().filter(t -> t.type == ItemTemplateType.RECIPE_SHIP).sorted(Comparator.comparing(ItemTemplate::getName)).map(RecipeShipTemplate.class::cast).forEach(t -> {
            // check that all wood requirements are equal in amount
            final Collection<WoodTypeDesc> woodTypeDescs = (Collection<WoodTypeDesc>) t.woodTypeDescs.values();
            final int woodAmount = woodTypeDescs.iterator().next().requirements.iterator().next().amount;
            assert woodTypeDescs.stream().allMatch(w -> w.requirements.iterator().next().amount == woodAmount);

            final Collection<Requirement> c = t.results.values();
            final Requirement result = c.stream().filter(r -> world.itemTemplatesById.get(r.template).type == ItemTemplateType.SHIP).findFirst().get();
            final Need need = need(world, t, 1 / (double) result.amount, SHIP_RESALE_TAX);

            needs.put(result.template, need);
            System.out.format("| %-21s | %7.2f | %7.2f | %6d |  %9.2f | %9.2f | %9.2f | %9.2f |%n", t(t.name.substring(0, t.name.length() - " Blueprint".length()), 21), (double) t.goldRequirements, need.laborPrice, woodAmount, need.basePrice, need.priceIncTax, need.consumptionPrice, need.consumptionIncCCostPrice);
            //System.out.println(t.woodTypeDescs);
        });
        System.out.println("+-----------------------+---------+---------+--------+------------+-----------+-----------+-----------+");

        export(needs);
    }

    static Need need(final World world, final AbstractRecipeTemplate<?> recipe, final double num, final double taxRate) {
        // you do not pay sales tax when you craft :)
        final double price = recipe.goldRequirements * num;
        final Need need = new Need(price, price, recipe.laborPrice * num, price, price);
        need.add(need(world, recipe.fullRequirements, num));
        // because the sales tax is added at the end
//        System.out.println("1: " + need);
        need.add(new Need(0, tax(need.priceIncTax, taxRate), 0, 0, tax(need.consumptionIncCCostPrice, taxRate)));
//        System.out.println("2: " + need);
        return need;
    }

    private static Need need(final World world, final Collection<Requirement> requirements, final double num) {
        final Need need = new Need(0, 0, 0, 0, 0);
        requirements.stream().forEach(r -> {
            final ItemTemplate<?> item = world.itemTemplatesById.get(r.template);
            final Need requirementNeed = need(world, r, num);
            //System.out.println((num * r.amount) + "x " + item.name + ": " + requirementNeed);
            need.add(requirementNeed);
        });
//        System.out.println("3: " + need);
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
            return need(world, subRecipe, numCrafs, SALES_TAX);
        }
        else if (item instanceof ResourceTemplate) {
            return need(world, (ResourceTemplate<?>) item, requirement.amount * num);
        } else
            throw new RuntimeException("NYI " + item);
    }

    private static Need need(final World world, final ResourceTemplate<?> resource, final double num) {
        // simply buy of port
        return new Need(resource.basePrice * num, (resource.basePrice + salesTax(resource.basePrice)) * num, 0, consumptionPrice(resource.basePrice) * num, priceIncSalesTax(consumptionPrice(resource.basePrice)) * num);
    }

    private static double priceIncSalesTax(final double price) {
        return price + salesTax(price);
    }

    private static double salesTax(final double basePrice) {
        return tax(basePrice, SALES_TAX);
    }

    private static double tax(final double basePrice, final double taxRate) {
        return basePrice * taxRate;
    }
}
