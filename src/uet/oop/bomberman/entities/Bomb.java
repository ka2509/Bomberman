package uet.oop.bomberman.entities;

import javafx.application.Platform;
import javafx.scene.image.Image;
import uet.oop.bomberman.BombermanGame;
import uet.oop.bomberman.enemies.Balloom;
import uet.oop.bomberman.enemies.Enemy;
import uet.oop.bomberman.enemies.oneAl;
import uet.oop.bomberman.graphics.Sprite;
import uet.oop.bomberman.graphics.SpriteSheet;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

public class Bomb  extends Entity{
    private int animated;
    private boolean setBomb = false;
    private boolean bombExploded =false;
    private List<Entity> entities = new ArrayList<>();
    private List<Entity> walls = new ArrayList<>();
    private List<Entity> bombs = new ArrayList<>();
    private List<Entity> explodes = new ArrayList<>();
    private List<Entity> bricks= new ArrayList<>();
    private List<Enemy> ballooms = new ArrayList<>();
    private List<Enemy> oneAls = new ArrayList<>();
    private int bombCount = 0;
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
        bombExploded = true;
        expLeft();
        expRight();
        expUp();
        expDown();
        for (Entity p : entities) {
            if (p.getX() - 10 <= x +16 && p.getX() + 10 >= x - 16) {
                if (p.getY() + 16 >= y - 10 && p.getY() - 16 <= y + 10) {
                    ((Bomber) p).setKilled();
                }
            }
        }
        for (Enemy p : ballooms) {
            if (p.getX() - 16 <= x +16 && p.getX() + 16 >= x - 16) {
                if (p.getY() + 16 >= y - 10 && p.getY() - 16 <= y + 10) {
                     p.setKilled();
                }
            }
        }
        for (Enemy p : oneAls) {
            if (p.getX() - 16 <= x +16 && p.getX() + 16 >= x - 16) {
                if (p.getY() + 16 >= y - 10 && p.getY() - 16 <= y + 10) {
                    p.setKilled();
                }
            }
        }
    }

    public void expLeft() {
        for(int i = 1 ; i<=radius; i++) {
            Entity left  = new tmpObject(x/Sprite.SCALED_SIZE-i, y/Sprite.SCALED_SIZE, Sprite.explosion_horizontal.getFxImage());
            if(validBlock(x-32,y)) {
                if(validBlock(x-32*i,y)) {
                    explodes.add(left);
                }
            }
            else {
                for (Entity p : bricks) {
                    if (p.getX() - 10 >= left.getX() - 36 && p.getX() + 10 <= left.getX() + 48) {
                        if (p.getY() + 16 >= left.getY() - 10 && p.getY() - 16 <= left.getY() + 10) {
                            ((Brick) p).destroy();
                        }
                    }
                }
                return;
            }
                for (Entity p : entities) {
                    if (p.getX() - 10 <= left.getX() +16 && p.getX() + 10 >= left.getX() - 16) {
                        if (p.getY() + 16 >= left.getY() - 10 && p.getY() - 16 <= left.getY() + 10) {
                            ((Bomber) p).setKilled();
                        }
                    }
                }
            for (Entity p : bricks) {
                if (p.getX() - 10 >= left.getX() - 36 && p.getX() + 10 <= left.getX() + 48) {
                    if (p.getY() + 16 >= left.getY() - 10 && p.getY() - 16 <= left.getY() + 10) {
                        ((Brick) p).destroy();
                    }
                }
            }
            for (Enemy p : ballooms) {
                if (p.getX() - 10 <= left.getX() +16 && p.getX() + 10 >= left.getX() - 16) {
                    if (p.getY() + 16 >= left.getY() - 10 && p.getY() - 16 <= left.getY() + 10) {
                         p.setKilled();
                    }
                }
            }
            for (Enemy p :oneAls) {
                if (p.getX() - 10 <= left.getX() +16 && p.getX() + 10 >= left.getX() - 16) {
                    if (p.getY() + 16 >= left.getY() - 10 && p.getY() - 16 <= left.getY() + 10) {
                        p.setKilled();
                    }
                }
            }
        }
    }
    public void expRight() {
        for(int i = 1; i <= radius; i++) {
            Entity right = new tmpObject(x/Sprite.SCALED_SIZE+i, y/Sprite.SCALED_SIZE, Sprite.explosion_horizontal.getFxImage());
            if(validBlock(x+32,y)) {
                if(validBlock(x+32*i,y)) {
                    explodes.add(right);
                }
            }
            else {
                for (Entity p : bricks) {
                    if (p.getX() - 10 >= right.getX() - 16 && p.getX() + 10 <= right.getX() + 36) {
                        if (p.getY() + 16 >= right.getY() - 10 && p.getY() - 16 <= right.getY() + 10) {
                            ((Brick) p).destroy();
                        }
                    }
                }
                return;
            }
                for (Entity p : entities) {
                    if (p.getX() - 10 <= right.getX() + 16 && p.getX() + 10 >= right.getX() - 16) {
                        if (p.getY() + 16 >= right.getY() - 10 && p.getY() - 16 <= right.getY() + 10) {
                            ((Bomber) p).setKilled();
                        }
                    }
                }
            for (Entity p : bricks) {
                if (p.getX() - 10 >= right.getX() - 16 && p.getX() + 10 <= right.getX() + 36) {
                    if (p.getY() + 16 >= right.getY() - 10 && p.getY() - 16 <= right.getY() + 10) {
                        ((Brick) p).destroy();
                    }
                }
            }
            for (Enemy p : ballooms) {
                if (p.getX() - 10 <= right.getX() + 16 && p.getX() + 10 >= right.getX() - 16) {
                    if (p.getY() + 16 >= right.getY() - 10 && p.getY() - 16 <= right.getY() + 10) {
                        p.setKilled();
                    }
                }
            }
            for (Enemy p : oneAls) {
                if (p.getX() - 10 <= right.getX() + 16 && p.getX() + 10 >= right.getX() - 16) {
                    if (p.getY() + 16 >= right.getY() - 10 && p.getY() - 16 <= right.getY() + 10) {
                        p.setKilled();
                    }
                }
            }
        }
    }
    public void expUp() {
        for(int i = 1; i <= radius; i++) {
            Entity top = new tmpObject(x/Sprite.SCALED_SIZE, y/Sprite.SCALED_SIZE-i, Sprite.explosion_vertical.getFxImage());
            if(validBlock(x,y-32)) {
                if(validBlock(x,y-32*i)) {
                    explodes.add(top);
                }
            }
            else {
                for (Entity p : bricks) {
                    if (p.getX() + 10 >= top.getX() - 10 && p.getX() - 10 <= top.getX() + 10) {
                        if (p.getY() + 16 >= top.getY() - 10 && p.getY() - 17 <= top.getY() + 10) {
                            ((Brick) p).destroy();
                        }
                    }
                }
                return;
            }
                for (Entity p : entities) {
                    if (p.getX() + 10 >= top.getX() - 10 && p.getX() - 10 <= top.getX() + 10) {
                        if (p.getY() + 16 >= top.getY() - 10 && p.getY() - 17 <= top.getY() + 10) {
                            ((Bomber) p).setKilled();
                        }
                    }
                }
            for (Entity p : bricks) {
                if (p.getX() + 10 >= top.getX() - 10 && p.getX() - 10 <= top.getX() + 10) {
                    if (p.getY() + 16 >= top.getY() - 10 && p.getY() - 17 <= top.getY() + 10) {
                        ((Brick) p).destroy();
                    }
                }
            }
            for (Enemy p : ballooms) {
                if (p.getX() + 16 >= top.getX() - 10 && p.getX() - 16 <= top.getX() + 10) {
                    if (p.getY() + 16 >= top.getY() - 10 && p.getY() - 16 <= top.getY() + 10) {
                        p.setKilled();
                    }
                }
            }
            for (Enemy p : oneAls) {
                if (p.getX() + 16 >= top.getX() - 10 && p.getX() - 16 <= top.getX() + 10) {
                    if (p.getY() + 16 >= top.getY() - 10 && p.getY() - 16 <= top.getY() + 10) {
                        p.setKilled();
                    }
                }
            }
        }
    }
    public void expDown() {
        for(int i = 1; i <= radius; i++) {
            Entity bot = new tmpObject(x/Sprite.SCALED_SIZE, y/Sprite.SCALED_SIZE +i, Sprite.explosion_vertical.getFxImage());
            if(validBlock(x, y+32)) {
                if(validBlock(x, y+32*i)) {
                    explodes.add(bot);
                }
            }
            else {
                for (Entity p : bricks) {
                    if (p.getX() + 10 >= bot.getX() - 10 && p.getX() - 10 <= bot.getX() + 10) {
                        if (p.getY() + 16 >= bot.getY() - 10 && p.getY() - 18 <= bot.getY() + 10) {
                            ((Brick) p).destroy();
                        }
                    }
                }
                return;
            }
                for (Entity p : entities) {
                    if (p.getX() + 10 >= bot.getX() - 10 && p.getX() - 10 <= bot.getX() + 10) {
                        if (p.getY() + 16 >= bot.getY() - 10 && p.getY() - 18 <= bot.getY() + 10) {
                            ((Bomber) p).setKilled();
                        }
                    }
                }
            for (Entity p : bricks) {
                if (p.getX() + 10 >= bot.getX() - 10 && p.getX() - 10 <= bot.getX() + 10) {
                    if (p.getY() + 16 >= bot.getY() - 10 && p.getY() - 18 <= bot.getY() + 10) {
                        ((Brick) p).destroy();
                    }
                }
            }
                for (Enemy p : ballooms) {
                    if (p.getX() + 16 >= bot.getX() - 10 && p.getX() - 16 <= bot.getX() + 10) {
                        if (p.getY() + 16 >= bot.getY() - 10 && p.getY() - 16 <= bot.getY() + 10) {
                            p.setKilled();
                        }
                    }
                }
            for (Enemy p : oneAls) {
                if (p.getX() + 16 >= bot.getX() - 10 && p.getX() - 16 <= bot.getX() + 10) {
                    if (p.getY() + 16 >= bot.getY() - 10 && p.getY() - 16 <= bot.getY() + 10) {
                        p.setKilled();
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
