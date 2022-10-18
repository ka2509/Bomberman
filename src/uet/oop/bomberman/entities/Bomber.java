package uet.oop.bomberman.entities;
import javafx.application.Platform;
import uet.oop.bomberman.enemies.Balloom;
import uet.oop.bomberman.enemies.Enemy;
import uet.oop.bomberman.graphics.Sprite;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.SnapshotParameters;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;

import javax.swing.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Bomber extends Entity {
   public boolean goUp;
   private int die_animation = 0;
   public boolean goDown;
   public boolean goRight;
   public boolean goLeft;
   private boolean  isAlive;
   private boolean hasFlame;
   private boolean hasBombs;
   private int animated =0;
   private int die_animated = 0;
    private boolean hasActiveBomb;
    private boolean canActiveBomb2;
    private List<Entity> entities = new ArrayList<>();
    private List<Entity> walls = new ArrayList<>();
    private List<Entity> bombs = new ArrayList<>();
    private List<Entity> bricks= new ArrayList<>();
    private List<Entity> explodes = new ArrayList<>();
    private List<Entity> buffs = new ArrayList<>();
    private List<Enemy> ballooms = new ArrayList<>();
    public Bomber(int x, int y, Image img, List<Entity> entities,
                  List<Entity> walls, List<Entity> bombs,
                  List<Entity> explodes, List<Entity> bricks,
                  List<Entity> buffs, List<Enemy> ballooms) {
        super( x, y, img);
        this.entities = entities;
        this.walls = walls;
        this.bombs = bombs;
        this.explodes = explodes;
        this.bricks = bricks;
        this.buffs = buffs;
        this.ballooms = ballooms;
        hasActiveBomb = false;
        isAlive = true;
        hasFlame = false;
        hasBombs = false;
        canActiveBomb2 = false;
    } 
   public void standUp() {
      img = Sprite.player_up.getFxImage();
   }
   public void standRight() {
      img = Sprite.player_right.getFxImage();
   }
   public void standLeft() {
      img = Sprite.player_left.getFxImage();
   }
   public void standDown() {
      img = Sprite.player_down.getFxImage();
   }
   public void placeBomb() {
           Entity bomb = new Bomb(hasFlame ? 2 : 1, entities, walls, bombs, explodes, bricks, ballooms);
           if (!hasBombs && hasActiveBomb) {
               return;
           }
           else if (hasBombs && bombs.size() ==2) {
               return;
           }
           if(hasActiveBomb == false && hasBombs == false) {
               bomb.x = Sprite.SCALED_SIZE * (x / Sprite.SCALED_SIZE);
               bomb.y = Sprite.SCALED_SIZE * (y / Sprite.SCALED_SIZE);
               new Thread(() -> {
                   try {
                       hasActiveBomb = true;
                       Platform.runLater(() -> {
                           ((Bomb) bomb).setBomb(bomb);
                       });
                       Thread.sleep(1500);
                       Platform.runLater(() -> {
                           ((Bomb) bomb).explode();
                       });
                       Thread.sleep(300);
                       Platform.runLater(() -> {
                           ((Bomb) bomb).clear();
                       });
                       Thread.sleep(100);
                       hasActiveBomb = false;
                   } catch (InterruptedException e) {
                       e.printStackTrace();
                   }
               }).start();
               return;
           }
           if(hasActiveBomb == false && hasBombs == true) {
               bomb.x = Sprite.SCALED_SIZE * (x / Sprite.SCALED_SIZE);
               bomb.y = Sprite.SCALED_SIZE * (y / Sprite.SCALED_SIZE);
               new Thread(() -> {
                   try {
                       hasActiveBomb = true;
                       Platform.runLater(() -> {
                           ((Bomb) bomb).setBomb(bomb);
                       });
                       Thread.sleep(500);
                       if(hasBombs) {
                           canActiveBomb2 = true;
                       }
                       Thread.sleep(1000);
                       Platform.runLater(() -> {
                           ((Bomb) bomb).explode();
                       });
                       Thread.sleep(300);
                       Platform.runLater(() -> {
                           ((Bomb) bomb).clear();
                       });
                       Thread.sleep(100);
                       hasActiveBomb = false;
                       canActiveBomb2 = false;
                   } catch (InterruptedException e) {
                       e.printStackTrace();
                   }
               }).start();
           }
           else if (canActiveBomb2 == true) {
           bomb.x = Sprite.SCALED_SIZE * (x / Sprite.SCALED_SIZE);
           bomb.y = Sprite.SCALED_SIZE * (y / Sprite.SCALED_SIZE);
           new Thread(() -> {
               try {
                   Platform.runLater(() -> {
                       ((Bomb) bomb).setBomb(bomb);
                   });
                   Thread.sleep(1500);
                   Platform.runLater(() -> {
                       ((Bomb) bomb).explode();
                   });
                   Thread.sleep(300);
                   Platform.runLater(() -> {
                       ((Bomb) bomb).clear();
                   });
                   Thread.sleep(100);
               } catch (InterruptedException e) {
                   e.printStackTrace();
               }
           }).start();
       }

   }
   void setKilled() {
        isAlive = false;
   }
   public boolean isAlive() {
        return isAlive;
   }
public void checkBuff() {
    List<Entity> buffed = new ArrayList<>();
        for(int i = 0; i < buffs.size(); i++) {
         Entity p = buffs.get(i);
            if(this.x - 10 <= p.getX() + 10 && this.x +10 >= p.getX() -10) {
            if(this.y +16 >= p.getY() - 10 && this.y -16 <= p.getY() +10) {
                switch (i) {
                    case 0 : hasFlame = true; buffed.add(p); break;
                    case 1 : hasBombs = true; buffed.add(p); break;
                }
            }
        }
    }
    buffs.removeAll(buffed);
}
   private boolean imPassable(int x, int y) {
        if(x < 32 || y < 32 || x > 616 || y > 416  ) {
            return true;
        }
       for(Entity p : walls) {
           if((x +6 > p.getX() -16) && x -16 < p.getX() +16) {
               if((y +16 >p.getY() - 16) && (y -16 <p.getY() +16)) {
                   if(goRight || goLeft) {
                       if(y - p.getY() > 10) {
                           this.y += 1;
                       } else if (y - p.getY() < -10) {
                           this.y -= 1;
                       }
                   }
                   if(goDown || goUp) {
                       if(x - p.getX() > 10) {
                           this.x += 1;
                       } else if (x - p.getX() < -11) {
                           this.x -= 1;
                       }
                   }
                   return  true;
               }
           }
       }
       for(Entity p : bricks) {
           if((x +6 > p.getX() -16) && x -16 < p.getX() +16) {
               if((y +16 >p.getY() - 16) && (y -16 <p.getY() +16)) {
                   if(goRight || goLeft) {
                       if(y - p.getY() > 8) {
                           this.y += 1;
                       } else if (y - p.getY() < -8) {
                           this.y -= 1;
                       }
                   }
                   if(goDown || goUp) {
                       if(x - p.getX() > 8) {
                           this.x += 1;
                       } else if (x - p.getX() < -9) {
                           this.x -= 1;
                       }
                   }
                   return  true;
               }
           }
       }
       for(Entity p : bombs) {
           if ( this.x -16 > p.getX() +15 || this.x + 6 <p.getX() -15 || this.y - 16 > p.getY() + 15 || this.y + 16 <p.getY() -15) {
               if((x +6 >= p.getX() -16) && x -16 <=p.getX() +16) {
                   if((y +16 >p.getY() - 16) && (y -16 <p.getY() +16)) {
                       return  true;
                   }
               }
           }
       }
        return false;
    }
    private void clearDeadEntity() {
        List<Entity>brick = new ArrayList<>();
        for(Entity p : bricks) {
            if(p.getX() == 0 && p.getY() == 0) {
                brick.add(p);
            }
        }
        bricks.removeAll(brick);
        List<Enemy>balloom = new ArrayList<>();
        for(Enemy p : ballooms) {
            if(p.getX() == 0 && p.getY() == 0) {
                balloom.add(p);
            }
        }
        ballooms.removeAll(balloom);
    }
    private void handleKill() {
        for(Enemy p : ballooms) {
            if (p.getX() - 16 < x +10 && p.getX() + 16 > x - 10) {
                if (p.getY() + 16 > y - 16 && p.getY() - 16 < y + 16) {
                    setKilled();
                }
            }
        }
    }
           @Override
    public void update() {
               handleKill();
        if(goUp) {
            img = Sprite.movingSprite(Sprite.player_up,Sprite.player_up_1, Sprite.player_up_2, animated++, 40).getFxImage();
            if(!imPassable(x, y-1)) {
                y -=1;
               // checkBuff();
            }
//             System.out.println("Up:" + x);
//             System.out.println(y);
         }
         if(goRight) {
            img = Sprite.movingSprite(Sprite.player_right,Sprite.player_right_1, Sprite.player_right_2, animated++, 40).getFxImage();
            if(!imPassable(x+1,y)) {
                x += 1;
                //checkBuff();
            }
//             System.out.println("Right:" + x);
//             System.out.println(y);
         }
         if(goLeft) {
            img = Sprite.movingSprite(Sprite.player_left,Sprite.player_left_1, Sprite.player_left_2, animated++, 40).getFxImage();
            if(!imPassable(x-1,y)) {
                x -=1;
                //checkBuff();
            }
//             System.out.println("Left:" + x);
//             System.out.println(y);
         }
         if(goDown) {
            img = Sprite.movingSprite(Sprite.player_down,Sprite.player_down_1, Sprite.player_down_2, animated++,40).getFxImage();
            if(!imPassable(x,y+1)) {
                y += 1;
                //checkBuff();
            }
//             System.out.println("Down" + x);
//             System.out.println(y);
         }
         if(!buffs.isEmpty()) {
             checkBuff();
         }
         if(!isAlive()) {
            if( die_animation == 200) {
                x= 0; y=0;
                img = Sprite.hide.getFxImage();
                return;
            }
             img = Sprite.movingSprite(Sprite.player_dead1, Sprite.player_dead2, Sprite.player_dead3, die_animation++, 400).getFxImage();
            die_animation++;
         }
         clearDeadEntity();
    };   
    }
