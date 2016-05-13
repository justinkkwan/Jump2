package Player;

import javax.swing.*;

/**
 * Created by Justin Kwan on 5/1/2016.
 */
public abstract class Player {
    public ImageIcon activePicture;
    public ImageIcon pictureRight;
    public ImageIcon pictureLeft;
    public int x;
    public int y;
    public int dx;
    public int dy;
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
}
