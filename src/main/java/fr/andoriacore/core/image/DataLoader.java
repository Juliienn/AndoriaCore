package fr.andoriacore.core.image;

import fr.andoriacore.core.AndoriaCore;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

public class DataLoader {

    public static void loadMaps() throws IOException{
        final File imageDir = AndoriaCore.IMAGES_DIR;
        final File imageMapDir = AndoriaCore.IMAGES_MAP_DIR;

        if(!(imageDir.exists())){
            if(!imageDir.mkdirs()){
                throw new IOException("Cannot create images directories");
            }
        }
        if(!(imageMapDir.exists())){
            if(!imageMapDir.mkdirs()){
                throw new IOException("Cannot create images directories");
            }
        }

        final File[] files = imageMapDir.listFiles();
        if(files != null){
            ImageMap imageMap;
            ImageMapYML imageMapYML;

            for(File file : files){
                if(file.getName().endsWith(".yml")){
                    imageMapYML = new ImageMapYML(UUID.fromString(file.getName().replaceAll(".yml", "")));
                    imageMap = imageMapYML.read();

                    AndoriaCore.IMAGE_MAP_MANAGER.addImageMap(imageMap);
                    new UpdateImageTask(imageMap).runTaskAsynchronously(AndoriaCore.getInstance());
                }
            }
        }
    }
}
