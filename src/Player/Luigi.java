package Player;

import javax.swing.*;

/**
 * Created by Justin Kwan on 5/3/2016.
 */
public class Luigi extends Player {

    public Luigi(){
        super();
        activePicture = new ImageIcon(getClass().getResource("res/Luigi.png"));
        pictureRight = new ImageIcon(getClass().getResource("res/Luigi.png"));
        pictureLeft = new ImageIcon(getClass().getResource("res/LuigiLeft.png"));
    }
}
