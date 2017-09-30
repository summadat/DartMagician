package com.summadat.gfx;

/**
 * Created by Noah on 23-Sep-17.
 */
public class Location {

    private int x, y;

    public Location(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public Location translate(int x, int y) {
        this.x += x;
        this.y += y;

        return this;
    }

    public void set(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public boolean isWithin(int x1, int y1, int x2, int y2) {
        if (x > x1 && x < x2 && y > y1 && y < y2)
            return true;

        return false;
    }
}
