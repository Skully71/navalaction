package navalaction.json;

import navalaction.model.ItemTemplate;
import navalaction.model.MaterialTemplate;

import javax.json.JsonObject;

/**
 *
 */
public class JsonMaterialTemplateBuilder extends JsonItemTemplateBuilder {
    @Override
    public ItemTemplate<JsonObject> apply(final JsonObject obj) {
        if (!obj.getString("__type").equals("MegaChaka.Services.Items.MaterialTemplate, MegaChaka"))
            return null;
        //[__type, Name, Id, PreventTeleport, DropChanceReductionPerItem, MaxStack, ItemWeight, BasePrice, SellPrice, BuyPrice, PriceReductionAmount, ConsumedScale, NonConsumedScale, PriceTierQuantity, MaxQuantity, SortingGroup, SellableInShop, ResetStockOnServerStart, SellPriceCoefficient, ItemType, MongoID]
        //System.out.println(obj);
        return new MaterialTemplate(id(obj), name(obj), type(obj), obj.getInt("BasePrice"), obj.getJsonNumber("ItemWeight").doubleValue(), obj);
    }
}
