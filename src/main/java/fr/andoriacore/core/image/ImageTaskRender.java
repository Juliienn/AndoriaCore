package fr.elysiumcore.core.image;

import com.google.common.collect.Lists;
import fr.elysiumcore.core.AndoriaCore;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.map.MapView;
import org.bukkit.scheduler.BukkitRunnable;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.UUID;

public class ImageTaskRender extends BukkitRunnable {

    private Player player;
    private String path;

    public ImageTaskRender(Player player, String path){
        this.player = player;
        this.path = path;
    }

    @Override
    public void run() {
        try {
            final ArrayList<Short> mapIds = Lists.newArrayList();
            final BufferedImage image = ImageHelper.getImage(path);
            final int row = image.getHeight()/128;
            final int col = image.getWidth()/128;
            MapView map;
            for (int i = 0; i < row; i++){
                for(int j = 0; j < col; j++){
                    map = Bukkit.createMap(player.getWorld());
                    map = RenderHelper.resetRenderers(map);
                    map.setScale(MapView.Scale.FARTHEST);
                    map.addRenderer(new ImageMapRenderer(image.getSubimage(j*128, i*128, 128, 128)));

                    mapIds.add(map.getId());
                }
            }

            for(short id : mapIds){
                player.getInventory().addItem(new ItemStack(Material.MAP, 1, id));
            }

            final ImageMap imageMap = new ImageMap(UUID.randomUUID(), path, mapIds);
            final ImageMapYML imageMapYML = new ImageMapYML(imageMap.getUuid());
            imageMapYML.write(imageMap);
            AndoriaCore.IMAGE_MAP_MANAGER.addImageMap(imageMap);

            player.sendMessage("§2Rendu terminé");

        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }
}
