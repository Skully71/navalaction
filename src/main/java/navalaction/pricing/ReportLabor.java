package navalaction.pricing;

import navalaction.Port;
import navalaction.json.JsonWorldBuilder;
import navalaction.model.*;

import java.io.IOException;
import java.util.Comparator;

import static navalaction.pricing.Report.need;
import static navalaction.pricing.Util.t;

/**
 *
 */
public class ReportLabor {
    public static void main(final String[] args) throws IOException {
//        final Map<Integer, Need> needs = new HashMap<>();
        final World world = JsonWorldBuilder.create("res/20160823");
        //final Port port = world.portsByName.get("Willemstad");
        world.ports.values().stream().filter(p -> p.capital == true).sorted(Comparator.comparing(Port::getName)).forEach(port -> {
            final Shop shop = world.shops.get(port.id);
            System.out.println(port.name + ":");
            System.out.println("+---------------------------------------------+---------------------+---------------------+");
            System.out.println("|                                             | Base Price          | Consumption Price   |");
            System.out.println("+-----------------------+----------+----------+---------------------+---------------------+");
            System.out.println("| Recipe                | Bid      | Ask      | Bid(h)   | Ask(h)   | Bid(h)   | Ask(h)   |");
            System.out.println("+-----------------------+----------+----------+----------+----------+----------+----------+");
            world.itemTemplates.values().stream().filter(t -> t.type == ItemTemplateType.RECIPE).sorted(Comparator.comparing(ItemTemplate::getName)).map(RecipeResourceTemplate.class::cast).forEach(t -> {
                // TODO: pick the right result :)
                final Requirement result = (Requirement) t.results.values().iterator().next();
                final ResourceTemplate<?> item = world.itemTemplate(result.template, ResourceTemplate.class);
                final Need need = need(world, t, 1 / (double) result.amount, 0.0f);
                final RegularItem regularItem = shop.regularItems.get(item.id);
                System.out.format("| %-21s | ", t(t.name.substring(0, t.name.length() - " Blueprint".length()), 21));
                if (regularItem != null) {
                    // Bid
                    if (regularItem.sellPrice > 0)
                        System.out.format(" %7d | ", regularItem.sellPrice);
                    else
                        System.out.print("         | ");
                    // Ask
                    if (regularItem.buyPrice > 0)
                        System.out.format(" %7d | ", regularItem.buyPrice);
                    else
                        System.out.print("         | ");
                    // basePrice
                    // Bid(h) vs basePrice
                    if (regularItem.sellPrice > 0)
                        System.out.format(" %7.2f | ", (regularItem.sellPrice - need.basePrice) / need.laborPrice);
                    else
                        System.out.print("         | ");
                    // Ask(h) vs basePrice
                    if (regularItem.buyPrice > 0)
                        System.out.format(" %7.2f | ", (regularItem.buyPrice - need.basePrice) / need.laborPrice);
                    else
                        System.out.print("         | ");
                    // consumptionPrice
                    // Bid(h) vs consumptionPrice
                    if (regularItem.sellPrice > 0)
                        System.out.format(" %7.2f | ", (regularItem.sellPrice - need.consumptionPrice) / need.laborPrice);
                    else
                        System.out.print("         | ");
                    // Ask(h) vs consumptionPrice
                    if (regularItem.buyPrice > 0)
                        System.out.format(" %7.2f | ", (regularItem.buyPrice - need.consumptionPrice) / need.laborPrice);
                    else
                        System.out.print("         | ");
                } else
                    System.out.print("         |          |          |          |          |          |");
                System.out.println();
            });
            System.out.println("+-----------------------+----------+----------+----------+----------+----------+----------+");
            System.out.println();
        });
    }
}
