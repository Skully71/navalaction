package navalaction.json;

import navalaction.model.Item;
import navalaction.model.ItemTemplate;
import navalaction.model.ItemTemplateType;

import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.json.JsonReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.*;

/**
 *
 */
public class JsonItemTemplateBuilder {
    private static ItemTemplate create(final JsonObject obj) {
        // __type, Name, Id, MaxStack, ItemWeight, BasePrice, SellPrice, BuyPrice, PriceReductionAmount, ConsumedScale, NonConsumedScale, PriceTierQuantity, MaxQuantity, SortingGroup, SellableInShop, SellPriceCoefficient, ItemType, MongoID
        // TODO: there is more
        Collection<Item> items = null;
        final JsonArray itemsArray = obj.getJsonArray("Items");
        if (itemsArray != null) {
            items = new HashSet<>();
            final Collection<Item> c = items;
            obj.getJsonArray("Items").stream().map(JsonObject.class::cast).forEach(i -> {
                c.add(createItem(i));
            });
        }
        return new ItemTemplate(obj.getInt("Id"), obj.getString("Name"), ItemTemplateType.find(obj.getString("__type")), items);
    }

    private static Item createItem(final JsonObject obj) {
        return new Item(obj.getInt("Template"), obj.getJsonNumber("Chance").doubleValue());
    }

    public static Map<Integer, ItemTemplate> read(final String itemTemplatesFileName) throws IOException {
        final Map<Integer, ItemTemplate> itemTemplates = new HashMap<>();
        try (final Reader r = new FileReader(itemTemplatesFileName)) {
            final char[] ignore = new char["var ItemTemplates = ".length()];
            r.read(ignore);
            final JsonReader reader = Json.createReader(r);
            final JsonArray array = reader.readArray();
            reader.close();
            array.stream().map(JsonObject.class::cast).forEach(s -> {
                //System.out.println(s);
                final ItemTemplate itemTemplate = create(s);
                itemTemplates.put(itemTemplate.id, itemTemplate);
//                if (itemTemplate.type == ItemTemplateType.LOOT_TABLE_ITEM && itemTemplate.name.indexOf("Fishing") != -1) {
//                    System.out.println(s.getJsonArray("Items"));
//                }
            });
        }
        return Collections.unmodifiableMap(itemTemplates);
    }

}
