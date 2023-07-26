package fr.andoriacore.core.grades.command;

import fr.andoriaapi.database.player.PlayerDataManager;
import fr.andoriaapi.spigot.commands.AndoriaCommand;
import fr.andoriaapi.spigot.player.AndoriaPlayer;
import fr.andoriacore.core.grades.inventories.GradesInventory;
import org.bukkit.entity.Player;

public class GradesCommand extends AndoriaCommand {


    public GradesCommand(PlayerDataManager playerDataManager) {
        super(playerDataManager, 0, "grades");
    }

    @Override
    public void execute(AndoriaPlayer elysiumPlayer, String[] strings) {
        elysiumPlayer.openInventory(new GradesInventory());
    }

    @Override
    public void execute(Player player, String[] args) {

    }
}