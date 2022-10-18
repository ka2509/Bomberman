package uet.oop.bomberman.enemies;

import javafx.scene.image.Image;
import uet.oop.bomberman.enemies.Enemy;
import uet.oop.bomberman.entities.Entity;

import java.util.ArrayList;
import java.util.List;

public class oneAl extends Enemy {
    private double distance, diffX, diffY;
    double velX, velY;
    private double radius;
    private List<Entity> entities = new ArrayList<>();
    public oneAl(int xUnit, int yUnit, Image img, List<Entity> entities) {
        super(xUnit, yUnit, img);
        this.radius = 5;
        this.entities = entities;
    }
    private void oneAlMoving() {
        x+=velX;
        y+=velY;
        diffX = x - (double)entities.get(0).getX() - 16;
        diffY = y - (double)entities.get(0).getY() - 16;
        distance = Math.sqrt((x - (double)entities.get(0).getX())*(x - (double)entities.get(0).getX())
                + (y - (double)entities.get(0).getY())*(y - (double)entities.get(0).getY()));
//        if(distance <= radius) {
//
//        }
//        else {
//            velX = 0;
//            velY = 0;
//        }
        velX = ((-1/distance)*diffX);
        velY = ((-1/distance)*diffY);
    }

    @Override
    public void update() {
            oneAlMoving();
    }
}
