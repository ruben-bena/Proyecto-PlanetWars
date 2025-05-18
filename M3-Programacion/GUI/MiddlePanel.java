package GUI;

import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Stroke;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

import GUI.*;
import classes.*;

public class MiddlePanel extends JPanel{
        private BufferedImage activeImage;
        private BufferedImage earthImage;
        private BufferedImage battleScene, sword_turnImage, explosionImage;
        private Planet planet;
        private Graphics2D g2d;
        private MilitaryUnit allyUnit, enemyUnit;
        private Battle battle;
        private Color threatDisplayColor;
        private int timerCountdown;
        private Font customFontBig, customFont, customFontSmall, customFontBiggest;
        private String threatDisplayStr, invadeDisplayStr, activeDisplayStr, messageStr;
        MiddlePanel(Planet planet) {
            setLayout(new BorderLayout());
            add(new PaddingPanel(), BorderLayout.NORTH);
            setFocusable(true);
            threatDisplayStr = "THREAT DETECTED";
            invadeDisplayStr = "SEARCHING";
            activeDisplayStr = threatDisplayStr;
            messageStr = "";
            
            try {
                customFontBiggest = Font.createFont(Font.TRUETYPE_FONT, new File(Globals.customFont)).deriveFont(68f);
                customFontBig = Font.createFont(Font.TRUETYPE_FONT, new File(Globals.customFont)).deriveFont(48f);
                customFont = Font.createFont(Font.TRUETYPE_FONT, new File(Globals.customFont)).deriveFont(32f);
                customFontSmall = Font.createFont(Font.TRUETYPE_FONT, new File(Globals.customFont)).deriveFont(18f);
            } catch (FontFormatException | IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

            addKeyListener(new KeyAdapter() {
                @Override
                public void keyPressed(KeyEvent e) {
                    // TODO Auto-generated method stub
                    super.keyPressed(e);
                    System.out.println(e.getKeyCode());
                    if (e.getKeyCode() == KeyEvent.VK_SPACE) {
                        if(battle != null && battle.isHasCombatStarted()){
                            battle.setSkipBattle(true);
                            System.out.println("Skipping...");
                        }
                    }
                }
            });
            timerCountdown = Time.secondsCountdownBattle;
            this.planet = planet;
            threatDisplayColor = Color.WHITE;
            try {
                earthImage = ImageIO.read(new File("./M3-Programacion/GUI/images/earth3.png"));
                battleScene = ImageIO.read(new File("./M3-Programacion/GUI/images/space2.png"));
                sword_turnImage = ImageIO.read(new File("./M3-Programacion/GUI/images/sword_turn.png"));
                explosionImage = ImageIO.read(new File("./M3-Programacion/GUI/images/explosion.png"));
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

            activeImage = earthImage;
            
            // Things to fix: 1. Timer for another battle is almost instant after the battle is over // Fixed, maybe could use some improvements though
            // Things to add: 1. Cheats pop-up
        }

        protected void paintComponent(Graphics g) {
            super.paintComponent(g);

            g2d = (Graphics2D) g;
            g2d.drawImage(activeImage.getScaledInstance(getWidth(), getHeight(), Image.SCALE_SMOOTH), 0, 0, this);
            // System.out.println("Width = " + getWidth() + " Height = " + getHeight());

            
            if(activeImage.equals(earthImage)) {
                g2d.setColor(Color.BLACK);
                // g2d.fillRect(getWidth()/2 - 250, 0, 500, 150);
                g2d.setFont(customFontBig);
                g2d.setColor(threatDisplayColor);
                // g2d.drawString(planet.getPlanetName(), getWidth() / 2 - (planet.getPlanetName().length() * 10), 50);
                g2d.drawString(planet.getPlanetName(), 50, 75);
            }

            

            if(planet.getCurrentThreat() != null) {
                // if combat hasn't started but there is a fight incoming
                if(!planet.getCurrentThreat().isHasCombatStarted() || planet.getIsInvading() && battle.getBattleType() == 0) {
                    g2d.setColor(Color.BLACK);
                    g2d.setFont(customFontBig);
                    g2d.setColor(threatDisplayColor);
                    if(activeDisplayStr.equals(threatDisplayStr)) {
                        g2d.drawString(activeDisplayStr, getWidth() / 2 - (activeDisplayStr.length() * 13), 75);
                        g2d.drawString(String.valueOf(timerCountdown), getWidth() / 2, 160);
                    } else {
                        g2d.setFont(customFont);
                        g2d.drawString(activeDisplayStr, 50, 150);
                        g2d.drawString(String.valueOf(timerCountdown), 50, 200);
                    }
                }

                

                // if combat has started
                if(planet.getCurrentThreat().isHasCombatStarted()) {
                    // g2d.setFont(new Font("Arial", 1, 32));
                    g2d.setFont(customFont);
                    g2d.setColor(new Color(255,255,255, 220));
                    g2d.drawString("\"Space\" to skip", getWidth()/2-140, getHeight() - 30);
                    // g2d.setFont(new Font("Arial", 3, 72));
                    g2d.setFont(customFontBiggest);
                    
                    if (battle.getAttackingArmy() == 0) {
                        int[] xPoints = new int[3];
                        xPoints[0] = 0;
                        xPoints[1] = getWidth();
                        xPoints[2] = 0;
                        int[] yPoints = new int[3];
                        yPoints[0] = 0;
                        yPoints[1] = getHeight();
                        yPoints[2] = getHeight();
                        g2d.setColor(new Color(255,255,255, 20));
                        g2d.fillPolygon(xPoints, yPoints, 3);
                    } else {
                        int[] xPoints = new int[3];
                        xPoints[0] = getWidth();
                        xPoints[1] = 0;
                        xPoints[2] = getWidth();
                        int[] yPoints = new int[3];
                        yPoints[0] = 0;
                        yPoints[1] = 0;
                        yPoints[2] = getHeight();
                        g2d.setColor(new Color(255,255,255, 20));
                        g2d.fillPolygon(xPoints, yPoints, 3);
                    }

                    g2d.setColor(Color.WHITE);
                    // Painting the units
                    g2d.drawImage(allyUnit.getBufferedImage().getScaledInstance(300, 300, Image.SCALE_SMOOTH), 30, getHeight()-330, this);
                    g2d.drawImage(enemyUnit.getBufferedImage().getScaledInstance(300, 300, Image.SCALE_SMOOTH), getWidth() - 330, 30, this);
                    

                    if(allyUnit.getActualArmor() <= 0) {
                        g2d.drawImage(explosionImage.getScaledInstance(300, 300, Image.SCALE_SMOOTH), 30, getHeight()-330, this);
                    } else if (enemyUnit.getActualArmor() <= 0) {
                        g2d.drawImage(explosionImage.getScaledInstance(300, 300, Image.SCALE_SMOOTH), getWidth() - 330, 30, this);
                    }

                    
                    //Painting the "health bar"
                    g2d.setColor(Color.BLACK);
                    g2d.fillRect(20, getHeight()-410, 300, 60);
                    if(battle.getPlanetArmyPercRemaining() < 50) {
                        g2d.setColor(Globals.healthBarInjuredColor);
                    } else {
                        g2d.setColor(Globals.healthBarHealthyColor);
                    }
                    
                    g2d.fillRect(30, getHeight()-400, (int) (280 * (battle.getPlanetArmyPercRemaining()/100f)), 40);
                    
                    g2d.setColor(Color.BLACK);
                    g2d.fillRect(getWidth() -330, getHeight()-350, 300, 60);

                    if(battle.getEnemyArmyPercRemaining() < 50) {
                        g2d.setColor(Globals.healthBarInjuredColor);
                    } else {
                        g2d.setColor(Globals.healthBarHealthyColor);
                    }
  
                    g2d.fillRect(getWidth() -320, getHeight()-340, (int) (280 * (battle.getEnemyArmyPercRemaining()/100f)), 40);

                    // Painting the "vs" thing
                    g2d.setColor(Color.BLACK);
                    g2d.setStroke(new BasicStroke(10));
                    g2d.drawLine(0, 0, getWidth(), getHeight());
                    
                    g2d.setColor(Color.WHITE);
                    g2d.setFont(new Font("Arial", 3, 96));
                    g2d.drawString("VS", getWidth()/2-48, getHeight()/2+48);

                    // Painting the names of the planets
                    g2d.setFont(customFontBig);
                    if(battle.getBattleType() == 0) {
                        g2d.drawString(battle.getUserPlanet().getPlanetName(), 50, getHeight()-410 - 20);
                        g2d.drawString(battle.getEnemyPlanet().getPlanetName(), getWidth() -320, 450);
                    } else {
                        g2d.drawString(battle.getEnemyPlanet().getPlanetName(), 50, getHeight()-410 - 20);
                        g2d.drawString(battle.getUserPlanet().getPlanetName(), getWidth() -320, 450);
                    }
                }
            }
            // drawing the messageStr
            if(!messageStr.equals("")) {
                g2d.setColor(new Color(0,0,0,220));
                g2d.fillRect(0, getHeight()/2-30, getWidth(), 60);
            }
            g2d.setFont(customFontBig);
            g2d.setColor(Color.RED);
            g2d.drawString(messageStr, getWidth() - messageStr.length() * 54, getHeight()/2 + 20);
            g2d.setFont(customFontBig);

            
            
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

        class ThreatDisplayTimer extends TimerTask {
            private int time;
            private Timer timer;
            public ThreatDisplayTimer() {
                activeDisplayStr = threatDisplayStr;
                time = 0;
                timer = new Timer();

                timer.schedule(this, 0, Time.secInMs);

            }

            @Override
            public void run() {
                // TODO Auto-generated method stub
                time = time + Time.secInMs;
                timerCountdown--;
               
                if(threatDisplayColor == Color.WHITE) {
                        threatDisplayColor = Color.RED;
                } else {
                        threatDisplayColor = Color.WHITE;
                    }
                
                if(time > Time.countdownBattleTime) {
                    cancel();
                    threatDisplayColor = Color.WHITE;
                    timerCountdown = Time.secondsCountdownBattle;
                }

                repaint();

            }
        }

        class ShowMessage extends TimerTask {
            private int time;
            private Timer timer;
            private String message;
            private int seconds;
            ShowMessage(String message, int seconds) {
                this.message = message;
                this.seconds = seconds;
                timer = new Timer();
                time = 0;
                AudioPlayer.doError();

                timer.schedule(this, 0, Time.secInMs);

            }
            
            @Override
            public void run() {
                time++;
                
                messageStr = message;
                repaint();
                if(time > seconds) {
                    messageStr = "";
                    repaint();
                    cancel();
                }
            }

        }

        public void doShowMessage(String message, int seconds) {
            if(messageStr.equals("")){
                new ShowMessage(message, seconds);
            }
        }

        public void doThreatDisplay() {
            new ThreatDisplayTimer();
        }

        class InvadeDisplayTimer extends TimerTask {
            private int time;
            private Timer timer;
            private int nDots = 0;
            public InvadeDisplayTimer() {
                activeDisplayStr = invadeDisplayStr;
                time = 0;
                timer = new Timer();

                timer.schedule(this, 0, Time.secInMs);

            }

            @Override
            public void run() {
                // TODO Auto-generated method stub
                time = time + Time.secInMs;
                timerCountdown--;
                
                if (nDots <= 3) {
                    invadeDisplayStr += ".";
                    activeDisplayStr = invadeDisplayStr;
                    nDots++;
                }

                if(nDots >= 4) {
                    invadeDisplayStr = "SEARCHING";
                    activeDisplayStr = invadeDisplayStr;
                    nDots = 0;
                }
               
                
                if(time > Time.countdownBattleTime) {
                    cancel();
                    timerCountdown = Time.secondsCountdownBattle;
                }

                repaint();

            }
        }

        public void doInvadeDisplay() {
            new InvadeDisplayTimer();
        }
    }