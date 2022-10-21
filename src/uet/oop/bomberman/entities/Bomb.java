package uet.oop.bomberman.entities;

import javafx.application.Platform;
import javafx.scene.image.Image;
import uet.oop.bomberman.BombermanGame;
import uet.oop.bomberman.enemies.Balloom;
import uet.oop.bomberman.enemies.Enemy;
import uet.oop.bomberman.enemies.oneAl;
import uet.oop.bomberman.graphics.Sprite;
import uet.oop.bomberman.graphics.SpriteSheet;
import uet.oop.bomberman.media.GameMedia;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

public class Bomb  extends Entity{
    private int animated;
    private boolean setBomb = false;
    private boolean bombExploded =false;
    private List<Entity> entities;
    private List<Entity> walls;
    private List<Entity> bombs;
    private List<Entity> explodes;
    private List<Entity> bricks;
    private List<Enemy> ballooms;
    private List<Enemy> oneAls;
    private int radius;
    public Bomb(int radius, List<Entity> entities,
                List<Entity> walls,List<Entity> bombs,
                List<Entity> explodes, List<Entity> bricks,
                List<Enemy> ballooms, List<Enemy> oneAls) {
      this.entities = entities;
      this.radius = radius;
      this.walls = walls;
      this.bombs = bombs;
      this.oneAls = oneAls;
      animated = 0;
      this.explodes =explodes;
      this.bricks = bricks;
      this.ballooms = ballooms;
    }
    public Bomb(int x, int y, Image img) {
        super(x, y, img);
    }
    public void setBomb(Entity bomb) {
        if(validPlace(bomb.getX(),bomb.getY())) {
            setBomb = true;
            bombs.add(bomb);
        }
    }
    private boolean validPlace(int x, int y) {
        if(bombs.isEmpty()) {
            return true;
        }
        else if(bombs.get(0).getX() == x && bombs.get(0).getY() == y) {
            return false;
        }
        return true;
    }
    public void explode() {
        BombermanGame.map[x/Sprite.SCALED_SIZE][y/Sprite.SCALED_SIZE] = 1;
        GameMedia.setBombExplosionSound();
        GameMedia.getBombExplosionSound().play();
        bombExploded = true;
        expLeft();
        expRight();
        expUp();
        expDown();
        for (Entity p : entities) {
            int x = p.getX();
            int y = p.getY();
            if (x - 10 <= this.x +16 && x + 10 >= this.x - 16) {
                if (y + 16 >= this.y - 10 && y - 16 <= this.y + 10) {
                    ((Bomber) p).setKilled();
                }
            }
        }
        for (Enemy p : ballooms) {
            double x = p.getX();
            double y = p.getY();
            if (x - 16 <= this.x +16 && x + 16 >= this.x - 16) {
                if (y + 16 >= this.y - 10 && y - 16 <= this.y + 10) {
                     p.setKilled();
                }
            }
        }
        for (Enemy p : oneAls) {
            double x = p.getX();
            double y = p.getY();
            if (x - 16 <= this.x +16 && x + 16 >= this.x - 16) {
                if (y + 16 >= this.y - 10 && y - 16 <= this.y + 10) {
                    p.setKilled();
                }
            }
        }
        if(((Portal) BombermanGame.portal).covered() == false) {
            int x = BombermanGame.portal.getX();
            int y = BombermanGame.portal.getY();
            if (x - 15 <= this.x +16 && x + 15 >= this.x - 16) {
                if (y + 15 >= this.y - 10 && y - 15 <= this.y + 10) {
                    ((Portal) BombermanGame.portal).exploded = true;
                }
            }
        }
    }

