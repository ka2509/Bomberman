package uet.oop.bomberman.enemies;

import javafx.scene.image.Image;
import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.graphics.Sprite;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Balloom extends Enemy{
    public Balloom(int xUnit, int yUnit, Image img,
                   List<Entity> bricks,
                   List<Entity> walls,
                   List<Entity> bomb) {
        super(xUnit, yUnit, img, bricks, walls, bomb);
        isAlive = true;
        speed = 0.5;
        choseDir(speed);
    }
    public void balloomMoving() {
        switch (dir) {
            case 0: img = Sprite.movingSprite(Sprite.balloom_right1, Sprite.balloom_right2, Sprite.balloom_right3, animated++, 50).getFxImage();
                if(!imPassable(x+speed, y)) {
                    x += speed;
                }
                else {
                    choseDir(speed);
                }
                break;
            case 1: img = Sprite.movingSprite(Sprite.balloom_right1, Sprite.balloom_right2, Sprite.balloom_right3, animated++, 50).getFxImage();
                if(!imPassable(x, y-speed)) {
                    y -= speed;
                }
                else {
                    choseDir(speed);
                }
                break;
            case 2: img = Sprite.movingSprite(Sprite.balloom_left1, Sprite.balloom_left2, Sprite.balloom_left3, animated++, 40).getFxImage();
                if(!imPassable(x-speed, y)) {
                    x -= speed;
                }
                else {
                    choseDir(speed);
                }
                break;
            case 3: img = Sprite.movingSprite(Sprite.balloom_left1, Sprite.balloom_left2, Sprite.balloom_left3, animated++, 50).getFxImage();
                if(!imPassable(x, y+speed)) {
                    y += speed;
                }
                else {
                    choseDir(speed);
                }
                break;
        }
    }
    @Override
    public void update() {
        if(!isAlive) {
            if(die_animation == 40) {
                img  = Sprite.hide.getFxImage();
                x = 0; y = 0;
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
