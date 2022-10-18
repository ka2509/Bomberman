package uet.oop.bomberman.enemies;

import javafx.scene.image.Image;
import uet.oop.bomberman.BombermanGame;
import uet.oop.bomberman.enemies.Enemy;
import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.graphics.Sprite;
import uet.oop.bomberman.ai.*;

import java.util.ArrayList;
import java.util.List;

public class oneAl extends Enemy {
    private double distance, diffX, diffY;
    private double velX, velY;
    private String direction;
    private int animated = 0;
    private double radius;
    private List<Entity> bricks= new ArrayList<>();
    private List<Entity> walls = new ArrayList<>();
    private List<Entity> bomb = new ArrayList<>();
    private List<Entity> entities = new ArrayList<>();
    public oneAl(int xUnit, int yUnit, Image img, List<Entity> entities,
                 List<Entity> bricks,
                 List<Entity> walls,
                 List<Entity> bomb) {
        super(xUnit, yUnit, img);
        this.radius = 5;
        this.bricks = bricks;
        this.walls = walls;
        this.bomb = bomb;
        this.entities = entities;
        onPath = true;
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
//        for(Entity p : bomb) {
//            if((x+16 > p.getX() -16) && x-16  < p.getX() +16) {
//                if((y +16 > p.getY() - 16) && (y -16 < p.getY() +16)) {
//                    return  true;
//                }
//            }
//        }
        return false;
    }
    private void goLeft() {
        img = Sprite.movingSprite(Sprite.oneal_left1, Sprite.oneal_left2, Sprite.oneal_left3, animated++, 40).getFxImage();
        if(!imPassable(x-0.5, y)) {
            x -= 0.5;
        }
    }
    private void goRight() {
        img = Sprite.movingSprite(Sprite.oneal_right1, Sprite.oneal_right2, Sprite.oneal_right3, animated++, 50).getFxImage();
        if(!imPassable(x+0.5, y)) {
            x += 0.5;
        }
    }
    private void goUp() {
        img = Sprite.movingSprite(Sprite.oneal_right1, Sprite.oneal_right2, Sprite.oneal_right3, animated++, 50).getFxImage();
        if(!imPassable(x, y-0.5)) {
            y -= 0.5;
        }
    }
    private void goDown() {
        img = Sprite.movingSprite(Sprite.oneal_left1, Sprite.oneal_left2, Sprite.oneal_left3, animated++, 50).getFxImage();
        if(!imPassable(x, y+0.5)) {
            y += 0.5;
        }
    }
    private void oneAlMoving() {
//        diffX = x - (double)entities.get(0).getX();
//        diffY = y - (double)entities.get(0).getY();
//        distance = Math.sqrt((x - (double)entities.get(0).getX())*(x - (double)entities.get(0).getX())
//                + (y - (double)entities.get(0).getY())*(y - (double)entities.get(0).getY()));
////        velX = ((-1/distance)*diffX);
////        velY = ((-1/distance)*diffY);
////            x+=velX;
////            y+=velY;
        if(onPath) {
            int goalCol = entities.get(0).getX();
            int goalRow = entities.get(0).getY();
            searchPath(goalCol, goalRow);
        }
    }
    public void searchPath(int goalCol, int goalRow) {
            int startCol = (int) (x/Sprite.SCALED_SIZE);
            int startRow = (int) (x/Sprite.SCALED_SIZE);
            BombermanGame.pFinder.setNode(startCol, startRow, goalCol, goalRow);
            if(BombermanGame.pFinder.search() == true) {
                int nextX = BombermanGame.pFinder.pathList.get(0).col * Sprite.SCALED_SIZE;
                int nextY = BombermanGame.pFinder.pathList.get(0).row * Sprite.SCALED_SIZE;

                int enLeftX = (int) (x - 16);
                int enRightX = (int) (x + 16);
                int enTopY = (int) (y - 16);
                int enBotY = (int) (y + 16);
                if(enTopY > nextY - 16 && enLeftX >= nextX - 16 && enRightX < nextX + 16) {
                    direction = "up";
                } else if(enTopY < nextY - 16 && enLeftX >= nextX - 16 && enRightX < nextX + 16) {
                    direction = "down";
                }
                else if(enTopY >= nextY - 16 && enBotY < nextY + 16) {
                    if(enLeftX > nextX - 16) {
                        direction = "left";
                    }
                    if(enLeftX < nextX - 16) {
                        direction = "right";
                    }
                } else if (enTopY > nextY - 16 && enLeftX > nextX - 16) {
                    direction = "up";
                    if(imPassable(x,y+0.5)) {
                        direction = "left";
                    }
                }
                else if(enTopY > nextY - 16 && enLeftX < nextX - 16) {
                    direction = "up";
                    if(imPassable(x, y+0.5)) {
                        direction = "right";
                    }
                }
                else if(enTopY < nextY - 16 && enLeftX > nextX - 16) {
                    direction = "down";
                    if(imPassable(x,y-0.5)) {
                        direction = "left";
                    }
                }
                else if(enTopY < nextY - 16 && enLeftX < nextX - 16) {
                    direction = "down";
                    if(imPassable(x,y-0.5)) {
                        direction = "right";
                    }
                }
                int nextCol = BombermanGame.pFinder.pathList.get(0).col;
                int nextRow = BombermanGame.pFinder.pathList.get(0).row;
                if(nextCol == goalCol && nextRow == goalRow) {
                    onPath = false;
                }
            }
    }

    @Override
    public void update() {
        oneAlMoving();
        switch (direction) {
            case "up" :
                img = Sprite.movingSprite(Sprite.oneal_right1,Sprite.oneal_right2, Sprite.oneal_right3, animated++, 40).getFxImage();
                if(imPassable(x,y-0.5)) {
                    y-=0.5;
                }
                break;
            case "down" :
                img = Sprite.movingSprite(Sprite.oneal_left1,Sprite.oneal_left2, Sprite.oneal_left3, animated++, 40).getFxImage();
                if(imPassable(x,y+0.5)) {
                    y+=0.5;
                }
                break;
            case "right" :
                img = Sprite.movingSprite(Sprite.oneal_right1,Sprite.oneal_right2, Sprite.oneal_right3, animated++, 40).getFxImage();
                if(imPassable(x-+0.5,y)) {
                    x+=0.5;
                }
                break;
            case "left" :
                img = Sprite.movingSprite(Sprite.oneal_left1,Sprite.oneal_left2, Sprite.oneal_left3, animated++, 40).getFxImage();
                if(imPassable(x-0.5,y)) {
                    x-=0.5;
                }
                break;
        }
    }
}
