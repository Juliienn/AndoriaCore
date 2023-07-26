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

public class SamuraiItem extends InventoryItem {

    private final PlayerData playerData;

    public SamuraiItem(PlayerData playerData) {
        super(new ItemStack(Material.DIAMOND_SWORD), AndoriaRanks.SAMURAI.getTagId() + AndoriaRanks.SAMURAI.getPrefix(), playerData.getRankInfos().getRank().getPower() >= 4);
        ArrayList<String> lores = new ArrayList<>();
        lores.add(" ");
        lores.add("§e" + Symbols.STAR + " §7Accés au /kit " + AndoriaRanks.SAMURAI.getTagId() + "Samurai");
        lores.add("§e" + Symbols.STAR + " §7Accés au /near ");
        lores.add("§e" + Symbols.STAR + " §7Accés à 5 sethomes ");
        lores.add("§e" + Symbols.STAR + " §7Accés à 3 banques");
        lores.add("§e" + Symbols.STAR + " §7Gain de 300% d'xp supplémentaire");
        lores.add("§e" + Symbols.STAR + " §7Pas de perte d'exp en mourrant");
        lores.add("§e" + Symbols.STAR + " §7Accés à l'end");
        lores.add("§e" + Symbols.STAR + " §7Avantages du grade précédent");
        lores.add(" ");
        lores.add("§e" + Symbols.ARROW_RIGHT_FULL + " §bPrix: 2000 points VIP");
        if(playerData.getRankInfos().getRank().getPower() >= 4){
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
        if(playerData.getRankInfos().getRank().getPower() <= 4) {
            AndoriaPlayer andoriaPlayer = AndoriaPlayer.getAndoriaPlayer(inventoryClickEvent.getWhoClicked().getUniqueId());
            AndoriaInventory andoriaInventory = andoriaPlayer.getOpenedInventory();
            andoriaInventory.clear();
            andoriaInventory.setItem(new AcceptItem() {
                @Override
                public void onClick(InventoryClickEvent event) {
                    if (event.getWhoClicked() instanceof Player) {
                        event.setCancelled(true);
                        Player player = (Player) event.getWhoClicked();
                        if (playerData.getPbs() >= 2000) {
                            playerData.removePbs(2000);
                            playerData.getRankInfos().setRank(AndoriaRanks.SAMURAI);
                            playerData.getRankInfos().setPurchased_at(new Timestamp(System.currentTimeMillis()));
                            playerData.getRankInfos().setExpirationDate(new Timestamp(System.currentTimeMillis() + (long) 30 * 24 * 60 * 60 * 1000));
                            player.closeInventory();
                            player.sendMessage("§7Vous possédez maintenant le grade: " + AndoriaRanks.SAMURAI.getTagId() + AndoriaRanks.SAMURAI.getPrefix());
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