package uet.oop.bomberman.entities;
import javafx.application.Platform;
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
   private boolean hasPower;
   private int animated =0;
   private int die_animated = 0;
    private boolean hasActiveBomb;
    private List<Entity> entities = new ArrayList<>();
    private List<Entity> walls = new ArrayList<>();
    private List<Entity> bombs = new ArrayList<>();
    private List<Entity> bricks= new ArrayList<>();
    private List<Entity> explodes = new ArrayList<>();
    private List<Entity> buffs = new ArrayList<>();
    public Bomber(int x, int y, Image img, List<Entity> entities,
                  List<Entity> walls, List<Entity> bombs,
                  List<Entity> explodes, List<Entity> bricks,
                  List<Entity> buffs) {
        super( x, y, img);
        this.entities = entities;
        this.walls = walls;
        this.bombs = bombs;
        this.explodes = explodes;
        this.bricks = bricks;
        this.buffs = buffs;
        hasActiveBomb = false;
        isAlive = true;
        hasPower = false;
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
       if (hasActiveBomb) {
           return;
       }
       Entity bomb = new Bomb(hasPower ? 2 : 1,entities, walls, bombs, explodes, bricks);

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
                   ((Bomb)bomb).clear();
               });
               Thread.sleep(100);
               hasActiveBomb = false;
           } catch (InterruptedException e) {
               e.printStackTrace();
           }
       }).start();

   }
   void setKilled() {
        isAlive = false;
   }
   public boolean isAlive() {
        return isAlive;
   }
//   public void checkBuff() {
//        for(Entity p : buffs) {
//            if(p.getX() == 0 && p.getY() == 0) {
//                return;
//            }
//            else if(this.x - 10 <= p.getX() + 10 && this.x +10 >= p.getX() -10) {
//                if(this.y +16 >= p.getY() - 10 && this.y -16 <= p.getY() +10) {
//                    hasPower = true;
//                    ((Buff) p).hide();
//                }
//            }
//        }
//   }
public void checkBuff() {
    List<Entity> buffed = new ArrayList<>();
        for(Entity p : buffs) {
         if(this.x - 10 <= p.getX() + 10 && this.x +10 >= p.getX() -10) {
            if(this.y +16 >= p.getY() - 10 && this.y -16 <= p.getY() +10) {
                hasPower = true;
                buffed.add(p);
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
       if(!bombs.isEmpty()) {
           if ( this.x -16 > bombs.get(0).getX() +15 || this.x + 6 <bombs.get(0).getX() -15) {
               if((x +6 >= bombs.get(0).getX() -16) && x -16 <=bombs.get(0).getX() +16) {
                   if((y +16 >bombs.get(0).getY() - 16) && (y -16 <bombs.get(0).getY() +16)) {
                       return  true;
                   }
               }
           }
           if (this.y - 16 > bombs.get(0).getY() + 15 || this.y + 16 <bombs.get(0).getY() -15) {
               if((x +6 >= bombs.get(0).getX() -16) && x -16 <=bombs.get(0).getX() +16) {
                   if((y +16 >bombs.get(0).getY() - 16) && (y -16 <bombs.get(0).getY() +16)) {
                       return  true;
                   }
               }
           }
       }
        return false;
    }
           @Override
    public void update() {
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
            if( die_animation == 100) {
                x= 0; y=0;
                img = Sprite.hide.getFxImage();
                return;
            }
             img = Sprite.movingSprite(Sprite.player_dead1, Sprite.player_dead2, Sprite.player_dead3, die_animation++, 200).getFxImage();
            die_animation++;
         }
    };   
    }
