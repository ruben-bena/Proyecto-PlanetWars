package GUI;
import classes.*;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.LayoutManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

import javax.imageio.ImageIO;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.border.Border;

public class BottomPanel extends JPanel {
        private JPanel mainPanel, shopPanel, lightHunterPanel, heavyHunterPanel, battleShipPanel, armoredShipPanel, missileLauncherPanel, 
        ionCannonPanel, plasmaCannonPanel, lightHunterNamePanel, heavyHunterNamePanel, battleShipNamePanel, armoredShipNamePanel,
        lightHunterCostPanel, heavyHunterCostPanel, battleShipCostPanel, armoredShipCostPanel, missileLauncherNamePanel,
        ionCannonNamePanel, plasmaCannonNamePanel, missileLauncherCostPanel, ionCannonCostPanel, plasmaCannonCostPanel;
        private JButton buyLightHunterButton, buyHeavyHunterButton, buyBattleShipButton, buyArmoredShipButton, buyMissileLauncherButton,
        buyIonCannonButton, buyPlasmaCannonButton;
        private JLabel lightHunterNameLabel, heavyHunterNameLabel, battleShipNameLabel, armoredShipNameLabel, missileLauncherNameLabel, ionCannonNameLabel,
        plasmaCannonNameLabel;
        private JLabel lightHunterPriceMetalLabel, lightHunterPriceDeuteriumLabel, heavyHunterPriceMetalLabel, heavyHunterPriceDeuteriumLabel,
        battleShipPriceMetalLabel, battleShipPriceDeuteriumLabel, armoredShipPriceMetalLabel, armoredShipPriceDeuteriumLabel,
        missileLauncherPriceMetalLabel, missileLauncherPriceDeuteriumLabel, ionCannonPriceMetalLabel, ionCannonPriceDeuteriumLabel,
        plasmaCannonPriceMetalLabel, plasmaCannonPriceDeuteriumLabel;
        private ImageIcon deuteriumIcon, metalIcon;
        private Font customFont, customFontSmaller;

        BottomPanel(Planet planet) {
            setSize(new Dimension(200,200));
            setPreferredSize(new Dimension(200,200));
            setLayout(new BorderLayout());
            setBackground(Color.BLACK);
            add(new PaddingPanel(), BorderLayout.NORTH);
            add(new PaddingPanel(), BorderLayout.WEST);
            add(new PaddingPanel(), BorderLayout.EAST);
            add(new PaddingPanel(), BorderLayout.SOUTH);

            try {
                customFont = Font.createFont(Font.TRUETYPE_FONT, new File(Globals.customFont)).deriveFont(24f);
                customFontSmaller = Font.createFont(Font.TRUETYPE_FONT, new File(Globals.customFont)).deriveFont(16f);
            } catch (FontFormatException | IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }


            deuteriumIcon = new ImageIcon("./M3-Programacion/GUI/images/redstone.png");
            Image redstoneIconScaled = deuteriumIcon.getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH);
            Image redstoneImageScaledTiny = redstoneIconScaled.getScaledInstance(20, 20, Image.SCALE_SMOOTH);

            metalIcon = new ImageIcon("./M3-Programacion/GUI/images/iron_ingot.png");
            Image metalIconScaled = metalIcon.getImage().getScaledInstance(40, 40, Image.SCALE_SMOOTH);
            Image metalImageScaledTiny = metalIconScaled.getScaledInstance(20, 20, Image.SCALE_SMOOTH);
            
            mainPanel = new JPanel();
            mainPanel.setLayout(new BorderLayout());
            shopPanel = new JPanel();
            shopPanel.setLayout(new GridLayout(1,7));
            shopPanel.setBackground(Globals.bottomBackgroundColor);

            //LightHunters panel
            lightHunterPanel = new JPanel();
            lightHunterPanel.setLayout(new BorderLayout());
            lightHunterPanel.setBackground(Color.GREEN);

