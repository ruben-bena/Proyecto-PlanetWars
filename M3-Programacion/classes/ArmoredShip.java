package classes;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

public class ArmoredShip extends Ship implements MilitaryUnit {
    private static ImageIcon img = new ImageIcon("./M3-Programacion/GUI/images/armoredShip.png");
    public ArmoredShip(int armor, int baseDamage) { // Apparently this gets calculated beforehand.
        setArmor(armor);
        setInitialArmor(armor);
        setBaseDamage(baseDamage);
    }

    public ArmoredShip() {
        setArmor(Variables.ARMOR_ARMOREDSHIP);
        setInitialArmor(Variables.ARMOR_ARMOREDSHIP);
        setBaseDamage(Variables.BASE_DAMAGE_ARMOREDSHIP);
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
        return Variables.METAL_COST_ARMOREDSHIP;
    }

    public int getDeuteriumCost() {
        return Variables.DEUTERIUM_COST_ARMOREDSHIP;
    }

    public int getChanceGeneratinWaste() {
        return Variables.CHANCE_GENERATING_WASTE_ARMOREDSHIP;
    }

    public int getChanceAttackAgain() {
        return Variables.CHANCE_ATTACK_AGAIN_ARMOREDSHIP;
    }

    public void resetArmor() {
        setArmor(getInitialArmor());
    }

    public String getName() {
        return "Armored Ship";
    }

    public ImageIcon getImg() {
        return img;
    }

    public static ImageIcon getImgIcon() {
        return img;
    }
    
    public BufferedImage getBufferedImage() {
        try {
            return ImageIO.read(new File("./M3-Programacion/GUI/images/armoredShip.png"));
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }

}