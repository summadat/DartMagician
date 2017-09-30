package com.summadat.containers;

import com.summadat.gfx.Camera;
import com.summadat.world.Entity;
import com.summadat.world.World;

import java.awt.*;
import java.util.ArrayList;

/**
 * Created by Noah on 23-Sep-17.
 */
public class EntityContainer {

    public ArrayList<Entity> entities;
    public static final int MAX_ENTITIES = 256 * 100;
    public int num = 0;

    public EntityContainer() {

        entities = new ArrayList<>();
    }

    public void add(EntityContainer e) {
        for (Entity entity : e.entities) {
            add(entity);
        }
    }

    public void draw(Graphics2D graphics, Camera camera) {
        for (Entity e : entities) {
            e.draw(graphics, camera);
        }
    }

    public void add(Entity e) {
        entities.add(e);
        num++;
    }

    public void remove(Entity e) {
        entities.remove(e.id);
        num--;
    }

    public void update() {
        for (Entity e : entities) {
            if (e.id != World.playerID) {
                if (Math.random() * 90 > 89) {
                    e.setSpeedX((int) (Math.random() * 3));
                    e.setSpeedY((int) (Math.random() * 3));
                } else if (Math.random() * 90 < 1) {
                    e.setSpeedX((int) (Math.random() * -3));
                    e.setSpeedY((int) (Math.random() * -3));
                }
            }
            e.update();
        }
    }

    public Entity get(int id) {
        return entities.get(id);
    }

}
