package GUI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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
    private JButton acceptButton;
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

        if (battle.getWinner() == 0) {
            resultLabel = new JLabel("You won!");
        } else {
            resultLabel = new JLabel("You lost...");
        }
        resultLabel.setFont(new Font("Arial", Font.BOLD, 96));
        resultPanel = new JPanel();
        resultPanel.add(resultLabel);
        resultLabel.setForeground(Color.WHITE);
        resultPanel.setBackground(Color.BLACK);

        mainPanel.add(resultPanel, BorderLayout.NORTH);

        report_text = new JTextArea(battle.getBattleDevelopment());
        report_text.setFont(new Font("Arial", Font.BOLD, 24));
        scrollPane = new JScrollPane(report_text);

        mainPanel.add(scrollPane, BorderLayout.CENTER);

        acceptButton = new JButton("Accept");
        acceptButton.addActionListener(this);
        acceptButton.setFont(new Font("Arial", Font.BOLD, 24));
        acceptButton.setPreferredSize(new Dimension(200,50));
        acceptButton.setBackground(Color.WHITE);
        
        buttonPanel = new JPanel();
        buttonPanel.add(acceptButton);
        buttonPanel.setBackground(Color.BLACK);

        mainPanel.add(buttonPanel, BorderLayout.SOUTH);
        add(mainPanel);

        setVisible(true);

    }
    @Override
    public void actionPerformed(ActionEvent e) {
        // TODO Auto-generated method stub
        if (e.getActionCommand() == "Accept") {
            dispose();
        }
    }

}
