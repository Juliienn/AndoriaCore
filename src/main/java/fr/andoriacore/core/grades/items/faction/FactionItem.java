package fr.elysiumcore.core.grades.items.faction;

import fr.elysiumapi.commons.Symbols;
import fr.elysiumapi.database.player.PlayerData;
import fr.elysiumapi.database.player.PlayerDataManager;
import fr.elysiumapi.spigot.inventories.ElysiumInventory;
import fr.elysiumapi.spigot.items.InventoryItem;
import fr.elysiumapi.spigot.player.ElysiumPlayer;
import fr.elysiumcore.core.AndoriaCore;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;

public class FactionItem extends InventoryItem {

    private PlayerDataManager playerDataManager;

    public FactionItem() {
        super(new ItemStack(Material.DIAMOND_HELMET), "§eFaction");
        super.addAllItemFlags();
        ArrayList<String> lores = new ArrayList<>();
        lores.add(" ");
        lores.add("§e" + Symbols.STARBALL + " §bLa durée des grades est de 1 mois");
        super.setLore(lores);
        this.playerDataManager = AndoriaCore.getInstance().getPlayerDataManager();
    }

    @Override
    public void onClick(InventoryClickEvent event) {
        event.setCancelled(true);
        if(!(event.getWhoClicked() instanceof Player)) return;
        ElysiumPlayer elysiumPlayer = ElysiumPlayer.getIElysiumPlayer(event.getWhoClicked().getUniqueId());
        PlayerData playerData = playerDataManager.getPlayerData(elysiumPlayer.getUUID(), elysiumPlayer.getPlayer().getName());
        ElysiumInventory elysiumInventory = elysiumPlayer.getOpenedInventory();
        elysiumInventory.clear();
        elysiumInventory.setItem(new GuerrierItem(playerData), 10);
        elysiumInventory.setItem(new DaimyoItem(playerData), 12);
        elysiumInventory.setItem(new TaishoItem(playerData), 14);
        elysiumInventory.setItem(new SamuraiItem(playerData), 16);
    }
}