            buyLightHunterButton = new JButton("buyLightHunter");
            buyLightHunterButton.setBackground(Globals.bottomImageBackgroundColor);
            buyLightHunterButton.setIcon(new ImageIcon(LightHunter.getImgIcon().getImage().getScaledInstance(70, 100, Image.SCALE_SMOOTH)));
            buyLightHunterButton.setFont(new Font("Arial", 1, 0));
            buyLightHunterButton.setSize(30, 30);
            buyLightHunterButton.setPreferredSize(new Dimension(getWidth(),getHeight()));
            buyLightHunterButton.addActionListener(new ActionListener() {

                @Override
                public void actionPerformed(ActionEvent e) {
                    try {
                        AudioPlayer.buttonSound();
                        if(planet.getCurrentThreat() != null) {
                            if (planet.getCurrentThreat().isHasCombatStarted()) {
                                return;
                            }
                        }
                        planet.newLightHunter(1);;
                    } catch (ResourceException e1) {
                        // TODO Auto-generated catch block
                        e1.printStackTrace();
                    }
                }
                
            });
            lightHunterNamePanel = new JPanel();
            lightHunterNameLabel = new JLabel("Light Hunter - " + planet.getArmy()[0].size());
            lightHunterNameLabel.setForeground(Globals.bottomFontColor);
            lightHunterNamePanel.setBackground(Globals.bottomBackgroundColor);
            
            // lightHunterNameLabel.setFont(new Font("Arial", 1, 20));
            lightHunterNameLabel.setFont(customFontSmaller);

            lightHunterNamePanel.add(lightHunterNameLabel);

            lightHunterPanel.add(lightHunterNamePanel, BorderLayout.NORTH);
            lightHunterPanel.add(buyLightHunterButton, BorderLayout.CENTER);

            lightHunterCostPanel = new JPanel();
            lightHunterCostPanel.setBackground(Globals.bottomBackgroundColor);
            
            lightHunterCostPanel.add(new JLabel(new ImageIcon(metalImageScaledTiny)));
            lightHunterPriceMetalLabel = new JLabel("" + Variables.METAL_COST_LIGTHHUNTER);
            lightHunterPriceMetalLabel.setForeground(Globals.bottomFontColor);
            // lightHunterPriceMetalLabel.setFont(new Font("Arial", 1, 20));
            lightHunterPriceMetalLabel.setFont(customFontSmaller);
            lightHunterCostPanel.add(lightHunterPriceMetalLabel);
            lightHunterCostPanel.add(new JLabel(new ImageIcon(redstoneImageScaledTiny)));
            lightHunterPriceDeuteriumLabel = new JLabel("" + Variables.DEUTERIUM_COST_LIGTHHUNTER);
            lightHunterPriceDeuteriumLabel.setForeground(Globals.bottomFontColor);
            // lightHunterPriceDeuteriumLabel.setFont(new Font("Arial", 1, 20));
            lightHunterPriceDeuteriumLabel.setFont(customFontSmaller);
            lightHunterCostPanel.add(lightHunterPriceDeuteriumLabel);

            lightHunterPanel.add(lightHunterCostPanel, BorderLayout.SOUTH);

            shopPanel.add(lightHunterPanel);

            //HeavyHuntersPanel
            heavyHunterPanel = new JPanel();
            heavyHunterPanel.setLayout(new BorderLayout());
            heavyHunterPanel.setBackground(Globals.bottomBackgroundColor);

            buyHeavyHunterButton = new JButton("buyHeavyHunter");
            buyHeavyHunterButton.setBackground(Globals.bottomImageBackgroundColor);
            buyHeavyHunterButton.setIcon(new ImageIcon(HeavyHunter.getImgIcon().getImage().getScaledInstance(70, 100, Image.SCALE_SMOOTH)));
            buyHeavyHunterButton.setFont(new Font("Arial", 1, 0));
            buyHeavyHunterButton.setSize(30, 30);
            buyHeavyHunterButton.setPreferredSize(new Dimension(getWidth(),getHeight()));
            buyHeavyHunterButton.addActionListener(new ActionListener() {

                @Override
                public void actionPerformed(ActionEvent e) {
                    try {
                        AudioPlayer.buttonSound();
                        if(planet.getCurrentThreat() != null) {
                            if (planet.getCurrentThreat().isHasCombatStarted()) {
                                return;
                            }
                        }
                        planet.newHeavyHunter(1);;
                    } catch (ResourceException e1) {
                        // TODO Auto-generated catch block
                        e1.printStackTrace();
                    }
                }
                
            });
            heavyHunterNamePanel = new JPanel();
            heavyHunterNameLabel = new JLabel("Heavy Hunter - " + planet.getArmy()[1].size());
            heavyHunterNameLabel.setForeground(Globals.bottomFontColor);
            heavyHunterNamePanel.setBackground(Globals.bottomBackgroundColor);
            
