package uet.oop.bomberman.enemies;

import javafx.scene.canvas.GraphicsContext;
import uet.oop.bomberman.graphics.Sprite;
import javafx.scene.image.Image;


public abstract class Enemy {
    protected double x;
    protected double y;
    protected Image img;
    protected boolean isAlive;
    public Enemy( int xUnit, int yUnit,Image img) {
        this.x = xUnit * Sprite.SCALED_SIZE;
        this.y = yUnit * Sprite.SCALED_SIZE;
        this.img = img;
        isAlive = true;
    }
    public abstract void update();
    public void render(GraphicsContext gc) {
        gc.drawImage(img, x, y);
    }
    public double getX() {
        return x;
    }
    public double getY() {
        return y;
    }
    public void setX(int x) {
        this.x = x;
    }
    public void setY(int x) {
        this.y = x;
    }
}
