package navalaction.json;

import navalaction.model.CannonTemplate;
import navalaction.model.ItemTemplate;

import javax.json.JsonObject;

/**
 *
 */
public class JsonCannonTemplateBuilder extends JsonItemTemplateBuilder {
    @Override
    public ItemTemplate<JsonObject> apply(final JsonObject obj) {
        if (!obj.getString("__type").equals("MegaChaka.Services.Items.CannonTemplate, MegaChaka"))
            return null;
        //[__type, MaxItems, Name, Id, PreventTeleport, DropChanceReductionPerItem, MaxStack, ItemWeight, BasePrice, SellPrice, BuyPrice, PriceReductionAmount, ConsumedScale, NonConsumedScale, PriceTierQuantity, MaxQuantity, SortingOverrideTemplateType, SortingGroup, SellableInShop, CanBeSoldToShop, ResetStockOnServerStart, SellPriceCoefficient, ItemType, MongoID, Type, Form, Material, Caliber, Weight, Template, Limitation1_Value, Limitation2_Value, Limitation3_Value, ShowInContractsSelector, DeliveryOrderOptions, PortPrices]
        //System.out.println(obj.keySet());
        return new CannonTemplate(id(obj), name(obj), type(obj), obj.getInt("BasePrice"), obj.getJsonNumber("ItemWeight").doubleValue(), obj);
    }
}
