package com.summadat.containers;

/**
 * Created by Noah on 29-Sep-17.
 */
public class Collision {

    public int x1, x2, y1, y2;

    public Collision(int x1, int x2, int y1, int y2) {
        this.x1 = x1;
        this.x2 = x2;
        this.y1 = y1;
        this.y2 = y2;
    }

    public boolean isCollision(Collision c) {
        if (c.x1 >= x1 && c.x1 <= x2 || c.x2 >= x1 && c.x2 <= x2) {
            if (c.y1 >= y1 && c.y1 <= y2 || c.y2 >= y1 && c.y2 <= y2) {
                return true;
            }
        }
        return false;
    }

}
