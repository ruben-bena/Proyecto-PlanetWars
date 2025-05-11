package GUI;
import javax.swing.JPanel;

import classes.*;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.LayoutManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

import javax.imageio.ImageIO;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.border.Border;


public class MainPanel extends JPanel {
    private LeftPanel leftPanel;
    private RightPanel rightPanel;
    private BottomPanel bottomPanel;
    private MiddlePanel middlePanel;

    MainPanel(Planet planet) {
        setBackground(Color.BLUE);
        setLayout(new BorderLayout());

        leftPanel = new LeftPanel(planet);
        rightPanel = new RightPanel();
        bottomPanel = new BottomPanel(planet);
        middlePanel = new MiddlePanel(planet);
        
        middlePanel.setBackground(Color.MAGENTA);

        add(leftPanel, BorderLayout.WEST);
        add(rightPanel, BorderLayout.EAST);
        add(bottomPanel, BorderLayout.SOUTH);
        add(middlePanel, BorderLayout.CENTER);

        Timer timer = new Timer();
        TimerTask updateTask = new TimerTask() {
            public void run() {
                updateAll(planet);
            }
        };
        timer.schedule(updateTask, 0, 100);
        
        setVisible(true);
    }
    
    public LeftPanel getLeftPanel() {
        return leftPanel;
    }


    public RightPanel getRightPanel() {
        return rightPanel;
    }


    public BottomPanel getBottomPanel() {
        return bottomPanel;
    }


    public MiddlePanel getMiddlePanel() {
        return middlePanel;
    }


    public void updateAll(Planet planet) {
        leftPanel.getMetalJLabel().setText((planet.getMetal() + " +" + 2000 * planet.getMetalMineLvl()));
        leftPanel.getDeuteriumJLabel().setText(String.valueOf(planet.getDeuterium()) + " +" + 800 * planet.getDeuteriumMineLvl());
        leftPanel.getTechAttackLvlAmountTextArea().setText("" + planet.getTechnologyAttack());
        leftPanel.getTechAttackLvlCost().setText("" + planet.getUpgradeAttackTechnologyDeuteriumCost());
        leftPanel.getTechDefenseLvlAmountTextArea().setText("" + planet.getTechnologyDefense());
        leftPanel.getTechDefenseLvlCost().setText("" + planet.getUpgradeDefenseTechnologyDeuteriumCost());
        leftPanel.getMetalMineLvlTextArea().setText("" + planet.getMetalMineLvl());
        leftPanel.getDeuteriumMineLvlTextArea().setText("" + planet.getDeuteriumMineLvl());
        leftPanel.getMetalMineCostLabel().setText("" + planet.getUpgradeMetalMineLvlMetalCost());
        leftPanel.getDeuteriumMineCostLabel().setText("" + planet.getUpgradeDeuteriumMineLvlDeuteriumCost());

        bottomPanel.getLightHunterNameLabel().setText("Light Hunter - " + planet.getArmy()[0].size());
        bottomPanel.getHeavyHunterNameLabel().setText("Heavy Hunter - " + planet.getArmy()[1].size());
        bottomPanel.getBattleShipNameLabel().setText("Battle Ship - " + planet.getArmy()[2].size());
        bottomPanel.getArmoredShipNameLabel().setText("Armored Ship - " + planet.getArmy()[3].size());
        bottomPanel.getMissileLauncherNameLabel().setText("Missile Launcher - " + planet.getArmy()[4].size());
        bottomPanel.getIonCannonNameLabel().setText("Ion Cannon - " + planet.getArmy()[5].size());
        bottomPanel.getPlasmaCannonNameLabel().setText("Plasma Cannon - " + planet.getArmy()[6].size());
    }
}