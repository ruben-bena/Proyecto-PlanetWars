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
   private MainPanel mainPanel;
    public MainScreen(Planet planet) {
        super();
        setTitle("Planet Wars");

        // Make it so the player can't buy units during battle
        
        setDefaultCloseOperation(EXIT_ON_CLOSE);
		setSize(1480, 920);
        mainPanel = new MainPanel(planet);

        add(mainPanel);
		
		setVisible(true);
    }
    public MainPanel getMainPanel() {
        return mainPanel;
    }
    

}




