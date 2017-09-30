package com.summadat.gfx;

/**
 * Created by Noah on 23-Sep-17.
 */
public class Camera {

    private Location location;

    public Camera() {
        this(new Location(200,200));
    }

    public Camera(Location location) {
        this.location = location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(int x, int y) {
        location.set(x, y);
    }

    public void move(int x, int y) {
        location.translate(x, y);
    }

}

