package classes;

public class IonCannon extends Defense {

    public IonCannon(int armor, int baseDamage) {
        setArmor(armor);
        setInitialArmor(armor);
        setBaseDamage(baseDamage);
    }
    public int attack() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'attack'");
    }

    public void takeDamage(int receivedDamage) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'tekeDamage'");
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

}