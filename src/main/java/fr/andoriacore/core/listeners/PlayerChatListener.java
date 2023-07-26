package fr.andoriacore.core.listeners;

import fr.andoriaapi.commons.ranks.AndoriaRanks;
import fr.andoriaapi.database.player.PlayerData;
import fr.andoriacore.core.AndoriaCore;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class PlayerChatListener implements Listener {
    private final AndoriaCore andoriaCore;

    public PlayerChatListener(AndoriaCore andoriaCore) {
        this.andoriaCore = andoriaCore;
    }

    @EventHandler(priority = EventPriority.NORMAL)
    public void onChat(AsyncPlayerChatEvent event){
        Player player = event.getPlayer();
        PlayerData playerData = andoriaCore.getPlayerDataManager().getPlayerData(player.getUniqueId(), player.getName());
        AndoriaRanks rank = playerData.getRankInfos().getRank();
        event.setFormat(rank.getTagId()  + rank.getPrefix() + " " + player.getName() + "§f: " + event.getMessage());
    }
}
