package GUI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class MainScreen extends JFrame {
    
    public MainScreen() {
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
        MainPanel mainPanel = new MainPanel();

        String currentDir = System.getProperty("user.dir");
        System.out.println("Current working directory: " + currentDir);

        add(mainPanel);
		
		setVisible(true);
    }
}

class MainPanel extends JPanel {
    private LeftPanel leftPanel;
    private GameSettingsPanel rightPanel;
    private BottomPanel bottomPanel;
    private MiddlePanel middlePanel;

    MainPanel() {
        setBackground(Color.BLUE);
        setLayout(new BorderLayout());
        
        leftPanel = new LeftPanel();
        rightPanel = new GameSettingsPanel();
        bottomPanel = new BottomPanel();
        middlePanel = new MiddlePanel();

        middlePanel.setBackground(Color.MAGENTA);
        

        add(leftPanel, BorderLayout.WEST);
        add(rightPanel, BorderLayout.EAST);
        add(bottomPanel, BorderLayout.SOUTH);
        add(middlePanel, BorderLayout.CENTER);
        
        setVisible(true);
    }

    class LeftPanel extends JPanel {
        private JPanel mainPanel, panel1, panel2;
    
        LeftPanel() {
            setSize(new Dimension(200,200));
            setPreferredSize(new Dimension(200,200));
            setLayout(new BorderLayout());
            add(new PaddingPanel(), BorderLayout.NORTH);
            add(new PaddingPanel(), BorderLayout.WEST);
            add(new PaddingPanel(), BorderLayout.EAST);
            
    
            mainPanel = new JPanel();
            panel1 = new JPanel();
            panel2 = new JPanel();
    
            mainPanel.setBackground(Color.orange);
    
            add(mainPanel);
        }
    }

    class GameSettingsPanel extends JPanel {
        private JPanel mainPanel, imagePanel, buttonsPanel;
        private JButton newGameButton, battleReportButton, settingsButton, exitButton;

        GameSettingsPanel() {

            // GameSettingsPanel
            setSize(new Dimension(200,200));
            setPreferredSize(new Dimension(200,200));
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
            // TODO: Define events for the buttons
            // TODO: Implement those events as methods of the class GameSettingsPanel
            // TODO: Define each button method
            // TODO: Add button that pauses the game
            buttonsPanel = new JPanel();
            buttonsPanel.setLayout(new GridLayout(4,1));
            buttonsPanel.setPreferredSize(new Dimension(Integer.MAX_VALUE, 300));
            buttonsPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 300));
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
    }

    public void newGameEvent() {
        
    }

    public void battleReportEvent() {

    }

    public void settingsEvent() {

    }

    public void exitEvent() {
        // Window window = SwingUtilities.getWindowAncestor(this);
        // if (window != null) {
        //     window.dispose();
        // }
        
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

    
            // add(mainPanel);
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
            
            // g.drawImage(null, ALLBITS, ABORT, bottomPanel);
        }
    }

    class PaddingPanel extends JPanel {
        PaddingPanel() {
            setBackground(Color.black);
            setPreferredSize(new Dimension(10,10));
        }
    }
    
}



