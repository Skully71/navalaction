package navalaction.model;

import java.util.Collection;
import java.util.Collections;

/**
 *
 */
public class WoodTypeDesc {
    public final WoodType woodType;
    public final Collection<Requirement> requirements;

    public WoodTypeDesc(final WoodType woodType, final Collection<Requirement> requirements) {
        this.woodType = woodType;
        this.requirements = Collections.unmodifiableCollection(requirements);
    }
}
