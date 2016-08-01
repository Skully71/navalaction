package navalaction.json;

import navalaction.model.CrewRepairItemTemplate;
import navalaction.model.ItemTemplate;

import javax.json.JsonObject;

/**
 *
 */
public class JsonCrewRepairItemTemplateBuilder extends JsonItemTemplateBuilder {
    @Override
    public ItemTemplate<JsonObject> apply(final JsonObject obj) {
        if (!obj.getString("__type").equals("MegaChaka.Services.Items.CrewRepairItemTemplate, MegaChaka"))
            return null;
        //[__type, Name, Id, PreventTeleport, DropChanceReductionPerItem, MaxStack, ItemWeight, BasePrice, SellPrice, BuyPrice, PriceReductionAmount, ConsumedScale, NonConsumedScale, PriceTierQuantity, MaxQuantity, SortingGroup, SellableInShop, ResetStockOnServerStart, SellPriceCoefficient, ItemType, MongoID, Amount, DisposeOnUse, CanBeUsedInPort, CanBeUsedInOpenWorld]
        //System.out.println(obj);
        return new CrewRepairItemTemplate(id(obj), name(obj), type(obj), obj.getInt("BasePrice"), obj.getJsonNumber("ItemWeight").doubleValue(), obj);
    }
}
