package navalaction.model;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;

/**
 *
 */
public class RecipeShipTemplate extends ItemTemplate {
    public final Map<WoodType, WoodTypeDesc> woodTypeDescs;
    public final int laborPrice;
    public final Collection<Requirement> fullRequirements;

    public RecipeShipTemplate(final int id, final String name, final ItemTemplateType type, final Map<WoodType, WoodTypeDesc> woodTypeDescs, final int laborPrice, final Collection<Requirement> fullRequirements) {
        super(id, name, type, null);
        this.woodTypeDescs = Collections.unmodifiableMap(woodTypeDescs);
        this.laborPrice = laborPrice;
        this.fullRequirements = Collections.unmodifiableCollection(fullRequirements);
    }
}
