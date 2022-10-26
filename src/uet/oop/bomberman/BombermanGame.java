package uet.oop.bomberman;

import java.util.ArrayList;
import java.util.List;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

import uet.oop.bomberman.ai.PathFinder;
import uet.oop.bomberman.entities.*;
import uet.oop.bomberman.graphics.Sprite;
import uet.oop.bomberman.enemies.*;
import  uet.oop.bomberman.media.*;

public class BombermanGame extends Application {

    public static final int WIDTH = 21;
    public static final int HEIGHT = 15;
    public static int[][] map = new int[WIDTH][HEIGHT];
    private GraphicsContext gc;
    private Canvas canvas;
    public static PathFinder pFinder = new PathFinder();
    public static List<Entity> entities = new ArrayList<>();
    public static List<Entity> grass = new ArrayList<>();
    public static List<Entity> walls = new ArrayList<>();
    public static List<Entity> bomb = new ArrayList<>();
    public static List<Entity> explodes = new ArrayList<>();
    public static List<Entity> bricks = new ArrayList<>();
    private List<Entity> buffs = new ArrayList<>();
    public static List<Enemy> ballooms = new ArrayList<>();
    public static List<Enemy> oneAls = new ArrayList<>();
    private static List<Entity> timer = new ArrayList<>();
    public static Entity portal = new Portal();
    public static void main(String[] args) {
        Application.launch(BombermanGame.class);
    }
    private int play1timewinsound = 1;
    private int test = 1;
    private int soundWalk = 0;
    private boolean stopMenuMusic = false;
    public static boolean isDone = false;
    public static boolean isWin = false;
    private boolean pauseStatus = false;
   public static boolean replay = false;
    @Override
    public void start(Stage stage) {
        // Tao Canvas
        canvas = new Canvas(Sprite.SCALED_SIZE * WIDTH, Sprite.SCALED_SIZE * (HEIGHT));
        gc = canvas.getGraphicsContext2D();

        // Tao root container
        Group root = new Group();
        root.getChildren().add(canvas);

        // Tao menu
        Menu menu = new Menu(0);

        // Tao scene
        Scene scene = new Scene(root);

        // Them scene vao stage
        stage.setScene(scene);
        stage.show();

        menu.render(gc);
        GameMedia.setRestartSound();
        GameMedia.setWinSound();
        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                if(stopMenuMusic) {
                    this.stop();
                }
                if(GameMedia.getMainMenuSound() == null) {
                    GameMedia.setMainMenuSound();
                    GameMedia.getMainMenuSound().play();
                } else {
                    if(GameMedia.getMainMenuSound().getCurrentTime().equals(GameMedia.getMainMenuSound().getStopTime())) {
                        GameMedia.setMainMenuSoundToNull();
                    }
                }
                scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
                    @Override
                    public void handle(KeyEvent event) {
                        switch (event.getCode()) {
                            case ENTER:
                                GameMedia.getMainMenuSound().stop();
                                handleGamePlay(scene,stage);
                                stopMenuMusic = true;
                                break;
                            case ESCAPE:
                                stage.close();
                        }
                    }
                });
            }
        };
        timer.start();

    }
    public void handleGamePlay(Scene scene, Stage stage) {
        Entity bomberman = new Bomber(1, 1, Sprite.player_right.getFxImage(),
                    entities, walls, bomb,
                    explodes, bricks, buffs,
                    ballooms, oneAls);
            entities.add(bomberman);
            Entity aTime = new Counter(0, 0, Sprite.three.getFxImage(), timer);
            Entity bTime = new Counter(1, 0, Sprite.zero.getFxImage(), timer);
            Entity cTime = new Counter(2, 0, Sprite.zero.getFxImage(), timer);
            timer.add(aTime);
            timer.add(bTime);
            timer.add(cTime);
            AnimationTimer timer1 = new AnimationTimer() {
                @Override
                public void handle(long l) {
                    if(GameMedia.getThemeSound() == null) {
                        GameMedia.setThemeSound();
                        GameMedia.getThemeSound().play();
                    } else {
                        if(GameMedia.getThemeSound().getCurrentTime().equals(GameMedia.getThemeSound().getStopTime())) {
                            GameMedia.setThemeSoundToNull();
                        }
                    }
                    if(replay) {
                        this.stop();
                    }
                    else if(test >0){
                        createMap();
                        test--;
                    }
                    if(isWin) {
                        Menu win = new Menu(3);
                        GameMedia.getThemeSound().stop();
                        if(play1timewinsound > 0) {
                            GameMedia.getWinSound().play();
                            play1timewinsound--;
                        }
                        win.render(gc);
                        scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
                            @Override
                            public void handle(KeyEvent event) {
                                switch (event.getCode()) {
                                    case R:
                                        clearAll(bomberman);
                                        GameMedia.getWinSound().stop();
                                        GameMedia.getRestartSound().play();
                                        replay = true;
                                        break;
                                    case ESCAPE:
                                        stage.close();
                                        break;
                                }
                            }
                        });
                    }
                    else if (!isDone) {
                        if (!stopUpdate()) {
                            update();
                            render();
                            if (((Bomber) bomberman).isAlive()) {
                                initscene(scene, bomberman, stage);
                            }
                        }
                    } else if(isDone){
                        Menu lose = new Menu(2);
                        GameMedia.getThemeSound().stop();
                        lose.render(gc);
                        scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
                            @Override
                            public void handle(KeyEvent event) {
                                switch (event.getCode()) {
                                    case R:
                                        clearAll(bomberman);
                                        replay = true;
                                        GameMedia.getRestartSound().play();
                                        break;
                                    case ESCAPE:
                                        stage.close();
                                        break;
                                }
                            }
                        });
                    }
                }
            };
            timer1.start();
            AnimationTimer timer2 = new AnimationTimer() {
                @Override
                public void handle(long now) {
                    if(GameMedia.getThemeSound() == null) {
                        GameMedia.setThemeSound();
                        GameMedia.getThemeSound().play();
                    } else {
                        if(GameMedia.getThemeSound().getCurrentTime().equals(GameMedia.getThemeSound().getStopTime())) {
                            GameMedia.setThemeSoundToNull();
                        }
                    }
                    if(replay) {
                        if(test > 0) {
                            createMap();
                            GameMedia.getThemeSound().play();
                            test--;
                        }
                        if(isWin) {
                            Menu win = new Menu(3);
                            GameMedia.getThemeSound().stop();
                            if(play1timewinsound > 0) {
                                GameMedia.getWinSound().play();
                                play1timewinsound--;
                            }
                            win.render(gc);
                            scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
                                @Override
                                public void handle(KeyEvent event) {
                                    switch (event.getCode()) {
                                        case R:
                                            clearAll(bomberman);
                                            GameMedia.getWinSound().stop();
                                            GameMedia.getRestartSound().play();
                                            replay = true;
                                            break;
                                        case ESCAPE:
                                            stage.close();
                                            break;
                                    }
                                }
                            });
                        }
                        else if (!isDone) {
                            if (!stopUpdate()) {
                                update();
                                render();
                                if (((Bomber) bomberman).isAlive()) {
                                    initscene(scene, bomberman, stage);
                                }
                            }
                        } else if(isDone) {
                            Menu lose = new Menu(2);
                            lose.render(gc);
                            GameMedia.getThemeSound().stop();
                            scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
                                @Override
                                public void handle(KeyEvent event) {
                                    switch (event.getCode()) {
                                        case R:
                                            clearAll(bomberman);
                                            GameMedia.getRestartSound().play();
                                            replay = true;
                                            break;
                                        case ESCAPE:
                                            stage.close();
                                            break;
                                    }
                                }
                            });
                        }
                    }
                }
            };
            timer2.start();
    }

    public void clearAll(Entity bomberman) {
        for(int i =0; i< WIDTH; i++) {
            for(int j=0;j<HEIGHT;j++) {
                map[i][j] = 10;
            }
        }
        test = 1;
        play1timewinsound = 1;
        ballooms.clear();
        oneAls.clear();
        grass.clear();
        walls.clear();
        bomb.clear();
        explodes.clear();
        bricks.clear();
        buffs.clear();
        ((Counter)timer.get(0)).resetA();
        ((Counter)timer.get(1)).resetB();
        ((Counter)timer.get(2)).resetB();
        ((Bomber)bomberman).reset();
        isDone = false;
        isWin = false;
    }
    public boolean stopUpdate() {
        return pauseStatus;
    }
    public void initscene(Scene scene, Entity bomberman, Stage stage) {
        scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if (((Bomber) bomberman).isAlive()) {
                    switch (event.getCode()) {
                        case UP:
                            ((Bomber) bomberman).goUp = true;
                            ((Bomber) bomberman).goDown = false;
                            ((Bomber) bomberman).goRight = false;
                            ((Bomber) bomberman).goLeft = false;
                            soundWalk++;
                            if(soundWalk==4) {
                                GameMedia.setUpDownWalkSound();
                                GameMedia.getUpDownWalkSound().play();
                                soundWalk = 0;
                            }
                            break;
                        case DOWN:
                            ((Bomber) bomberman).goDown = true;
                            ((Bomber) bomberman).goUp = false;
                            ((Bomber) bomberman).goRight = false;
                            ((Bomber) bomberman).goLeft = false;
                            soundWalk++;
                            if(soundWalk==4) {
                                GameMedia.setUpDownWalkSound();
                                GameMedia.getUpDownWalkSound().play();
                                soundWalk = 0;
                            }
                            break;
                        case RIGHT:
                            ((Bomber) bomberman).goRight = true;
                            ((Bomber) bomberman).goUp = false;
                            ((Bomber) bomberman).goDown = false;
                            ((Bomber) bomberman).goLeft = false;
                            soundWalk++;
                            if(soundWalk==4) {
                                GameMedia.setLeftRightWalkSound();
                                GameMedia.getLeftRightWalkSound().play();
                                soundWalk = 0;
                            }

                            break;
                        case LEFT:
                            ((Bomber) bomberman).goLeft = true;
                            ((Bomber) bomberman).goRight = false;
                            ((Bomber) bomberman).goDown = false;
                            ((Bomber) bomberman).goUp = false;
                            soundWalk++;
                            if(soundWalk==4) {
                                GameMedia.setLeftRightWalkSound();
                                GameMedia.getLeftRightWalkSound().play();
                                soundWalk = 0;
                            }
                            break;
                        case B:
                                ((Bomber) bomberman).placeBomb();
                            break;
                        case P:
                            pauseStatus = true;
                            Menu pause = new Menu(1);
                            pause.render(gc);
                            break;
                        case R:
                            if(pauseStatus == true) {
                                pauseStatus = false;
                            }
                            break;
                        case ESCAPE:
                            stage.close();
                    }
                }
            }
        });
         scene.setOnKeyReleased(new EventHandler<KeyEvent>() {
        public void handle (KeyEvent event){
        switch (event.getCode()) {
            case UP:
                ((Bomber) bomberman).goUp = false;
                ((Bomber) bomberman).standUp();
                break;
            case DOWN:
                ((Bomber) bomberman).goDown = false;
                ((Bomber) bomberman).standDown();
                break;
            case RIGHT:
                ((Bomber) bomberman).goRight = false;
                ((Bomber) bomberman).standRight();
                break;
            case LEFT:
                ((Bomber) bomberman).goLeft = false;
                ((Bomber) bomberman).standLeft();
                break;
        }
    }
    });
}
    public void createMap() {
        for (int i = 0; i < WIDTH; i++) {
            for (int j = 0; j < HEIGHT; j++) {
                Entity object;
                if (j == 0 || j == HEIGHT - 1 || i == 0 || i == WIDTH - 1) {
                    object = new Wall(i, j, Sprite.wall.getFxImage());
                    walls.add(object);
                    map[i][j] = 0;
                } else {
                    object = new Grass(i, j, Sprite.grass.getFxImage());
                    grass.add(object);
                    map[i][j] = 1;
                }
            }
        }
            for (int i = 1; i < WIDTH - 2; i++) {
                for (int j = 1; j < HEIGHT-1; j++) {
                    Entity object;
                     if(i%2==0 && j%2==0) {
                        object = new Wall(i, j, Sprite.wall.getFxImage());
                        walls.add(object);
                        map[i][j] = 0;
                    }
                     else if ((j == 1 && i >= 3) || (i == 1 && j >= 3) || (i >= 3 && j >= 3 )) {
                         if ((int) Math.floor(Math.random() * 100 + 1) <= 30) {
                             object = new Brick(i, j, Sprite.brick.getFxImage());
                             bricks.add(object);
                             map[i][j] = 2;
                         }
                     }
                }
            }
            for(int i = 4; i < WIDTH - 2; i++) {
                for(int j = 4; j < HEIGHT - 2; j++) {
                    if(map[i][j] == 1 && (int) Math.floor(Math.random() * 100 + 1) <= 25) {
                        if(ballooms.size() < 5) {
                            ballooms.add(new Balloom(i, j, Sprite.balloom_right1.getFxImage(), bricks, walls, bomb));
                            i+=2;
                            j+=2;
                        }
                    }
                    else if(i > 5 && j > 5 && map[i][j] == 1 && (int) Math.floor(Math.random() * 100 + 1) <= 50) {
                        if(oneAls.size() == 0) {
                            oneAls.add(new oneAl(i, j, Sprite.oneal_right1.getFxImage(), bricks, walls, bomb,pFinder,entities));
                        }
                    }
                }
            }
        Entity tmp1 = bricks.get((int) Math.floor(Math.random() * (bricks.size())));
        Entity tmp2 = bricks.get((int) Math.floor(Math.random() * (bricks.size())));
        Entity tmp3 = bricks.get((int) Math.floor(Math.random() * (bricks.size())));
        Entity powerup_flames = new Buff(tmp1.getX()/32, tmp1.getY()/32, Sprite.powerup_flames.getFxImage());
        Entity powerup_bombs = new Buff(tmp2.getX()/32, tmp2.getY()/32, Sprite.powerup_bombs.getFxImage());
        portal.setX(tmp3.getX());
        portal.setY(tmp3.getY());
        portal.setImg(Sprite.portal.getFxImage());
        buffs.add(powerup_flames);
        buffs.add(powerup_bombs);
        System.out.println(portal.getX());
        System.out.println(portal.getY());
        System.out.println(powerup_flames.getX());
        System.out.println(powerup_flames.getY());
        System.out.println(powerup_bombs.getX());
        System.out.println(powerup_bombs.getY());
    }

    public void update() {
        entities.forEach(Entity::update);
        bomb.forEach(Entity::update);
        bricks.forEach(Entity::update);
        ballooms.forEach(Enemy::update);
        oneAls.forEach(Enemy::update);
        timer.forEach(Entity::update);
        portal.update();
    }

    public void render() {
        gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
        grass.forEach(g -> g.render(gc));
        buffs.forEach(g -> g.render(gc));
        portal.render(gc);
        walls.forEach(g -> g.render(gc));
        bricks.forEach(g -> g.render(gc));
        entities.forEach(g -> g.render(gc));
        bomb.forEach(g -> g.render(gc));
        explodes.forEach(g -> g.render(gc));
        ballooms.forEach(g -> g.render(gc));
        oneAls.forEach(g -> g.render(gc));
        timer.forEach(g -> g.render(gc));
    }
}
