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


public class LeftPanel extends JPanel implements ActionListener{
        private JPanel mainPanel, rotatingEarthPanel, infoPanel, metalTextPanel, metalResourcePanel, deuteriumTextPanel, deuteriumResourcePanel, technologyPanel,
        techAttackPanel, techDefensePanel, technologyTextPanel, resourcesTextPanel, minesPanel, minesTxtPanel, metalMinePanel, deuteriumMinePanel;
        private ImageIcon rotatingEarthIcon, metalIcon, deuteriumIcon, techDefenseIcon, techAttackIcon, plusIcon;
        private JLabel rotatingEarthLabel, metalTextLabel, deuteriumTextLabel, metalImageLabel, deuteriumImageLabel, metalAmountLabel, 
        deuteriumAmountLabel, technologyTextLabel, techAttackImgLabel, techDefenseImgLabel, techAttackLvlCost, techDefenseLvlCost, techAttackLvlUpLabel,
        resourcesTextLabel, minesTxtLabel, metalMineImgLabel, deuteriumMineImgLabel, metalMineCostLabel, deuteriumMineCostLabel;
        private String metalStr, deuteriumStr;
        private JButton lvlUpAttackButton, lvlUpDefenseButton,lvlUpMetalMineButton, lvlUpDeuteriumMineButton;
        private JTextArea techAttackLvlAmountTextArea, techDefenseLvlAmountTextArea, metalMineLvlTextArea, deuteriumMineLvlTextArea;
        private Font customFont, customFontSmaller;
    
