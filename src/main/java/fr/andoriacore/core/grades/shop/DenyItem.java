package fr.andoriacore.core.grades.shop;

import fr.andoriaapi.spigot.items.InventoryItem;
import org.bukkit.Material;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

public class DenyItem extends InventoryItem {
    public DenyItem() {
        super(new ItemStack(Material.WOOL, 1, (byte)14), "§4Annuler");
        super.addAllItemFlags();
    }

    @Override
    public void onClick(InventoryClickEvent event) {
        event.getWhoClicked().closeInventory();
    }
}
