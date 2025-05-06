package classes;

public class PlasmaCannon extends Defense {

    public PlasmaCannon(int armor, int baseDamage) {
        setArmor(armor);
        setInitialArmor(armor);
        setBaseDamage(baseDamage);
    }
    
    public int attack() {
        throw new UnsupportedOperationException("Unimplemented method 'attack'");
    }

    public void takeDamage(int receivedDamage) {
        throw new UnsupportedOperationException("Unimplemented method 'tekeDamage'");
    }

    public int getActualArmor() {
        return getArmor();
    }

    public int getMetalCost() {
        return Variables.METAL_COST_PLASMACANNON;
    }

    public int getDeuteriumCost() {
        return Variables.DEUTERIUM_COST_PLASMACANNON;
    }

    public int getChanceGeneratinWaste() {
        return Variables.CHANCE_GENERATING_WASTE_PLASMACANNON;
    }

    public int getChanceAttackAgain() {
        return Variables.CHANCE_ATTACK_AGAIN_PLASMACANNON;
    }

    public void resetArmor() {
        setArmor(getInitialArmor());
    }

}