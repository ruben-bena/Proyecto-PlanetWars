package GUI;
import classes.*;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import java.awt.Image;
import java.awt.Toolkit;

public class MainScreen extends JFrame {
   private MainPanel mainPanel;
   private ImageIcon icon;
    public MainScreen(Planet planet) {
        super();
        setTitle("Planet Wars");
        setResizable(false);

        icon = new ImageIcon("./assets/iconoPW.png");
        setIconImage(icon.getImage());
        
        setDefaultCloseOperation(EXIT_ON_CLOSE);
		setSize(1480, 920);
        mainPanel = new MainPanel(planet, this);

        add(mainPanel);
		
		setVisible(true);
    }
    public MainPanel getMainPanel() {
        return mainPanel;
    }

    

}




