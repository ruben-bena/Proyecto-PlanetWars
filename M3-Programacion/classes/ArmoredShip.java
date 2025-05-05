package classes;

public class ArmoredShip extends Ship implements MilitaryUnit {

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


}