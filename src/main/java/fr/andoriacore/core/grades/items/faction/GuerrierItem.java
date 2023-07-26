package fr.andoriacore.core.grades.items.faction;

import fr.andoriaapi.commons.Symbols;
import fr.andoriaapi.commons.ranks.AndoriaRanks;
import fr.andoriaapi.database.player.PlayerData;
import fr.andoriaapi.spigot.inventories.AndoriaInventory;
import fr.andoriaapi.spigot.items.InventoryItem;
import fr.andoriaapi.spigot.player.AndoriaPlayer;
import fr.andoriacore.core.AndoriaCore;
import fr.andoriacore.core.grades.shop.AcceptItem;
import fr.andoriacore.core.grades.shop.DenyItem;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import java.sql.Timestamp;
import java.util.ArrayList;

public class GuerrierItem extends InventoryItem {

    private final PlayerData playerData;
    public GuerrierItem(PlayerData playerData) {
        super(new ItemStack(Material.WOOD_SWORD), AndoriaRanks.GUERRIER.getTagId() + AndoriaRanks.GUERRIER.getPrefix(), playerData.getRankInfos().getRank().getPower() >= 1);
        ArrayList<String> lores = new ArrayList<>();
        lores.add(" ");
        lores.add("§e" + Symbols.STAR + " §7Accés au /kit §3Guerrier ");
        lores.add("§e" + Symbols.STAR + " §7Accés au /feed ");
        lores.add("§e" + Symbols.STAR + " §7Accés au /craft");
        lores.add("§e" + Symbols.STAR + " §7Accés à 2 sethomes ");
        lores.add("§e" + Symbols.STAR + " §7Gain de 50% d'xp supplémentaire");
        lores.add(" ");
        lores.add("§e" + Symbols.ARROW_RIGHT_FULL + " §bPrix: 500 points VIP");
        if(playerData.getRankInfos().getRank().getPower() >= 1){
            lores.add("§a" + Symbols.OK + " Acheté");
        }
        super.setLore(lores);
        super.addAllItemFlags();
        this.playerData = playerData;
    }

    @Override
    public void onClick(InventoryClickEvent inventoryClickEvent) {
        inventoryClickEvent.setCancelled(true);
        if(!(inventoryClickEvent.getWhoClicked() instanceof Player)) return;
        if(playerData.getRankInfos().getRank().getPower() <= 1) {
            AndoriaPlayer andoriaPlayer = AndoriaPlayer.getAndoriaPlayer(inventoryClickEvent.getWhoClicked().getUniqueId());
            AndoriaInventory andoriaInventory = andoriaPlayer.getOpenedInventory();
            andoriaInventory.clear();
            andoriaInventory.setItem(new AcceptItem() {
                @Override
                public void onClick(InventoryClickEvent event) {
                    if (event.getWhoClicked() instanceof Player) {
                        event.setCancelled(true);
                        Player player = (Player) event.getWhoClicked();
                        if (playerData.getPbs() >= 500) {
                            playerData.removePbs(500);
                            playerData.getRankInfos().setRank(AndoriaRanks.GUERRIER);
                            playerData.getRankInfos().setPurchased_at(new Timestamp(System.currentTimeMillis()));
                            playerData.getRankInfos().setExpirationDate(new Timestamp(System.currentTimeMillis() + (long) 30 * 24 * 60 * 60 * 1000));
                            player.closeInventory();
                            player.sendMessage("§7Vous possédez maintenant le grade: " + AndoriaRanks.GUERRIER.getTagId() + AndoriaRanks.GUERRIER.getPrefix());
                        } else {
                            player.closeInventory();
                            player.sendMessage("§cVous n'avez pas assez de points VIP pour acheter ce grade, pour en acheter: /vip");
                            return;
                        }
                        AndoriaCore.getInstance().getPlayerDataManager().sendPlayerDataToRedis(playerData);
                    }
                }
            }, 11);
            andoriaInventory.setItem(new DenyItem(), 15);
        }
    }
}
