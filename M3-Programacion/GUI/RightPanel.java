package GUI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JPanel;

public class RightPanel extends JPanel {
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