package navalaction.json;

import navalaction.model.RegularItem;
import navalaction.model.Resource;
import navalaction.model.Shop;

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
public class JsonShopBuilder {
    private static Shop create(final JsonObject obj) {
        final Map<Integer, RegularItem> regularItems = new HashMap<>();
        obj.getJsonArray("RegularItems").stream().map(JsonObject.class::cast).forEach(i -> {
            final RegularItem item = new RegularItem(i.getInt("TemplateId"), i.getInt("Quantity"), i.getInt("SellPrice"), i.getInt("BuyPrice"), i.getInt("BuyContractQuantity"), i.getInt("SellContractQuantity"));
            regularItems.put(item.templateId, item);
        });
        // TODO: Created, Modified
        final Map<Integer, Resource> resourcesProduced = new HashMap<>();
        obj.getJsonArray("ResourcesProduced").stream().map(JsonObject.class::cast).forEach(i -> {
            final Resource resource = new Resource(i.getInt("Key"), i.getInt("Value"));
            resourcesProduced.put(resource.key, resource);
        });
        final Map<Integer, Resource> resourcesConsumed = new HashMap<>();
        obj.getJsonArray("ResourcesConsumed").stream().map(JsonObject.class::cast).forEach(i -> {
            final Resource resource = new Resource(i.getInt("Key"), i.getInt("Value"));
            resourcesConsumed.put(resource.key, resource);
        });
        return new Shop(Integer.valueOf(obj.getString("Id")), regularItems, resourcesProduced, resourcesConsumed);
    }

    public static Map<Integer, Shop> read(final String shopsFileName) throws IOException {
        final Map<Integer, Shop> shops = new HashMap<>();
        try (final Reader r = new FileReader(shopsFileName)) {
            final char[] ignore = new char["var Shops = ".length()];
            r.read(ignore);
            final JsonReader reader = Json.createReader(r);
            final JsonArray array = reader.readArray();
            reader.close();
            array.stream().forEach(s -> {
                //System.out.println(s);
                final Shop shop = JsonShopBuilder.create((JsonObject) s);
                shops.put(shop.id, shop);
            });
        }
        return Collections.unmodifiableMap(shops);
    }
}
