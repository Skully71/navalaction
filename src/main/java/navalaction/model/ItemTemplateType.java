package navalaction.model;

/**
 *
 */
public enum ItemTemplateType {
    BUILDING,
    CANNON,
    CHANGE_NATION_ITEM,
    CONQUEST_FLAG,
    CONTAINER,
    CONVERTIBLE_ITEM,
    CREW_REPAIR_ITEM,
    EVENT_SPAWNER_ITEM,
    EXTRA_LABOR_HOURS_USABLE_ITEM,
    EXTRA_XP_USABLE_ITEM,
    INDEPENDENT_LOOT_TABLE,
    ITEM,
    LIMITED_CONTAINER,
    LOOT_CONTAINER,
    LOOT_SHIPS_CONTAINER,
    LOOT_TABLE_ITEM,
    MARK_ITEM,
    MATERIAL,
    MEGA_SHIP_WRECK_INFO_LETTER,
    MODULE,
    MODULES_CONTAINER,
    NATION_LOOT_TABLE,
    RECIPE,
    RECIPE_MODULE,
    RECIPE_RESOURCE,
    RECIPE_SHIP,
    RESOURCE,
    TIME_BASED_ITEM,
    SHIP,
    SHIP_CONVERTIBLE_ITEM,
    SHIP_LOOT_TABLE_ITEM,
    SHIP_REPAIR_ITEM,
    SHIP_REPAIR_KIT_ITEM,
    SHIP_SKIN_ITEM,
    SHIP_UPGRADE_BOOK_ITEM,
    SHIPS_CONTAINER,
    TIME_BASED_CONVERTIBLE_ITEM,
    TOP_PLAYERS_LETTER,
    USABLE_ITEM,
    ;

    public static ItemTemplateType find(final String text) {
        final String s = text.substring("MegaChaka.Services.Items.".length(), text.indexOf(',') - "Template".length());
        return ItemTemplateType.valueOf(toIdentifier(s));
    }

    private static String toIdentifier(final String s) {
        final StringBuilder sb = new StringBuilder();
        for (int i = 0; i < s.length(); i++) {
            final char c = s.charAt(i);
            if (i > 0 && Character.isUpperCase(c)) {
                sb.append('_');
            }
            sb.append(Character.toUpperCase(c));
        }
        return sb.toString();
    }
}