            // heavyHunterNameLabel.setFont(new Font("Arial", 1, 20));
            heavyHunterNameLabel.setFont(customFontSmaller);

            heavyHunterNamePanel.add(heavyHunterNameLabel);

            heavyHunterPanel.add(heavyHunterNamePanel, BorderLayout.NORTH);
            heavyHunterPanel.add(buyHeavyHunterButton, BorderLayout.CENTER);

            heavyHunterCostPanel = new JPanel();
            heavyHunterCostPanel.setBackground(Globals.bottomBackgroundColor);
            
            heavyHunterCostPanel.add(new JLabel(new ImageIcon(metalImageScaledTiny)));
            heavyHunterPriceMetalLabel = new JLabel("" + Variables.METAL_COST_HEAVYHUNTER);
            heavyHunterPriceMetalLabel.setForeground(Globals.bottomFontColor);
            // heavyHunterPriceMetalLabel.setFont(new Font("Arial", 1, 20));
            heavyHunterPriceMetalLabel.setFont(customFontSmaller);
            heavyHunterCostPanel.add(heavyHunterPriceMetalLabel);
            heavyHunterCostPanel.add(new JLabel(new ImageIcon(redstoneImageScaledTiny)));
            heavyHunterPriceDeuteriumLabel = new JLabel("" + Variables.DEUTERIUM_COST_HEAVYHUNTER);
            heavyHunterPriceDeuteriumLabel.setForeground(Globals.bottomFontColor);
            // heavyHunterPriceDeuteriumLabel.setFont(new Font("Arial", 1, 20));
            heavyHunterPriceDeuteriumLabel.setFont(customFontSmaller);
            heavyHunterCostPanel.add(heavyHunterPriceDeuteriumLabel);

            heavyHunterPanel.add(heavyHunterCostPanel, BorderLayout.SOUTH);


            shopPanel.add(heavyHunterPanel);





            //battleShip panel
            battleShipPanel = new JPanel();
            battleShipPanel.setBackground(Globals.bottomBackgroundColor);

            battleShipPanel.setLayout(new BorderLayout());
            // battleShipPanel.setBackground(Color.BLACK);

            buyBattleShipButton = new JButton("buyBattleShip");
            buyBattleShipButton.setBackground(Globals.bottomImageBackgroundColor);
            buyBattleShipButton.setIcon(new ImageIcon(BattleShip.getImgIcon().getImage().getScaledInstance(70, 100, Image.SCALE_SMOOTH)));
            buyBattleShipButton.setFont(new Font("Arial", 1, 0));
            buyBattleShipButton.setSize(30, 30);
            buyBattleShipButton.setPreferredSize(new Dimension(getWidth(),getHeight()));
            buyBattleShipButton.addActionListener(new ActionListener() {

                @Override
                public void actionPerformed(ActionEvent e) {
                    try {
                        AudioPlayer.buttonSound();
                        if(planet.getCurrentThreat() != null) {
                            if (planet.getCurrentThreat().isHasCombatStarted()) {
                                return;
                            }
                        }
                        planet.newBattleShip(1);;
                    } catch (ResourceException e1) {
                        // TODO Auto-generated catch block
                        e1.printStackTrace();
                    }
                }
                
            });
            battleShipNamePanel = new JPanel();
            battleShipNameLabel = new JLabel("Battle Ship - " + planet.getArmy()[2].size());
            battleShipNameLabel.setForeground(Globals.bottomFontColor);
            battleShipNamePanel.setBackground(Globals.bottomBackgroundColor);
            
            // battleShipNameLabel.setFont(new Font("Arial", 1, 20));
            battleShipNameLabel.setFont(customFontSmaller);

            battleShipNamePanel.add(battleShipNameLabel);

