package uet.oop.bomberman.enemies;

import javafx.scene.image.Image;
import uet.oop.bomberman.BombermanGame;
import uet.oop.bomberman.enemies.Enemy;
import uet.oop.bomberman.entities.Bomb;
import uet.oop.bomberman.entities.Bomber;
import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.graphics.Sprite;
import uet.oop.bomberman.ai.*;
import uet.oop.bomberman.media.GameMedia;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

//2: trai 3 : xuong 1 : len 0 : phai
public class oneAl extends Enemy {
    private int test = 1;
    private int count = 0;
    private boolean onPath;
    private List<Entity> entities;
    private PathFinder pFinder;
    public oneAl(int xUnit, int yUnit, Image img,
                 List<Entity> bricks,
                 List<Entity> walls,
                 List<Entity> bomb,
                 PathFinder pFinder,
                 List<Entity> entities) {
        super(xUnit, yUnit, img, bricks, walls, bomb);
        speed = 1;
        this.entities = entities;
        this.pFinder = pFinder;
        onPath = true;
        isAlive = true;
    }
 
    
    private void oneAlMoving() {
        if(count == 0) {
            double diffX = entities.get(0).getX() - x;
            double diffY = entities.get(0).getY() - y;
            if(Math.abs(diffX) <=96 && Math.abs(diffY) <=96) {
                onPath = true;
            }
            else {
                onPath = false;
            }
        }
        if(onPath && ((Bomber) entities.get(0)).isAlive()) {
                int goalCol = entities.get(0).getX()/Sprite.SCALED_SIZE;
                int goalRow = entities.get(0).getY()/Sprite.SCALED_SIZE;
                while (test > 0) {
                    test--;
                    System.out.println(goalCol);
                    System.out.println(goalRow);
                }
                searchPath(goalCol, goalRow);
            }
            else {
                movingRandom();
            }
    }
    private void movingRandom() {
        switch (dir) {
            case 0: img = Sprite.movingSprite(Sprite.oneal_right1, Sprite.oneal_right2, Sprite.oneal_right3, animated++, 50).getFxImage();
                if(!imPassable(x+speed, y)) {
                    x += speed;
                }
                else {
                    choseDir(speed);
                }
                break;
            case 1: img = Sprite.movingSprite(Sprite.oneal_right1, Sprite.oneal_right2, Sprite.oneal_right3, animated++, 50).getFxImage();
                if(!imPassable(x, y-speed)) {
                    y -= speed;
                }
                else {
                    choseDir(speed);
                }
                break;
            case 2: img = Sprite.movingSprite(Sprite.oneal_left1, Sprite.oneal_left2, Sprite.oneal_left3, animated++, 40).getFxImage();
                if(!imPassable(x-speed, y)) {
                    x -= speed;
                }
                else {
                    choseDir(speed);
                }
                break;
            case 3: img = Sprite.movingSprite(Sprite.oneal_left1, Sprite.oneal_left2, Sprite.oneal_left3, animated++, 50).getFxImage();
                if(!imPassable(x, y+speed)) {
                    y += speed;
                }
                else {
                    choseDir(speed);
                }
                break;
        }
    }
    private void searchPath(int goalCol, int goalRow) {
            int startCol = (int)(x/Sprite.SCALED_SIZE);
            int startRow =  (int)(y/Sprite.SCALED_SIZE);
           pFinder.setNode(startCol, startRow, goalCol, goalRow);
            if(pFinder.search() == true) {
                double nextX = pFinder.pathList.get(0).col * Sprite.SCALED_SIZE;
                double nextY = pFinder.pathList.get(0).row * Sprite.SCALED_SIZE;
                double enLeftX = (x - 16);
                double enTopY = (y - 16);
                if(enTopY > nextY - 16 && enLeftX == nextX - 16) {
                    dir = 1;
                } else if(enTopY < nextY - 16 && enLeftX == nextX - 16) {
                    dir = 3;
                } else if (enTopY == nextY -16) {
                    //left or right
                    if (enLeftX > nextX - 16) {
                        dir = 2;
                    }
                    if(enLeftX <  nextX - 16) {
                        dir = 0;
                    }
                }
                int nextCol = pFinder.pathList.get(0).col ;
                int nextRow = pFinder.pathList.get(0).row ;
                if(nextCol == goalCol && nextRow == goalRow) {
                    onPath = false;
                    count++;
                }
            }
            else {
                switch (dir) {
                    case 0:
                        if(!imPassable(x+speed, y)) {
                            dir = 0;
                        }
                        else {
                            choseDir(speed);
                        }
                        break;
                    case 1:
                        if(!imPassable(x, y-speed)) {
                            dir = 1;
                        }
                        else {
                            choseDir(speed);
                        }
                        break;
                    case 2:
                        if(!imPassable(x-speed, y)) {
                           dir = 2;
                        }
                        else {
                            choseDir(speed);
                        }
                        break;
                    case 3:
                        if(!imPassable(x, y+speed)) {
                            dir = 3;
                        }
                        else {
                            choseDir(speed);
                        }
                        break;
                }
            }
    }

    @Override
    public void update() {
        if(!isAlive) {
            if(oneDiesound > 0) {
                GameMedia.setDeathSound();
                GameMedia.getDeathSound().play();
                oneDiesound--;
            }
            if(die_animation == 40) {
                img  = Sprite.hide.getFxImage();
                x = 0; y = 0;
                return;
            }
            img  = Sprite.movingSprite(Sprite.oneal_dead, Sprite.hide, Sprite.hide, animated++, 40).getFxImage();
            die_animation++;
        }
        else {
            oneAlMoving();
            if(onPath) {
                switch (dir) {
                    case 1 :
                        img = Sprite.movingSprite(Sprite.oneal_right1,Sprite.oneal_right2, Sprite.oneal_right3, animated++, 40).getFxImage();
                        y-=speed;
                        break;
                    case 3 :
                        img = Sprite.movingSprite(Sprite.oneal_left1,Sprite.oneal_left2, Sprite.oneal_left3, animated++, 40).getFxImage();
                        y+=speed;
                        break;
                    case 0 :
                        img = Sprite.movingSprite(Sprite.oneal_right1,Sprite.oneal_right2, Sprite.oneal_right3, animated++, 40).getFxImage();
                        x+=speed;
                        break;
                    case 2 :
                        img = Sprite.movingSprite(Sprite.oneal_left1,Sprite.oneal_left2, Sprite.oneal_left3, animated++, 40).getFxImage();
                        x-=speed;
                        break;
                    default:
                        break;
                }
            }
        }
    }
}
