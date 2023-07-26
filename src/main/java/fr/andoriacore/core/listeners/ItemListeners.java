package fr.elysiumcore.core.listeners;

import fr.elysiumapi.spigot.items.InventoryItem;
import fr.elysiumapi.spigot.items.ItemBuilder;
import fr.elysiumapi.spigot.player.ElysiumPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ItemListeners implements Listener {

    @EventHandler
    public void onInteract(PlayerInteractEvent event){
        Player player = event.getPlayer();
        ElysiumPlayer elysiumPlayer = ElysiumPlayer.getIElysiumPlayer(player.getUniqueId());
        for(ItemBuilder items : elysiumPlayer.getItems()){
            if(items.getItem().equals(event.getItem())){
                items.action(player);
                items.action(ElysiumPlayer.getIElysiumPlayer(player.getUniqueId()));
                break;
            }
        }
    }

    @EventHandler
    public void onInteractEntity(PlayerInteractEntityEvent event){
        Player player = event.getPlayer();
        ElysiumPlayer elysiumPlayer = ElysiumPlayer.getIElysiumPlayer(player.getUniqueId());
        for(ItemBuilder items : elysiumPlayer.getItems()){
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
        ElysiumPlayer elysiumPlayer = ElysiumPlayer.getIElysiumPlayer(player.getUniqueId());
        if(elysiumPlayer.getOpenedInventory() != null && elysiumPlayer.getOpenedInventory().getInventory().equals(event.getInventory())){
            List<InventoryItem> items = elysiumPlayer.getOpenedInventory().getItems();
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
            ElysiumPlayer elysiumPlayer = ElysiumPlayer.getIElysiumPlayer(player.getUniqueId());
            elysiumPlayer.setOpenedInventory(null);
        }
    }
}