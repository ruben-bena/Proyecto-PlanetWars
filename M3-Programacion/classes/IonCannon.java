package classes;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

public class IonCannon extends Defense {
    private static ImageIcon img = new ImageIcon("./M3-Programacion/GUI/images/ionCannon2.png");
    public IonCannon(int armor, int baseDamage) {
        setArmor(armor);
        setInitialArmor(armor);
        setBaseDamage(baseDamage);
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
        // TODO Auto-generated method stub
        return getArmor();
    }

    public int getMetalCost() {
        // TODO Auto-generated method stub
        return Variables.METAL_COST_IONCANNON;
    }

    public int getDeuteriumCost() {
        // TODO Auto-generated method stub
        return Variables.DEUTERIUM_COST_IONCANNON;
    }

    public int getChanceGeneratinWaste() {
        // TODO Auto-generated method stub
        return Variables.CHANCE_GENERATING_WASTE_IONCANNON;
    }

    public int getChanceAttackAgain() {
        // TODO Auto-generated method stub
        return Variables.CHANCE_ATTACK_AGAIN_IONCANNON;
    }

    public void resetArmor() {
        // TODO Auto-generated method stub
        setArmor(Variables.ARMOR_ARMOREDSHIP);

    }

    public String getName() {
        return "Ion Cannon";
    }

    public ImageIcon getImg() {
        return img;
    }

    public static ImageIcon getImgIcon() {
        return img;
    }

    public BufferedImage getBufferedImage(){
        try {
            return ImageIO.read(new File("./M3-Programacion/GUI/images/ionCannon2.png"));
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }

    public boolean hasBeenDamaged() {
        // TODO Auto-generated method stub
        if(getArmor() < getInitialArmor()) {
            return true;
        }
        else { 
            return false;
        }
    }
    

}