        LeftPanel(Planet planet) {
            setSize(new Dimension(230,230));
            setPreferredSize(new Dimension(230,230));
            setLayout(new BorderLayout());
            add(new PaddingPanel(), BorderLayout.NORTH);
            add(new PaddingPanel(), BorderLayout.WEST);
            add(new PaddingPanel(), BorderLayout.EAST);

            try {
                customFont = Font.createFont(Font.TRUETYPE_FONT, new File(Globals.customFont)).deriveFont(24f);
                customFontSmaller = Font.createFont(Font.TRUETYPE_FONT, new File(Globals.customFont)).deriveFont(20f);
            } catch (FontFormatException | IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

            mainPanel = new JPanel();
            // mainPanel.setBackground(Color.orange);
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
            // rotatingEarthPanel.setBackground(Color.orange);
            rotatingEarthPanel.setLayout(new BorderLayout());
            
            rotatingEarthPanel.add(new PaddingPanel(new Color(50,50,50), new Dimension(getWidth()/5/2+5,0)), BorderLayout.WEST);
            rotatingEarthPanel.add(new PaddingPanel(new Color(50,50,50), new Dimension(0,10)), BorderLayout.NORTH);
            rotatingEarthPanel.setBackground(new Color(50,50,50));
            rotatingEarthPanel.add(rotatingEarthLabel);

            // Making the info panel
            infoPanel = new JPanel();
            infoPanel.setLayout(new GridLayout(3,1));

            resourcesTextLabel = new JLabel("Resources");
            // resourcesTextLabel.setFont(new Font("Arial", 1, 24));
            resourcesTextLabel.setFont(customFont);
            resourcesTextLabel.setForeground(Color.WHITE);

            resourcesTextPanel = new JPanel();
            resourcesTextPanel.setBackground(new Color(30,30,30));
            // resourcesTextLabel.setBackground(Color.MAGENTA);
            resourcesTextPanel.add(resourcesTextLabel);

            metalResourcePanel = new JPanel();
  
            metalIcon = new ImageIcon("./M3-Programacion/GUI/images/iron_ingot.png");
            Image metalIconScaled = metalIcon.getImage().getScaledInstance(40, 40, Image.SCALE_SMOOTH);
            metalImageLabel = new JLabel(new ImageIcon(metalIconScaled));

            metalStr = String.valueOf(planet.getMetal() + " +" + 2000 * planet.getMetalMineLvl());
            metalTextLabel = new JLabel(metalStr);
            // metalTextLabel.setFont(new Font("Arial", 1, 20));
            metalTextLabel.setFont(customFontSmaller);

            metalResourcePanel.add(metalImageLabel);
            metalResourcePanel.add(metalTextLabel);

            deuteriumResourcePanel = new JPanel();

            deuteriumIcon = new ImageIcon("./M3-Programacion/GUI/images/redstone.png");
            Image redstoneIconScaled = deuteriumIcon.getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH);
            deuteriumImageLabel = new JLabel(new ImageIcon(redstoneIconScaled));

            deuteriumStr = String.valueOf(planet.getDeuterium()) + " +" + 800 * planet.getDeuteriumMineLvl();
            deuteriumTextLabel = new JLabel(deuteriumStr);
            // deuteriumTextLabel.setFont(new Font("Arial", 1, 20));
            deuteriumTextLabel.setFont(customFontSmaller);

            deuteriumResourcePanel.add(deuteriumImageLabel);
            deuteriumResourcePanel.add(deuteriumTextLabel);
            
            infoPanel.add(resourcesTextPanel);
            infoPanel.add(metalResourcePanel);
            infoPanel.add(deuteriumResourcePanel);
            // infoPanel.add(new PaddingPanel(Color.magenta)); // Simply to differentiate panels

            // Making the technology lvls Panel
            technologyPanel = new JPanel();
            technologyPanel.setLayout(new GridLayout(3,1));

            technologyTextPanel = new JPanel();
            technologyTextPanel.setBackground(new Color(30,30,30));

            technologyTextLabel = new JLabel("Technology");
            
            // technologyTextLabel.setFont(new Font("Arial", 1, 24));
            technologyTextLabel.setFont(customFont);
            technologyTextLabel.setForeground(Color.WHITE);

            
            technologyTextPanel.add(technologyTextLabel);
            technologyPanel.add(technologyTextPanel);

            techAttackPanel = new JPanel();

            techAttackLvlAmountTextArea = new JTextArea("" + planet.getTechnologyAttack());
            // techAttackLvlAmountTextArea.setFont(new Font("Arial", 1, 20));
            techAttackLvlAmountTextArea.setFont(customFontSmaller);
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
            // techAttackLvlCost.setFont(new Font("Arial", 1, 20));
            techAttackLvlCost.setFont(customFontSmaller);
            techAttackPanel.add(techAttackLvlCost);
            Image redstoneImageScaledTiny = redstoneIconScaled.getScaledInstance(20, 20, Image.SCALE_SMOOTH);
            techAttackPanel.add(new JLabel(new ImageIcon(redstoneImageScaledTiny)));


            techDefensePanel = new JPanel();

            techDefenseLvlAmountTextArea = new JTextArea("" + planet.getTechnologyDefense());
            techDefenseLvlAmountTextArea.setEditable(false);
            // techDefenseLvlAmountTextArea.setFont(new Font("Arial", 1, 20));
            techDefenseLvlAmountTextArea.setFont(customFontSmaller);
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
            // techDefenseLvlCost.setFont(new Font("Arial", 1, 20));
            techDefenseLvlCost.setFont(customFontSmaller);
            techDefensePanel.add(techDefenseLvlCost);
            techDefensePanel.add(new JLabel(new ImageIcon(redstoneImageScaledTiny)));

            
            technologyPanel.add(techAttackPanel);
            technologyPanel.add(techDefensePanel);

            // Mines panel
            minesPanel = new JPanel();
            minesPanel.setLayout(new GridLayout(3,1));
            minesPanel.setBackground(Color.PINK);

            minesTxtPanel = new JPanel();
            minesTxtPanel.setBackground(new Color(30,30,30));
            minesTxtLabel = new JLabel("Mines");
            // minesTxtLabel.setFont(new Font("Arial", 1, 24));
            minesTxtLabel.setFont(customFont);
            minesTxtLabel.setForeground(Color.WHITE);
            minesTxtPanel.add(minesTxtLabel);

            // metal mine panel
            metalMinePanel = new JPanel();

            metalMineLvlTextArea = new JTextArea("" + planet.getMetalMineLvl());
            metalMineLvlTextArea.setEditable(false);
            // metalMineLvlTextArea.setFont(new Font("Arial", 1, 20));
            metalMineLvlTextArea.setFont(customFontSmaller);
            metalMineLvlTextArea.setPreferredSize(new Dimension(30,30));
            metalMinePanel.add(metalMineLvlTextArea);

            lvlUpMetalMineButton = new JButton("lvlUpMetalMine");
            lvlUpMetalMineButton.setIcon(new ImageIcon(plusIconScaled));
            lvlUpMetalMineButton.setFont(new Font("Arial", 1, 0));
            lvlUpMetalMineButton.setSize(30, 30);
            lvlUpMetalMineButton.setPreferredSize(new Dimension(plusIconScaled.getWidth(this),plusIconScaled.getHeight(this)));
            lvlUpMetalMineButton.addActionListener(new ActionListener() {

                @Override
                public void actionPerformed(ActionEvent e) {
                    planet.upgradeMetalMine();
                }
                
            });
            metalMineImgLabel = new JLabel(new ImageIcon(metalIconScaled));

            metalMineCostLabel = new JLabel("" + planet.getUpgradeMetalMineLvlMetalCost());
            // metalMineCostLabel.setFont(new Font("Arial", 1, 20));
            metalMineCostLabel.setFont(customFontSmaller);

            metalMinePanel.add(lvlUpMetalMineButton);
            metalMinePanel.add(metalMineImgLabel);
            metalMinePanel.add(metalMineCostLabel);
            metalMinePanel.add(new JLabel(new ImageIcon(metalIconScaled.getScaledInstance(20, 20, Image.SCALE_SMOOTH))));

            // deuterium mine panel
            deuteriumMinePanel = new JPanel();

            deuteriumMineLvlTextArea = new JTextArea("" + planet.getDeuteriumMineLvl());
            deuteriumMineLvlTextArea.setEditable(false);
            // deuteriumMineLvlTextArea.setFont(new Font("Arial", 1, 20));
            deuteriumMineLvlTextArea.setFont(customFontSmaller);
            deuteriumMineLvlTextArea.setPreferredSize(new Dimension(30,30));
            deuteriumMinePanel.add(deuteriumMineLvlTextArea);

            lvlUpDeuteriumMineButton = new JButton("lvlUpDeuteriumMine");
            lvlUpDeuteriumMineButton.setIcon(new ImageIcon(plusIconScaled));
            lvlUpDeuteriumMineButton.setFont(new Font("Arial", 1, 0));
            lvlUpDeuteriumMineButton.setSize(30, 30);
            lvlUpDeuteriumMineButton.setPreferredSize(new Dimension(plusIconScaled.getWidth(this),plusIconScaled.getHeight(this)));
            lvlUpDeuteriumMineButton.addActionListener(new ActionListener() {

                @Override
                public void actionPerformed(ActionEvent e) {
                    planet.upgradeDeuteriumMine();
                }
                
            });
            deuteriumMineImgLabel = new JLabel(new ImageIcon(redstoneIconScaled));

            deuteriumMineCostLabel = new JLabel("" + planet.getUpgradeDeuteriumMineLvlDeuteriumCost());
            // deuteriumMineCostLabel.setFont(new Font("Arial", 1, 20));
            deuteriumMineCostLabel.setFont(customFontSmaller);

            deuteriumMinePanel.add(lvlUpDeuteriumMineButton);
            deuteriumMinePanel.add(deuteriumMineImgLabel);
            deuteriumMinePanel.add(deuteriumMineCostLabel);
            deuteriumMinePanel.add(new JLabel(new ImageIcon(redstoneImageScaledTiny)));


            //
            minesPanel.add(minesTxtPanel);
            minesPanel.add(metalMinePanel);
            minesPanel.add(deuteriumMinePanel);


            // Adding everything in order
            mainPanel.add(rotatingEarthPanel);
            mainPanel.add(infoPanel);
            mainPanel.add(technologyPanel);
            mainPanel.add(minesPanel);

            
            
    
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
        

        public JLabel getMetalMineCostLabel() {
            return metalMineCostLabel;
        }

        public JLabel getDeuteriumMineCostLabel() {
            return deuteriumMineCostLabel;
        }

        public JTextArea getMetalMineLvlTextArea() {
            return metalMineLvlTextArea;
        }

        public JTextArea getDeuteriumMineLvlTextArea() {
            return deuteriumMineLvlTextArea;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            System.out.println(e.getActionCommand());
            if (e.getActionCommand() == "lvlUpAttackTech") {
                
            }
        }
    }
