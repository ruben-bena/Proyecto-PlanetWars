package GUI;

import java.awt.BorderLayout;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

import GUI.MainPanel.PaddingPanel;

public class MiddlePanel extends JPanel {

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