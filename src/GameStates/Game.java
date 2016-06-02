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
import java.beans.PropertyChangeListener;
import java.util.LinkedList;

/**
 * Created by Justin Kwan on 4/12/2016.
 */
public class Game extends JPanel implements ActionListener{

    //private Action leftPress = new leftAction();

    private JLabel scoreLabel = new JLabel("0");
    private int score =0;

    private SoundPlayer sp = new SoundPlayer();

    private LinkedList<Goomba> goombas = new LinkedList<Goomba>();
    private LinkedList<Goomba> removeList = new LinkedList<Goomba>();
    private Player player = new Mario();
    private static int dx = 4;
    private int gravity = 0;

    private Timer timer = new Timer(6, this);
    private boolean jumping = false;
    public boolean[] keys = new boolean[10]; //{W A S D (if A and D both pressed) R...}

    public ImageIcon SkyImage = new ImageIcon(getClass().getResource("res/sky.png"));
    public ImageIcon GroundImage = new ImageIcon(getClass().getResource("res/ground.png"));


    public void paintComponent(Graphics g){
        super.paintComponent(g);

        SkyImage.paintIcon(this, g, 0, 0);
        GroundImage.paintIcon(this, g, 0, 700);

        ImageIcon playerIMG = player.activePicture;
        playerIMG.paintIcon(this, g, player.getx(), player.gety());

        for(Goomba gom: goombas){
            ImageIcon enemyIMG = gom.getPicture();
            enemyIMG.paintIcon(this, g, gom.x, gom.y);
        }

        int numZeros = 10 - Integer.toString(score).length();
        String front = "";
        while(numZeros>0){
            front+="0";
            numZeros--;
        }

        scoreLabel.setText(front + Integer.toString(score));

    }

    public void main(){

        scoreLabel.setFont(new Font(scoreLabel.getFont().getName(), Font.BOLD, 40));
        scoreLabel.setPreferredSize(new Dimension(1150,50));
        scoreLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        this.add(scoreLabel);

        goombas.add(new Goomba());
        goombas.add(new Goomba(600,600));
        timer.start();

//        setFocusable(true);
//        setFocusTraversalKeysEnabled(false);

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

        this.getInputMap(WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("R"), "restart");
        this.getActionMap().put("restart", new restart());
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        tickPlayerX();
        tickPlayerY();

        tickEnemies();

        gravity=(gravity+1)%4;
        repaint();
    }

    private void tickPlayerX(){
        if(keys[1]){
            player.setdx(-1*dx);
            player.activePicture = player.pictureLeft;

        }
        if(keys[3]){
            player.setdx(dx);
            player.activePicture = player.pictureRight;
        }
        if(keys[1]&&keys[3]){
            if(keys[4]){
                player.setdx(dx);
                player.activePicture = player.pictureRight;
            }
            else{
                player.setdx(-1*dx);
                player.activePicture = player.pictureLeft;
            }
        }
        if(!keys[1]&&!keys[3]) player.setdx(0);
        player.incrementx();
    }

    private void tickPlayerY(){
        if(keys[0]&&!jumping){
            player.setdy(-12);
            jumping = true;
            sp.playSound(0);
        }
        if(jumping){
            player.incrementy();
            player.count++;
            if(!keys[0]&&player.getdy()<0&&!player.hitEnemy)
                player.setdy(0);
            if(gravity==0) {
                player.gravityEffect();
            }
        }
        if(player.gety()>=600){
            jumping=false;
            player.sety(600);
            player.setdy(0);
        }
    }

    public void tickEnemies(){
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
                if (gom.x < 0 || gom.x > 1080) gom.dx = -gom.dx;
                handlePlayerEnemyInteraction(gom);

                if(gom.state==1&&gom.y<600){
                    gom.y+=4;
                    if(gom.y>600) gom.y=600;
                }
            }
            else gom.count++;
            if (gom.count>40){ // slight pause before removing goombas and also a window of time for player to be in enemy hitbox
                removeList.add(gom);
                player.hitEnemy=false;
            }
        }
        for(Goomba gom: removeList){
            goombas.remove(gom);
        }

        if(goombas.size()<2){
            goombas.add(new Goomba(1079,600));
        }
    }

    public void handlePlayerEnemyInteraction(Goomba gom) {
        if ((gom.x - player.getx() < player.activePicture.getIconWidth()-5 && //goomba and mario can be within 4 pixels of each other on left of goomba
                player.getx() - gom.x < gom.getPicture().getIconWidth()-8) && //goomba and mario can be within 7 pixels of each other on right of goomba
                gom.y - player.gety() < player.activePicture.getIconHeight()) {
            if(player.getdy() > 0){
                gom.onHit();
                player.hitEnemy=true;
                player.setdy(-5);
                if(keys[0]){
                    player.setdy(-12);
                    sp.playSound(2);
                }
                else
                    sp.playSound(1);
                player.incrementy();
                jumping=true;
                score+=200;
            }
            else if (gom.y - player.gety() < player.activePicture.getIconHeight() - gom.getPicture().getIconHeight()/2){
                // this allows players to hit a goomba diagonally and not be punished for it (within reason)
                gameOver();
            }
        }
    }

    public void setCharacter(int character) {
        switch (character){
            case 1:
                player = new Mario();
                break;
            case 2:
                player = new Luigi();
                break;
            default:
                player = new Mario();
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

    public void gameOver(){
        sp.playSound(3);
        timer.stop();


    }

    private class restart extends AbstractAction {
        @Override
        public void actionPerformed(ActionEvent e) {
            remove(scoreLabel);
            goombas.clear();
            score=0;
            player.setx(0);
            player.sety(600);
            player.setdy(0);
            jumping = false;
            main();
        }
    }
}
