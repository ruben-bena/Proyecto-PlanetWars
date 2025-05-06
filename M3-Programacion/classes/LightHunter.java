package classes;

public class LightHunter extends Ship implements MilitaryUnit {

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

    

}