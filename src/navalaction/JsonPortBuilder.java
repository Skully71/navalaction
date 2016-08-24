package navalaction;

import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.json.JsonReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.HashMap;
import java.util.Map;

/**
 *
 */
public class JsonPortBuilder {
    public static Port create(final JsonObject obj) {
        final JsonObject pos = obj.getJsonObject("Position");
        return new Port(Integer.valueOf(obj.getString("Id")), obj.getString("Name"), pos.getJsonNumber("x").doubleValue(), pos.getJsonNumber("y").doubleValue(), pos.getJsonNumber("z").doubleValue(), obj.getInt("ConquestFlagTimeSlot"), obj.getBoolean("Capital"));
    }

    public static Map<Integer, Port> read(final String portsFileName) throws IOException {
        final Map<Integer, Port> ports = new HashMap<>();
        try (final Reader r = new FileReader(portsFileName)) {
            final char[] ignore = new char["var Ports = ".length()];
            r.read(ignore);
            final JsonReader reader = Json.createReader(r);
            final JsonArray portArray = reader.readArray();
            reader.close();
            portArray.stream().map(JsonObject.class::cast).forEach(p -> {
                final Port port = JsonPortBuilder.create(p);
                ports.put(port.id, port);
            });
        }
        return ports;
    }
}
