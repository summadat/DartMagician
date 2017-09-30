package com.summadat.world;

import com.summadat.GamePanel;
import com.summadat.containers.Collision;
import com.summadat.gfx.Animation;
import com.summadat.gfx.Camera;
import com.summadat.gfx.Location;

import java.awt.*;

/**
 * Created by Noah on 23-Sep-17.
 */
public class Entity {

    Animation[] sprites;
    Location location;

    public static final int UP = 0, DOWN = 3, LEFT = 1, RIGHT = 2;
    private int direction = UP;

    private int speedX = 0;
    private int speedY = 0;
    public static int NUM = 0;
    public int id = 0;
    boolean col = false;

    public Entity(Animation[] s) {
        this(s, new Location(200, 200));

    }

    public Entity(Animation[] s, Location loc) {
        location = loc;
        sprites = s;
        id = NUM++;
    }

    public void setAnimation(Animation[] s) {
        sprites = s;
    }
    public Animation[] getAnimations() {
        return sprites;
    }

    public void setLocation(Location l) {
        location = l;
    }

    public int calculateX(Camera c) {
        return location.getX() - c.getLocation().getX() + (GamePanel.WIDTH / 2);
    }

    public int calculateY(Camera c) {
        return location.getY() - c.getLocation().getY() + (GamePanel.HEIGHT / 2);
    }

    public Location getLocation() {
        return location;
    }

    public void setSpeed(int x, int y) {
        speedX = x;
        speedY = y;
    }

    public void setSpeedX(int speed) {
        this.speedX = speed;
    }

    public void setSpeedY(int speed) {
        this.speedY = speed;
    }

    public void update() {

        Collision c = new Collision(location.getX() + speedX, location.getX() + speedX + 32, location.getY() + speedY, location.getY() + speedY + 32);


        for (Tile t : World.map.nonWalkables) {
            if (c.isCollision(t.border)) {
                col = true;
                break;
            } else {
                col = false;
            }
        }

        if (speedX != 0 || speedY != 0) {

            sprites[direction].unpause();

            if (speedY > 0) {

                direction = UP;
                speedX = 0;

            } else if (speedY < 0) {

                direction = DOWN;
                speedX = 0;

            } else if (speedX > 0) {

                direction = RIGHT;
                speedY = 0;

            } else if (speedX < 0) {

                direction = LEFT;
                speedY = 0;
            }

            if (col) {
                speedX = 0;
                speedY = 0;
                sprites[direction].pause();
            }

            if (location.getX() + speedX > (Map.width - 1) * 32) {
                speedX = (Map.width - 1) * 32 - location.getX();
            } else if (location.getX() + speedX < 0) {
                speedX = -location.getX();
            }

            if (location.getY() + speedY > (Map.height - 1) * 32) {
                speedY = (Map.height - 1) * 32 - location.getY();
            } else if (location.getY() + speedY < 0) {
                speedY = -location.getY();
            }

            location.translate(speedX, speedY);
        } else {
            sprites[direction].pause();
        }
    }

    public void draw(Graphics2D graphics, Camera camera) {
        /* if (col) {
            graphics.setColor(Color.CYAN);
            graphics.fillRect(calculateX(camera), calculateY(camera), 32, 32);
        } */
        graphics.drawImage(sprites[direction].getImage(), calculateX(camera), calculateY(camera), null);
    }

}
