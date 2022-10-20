package uet.oop.bomberman.enemies;

import javafx.scene.canvas.GraphicsContext;
import uet.oop.bomberman.BombermanGame;
import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.graphics.Sprite;
import javafx.scene.image.Image;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public abstract class Enemy {
    //toa do hinh anh
    protected double x;
    protected double y;
    protected Image img;
    protected double speed;
    protected int dir;
    //bien animation
    protected int animated;
    protected int die_animation;
    protected boolean isAlive;
    private List<Entity> bricks= new ArrayList<>();
    private List<Entity> walls = new ArrayList<>();
    private List<Entity> bomb = new ArrayList<>();
    public Enemy(int xUnit, int yUnit,Image img,
                 List<Entity> bricks,
                 List<Entity> walls,
                 List<Entity> bomb) {
        this.x = xUnit * Sprite.SCALED_SIZE;
        this.y = yUnit * Sprite.SCALED_SIZE;
        this.img = img;
        this.bricks = bricks;
        this.walls = walls;
        this.bomb = bomb;
        dir = 0;
        animated = 0;
        die_animation = 0;
        isAlive = true;
    }
    public abstract void update();
    public void setKilled() {
        isAlive = false;
    }
    protected void choseDir(double speed) {
        ArrayList<Integer> availableDir = new ArrayList<>();
        if(!imPassable(x+speed, y)) {
            availableDir.add(0);
        }
        if(!imPassable(x-speed, y)) {
            availableDir.add(2);
        }
        if(!imPassable(x, y+speed)) {
            availableDir.add(3);
        }
        if(!imPassable(x, y-speed)) {
            availableDir.add(1);
        }
        if(availableDir.isEmpty()) {
            dir = 0;
        }
        else {
            Random rand = new Random();
            int ranDir = rand.nextInt(availableDir.size());
            dir = availableDir.get(ranDir);
        }
    }
    protected boolean imPassable(double x, double y) {
        if(x < 32 || y < 32 || x > 8 + (BombermanGame.WIDTH - 2)*32 || y > (BombermanGame.HEIGHT - 2)*32) {
            return true;
        }
        for(Entity p : walls) {
            if((x+16 > p.getX() -16) && x -16 < p.getX() +16) {
                if((y +16 > p.getY() - 16) && (y -16 < p.getY() +16)) {
                    return  true;
                }
            }
        }
        for(Entity p : bricks) {
            if((x+16 > p.getX() -16) && x-16  < p.getX() +16) {
                if((y +16 > p.getY() - 16) && (y -16 < p.getY() +16)) {
                    return  true;
                }
            }
        }
        for(Entity p : bomb) {
            if((x+16 > p.getX() -16) && x-16  < p.getX() +16) {
                if((y +16 > p.getY() - 16) && (y -16 < p.getY() +16)) {
                    return  true;
                }
            }
        }
        return false;
    }

    public void render(GraphicsContext gc) {
        gc.drawImage(img, x, y);
    }
    public double getX() {
        return x;
    }
    public double getY() {
        return y;
    }
    public void setX(int x) {
        this.x = x;
    }
    public void setY(int x) {
        this.y = x;
    }
}
