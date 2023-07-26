package fr.elysiumcore.core;

import fr.elysiumapi.commons.server.ServerType;
import fr.elysiumapi.database.player.PlayerDataManager;
import fr.elysiumapi.database.redis.JedisConnection;
import fr.elysiumapi.database.redis.JedisConnector;
import fr.elysiumapi.database.sql.DatabaseManager;
import fr.elysiumapi.utils.PlayerUtils;
import fr.elysiumcore.core.listeners.ItemListeners;
import fr.elysiumcore.core.image.CommandMap;
import fr.elysiumcore.core.image.DataLoader;
import fr.elysiumcore.core.image.ImageMapManager;
import fr.elysiumcore.core.listeners.PlayerChatListener;
import fr.elysiumcore.core.listeners.PlayerJoinListener;
import fr.elysiumcore.core.grades.command.GradesCommand;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

public class AndoriaCore extends JavaPlugin {

    public static File IMAGES_DIR;
    public static File IMAGES_MAP_DIR;
    public static ImageMapManager IMAGE_MAP_MANAGER;
    private PlayerDataManager playerDataManager;
    private JedisConnector jedisConnector;
    private static AndoriaCore instance;

    public void onEnable(){

        PlayerUtils.elysiumPlayer = new HashMap<>();

        DatabaseManager.initConnection(DatabaseManager.PLAYERS);

        instance = this;
        
        this.jedisConnector = new JedisConnector(new JedisConnection("localhost", 6379,""));
        this.playerDataManager = new PlayerDataManager(jedisConnector);

        jedisConnector.connect();

        getServer().getPluginManager().registerEvents(new PlayerJoinListener(), this);
        getServer().getPluginManager().registerEvents(new ItemListeners(), this);
        getServer().getPluginManager().registerEvents(new PlayerChatListener(this), this);

        getCommand("grades").setExecutor(new GradesCommand(playerDataManager));
        getCommand("map").setExecutor(new CommandMap(playerDataManager));

        IMAGES_DIR = new File(getDataFolder(), "images");
        IMAGES_MAP_DIR = new File(getDataFolder(), "maps");
        IMAGE_MAP_MANAGER = new ImageMapManager();

        ServerType.setServerType(ServerType.LOBBY);

        try {
            DataLoader.loadMaps();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static AndoriaCore getInstance() {
        return instance;
    }

    public JedisConnector getJedisConnector() {
        return jedisConnector;
    }

    public PlayerDataManager getPlayerDataManager() {
        return playerDataManager;
    }

    public void onDisable(){
        DatabaseManager.closeConnection(DatabaseManager.PLAYERS);
        jedisConnector.killConnection();
    }
}
