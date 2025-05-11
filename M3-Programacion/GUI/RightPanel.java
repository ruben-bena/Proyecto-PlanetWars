package GUI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

class RightPanel extends JPanel {
        private JPanel mainPanel, imagePanel, buttonsPanel;
        private JButton newGameButton, battleReportButton, settingsButton, exitButton;

        RightPanel() {

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
            imagePanel = new JPanel();
            imagePanel.setBackground(Color.RED);
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
            }

        }

        public void newGameEvent() {
            // Maybe we don't add this functionality since we would have to refactor a lot of code in Main
            // TODO: Decide at the end what we do with this
        }
    
        // Opens a new JDialog with an JTextArea which has info of the previous 5 battles the actual planet played
        public void battleReportEvent() {
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