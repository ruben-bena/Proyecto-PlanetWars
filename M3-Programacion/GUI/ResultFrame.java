package GUI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

import javax.swing.Action;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.border.Border;

import classes.Battle;

public class ResultFrame extends JFrame implements ActionListener {
    private JPanel mainPanel, resultPanel, buttonPanel;
    private JTextArea report_text;
    private JLabel resultLabel;
    private JScrollPane scrollPane;
    private JButton acceptButton, switchViewButton;
    private Font customFontBiggest, customFontBig, customFont, customFontSmall;
    private String activeStringView;
    private Battle battle;
    public ResultFrame(Battle battle) {
        setSize(new Dimension(1000,700));
        mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        add(new PaddingPanel(), BorderLayout.WEST);
        add(new PaddingPanel(), BorderLayout.NORTH);
        add(new PaddingPanel(), BorderLayout.SOUTH);
        add(new PaddingPanel(), BorderLayout.EAST);
        activeStringView = battle.getBattleDevelopment();
        this.battle = battle;

        try {
                customFontBiggest = Font.createFont(Font.TRUETYPE_FONT, new File(Globals.customFont)).deriveFont(68f);
                customFontBig = Font.createFont(Font.TRUETYPE_FONT, new File(Globals.customFont)).deriveFont(40f);
                customFont = Font.createFont(Font.TRUETYPE_FONT, new File(Globals.customFont)).deriveFont(22f);
                customFontSmall = Font.createFont(Font.TRUETYPE_FONT, new File(Globals.customFont)).deriveFont(16f);
            } catch (FontFormatException | IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }


        if (battle.getWinner() == 0 && battle.getBattleType() == 0|| (battle.getWinner() == 1 && battle.getBattleType() == 1)) {
            resultLabel = new JLabel("You won!");
        } else {
            resultLabel = new JLabel("You lost...");
        }
        // resultLabel.setFont(new Font("Arial", Font.BOLD, 96));
        resultLabel.setFont(customFontBiggest);
        resultPanel = new JPanel();
        resultPanel.add(resultLabel);
        resultLabel.setForeground(Color.WHITE);
        resultPanel.setBackground(Color.BLACK);

        mainPanel.add(resultPanel, BorderLayout.NORTH);

        report_text = new JTextArea(activeStringView);
        report_text.setEditable(false);

        report_text.setFont(customFontSmall);
        scrollPane = new JScrollPane(report_text);

        mainPanel.add(scrollPane, BorderLayout.CENTER);

        acceptButton = new JButton("Accept");
        acceptButton.addActionListener(this);
        // acceptButton.setFont(new Font("Arial", Font.BOLD, 24));
        acceptButton.setFont(customFont);
        acceptButton.setPreferredSize(new Dimension(200,50));
        acceptButton.setBackground(Color.WHITE);

        switchViewButton = new JButton("Switch View");
        switchViewButton.addActionListener(this);
        switchViewButton.setFont(customFont);
        switchViewButton.setPreferredSize(new Dimension(200,50));
        switchViewButton.setBackground(Color.WHITE);
        
        buttonPanel = new JPanel();
        buttonPanel.add(acceptButton);
        buttonPanel.setBackground(Color.BLACK);
        buttonPanel.add(switchViewButton);

        mainPanel.add(buttonPanel, BorderLayout.SOUTH);
        add(mainPanel);

        setVisible(true);

    }

    public ResultFrame(Battle battle, int battleN) {
        setSize(new Dimension(1000,700));
        mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        add(new PaddingPanel(), BorderLayout.WEST);
        add(new PaddingPanel(), BorderLayout.NORTH);
        add(new PaddingPanel(), BorderLayout.SOUTH);
        add(new PaddingPanel(), BorderLayout.EAST);
        this.battle = battle;

        // activeStringView = battleDevelopment;
        activeStringView = battle.getBattleDevelopment();

        try {
            customFontBiggest = Font.createFont(Font.TRUETYPE_FONT, new File(Globals.customFont)).deriveFont(68f);
            customFontBig = Font.createFont(Font.TRUETYPE_FONT, new File(Globals.customFont)).deriveFont(40f);
            customFont = Font.createFont(Font.TRUETYPE_FONT, new File(Globals.customFont)).deriveFont(22f);
            customFontSmall = Font.createFont(Font.TRUETYPE_FONT, new File(Globals.customFont)).deriveFont(16f);
        } catch (FontFormatException | IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        resultLabel = new JLabel("Report");
        // resultLabel.setFont(new Font("Arial", Font.BOLD, 96));
        resultLabel.setFont(customFontBiggest);
        resultPanel = new JPanel();
        resultPanel.add(resultLabel);
        resultLabel.setForeground(Color.WHITE);
        resultPanel.setBackground(Color.BLACK);

        mainPanel.add(resultPanel, BorderLayout.NORTH);

        report_text = new JTextArea(activeStringView);
        report_text.setEditable(false);
        // report_text.setFont(new Font("Arial", Font.BOLD, 24));
        report_text.setFont(customFont);
        scrollPane = new JScrollPane(report_text);

        mainPanel.add(scrollPane, BorderLayout.CENTER);

        acceptButton = new JButton("Accept");
        acceptButton.addActionListener(this);
        // acceptButton.setFont(new Font("Arial", Font.BOLD, 24));
        acceptButton.setFont(customFont);
        acceptButton.setPreferredSize(new Dimension(200,50));
        acceptButton.setBackground(Color.WHITE);
        
        switchViewButton = new JButton("Switch View");
        switchViewButton.addActionListener(this);
        switchViewButton.setFont(customFont);
        switchViewButton.setPreferredSize(new Dimension(200,50));
        switchViewButton.setBackground(Color.WHITE);

        buttonPanel = new JPanel();
        buttonPanel.add(acceptButton);
        buttonPanel.setBackground(Color.BLACK);
        buttonPanel.add(switchViewButton);

        mainPanel.add(buttonPanel, BorderLayout.SOUTH);
        add(mainPanel);

        setVisible(true);
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        AudioPlayer.buttonSound();
        if (e.getActionCommand() == "Accept") {
            dispose();
        } if (e.getActionCommand() == "Switch View") {
            if(report_text.getText().equals(battle.getBattleDevelopment())) {
                report_text.setText(battle.getBattleReport());
            } else {
                report_text.setText(battle.getBattleDevelopment());
            }
        }
    }

}
