package GUI;
import classes.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.JFrame;

public class MainScreen extends JFrame {
    private MainPanel mainPanel;
    public MainScreen(Planet planet) {
        super();
        setTitle("Planet Wars");
        setResizable(false);
        
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

// class MainPanel extends JPanel {
//     private LeftPanel leftPanel;
//     private RightPanel rightPanel;
//     private BottomPanel bottomPanel;
//     private MiddlePanel middlePanel;

//     MainPanel() {
//         setBackground(Color.BLUE);
//         setLayout(new BorderLayout());
        
//         leftPanel = new LeftPanel();
//         rightPanel = new RightPanel();
//         bottomPanel = new BottomPanel();
//         middlePanel = new MiddlePanel();

//         middlePanel.setBackground(Color.MAGENTA);
        

//         add(leftPanel, BorderLayout.WEST);
//         add(rightPanel, BorderLayout.EAST);
//         add(bottomPanel, BorderLayout.SOUTH);
//         add(middlePanel, BorderLayout.CENTER);
        
//         setVisible(true);
//     }

//     class LeftPanel extends JPanel {
//         private JPanel mainPanel, panel1, panel2;
    
//         LeftPanel() {
//             setSize(new Dimension(200,200));
//             setPreferredSize(new Dimension(200,200));
//             setLayout(new BorderLayout());
//             add(new PaddingPanel(), BorderLayout.NORTH);
//             add(new PaddingPanel(), BorderLayout.WEST);
//             add(new PaddingPanel(), BorderLayout.EAST);
            
    
//             mainPanel = new JPanel();
//             panel1 = new JPanel();
//             panel2 = new JPanel();
    
//             mainPanel.setBackground(Color.orange);
    
//             add(mainPanel);
//         }
//     }

//     class RightPanel extends JPanel {
//         private JPanel mainPanel, panel1, panel2;

//         RightPanel() {

//             setSize(new Dimension(200,200));
//             setPreferredSize(new Dimension(200,200));
//             setLayout(new BorderLayout());
//             add(new PaddingPanel(), BorderLayout.NORTH);
//             add(new PaddingPanel(), BorderLayout.WEST);
//             add(new PaddingPanel(), BorderLayout.EAST);
            
    
//             mainPanel = new JPanel();
//             panel1 = new JPanel();
//             panel2 = new JPanel();
    
//             mainPanel.setBackground(Color.GREEN);
    
//             add(mainPanel);
//         }
//     }

//     class BottomPanel extends JPanel {
//         private JPanel mainPanel, panel1, panel2;

//         BottomPanel() {
//             setSize(new Dimension(200,200));
//             setPreferredSize(new Dimension(200,200));
//             setLayout(new BorderLayout());
//             setBackground(Color.BLACK);
//             add(new PaddingPanel(), BorderLayout.NORTH);
//             add(new PaddingPanel(), BorderLayout.WEST);
//             add(new PaddingPanel(), BorderLayout.EAST);
//             add(new PaddingPanel(), BorderLayout.SOUTH);
    
//             mainPanel = new JPanel();
//             panel1 = new JPanel();
//             panel2 = new JPanel();
    
//             mainPanel.setBackground(Color.WHITE);
    
//             add(mainPanel);
//         }
//     }

    
//     class MiddlePanel extends JPanel {

//         private BufferedImage earthImage;

//         MiddlePanel() {
//             setLayout(new BorderLayout());
//             add(new PaddingPanel(), BorderLayout.NORTH);

    
//             // add(mainPanel);
//         }

//         protected void paintComponent(Graphics g) {
//             super.paintComponent(g);

        
//             try {
//                 earthImage = ImageIO.read(new File("./M3-Programacion/GUI/images/earth.jpg"));
//             } catch (IOException e) {
//                 // TODO Auto-generated catch block
//                 e.printStackTrace();
//             }

//             Graphics2D g2d = (Graphics2D) g;
//             g2d.drawImage(earthImage.getScaledInstance(getWidth(), getHeight(), Image.SCALE_SMOOTH), 0, 0, this);
//             g2d.fillRect(200, 200, 300, 300);
            
//             // g.drawImage(null, ALLBITS, ABORT, bottomPanel);
//         }
//     }

//     class PaddingPanel extends JPanel {
//         PaddingPanel() {
//             setBackground(Color.black);
//             setPreferredSize(new Dimension(10,10));
//         }
//     }
    
// }



