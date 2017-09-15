package navalaction.json;

import navalaction.JsonPortBuilder;
import navalaction.Port;
import navalaction.model.ItemTemplate;
import navalaction.model.Shop;
import navalaction.model.World;

import java.io.IOException;
import java.util.Map;

/**
 *
 */
public class JsonWorldBuilder {
    public static World create(final String dirName) throws IOException {
        return create(dirName, "eu1");
    }

    public static World create(final String dirName, final String shard) throws IOException {
        final Map<Integer, ItemTemplate<?>> itemTemplates = JsonItemTemplateBuilder.read(dirName + "/ItemTemplates_cleanopenworldprod" + shard + ".json");
        final Map<Integer, Port> ports = JsonPortBuilder.read(dirName + "/Ports_cleanopenworldprod" + shard + ".json");
        final Map<Integer, Shop> shops = JsonShopBuilder.read(dirName + "/Shops_cleanopenworldprod" + shard + ".json");
        final World world = new World(itemTemplates, ports, shops);
        return world;
    }
}
