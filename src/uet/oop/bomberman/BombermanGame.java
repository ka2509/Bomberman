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
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import uet.oop.bomberman.entities.*;
import uet.oop.bomberman.graphics.Sprite;
import uet.oop.bomberman.graphics.SpriteSheet;


public class BombermanGame extends Application {

    public static final int WIDTH = 21;
    public static final int HEIGHT = 15;

    private GraphicsContext gc;
    private Canvas canvas;
    private List<Entity> entities = new ArrayList<>();
    private List<Entity> grass = new ArrayList<>();
    private List<Entity> walls = new ArrayList<>();
    private List<Entity> bomb = new ArrayList<>();
    private List<Entity> explodes = new ArrayList<>();
    private List<Entity> bricks = new ArrayList<>();
    private List<Entity> buffs = new ArrayList<>();
    public static void main(String[] args) {
        Application.launch(BombermanGame.class);
    }

    @Override
    public void start(Stage stage) {
        // Tao Canvas
        canvas = new Canvas(Sprite.SCALED_SIZE * WIDTH, Sprite.SCALED_SIZE * HEIGHT);
        gc = canvas.getGraphicsContext2D();

        // Tao root container
        Group root = new Group();
        root.getChildren().add(canvas);

        // Tao scene
        Scene scene = new Scene(root);

        // Them scene vao stage
        stage.setScene(scene);
        stage.show();
        createMap();

        Entity bomberman = new Bomber(1, 1, Sprite.player_right.getFxImage(), entities, walls, bomb, explodes, bricks, buffs);
        entities.add(bomberman);
        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long l) {
                update();
                render();
                if(((Bomber)bomberman).isAlive()) {initscene(scene, bomberman);}
            }
        };
        timer.start();
    }

    public void initscene(Scene scene, Entity bomberman) {
        scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if(((Bomber) bomberman).isAlive() == true) {
                    switch (event.getCode()) {
                        case UP:
                            ((Bomber) bomberman).goUp = true;
                            ((Bomber) bomberman).goDown = false;
                            ((Bomber) bomberman).goRight = false;
                            ((Bomber) bomberman).goLeft = false;
                            break;
                        case DOWN:
                            ((Bomber) bomberman).goDown = true;
                            ((Bomber) bomberman).goUp = false;
                            ((Bomber) bomberman).goRight = false;
                            ((Bomber) bomberman).goLeft = false;
                            break;
                        case RIGHT:
                            ((Bomber) bomberman).goRight = true;
                            ((Bomber) bomberman).goUp = false;
                            ((Bomber) bomberman).goDown = false;
                            ((Bomber) bomberman).goLeft = false;
                            break;
                        case LEFT:
                            ((Bomber) bomberman).goLeft = true;
                            ((Bomber) bomberman).goRight = false;
                            ((Bomber) bomberman).goDown = false;
                            ((Bomber) bomberman).goUp = false;
                            break;
                        case B:
                            ((Bomber) bomberman).placeBomb();
                    }
                }
            }
        });
         scene.setOnKeyReleased(new EventHandler<KeyEvent>()

    {
        @Override
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
                } else {
                    object = new Grass(i, j, Sprite.grass.getFxImage());
                    grass.add(object);
                }
            }
        }
            for (int i = 1; i < WIDTH - 2; i++) {
                for (int j = 1; j < HEIGHT-1; j++) {
                    Entity object;
                     if(i%2==0 && j%2==0) {
                        object = new Wall(i, j, Sprite.wall.getFxImage());
                        walls.add(object);
                    }
                     else if ((j == 1 && i >= 3) || (i == 1 && j >= 3) || (i >= 3 && j >= 3 )) {
                         if ((int) Math.floor(Math.random() * 100 + 1) <= 30) {
                             object = new Brick(i, j, Sprite.brick.getFxImage());
                             bricks.add(object);
                         }
                     }
                }
            }
           Entity tmp = bricks.get((int) Math.floor(Math.random() * (bricks.size())));
                Entity powerup_flames = new Buff(tmp.getX()/32, tmp.getY()/32, Sprite.powerup_flames.getFxImage());
                buffs.add(powerup_flames);
                System.out.println(powerup_flames.getX());
        System.out.println(powerup_flames.getY());
    }


    public void update() {
        if(!entities.isEmpty()) {
            entities.forEach(Entity::update);
        }
        bomb.forEach(Entity::update);
        bricks.forEach(Entity::update);
    }

    public void render() {
        gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
        grass.forEach(g -> g.render(gc));
        buffs.forEach(g -> g.render(gc));
        walls.forEach(g -> g.render(gc));
        bricks.forEach(g -> g.render(gc));
        entities.forEach(g -> g.render(gc));
        bomb.forEach(g -> g.render(gc));
        explodes.forEach(g -> g.render(gc));
    }
}
