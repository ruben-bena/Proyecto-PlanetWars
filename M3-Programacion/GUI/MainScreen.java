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
        mainPanel = new MainPanel(planet);

        String currentDir = System.getProperty("user.dir");
        System.out.println("Current working directory: " + currentDir);

        add(mainPanel);
		
		setVisible(true);
    }
    public MainPanel getMainPanel() {
        return mainPanel;
    }
    

}




