package fr.elysiumcore.core.listeners;

import fr.elysiumapi.commons.ranks.ElysiumRanks;
import fr.elysiumapi.database.player.PlayerData;
import fr.elysiumcore.core.AndoriaCore;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class PlayerChatListener implements Listener {
    private final AndoriaCore elysiumCore;

    public PlayerChatListener(AndoriaCore elysiumCore) {
        this.elysiumCore = elysiumCore;
    }

    @EventHandler(priority = EventPriority.NORMAL)
    public void onChat(AsyncPlayerChatEvent event){
        Player player = event.getPlayer();
        PlayerData playerData = elysiumCore.getPlayerDataManager().getPlayerData(player.getUniqueId(), player.getName());
        ElysiumRanks rank = playerData.getRankInfos().getRank();
        event.setFormat(rank.getTagId()  + rank.getPrefix() + " " + player.getName() + "§f: " + event.getMessage());
    }
}