            battleShipPanel.add(battleShipNamePanel, BorderLayout.NORTH);
            battleShipPanel.add(buyBattleShipButton, BorderLayout.CENTER);

            battleShipCostPanel = new JPanel();
            battleShipCostPanel.setBackground(Globals.bottomBackgroundColor);
            
            battleShipCostPanel.add(new JLabel(new ImageIcon(metalImageScaledTiny)));
            battleShipPriceMetalLabel = new JLabel("" + Variables.METAL_COST_BATTLESHIP);
            battleShipPriceMetalLabel.setForeground(Globals.bottomFontColor);
            // battleShipPriceMetalLabel.setFont(new Font("Arial", 1, 20));
            battleShipPriceMetalLabel.setFont(customFontSmaller);

            battleShipCostPanel.add(battleShipPriceMetalLabel);
            battleShipCostPanel.add(new JLabel(new ImageIcon(redstoneImageScaledTiny)));
            battleShipPriceDeuteriumLabel = new JLabel("" + Variables.DEUTERIUM_COST_BATTLESHIP);
            battleShipPriceDeuteriumLabel.setForeground(Globals.bottomFontColor);
            // battleShipPriceDeuteriumLabel.setFont(new Font("Arial", 1, 20));
            battleShipPriceDeuteriumLabel.setFont(customFontSmaller);
            battleShipCostPanel.add(battleShipPriceDeuteriumLabel);

            battleShipPanel.add(battleShipCostPanel, BorderLayout.SOUTH);

            shopPanel.add(battleShipPanel);

            //armoredShip panel
            armoredShipPanel = new JPanel();
            // armoredShipPanel.setBackground(Color.CYAN);


            armoredShipPanel.setLayout(new BorderLayout());
            armoredShipPanel.setBackground(Globals.bottomBackgroundColor);

            buyArmoredShipButton = new JButton("buyArmoredShip");
            buyArmoredShipButton.setBackground(Globals.bottomImageBackgroundColor);
            buyArmoredShipButton.setIcon(new ImageIcon(ArmoredShip.getImgIcon().getImage().getScaledInstance(70, 100, Image.SCALE_SMOOTH)));
            buyArmoredShipButton.setFont(new Font("Arial", 1, 0));
            buyArmoredShipButton.setSize(30, 30);
            buyArmoredShipButton.setPreferredSize(new Dimension(getWidth(),getHeight()));
            buyArmoredShipButton.addActionListener(new ActionListener() {

                @Override
                public void actionPerformed(ActionEvent e) {
                    try {
                        AudioPlayer.buttonSound();
                        if(planet.getCurrentThreat() != null) {
                            if (planet.getCurrentThreat().isHasCombatStarted()) {
                                return;
                            }
                        }
                        planet.newArmoredShip(1);
                    } catch (ResourceException e1) {
                        // TODO Auto-generated catch block
                        e1.printStackTrace();
                    }
                }
                
            });
            armoredShipNamePanel = new JPanel();
            armoredShipNameLabel = new JLabel("Armored Ship - " + planet.getArmy()[3].size());
            armoredShipNameLabel.setForeground(Globals.bottomFontColor);
            armoredShipNamePanel.setBackground(Globals.bottomBackgroundColor);
            
            // armoredShipNameLabel.setFont(new Font("Arial", 1, 20));
            armoredShipNameLabel.setFont(customFontSmaller);

            armoredShipNamePanel.add(armoredShipNameLabel);

            armoredShipPanel.add(armoredShipNamePanel, BorderLayout.NORTH);
            armoredShipPanel.add(buyArmoredShipButton, BorderLayout.CENTER);

            armoredShipCostPanel = new JPanel();
            armoredShipCostPanel.setBackground(Globals.bottomBackgroundColor);
            
