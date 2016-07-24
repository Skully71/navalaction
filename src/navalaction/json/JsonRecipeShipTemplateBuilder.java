package navalaction.json;

import navalaction.model.*;

import javax.json.JsonObject;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

/**
 *
 */
public class JsonRecipeShipTemplateBuilder extends JsonItemTemplateBuilder {
    @Override
    public ItemTemplate<JsonObject> apply(final JsonObject obj) {
        if (!obj.getString("__type").equals("MegaChaka.Services.Items.RecipeShipTemplate, MegaChaka"))
            return null;
        // [__type, Name, Id, MaxStack, ItemWeight, BasePrice, SellPrice, BuyPrice, PriceReductionAmount, ConsumedScale, NonConsumedScale, PriceTierQuantity, MaxQuantity, SortingGroup, SellableInShop, SellPriceCoefficient, ItemType, MongoID, WoodTypeDescs, Qualities, LaborPrice, BuildingRequirements, FullRequirements, GoldRequirements, Results, CraftGroup, RequiresLevel, GivesXP, AccessibleByLevel, BreakUpRecipeSpawnChance, DisposeOnUse, CanBeUsedInPort, CanBeUsedInOpenWorld]
        final Map<WoodType, WoodTypeDesc> woodTypeDescs = new HashMap<>();
        obj.getJsonArray("WoodTypeDescs").stream().map(JsonObject.class::cast).forEach(w -> {
            final WoodTypeDesc woodTypeDesc = JsonWoodTypeDescBuilder.create(w);
            woodTypeDescs.put(woodTypeDesc.woodType, woodTypeDesc);
        });
        // TODO: how many notes
        //System.out.println(obj.getJsonArray("Qualities"));
        final int laborPrice = obj.getInt("LaborPrice");
        //System.out.println(obj.getJsonArray("BuildingRequirements")); // should be 439 == Shipyard
        final Collection<Requirement> fullRequirements = new HashSet<>();
        obj.getJsonArray("FullRequirements").stream().map(JsonObject.class::cast).forEach(r -> {
            fullRequirements.add(JsonRequirementBuilder.create(r));
        });
        //System.out.println(obj.getInt("GoldRequirements")); // should be 0
        // TODO: Results
        return new RecipeShipTemplate(obj.getInt("Id"), obj.getString("Name"), ItemTemplateType.find(obj.getString("__type")), woodTypeDescs, laborPrice, fullRequirements, obj.getInt("GoldRequirements"), null, obj);
    }
}
