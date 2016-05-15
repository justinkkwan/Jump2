package Enemies;

import javax.swing.*;
import java.util.Random;

/**
 * Created by Justin Kwan on 4/12/2016.
 */
public class Goomba {
    public ImageIcon picture0 = new ImageIcon(getClass().getResource("res/StompedGoomba.png"));
    public ImageIcon picture1 = new ImageIcon(getClass().getResource("res/Goomba.png"));
    public ImageIcon picture2 = new ImageIcon(getClass().getResource("res/FlyingGoomba.png"));
    public int x;
    public int y;
    public int dx;
    public int dy;
    public int state;
    public int count;
    private static Random rand = new Random();

    public Goomba(){
        state = 1;
        x = 400;
        y = 600;

        dx=-2;
    }
    public Goomba(int x, int y){
        state = rand.nextInt(2)+1;

        this.x = x;
        this.y = y;

        dx=-2;
    }

    public ImageIcon getPicture(){
        switch (state) {
            case 0:
                return picture0;
            case 1:
                return picture1;
            case 2:
                return picture2;
        }

        return picture1;
    }

    public void onHit(){
        state--;
    }
}
