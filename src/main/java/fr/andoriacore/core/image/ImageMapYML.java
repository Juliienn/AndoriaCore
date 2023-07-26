package fr.andoriacore.core.image;

import fr.andoriacore.core.AndoriaCore;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.UUID;

public class ImageMapYML {

    private UUID imageUUID;
    private File configFile;
    private YamlConfiguration yamlConfiguration;

    public ImageMapYML(UUID imageUUID){
        this.imageUUID = imageUUID;
        this.configFile = new File(AndoriaCore.IMAGES_MAP_DIR, imageUUID.toString() + ".yml");
        this.yamlConfiguration = YamlConfiguration.loadConfiguration(configFile);
    }

    public void write(ImageMap imageMap){
        final ConfigurationSection config = this.yamlConfiguration.createSection("image");

        config.set("uuid", imageMap.getUuid().toString());
        config.set("path", imageMap.getPath());
        config.set("ids", imageMap.getMapIds());
        save();
    }

    public ImageMap read(){
        final ConfigurationSection configurationSection = this.yamlConfiguration.getConfigurationSection("image");
        final String uuid = configurationSection.getString("uuid");
        final String path = configurationSection.getString("path");
        final ArrayList<Short> ids = (ArrayList<Short>) configurationSection.getShortList("ids");
        return new ImageMap(UUID.fromString(uuid), path, ids);
    }

    public void save(){
        try{
            yamlConfiguration.save(configFile);
        }catch(IOException e){
            System.err.println("Cannot save image map config file: " + imageUUID.toString());
        }
    }
}