            armoredShipCostPanel.add(new JLabel(new ImageIcon(metalImageScaledTiny)));
            armoredShipPriceMetalLabel = new JLabel("" + Variables.METAL_COST_ARMOREDSHIP);
            armoredShipPriceMetalLabel.setForeground(Globals.bottomFontColor);
            // armoredShipPriceMetalLabel.setFont(new Font("Arial", 1, 20));
            armoredShipPriceMetalLabel.setFont(customFontSmaller);
            armoredShipCostPanel.add(armoredShipPriceMetalLabel);
            armoredShipCostPanel.add(new JLabel(new ImageIcon(redstoneImageScaledTiny)));
            battleShipPriceDeuteriumLabel = new JLabel("" + Variables.DEUTERIUM_COST_ARMOREDSHIP);
            battleShipPriceDeuteriumLabel.setForeground(Globals.bottomFontColor);
            // battleShipPriceDeuteriumLabel.setFont(new Font("Arial", 1, 20));
            battleShipPriceDeuteriumLabel.setFont(customFontSmaller);
            armoredShipCostPanel.add(battleShipPriceDeuteriumLabel);

            armoredShipPanel.add(armoredShipCostPanel, BorderLayout.SOUTH);
            

            shopPanel.add(armoredShipPanel);

            //missileLauncher panel
            missileLauncherPanel = new JPanel();
            // missileLauncherPanel.setBackground(Color.RED);

            missileLauncherPanel.setLayout(new BorderLayout());
            missileLauncherPanel.setBackground(Globals.bottomBackgroundColor);

            buyMissileLauncherButton = new JButton("buyMissileLauncher");
            buyMissileLauncherButton.setBackground(Globals.bottomImageBackgroundColor);
            buyMissileLauncherButton.setIcon(new ImageIcon(MissileLauncher.getImgIcon().getImage().getScaledInstance(130, 80, Image.SCALE_SMOOTH)));
            buyMissileLauncherButton.setFont(new Font("Arial", 1, 0));
            buyMissileLauncherButton.setSize(30, 30);
            buyMissileLauncherButton.setPreferredSize(new Dimension(getWidth(),getHeight()));
            buyMissileLauncherButton.addActionListener(new ActionListener() {

                @Override
                public void actionPerformed(ActionEvent e) {
                    try {
                        AudioPlayer.buttonSound();
                        if(planet.getCurrentThreat() != null) {
                            if (planet.getCurrentThreat().isHasCombatStarted()) {
                                return;
                            }
                        }
                        planet.newMissileLauncher(1);
                    } catch (ResourceException e1) {
                        // TODO Auto-generated catch block
                        e1.printStackTrace();
                    }
                }
                
            });
            missileLauncherNamePanel = new JPanel();
            missileLauncherNameLabel = new JLabel("Missile Launcher - " + planet.getArmy()[4].size());
            missileLauncherNameLabel.setForeground(Globals.bottomFontColor);
            missileLauncherNamePanel.setBackground(Globals.bottomBackgroundColor);
            
            // missileLauncherNameLabel.setFont(new Font("Arial", 1, 20));
            missileLauncherNameLabel.setFont(customFontSmaller);

            missileLauncherNamePanel.add(missileLauncherNameLabel);

            missileLauncherPanel.add(missileLauncherNamePanel, BorderLayout.NORTH);
            missileLauncherPanel.add(buyMissileLauncherButton, BorderLayout.CENTER);

            missileLauncherCostPanel = new JPanel();
            missileLauncherCostPanel.setBackground(Globals.bottomBackgroundColor);
            
            missileLauncherCostPanel.add(new JLabel(new ImageIcon(metalImageScaledTiny)));
            missileLauncherPriceMetalLabel = new JLabel("" + Variables.METAL_COST_MISSILELAUNCHER);
            missileLauncherPriceMetalLabel.setForeground(Globals.bottomFontColor);
            // missileLauncherPriceMetalLabel.setFont(new Font("Arial", 1, 20));
            missileLauncherPriceMetalLabel.setFont(customFontSmaller);
            missileLauncherCostPanel.add(missileLauncherPriceMetalLabel);
            missileLauncherCostPanel.add(new JLabel(new ImageIcon(redstoneImageScaledTiny)));
            missileLauncherPriceDeuteriumLabel = new JLabel("" + Variables.DEUTERIUM_COST_MISSILELAUNCHER);
            missileLauncherPriceDeuteriumLabel.setForeground(Globals.bottomFontColor);
            // missileLauncherPriceDeuteriumLabel.setFont(new Font("Arial", 1, 20));
            missileLauncherPriceDeuteriumLabel.setFont(customFontSmaller);
            missileLauncherCostPanel.add(missileLauncherPriceDeuteriumLabel);

