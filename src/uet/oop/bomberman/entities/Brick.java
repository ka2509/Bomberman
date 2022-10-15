package uet.oop.bomberman.entities;

import javafx.scene.image.Image;
import uet.oop.bomberman.graphics.Sprite;

public class Brick extends Entity {
    private boolean destroyed;
    private int animated = 0;
    private int destroy_animation = 0;
    public Brick(int x, int y, Image image) {
        super(x, y, image);
    }
    public void destroy() {
        destroyed = true;
    }
    public boolean isDestroyed() {
        return  destroyed;
    }
    @Override
    public void update() {
        if(isDestroyed()) {
            if(destroy_animation == 100) {
                x = 0; y = 0;
                img = Sprite.hide.getFxImage();
                return;
            }
                img = Sprite.movingSprite(Sprite.brick_exploded, Sprite.brick_exploded1, Sprite.brick_exploded2, animated++, 100).getFxImage();
                destroy_animation++;
        }
    }
}
