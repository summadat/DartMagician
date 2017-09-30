package com.summadat.world;

import com.summadat.GamePanel;
import com.summadat.containers.Collision;
import com.summadat.gfx.Camera;
import com.summadat.gfx.Location;
import com.summadat.gfx.Sprite;

import java.awt.*;

/**
 * Created by Noah on 23-Sep-17.
 */
public class Tile {

    Location loc;
    Sprite image;
    Sprite layer;

    boolean hasLayer = false;

    public boolean walkable = true;
    public Collision border;

    public Tile(Location loc, Sprite img) {
        this.loc = loc;
        this.image = img;
        border = new Collision(loc.getX() * 32, (loc.getX() + 1) * 32, loc.getY() * 32, (loc.getY() + 1) * 32);
    }

    public Location getLoc() {
        return loc;
    }

    public void setWalkable(boolean b) {
        walkable = b;
    }

    public void setLayer(Sprite s) {
        layer = s;
        hasLayer = true;
    }

    public void removeLayer() {
        hasLayer = false;
    }

    public void setImage(Sprite s) {
        image = s;
    }

    public int calculateX(Camera c) {
        return (loc.getX() * 32) - c.getLocation().getX() + (GamePanel.WIDTH / 2);
    }

    public int calculateY(Camera c) {
        return (loc.getY() * 32) - c.getLocation().getY() + (GamePanel.HEIGHT / 2);
    }

    public void draw(Graphics2D graphics, Camera camera) {
        graphics.drawImage(image.getImage(), calculateX(camera), calculateY(camera), null);

        if (hasLayer) {
            graphics.drawImage(layer.getImage(), calculateX(camera), calculateY(camera), null);
        }
    }
}
