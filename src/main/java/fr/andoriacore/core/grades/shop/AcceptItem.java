package fr.andoriacore.core.grades.shop;

import fr.andoriaapi.spigot.items.InventoryItem;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public abstract class AcceptItem extends InventoryItem {
    public AcceptItem() {
        super(new ItemStack(Material.WOOL, 1, (byte)13), "§2Accepter");
        super.addAllItemFlags();
    }
}
