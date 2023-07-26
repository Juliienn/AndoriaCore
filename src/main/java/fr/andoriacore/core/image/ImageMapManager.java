package fr.andoriacore.core.image;

import com.google.common.collect.Lists;

import java.util.ArrayList;

public class ImageMapManager {

    private ArrayList<ImageMap> imageMaps;

    public ImageMapManager() {

        this.imageMaps = Lists.newArrayList();
    }

    public void addImageMap(ImageMap imageMap){
        this.imageMaps.add(imageMap);
    }
    public void removeImageMap(ImageMap imageMap){
        this.imageMaps.remove(imageMap);
    }

    public ArrayList<ImageMap> getImageMaps() {
        return imageMaps;
    }
}
