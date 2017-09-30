package com.summadat;

import com.summadat.containers.EntityContainer;
import com.summadat.gfx.Camera;
import com.summadat.gfx.Location;
import com.summadat.world.Entity;
import com.summadat.world.World;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.FileReader;

/**
 * Created by Noah on 19-Sep-17.
 */
public class GamePanel extends JPanel implements Runnable, KeyListener, MouseListener {

    public static final int WIDTH = 640;
    public static final int HEIGHT = 480;

    private Camera camera;

    private Thread thread;
    private boolean running;
    public static  final int MAXFPS = 60;
    private final double TARGET_TIME = 1.0 / MAXFPS;

    private BufferedImage image;
    private Graphics2D g;

    private World world;

    public GamePanel() {
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        setFocusable(true);
        requestFocus();
    }

    private void init() {
        running = true;
        image = new BufferedImage(WIDTH, HEIGHT, 1);
        g = (Graphics2D) image.getGraphics();
        camera = new Camera(new Location(0, 0));
        world = new World();
        world.loadTiles("res/sheet.png");
        world.generateMap();
        Entity.NUM = 0;
        world.liveEntities = loadEntities("entities/_entities-default_7.enf");
        world.newPlayer();
    }

    public EntityContainer loadEntities(String s) {

        EntityContainer cont = new EntityContainer();
        try {

            BufferedReader reader = new BufferedReader(new FileReader(s));

            String nam = reader.readLine();

            String in;

            while ((in = reader.readLine()) != "EOF") {
                System.out.println(in);

                String[] data = in.split(":");

                int x = Integer.parseInt(data[0]);
                int y = Integer.parseInt(data[1]);
                int id = 0;
                id = Integer.parseInt(data[2]);

                if (id != 0) {
                    Entity e = new Entity(world.getEntities().get(id).getAnimations());
                    e.setLocation(new Location(x * 32, y * 32));

                    cont.add(e);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return cont;
    }

    public void start() {
        thread = new Thread(this);
        thread.run();
    }

    public void stop() {
        running = false;
    }

    public void run() {

        init();

        double start = 0;
        double last = System.nanoTime() / 1000000000.0;
        double elapsed = 0;
        double unprocessed = 0;

        double frameTime = 0;
        int frames = 0;
        int fps = 0;

        boolean render;

        while(running) {

            render = false;

            start = System.nanoTime() / 1000000000.0;
            elapsed = start - last;
            last = start;

            unprocessed += elapsed;
            frameTime += elapsed;

            while (unprocessed >= TARGET_TIME) {

                unprocessed -= TARGET_TIME;
                render = true;

                update();

                if (frameTime >= 1.0) {
                    frameTime = 0;
                    fps = frames;
                    frames = 0;
                //    System.out.println("FPS:" + Integer.toString(fps));
                }
            }

            if (render) {
                drawWorld();
                flipBuffer();
                frames++;
            } else {
                try {
                    Thread.sleep(1);
                } catch (Exception e) {

                }
            }
        }
    }

    private void update() {
        world.update();
        updateCamera();
    }

    private void updateCamera() {
        int x = world.getPlayer().getLocation().getX();
        int y = world.getPlayer().getLocation().getY();

        if (x <= (WIDTH / 2))
            x = (WIDTH / 2);

        else if (x > ((world.map.width) * 32) - WIDTH / 2)
            x = ((world.map.width)* 32) - WIDTH / 2;

        if (y <= (HEIGHT / 2))
            y = (HEIGHT / 2);

        else if (y > ((world.map.height ) * 32) - HEIGHT / 2)
            y = ((world.map.height) * 32) - HEIGHT / 2;

        camera.setLocation(x, y);

    }

    private void drawWorld() {
        world.draw(g, camera);
    }

    private void flipBuffer() {
        Graphics g2 = getGraphics();
        g2.drawImage(image, 0, 0, WIDTH, HEIGHT, null);
        g2.dispose();
    }

    public void addNotify() {
        super.addNotify();
        if(thread == null) {
            addKeyListener(this);
            thread = new Thread(this);
            thread.start();
        }
    }

    public void keyTyped(KeyEvent e) {

    }

    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_LEFT) {
            world.getPlayer().setSpeedX(-2);
            world.getPlayer().setSpeedY(0);
        } else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
            world.getPlayer().setSpeedX(2);
            world.getPlayer().setSpeedY(0);
        } else if (e.getKeyCode() == KeyEvent.VK_UP) {
            world.getPlayer().setSpeedY(-2);
            world.getPlayer().setSpeedX(0);
        } else if ( e.getKeyCode() == KeyEvent.VK_DOWN) {
            world.getPlayer().setSpeedY(2);
            world.getPlayer().setSpeedX(0);
        }
        if (e.getKeyCode() == KeyEvent.VK_M) {
            world.newPlayer();
        }
    }

    public void keyReleased(KeyEvent e) {
          if (e.getKeyCode() == KeyEvent.VK_A || e.getKeyCode() == KeyEvent.VK_LEFT || e.getKeyCode() == KeyEvent.VK_D || e.getKeyCode() == KeyEvent.VK_RIGHT) {
              world.getPlayer().setSpeedX(0);
        } else if (e.getKeyCode() == KeyEvent.VK_W || e.getKeyCode() == KeyEvent.VK_UP || e.getKeyCode() == KeyEvent.VK_S || e.getKeyCode() == KeyEvent.VK_DOWN) {
              world.getPlayer().setSpeedY(0);
        }
    }

    public void mouseClicked(MouseEvent e) {

    }

    public void mousePressed(MouseEvent e) {

    }

    public void mouseReleased(MouseEvent e) {

    }

    public void mouseEntered(MouseEvent e) {

    }

    public void mouseExited(MouseEvent e) {

    }
}