            missileLauncherPanel.add(missileLauncherCostPanel, BorderLayout.SOUTH);
            


            shopPanel.add(missileLauncherPanel);

            //ionCannon panel
            ionCannonPanel = new JPanel();
            // ionCannonPanel.setBackground(Color.YELLOW);

            ionCannonPanel.setLayout(new BorderLayout());
            ionCannonPanel.setBackground(Globals.bottomBackgroundColor);

            buyIonCannonButton = new JButton("buyIonCannon");
            buyIonCannonButton.setBackground(Globals.bottomImageBackgroundColor);
            buyIonCannonButton.setIcon(new ImageIcon(IonCannon.getImgIcon().getImage().getScaledInstance(110, 110, Image.SCALE_SMOOTH)));
            buyIonCannonButton.setFont(new Font("Arial", 1, 0));
            buyIonCannonButton.setSize(30, 30);
            buyIonCannonButton.setPreferredSize(new Dimension(getWidth(),getHeight()));
            buyIonCannonButton.addActionListener(new ActionListener() {

                @Override
                public void actionPerformed(ActionEvent e) {
                    try {
                        AudioPlayer.buttonSound();
                        if(planet.getCurrentThreat() != null) {
                            if (planet.getCurrentThreat().isHasCombatStarted()) {
                                return;
                            }
                        }
                        planet.newIonCannon(1);
                    } catch (ResourceException e1) {
                        // TODO Auto-generated catch block
                        e1.printStackTrace();
                    }
                }
                
            });
            ionCannonNamePanel = new JPanel();
            ionCannonNameLabel = new JLabel("Ion Cannon - " + planet.getArmy()[5].size());
            ionCannonNameLabel.setForeground(Globals.bottomFontColor);
            ionCannonNamePanel.setBackground(Globals.bottomBackgroundColor);
            
            // ionCannonNameLabel.setFont(new Font("Arial", 1, 20));
            ionCannonNameLabel.setFont(customFontSmaller);

            ionCannonNamePanel.add(ionCannonNameLabel);

            ionCannonPanel.add(ionCannonNamePanel, BorderLayout.NORTH);
            ionCannonPanel.add(buyIonCannonButton, BorderLayout.CENTER);

            ionCannonCostPanel = new JPanel();
            ionCannonCostPanel.setBackground(Globals.bottomBackgroundColor);
            
            ionCannonCostPanel.add(new JLabel(new ImageIcon(metalImageScaledTiny)));
            ionCannonPriceMetalLabel = new JLabel("" + Variables.METAL_COST_IONCANNON);
            ionCannonPriceMetalLabel.setForeground(Globals.bottomFontColor);
            // ionCannonPriceMetalLabel.setFont(new Font("Arial", 1, 20));
            ionCannonPriceMetalLabel.setFont(customFontSmaller);
            ionCannonCostPanel.add(ionCannonPriceMetalLabel);
            ionCannonCostPanel.add(new JLabel(new ImageIcon(redstoneImageScaledTiny)));
            ionCannonPriceDeuteriumLabel = new JLabel("" + Variables.DEUTERIUM_COST_IONCANNON);
            ionCannonPriceDeuteriumLabel.setForeground(Globals.bottomFontColor);
            // ionCannonPriceDeuteriumLabel.setFont(new Font("Arial", 1, 20));
            ionCannonPriceDeuteriumLabel.setFont(customFontSmaller);
            ionCannonCostPanel.add(ionCannonPriceDeuteriumLabel);

            ionCannonPanel.add(ionCannonCostPanel, BorderLayout.SOUTH);


            shopPanel.add(ionCannonPanel);

            //plasmaCannon panel
            plasmaCannonPanel = new JPanel();
            // plasmaCannonPanel.setBackground(Color.WHITE);

            plasmaCannonPanel.setLayout(new BorderLayout());
            plasmaCannonPanel.setBackground(Globals.bottomBackgroundColor);

