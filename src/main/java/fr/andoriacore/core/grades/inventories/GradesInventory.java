package fr.elysiumcore.core.grades.inventories;

import fr.elysiumapi.spigot.inventories.ElysiumInventory;
import fr.elysiumcore.core.grades.items.faction.FactionItem;

public class GradesInventory extends ElysiumInventory {

    // - ? - - ? - - ? -

    public GradesInventory() {
        super("§eGrades", 27);
        super.setItem(new FactionItem(), 13);
    }
}
