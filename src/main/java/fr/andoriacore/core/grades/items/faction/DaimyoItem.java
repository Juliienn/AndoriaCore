package fr.elysiumcore.core.grades.items.faction;

import fr.elysiumapi.commons.Symbols;
import fr.elysiumapi.commons.ranks.ElysiumRanks;
import fr.elysiumapi.database.player.PlayerData;
import fr.elysiumapi.spigot.inventories.ElysiumInventory;
import fr.elysiumapi.spigot.items.InventoryItem;
import fr.elysiumapi.spigot.player.ElysiumPlayer;
import fr.elysiumcore.core.AndoriaCore;
import fr.elysiumcore.core.grades.shop.AcceptItem;
import fr.elysiumcore.core.grades.shop.DenyItem;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import java.sql.Timestamp;
import java.util.ArrayList;

public class DaimyoItem extends InventoryItem {

    private final PlayerData playerData;

    public DaimyoItem(PlayerData playerData) {
        super(new ItemStack(Material.STONE_SWORD), ElysiumRanks.DAIMYO.getTagId() + ElysiumRanks.DAIMYO.getPrefix(), playerData.getRankInfos().getRank().getPower() >= 2);
        ArrayList<String> lores = new ArrayList<>();
        lores.add(" ");
        lores.add("§e" + Symbols.STAR + " §7Accés au /kit " + ElysiumRanks.DAIMYO.getTagId() + "Daimyo");
        lores.add("§e" + Symbols.STAR + " §7Accés au /back ");
        lores.add("§e" + Symbols.STAR + " §7Accés à 3 sethomes ");
        lores.add("§e" + Symbols.STAR + " §7Accés à 1 banque");
        lores.add("§e" + Symbols.STAR + " §7Gain de 100% d'xp supplémentaire");
        lores.add("§e" + Symbols.STAR + " §7Accés à la farmzone catégorie 1");
        lores.add("§e" + Symbols.STAR + " §7Avantages du grade précédent");
        lores.add(" ");
        lores.add("§e" + Symbols.ARROW_RIGHT_FULL + " §bPrix: 1000 points VIP");
        if(playerData.getRankInfos().getRank().getPower() >= 2){
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
        if(playerData.getRankInfos().getRank().getPower() <= 2) {
            ElysiumPlayer elysiumPlayer = ElysiumPlayer.getIElysiumPlayer(inventoryClickEvent.getWhoClicked().getUniqueId());
            ElysiumInventory elysiumInventory = elysiumPlayer.getOpenedInventory();
            elysiumInventory.clear();
            elysiumInventory.setItem(new AcceptItem() {
                @Override
                public void onClick(InventoryClickEvent event) {
                    if (event.getWhoClicked() instanceof Player) {
                        event.setCancelled(true);
                        Player player = (Player) event.getWhoClicked();
                        if (playerData.getVIP() >= 1000) {
                            playerData.removeVIP(1000);
                            playerData.getRankInfos().setRank(ElysiumRanks.DAIMYO);
                            playerData.getRankInfos().setPurchased_at(new Timestamp(System.currentTimeMillis()));
                            playerData.getRankInfos().setExpirationDate(new Timestamp(System.currentTimeMillis() + (long) 30 * 24 * 60 * 60 * 1000));
                            player.closeInventory();
                            player.sendMessage("§7Vous possédez maintenant le grade: " + ElysiumRanks.DAIMYO.getTagId() + ElysiumRanks.DAIMYO.getPrefix());
                        } else {
                            player.closeInventory();
                            player.sendMessage("§cVous n'avez pas assez de points VIP pour acheter ce grade, pour en acheter: /vip");
                            return;
                        }
                        AndoriaCore.getInstance().getPlayerDataManager().sendPlayerDataToRedis(playerData);
                    }
                }
            }, 11);
            elysiumInventory.setItem(new DenyItem(), 15);
        }
    }
}
