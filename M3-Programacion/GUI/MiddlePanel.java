package GUI;

import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Stroke;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

import GUI.*;
import classes.*;

public class MiddlePanel extends JPanel {
        private BufferedImage activeImage;
        private BufferedImage earthImage;
        private BufferedImage battleScene, sword_turnImage, explosionImage;
        private Planet planet;
        private Graphics2D g2d;
        private MilitaryUnit allyUnit, enemyUnit;
        private Battle battle;
        MiddlePanel(Planet planet) {
            setLayout(new BorderLayout());
            add(new PaddingPanel(), BorderLayout.NORTH);
            this.planet = planet;
            try {
                earthImage = ImageIO.read(new File("./M3-Programacion/GUI/images/earth.jpg"));
                battleScene = ImageIO.read(new File("./M3-Programacion/GUI/images/space.jpg"));
                sword_turnImage = ImageIO.read(new File("./M3-Programacion/GUI/images/sword_turn.png"));
                explosionImage = ImageIO.read(new File("./M3-Programacion/GUI/images/explosion.png"));
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

            activeImage = earthImage;
            
            // Things to fix: 1. Right now the enemy uses defenses too, idk if it's just messing up in the visual aspect or it's also doing it in the logic
            // Things to add: 1. Results pop-up   2. Cheats pop-up
        }

        protected void paintComponent(Graphics g) {
            super.paintComponent(g);

            g2d = (Graphics2D) g;
            g2d.drawImage(activeImage.getScaledInstance(getWidth(), getHeight(), Image.SCALE_SMOOTH), 0, 0, this);
            // g2d.fillRect(200, 200, 300, 300);
            if(planet.getCurrentThreat() != null) {
                System.out.println("bbbbbbbbbbbbbbbbb");
                if(planet.getCurrentThreat().isHasCombatStarted()) { // It doesn't get to here
                    System.out.println("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa");
                    
                    // Painting the units

                    g2d.drawImage(allyUnit.getBufferedImage().getScaledInstance(300, 300, Image.SCALE_SMOOTH), 30, getHeight()-330, this);
                    g2d.drawImage(enemyUnit.getBufferedImage().getScaledInstance(300, 300, Image.SCALE_SMOOTH), getWidth() - 330, 30, this);
                    if (battle.getAttackingArmy() == 0) {
                        g2d.drawImage(sword_turnImage.getScaledInstance(100, 100, Image.SCALE_SMOOTH), 300, getHeight() - 150, this);
                    } else {
                        g2d.drawImage(sword_turnImage.getScaledInstance(100, 100, Image.SCALE_SMOOTH), getWidth() - 330, 50, this);
                    }

                    if(allyUnit.getActualArmor() <= 0) {
                        g2d.drawImage(explosionImage.getScaledInstance(300, 300, Image.SCALE_SMOOTH), 30, getHeight()-330, this);
                    } else if (enemyUnit.getActualArmor() <= 0) {
                        g2d.drawImage(explosionImage.getScaledInstance(300, 300, Image.SCALE_SMOOTH), getWidth() - 330, 30, this);
                    }
                    g2d.setStroke(new BasicStroke(10));
                    g2d.drawLine(0, 0, getWidth(), getHeight());
                    g2d.setColor(Color.WHITE);
                    g2d.setFont(new Font("Arial", 3, 72));
                    g2d.drawString("VS", getWidth()/2-36, getHeight()/2+36);

                }
            }
            
        }

        public void paintCurrentBattleState(Battle battle, MilitaryUnit allyUnit, MilitaryUnit enemyUnit) {
            this.battle = battle;
            this.allyUnit = allyUnit;
            this.enemyUnit = enemyUnit;
            
            repaint();
        }

        public void changeScreenToBattleScene() {
            activeImage = battleScene;
            repaint();
        }

        public void changeScreenToDefaultScene() {
            activeImage = earthImage;
            repaint();
        }

        public void paintTest(Graphics2D g2d) {
            g2d.fillOval(50, 50, 200, 200);
            repaint();
        }
    }