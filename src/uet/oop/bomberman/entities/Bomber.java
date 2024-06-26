package uet.oop.bomberman.entities;

import javafx.application.Platform;
import javafx.scene.image.Image;

import uet.oop.bomberman.enemies.Enemy;
import uet.oop.bomberman.graphics.Sprite;
import uet.oop.bomberman.BombermanGame;
import uet.oop.bomberman.media.GameMedia;

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
   private int animated ;
    private boolean hasActiveBomb;
    private boolean canActiveBomb2;
    private List<Entity> entities;
    private List<Entity> walls;
    private List<Entity> bombs;
    private List<Entity> bricks;
    private List<Entity> explodes;
    private List<Entity> buffs;
    private List<Enemy> ballooms;
    private List<Enemy> oneAls;
    private int oneDiesound;
    public Bomber(int x, int y, Image img, List<Entity> entities,
                  List<Entity> walls, List<Entity> bombs,
                  List<Entity> explodes, List<Entity> bricks,
                  List<Entity> buffs, List<Enemy> ballooms,
                  List<Enemy> oneAls) {
        super( x, y, img);
        animated = 0;
        this.entities = entities;
        this.walls = walls;
        this.bombs = bombs;
        this.explodes = explodes;
        this.bricks = bricks;
        this.buffs = buffs;
        this.ballooms = ballooms;
        this.oneAls = oneAls;
        hasActiveBomb = false;
        isAlive = true;
        hasFlame = false;
        hasBombs = false;
        canActiveBomb2 = false;
        oneDiesound = 1;
    }
    public Bomber(){}
    public void reset() {
        x = 32;
        y = 32;
        img  = Sprite.player_right.getFxImage();
        hasActiveBomb = false;
        isAlive = true;
        hasFlame = false;
        hasBombs = false;
        canActiveBomb2 = false;
        animated = 0;
        die_animation = 0;
        oneDiesound = 1;
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
           Entity bomb = new Bomb(hasFlame ? 2 : 1, entities,
                   walls, bombs, explodes,
                   bricks, ballooms, oneAls);
           if (!hasBombs && hasActiveBomb) {
               return;
           }
           else if (hasBombs && bombs.size() ==2) {
               return;
           }
           if(hasActiveBomb == false && hasBombs == false) {
               bomb.x = Sprite.SCALED_SIZE * (x / Sprite.SCALED_SIZE);
               bomb.y = Sprite.SCALED_SIZE * (y / Sprite.SCALED_SIZE);
               BombermanGame.map[x / Sprite.SCALED_SIZE][y / Sprite.SCALED_SIZE] = 3;
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
                       canActiveBomb2 = true;
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
            if (canActiveBomb2 == true) {
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
   public void setKilled() {
        isAlive = false;
   }
   public boolean isAlive() {
        return isAlive;
   }
private void checkBuff() {
    List<Entity> buffed = new ArrayList<>();
        for(int i = 0; i < buffs.size(); i++) {
         Entity p = buffs.get(i);
            if(this.x - 10 <= p.getX() + 10 && this.x +10 >= p.getX() -10) {
            if(this.y +16 >= p.getY() - 10 && this.y -16 <= p.getY() +10) {
                GameMedia.setGetPowerUpSound();
                GameMedia.getGetPowerUpSound().play();
                switch (i) {
                    case 0 :
                        if(hasFlame == false) {
                            hasFlame = true;
                        }
                        else {
                            hasBombs = true;
                        }
                        buffed.add(p);
                        break;
                    case 1 : hasBombs = true; buffed.add(p); break;
                    default: break;
                }
            }
        }
    }
    buffs.removeAll(buffed);
}
   private boolean imPassable(int x, int y) {
        if(x < 32 || y < 32 || x > 8 + (BombermanGame.WIDTH - 2)*32 || y > (BombermanGame.HEIGHT - 2)*32) {
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
            int dieX = p.getX();
            int dieY = p.getY();
            if(dieX == 0 && dieY == 0) {
                brick.add(p);
            }
        }
        bricks.removeAll(brick);
        List<Enemy>balloom = new ArrayList<>();
        for(Enemy p : ballooms) {
            double dieX = p.getX();
            double dieY = p.getY();
            if(dieX == 0 && dieY == 0) {
                balloom.add(p);
            }
        }
        ballooms.removeAll(balloom);
        List<Enemy>oneAl = new ArrayList<>();
        for(Enemy p : oneAl) {
            double dieX = p.getX();
            double dieY = p.getY();
            if(dieX == 0 && dieY == 0) {
                oneAl.add(p);
            }
        }
        oneAls.removeAll(oneAl);
    }
    private void handleStatus() {
        for(Enemy p : ballooms) {
            if (p.getX() - 16 < x +10 && p.getX() + 16 > x - 10) {
                if (p.getY() + 16 > y - 16 && p.getY() - 16 < y + 16) {
                    setKilled();
                }
            }
        }
        for(Enemy p : oneAls) {
            if (p.getX() - 16 < x +10 && p.getX() + 16 > x - 10) {
                if (p.getY() + 16 > y - 16 && p.getY() - 16 < y + 16) {
                    setKilled();
                }
            }
        }
        if(((Portal)BombermanGame.portal).opened == true) {
            if (BombermanGame.portal.getX() - 15 < x + 10 && BombermanGame.portal.getX() + 15 > x - 10) {
                if (BombermanGame.portal.getY() + 15 > y - 16 && BombermanGame.portal.getY() - 15 < y + 16) {
                    BombermanGame.isWin = true;
                }
            }
        }
    }
           @Override
    public void update() {
               if(!isAlive()) {
                   if(oneDiesound > 0) {
                       GameMedia.setGameOverSound();
                       GameMedia.getGameOverSound().play();
                       oneDiesound--;
                   }
                   if( die_animation == 200) {
                       x= 0; y=0;
                       img = Sprite.hide.getFxImage();
                       BombermanGame.isDone = true;
                       return;
                   }
                   img = Sprite.movingSprite(Sprite.player_dead1, Sprite.player_dead2, Sprite.player_dead3, die_animation++, 400).getFxImage();
                   die_animation++;
               }
               else {
                   handleStatus();
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
                           //x += 1;
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
                           BombermanGame.isDone = true;
                           return;
                       }
                       img = Sprite.movingSprite(Sprite.player_dead1, Sprite.player_dead2, Sprite.player_dead3, die_animation++, 400).getFxImage();
                       die_animation++;
                   }
               }

         clearDeadEntity();
    };   
    }
