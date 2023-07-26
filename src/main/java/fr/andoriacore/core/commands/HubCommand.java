package fr.elysiumcore.core.commands;

import fr.elysiumapi.database.player.PlayerDataManager;
import fr.elysiumapi.spigot.commands.ElysiumCommand;
import fr.elysiumapi.spigot.player.ElysiumPlayer;
import org.bukkit.entity.Player;

public class HubCommand extends ElysiumCommand {
    public HubCommand(PlayerDataManager playerDataManager) {
        super(playerDataManager, 0, "hub");
    }

    @Override
    public void execute(ElysiumPlayer player, String[] args) {
        player.connect("lobby");
    }

    @Override
    public void execute(Player player, String[] args) {

    }
}
