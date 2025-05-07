package classes;

public class MissileLauncher extends Defense {

    public MissileLauncher(int armor, int baseDamage) {
        setArmor(armor);
        setInitialArmor(armor);
        setBaseDamage(baseDamage);
    }
    
    public int attack() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'attack'");
    }

    public void takeDamage(int receivedDamage) {
        throw new UnsupportedOperationException("Unimplemented method 'tekeDamage'");
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

}