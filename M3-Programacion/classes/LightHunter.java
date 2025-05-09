package classes;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

public class LightHunter extends Ship implements MilitaryUnit {
    private static ImageIcon img = new ImageIcon("./M3-Programacion/GUI/images/lightHunter.png");

    public LightHunter(int armor, int baseDamage) { // Apparently this gets calculated beforehand.
        setArmor(armor);
        setInitialArmor(armor);
        setBaseDamage(baseDamage);
    }

    public LightHunter() {
        setArmor(Variables.ARMOR_LIGTHHUNTER);
        setInitialArmor(Variables.ARMOR_LIGTHHUNTER);
        setBaseDamage(Variables.BASE_DAMAGE_LIGTHHUNTER);
    }
    
    public int attack() {
        return getBaseDamage();
    }

    public void takeDamage(int receivedDamage) {
        setArmor(getArmor() - receivedDamage);
        if (getArmor() < 0) {
            setArmor(0);
        }
    }

    public int getActualArmor() {
        return getArmor();
    }

    public int getMetalCost() {
        return Variables.METAL_COST_LIGTHHUNTER;
    }

    public int getDeuteriumCost() {
        return Variables.DEUTERIUM_COST_LIGTHHUNTER;
    }

    public int getChanceGeneratinWaste() {
        return Variables.CHANCE_GENERATING_WASTE_LIGTHHUNTER;
    }

    public int getChanceAttackAgain() {
        return Variables.CHANCE_ATTACK_AGAIN_LIGTHHUNTER;
    }

    public void resetArmor() {
        setArmor(getInitialArmor());
    }

    @Override
    public String toString() {
        return "LightHunter []";
    }

    public String getName() {
        return "Light Hunter";
    }

    public ImageIcon getImg() {
        return img;
    }

    public static ImageIcon getImgIcon() {
        return img;
    }

    public BufferedImage getBufferedImage() {
        try {
            return ImageIO.read(new File("./M3-Programacion/GUI/images/lightHunter.png"));
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }

    
}