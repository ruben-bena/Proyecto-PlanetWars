package GUI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;

import classes.Planet;

public class SettingsFrame extends JFrame implements ActionListener {
    private JPanel mainPanel, difficultyPanel, difficultyButtonsPanel, cheatsTextPanel, cheatsPanel, addMetalPanel, addDeuteriumPanel, changeNamePanel, muteBGMPanel;
    private JLabel difficultyLabel, cheatsTextLabel, metalImageLabel, deuteriumImageLabel;
    private JButton easyButton, mediumButton, hardButton, addMetalButton, addDeuteriumButton, changeNameButton, muteBGMButton;
    private Planet planet;
    private JTextArea metalInputTextArea, deuteriumInputTextArea, changeNameInputTextArea;
    private ImageIcon metalIcon, deuteriumIcon, plusIcon;
    private MiddlePanel mp;
    private RightPanel rp;
    private Font customFontBiggest, customFontBig, customFont, customFontSmall;
    
    public SettingsFrame(Planet planet, MiddlePanel mp, RightPanel rp) {
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
        setSize(new Dimension(500,500));
        setTitle("Settings");
        this.mp = mp;
        this.rp = rp;
        setLayout(new BorderLayout());
        add(new PaddingPanel(), BorderLayout.WEST);
        add(new PaddingPanel(), BorderLayout.NORTH);
        add(new PaddingPanel(), BorderLayout.EAST);
        add(new PaddingPanel(), BorderLayout.SOUTH);

        try {
            customFontBiggest = Font.createFont(Font.TRUETYPE_FONT, new File(Globals.customFont)).deriveFont(68f);
            customFontBig = Font.createFont(Font.TRUETYPE_FONT, new File(Globals.customFont)).deriveFont(40f);
            customFont = Font.createFont(Font.TRUETYPE_FONT, new File(Globals.customFont)).deriveFont(22f);
            customFontSmall = Font.createFont(Font.TRUETYPE_FONT, new File(Globals.customFont)).deriveFont(16f);
        } catch (FontFormatException | IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }


        this.planet = planet;

        mainPanel = new JPanel();
        mainPanel.setBackground(Color.WHITE);
        mainPanel.setLayout(new GridLayout(6,1));

        difficultyPanel = new JPanel();
        difficultyPanel.setLayout(new BorderLayout());
        difficultyLabel = new JLabel("DIFFICULTY: " + planet.getDifficultyStr());
        // difficultyLabel.setFont(new Font("Arial", Font.BOLD, 18));
        difficultyLabel.setFont(customFontSmall);
        difficultyPanel.setBackground(Globals.settingsPanelColor);
        difficultyLabel.setForeground(Color.WHITE);
        difficultyPanel.add(new PaddingPanel(Globals.settingsPanelColor, new Dimension(160,20)), BorderLayout.WEST);
        difficultyPanel.add(difficultyLabel, BorderLayout.CENTER);


        difficultyButtonsPanel = new JPanel();
        difficultyButtonsPanel.setLayout(new GridLayout(1,3));

        easyButton = new JButton("Easy");
        easyButton.setBackground(Globals.settingsButtonColor);
        // easyButton.setFont(new Font("Arial", Font.BOLD, 18));
        easyButton.setFont(customFontSmall);
        mediumButton = new JButton("Medium");
        mediumButton.setBackground(Globals.settingsButtonColor);
        // mediumButton.setFont(new Font("Arial", Font.BOLD, 18));
        mediumButton.setFont(customFontSmall);
        hardButton = new JButton("Hard");
        hardButton.setBackground(Globals.settingsButtonColor);
        // hardButton.setFont(new Font("Arial", Font.BOLD, 18));
        hardButton.setFont(customFontSmall);


        difficultyButtonsPanel.add(easyButton);
        difficultyButtonsPanel.add(mediumButton);
        difficultyButtonsPanel.add(hardButton);

        easyButton.addActionListener(this);
        mediumButton.addActionListener(this);
        hardButton.addActionListener(this);

        changeNamePanel = new JPanel();
        changeNamePanel.add(new PaddingPanel(Globals.settingsPanelColor, new Dimension(getWidth(), 20)));
        changeNamePanel.setBackground(Globals.settingsPanelColor);
        changeNameButton = new JButton("Change Name");
        changeNameButton.setBackground(Globals.settingsButtonColor);
        changeNameButton.setForeground(Globals.settingsButtonFontColor);
        changeNameButton.setFont(customFontSmall);
        changeNameButton.setPreferredSize(new Dimension(200, 40));
        changeNameButton.addActionListener(this);
        changeNamePanel.add(changeNameButton);

        changeNameInputTextArea = new JTextArea();
        changeNameInputTextArea.setPreferredSize(new Dimension(200, 40));
        changeNameInputTextArea.setFont(customFont);
        changeNamePanel.add(changeNameInputTextArea);



        cheatsTextPanel = new JPanel();
        cheatsTextPanel.setLayout(new BorderLayout());
        cheatsTextLabel = new JLabel("Cheats");
        // cheatsTextLabel.setFont(new Font("Arial", Font.BOLD, 18));
        cheatsTextLabel.setFont(customFontSmall);
        cheatsTextPanel.setBackground(Globals.settingsPanelColor);
        cheatsTextLabel.setForeground(Globals.settingsFontColor);
        cheatsTextPanel.add(new PaddingPanel(Globals.settingsPanelColor, new Dimension(210, 10)), BorderLayout.WEST);
        cheatsTextPanel.add(cheatsTextLabel);

        cheatsPanel = new JPanel();
        cheatsPanel.setLayout(new GridLayout(1,2));

        addMetalPanel = new JPanel();
        addMetalPanel.setBackground(Globals.settingsSecPanelColor);

        metalIcon = new ImageIcon("./M3-Programacion/GUI/images/iron_ingot.png");
        Image metalIconScaled = metalIcon.getImage().getScaledInstance(40, 40, Image.SCALE_SMOOTH);
        metalImageLabel = new JLabel(new ImageIcon(metalIconScaled));

        metalInputTextArea = new JTextArea();
        metalInputTextArea.setPreferredSize(new Dimension(100,40));
        // metalInputTextArea.setFont(new Font("Arial", Font.BOLD, 18));
        metalInputTextArea.setFont(customFont);
        metalInputTextArea.setLineWrap(true);

        plusIcon = new ImageIcon("./M3-Programacion/GUI/images/plus_icon.png");
        Image plusIconScaled = plusIcon.getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH);