            buyPlasmaCannonButton = new JButton("buyPlasmaCannon");
            buyPlasmaCannonButton.setBackground(Globals.bottomImageBackgroundColor);
            buyPlasmaCannonButton.setIcon(new ImageIcon(PlasmaCannon.getImgIcon().getImage().getScaledInstance(110, 110, Image.SCALE_SMOOTH)));
            buyPlasmaCannonButton.setFont(new Font("Arial", 1, 0));
            buyPlasmaCannonButton.setSize(30, 30);
            buyPlasmaCannonButton.setPreferredSize(new Dimension(getWidth(),getHeight()));
            buyPlasmaCannonButton.addActionListener(new ActionListener() {

                @Override
                public void actionPerformed(ActionEvent e) {
                    try {
                        AudioPlayer.buttonSound();
                        if(planet.getCurrentThreat() != null) {
                            if (planet.getCurrentThreat().isHasCombatStarted()) {
                                return;
                            }
                        }
                        planet.newPlasmaCannon(1);
                    } catch (ResourceException e1) {
                        // TODO Auto-generated catch block
                        e1.printStackTrace();
                    }
                }
                
            });
            plasmaCannonNamePanel = new JPanel();
            plasmaCannonNameLabel = new JLabel("Plasma Cannon - " + planet.getArmy()[6].size());
            plasmaCannonNameLabel.setForeground(Globals.bottomFontColor);
            plasmaCannonNamePanel.setBackground(Globals.bottomBackgroundColor);
            
            // plasmaCannonNameLabel.setFont(new Font("Arial", 1, 20));
            plasmaCannonNameLabel.setFont(customFontSmaller);

            plasmaCannonNamePanel.add(plasmaCannonNameLabel);

            plasmaCannonPanel.add(plasmaCannonNamePanel, BorderLayout.NORTH);
            plasmaCannonPanel.add(buyPlasmaCannonButton, BorderLayout.CENTER);

            plasmaCannonCostPanel = new JPanel();
            plasmaCannonCostPanel.setBackground(Globals.bottomBackgroundColor);
            
            plasmaCannonCostPanel.add(new JLabel(new ImageIcon(metalImageScaledTiny)));
            plasmaCannonPriceMetalLabel = new JLabel("" + Variables.METAL_COST_PLASMACANNON);
            plasmaCannonPriceMetalLabel.setForeground(Globals.bottomFontColor);
            // plasmaCannonPriceMetalLabel.setFont(new Font("Arial", 1, 20));
            plasmaCannonPriceMetalLabel.setFont(customFontSmaller);
            plasmaCannonCostPanel.add(plasmaCannonPriceMetalLabel);
            plasmaCannonCostPanel.add(new JLabel(new ImageIcon(redstoneImageScaledTiny)));
            plasmaCannonPriceDeuteriumLabel = new JLabel("" + Variables.DEUTERIUM_COST_PLASMACANNON);
            plasmaCannonPriceDeuteriumLabel.setForeground(Globals.bottomFontColor);
            // plasmaCannonPriceDeuteriumLabel.setFont(new Font("Arial", 1, 20));
            plasmaCannonPriceDeuteriumLabel.setFont(customFontSmaller);
            plasmaCannonCostPanel.add(plasmaCannonPriceDeuteriumLabel);

            plasmaCannonPanel.add(plasmaCannonCostPanel, BorderLayout.SOUTH);

            shopPanel.add(plasmaCannonPanel);


            // Adding the panel shopPanel, which is as big as the panel itself.
            mainPanel.add(shopPanel, BorderLayout.CENTER);
            mainPanel.setBackground(Color.WHITE);
    
            add(mainPanel);
        }

        public JLabel getHeavyHunterNameLabel() {
            return heavyHunterNameLabel;
        }

        public JLabel getLightHunterNameLabel() {
            return lightHunterNameLabel;
        }

        public JLabel getBattleShipNameLabel() {
            return battleShipNameLabel;
        }

        public JLabel getArmoredShipNameLabel() {
            return armoredShipNameLabel;
        }

        public JLabel getMissileLauncherNameLabel() {
            return missileLauncherNameLabel;
        }

        public JLabel getIonCannonNameLabel() {
            return ionCannonNameLabel;
        }

        public JLabel getPlasmaCannonNameLabel() {
            return plasmaCannonNameLabel;
        }
    }

