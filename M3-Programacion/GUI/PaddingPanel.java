package GUI;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JPanel;

public class PaddingPanel extends JPanel {
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