        addMetalButton = new JButton("addMetal");
        addMetalButton.setFont(new Font("Arial", 1, 0));
        addMetalButton.setIcon(new ImageIcon(plusIconScaled));
        addMetalButton.setPreferredSize(new Dimension(plusIconScaled.getWidth(this), plusIconScaled.getHeight(this)));
        addMetalButton.addActionListener(this);

        addMetalPanel.add(metalImageLabel);
        addMetalPanel.add(metalInputTextArea);
        addMetalPanel.add(addMetalButton);

        

        addDeuteriumPanel = new JPanel();
        addDeuteriumPanel.setBackground(Globals.settingsSecPanelColor);

        deuteriumIcon = new ImageIcon("./M3-Programacion/GUI/images/redstone.png");
        Image redstoneIconScaled = deuteriumIcon.getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH);
        deuteriumImageLabel = new JLabel(new ImageIcon(redstoneIconScaled));

        deuteriumInputTextArea = new JTextArea();
        deuteriumInputTextArea.setPreferredSize(new Dimension(100,40));
        // deuteriumInputTextArea.setFont(new Font("Arial", Font.BOLD, 18));
        deuteriumInputTextArea.setFont(customFont);
        deuteriumInputTextArea.setLineWrap(true);


        addDeuteriumButton = new JButton("addDeuterium");
        addDeuteriumButton.setFont(new Font("Arial", 1, 0));
        addDeuteriumButton.setIcon(new ImageIcon(plusIconScaled));
        addDeuteriumButton.setPreferredSize(new Dimension(plusIconScaled.getWidth(this), plusIconScaled.getHeight(this)));
        addDeuteriumButton.addActionListener(this);

        addDeuteriumPanel.add(deuteriumImageLabel);
        addDeuteriumPanel.add(deuteriumInputTextArea);
        addDeuteriumPanel.add(addDeuteriumButton);

        cheatsPanel.add(addMetalPanel);
        cheatsPanel.add(addDeuteriumPanel);


        muteBGMPanel = new JPanel();

        muteBGMButton = new JButton("Music ON/OFF");
        muteBGMButton.setFont(customFontSmall);
        muteBGMPanel.setBackground(Globals.settingsPanelColor);
        muteBGMButton.setForeground(Globals.settingsButtonFontColor);
        muteBGMButton.setBackground(Globals.settingsButtonColor);
        muteBGMPanel.add(muteBGMButton);
        muteBGMButton.addActionListener(this);

        mainPanel.add(difficultyPanel);
        mainPanel.add(difficultyButtonsPanel);
        mainPanel.add(changeNamePanel);
        mainPanel.add(cheatsTextPanel);
        mainPanel.add(cheatsPanel);
        mainPanel.add(muteBGMPanel);




        add(mainPanel);




        setVisible(true);
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        // TODO Auto-generated method stub
        AudioPlayer.buttonSound();
        System.out.println(e.getActionCommand());
        if(e.getActionCommand() == "Easy") {
            planet.setDifficulty(1);;
            updateLabels();
        }
        if(e.getActionCommand() == "Medium") {
            planet.setDifficulty(2);
            updateLabels();
        }
        if(e.getActionCommand() == "Hard") {
            planet.setDifficulty(3);
            updateLabels();
        }

        if(e.getActionCommand() == "addMetal") {
            if (isOnlyNumbers(metalInputTextArea.getText())) {
                planet.setMetal(planet.getMetal() + Integer.valueOf(metalInputTextArea.getText()));
            }
            metalInputTextArea.setText("");
        }

        if(e.getActionCommand() == "addDeuterium") {
            if (isOnlyNumbers(deuteriumInputTextArea.getText())) {
                planet.setDeuterium(planet.getDeuterium() + Integer.valueOf(deuteriumInputTextArea.getText()));
            }
            deuteriumInputTextArea.setText("");
        }

        if(e.getActionCommand() == "Change Name") {
            
            if (isOnlyText(changeNameInputTextArea.getText())) {
                planet.setPlanetName(changeNameInputTextArea.getText());
                changeNameInputTextArea.setText("");
                mp.repaint();
            }
        }

        if(e.getActionCommand() == "Music ON/OFF") {
            if (!rp.getBgmPlayer().isMuted()) {
                rp.getBgmPlayer().changeVolume(-50);
                rp.getBgmPlayer().setMuted(true);
            } else {
                rp.getBgmPlayer().changeVolume(0);
                rp.getBgmPlayer().setMuted(false);
            }
            
        }
    }

    public void updateLabels() {
        difficultyLabel.setText("DIFFICULTY: " + planet.getDifficultyStr());
    }

    public boolean isOnlyNumbers(String string) {
        if(string.length() == 0) {
            return false;
        }
        
        for (char c : string.toCharArray()) {
            if(!Character.isDigit(c)) {
                return false;
            }
        }
        return true;
    }

    public boolean isOnlyText(String string) {
        if(string.length() == 0) {
            return false;
        }
        
        for (char c : string.toCharArray()) {
            if(Character.isDigit(c)) {
                return false;
            }
        }
        return true;
    }
}
