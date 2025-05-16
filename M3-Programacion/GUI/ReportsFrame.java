package GUI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.Border;

import classes.Planet;

public class ReportsFrame extends JFrame {
    private JPanel mainPanel;
    private Font customFontBiggest, customFontBig, customFont, customFontSmall;
    public ReportsFrame(Planet planet) {
        setTitle("Battle Reports");
        setSize(new Dimension(300,200));
        setLocationRelativeTo(null);
        setResizable(false);
        setBackground(Color.BLACK);

        try {
                customFontBiggest = Font.createFont(Font.TRUETYPE_FONT, new File(Globals.customFont)).deriveFont(68f);
                customFontBig = Font.createFont(Font.TRUETYPE_FONT, new File(Globals.customFont)).deriveFont(40f);
                customFont = Font.createFont(Font.TRUETYPE_FONT, new File(Globals.customFont)).deriveFont(32f);
                customFontSmall = Font.createFont(Font.TRUETYPE_FONT, new File(Globals.customFont)).deriveFont(16f);
            } catch (FontFormatException | IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

        mainPanel = new JPanel();
        int nBattles = 0;

        for (int i = 0; i < planet.getBattleReports().length; i++) {
            if (planet.getBattleReports()[i] != null) {
                nBattles++;
            }
        }
        if(nBattles == 0) {
            JPanel textPanel = new JPanel();
            textPanel.setLayout(new BorderLayout());
            textPanel.setBackground(Color.BLACK);
            JLabel label = new JLabel("There are no reports to show");
            // label.setFont(new Font("Arial", Font.BOLD, 18));
            label.setFont(customFontSmall);
            label.setForeground(new Color(255,255,255,150));
            textPanel.add(label, BorderLayout.CENTER);
            textPanel.add(new PaddingPanel(Color.BLACK, new Dimension(15,20)), BorderLayout.WEST);
            add(textPanel);
        }
        else {
            mainPanel.setLayout(new GridLayout(nBattles, 1));

            for (int i = 0; i < nBattles; i++) {
                JButton button = new JButton("Battle N-"+(planet.getNBattles() - i));
                button.setBackground(Color.WHITE);
                int n = i;
                button.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        new ResultFrame(planet.getBattleReport(n), n);
                    }
                    

                });

                mainPanel.add(button);
            }

            add(mainPanel);
        }
        setVisible(true);

    }
}
