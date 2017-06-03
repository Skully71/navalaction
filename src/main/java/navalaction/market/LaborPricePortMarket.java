package navalaction.market;

import navalaction.Port;
import navalaction.json.JsonWorldBuilder;
import navalaction.model.*;

import java.io.IOException;
import java.util.Collection;
import java.util.Comparator;
import java.util.Map;

/**
 *
 */
public class LaborPricePortMarket {
    public static void main(final String[] args) throws IOException {
        final World world = JsonWorldBuilder.create("res/20160821");
        final Map<Integer, Shop> shops = world.shops;
        final Port port = world.portsByName.get("Mortimer Town");
        final Shop shop = world.shops.get(port.id);
        System.out.println("+-----------------------+--------+------------+------------+------------+--------+----------+");
        System.out.println("| Recipe                | Amount | Revenue    | Purchase   | Profit     | Labor  | P/H      |");
        System.out.println("+-----------------------+--------+------------+------------+------------+--------+----------+");
        world.itemTemplates.values().stream().filter(t -> t.type == ItemTemplateType.RECIPE).sorted(Comparator.comparing(ItemTemplate::getName)).map(RecipeResourceTemplate.class::cast).forEach(recipe -> {
            // TODO: pick the right result :)
            final Requirement result = (Requirement) recipe.results.values().iterator().next();
            final ResourceTemplate<?> item = world.itemTemplate(result.template, ResourceTemplate.class);
            System.out.format("| %-21s | ", item.name);
            final RegularItem regularItem = shop.regularItems.get(item.id);
            if (regularItem != null && regularItem.sellPrice > 0) {
//                System.out.println(shop.regularItems.get(item.id));
                final int numCrafts = Math.min(regularItem.sellContractQuantity / result.amount, ((Collection<Requirement>) recipe.fullRequirements).stream().mapToInt(r -> {
                            final RegularItem requirementContracts = shop.regularItems.get(r.template);
                            if (requirementContracts != null) {
                                return requirementContracts.buyContractQuantity / r.amount;
                            } else {
                                return 0;
                            }
                        }).min().getAsInt()
                );
//                System.out.println(numCrafts);
                if (numCrafts > 0) {
                    final int n = numCrafts;
                    final double cost = ((Collection<Requirement>) recipe.fullRequirements).stream().mapToDouble(r -> {
//                        System.out.println(" " + r);
//                        System.out.println(" " + shop.regularItems.get(r.template));
                        final RegularItem requirementContracts = shop.regularItems.get(r.template);
                        return requirementContracts.buyPrice * r.amount * n;
                    }).sum();
                    final double revenue = numCrafts * result.amount * regularItem.sellPrice;
                    final double profit = revenue - cost;
                    final double laborPrice = profit / (double) (recipe.laborPrice * numCrafts);
                    System.out.format("%6d | %10.2f | %10.2f | %10.2f | %6d |", (numCrafts * result.amount), revenue, cost, profit, recipe.laborPrice * numCrafts, laborPrice);
                    if (laborPrice > 0) {
                        System.out.format(" %8.2f |", laborPrice);
                    } else {
                        System.out.print("      n/a |");
                    }
                } else {
                    System.out.print("   n/a |        n/a |        n/a |        n/a |    n/a |      n/a |");
                }
            } else {
                System.out.print("   n/a |        n/a |        n/a |        n/a |    n/a |      n/a |");
            }
            System.out.println();
        });
        System.out.println("+-----------------------+--------+------------+------------+------------+--------+----------+");
    }
}
