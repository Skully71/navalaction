package navalaction.json;

import navalaction.model.ItemTemplate;
import navalaction.model.ItemTemplateType;

import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.json.JsonReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 *
 */
public class JsonItemTemplateBuilder {
    private static ItemTemplate create(final JsonObject obj) {
        // __type, Name, Id, MaxStack, ItemWeight, BasePrice, SellPrice, BuyPrice, PriceReductionAmount, ConsumedScale, NonConsumedScale, PriceTierQuantity, MaxQuantity, SortingGroup, SellableInShop, SellPriceCoefficient, ItemType, MongoID
        // TODO: there is more
        return new ItemTemplate(obj.getInt("Id"), obj.getString("Name"), ItemTemplateType.find(obj.getString("__type")));
    }

    public static Map<Integer, ItemTemplate> read(final String itemTemplatesFileName) throws IOException {
        final Map<Integer, ItemTemplate> itemTemplates = new HashMap<>();
        try (final Reader r = new FileReader(itemTemplatesFileName)) {
            final char[] ignore = new char["var ItemTemplates = ".length()];
            r.read(ignore);
            final JsonReader reader = Json.createReader(r);
            final JsonArray array = reader.readArray();
            reader.close();
            array.stream().forEach(s -> {
                //System.out.println(s);
                final ItemTemplate itemTemplate = create((JsonObject) s);
                itemTemplates.put(itemTemplate.id, itemTemplate);
            });
        }
        return Collections.unmodifiableMap(itemTemplates);
    }

}
