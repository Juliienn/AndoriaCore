package fr.andoriacore.core.commands;

import fr.andoriaapi.database.player.PlayerDataManager;
import fr.andoriaapi.spigot.commands.AndoriaCommand;
import fr.andoriaapi.spigot.player.AndoriaPlayer;
import org.bukkit.entity.Player;

public class HubCommand extends AndoriaCommand {
    public HubCommand(PlayerDataManager playerDataManager) {
        super(playerDataManager, 0, "hub");
    }

    @Override
    public void execute(AndoriaPlayer player, String[] args) {
        player.connect("lobby");
    }

    @Override
    public void execute(Player player, String[] args) {

    }
}
