package GUI;
import classes.*;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
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

public class MainScreen extends JFrame {
    
    public MainScreen(Planet planet) {
        super();
        

        File folder = new File("./M3-Programacion/GUI/images/");

        File[] files = folder.listFiles(); // returns null if not a directory

        if (files != null) {
            for (File file : files) {
                System.out.println(file.getName() + (file.isDirectory() ? " [DIR]" : ""));
            }
        } else {
            System.out.println("The specified path is not a directory or doesn't exist.");
        }

        setDefaultCloseOperation(EXIT_ON_CLOSE);
		setSize(1480, 920);
        MainPanel mainPanel = new MainPanel(planet);

        String currentDir = System.getProperty("user.dir");
        System.out.println("Current working directory: " + currentDir);

        add(mainPanel);
		
		setVisible(true);
    }
}

class MainPanel extends JPanel {
    private LeftPanel leftPanel;
    private RightPanel rightPanel;
    private BottomPanel bottomPanel;
    private MiddlePanel middlePanel;

    MainPanel(Planet planet) {
        setBackground(Color.BLUE);
        setLayout(new BorderLayout());

        

        
        leftPanel = new LeftPanel(planet);
        rightPanel = new RightPanel();
        bottomPanel = new BottomPanel(planet);
        middlePanel = new MiddlePanel();
        
        middlePanel.setBackground(Color.MAGENTA);
        

        add(leftPanel, BorderLayout.WEST);
        add(rightPanel, BorderLayout.EAST);
        add(bottomPanel, BorderLayout.SOUTH);
        add(middlePanel, BorderLayout.CENTER);

        Timer timer = new Timer();
        TimerTask updateTask = new TimerTask() {
            public void run() {
                updateAll(planet);
            }
        };
        timer.schedule(updateTask, 0, 100);
        
        setVisible(true);
    }

    class LeftPanel extends JPanel implements ActionListener{
        private JPanel mainPanel, rotatingEarthPanel, infoPanel, metalTextPanel, metalResourcePanel, deuteriumTextPanel, deuteriumResourcePanel, technologyPanel,
        techAttackPanel, techDefensePanel, technologyTextPanel;
        private ImageIcon rotatingEarthIcon, metalIcon, deuteriumIcon, techDefenseIcon, techAttackIcon, plusIcon;
        private JLabel rotatingEarthLabel, metalTextLabel, deuteriumTextLabel, metalImageLabel, deuteriumImageLabel, metalAmountLabel, 
        deuteriumAmountLabel, technologyTextLabel, techAttackImgLabel, techDefenseImgLabel, techAttackLvlCost, techDefenseLvlCost, techAttackLvlUpLabel;
        private String metalStr, deuteriumStr;
        private JButton lvlUpAttackButton, lvlUpDefenseButton;
        private JTextArea techAttackLvlAmountTextArea, techDefenseLvlAmountTextArea;
    
