package com.summadat.world;

import com.summadat.GamePanel;
import com.summadat.containers.EntityContainer;
import com.summadat.gfx.AnimationLoader;
import com.summadat.gfx.Camera;
import com.summadat.gfx.Sprite;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;

/**
 * Created by Noah on 23-Sep-17.
 */
public class World {

    EntityContainer entities;
    Entity player;
    Sprite[] sprites;
    public static Map map;
    public static int playerID = 1;
    public static final AnimationLoader loader = new AnimationLoader();

    public void draw(Graphics2D graphics, Camera camera) {
        graphics.fillRect(0, 0, GamePanel.WIDTH, GamePanel.HEIGHT);
        map.draw(graphics, camera);
        entities.draw(graphics, camera);
        if (player != null)
            player.draw(graphics, camera);
    }

    public World() {
        entities = loader.loadAnimations("res/chars1.png");


        entities.add(loader.loadAnimations("res/chars2.png"));
        entities.add(loader.loadAnimations("res/chars3.png"));
        entities.add(loader.loadAnimations("res/chars4.png"));
        entities.add(loader.loadAnimations("res/chars5.png"));

        entities.add(loader.loadAnimations("res/Butterflies.png"));
        entities.add(loader.loadAnimations("res/animals.png"));
        entities.add(loader.loadAnimations("res/ducks.png"));
        entities.add(loader.loadAnimations("res/camels.png"));
        entities.add(loader.loadAnimations("res/horses.png"));
        entities.add(loader.loadAnimations("res/doggos.png"));
        entities.add(loader.loadAnimations("res/knights.png"));


        System.out.println(entities.num);

        newPlayer();

    }

    public void generateMap() {
        map = Map.load("maps/default.map");
    }

    public Entity newPlayer() {
        playerID = (int)(Math.random() * entities.num);
        System.out.println("playerID = " + playerID);
        return entities.get(playerID);
    }

    public Entity getPlayer() {
       /* if (player != null)
            return player;

        return newPlayer(); */
        return entities.get(playerID);
    }

    public EntityContainer getEntities() {
        return entities;
    }

    public void update() {
        getPlayer().update();
        entities.update();
    }

    public void loadTiles(String s) {
        try {
            BufferedImage image = ImageIO.read(new File(s));

            int numX = (image.getWidth() / 32);
            int numY = (image.getHeight() / 32);

            sprites = new Sprite[numX * numY];


            int num = 0;

            for (int x = 0; x < numX; x++) {
                for (int y = 0; y < numY; y++) {
                    sprites[num++] = new Sprite(image.getSubimage(x * 32, y * 32, 32, 32));
                }
            }

        } catch (Exception e) {
            System.out.println("Failed to load sprites: " + s);
            e.printStackTrace();
        }
    }

}
