package fr.andoriacore.core.grades.inventories;

import fr.andoriaapi.spigot.inventories.AndoriaInventory;
import fr.andoriacore.core.grades.items.faction.FactionItem;

public class GradesInventory extends AndoriaInventory {

    // - ? - - ? - - ? -

    public GradesInventory() {
        super("§eGrades", 27);
        super.setItem(new FactionItem(), 13);
    }
}
