package uet.oop.bomberman.enemies;

import javafx.scene.image.Image;
import uet.oop.bomberman.BombermanGame;
import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.graphics.Sprite;

import java.awt.peer.ScrollbarPeer;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Balloom extends Enemy{
    private int animated = 0;
    private int die_animation = 0;
    private int dir =0;
    private List<Entity> bricks= new ArrayList<>();
    private List<Entity> walls = new ArrayList<>();
    private List<Entity> bomb = new ArrayList<>();
    public Balloom(int xUnit, int yUnit, Image img, List<Entity> bricks, List<Entity> walls,
                   List<Entity> bomb) {
        super(xUnit, yUnit, img);
        this.bricks = bricks;
        this.walls = walls;
        this.bomb = bomb;
        choseDir();
    }
    private void moveLeft() {
        img = Sprite.movingSprite(Sprite.balloom_left1, Sprite.balloom_left2, Sprite.balloom_left3, animated++, 40).getFxImage();
        if(!imPassable(x-0.5, y)) {
            x -= 0.5;
        }
        else {
            choseDir();
        }
    }
    private void moveRight() {
        img = Sprite.movingSprite(Sprite.balloom_right1, Sprite.balloom_right2, Sprite.balloom_right3, animated++, 50).getFxImage();
        if(!imPassable(x+0.5, y)) {
            x += 0.5;
        }
        else {
            choseDir();
        }
    }
    private void moveDown() {
        img = Sprite.movingSprite(Sprite.balloom_left1, Sprite.balloom_left2, Sprite.balloom_left3, animated++, 50).getFxImage();
        if(!imPassable(x, y+0.5)) {
            y += 0.5;
        }
        else {
            choseDir();
        }
    }
    private void moveUp() {
        img = Sprite.movingSprite(Sprite.balloom_right1, Sprite.balloom_right2, Sprite.balloom_right3, animated++, 50).getFxImage();
        if(!imPassable(x, y-0.5)) {
            y -= 0.5;
        }
        else {
            choseDir();
        }
    }
    private void choseDir() {
       ArrayList<Integer> availableDir = new ArrayList<>();
        if(!imPassable(x+0.5, y)) {
            availableDir.add(0);
        }
        if(!imPassable(x-0.5, y)) {
            availableDir.add(2);
        }
        if(!imPassable(x, y+0.5)) {
            availableDir.add(3);
        }
        if(!imPassable(x, y-0.5)) {
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
    public void balloomMoving() {
        switch (dir) {
            case 0: moveRight(); break;
            case 1: moveUp(); break;
            case 2: moveLeft(); break;
            case 3: moveDown(); break;
        }
    }
    public void setKilled() {
        isAlive = false;
    }
    private boolean imPassable(double x, double y) {
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
    @Override
    public void update() {
        if(!isAlive) {
            if(die_animation == 40) {
                img  = Sprite.hide.getFxImage();
                x = 0; y=0;
                return;
            }
            img  = Sprite.movingSprite(Sprite.balloom_dead, Sprite.hide, Sprite.hide, animated++, 40).getFxImage();
            die_animation++;
        }
        else {
            balloomMoving();
        }

    }
}
