package navalaction.json;

import navalaction.model.ItemTemplate;
import navalaction.model.ResourceTemplate;

import javax.json.JsonObject;

/**
 *
 */
public class JsonResourceTemplateBuilder extends JsonItemTemplateBuilder {
    //static final JsonResourceTemplateBuilder INSTANCE = new JsonResourceTemplateBuilder();

    @Override
    public ItemTemplate<JsonObject> apply(final JsonObject obj) {
        if (!obj.getString("__type").equals("MegaChaka.Services.Items.ResourceTemplate, MegaChaka") &&
                !obj.getString("__type").equals("MegaChaka.Services.Items.ExtraLaborHoursUsableItemTemplate, MegaChaka"))
            return null;
        //[__type, Name, Id, MaxStack, ItemWeight, BasePrice, SellPrice, BuyPrice, PriceReductionAmount, ConsumedScale, NonConsumedScale, PriceTierQuantity, MaxQuantity, SortingGroup, SellableInShop, SellPriceCoefficient, ItemType, MongoID, InitialAmount, ProductionScale, ConsumptionScale, SpawnChance, AutoFillCoefficient, ProducedByNation, ConsumedByNation, ProducedInCapitals, ProducedInTowns, ConsumedInCapitals, ConsumedInTowns]
        //System.out.println(obj);
        return new ResourceTemplate(id(obj), name(obj), type(obj), obj.getInt("BasePrice"), obj.getJsonNumber("ItemWeight").doubleValue(), obj);
    }
}
