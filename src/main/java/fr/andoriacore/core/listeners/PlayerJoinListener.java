package fr.elysiumcore.core.listeners;

import fr.elysiumapi.spigot.player.ElysiumPlayer;
import fr.elysiumapi.utils.PlayerUtils;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerJoinListener implements Listener {

    @EventHandler(priority = EventPriority.NORMAL)
    public void onJoin(PlayerJoinEvent event){
        Player player = event.getPlayer();
        if(!(PlayerUtils.elysiumPlayer.containsKey(player.getUniqueId()))){
            PlayerUtils.elysiumPlayer.put(player.getUniqueId(), new ElysiumPlayer(player) {});
        }
        event.setJoinMessage(null);
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event){
        Player player = event.getPlayer();
        PlayerUtils.elysiumPlayer.remove(player.getUniqueId());
        event.setQuitMessage(null);
    }
}
