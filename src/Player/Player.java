package Player;

import javax.swing.*;

/**
 * Created by Justin Kwan on 5/1/2016.
 */
public abstract class Player {
    public ImageIcon activePicture;
    public ImageIcon pictureRight;
    public ImageIcon pictureLeft;

    private int x;
    private int y;
    private int dx;
    private int dy;

    public boolean jump;
    public int count;
    public boolean hitEnemy;


    public Player(){
        x=0;
        y=600;
        dx = 0;
        dy = 0;
        jump = false;
        hitEnemy = false;
    }

    public int getx(){
        return x;
    }

    public void setx(int x){
        this.x = x;
    }

    public int gety(){
        return y;
    }

    public void sety(int y){
        this.y = y;
    }

    public int getdx(){
        return dx;
    }

    public void setdx(int dx){
        this.dx = dx;
    }

    public int getdy(){
        return dy;
    }

    public void setdy(int dy){
        this.dy = dy;
    }

    public void incrementx(){
        if(x+dx>0&&x+dx<1080) x+=dx;
    }

    public void incrementy(){
        y+=dy;
    }

    public void gravityEffect(){
        if(dy<10) dy++;
    }

}
