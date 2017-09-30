package com.summadat.gfx;

import com.summadat.GamePanel;
import com.summadat.containers.EntityContainer;
import com.summadat.world.Entity;
import com.summadat.world.Map;
import com.summadat.world.Tile;
import com.summadat.world.World;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

/**
 * Created by Noah on 24-Sep-17.
 */
public class AnimationLoader {

    public static Sprite block;

    public AnimationLoader() {
        try {
        } catch(Exception e) {
            System.out.println("failed to load black block!");
        }
    }


    public EntityContainer loadAnimations(String url) {
        Animation[] sprites;
        EntityContainer entities = new EntityContainer();

        int num = 1;

        try {
            BufferedImage image = ImageIO.read(new File(url));

            int numX = (image.getWidth() / 32) / 3;
            int numY = (image.getHeight() / 32) / 4;

            BufferedImage i;
            BufferedImage[] frames;

            for (int y = 0; y < numY; y++) {

                for (int x = 0; x < numX; x++) {

                    sprites = new Animation[numX];

                    i = image.getSubimage(x * 32 * 3, y * 32 * 4, 32 * 3, 32 * 4);

                    for (int a = 0; a < 4; a++) {

                        frames = new BufferedImage[3];
                        int offset = 0;

                        for (int b = 0; b < 3; b++) {
                            frames[offset++] = i.getSubimage(b * 32, a * 32, 32, 32);
                        }
                        sprites[a] = new Animation(frames, 0.3);
                    }

                    if (sprites != null) {
                    } else {
                        System.out.println("fukk");
                    }
                        Entity e = new Entity(sprites, new Location(((int)(Math.random() * Map.width) * 32), ((int)(Math.random() * Map.height) * 32)));
                        entities.add(e);
                        num++;
                }
            }
            System.out.println("loaded " + num);
            return entities;
        } catch (Exception e) {
            System.out.println("Failed to load sprites: " + url);
            e.printStackTrace();
        }
        return null;
    }

    public Sprite[] loadTiles(String s) {
        Sprite[] sprites;
        try {

            BufferedImage image = ImageIO.read(new File(s));

            int numX = (image.getWidth() / 32);
            int numY = (image.getHeight() / 32);

            sprites = new Sprite[numX * numY + 1];

            int num = 1;

            block = new Sprite(ImageIO.read(new File("res/block.png")));
            sprites[0] = block;

            for (int x = 0; x < numX; x++) {
                for (int y = 0; y < numY; y++) {
                    sprites[num++] = new Sprite(image.getSubimage(x * 32, y * 32, 32, 32));
                }
            }
            return sprites;
        } catch (Exception e) {
            System.out.println("Failed to load sprites: " + s);
            e.printStackTrace();
        }
        return null;
    }

}
