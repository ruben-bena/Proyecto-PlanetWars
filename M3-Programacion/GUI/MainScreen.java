package GUI;
import classes.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

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
        
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE); // Closing the program is managed elsewhere
		setSize(1480, 920);
        mainPanel = new MainPanel(planet, this);

        add(mainPanel);

        // Call the closeGame() method before finishing the program (saving data, cutting ddbb Connection)
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                System.out.println("Entro en el listener de windowClosing");
                Main.closeGame();
            }
        });
		
		setVisible(true);
    }
    public MainPanel getMainPanel() {
        return mainPanel;
    }

    
}




