package GUI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;

import classes.Planet;

public class SettingsFrame extends JFrame implements ActionListener {
    private JPanel mainPanel, difficultyPanel, difficultyButtonsPanel, cheatsTextPanel, cheatsPanel, addMetalPanel, addDeuteriumPanel;
    private JLabel difficultyLabel, cheatsTextLabel, metalImageLabel, deuteriumImageLabel;
    private JButton easyButton, mediumButton, hardButton, addMetalButton, addDeuteriumButton;
    private Planet planet;
    private JTextArea metalInputTextArea, deuteriumInputTextArea;
    private ImageIcon metalIcon, deuteriumIcon, plusIcon;
    
    public SettingsFrame(Planet planet) {
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
        setSize(new Dimension(500,300));
        setTitle("Settings");

        setLayout(new BorderLayout());
        add(new PaddingPanel(), BorderLayout.WEST);
        add(new PaddingPanel(), BorderLayout.NORTH);
        add(new PaddingPanel(), BorderLayout.EAST);
        add(new PaddingPanel(), BorderLayout.SOUTH);

        this.planet = planet;

        mainPanel = new JPanel();
        mainPanel.setBackground(Color.WHITE);
        mainPanel.setLayout(new GridLayout(4,1));

        difficultyPanel = new JPanel();
        difficultyPanel.setLayout(new BorderLayout());
        difficultyLabel = new JLabel("DIFFICULTY: " + planet.getDifficultyStr());
        difficultyLabel.setFont(new Font("Arial", Font.BOLD, 18));
        difficultyPanel.setBackground(new Color(30,30,30));
        difficultyLabel.setForeground(Color.WHITE);
        difficultyPanel.add(new PaddingPanel(new Color(30,30,30), new Dimension(160,20)), BorderLayout.WEST);
        difficultyPanel.add(difficultyLabel, BorderLayout.CENTER);


        difficultyButtonsPanel = new JPanel();
        difficultyButtonsPanel.setLayout(new GridLayout(1,3));

        easyButton = new JButton("Easy");
        easyButton.setBackground(Color.WHITE);
        easyButton.setFont(new Font("Arial", Font.BOLD, 18));
        mediumButton = new JButton("Medium");
        mediumButton.setBackground(Color.WHITE);
        mediumButton.setFont(new Font("Arial", Font.BOLD, 18));
        hardButton = new JButton("Hard");
        hardButton.setBackground(Color.WHITE);
        hardButton.setFont(new Font("Arial", Font.BOLD, 18));


        difficultyButtonsPanel.add(easyButton);
        difficultyButtonsPanel.add(mediumButton);
        difficultyButtonsPanel.add(hardButton);

        easyButton.addActionListener(this);
        mediumButton.addActionListener(this);
        hardButton.addActionListener(this);

        cheatsTextPanel = new JPanel();
        cheatsTextPanel.setLayout(new BorderLayout());
        cheatsTextLabel = new JLabel("Cheats");
        cheatsTextLabel.setFont(new Font("Arial", Font.BOLD, 18));
        cheatsTextPanel.setBackground(new Color(30,30,30));
        cheatsTextLabel.setForeground(Color.WHITE);
        cheatsTextPanel.add(new PaddingPanel(new Color(30,30,30), new Dimension(210, 10)), BorderLayout.WEST);
        cheatsTextPanel.add(cheatsTextLabel);

        cheatsPanel = new JPanel();
        cheatsPanel.setLayout(new GridLayout(1,2));

        addMetalPanel = new JPanel();
        addMetalPanel.setBackground(Color.GRAY);

        metalIcon = new ImageIcon("./M3-Programacion/GUI/images/iron_ingot.png");
        Image metalIconScaled = metalIcon.getImage().getScaledInstance(40, 40, Image.SCALE_SMOOTH);
        metalImageLabel = new JLabel(new ImageIcon(metalIconScaled));

        metalInputTextArea = new JTextArea();
        metalInputTextArea.setPreferredSize(new Dimension(100,40));
        metalInputTextArea.setFont(new Font("Arial", Font.BOLD, 18));
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
        addDeuteriumPanel.setBackground(Color.GRAY);

        deuteriumIcon = new ImageIcon("./M3-Programacion/GUI/images/redstone.png");
        Image redstoneIconScaled = deuteriumIcon.getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH);
        deuteriumImageLabel = new JLabel(new ImageIcon(redstoneIconScaled));

        deuteriumInputTextArea = new JTextArea();
        deuteriumInputTextArea.setPreferredSize(new Dimension(100,40));
        deuteriumInputTextArea.setFont(new Font("Arial", Font.BOLD, 18));
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

        mainPanel.add(difficultyPanel);
        mainPanel.add(difficultyButtonsPanel);
        mainPanel.add(cheatsTextPanel);
        mainPanel.add(cheatsPanel);




        add(mainPanel);




        setVisible(true);
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        // TODO Auto-generated method stub
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
}
