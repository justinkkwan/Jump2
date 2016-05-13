package GameStates;

import Enemies.Goomba;
import Player.Player;
import Player.Mario;
import Player.Luigi;
import Sounds.SoundPlayer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedList;

/**
 * Created by Justin Kwan on 4/12/2016.
 */
public class Game extends JPanel implements ActionListener{

    //private Action leftPress = new leftAction();

    private SoundPlayer sp = new SoundPlayer();

    private LinkedList<Goomba> goombas = new LinkedList<Goomba>();
    private LinkedList<Goomba> removeList = new LinkedList<Goomba>();
    private Player mar = new Mario();
    private static int dx = 4;
    private int gravity = 0;

    private Timer timer = new Timer(5, this);
    private boolean jumping = false;
    public boolean[] keys = new boolean[10]; //{W A S D (if A and D both pressed) ...}


    public void paintComponent(Graphics g){
        super.paintComponent(g);

        ImageIcon playerIMG = mar.activePicture;
        playerIMG.paintIcon(this, g, mar.x, mar.y);

        for(Goomba gom: goombas){
            ImageIcon enemyIMG = gom.getPicture();
            enemyIMG.paintIcon(this, g, gom.x, gom.y);
        }
    }

    public void main(){
//        mar = new Mario();
        goombas.add(new Goomba());
        goombas.add(new Goomba(200,600));
        timer.start();

//        setFocusable(true);
//        setFocusTraversalKeysEnabled(false);

        //unsure
        this.getInputMap(WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("W"), "upPressed");
        this.getActionMap().put("upPressed", new UpAction("up"));
        this.getInputMap(WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("A"), "leftPressed");
        this.getActionMap().put("leftPressed", new leftAction(true));
        this.getInputMap(WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("D"), "rightPressed");
        this.getActionMap().put("rightPressed", new rightAction(true));
        this.getInputMap(WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("released W"), "releasedUp");
        this.getInputMap(WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("released A"), "releasedL");
        this.getInputMap(WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("released D"), "releasedR");
        this.getActionMap().put("releasedUp", new UpAction("down"));
        this.getActionMap().put("releasedL", new leftAction(false));
        this.getActionMap().put("releasedR", new rightAction(false));
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(keys[1]){
            mar.x -= dx;
            mar.activePicture = mar.pictureLeft;

        }
        if(keys[3]){
            mar.x += dx;
            mar.activePicture = mar.pictureRight;
        }
        if(keys[1]&&keys[3]){
            if(keys[4]){
                mar.x += dx;
                mar.activePicture = mar.pictureRight;
            }
            else{
                mar.x -= dx;
                mar.activePicture = mar.pictureLeft;
            }
        }


        if(keys[0]&&!jumping){
            mar.dy=-12;
            jumping = true;
            sp.playSound(0);
        }
        if(jumping){
            mar.y +=mar.dy;
            mar.count++;
            if(!keys[0]&&mar.dy<0&&!mar.hitEnemy)
                mar.dy=0;
            if(gravity==0) {
                mar.dy++;
            }
        }
        if(mar.y>=600){
            jumping=false;
            mar.y = 600;
            mar.dy=0;
        }

        for(Goomba gom: goombas){
            if(gom.state>0) {
                gom.x += gom.dx;
                if(gom.state==2){
                    gom.y+=gom.dy;
                    if(gravity==0)
                        gom.dy++;
                    if(gom.y>=600){
                        gom.y=600;
                        gom.dy=-8;
                    }
                }
                if (gom.x < 0 || gom.x > 1050) gom.dx = -gom.dx;
                if ((gom.x - mar.x < 96 && mar.x - gom.x < 92) &&
                        mar.y - gom.y>-100 &&
                        mar.dy > 0) {
                    gom.onHit();
                    mar.hitEnemy=true;
                    mar.dy=-5;
                    if(keys[0]){
                        mar.dy=-12;
                        sp.playSound(2);
                    }
                    else
                        sp.playSound(1);
                    jumping=true;
                }

                if(gom.state==1&&gom.y<600){
                    gom.y+=4;
                    if(gom.y>600) gom.y=600;
                }
            }
            else gom.count++;
            if (gom.count>40){
                removeList.add(gom);
                mar.hitEnemy=false;
            }
        }
        for(Goomba gom: removeList){
            goombas.remove(gom);
        }



        if(goombas.size()<2){
            goombas.add(new Goomba(1049,600));
        }

        gravity=(gravity+1)%4;
        repaint();
    }

    public void setCharacter(int character) {
        switch (character){
            case 1:
                mar = new Mario();
                break;
            case 2:
                mar = new Luigi();
                break;
            default:
                mar = new Mario();
                break;
        }
    }

    private class UpAction extends AbstractAction {

        private int dy;
        private boolean up;
        public UpAction(String s){
            if(s.equals("up")){
                if(!jumping)
                    up = true;

            }
            if(s.equals("down"))
                up = false;
        }
        @Override
        public void actionPerformed(ActionEvent e) {
            keys[0] = up;
        }
    }

    private class leftAction extends AbstractAction {
        private boolean b;
        public leftAction(boolean b) {
            this.b = b;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            keys[1] = b;
            keys[4] = false;
        }
    }

    private class rightAction extends AbstractAction {
        private boolean b;
        public rightAction(boolean b) {
            this.b=b;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            keys[3] = b;
            keys[4] = true;
        }
    }
}
