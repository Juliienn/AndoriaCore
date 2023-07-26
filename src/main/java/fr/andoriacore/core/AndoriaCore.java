package fr.andoriacore.core;

import fr.andoriaapi.commons.server.ServerState;
import fr.andoriaapi.commons.server.ServerType;
import fr.andoriaapi.database.player.PlayerDataManager;
import fr.andoriaapi.database.redis.JedisConnection;
import fr.andoriaapi.database.redis.JedisConnector;
import fr.andoriaapi.database.sql.DatabaseManager;
import fr.andoriaapi.utils.PlayerUtils;
import fr.andoriacore.core.image.CommandMap;
import fr.andoriacore.core.image.ImageMapManager;
import fr.andoriacore.core.listeners.ItemListeners;
import fr.andoriacore.core.image.DataLoader;
import fr.andoriacore.core.listeners.PlayerChatListener;
import fr.andoriacore.core.listeners.PlayerJoinListener;
import fr.andoriacore.core.grades.command.GradesCommand;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

public class AndoriaCore extends JavaPlugin {

    public static File IMAGES_DIR;
    public static File IMAGES_MAP_DIR;
    public static ImageMapManager IMAGE_MAP_MANAGER;
    private PlayerDataManager playerDataManager;
    private JedisConnector playerConnector;
    private static AndoriaCore instance;

    public void onEnable(){
        ServerState.setServerState(ServerState.STARTING);
        PlayerUtils.andoriaPlayers = new HashMap<>();

        DatabaseManager.initConnection(DatabaseManager.PLAYERS);

        instance = this;

        this.playerConnector = new JedisConnector(new JedisConnection("localhost", 6379,""));
        this.playerDataManager = new PlayerDataManager(playerConnector);

        playerConnector.connect();

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
        ServerState.setServerState(ServerState.WORKING);
    }

    public static AndoriaCore getInstance() {
        return instance;
    }

    public JedisConnector getPlayerConnector() {
        return playerConnector;
    }

    public PlayerDataManager getPlayerDataManager() {
        return playerDataManager;
    }

    public void onDisable(){
        DatabaseManager.closeConnection(DatabaseManager.PLAYERS);
        playerConnector.killConnection();
    }
}
