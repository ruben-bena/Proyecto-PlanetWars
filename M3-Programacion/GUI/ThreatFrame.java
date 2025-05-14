package GUI;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;

import classes.ArmoredShip;
import classes.BattleShip;
import classes.HeavyHunter;
import classes.LightHunter;
import classes.MilitaryUnit;
import classes.Planet;

public class ThreatFrame extends JFrame {
    private ThreatPanel threatPanel;
    private ArrayList<MilitaryUnit>[] enemyArmy;

    public ThreatFrame(Planet planet) {
        setSize(new Dimension(470,230));
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setTitle("Current Threat");
        setResizable(false);
        enemyArmy = planet.getCurrentThreat().getEnemyArmy();

        threatPanel = new ThreatPanel();
        threatPanel.setBackground(Color.BLACK);

        add(threatPanel);



        setVisible(true);
    }

    class ThreatPanel extends JPanel {
        private TimerTask task;
        private Timer timer;
        ThreatPanel() {
            timer = new Timer();
            task = new TimerTask() {

                @Override
                public void run() {
                    repaint();
                }
                
            };

            timer.schedule(task, 0, 500);
        }

        @Override
        protected void paintComponent(Graphics g) {
            // TODO Auto-generated method stub
            super.paintComponent(g);
            Graphics2D g2d = (Graphics2D) g;
            g2d.setFont(new Font("Arial", Font.BOLD, 36));
            g2d.setColor(Color.WHITE);

            int offset = 0;
            for(int i = 0; i < enemyArmy.length; i++) {
                if(enemyArmy[i].size() > 0) {
                    g2d.drawImage(enemyArmy[i].get(0).getBufferedImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH), 30 + 100 * offset, 20, this);
                    g2d.drawString("x" + enemyArmy[i].size(), 60 + 100 * offset, 150);
                    offset++;
                }
            }

            
        }
    }
    
}
