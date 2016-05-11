package Player;

import javax.swing.*;

/**
 * Created by Justin Kwan on 4/12/2016.
 */
public class Mario extends Player{

    public Mario(){
        super();
        activePicture = new ImageIcon(getClass().getResource("res/Mario.png"));
        pictureRight = new ImageIcon(getClass().getResource("res/Mario.png"));
        pictureLeft = new ImageIcon(getClass().getResource("res/MarioLeft.png"));
    }
}
