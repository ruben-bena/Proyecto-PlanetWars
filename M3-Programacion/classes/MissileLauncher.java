package classes;

import javax.swing.ImageIcon;

public class MissileLauncher extends Defense {
    private static ImageIcon img = new ImageIcon("./M3-Programacion/GUI/images/missile_launcher.png");

    public MissileLauncher(int armor, int baseDamage) {
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
        return getArmor();
    }

    public int getMetalCost() {
        return Variables.METAL_COST_MISSILELAUNCHER;
    }

    public int getDeuteriumCost() {
        return Variables.DEUTERIUM_COST_MISSILELAUNCHER;
    }

    public int getChanceGeneratinWaste() {
        return Variables.CHANCE_GENERATING_WASTE_MISSILELAUNCHER;
    }

    public int getChanceAttackAgain() {
        return Variables.CHANCE_ATTACK_AGAIN_MISSILELAUNCHER;
    }

    public void resetArmor() {
        setArmor(getInitialArmor());
    }

    public String getName() {
        return "Missile Launcher";
    }

    public static ImageIcon getImg() {
        return img;
    }
    
}