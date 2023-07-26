package fr.andoriacore.core.image;

import fr.andoriaapi.database.player.PlayerDataManager;
import fr.andoriaapi.spigot.commands.AndoriaCommand;
import fr.andoriaapi.spigot.player.AndoriaPlayer;
import fr.andoriacore.core.AndoriaCore;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class CommandMap extends AndoriaCommand {
    public CommandMap(PlayerDataManager playerDataManager) {
        super(playerDataManager, 10, "map");
    }

    @Override
    public void execute(AndoriaPlayer player, String[] args) {
        if(args.length == 2){
            if(args[0].equalsIgnoreCase("render")){
                final String path = args[1];
                new ImageTaskRender(Bukkit.getPlayer(player.getUUID()), path).runTaskAsynchronously(AndoriaCore.getInstance());
            }
        }else{
            player.sendMessage("§c/map render <lien|url>");
        }
    }

    @Override
    public void execute(Player player, String[] args) {

    }
}
