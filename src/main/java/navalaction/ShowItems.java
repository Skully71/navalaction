package navalaction;

import navalaction.json.JsonWorldBuilder;
import navalaction.model.ItemTemplate;
import navalaction.model.World;

import javax.json.JsonObject;
import java.io.IOException;
import java.util.Comparator;

/**
 *
 */
public class ShowItems {
    public static void main(final String[] args) throws IOException {
        final World world = JsonWorldBuilder.create("res/20170125");
//        world.itemTemplates.values().stream().sorted(Comparator.comparing(ItemTemplate::getName)).forEach(t -> {
//            if (t.type == ItemTemplateType.SHIP_LOOT_TABLE_ITEM && ((Collection<Item>) t.items).stream().anyMatch(i -> i.template == 25)) {
//                System.out.println(t.name + ": " + t.attachment);
//            }
//            /*
//            final ItemTemplate<JsonObject> current = t;
//            final ItemTemplate<JsonObject> other = world1.itemTemplate(t.id, ItemTemplate.class);
//            if (other == null || !current.attachment.equals(other.attachment)) {
//                System.out.println("Name: " + current.name + ": " + current.attachment);
//            }
//            if (current.type == ItemTemplateType.MEGA_SHIP_WRECK_INFO_LETTER)
//                System.out.println(current.attachment);
//            */
//        });
        world.itemTemplates.values().stream().filter(t -> t.name.contains("Silver")).sorted(Comparator.comparing(ItemTemplate::getName)).forEach(t -> {
            System.out.println(t.name + ": " + t.attachment);
            final JsonObject obj = (JsonObject) t.attachment;
            System.out.println(obj.getJsonString("SortingGroup"));
        });
        world.itemTemplates.values().stream().filter(t -> ((JsonObject) t.attachment).getString("SortingGroup").equals("Resource.Trading")).sorted(Comparator.comparing(ItemTemplate::getName)).forEach(t -> {
            //System.out.println(t.name + ": " + t.attachment);
            final JsonObject obj = (JsonObject) t.attachment;
            System.out.println(t.name + ": " + obj.getInt("BasePrice"));
        });
    }
}
