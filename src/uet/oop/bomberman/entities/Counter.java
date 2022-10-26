package uet.oop.bomberman.entities;

import javafx.scene.image.Image;
import uet.oop.bomberman.BombermanGame;
import uet.oop.bomberman.graphics.Sprite;

import java.util.ArrayList;
import java.util.List;

public class Counter extends Entity{
    private int count = 0;
    private int number = 9;
    private int number1 = 9;
    private int number3 = 2;
    private List<Entity> timer;

    public Counter(int xUnit, int yUnit, Image img, List<Entity> timer) {
        super(xUnit, yUnit, img);
        this.timer = timer;
    }
    public Counter(){}
    public void resetA() {
        count =0;
        number = 9;
        number1 =9;
        number3 =2;
        img  = Sprite.three.getFxImage();
    }
public void resetB() {
    count =0;
    number = 9;
    number1 =9;
    number3 =2;
    img  = Sprite.zero.getFxImage();
}
    @Override
    public void update() {
            count++;
            if(count == 120) {
            timer.get(2).img = Sprite.numbersArray[number].getFxImage();
            if(number == 9) {
                timer.get(1).img = Sprite.numbersArray[number1].getFxImage();
                if(number1 == 9) {
                    timer.get(0).img = Sprite.numbersArray[number3].getFxImage();
                        number3--;
                }
                number1--;
            }
            count = 0;
            number--;
//            if(number == 0 && number1 == 0 && number3 == 0) {
//                BombermanGame.isDone = true;
//            }
//            if(number < 0) {
//                number = 9;
//            }
//            if(number1 < 0) {
//                number1 = 9;
//            }
        }
        if(number < 0 && number1 < 0 && number3 < 0) {
            BombermanGame.isDone = true;
        }
        if(number1 >=0 && number < 0) {
            number = 9;
        }
        if(number3 >=0 && number1 < 0) {
            number1 = 9;
        }
    }
}
