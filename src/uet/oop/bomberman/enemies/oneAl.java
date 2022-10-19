package uet.oop.bomberman.enemies;

import javafx.scene.image.Image;
import uet.oop.bomberman.BombermanGame;
import uet.oop.bomberman.enemies.Enemy;
import uet.oop.bomberman.entities.Bomb;
import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.graphics.Sprite;
import uet.oop.bomberman.ai.*;

import java.util.ArrayList;
import java.util.List;

public class oneAl extends Enemy {
    private int test = 1;
    private boolean onPath;
    private boolean noNeedonPath;
    private int actionLockCounter;
    private double radius;
    private List<Entity> entities = new ArrayList<>();
    private PathFinder pFinder = new PathFinder();
    public oneAl(int xUnit, int yUnit, Image img,
                 List<Entity> bricks,
                 List<Entity> walls,
                 List<Entity> bomb,
                 PathFinder pFinder,
                 List<Entity> entities) {
        super(xUnit, yUnit, img, bricks, walls, bomb);
        this.radius = 5;
        actionLockCounter = 0;
        speed = 0.5;
        noNeedonPath = false;
        this.entities = entities;
        this.pFinder = pFinder;
        onPath = true;
        isAlive = true;
    }
 
    
    private void oneAlMoving() {
//        if( Math.abs(entities.get(0).getX()/Sprite.SCALED_SIZE - x/Sprite.SCALED_SIZE) <=3
//                && Math.abs(entities.get(0).getY()/Sprite.SCALED_SIZE - y/Sprite.SCALED_SIZE) <= 3) {
//            onPath = true;
//        }
//        else {
//            onPath = false;
//        }
        if(onPath) {
            int goalCol = entities.get(0).getX()/Sprite.SCALED_SIZE;
            int goalRow = entities.get(0).getY()/Sprite.SCALED_SIZE;
            searchPath(goalCol, goalRow);
        }
    }
    private void searchPath(int goalCol, int goalRow) {
            int startCol = (int)(x/Sprite.SCALED_SIZE);
            int startRow =  (int)(y/Sprite.SCALED_SIZE);


           pFinder.setNode(startCol, startRow, goalCol, goalRow);
            if(pFinder.search() == true) {
                double nextX = pFinder.pathList.get(0).col * Sprite.SCALED_SIZE;
                double nextY = pFinder.pathList.get(0).row * Sprite.SCALED_SIZE;
                double enLeftX = (x - 15);
                double enRightX = (x + 15);
                double enTopY = (y - 15);
                double enBotY = (y + 15);
                if(enTopY > nextY - 16 && enLeftX >= nextX - 16 && enRightX < nextX + 16) {
                    dir = 1;
                } else if(enTopY < nextY - 16 && enLeftX >= nextX - 16 && enRightX < nextX + 16) {
                    dir = 3;
                }
                else if(enTopY >= nextY - 16 && enBotY < nextY + 16) {
                    if(enLeftX > nextX - 16) {
                        dir = 2;
                    }
                    if(enLeftX < nextX - 16) {
                        dir = 0;
                    }
                }
                else if (enTopY > nextY - 16 && enLeftX > nextX - 16) {
                    dir = 1;
                    if(imPassable(x,y-speed)) {
                        dir = 2;
                    }
                }
                else if(enTopY > nextY - 16 && enLeftX < nextX - 16) {
                    dir = 1;
                    if(imPassable(x, y-speed)) {
                        dir = 0;
                    }
                }
                else if(enTopY < nextY - 16 && enLeftX > nextX - 16) {
                    dir = 3;
                    if(imPassable(x,y+speed)) {
                        dir = 2;
                    }
                }
                else if(enTopY < nextY - 16 && enLeftX < nextX - 16) {
                    dir = 3;
                    if(imPassable(x,y+speed)) {
                        dir = 0;
                    }
                }
                int nextCol = pFinder.pathList.get(0).col;
                int nextRow = pFinder.pathList.get(0).row;
                if(nextCol == goalCol && nextRow == goalRow) {
                    onPath = false;
                    System.out.println("end");
                }
            }
//            else {
//                noNeedonPath = true;
//                switch (dir) {
//                    case 0: img = Sprite.movingSprite(Sprite.oneal_right1, Sprite.oneal_right2, Sprite.oneal_right3, animated++, 50).getFxImage();
//                        if(!imPassable(x+speed, y)) {
//                            x += speed;
//                        }
//                        else {
//                            choseDir(speed);
//                        }
//                        break;
//                    case 1: img = Sprite.movingSprite(Sprite.oneal_right1, Sprite.oneal_right2, Sprite.oneal_right3, animated++, 50).getFxImage();
//                        if(!imPassable(x, y-speed)) {
//                            y -= speed;
//                        }
//                        else {
//                            choseDir(speed);
//                        }
//                        break;
//                    case 2: img = Sprite.movingSprite(Sprite.oneal_left1, Sprite.oneal_left2, Sprite.oneal_left3, animated++, 40).getFxImage();
//                        if(!imPassable(x-speed, y)) {
//                            x -= speed;
//                        }
//                        else {
//                            choseDir(speed);
//                        }
//                        break;
//                    case 3: img = Sprite.movingSprite(Sprite.oneal_left1, Sprite.oneal_left2, Sprite.oneal_left3, animated++, 50).getFxImage();
//                        if(!imPassable(x, y+speed)) {
//                            y += speed;
//                        }
//                        else {
//                            choseDir(speed);
//                        }
//                        break;
//                }
//            }
    }

    @Override
    public void update() {
        oneAlMoving();
        if(onPath ) {
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
//        else {
//            switch (dir) {
//                case 0: img = Sprite.movingSprite(Sprite.oneal_right1, Sprite.oneal_right2, Sprite.oneal_right3, animated++, 50).getFxImage();
//                    if(!imPassable(x+speed, y)) {
//                        x += speed;
//                    }
//                    else {
//                        choseDir(speed);
//                    }
//                    break;
//                case 1: img = Sprite.movingSprite(Sprite.oneal_right1, Sprite.oneal_right2, Sprite.oneal_right3, animated++, 50).getFxImage();
//                    if(!imPassable(x, y-speed)) {
//                        y -= speed;
//                    }
//                    else {
//                        choseDir(speed);
//                    }
//                    break;
//                case 2: img = Sprite.movingSprite(Sprite.oneal_left1, Sprite.oneal_left2, Sprite.oneal_left3, animated++, 40).getFxImage();
//                    if(!imPassable(x-speed, y)) {
//                        x -= speed;
//                    }
//                    else {
//                        choseDir(speed);
//                    }
//                    break;
//                case 3: img = Sprite.movingSprite(Sprite.oneal_left1, Sprite.oneal_left2, Sprite.oneal_left3, animated++, 50).getFxImage();
//                    if(!imPassable(x, y+speed)) {
//                        y += speed;
//                    }
//                    else {
//                        choseDir(speed);
//                    }
//                    break;
//            }
//        }
    }
}
