package fr.elysiumcore.core.image;

import org.bukkit.Bukkit;
import org.bukkit.map.MapView;
import org.bukkit.scheduler.BukkitRunnable;

import java.awt.image.BufferedImage;
import java.io.IOException;

public class UpdateImageTask extends BukkitRunnable {

    private ImageMap imageMap;

    public UpdateImageTask(ImageMap imageMap){
        this.imageMap = imageMap;
    }

    @Override
    public void run() {
        try {
            final BufferedImage image = ImageHelper.getImage(imageMap.getPath());
            final int row = image.getHeight()/128;
            final int col = image.getWidth()/128;
            MapView map;
            int index = 0;
            for (int i = 0; i < row; i++){
                for(int j = 0; j < col; j++){
                    map = Bukkit.getMap(imageMap.getMapIds().get(index));
                    map = RenderHelper.resetRenderers(map);
                    map.setScale(MapView.Scale.FARTHEST);
                    map.addRenderer(new ImageMapRenderer(image.getSubimage(j*128, i*128, 128, 128)));

                    index++;
                }
            }

        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }
}
