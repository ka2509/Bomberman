package uet.oop.bomberman.entities;

import javafx.scene.image.Image;
import uet.oop.bomberman.BombermanGame;
import uet.oop.bomberman.ai.PathFinder;
import uet.oop.bomberman.enemies.Enemy;
import uet.oop.bomberman.enemies.oneAl;
import uet.oop.bomberman.graphics.Sprite;

import java.util.ArrayList;
import java.util.List;


public class Portal extends Entity{
    public boolean opened = false;
    public boolean exploded = false;
    public Portal() {
    }
    public Portal(int xUnit, int yUnit, Image img) {
        super(xUnit, yUnit, img);
    }
    public boolean covered() {
        if(BombermanGame.map[x/32][y/32] == 1) {
           return false;
        }
        return true;
    }
    public void checkStatus() {
        if(BombermanGame.ballooms.isEmpty() && BombermanGame.oneAls.isEmpty() && !covered()) {
            opened = true;
        }
        else {
            opened = false;
        }
        if(exploded == true) {
            BombermanGame.oneAls.add(new oneAl(x/32, y/32, Sprite.oneal_right1.getFxImage(), BombermanGame.bricks,
                    BombermanGame.walls, BombermanGame.bomb,
                    BombermanGame.pFinder,BombermanGame.entities));
            exploded =false;
        }
    }
    @Override
    public void update() {
        checkStatus();
    }
}
