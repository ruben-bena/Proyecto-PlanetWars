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


public class LeftPanel extends JPanel implements ActionListener{
        private JPanel mainPanel, rotatingEarthPanel, infoPanel, metalTextPanel, metalResourcePanel, deuteriumTextPanel, deuteriumResourcePanel, technologyPanel,
        techAttackPanel, techDefensePanel, technologyTextPanel, resourcesTextPanel;
        private ImageIcon rotatingEarthIcon, metalIcon, deuteriumIcon, techDefenseIcon, techAttackIcon, plusIcon;
        private JLabel rotatingEarthLabel, metalTextLabel, deuteriumTextLabel, metalImageLabel, deuteriumImageLabel, metalAmountLabel, 
        deuteriumAmountLabel, technologyTextLabel, techAttackImgLabel, techDefenseImgLabel, techAttackLvlCost, techDefenseLvlCost, techAttackLvlUpLabel,
        resourcesTextLabel;
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
            infoPanel.setLayout(new GridLayout(4,1));

            resourcesTextLabel = new JLabel("Resources");
            resourcesTextLabel.setFont(new Font("Arial", 1, 24));
            resourcesTextLabel.setForeground(Color.WHITE);

            resourcesTextPanel = new JPanel();
            resourcesTextPanel.setBackground(new Color(30,30,30));
            // resourcesTextLabel.setBackground(Color.MAGENTA);
            resourcesTextPanel.add(resourcesTextLabel);

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
            
            infoPanel.add(resourcesTextPanel);
            infoPanel.add(metalResourcePanel);
            infoPanel.add(deuteriumResourcePanel);
            // infoPanel.add(new PaddingPanel(Color.magenta)); // Simply to differentiate panels

            // Making the technology lvls Panel
            technologyPanel = new JPanel();
            technologyPanel.setLayout(new GridLayout(3,1));

            technologyTextPanel = new JPanel();
            technologyTextPanel.setBackground(new Color(50,50,50));

            technologyTextLabel = new JLabel("Technology");
            technologyTextLabel.setFont(new Font("Arial", 1, 24));
            technologyTextLabel.setForeground(Color.WHITE);

            
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
