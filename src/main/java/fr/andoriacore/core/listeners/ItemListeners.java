package fr.andoriacore.core.listeners;

import fr.andoriaapi.spigot.items.InventoryItem;
import fr.andoriaapi.spigot.items.ItemBuilder;
import fr.andoriaapi.spigot.player.AndoriaPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;

import java.util.List;

public class ItemListeners implements Listener {

    @EventHandler
    public void onInteract(PlayerInteractEvent event){
        Player player = event.getPlayer();
        AndoriaPlayer andoriaPlayer = AndoriaPlayer.getAndoriaPlayer(player.getUniqueId());
        for(ItemBuilder items : andoriaPlayer.getItems()){
            if(items.getItem().equals(event.getItem())){
                items.action(player);
                items.action(andoriaPlayer);
                break;
            }
        }
    }

    @EventHandler
    public void onInteractEntity(PlayerInteractEntityEvent event){
        Player player = event.getPlayer();
        AndoriaPlayer andoriaPlayer = AndoriaPlayer.getAndoriaPlayer(player.getUniqueId());
        for(ItemBuilder items : andoriaPlayer.getItems()){
            if(items.getItem().equals(event.getPlayer().getItemInHand())){
                if(event.getRightClicked() instanceof Player){
                    items.actionTarget(player, (Player) event.getRightClicked());
                    break;
                }
                items.actionEntity(player, event.getRightClicked());
            }
        }
    }

    @EventHandler
    public void onClick(InventoryClickEvent event){
        Player player = (Player) event.getWhoClicked();
        AndoriaPlayer andoriaPlayer = AndoriaPlayer.getAndoriaPlayer(player.getUniqueId());
        if(andoriaPlayer.getOpenedInventory() != null && andoriaPlayer.getOpenedInventory().getInventory().equals(event.getInventory())){
            List<InventoryItem> items = andoriaPlayer.getOpenedInventory().getItems();
            for(InventoryItem item : items){
                if(event.getCurrentItem() != null && event.getCurrentItem().equals(item.getItem())){
                    item.onClick(event);
                    break;
                }
            }
        }
    }
    @EventHandler
    public void onClose(InventoryCloseEvent event){
        if(event.getPlayer() instanceof Player){
            Player player = (Player) event.getPlayer();
            AndoriaPlayer andoriaPlayer = AndoriaPlayer.getAndoriaPlayer(player.getUniqueId());
            andoriaPlayer.setOpenedInventory(null);
        }
    }
}