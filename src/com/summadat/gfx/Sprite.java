package com.summadat.gfx;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;

/**
 * Created by Noah on 23-Sep-17.
 */
public class Sprite {

    BufferedImage image;

    public Sprite() {

    }

    public Sprite(String url) {
        try {
            image = ImageIO.read(new File(url));
        } catch (Exception e) {
            System.out.println("Failed to load sprite!");
        }
    }

    public Sprite(BufferedImage i) {
        image = i;
    }

    public BufferedImage getImage() {
        return image;
    }

}
