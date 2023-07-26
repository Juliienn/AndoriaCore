package fr.andoriacore.core.grades.inventories;

import fr.andoriaapi.spigot.inventories.AndoriaInventory;
import fr.andoriacore.core.grades.items.faction.FactionItem;

public class GradesInventory extends AndoriaInventory {

    // - ? - - ? - - ? -

    public GradesInventory(boolean clear, int slot1, int slot2, int slot3, int slot4) {
        super("§eGrades", 27);
        super.setItem(new FactionItem(clear, slot1, slot2, slot3, slot4), 13);
    }
}
