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
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

import javax.imageio.ImageIO;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

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
        bottomPanel = new BottomPanel();
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

    class LeftPanel extends JPanel {
        private JPanel mainPanel, rotatingEarthPanel, infoPanel, metalTextPanel, metalResourcePanel, deuteriumTextPanel, deuteriumResourcePanel;
        private ImageIcon rotatingEarthIcon, metalIcon, deuteriumIcon;
        private JLabel rotatingEarthLabel, metalTextLabel, deuteriumTextLabel, metalImageLabel, deuteriumImageLabel, metalAmountLabel, deuteriumAmountLabel;
        private String metalStr, deuteriumStr;
    
        LeftPanel(Planet planet) {
            setSize(new Dimension(200,200));
            setPreferredSize(new Dimension(200,200));
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

            metalTextLabel = new JLabel("Metal");
            metalTextLabel.setFont(new Font("Arial", 1, 24));

            metalTextPanel = new JPanel();
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

            deuteriumTextLabel = new JLabel("Deuterium");
            deuteriumTextLabel.setFont(new Font("Arial", 1, 24));

            deuteriumTextPanel = new JPanel();
            deuteriumTextPanel.add(deuteriumTextLabel);

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
            infoPanel.add(deuteriumTextPanel);
            infoPanel.add(deuteriumResourcePanel);


            mainPanel.add(rotatingEarthPanel);
            mainPanel.add(infoPanel);

            
    
            add(mainPanel);
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
        private JPanel mainPanel, panel1, panel2;

        BottomPanel() {
            setSize(new Dimension(200,200));
            setPreferredSize(new Dimension(200,200));
            setLayout(new BorderLayout());
            setBackground(Color.BLACK);
            add(new PaddingPanel(), BorderLayout.NORTH);
            add(new PaddingPanel(), BorderLayout.WEST);
            add(new PaddingPanel(), BorderLayout.EAST);
            add(new PaddingPanel(), BorderLayout.SOUTH);
    
            mainPanel = new JPanel();
            panel1 = new JPanel();
            panel2 = new JPanel();
    
            mainPanel.setBackground(Color.WHITE);
    
            add(mainPanel);
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
    }
}



