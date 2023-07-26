package fr.elysiumcore.core.grades.command;

import fr.elysiumapi.database.player.PlayerDataManager;
import fr.elysiumapi.spigot.commands.ElysiumCommand;
import fr.elysiumapi.spigot.player.ElysiumPlayer;
import fr.elysiumcore.core.grades.inventories.GradesInventory;
import org.bukkit.entity.Player;

public class GradesCommand extends ElysiumCommand {


    public GradesCommand(PlayerDataManager playerDataManager) {
        super(playerDataManager, 0, "grades");
    }

    @Override
    public void execute(ElysiumPlayer elysiumPlayer, String[] strings) {
        elysiumPlayer.openInventory(new GradesInventory());
    }

    @Override
    public void execute(Player player, String[] args) {

    }
}