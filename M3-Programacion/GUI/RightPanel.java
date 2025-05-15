package GUI;

import classes.Main;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontFormatException;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import classes.Battle;
import classes.Main;
import classes.Planet;
import classes.ResourceException;
import classes.ThreatTimer;

import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

class RightPanel extends JPanel {
        private JPanel mainPanel, imagePanel, buttonsPanel, upperPanel, fixArmyPanel, costFixArmyPanel, costFixArmyAmountPanel, invadePanel;
        private JButton newGameButton, battleReportButton, settingsButton, exitButton, viewCurrentThreatButton, fixArmyButton, startABattle, invadeButton;
        private JLabel metalImageLabel, deuteriumImageLabel, metalCostFixLabel, deuteriumCostFixLabel;
        private Planet planet;
        private ImageIcon metalIcon, deuteriumIcon;
        private MainScreen ms;
        private Font customFont, customFontSmaller, customFontSmallest;

        RightPanel(Planet planet, MainScreen ms) {
            this.planet = planet;
            this.ms = ms;

            try {
                customFont = Font.createFont(Font.TRUETYPE_FONT, new File(Globals.customFont)).deriveFont(24f);
                customFontSmaller = Font.createFont(Font.TRUETYPE_FONT, new File(Globals.customFont)).deriveFont(18f);
                customFontSmallest = Font.createFont(Font.TRUETYPE_FONT, new File(Globals.customFont)).deriveFont(14f);
            } catch (FontFormatException | IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

  
            int gameSettingsWidth = 200;
            int gameSettingsHeight = 200;
            setSize(new Dimension(gameSettingsWidth,gameSettingsHeight));
            setPreferredSize(new Dimension(gameSettingsWidth,gameSettingsHeight));
            setLayout(new BorderLayout());
            add(new PaddingPanel(), BorderLayout.NORTH);
            add(new PaddingPanel(), BorderLayout.WEST);
            add(new PaddingPanel(), BorderLayout.EAST);
            
            // mainPanel --> Contains the subpanels for image and buttons.
            mainPanel = new JPanel();
            mainPanel.setBackground(Color.BLACK);
            mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
            add(mainPanel);


            upperPanel = new JPanel();
            upperPanel.setLayout(new GridLayout(2,1));
            
            fixArmyPanel = new JPanel();
            fixArmyPanel.setLayout(new GridLayout(3,1));


            fixArmyButton = new JButton("Fix Army");
            // fixArmyButton.setFont(new Font("Arial", Font.BOLD, 24));
            fixArmyButton.setFont(customFont);
            fixArmyButton.setBackground(Globals.rightButtonsColor);
            fixArmyButton.setForeground(Globals.rightButtonsFontColor);
            fixArmyButton.addActionListener(new ButtonEvents());
            fixArmyPanel.add(fixArmyButton);


            costFixArmyPanel = new JPanel();
            costFixArmyPanel.setLayout(new BorderLayout());
            costFixArmyAmountPanel = new JPanel();
            costFixArmyAmountPanel.setBackground(Globals.rightSecPanelColor);

            metalIcon = new ImageIcon("./M3-Programacion/GUI/images/iron_ingot.png");
            Image metalIconScaled = metalIcon.getImage().getScaledInstance(40, 40, Image.SCALE_SMOOTH);
            metalImageLabel = new JLabel(new ImageIcon(metalIconScaled));
            
            costFixArmyAmountPanel.add(metalImageLabel);
            metalCostFixLabel = new JLabel("" + planet.getFixArmyCost()[0]);
            metalCostFixLabel.setForeground(Globals.rightSecFontColor);
            costFixArmyAmountPanel.add(metalCostFixLabel);

            deuteriumIcon = new ImageIcon("./M3-Programacion/GUI/images/redstone.png");
            Image redstoneIconScaled = deuteriumIcon.getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH);
            deuteriumImageLabel = new JLabel(new ImageIcon(redstoneIconScaled));

            costFixArmyAmountPanel.add(deuteriumImageLabel);

            deuteriumCostFixLabel = new JLabel("" + planet.getFixArmyCost()[1]);
            deuteriumCostFixLabel.setForeground(Globals.rightSecFontColor);
            costFixArmyAmountPanel.add(deuteriumCostFixLabel);
            
            costFixArmyPanel.add(costFixArmyAmountPanel, BorderLayout.CENTER);

            fixArmyPanel.add(costFixArmyPanel);

            startABattle = new JButton("Start a fight");
            // startABattle.setFont(new Font("Arial", Font.BOLD, 20));
            startABattle.setFont(customFontSmaller);
            startABattle.setBackground(Globals.rightButtonsColor);
            startABattle.setForeground(Globals.rightButtonsFontColor);
            startABattle.addActionListener(new ActionListener() {

                @Override
                public void actionPerformed(ActionEvent e) {
                    // TODO Auto-generated method stub
                    new ThreatTimer(planet, ms, 1);
                }
                
            });

            fixArmyPanel.add(startABattle);
            

            upperPanel.add(fixArmyPanel);
            

            viewCurrentThreatButton = new JButton("View Threat");
            // viewCurrentThreatButton.
            viewCurrentThreatButton.setBackground(Globals.rightButtonsColor);
            viewCurrentThreatButton.setForeground(Globals.rightButtonsFontColor);
            // viewCurrentThreatButton.setFont(new Font("Arial", Font.BOLD, 14));
            viewCurrentThreatButton.setFont(customFontSmaller);
            viewCurrentThreatButton.addActionListener(new ButtonEvents());

            upperPanel.add(viewCurrentThreatButton);

            invadePanel = new JPanel();
            invadeButton = new JButton("Invade");
            invadeButton.addActionListener(new ButtonEvents());
            invadePanel.setLayout(new BorderLayout());
            invadePanel.add(invadeButton);

            imagePanel = new JPanel();
            imagePanel.setBackground(Color.BLACK);

            
            mainPanel.add(upperPanel);

            mainPanel.add(invadePanel);
            // buttonsPanel
            // TODO: Add button that pauses the game (optional)
            buttonsPanel = new JPanel();
            int buttonsPanelHeight = 300;
            buttonsPanel.setLayout(new GridLayout(4,1));
            buttonsPanel.setPreferredSize(new Dimension(Integer.MAX_VALUE, buttonsPanelHeight));
            buttonsPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, buttonsPanelHeight));
            buttonsPanel.setBackground(Color.YELLOW);

            newGameButton = new JButton("New Game");
            // newGameButton.setFont(new Font("Arial", Font.BOLD, 18));
            newGameButton.setFont(customFontSmaller);
            newGameButton.setBackground(Globals.rightButtonsColor);
            newGameButton.setForeground(Globals.rightButtonsFontColor);
            buttonsPanel.add(newGameButton);
            newGameButton.addActionListener(new ButtonEvents());

            battleReportButton = new JButton("Battle Report");
            // battleReportButton.setFont(new Font("Arial", Font.BOLD, 18));
            battleReportButton.setFont(customFontSmaller);
            battleReportButton.setBackground(Globals.rightButtonsColor);
            battleReportButton.setForeground(Globals.rightButtonsFontColor);
            buttonsPanel.add(battleReportButton);
            battleReportButton.addActionListener(new ButtonEvents());
            
            settingsButton = new JButton("Settings");
            // settingsButton.setFont(new Font("Arial", Font.BOLD, 18));
            settingsButton.setFont(customFontSmaller);
            settingsButton.setBackground(Globals.rightButtonsColor);
            settingsButton.setForeground(Globals.rightButtonsFontColor);
            buttonsPanel.add(settingsButton);
            settingsButton.addActionListener(new ButtonEvents());

            exitButton = new JButton("Exit");
            // exitButton.setFont(new Font("Arial", Font.BOLD, 18));
            exitButton.setFont(customFontSmaller);
            exitButton.setBackground(Globals.rightButtonsColor);
            exitButton.setForeground(Globals.rightButtonsFontColor);
            buttonsPanel.add(exitButton);
            exitButton.addActionListener(new ButtonEvents());

            mainPanel.add(buttonsPanel);
        }

        public JLabel getMetalCostFixLabel() {
            return metalCostFixLabel;
        }
        public JLabel getDeuteriumCostFixLabel() {
            return deuteriumCostFixLabel;
        }

        class ButtonEvents implements ActionListener {

            @Override
            public void actionPerformed(ActionEvent e) {
                if (e.getActionCommand().equals("New Game")) {
                    System.out.println("New Game pressed");
                    newGameEvent();   
                }
                if (e.getActionCommand().equals("Battle Report")) {
                    System.out.println("Battle Report pressed");
                    battleReportEvent();
                }
                if (e.getActionCommand().equals("Settings")) {
                    System.out.println("Settings pressed");
                    settingsEvent();
                }
                if (e.getActionCommand().equals("Exit")) {
                    System.out.println("Exit pressed");
                    exitEvent();
                }

                if (e.getActionCommand().equals("View Threat")) {
                    if (planet.isActiveThreat()) {
                        new ThreatFrame(planet);
                    }

                    System.out.println("Current threat");
                }

                if (e.getActionCommand().equals("Fix Army")) {
                    fixArmyEvent();
                    System.out.println("Fix Army pressed");
                    
                }

                if (e.getActionCommand() == "Invade") {
                    planet.setCurrentThreat(new Battle(planet, Main.createEnemyPlanet(planet), ms.getMainPanel(), ms, 1));
                    ms.getMainPanel().getMiddlePanel().doInvadeDisplay();
                }
            }

        }
        public void fixArmyEvent() {
            if(planet.getCurrentThreat() != null) {
                if (planet.getCurrentThreat().isHasCombatStarted()) {
                    return;
                }
            }
            if (planet.getMetal() > planet.getFixArmyCost()[0] && planet.getDeuterium() > planet.getFixArmyCost()[1]) {
                planet.setMetal(planet.getMetal() - planet.getFixArmyCost()[0]);
                planet.setDeuterium(planet.getDeuterium() - planet.getFixArmyCost()[1]);
                planet.resetArmyArmor();
            }
        }
        public void newGameEvent()  {
            try {
                planet.newGame();
            } catch (ResourceException e) {
                e.printStackTrace();
            }
        }
    
        public void battleReportEvent() {
            new ReportsFrame(planet);
            // TODO: Define this method when DDBB methods are ready

        }
    
        public void settingsEvent() {
            new SettingsFrame(planet, ms.getMainPanel().getMiddlePanel());
        }
    
        public void exitEvent() {
            JDialog exitWindow = new JDialog(SwingUtilities.getWindowAncestor(this), "Exit");
            exitWindow.setResizable(false);
            exitWindow.setLocationRelativeTo(null);
            exitWindow.setSize(300, 100);
            exitWindow.setBackground(new Color(30,30,30));
            exitWindow.getContentPane().setLayout(new BoxLayout(exitWindow.getContentPane(), BoxLayout.Y_AXIS));
    
            // exitLabel
            JLabel exitLabel = new JLabel("Are you sure you want to exit?");

            exitWindow.add(exitLabel);
    
            // exitButtonsPanel
            JPanel exitButtonsPanel = new JPanel();
            exitWindow.add(exitButtonsPanel);
    
            JButton confirmExitButton = new JButton("Yes");
            exitButtonsPanel.add(confirmExitButton);
            confirmExitButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    System.out.println("Yes pressed");
                    ms.dispose();
                    Main.closeGame();
                }
            });
    
            JButton cancelExitButton = new JButton("No");
            exitButtonsPanel.add(cancelExitButton);
            cancelExitButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    System.out.println("No pressed");
                    exitWindow.dispose();
                }
            });
        
            exitWindow.setVisible(true);
        }
    }