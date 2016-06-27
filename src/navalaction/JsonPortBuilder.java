package navalaction;

import javafx.geometry.Point3D;
import javafx.scene.shape.Line;

import javax.json.JsonObject;

/**
 *
 */
public class JsonPortBuilder {
    public static Port create(final JsonObject obj) {
        final JsonObject pos = obj.getJsonObject("Position");
        return new Port(obj.getString("Name"), pos.getJsonNumber("x").doubleValue(), pos.getJsonNumber("y").doubleValue(), pos.getJsonNumber("z").doubleValue());
    }
}
