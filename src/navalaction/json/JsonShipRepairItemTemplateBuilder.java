package navalaction.json;

import navalaction.model.ItemTemplate;
import navalaction.model.ShipRepairItemTemplate;

import javax.json.JsonObject;

/**
 *
 */
public class JsonShipRepairItemTemplateBuilder extends JsonItemTemplateBuilder {
    @Override
    public ItemTemplate<JsonObject> apply(final JsonObject obj) {
        if (!obj.getString("__type").equals("MegaChaka.Services.Items.ShipRepairItemTemplate, MegaChaka"))
            return null;
        //[__type, Name, Id, PreventTeleport, DropChanceReductionPerItem, MaxStack, ItemWeight, BasePrice, SellPrice, BuyPrice, PriceReductionAmount, ConsumedScale, NonConsumedScale, PriceTierQuantity, MaxQuantity, SortingGroup, SellableInShop, ResetStockOnServerStart, SellPriceCoefficient, ItemType, MongoID, ArmorAmount, SailsAmount, DisposeOnUse, CanBeUsedInPort, CanBeUsedInOpenWorld]
        //System.out.println(obj);
        return new ShipRepairItemTemplate(id(obj), name(obj), type(obj), obj.getInt("BasePrice"), obj.getJsonNumber("ItemWeight").doubleValue(), obj);
    }
}
