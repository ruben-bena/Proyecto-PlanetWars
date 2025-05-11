package GUI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import classes.Planet;
import classes.ResourceException;

import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

class RightPanel extends JPanel {
        private JPanel mainPanel, imagePanel, buttonsPanel, upperPanel, fixArmyPanel, costFixArmyPanel, costFixArmyAmountPanel;
        private JButton newGameButton, battleReportButton, settingsButton, exitButton, viewCurrentThreatButton, fixArmyButton;
        private JLabel metalImageLabel, deuteriumImageLabel, metalCostFixLabel, deuteriumCostFixLabel;
        private Planet planet;
        private ImageIcon metalIcon, deuteriumIcon;

        RightPanel(Planet planet) {
            this.planet = planet;
            // GameSettingsPanel
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
            mainPanel.setBackground(Color.GREEN);
            mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
            add(mainPanel);

            // imagePanel
            // TODO: Add image
            upperPanel = new JPanel();
            upperPanel.setLayout(new GridLayout(2,1));
            
            fixArmyPanel = new JPanel();
            fixArmyPanel.setLayout(new GridLayout(3,1));


            fixArmyButton = new JButton("Fix Army");
            fixArmyButton.setFont(new Font("Arial", Font.BOLD, 24));
            fixArmyButton.setBackground(Color.black);
            fixArmyButton.setForeground(Color.WHITE);
            fixArmyButton.addActionListener(new ButtonEvents());
            fixArmyPanel.add(fixArmyButton);


            costFixArmyPanel = new JPanel();
            costFixArmyPanel.setLayout(new BorderLayout());
            costFixArmyAmountPanel = new JPanel();

            metalIcon = new ImageIcon("./M3-Programacion/GUI/images/iron_ingot.png");
            Image metalIconScaled = metalIcon.getImage().getScaledInstance(40, 40, Image.SCALE_SMOOTH);
            metalImageLabel = new JLabel(new ImageIcon(metalIconScaled));
            
            costFixArmyAmountPanel.add(metalImageLabel);
            metalCostFixLabel = new JLabel("" + planet.getFixArmyCost()[0]);
            costFixArmyAmountPanel.add(metalCostFixLabel);

            deuteriumIcon = new ImageIcon("./M3-Programacion/GUI/images/redstone.png");
            Image redstoneIconScaled = deuteriumIcon.getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH);
            deuteriumImageLabel = new JLabel(new ImageIcon(redstoneIconScaled));

            costFixArmyAmountPanel.add(deuteriumImageLabel);

            deuteriumCostFixLabel = new JLabel("" + planet.getFixArmyCost()[1]);
            costFixArmyAmountPanel.add(deuteriumCostFixLabel);
            
            costFixArmyPanel.add(costFixArmyAmountPanel, BorderLayout.CENTER);

            fixArmyPanel.add(costFixArmyPanel);

            upperPanel.add(fixArmyPanel);
            

            viewCurrentThreatButton = new JButton("View Current Threat");
            viewCurrentThreatButton.addActionListener(new ButtonEvents());

            upperPanel.add(viewCurrentThreatButton);

            

            imagePanel = new JPanel();
            imagePanel.setBackground(Color.RED);

            
            mainPanel.add(upperPanel);

            mainPanel.add(imagePanel);
            // buttonsPanel
            // TODO: Add button that pauses the game (optional)
            buttonsPanel = new JPanel();
            int buttonsPanelHeight = 300;
            buttonsPanel.setLayout(new GridLayout(4,1));
            buttonsPanel.setPreferredSize(new Dimension(Integer.MAX_VALUE, buttonsPanelHeight));
            buttonsPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, buttonsPanelHeight));
            buttonsPanel.setBackground(Color.YELLOW);

            newGameButton = new JButton("New Game");
            buttonsPanel.add(newGameButton);
            newGameButton.addActionListener(new ButtonEvents());

            battleReportButton = new JButton("Battle Report");
            buttonsPanel.add(battleReportButton);
            battleReportButton.addActionListener(new ButtonEvents());
            
            settingsButton = new JButton("Settings");
            buttonsPanel.add(settingsButton);
            settingsButton.addActionListener(new ButtonEvents());

            exitButton = new JButton("Exit");
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

                if (e.getActionCommand().equals("View Current Threat")) {
                    if (planet.isActiveThreat()) {
                        new ThreatFrame(planet);
                    }
                    System.out.println("Current threat");
                }

                if (e.getActionCommand().equals("Fix Army")) {
                    fixArmyEvent();
                    System.out.println("Fix Army pressed");
                    
                }
            }

        }
        public void fixArmyEvent() {
            if (planet.getMetal() > planet.getFixArmyCost()[0] && planet.getDeuterium() > planet.getFixArmyCost()[1]) {
                planet.resetArmyArmor();
            }
        }
        public void newGameEvent()  {
            // Maybe we don't add this functionality since we would have to refactor a lot of code in Main
            try {
                planet.newGame();
            } catch (ResourceException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            // TODO: Decide at the end what we do with this
        }
    
        // Opens a new JDialog with an JTextArea which has info of the previous 5 battles the actual planet played
        public void battleReportEvent() {
            new ReportsFrame(planet);
            // TODO: Define this method when DDBB methods are ready

            // So this method just gets the 5 last values of the Battle_log table and puts them in the JTextArea
        }
    
        // Opens a new JDialog to teak some options in the game (as difficulty, color, etc.)
        public void settingsEvent() {
            // TODO: Create a new JDialog with some JButtons as options

            // TODO: Define something that can 'change' the game difficulty, maybe teaking stats in Variables
            // TODO: Define something that changes the color of the game somehow (maybe light-dark mode)
            // TODO: Define something that changes the name of the planet
            // TODO: Define something that changes the player portrait (optional)
        }
    
        public void exitEvent() {
            // TODO: Improve the visuals of exitWindow (layout, etc.)
            // exitWindow
            JDialog exitWindow = new JDialog(SwingUtilities.getWindowAncestor(this), "Exit");
            exitWindow.setResizable(false);
            exitWindow.setLocationRelativeTo(null);
            exitWindow.setSize(500, 300);
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
                    // idk how to call this method, it's supposed to be in Main i guess --> closeGame();
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