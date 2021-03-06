package navalaction.json;

import navalaction.model.Item;
import navalaction.model.ItemTemplate;
import navalaction.model.ItemTemplateType;

import javax.json.*;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.*;
import java.util.function.Function;

/**
 *
 */
public class JsonItemTemplateBuilder implements Function<JsonObject, ItemTemplate> {
    @Override
    public ItemTemplate<JsonObject> apply(final JsonObject jsonObject) {
        //throw new RuntimeException("TODO");
        return null;
    }

    private static ItemTemplate<JsonObject> create(final JsonObject obj) {
        // __type, Name, Id, MaxStack, ItemWeight, BasePrice, SellPrice, BuyPrice, PriceReductionAmount, ConsumedScale, NonConsumedScale, PriceTierQuantity, MaxQuantity, SortingGroup, SellableInShop, SellPriceCoefficient, ItemType, MongoID
        for (final JsonItemTemplateBuilder builder : ServiceLoader.load(JsonItemTemplateBuilder.class)) {
            final ItemTemplate result = builder.apply(obj);
            if (result != null)
                return result;
        }
        // TODO: there is more
        Collection<Item> items = null;
        /* This piece only applies to Loot
        final JsonArray itemsArray = obj.getJsonArray("Items");
        if (itemsArray != null) {
            items = new HashSet<>();
            final Collection<Item> c = items;
            obj.getJsonArray("Items").stream().map(JsonObject.class::cast).forEach(i -> {
                c.add(createItem(i));
            });
        }
        */
        //if (obj.getString("Name").equals("Iron Ore")) System.out.println(obj);
        //if (obj.getInt("Id") == 2) System.out.println(obj);
        return new ItemTemplate<>(obj.getInt("Id"), obj.getString("Name"), ItemTemplateType.find(obj.getString("__type")), items, obj);
    }

    private static Item createItem(final JsonObject obj) {
        final JsonNumber template = obj.getJsonNumber("Template");
        if (template == null) throw new IllegalStateException("No Template on " + obj);
        return new Item(template.intValue(), obj.getJsonNumber("Chance").doubleValue());
    }

    static int id(final JsonObject obj) {
        return obj.getInt("Id");
    }

    static String name(final JsonObject obj) {
        return obj.getString("Name");
    }

    public static Map<Integer, ItemTemplate<?>> read(final String itemTemplatesFileName) throws IOException {
        final Map<Integer, ItemTemplate<?>> itemTemplates = new HashMap<>();
        try (final Reader r = new FileReader(itemTemplatesFileName)) {
            final char[] ignore = new char["var ItemTemplates = ".length()];
            r.read(ignore);
            final JsonReader reader = Json.createReader(r);
            final JsonArray array = reader.readArray();
            reader.close();
            array.stream().map(JsonObject.class::cast).forEach(s -> {
                //System.out.println(s);
                final ItemTemplate itemTemplate = create(s);
                // 306 == Constitution Blueprint
//                if (itemTemplate.type == ItemTemplateType.RECIPE_RESOURCE)
//                    System.out.println(s);
                itemTemplates.put(itemTemplate.id, itemTemplate);
//                if (itemTemplate.type == ItemTemplateType.LOOT_TABLE_ITEM && itemTemplate.name.indexOf("Fishing") != -1) {
//                    System.out.println(s.getJsonArray("Items"));
//                }
            });
        }
        return Collections.unmodifiableMap(itemTemplates);
    }

    static ItemTemplateType type(final JsonObject obj) {
        return ItemTemplateType.find(obj.getString("__type"));
    }
}
