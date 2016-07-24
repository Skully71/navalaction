package navalaction.json;

import navalaction.model.ItemTemplate;
import navalaction.model.World;

import javax.json.JsonObject;
import java.io.IOException;
import java.util.Comparator;

/**
 *
 */
public class JsonDiff {
    public static void main(final String[] args) throws IOException {
        final World world1 = JsonWorldBuilder.create("res/20160711");
        final World world2 = JsonWorldBuilder.create("res/20160723");
        world2.itemTemplates.values().stream().sorted(Comparator.comparing(ItemTemplate::getName)).forEach(t -> {
            final ItemTemplate<JsonObject> current = t;
            final ItemTemplate<JsonObject> other = world1.itemTemplate(t.id, ItemTemplate.class);
            if (!current.attachment.equals(other.attachment)) {
                System.out.println("Name: " + current.name);
            }
        });
    }
}
