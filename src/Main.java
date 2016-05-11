import GameStates.Game;
import GameStates.Menu;
import Player.Luigi;
import Player.Mario;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeListener;

/**
 * Created by Justin Kwan on 4/12/2016.
 */
public class Main {
    static CardLayout card = new CardLayout();
    static JPanel container = new JPanel();
    static Game g = new Game();
    static Menu m = new Menu();


    public static void main(String[] args) {

        container.setLayout(card);

        container.add(g, "Game");
        container.add(m, "Menu");

        JFrame jframe = new JFrame();
        jframe.setTitle("Jump 2");
        jframe.setSize(1200,800);
        jframe.setVisible(true);
        jframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        jframe.add(container);

        JButton mario = new JButton(new ImageIcon (Mario.class.getResource("res/Mario.png")));
        JButton luigi = new JButton(new ImageIcon (Luigi.class.getResource("res/Luigi.png")));

        m.setLayout(null);

        mario.setBounds(400,400,100,200);
        luigi.setBounds(700,400,100,200);

        m.add(mario);
        m.add(luigi);


        mario.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                g.setCharacter(1);
                card.show(container, "Game");
                g.main();
            }
        });

        luigi.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                g.setCharacter(2);
                card.show(container, "Game");
                g.main();
            }
        });

        card.show(container, "Menu");
        //card.show(container, "Game");

//        g.setCharacter(p);
//        jframe.add(g);
//        g.main();


    }
}
