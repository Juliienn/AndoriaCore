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

    private final PlayerDataManager playerDataManager;
    private final boolean clear;
    private final int slot1,slot2,slot3,slot4;

    public FactionItem(boolean clear, int slot1, int slot2, int slot3, int slot4) {
        super(new ItemStack(Material.DIAMOND_HELMET), "§eFaction");
        super.addAllItemFlags();
        ArrayList<String> lores = new ArrayList<>();
        lores.add(" ");
        lores.add("§e" + Symbols.STARBALL + " §bLa durée des grades est de 1 mois");
        super.setLore(lores);
        this.playerDataManager = AndoriaCore.getInstance().getPlayerDataManager();
        this.clear = clear;
        this.slot1 = slot1;
        this.slot2 = slot2;
        this.slot3 = slot3;
        this.slot4 = slot4;
    }

    @Override
    public void onClick(InventoryClickEvent event) {
        event.setCancelled(true);
        if(!(event.getWhoClicked() instanceof Player)) return;
        AndoriaPlayer andoriaPlayer = AndoriaPlayer.getAndoriaPlayer(event.getWhoClicked().getUniqueId());
        PlayerData playerData = playerDataManager.getPlayerData(andoriaPlayer.getUUID(), andoriaPlayer.getPlayer().getName());
        AndoriaInventory andoriaInventory = andoriaPlayer.getOpenedInventory();
        if(clear)andoriaInventory.clear();
        andoriaInventory.setItem(new GuerrierItem(playerData), slot1);
        andoriaInventory.setItem(new DaimyoItem(playerData), slot2);
        andoriaInventory.setItem(new TaishoItem(playerData), slot3);
        andoriaInventory.setItem(new SamuraiItem(playerData), slot4);
    }
}