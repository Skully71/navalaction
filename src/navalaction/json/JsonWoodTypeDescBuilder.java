package navalaction.json;

import navalaction.model.Requirement;
import navalaction.model.WoodType;
import navalaction.model.WoodTypeDesc;

import javax.json.JsonObject;
import java.util.Collection;
import java.util.HashSet;
import java.util.function.Function;

/**
 *
 */
class JsonWoodTypeDescBuilder implements Function<JsonObject, WoodTypeDesc> {
    static final JsonWoodTypeDescBuilder INSTANCE = new JsonWoodTypeDescBuilder();

    @Override
    public WoodTypeDesc apply(final JsonObject obj) {
        final Collection<Requirement> requirements = new HashSet<>();
        obj.getJsonArray("Requirements").stream().map(JsonObject.class::cast).forEach(r -> {
            requirements.add(JsonRequirementBuilder.create(r));
        });
        return new WoodTypeDesc(WoodType.WOOD_TYPES.get(obj.getInt("WoodType")), requirements);
    }

    static WoodTypeDesc create(final JsonObject obj) {
        return INSTANCE.apply(obj);
    }
}
