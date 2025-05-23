package GUI;

import java.awt.Color;
import java.awt.Font;
import java.io.File;
import java.io.IOException;

public abstract class Globals {
    private static Color panelSecColor = new Color(30,30,30);

    public static String customFont = "./M3-Programacion/GUI/fonts/font.ttf";
    public static Color bottomFontColor = Color.WHITE;
    public static Color bottomImageBackgroundColor = Color.BLACK;
    public static Color bottomBackgroundColor = Color.BLACK;
    public static Color leftFontColor = Color.WHITE;
    public static Color leftPanelColor = Color.BLACK;
    public static Color leftPanelSecColor = panelSecColor;
    public static Color leftFontSecColor = Color.WHITE;

    public static Color rightButtonsColor = Color.BLACK;
    public static Color rightButtonsFontColor = Color.WHITE;
    public static Color rightSecPanelColor = panelSecColor;
    public static Color rightSecFontColor = Color.WHITE;

    public static Color settingsPanelColor = Color.BLACK;
    public static Color settingsFontColor = Color.WHITE;
    public static Color settingsSecPanelColor = Color.BLACK;
    public static Color settingsSecFontColor = Color.WHITE;
    public static Color settingsButtonColor = Color.WHITE;
    public static Color settingsButtonFontColor = Color.BLACK;


    public static Color healthBarHealthyColor = Color.WHITE;
    public static Color healthBarInjuredColor = new Color(255, 102, 102);
    
}
