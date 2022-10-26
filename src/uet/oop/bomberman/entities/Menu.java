package uet.oop.bomberman.entities;


import java.io.File;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class Menu {
    public int check1 = 0;
    public int check2 = 0;
    private Image img;
    private String menuFileName = new File("res/textures/Bomberman_Game.png").toURI().toString();
    private String pauseFileName = new File("res/textures/Pause.png").toURI().toString();
    private String loseFileName = new File("res/textures/lose.png").toURI().toString();
    private String winFileName = new  File("res/textures/win.png").toURI().toString();
    private Image menu = new Image(menuFileName);
    private Image pause = new Image(pauseFileName);
    private Image lose = new Image(loseFileName);
    private Image win = new Image(winFileName);
    public Menu(int type) {
        if(type == 0) {
            this.img = menu;
        }
        else if(type == 1) {
            this.img  = pause;
        }
        else if(type == 2) {
            this.img = lose;
        }
        else if(type == 3) {
            this.img = win;
        }
    }
    public void render(GraphicsContext gc) {
        gc.drawImage(img, 0, 0);
    }
}
