package classes;

public class BattleShip extends Ship implements MilitaryUnit {

    public BattleShip(int armor, int baseDamage) { // Apparently this gets calculated beforehand.
        setArmor(armor);
        setInitialArmor(armor);
        setBaseDamage(baseDamage);
    }

    public BattleShip() {
        setArmor(Variables.ARMOR_BATTLESHIP);
        setInitialArmor(Variables.ARMOR_BATTLESHIP);
        setBaseDamage(Variables.BASE_DAMAGE_BATTLESHIP);
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
        return Variables.METAL_COST_BATTLESHIP;
    }

    public int getDeuteriumCost() {
        return Variables.DEUTERIUM_COST_BATTLESHIP;
    }

    public int getChanceGeneratinWaste() {
        return Variables.CHANCE_GENERATING_WASTE_BATTLESHIP;
    }

    public int getChanceAttackAgain() {
        return Variables.CHANCE_ATTACK_AGAIN_BATTLESHIP;
    }

    public void resetArmor() {
        setArmor(getInitialArmor());
    }


}