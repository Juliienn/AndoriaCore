package fr.andoriacore.core.grades.items.faction;

import fr.andoriaapi.commons.Symbols;
import fr.andoriaapi.database.player.PlayerData;
import fr.andoriaapi.database.player.PlayerDataManager;
import fr.andoriaapi.spigot.inventories.AndoriaInventory;
import fr.andoriaapi.spigot.items.InventoryItem;
import fr.andoriaapi.spigot.player.AndoriaPlayer;
import fr.andoriacore.core.AndoriaCore;
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
        AndoriaPlayer andoriaPlayer = AndoriaPlayer.getAndoriaPlayer(event.getWhoClicked().getUniqueId());
        PlayerData playerData = playerDataManager.getPlayerData(andoriaPlayer.getUUID(), andoriaPlayer.getPlayer().getName());
        AndoriaInventory andoriaInventory = andoriaPlayer.getOpenedInventory();
        andoriaInventory.clear();
        andoriaInventory.setItem(new GuerrierItem(playerData), 10);
        andoriaInventory.setItem(new DaimyoItem(playerData), 12);
        andoriaInventory.setItem(new TaishoItem(playerData), 14);
        andoriaInventory.setItem(new SamuraiItem(playerData), 16);
    }
}