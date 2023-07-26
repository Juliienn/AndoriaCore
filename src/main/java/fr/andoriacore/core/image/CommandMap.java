package fr.elysiumcore.core.image;

import fr.elysiumapi.database.player.PlayerDataManager;
import fr.elysiumapi.spigot.commands.ElysiumCommand;
import fr.elysiumapi.spigot.player.ElysiumPlayer;
import fr.elysiumcore.core.AndoriaCore;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class CommandMap extends ElysiumCommand {
    public CommandMap(PlayerDataManager playerDataManager) {
        super(playerDataManager, 10, "map");
    }

    @Override
    public void execute(ElysiumPlayer player, String[] args) {
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
