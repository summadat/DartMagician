package com.summadat.world;

import com.summadat.GamePanel;
import com.summadat.gfx.AnimationLoader;
import com.summadat.gfx.Camera;
import com.summadat.gfx.Location;
import com.summadat.gfx.Sprite;

import java.awt.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;

/**
 * Created by Noah on 23-Sep-17.
 */
public class Map {

    Tile[][] tiles;
    public static int width = 32, height = 32;
    static Sprite[] sprites;

    public static ArrayList<Tile> nonWalkables;

    public String name = "";

    public Map(int w, int h) {
        width = w;
        height = h;
        tiles = new Tile[width][height];
        nonWalkables = new ArrayList<>();
    }

    public void setTile(int x, int y, Tile t) {
        tiles[x][y] = t;
    }

    public Tile tileAt(int x, int y) {
        return tiles[x][y];
    }

    public static Map load(String s) {

        System.out.println("Generating map");

        try {
            sprites = World.loader.loadTiles("res/shee.png");

            Map m;
            BufferedReader reader = new BufferedReader(new FileReader(s));

            String nam = reader.readLine();

            String[] dims = reader.readLine().split(":");

            int wid = Integer.parseInt(dims[0]);
            int hei = Integer.parseInt(dims[1]);

            m = new Map(wid, hei);
            m.name = nam;

            for (int x = 0; x < 64; x++) {
                for (int y = 0; y < 64; y++) {
                    m.setTile(x, y, new Tile(new Location(x, y), AnimationLoader.block));
                }
            }

            String input;
            while ((input = reader.readLine()) != null) {
                if (input.length() > 3) {
                    String[] tiles = input.split("/");
                    for (String str : tiles) {
                        String data[] = str.split(":");
                        int x = Integer.parseInt(data[0]);
                        int y = Integer.parseInt(data[1]);

                        int spriteid = 0;
                        int layer = 0;

                        boolean walkable = true;

                        if (data[2].contains("@")) {
                            spriteid = Integer.parseInt(data[2].split("@")[0]);
                            layer = Integer.parseInt(data[2].split("@")[1]);
                        } else {
                            spriteid = Integer.parseInt(data[2]);
                        }

                        if (data.length > 3) {
                            if (data[3] != null)
                                walkable = data[3].equals("true");
                        }

                        Sprite sprite;
                        Sprite layerimg;

                        if (spriteid != 0) {
                            sprite = sprites[spriteid];
                            if (layer != 0) {
                                layerimg = sprites[layer];
                                m.tileAt(x, y).setLayer(layerimg);
                            }
                        } else
                            sprite = AnimationLoader.block;

                        m.tileAt(x, y).setImage(sprite);
                        m.tileAt(x, y).setWalkable(walkable);

                        if (!walkable) {
                            m.nonWalkables.add(m.tileAt(x, y));
                        }

                    }
                } else {
                    break;
                }
            }
            return m;
        } catch (Exception e) {
            System.out.println("Failed to load map!");
            e.printStackTrace();
        }
        return null;
    }

    public void draw(Graphics2D graphics, Camera camera) {

        int cameraX = camera.getLocation().getX() / 32;
        int cameraY = camera.getLocation().getY() / 32;

        int startX = -32 + cameraX - (GamePanel.WIDTH / 64);
        int startY = -32 + cameraY - (GamePanel.HEIGHT / 64);

        int endX = 32 + cameraX + GamePanel.WIDTH / 64;
        int endY = 32 + cameraY + GamePanel.HEIGHT / 64;

        for (int x = startX; x < endX; x++) {
            for (int y = startY; y < endY; y++) {
                if (x >= 0 && x < width && y >= 0 && y < height)
                    tiles[x][y].draw(graphics, camera);
            }
        }
    }

}