        LeftPanel(Planet planet) {
            setSize(new Dimension(230,230));
            setPreferredSize(new Dimension(230,230));
            setLayout(new BorderLayout());
            add(new PaddingPanel(), BorderLayout.NORTH);
            add(new PaddingPanel(), BorderLayout.WEST);
            add(new PaddingPanel(), BorderLayout.EAST);

            mainPanel = new JPanel();
            mainPanel.setBackground(Color.orange);
            // mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
            mainPanel.setLayout(new GridLayout(4,1));

            rotatingEarthIcon = new ImageIcon("./M3-Programacion/GUI/images/rotating_earth.gif");
            rotatingEarthLabel = new JLabel(rotatingEarthIcon) {
                protected void paintComponent(Graphics g) {
                    g.drawImage(rotatingEarthIcon.getImage(), 0, 0, 150, 150, this);
                }
            };

            // Making the panel that has the rotating Earth in it
            rotatingEarthPanel = new JPanel();
            rotatingEarthPanel.setBackground(Color.orange);
            rotatingEarthPanel.setLayout(new BorderLayout());
            
            rotatingEarthPanel.add(new PaddingPanel(Color.ORANGE, new Dimension(getWidth()/5/2,0)), BorderLayout.WEST);
            rotatingEarthPanel.add(new PaddingPanel(Color.ORANGE, new Dimension(0,20/2)), BorderLayout.NORTH);
            rotatingEarthPanel.add(rotatingEarthLabel);

            // Making the info panel
            infoPanel = new JPanel();
            infoPanel.setLayout(new GridLayout(4,1));

            metalTextLabel = new JLabel("Resources");
            metalTextLabel.setFont(new Font("Arial", 1, 24));

            metalTextPanel = new JPanel();
            // metalTextLabel.setBackground(Color.MAGENTA);
            metalTextPanel.add(metalTextLabel);

            metalResourcePanel = new JPanel();
  
            metalIcon = new ImageIcon("./M3-Programacion/GUI/images/iron_ingot.png");
            Image metalIconScaled = metalIcon.getImage().getScaledInstance(40, 40, Image.SCALE_SMOOTH);
            metalImageLabel = new JLabel(new ImageIcon(metalIconScaled));

            metalStr = String.valueOf(planet.getMetal());
            metalTextLabel = new JLabel(metalStr);
            metalTextLabel.setFont(new Font("Arial", 1, 20));

            metalResourcePanel.add(metalImageLabel);
            metalResourcePanel.add(metalTextLabel);

            deuteriumResourcePanel = new JPanel();

            deuteriumIcon = new ImageIcon("./M3-Programacion/GUI/images/redstone.png");
            Image redstoneIconScaled = deuteriumIcon.getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH);
            deuteriumImageLabel = new JLabel(new ImageIcon(redstoneIconScaled));

            deuteriumStr = String.valueOf(planet.getDeuterium());
            deuteriumTextLabel = new JLabel(deuteriumStr);
            deuteriumTextLabel.setFont(new Font("Arial", 1, 20));

            deuteriumResourcePanel.add(deuteriumImageLabel);
            deuteriumResourcePanel.add(deuteriumTextLabel);
            
            infoPanel.add(metalTextPanel);
            infoPanel.add(metalResourcePanel);
            infoPanel.add(deuteriumResourcePanel);
            infoPanel.add(new PaddingPanel(Color.magenta)); // Simply to differentiate panels

            // Making the technology lvls Panel
            technologyPanel = new JPanel();
            technologyPanel.setLayout(new GridLayout(3,1));

            technologyTextPanel = new JPanel();

            technologyTextLabel = new JLabel("Technology");
            technologyTextLabel.setFont(new Font("Arial", 1, 20));
            technologyTextPanel.add(technologyTextLabel);
            technologyPanel.add(technologyTextPanel);

            techAttackPanel = new JPanel();

            techAttackLvlAmountTextArea = new JTextArea("" + planet.getTechnologyAttack());
            techAttackLvlAmountTextArea.setFont(new Font("Arial", 1, 20));
            techAttackLvlAmountTextArea.setPreferredSize(new Dimension(30,30));
            techAttackLvlAmountTextArea.setEditable(false);
            
            techAttackPanel.add(techAttackLvlAmountTextArea);

            plusIcon = new ImageIcon("./M3-Programacion/GUI/images/plus_icon.png");
            Image plusIconScaled = plusIcon.getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH);
            // techAttackLvlUpLabel = new JLabel(new ImageIcon(plusIconScaled));
            lvlUpAttackButton = new JButton("lvlUpAttackTech");
            lvlUpAttackButton.setIcon(new ImageIcon(plusIconScaled));
            lvlUpAttackButton.setFont(new Font("Arial", 1, 0));
            lvlUpAttackButton.setSize(30, 30);
            lvlUpAttackButton.setPreferredSize(new Dimension(plusIconScaled.getWidth(this),plusIconScaled.getHeight(this)));
            lvlUpAttackButton.addActionListener(new ActionListener() {

                @Override
                public void actionPerformed(ActionEvent e) {
                    try {
                        planet.upgradeTechnologyAttack();
                    } catch (ResourceException e1) {
                        // TODO Auto-generated catch block
                        e1.printStackTrace();
                    }
                }
                
            });
            techAttackPanel.add(lvlUpAttackButton);