    public void expLeft() {
        for(int i = 1 ; i<=radius; i++) {
            Entity left  = new tmpObject(x/Sprite.SCALED_SIZE-i, y/Sprite.SCALED_SIZE, Sprite.explosion_horizontal.getFxImage());
            int leftX = left.getX();
            int leftY = left.getY();
            if(validBlock(x-32,y)) {
                if(validBlock(x-32*i,y)) {
                    explodes.add(left);
                }
            }
            else {
                for (Entity p : bricks) {
                    int x = p.getX();
                    int y = p.getY();
                    if (x - 10 >= leftX - 36 && x + 10 <= leftX + 48) {
                        if (y + 16 >= leftY - 10 && y - 16 <= leftY + 10) {
                            ((Brick) p).destroy();
                            BombermanGame.map[x/32][y/32] = 1;
                        }
                    }
                }
                return;
            }
                for (Entity p : entities) {
                    int x = p.getX();
                    int y = p.getY();
                    if (x - 10 <= leftX +16 && p.getX() + 10 >= leftX - 16) {
                        if (y + 16 >= leftY - 10 && y - 16 <= leftY + 10) {
                            ((Bomber) p).setKilled();
                        }
                    }
                }
            for (Entity p : bricks) {
                int x = p.getX();
                int y = p.getY();
                if (x - 10 >= leftX - 36 && x + 10 <= leftX + 48) {
                    if (y + 16 >= leftY - 10 && y - 16 <= leftY + 10) {
                        ((Brick) p).destroy();
                        BombermanGame.map[x/32][y/32] = 1;
                    }
                }
            }
            for (Enemy p : ballooms) {
                double x = p.getX();
                double y = p.getY();
                if (x - 10 <= leftX +16 && x + 10 >= leftX - 16) {
                    if (y + 16 >= leftY - 10 && y - 16 <= leftY + 10) {
                         p.setKilled();
                    }
                }
            }
            for (Enemy p :oneAls) {
                double x = p.getX();
                double y = p.getY();
                if (x - 16 <= leftX +16 && x + 16 >= leftX - 16) {
                    if (y + 16 >= leftY - 10 && y - 16 <= leftY + 10) {
                        p.setKilled();
                    }
                }
            }
            if(((Portal) BombermanGame.portal).covered() == false) {
                int x = BombermanGame.portal.getX();
                int y = BombermanGame.portal.getY();
                if (x - 15 <= leftX +16 && x + 15 >= leftX - 16) {
                    if (y + 15 >= leftY - 10 && y - 15 <= leftY + 10) {
                        ((Portal) BombermanGame.portal).exploded = true;
                    }
                }
            }
        }
    }
    public void expRight() {
        for(int i = 1; i <= radius; i++) {
            Entity right = new tmpObject(x/Sprite.SCALED_SIZE+i, y/Sprite.SCALED_SIZE, Sprite.explosion_horizontal.getFxImage());
            int rightX = right.getX();
            int rightY = right.getY();
            if(validBlock(x+32,y)) {
                if(validBlock(x+32*i,y)) {
                    explodes.add(right);
                }
            }
            else {
                for (Entity p : bricks) {
                    int x = p.getX();
                    int y = p.getY();
                    if (x - 10 >= rightX - 16 && x + 10 <= rightX + 36) {
                        if (y + 16 >= rightY - 10 && y - 16 <= rightY + 10) {
                            ((Brick) p).destroy();
                            BombermanGame.map[x/32][y/32] = 1;
                        }
                    }
                }
                return;
            }
                for (Entity p : entities) {
                    int x = p.getX();
                    int y =p.getY();
                    if (x - 10 <= rightX + 16 && x + 10 >= rightX - 16) {
                        if (y + 16 >= rightY - 10 && y - 16 <= rightY + 10) {
                            ((Bomber) p).setKilled();
                        }
                    }
                }
            for (Entity p : bricks) {
                int x = p.getX();
                int y =p.getY();
                if (x - 10 >= rightX - 16 && x + 10 <= rightX + 36) {
                    if (y + 16 >= rightY - 10 && y - 16 <= rightY + 10) {
                        ((Brick) p).destroy();
                        BombermanGame.map[x/32][y/32] = 1;
                    }
                }
            }
            for (Enemy p : ballooms) {
               double x = p.getX();
               double y = p.getY();
                if (x - 10 <= rightX + 16 && x + 10 >= rightX - 16) {
                    if (y + 16 >= rightY - 10 && y - 16 <= rightY + 10) {
                        p.setKilled();
                    }
                }
            }
            for (Enemy p : oneAls) {
                double x = p.getX();
                double y = p.getY();
                if (x - 16 <= rightX + 16 && x + 16 >= rightX - 16) {
                    if (y + 16 >= rightY - 10 && y - 16 <= rightY + 10) {
                        p.setKilled();
                    }
                }
            }
            if(((Portal) BombermanGame.portal).covered() == false) {
                int x = BombermanGame.portal.getX();
                int y = BombermanGame.portal.getY();
                if (x - 15 <= rightX + 16 && x + 15 >= rightX - 16) {
                    if (y + 15 >= rightY - 10 && y - 15 <= rightY + 10) {
                        ((Portal) BombermanGame.portal).exploded = true;
                    }
                }
            }
        }
    }
    public void expUp() {
        for(int i = 1; i <= radius; i++) {
            Entity top = new tmpObject(x/Sprite.SCALED_SIZE, y/Sprite.SCALED_SIZE-i, Sprite.explosion_vertical.getFxImage());
            int topX = top.getX();
            int topY =top.getY();
            if(validBlock(x,y-32)) {
                if(validBlock(x,y-32*i)) {
                    explodes.add(top);
                }
            }
            else {
                for (Entity p : bricks) {
                    int x = p.getX();
                    int y = p.getY();
                    if (x + 10 >= topX - 10 && x - 10 <= topX + 10) {
                        if (y + 16 >= topY - 10 && y - 17 <= topY + 10) {
                            ((Brick) p).destroy();
                            BombermanGame.map[x/32][y/32] = 1;
                        }
                    }
                }
                return;
            }
                for (Entity p : entities) {
                    int x = p.getX();
                    int y = p.getY();
                    if (x + 10 >= topX - 10 && x - 10 <= topX + 10) {
                        if (y + 16 >= topY - 15 && y - 17 <= topY + 15) {
                            ((Bomber) p).setKilled();
                        }
                    }
                }
            for (Entity p : bricks) {
                int x = p.getX();
                int y = p.getY();
                if (x + 10 >= topX - 10 && x - 10 <= topX + 10) {
                    if (y + 16 >= topY - 10 && y - 17 <= topY + 10) {
                        ((Brick) p).destroy();
                        BombermanGame.map[x/32][y/32] = 1;
                    }
                }
            }
            for (Enemy p : ballooms) {
                double x = p.getX();
                double y = p.getY();
                if (x + 16 >= topX - 10 && x - 16 <= topX + 10) {
                    if (y + 16 >= topY - 15 && y - 16 <= topY + 15) {
                        p.setKilled();
                    }
                }
            }
            for (Enemy p : oneAls) {
                double x = p.getX();
                double y = p.getY();
                if (x + 16 >= topX - 10 && x - 16 <= topX + 10) {
                    if (y + 16 >= topY - 15 && y - 16 <= topY + 15) {
                        p.setKilled();
                    }
                }
            }
            if(((Portal) BombermanGame.portal).covered() == false) {
                int x = BombermanGame.portal.getX();
                int y = BombermanGame.portal.getY();
                if (x + 15 >= topX - 10 && x - 15 <= topX + 10) {
                    if (y + 15 >= topY - 15 && y - 15 <= topY + 15) {
                        ((Portal) BombermanGame.portal).exploded = true;
                    }
                }
            }
        }
    }
    public void expDown() {
        for(int i = 1; i <= radius; i++) {
            Entity bot = new tmpObject(x/Sprite.SCALED_SIZE, y/Sprite.SCALED_SIZE +i, Sprite.explosion_vertical.getFxImage());
            int botX = bot.getX();
            int botY = bot.getY();
            if(validBlock(x, y+32)) {
                if(validBlock(x, y+32*i)) {
                    explodes.add(bot);
                }
            }
            else {
                for (Entity p : bricks) {
                    int x = p.getX();
                    int y = p.getY();
                    if (x + 10 >= botX - 10 && x - 10 <= botX + 10) {
                        if (y + 16 >= botY - 10 && y - 18 <= botY + 10) {
                            ((Brick) p).destroy();
                            BombermanGame.map[x/32][y/32] = 1;
                        }
                    }
                }
                return;
            }
                for (Entity p : entities) {
                    int x = p.getX();
                    int y = p.getY();
                    if (x + 10 >= botX - 10 && x - 10 <= botX + 10) {
                        if (y + 16 >= botY - 15 && y - 18 <= botY + 15) {
                            ((Bomber) p).setKilled();
                        }
                    }
                }
            for (Entity p : bricks) {
                int x = p.getX();
                int y = p.getY();
                if (x + 10 >= botX - 10 && x - 10 <= botX + 10) {
                    if (y + 16 >= botY - 10 && y - 18 <= botY + 10) {
                        ((Brick) p).destroy();
                        BombermanGame.map[x/32][y/32] = 1;
                    }
                }
            }
                for (Enemy p : ballooms) {
                    double x = p.getX();
                    double y = p.getY();
                    if (x + 16 >= botX - 10 && x - 16 <= botX + 10) {
                        if (y + 16 >= botY - 15 && y - 16 <= botY + 15) {
                            p.setKilled();
                        }
                    }
                }
            for (Enemy p : oneAls) {
                double x = p.getX();
                double y = p.getY();
                if (x + 16 >= botX - 10 && x - 16 <= botX + 10) {
                    if (y + 16 >= botY - 15 && y - 16 <= botY + 15) {
                        p.setKilled();
                    }
                }
            }
            if(((Portal) BombermanGame.portal).covered() == false) {
                int x = BombermanGame.portal.getX();
                int y = BombermanGame.portal.getY();
                if (x + 15 >= botX - 10 && x - 15 <= botX + 10) {
                    if (y + 15 >= botY - 15 && y - 15 <= botY + 15) {
                        ((Portal) BombermanGame.portal).exploded = true;
                        System.out.println("no cong");
                    }
                }
            }
        }
    }
    public void clear() {
        bombs.remove(0);
        while(!explodes.isEmpty()) {
            explodes.remove(0);
        }
    }
    private boolean validBlock(int x, int y) {
        for (Entity p : walls) {
            if (p.getX() == x && p.getY() == y) {
                return false;
            }
        }
        for (Entity p : bricks) {
            if(p.getX() == x && p.getY() == y) {
                return false;
            }
        }
        return true;
    }
    @Override
    public void update() {
         if(setBomb) {
            img = Sprite.movingSprite(Sprite.bomb, Sprite.bomb_1, Sprite.bomb_2, animated++, 100 ).getFxImage();
         }
         if(bombExploded) {
             img = Sprite.movingSprite(Sprite.bomb_exploded, Sprite.bomb_exploded1, Sprite.bomb_exploded2, animated++, 100 ).getFxImage();
         }
    }
}
