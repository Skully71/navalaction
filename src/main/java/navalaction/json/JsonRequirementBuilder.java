package navalaction.json;

import navalaction.model.Requirement;

import javax.json.JsonObject;
import java.util.function.Function;

/**
 *
 */
class JsonRequirementBuilder implements Function<JsonObject, Requirement> {
    static final JsonRequirementBuilder INSTANCE = new JsonRequirementBuilder();

    @Override
    public Requirement apply(final JsonObject obj) {
        return new Requirement(obj.getInt("Template"), obj.getInt("Amount"), obj.getJsonNumber("Chance").doubleValue());
    }

    static Requirement create(final JsonObject obj) {
        return INSTANCE.apply(obj);
    }
}
