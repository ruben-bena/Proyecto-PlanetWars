package classes;

public class HeavyHunter extends Ship implements MilitaryUnit {

    public HeavyHunter(int armor, int baseDamage) { // Apparently this gets calculated beforehand.
        setArmor(armor);
        setInitialArmor(armor);
        setBaseDamage(baseDamage);
    }

    public HeavyHunter() {
        setArmor(Variables.ARMOR_HEAVYHUNTER);
        setInitialArmor(Variables.ARMOR_HEAVYHUNTER);
        setBaseDamage(Variables.BASE_DAMAGE_HEAVYHUNTER);
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
        return Variables.METAL_COST_HEAVYHUNTER;
    }

    public int getDeuteriumCost() {
        return Variables.DEUTERIUM_COST_HEAVYHUNTER;
    }

    public int getChanceGeneratinWaste() {
        return Variables.CHANCE_GENERATING_WASTE_HEAVYHUNTER;
    }

    public int getChanceAttackAgain() {
        return Variables.CHANCE_ATTACK_AGAIN_HEAVYHUNTER;
    }

    public void resetArmor() {
        setArmor(getInitialArmor());
    }

    public String getName() {
        return "Heavy Hunter";
    }


}