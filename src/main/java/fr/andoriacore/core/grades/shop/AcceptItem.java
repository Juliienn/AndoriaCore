package fr.elysiumcore.core.grades.shop;

import fr.elysiumapi.spigot.items.InventoryItem;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public abstract class AcceptItem extends InventoryItem {
    public AcceptItem() {
        super(new ItemStack(Material.WOOL, 1, (byte)13), "§2Accepter");
        super.addAllItemFlags();
    }
}
