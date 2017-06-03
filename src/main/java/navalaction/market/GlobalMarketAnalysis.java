package navalaction.market;

import navalaction.JsonPortBuilder;
import navalaction.Port;
import navalaction.json.JsonItemTemplateBuilder;
import navalaction.json.JsonShopBuilder;
import navalaction.json.JsonWorldBuilder;
import navalaction.json.JsonWorldBuilder;
import navalaction.model.ItemTemplate;
import navalaction.model.ItemTemplateType;
import navalaction.model.Shop;
import navalaction.model.World;

import java.io.IOException;
import java.util.Comparator;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

/**
 *
 */
public class GlobalMarketAnalysis {
    public static void main(final String[] args) throws IOException {
        final World world = JsonWorldBuilder.create("res/20160821");
        final Map<Integer, Shop> shops = world.shops;

        /*
        {
            final Port barataria = world.portsByName.get("Barataria");
            final Shop shop = world.shops.get(barataria.id);
            shop.regularItems.values()
                    .stream()
                    .filter(i -> itemTemplates.get(i.templateId).type == ItemTemplateType.MATERIAL || itemTemplates.get(i.templateId).type == ItemTemplateType.RESOURCE)
                    .sorted((i1, i2) -> itemTemplates.get(i1.templateId).name.compareTo(itemTemplates.get(i2.templateId).name))
                    .forEach(i -> {
                        final ItemTemplate template = itemTemplates.get(i.templateId);
                        System.out.println(template + " " + i);
                    });
        }
        */
        /*
        shop.regularItems.values().forEach(i -> {
            System.out.println(itemTemplates.get(i.templateId).name);
            //System.out.println(i);
        });
        */

        // 24 = coal
        // 607 = Tobacco
        final int id = 607;
        final AtomicInteger totalSell = new AtomicInteger(0), numSell = new AtomicInteger(0), maxSell = new AtomicInteger(0);
        final AtomicInteger totalBuy = new AtomicInteger(0), numBuy = new AtomicInteger(0), minBuy = new AtomicInteger(Integer.MAX_VALUE);
        world.ports.values().stream().forEach(p -> {
            final Shop shop = shops.get(p.id);
            shop.regularItems.values().stream().filter(i -> i.templateId == id).forEach(regularItem -> {
                System.out.print(regularItem);
                if (regularItem.sellContractQuantity > 0 || regularItem.sellPrice > 1) {
                    System.out.print("  ** bid " + regularItem.sellPrice);
                    totalSell.addAndGet(regularItem.sellPrice);
                    numSell.incrementAndGet();
                    if (regularItem.sellPrice > maxSell.intValue())
                        maxSell.set(regularItem.sellPrice);
                }
                if (regularItem.buyPrice > 0) {
                    //System.out.print("  ** " + shop.resourcesConsumed.get(regularItem.templateId));
                    totalBuy.addAndGet(regularItem.buyPrice);
                    numBuy.incrementAndGet();
                    if (regularItem.buyPrice < minBuy.intValue())
                        minBuy.set(regularItem.buyPrice);
                }
                System.out.println();
            });
        });
        final int bid = totalSell.intValue() / numSell.intValue();
        System.out.println(" " + bid + " " + maxSell);
        final int ask = totalBuy.intValue() / numBuy.intValue();
        System.out.println(" " + ask + " " + minBuy);

        world.itemTemplates.values().stream().filter(i -> i.type == ItemTemplateType.MATERIAL || i.type == ItemTemplateType.RESOURCE).sorted(Comparator.comparing(ItemTemplate::getName)).forEach(i -> {
            System.out.println(i.name);
            report(world, i);
        });

//        world.itemTemplates.values().stream().filter(i -> i.type == ItemTemplateType.MODULE).sorted(Comparator.comparing(ItemTemplate::getName)).map(ItemTemplate::getName).distinct().forEach(i -> {
//            System.out.println(i);
//        });
        world.itemTemplates.values().stream().sorted(Comparator.comparing(ItemTemplate::getName)).filter(t -> (t.type == ItemTemplateType.RECIPE_SHIP)).forEach(t -> {
            System.out.println(t);
            /*
            if (t.items != null) {
                t.items.stream().sorted((i1, i2) -> world.itemTemplatesById.get(i1.template).name.compareTo(world.itemTemplatesById.get(i2.template).name)).forEach(i -> {
                    System.out.println("  " + world.itemTemplatesById.get(i.template).name + ": " + i.chance);
                });
            }
            */
        });
    }

    private static void report(final World world, final ItemTemplate itemTemplate) {
        final int id = itemTemplate.id;
        final AtomicInteger totalSell = new AtomicInteger(0), numSell = new AtomicInteger(0), maxSell = new AtomicInteger(0);
        final AtomicInteger totalBuy = new AtomicInteger(0), numBuy = new AtomicInteger(0), minBuy = new AtomicInteger(Integer.MAX_VALUE);
        world.ports.values().stream().forEach(p -> {
            final Shop shop = world.shops.get(p.id);
            shop.regularItems.values().stream().filter(i -> i.templateId == id).forEach(regularItem -> {
                //System.out.print(regularItem);
                if (regularItem.sellContractQuantity > 0 || regularItem.sellPrice > 1) {
                    //System.out.print("  ** bid " + regularItem.sellPrice);
                    totalSell.addAndGet(regularItem.sellPrice);
                    numSell.incrementAndGet();
                    if (regularItem.sellPrice > maxSell.intValue())
                        maxSell.set(regularItem.sellPrice);
                }
                if (regularItem.buyPrice > 0) {
                    //System.out.print("  ** " + shop.resourcesConsumed.get(regularItem.templateId));
                    totalBuy.addAndGet(regularItem.buyPrice);
                    numBuy.incrementAndGet();
                    if (regularItem.buyPrice < minBuy.intValue())
                        minBuy.set(regularItem.buyPrice);
                }
                //System.out.println();
            });
        });
        final int bid = totalSell.intValue() / (numSell.intValue() > 0 ? numSell.intValue() : 1);
        System.out.println(" avg. bid " + bid + " max. bid " + maxSell);
        final int ask = totalBuy.intValue() / (numBuy.intValue() > 0 ? numBuy.intValue() : 1);
        System.out.println(" avg. ask " + ask + " min. ask " + minBuy);
    }
}
