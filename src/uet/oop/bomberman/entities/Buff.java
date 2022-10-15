package uet.oop.bomberman.entities;

import javafx.scene.image.Image;
import uet.oop.bomberman.graphics.Sprite;


public class Buff extends Entity{
    public Buff(int xUnit, int yUnit, Image img) {
        super(xUnit, yUnit, img);
    }
    public void hide() {
        x = 0; y = 0;
        img  = Sprite.hide.getFxImage();
    }
    @Override
    public void update() {

    }
}
