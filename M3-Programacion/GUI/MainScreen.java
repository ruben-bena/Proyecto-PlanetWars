package GUI;
import classes.*;
import javax.swing.JFrame;

public class MainScreen extends JFrame {
   private MainPanel mainPanel;
    public MainScreen(Planet planet) {
        super();
        setTitle("Planet Wars");
        setResizable(false);
        
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