            techAttackIcon = new ImageIcon("./M3-Programacion/GUI/images/sword_icon.png");
            Image techAttackIconScaled = techAttackIcon.getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH);
            techAttackImgLabel = new JLabel(new ImageIcon(techAttackIconScaled));
            techAttackPanel.add(techAttackImgLabel);

            techAttackLvlCost = new JLabel("" + planet.getUpgradeAttackTechnologyDeuteriumCost());
            techAttackLvlCost.setFont(new Font("Arial", 1, 20));
            techAttackPanel.add(techAttackLvlCost);
            Image redstoneImageScaledTiny = redstoneIconScaled.getScaledInstance(20, 20, Image.SCALE_SMOOTH);
            techAttackPanel.add(new JLabel(new ImageIcon(redstoneImageScaledTiny)));


            techDefensePanel = new JPanel();

            techDefenseLvlAmountTextArea = new JTextArea("" + planet.getTechnologyDefense());
            techDefenseLvlAmountTextArea.setEditable(false);
            techDefenseLvlAmountTextArea.setFont(new Font("Arial", 1, 20));
            techDefenseLvlAmountTextArea.setPreferredSize(new Dimension(30,30));
            techDefensePanel.add(techDefenseLvlAmountTextArea);

            lvlUpDefenseButton = new JButton("lvlUpDefenseTech");
            lvlUpDefenseButton.setIcon(new ImageIcon(plusIconScaled));
            lvlUpDefenseButton.setFont(new Font("Arial", 1, 0));
            lvlUpDefenseButton.setSize(30, 30);
            lvlUpDefenseButton.setPreferredSize(new Dimension(plusIconScaled.getWidth(this),plusIconScaled.getHeight(this)));
            lvlUpDefenseButton.addActionListener(new ActionListener() {

                @Override
                public void actionPerformed(ActionEvent e) {
                    try {
                        planet.upgradeTechnologyDefense();
                    } catch (ResourceException e1) {
                        // TODO Auto-generated catch block
                        e1.printStackTrace();
                    }
                }
                
            });
            techDefensePanel.add(lvlUpDefenseButton);

            techDefenseIcon = new ImageIcon("./M3-Programacion/GUI/images/shield_icon.png");
            Image techDefenseIconScaled = techDefenseIcon.getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH);
            techDefenseImgLabel = new JLabel(new ImageIcon(techDefenseIconScaled));
            techDefensePanel.add(techDefenseImgLabel);

            techDefenseLvlCost = new JLabel("" + planet.getUpgradeDefenseTechnologyDeuteriumCost());
            techDefenseLvlCost.setFont(new Font("Arial", 1, 20));
            techDefensePanel.add(techDefenseLvlCost);
            techDefensePanel.add(new JLabel(new ImageIcon(redstoneImageScaledTiny)));

            
            technologyPanel.add(techAttackPanel);
            technologyPanel.add(techDefensePanel);
            





            // Adding everything in order

            mainPanel.add(rotatingEarthPanel);
            mainPanel.add(infoPanel);
            mainPanel.add(technologyPanel);

            
            
    
            add(mainPanel);
        }

        public JLabel getTechDefenseLvlCost() {
            return techDefenseLvlCost;
        }

        public JTextArea getTechDefenseLvlAmountTextArea() {
            return techDefenseLvlAmountTextArea;
        }

        public JLabel getTechAttackLvlCost() {
            return techAttackLvlCost;
        }

        public JTextArea getTechAttackLvlAmountTextArea() {
            return techAttackLvlAmountTextArea;
        }

        public JLabel getMetalJLabel() {
            return metalTextLabel;
        }

        public void setMetalStr(String amount) {
            this.metalStr = amount;
        }
        
        public JLabel getDeuteriumJLabel() {
            return deuteriumTextLabel;
        }

        public void addTechLvlPlanet(Planet planet) throws ResourceException {
            planet.upgradeTechnologyAttack();
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            System.out.println(e.getActionCommand());
            if (e.getActionCommand() == "lvlUpAttackTech") {
                
            }
        }
    }

    class RightPanel extends JPanel {
        private JPanel mainPanel, panel1, panel2;

        RightPanel() {

            setSize(new Dimension(200,200));
            setPreferredSize(new Dimension(200,200));
            setLayout(new BorderLayout());
            add(new PaddingPanel(), BorderLayout.NORTH);
            add(new PaddingPanel(), BorderLayout.WEST);
            add(new PaddingPanel(), BorderLayout.EAST);
            
    
            mainPanel = new JPanel();
            panel1 = new JPanel();
            panel2 = new JPanel();
    
            mainPanel.setBackground(Color.GREEN);
    
            add(mainPanel);
        }
    }

    class BottomPanel extends JPanel {
        private JPanel mainPanel, shopPanel, lightHunterPanel, heavyHunterPanel, battleShipPanel, armoredShipPanel, missileLauncherPanel, 
        ionCannonPanel, plasmaCannonPanel, lightHunterNamePanel, heavyHunterNamePanel, battleShipNamePanel, armoredShipNamePanel,
        lightHunterCostPanel, heavyHunterCostPanel, battleShipCostPanel, armoredShipCostPanel;
        private JButton buyLightHunterButton, buyHeavyHunterButton, buyBattleShipButton, buyArmoredShipButton;
        private JLabel lightHunterNameLabel, heavyHunterNameLabel, battleShipNameLabel, armoredShipNameLabel;
        private JLabel lightHunterPriceMetalLabel, lightHunterPriceDeuteriumLabel, heavyHunterPriceMetalLabel, heavyHunterPriceDeuteriumLabel,
        battleShipPriceMetalLabel, battleShipPriceDeuteriumLabel, armoredShipPriceMetalLabel, armoredShipPriceDeuteriumLabel;
        private ImageIcon deuteriumIcon, metalIcon;

        BottomPanel(Planet planet) {
            setSize(new Dimension(200,200));
            setPreferredSize(new Dimension(200,200));
            setLayout(new BorderLayout());
            setBackground(Color.BLACK);
            add(new PaddingPanel(), BorderLayout.NORTH);
            add(new PaddingPanel(), BorderLayout.WEST);
            add(new PaddingPanel(), BorderLayout.EAST);
            add(new PaddingPanel(), BorderLayout.SOUTH);

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
            shopPanel.setBackground(Color.pink);

            //LightHunters panel
            lightHunterPanel = new JPanel();
            lightHunterPanel.setLayout(new BorderLayout());
            lightHunterPanel.setBackground(Color.GREEN);

            buyLightHunterButton = new JButton("buyLightHunter");
            buyLightHunterButton.setBackground(Color.white);
            buyLightHunterButton.setIcon(new ImageIcon(LightHunter.getImg().getImage().getScaledInstance(70, 100, Image.SCALE_SMOOTH)));
            buyLightHunterButton.setFont(new Font("Arial", 1, 0));
            buyLightHunterButton.setSize(30, 30);
            buyLightHunterButton.setPreferredSize(new Dimension(getWidth(),getHeight()));
            buyLightHunterButton.addActionListener(new ActionListener() {

                @Override
                public void actionPerformed(ActionEvent e) {
                    try {
                        planet.newLightHunter(1);;
                    } catch (ResourceException e1) {
                        // TODO Auto-generated catch block
                        e1.printStackTrace();
                    }
                }
                
            });
            lightHunterNamePanel = new JPanel();
            lightHunterNameLabel = new JLabel("Light Hunter - " + planet.getArmy()[0].size());
            
            lightHunterNameLabel.setFont(new Font("Arial", 1, 20));

            lightHunterNamePanel.add(lightHunterNameLabel);

            lightHunterPanel.add(lightHunterNamePanel, BorderLayout.NORTH);
            lightHunterPanel.add(buyLightHunterButton, BorderLayout.CENTER);

            lightHunterCostPanel = new JPanel();
            
            lightHunterCostPanel.add(new JLabel(new ImageIcon(metalImageScaledTiny)));
            lightHunterPriceMetalLabel = new JLabel("" + Variables.METAL_COST_LIGTHHUNTER);
            lightHunterPriceMetalLabel.setFont(new Font("Arial", 1, 20));
            lightHunterCostPanel.add(lightHunterPriceMetalLabel);
            lightHunterCostPanel.add(new JLabel(new ImageIcon(redstoneImageScaledTiny)));
            lightHunterPriceDeuteriumLabel = new JLabel("" + Variables.DEUTERIUM_COST_LIGTHHUNTER);
            lightHunterPriceDeuteriumLabel.setFont(new Font("Arial", 1, 20));
            lightHunterCostPanel.add(lightHunterPriceDeuteriumLabel);

            lightHunterPanel.add(lightHunterCostPanel, BorderLayout.SOUTH);

            shopPanel.add(lightHunterPanel);

            //HeavyHuntersPanel
            heavyHunterPanel = new JPanel();
            heavyHunterPanel.setLayout(new BorderLayout());
            heavyHunterPanel.setBackground(Color.BLACK);

            buyHeavyHunterButton = new JButton("buyLightHunter");
            buyHeavyHunterButton.setBackground(Color.white);
            buyHeavyHunterButton.setIcon(new ImageIcon(HeavyHunter.getImg().getImage().getScaledInstance(70, 100, Image.SCALE_SMOOTH)));
            buyHeavyHunterButton.setFont(new Font("Arial", 1, 0));
            buyHeavyHunterButton.setSize(30, 30);
            buyHeavyHunterButton.setPreferredSize(new Dimension(getWidth(),getHeight()));
            buyHeavyHunterButton.addActionListener(new ActionListener() {

                @Override
                public void actionPerformed(ActionEvent e) {
                    try {
                        planet.newHeavyHunter(1);;
                    } catch (ResourceException e1) {
                        // TODO Auto-generated catch block
                        e1.printStackTrace();
                    }
                }
                
            });
            heavyHunterNamePanel = new JPanel();
            heavyHunterNameLabel = new JLabel("Heavy Hunter - " + planet.getArmy()[1].size());
            
            heavyHunterNameLabel.setFont(new Font("Arial", 1, 20));

            heavyHunterNamePanel.add(heavyHunterNameLabel);

            heavyHunterPanel.add(heavyHunterNamePanel, BorderLayout.NORTH);
            heavyHunterPanel.add(buyHeavyHunterButton, BorderLayout.CENTER);

            heavyHunterCostPanel = new JPanel();
            
            heavyHunterCostPanel.add(new JLabel(new ImageIcon(metalImageScaledTiny)));
            heavyHunterPriceMetalLabel = new JLabel("" + Variables.METAL_COST_HEAVYHUNTER);
            heavyHunterPriceMetalLabel.setFont(new Font("Arial", 1, 20));
            heavyHunterCostPanel.add(heavyHunterPriceMetalLabel);
            heavyHunterCostPanel.add(new JLabel(new ImageIcon(redstoneImageScaledTiny)));
            heavyHunterPriceDeuteriumLabel = new JLabel("" + Variables.DEUTERIUM_COST_HEAVYHUNTER);
            heavyHunterPriceDeuteriumLabel.setFont(new Font("Arial", 1, 20));
            heavyHunterCostPanel.add(heavyHunterPriceDeuteriumLabel);

            heavyHunterPanel.add(heavyHunterCostPanel, BorderLayout.SOUTH);


            shopPanel.add(heavyHunterPanel);





            //battleShip panel
            battleShipPanel = new JPanel();
            battleShipPanel.setBackground(Color.ORANGE);

            battleShipPanel.setLayout(new BorderLayout());
            battleShipPanel.setBackground(Color.BLACK);

            buyBattleShipButton = new JButton("buyBattleShip");
            buyBattleShipButton.setBackground(Color.white);
            buyBattleShipButton.setIcon(new ImageIcon(BattleShip.getImg().getImage().getScaledInstance(70, 100, Image.SCALE_SMOOTH)));
            buyBattleShipButton.setFont(new Font("Arial", 1, 0));
            buyBattleShipButton.setSize(30, 30);
            buyBattleShipButton.setPreferredSize(new Dimension(getWidth(),getHeight()));
            buyBattleShipButton.addActionListener(new ActionListener() {

                @Override
                public void actionPerformed(ActionEvent e) {
                    try {
                        planet.newBattleShip(1);;
                    } catch (ResourceException e1) {
                        // TODO Auto-generated catch block
                        e1.printStackTrace();
                    }
                }
                
            });
            battleShipNamePanel = new JPanel();
            battleShipNameLabel = new JLabel("Battle Ship - " + planet.getArmy()[2].size());
            
            battleShipNameLabel.setFont(new Font("Arial", 1, 20));

            battleShipNamePanel.add(battleShipNameLabel);

            battleShipPanel.add(battleShipNamePanel, BorderLayout.NORTH);
            battleShipPanel.add(buyBattleShipButton, BorderLayout.CENTER);

            battleShipCostPanel = new JPanel();
            
            battleShipCostPanel.add(new JLabel(new ImageIcon(metalImageScaledTiny)));
            battleShipPriceMetalLabel = new JLabel("" + Variables.METAL_COST_BATTLESHIP);
            battleShipPriceMetalLabel.setFont(new Font("Arial", 1, 20));
            battleShipCostPanel.add(battleShipPriceMetalLabel);
            battleShipCostPanel.add(new JLabel(new ImageIcon(redstoneImageScaledTiny)));
            battleShipPriceDeuteriumLabel = new JLabel("" + Variables.DEUTERIUM_COST_BATTLESHIP);
            battleShipPriceDeuteriumLabel.setFont(new Font("Arial", 1, 20));
            battleShipCostPanel.add(battleShipPriceDeuteriumLabel);

            battleShipPanel.add(battleShipCostPanel, BorderLayout.SOUTH);
            

            shopPanel.add(battleShipPanel);

            shopPanel.add(battleShipPanel);

            //armoredShip panel
            armoredShipPanel = new JPanel();
            armoredShipPanel.setBackground(Color.CYAN);
            shopPanel.add(armoredShipPanel);

            //missileLauncher panel
            missileLauncherPanel = new JPanel();
            missileLauncherPanel.setBackground(Color.RED);
            shopPanel.add(missileLauncherPanel);

            //ionCannon panel
            ionCannonPanel = new JPanel();
            ionCannonPanel.setBackground(Color.YELLOW);
            shopPanel.add(ionCannonPanel);

            //plasmaCannon panel
            plasmaCannonPanel = new JPanel();
            plasmaCannonPanel.setBackground(Color.WHITE);
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
    }

    
    class MiddlePanel extends JPanel {

        private BufferedImage earthImage;

        MiddlePanel() {
            setLayout(new BorderLayout());
            add(new PaddingPanel(), BorderLayout.NORTH);

        }

        protected void paintComponent(Graphics g) {
            super.paintComponent(g);

        
            try {
                earthImage = ImageIO.read(new File("./M3-Programacion/GUI/images/earth.jpg"));
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

            Graphics2D g2d = (Graphics2D) g;
            g2d.drawImage(earthImage.getScaledInstance(getWidth(), getHeight(), Image.SCALE_SMOOTH), 0, 0, this);
            g2d.fillRect(200, 200, 300, 300);
            
        }
    }

    class PaddingPanel extends JPanel {
        PaddingPanel() {
            setBackground(Color.black);
            setPreferredSize(new Dimension(10,10));
        }

        PaddingPanel(Color color) {
            setBackground(color);
            setPreferredSize(new Dimension(10,10));
        }

        PaddingPanel(Dimension dimension) {
            setBackground(Color.black);
            setPreferredSize(dimension);
        }

        PaddingPanel(Color color, Dimension dimension) {
            setBackground(color);
            setPreferredSize(dimension);
        }
        
    }
    
    public void updateAll(Planet planet) {
        leftPanel.getMetalJLabel().setText(String.valueOf(planet.getMetal()));
        leftPanel.getDeuteriumJLabel().setText(String.valueOf(planet.getDeuterium()));
        leftPanel.getTechAttackLvlAmountTextArea().setText("" + planet.getTechnologyAttack());
        leftPanel.getTechAttackLvlCost().setText("" + planet.getUpgradeAttackTechnologyDeuteriumCost());
        leftPanel.getTechDefenseLvlAmountTextArea().setText("" + planet.getTechnologyDefense());
        leftPanel.getTechDefenseLvlCost().setText("" + planet.getUpgradeDefenseTechnologyDeuteriumCost());

        bottomPanel.getLightHunterNameLabel().setText("Light Hunter - " + planet.getArmy()[0].size());
        bottomPanel.getHeavyHunterNameLabel().setText("Heavy Hunter - " + planet.getArmy()[1].size());
        bottomPanel.getBattleShipNameLabel().setText("Battle Ship- " + planet.getArmy()[2].size());
    }
}



