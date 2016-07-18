package navalaction.json;

import navalaction.model.ItemTemplate;
import navalaction.model.ItemTemplateType;
import navalaction.model.RecipeResourceTemplate;
import navalaction.model.Requirement;

import javax.json.JsonObject;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

/**
 *
 */
public class JsonRecipeTemplateBuilder extends JsonItemTemplateBuilder {
    @Override
    public ItemTemplate apply(final JsonObject obj) {
        if (!obj.getString("__type").equals("MegaChaka.Services.Items.RecipeTemplate, MegaChaka"))
            return null;
        //[__type, Name, Id, MaxStack, ItemWeight, BasePrice, SellPrice, BuyPrice, PriceReductionAmount, ConsumedScale, NonConsumedScale, PriceTierQuantity, MaxQuantity, SortingGroup, SellableInShop, SellPriceCoefficient, ItemType, MongoID, LaborPrice, BuildingRequirements, FullRequirements, GoldRequirements, Results, CraftGroup, RequiresLevel, GivesXP, AccessibleByLevel, BreakUpRecipeSpawnChance, DisposeOnUse, CanBeUsedInPort, CanBeUsedInOpenWorld]
        //System.out.println(obj);
        final Collection<Requirement> fullRequirements = new HashSet<>();
        obj.getJsonArray("FullRequirements").stream().map(JsonObject.class::cast).forEach(r -> {
            fullRequirements.add(JsonRequirementBuilder.create(r));
        });
        final Map<Integer, Requirement> results = new HashMap<>();
        obj.getJsonArray("Results").stream().map(JsonObject.class::cast).forEach(r -> {
            final Requirement result = JsonRequirementBuilder.create(r);
            results.put(result.template, result);
        });
        return new RecipeResourceTemplate(obj.getInt("Id"), obj.getString("Name"), ItemTemplateType.find(obj.getString("__type")), obj.getInt("LaborPrice"), fullRequirements, obj.getInt("GoldRequirements"), results);
    }
}
