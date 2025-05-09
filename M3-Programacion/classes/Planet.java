package classes;

import java.util.ArrayList;

public class Planet {
    private int technologyDefense;
    private int technologyAttack;
    private int metal;
    private int deuterium;
    private int upgradeDefenseTechnologyDeuteriumCost;
    private int upgradeAttackTechnologyDeuteriumCost;
    private boolean isActiveThreat;
    private ArrayList<MilitaryUnit>[] army;
    private Battle currentThreat;
    private String[] battleReports;
    private int nBattles;

    // Army[0] → arrayList de Ligth Hunter
    // Army[1] → arrayList de Heavy Hunter
    // Army[2] → arrayList de Battle Ship
    // Army[3] → arrayList de Armored Ship
    // Army[4] → arrayList de Missile Launcher
    // Army[5] → arrayList de Ion Cannon
    // Army[6] → arrayList de Plasma Cannon

    public Planet(int technologyDefense, int technologyAtack, int metal, int deuterium,
            int upgradeDefenseTechnologyDeuteriumCost, int upgradeAttackTechnologyDeuteriumCost) {
        this.technologyDefense = technologyDefense;
        this.technologyAttack = technologyAtack;
        this.metal = metal;
        this.deuterium = deuterium;
        this.upgradeDefenseTechnologyDeuteriumCost = upgradeDefenseTechnologyDeuteriumCost;
        this.upgradeAttackTechnologyDeuteriumCost = upgradeAttackTechnologyDeuteriumCost;
        this.army = new ArrayList[7];
        this.isActiveThreat = false;
        this.battleReports = new String[5];
        for(int i = 0; i < army.length; i++) {
            army[i] = new ArrayList<MilitaryUnit>();
        }
        this.nBattles = 0;
    }

    public void upgradeTechnologyDefense() throws ResourceException {
        if (deuterium >= upgradeDefenseTechnologyDeuteriumCost) {
            technologyDefense++;
            deuterium -= upgradeDefenseTechnologyDeuteriumCost;
            upgradeDefenseTechnologyDeuteriumCost += upgradeDefenseTechnologyDeuteriumCost*0.1; // 10% increase in the cost
            System.out.println("Defense now lvl: " + technologyDefense);
        } else {
            // System.out.println("No tienes suficiente deuterio para mejorar la tecnologia de defensa");
            throw new ResourceException("You don't have enough deuterium to upgrade the defense technology");
        }
    }
    
    public void upgradeTechnologyAttack() throws ResourceException {
        if (deuterium >= upgradeAttackTechnologyDeuteriumCost) {
            technologyAttack++;
            deuterium -= upgradeAttackTechnologyDeuteriumCost;
            upgradeAttackTechnologyDeuteriumCost += upgradeAttackTechnologyDeuteriumCost*0.1; // 10% increase in the cost
            System.out.println("Attack now lvl: " + technologyAttack);
        } else {
            // System.out.println("No tienes suficiente deuterio para mejorar la tecnologia de ataque");
            throw new ResourceException("You don't have enough deuterium to upgrade the attack technology");
        }
    }
    public void newLightHunter(int n) throws ResourceException {
        // First it tries to use deuterium, if you don't have enough, it tries to use metal
        int armor = Variables.ARMOR_LIGTHHUNTER + (getTechnologyDefense() * Variables.PLUS_ARMOR_LIGTHHUNTER_BY_TECHNOLOGY) * (Variables.ARMOR_LIGTHHUNTER/100);
        int damage = Variables.BASE_DAMAGE_LIGTHHUNTER + (getTechnologyAttack() * Variables.PLUS_ATTACK_LIGTHHUNTER_BY_TECHNOLOGY) * (Variables.BASE_DAMAGE_LIGTHHUNTER/100);
        // System.out.println(armor);
        for(int i=0; i<n; i++) {
            if(Variables.DEUTERIUM_COST_LIGTHHUNTER < deuterium && Variables.METAL_COST_LIGTHHUNTER < metal) {
                deuterium -= Variables.DEUTERIUM_COST_LIGTHHUNTER;
                metal -= Variables.METAL_COST_LIGTHHUNTER;
                army[0].add(new LightHunter(armor, damage));
                continue;

            }
            else {
                System.out.println("Added " + i + " Light Hunters");
                throw new ResourceException("You don't have enough resources to add more Light Hunters");
            }
            
        }
    }
    public void newHeavyHunter(int n) throws ResourceException {
        // First it tries to use deuterium, if you don't have enough, it tries to use metal
        int armor = Variables.ARMOR_HEAVYHUNTER + (getTechnologyDefense() * Variables.PLUS_ARMOR_HEAVYHUNTER_BY_TECHNOLOGY) * (Variables.ARMOR_HEAVYHUNTER/100);
        int damage = Variables.BASE_DAMAGE_HEAVYHUNTER + (getTechnologyAttack() * Variables.PLUS_ATTACK_HEAVYHUNTER_BY_TECHNOLOGY) * (Variables.BASE_DAMAGE_HEAVYHUNTER/100);
        for(int i=0; i<n; i++) {
            if(Variables.DEUTERIUM_COST_HEAVYHUNTER < deuterium && Variables.METAL_COST_HEAVYHUNTER < metal) {
                deuterium -= Variables.DEUTERIUM_COST_HEAVYHUNTER;
                metal -= Variables.METAL_COST_HEAVYHUNTER;
                army[1].add(new HeavyHunter(armor, damage));
                continue;

            }
            
            else {
                System.out.println("Added " + i + " Heavy Hunters");
                throw new ResourceException("You don't have enough resources to add more Heavy Hunters");
            }
            
        }
    }
    public void newBattleShip(int n) throws ResourceException {
        // First it tries to use deuterium, if you don't have enough, it tries to use metal
        int armor = Variables.ARMOR_BATTLESHIP + (getTechnologyDefense() * Variables.PLUS_ARMOR_BATTLESHIP_BY_TECHNOLOGY) * (Variables.ARMOR_BATTLESHIP/100);
        int damage = Variables.BASE_DAMAGE_BATTLESHIP + (getTechnologyAttack() * Variables.PLUS_ATTACK_BATTLESHIP_BY_TECHNOLOGY) * (Variables.BASE_DAMAGE_BATTLESHIP/100);
        
        for(int i=0; i<n; i++) {
            if(Variables.DEUTERIUM_COST_BATTLESHIP < deuterium && Variables.METAL_COST_BATTLESHIP < metal) {
                deuterium -= Variables.DEUTERIUM_COST_BATTLESHIP;
                metal -= Variables.METAL_COST_BATTLESHIP;
                army[2].add(new BattleShip(armor, damage));
                continue;

            }
            else {
                System.out.println("Added " + i + " Battle Ships");
                throw new ResourceException("You don't have enough resources to add more Battle Ships");
            }
            
        }
    }
    public void newArmoredShip(int n) throws ResourceException {
        // First it tries to use deuterium, if you don't have enough, it tries to use metal
        int armor = Variables.ARMOR_ARMOREDSHIP + (getTechnologyDefense() * Variables.PLUS_ARMOR_ARMOREDSHIP_BY_TECHNOLOGY) * (Variables.ARMOR_ARMOREDSHIP/100);
        int damage = Variables.BASE_DAMAGE_ARMOREDSHIP + (getTechnologyAttack() * Variables.PLUS_ATTACK_ARMOREDSHIP_BY_TECHNOLOGY) * (Variables.BASE_DAMAGE_ARMOREDSHIP/100);

        for(int i=0; i<n; i++) {
            if(Variables.DEUTERIUM_COST_ARMOREDSHIP < deuterium && Variables.METAL_COST_ARMOREDSHIP < metal) {
                deuterium -= Variables.DEUTERIUM_COST_ARMOREDSHIP;
                metal -= Variables.METAL_COST_ARMOREDSHIP;
                army[3].add(new ArmoredShip(armor, damage));
                continue;

            }
            
            else {
                System.out.println("Added " + i + " Armored Ships");
                throw new ResourceException("You don't have enough resources to add more Armored Ships");
            }
            
        }
    }   
    public void newMissileLauncher(int n) throws ResourceException {
        // First it tries to use deuterium, if you don't have enough, it tries to use metal
        int armor = Variables.ARMOR_MISSILELAUNCHER + (getTechnologyDefense() * Variables.PLUS_ARMOR_MISSILELAUNCHER_BY_TECHNOLOGY) * (Variables.ARMOR_MISSILELAUNCHER/100);
        int damage = Variables.BASE_DAMAGE_MISSILELAUNCHER + (getTechnologyAttack() * Variables.PLUS_ATTACK_MISSILELAUNCHER_BY_TECHNOLOGY) * (Variables.BASE_DAMAGE_MISSILELAUNCHER/100);

        for(int i=0; i<n; i++) {
            if(Variables.DEUTERIUM_COST_MISSILELAUNCHER < deuterium && Variables.METAL_COST_MISSILELAUNCHER < metal) {
                deuterium -= Variables.DEUTERIUM_COST_MISSILELAUNCHER;
                metal -= Variables.METAL_COST_MISSILELAUNCHER;
                army[4].add(new MissileLauncher(armor, damage));
                continue;

            }
            
            else {
                System.out.println("Added " + i + " Missile Launchers");
                throw new ResourceException("You don't have enough resources to add more Missile Launchers");
            }
            
        }
    }
    public void newIonCannon(int n) throws ResourceException {
        // First it tries to use deuterium, if you don't have enough, it tries to use metal
        int armor = Variables.ARMOR_IONCANNON + (getTechnologyDefense() * Variables.PLUS_ARMOR_IONCANNON_BY_TECHNOLOGY) * (Variables.ARMOR_IONCANNON/100);
        int damage = Variables.BASE_DAMAGE_IONCANNON + (getTechnologyAttack() * Variables.PLUS_ATTACK_IONCANNON_BY_TECHNOLOGY) * (Variables.BASE_DAMAGE_IONCANNON/100);

        for(int i=0; i<n; i++) {
            if(Variables.DEUTERIUM_COST_IONCANNON < deuterium && Variables.METAL_COST_IONCANNON < metal) {
                deuterium -= Variables.DEUTERIUM_COST_IONCANNON;
                metal -= Variables.METAL_COST_IONCANNON;
                army[5].add(new IonCannon(armor, damage));
                continue;

            }

            else {
                System.out.println("Added " + i + " Ion Cannons");
                throw new ResourceException("You don't have enough resources to add more Ion Cannons");
            }
            
        }

    }
    public void newPlasmaCannon(int n) throws ResourceException {
        // First it tries to use deuterium, if you don't have enough, it tries to use metal
        int armor = Variables.ARMOR_PLASMACANNON + (getTechnologyDefense() * Variables.PLUS_ARMOR_PLASMACANNON_BY_TECHNOLOGY) * (Variables.ARMOR_PLASMACANNON/100);
        int damage = Variables.BASE_DAMAGE_PLASMACANNON + (getTechnologyAttack() * Variables.PLUS_ATTACK_PLASMACANNON_BY_TECHNOLOGY) * (Variables.BASE_DAMAGE_PLASMACANNON/100);

        for(int i=0; i<n; i++) {
            if(Variables.DEUTERIUM_COST_PLASMACANNON < deuterium && Variables.METAL_COST_PLASMACANNON < metal) {
                deuterium -= Variables.DEUTERIUM_COST_PLASMACANNON;
                metal -= Variables.METAL_COST_PLASMACANNON;
                army[6].add(new PlasmaCannon(armor, damage));
                continue;

            }

            else {
                System.out.println("Added " + i + " Plasma Cannons");
                throw new ResourceException("You don't have enough resources to add more Plasma Cannons");
            }
            
        }
    }

    public void printStats() {
        System.out.println("\n\nPlanet Stats: \n");
        System.out.println("Technology Defense: " + technologyDefense);
        System.out.println("Technology Attack: " + technologyAttack);

        System.out.println("\nRESOURCES\n");
        System.out.println("Metal: " + metal);
        System.out.println("Deuterium: " + deuterium);
        
        System.out.println("\nFLEET\n");
        System.out.println("Light Hunters: " + army[0].size());
        System.out.println("Heavy Hunters: " + army[1].size());
        System.out.println("Battle Ships: " + army[2].size());
        System.out.println("Armored Ships: " + army[3].size());

        System.out.println("\nDEFENSES\n");
        System.out.println("Missile Launchers: " + army[4].size());
        System.out.println("Ion Cannons: " + army[5].size());
        System.out.println("Plasma Cannons: " + army[6].size());
        System.out.println();
        System.out.println("Total Army: " + (army[0].size() + army[1].size() + army[2].size() + army[3].size() + army[4].size() + army[5].size() + army[6].size()));
        System.out.println();
    }

    public int getTechnologyDefense() {
        return technologyDefense;
    }

    public void setTechnologyDefense(int technologyDefense) {
        this.technologyDefense = technologyDefense;
    }

    public int getTechnologyAttack() {
        return technologyAttack;
    }

    public void setTechnologyAttack(int technologyAttack) {
        this.technologyAttack = technologyAttack;
    }

    public int getMetal() {
        return metal;
    }

    public void setMetal(int metal) {
        this.metal = metal;
    }

    public int getDeuterium() {
        return deuterium;
    }

    public void setDeuterium(int deuterium) {
        this.deuterium = deuterium;
    }

    public int getUpgradeDefenseTechnologyDeuteriumCost() {
        return upgradeDefenseTechnologyDeuteriumCost;
    }

    public void setUpgradeDefenseTechnologyDeuteriumCost(int upgradeDefenseTechnologyDeuteriumCost) {
        this.upgradeDefenseTechnologyDeuteriumCost = upgradeDefenseTechnologyDeuteriumCost;
    }

    public int getUpgradeAttackTechnologyDeuteriumCost() {
        return upgradeAttackTechnologyDeuteriumCost;
    }

    public void setUpgradeAttackTechnologyDeuteriumCost(int upgradeAttackTechnologyDeuteriumCost) {
        this.upgradeAttackTechnologyDeuteriumCost = upgradeAttackTechnologyDeuteriumCost;
    }

    public ArrayList<MilitaryUnit>[] getArmy() {
        return army;
    }

    public void setArmy(ArrayList<MilitaryUnit>[] army) {
        this.army = army;
    }

    public boolean isActiveThreat() {
        return isActiveThreat;
    }

    public void setActiveThreat(boolean isActiveThreat) {
        this.isActiveThreat = isActiveThreat;
    }

    public void setCurrentThreat(Battle battle) {
        this.currentThreat = battle;
    }

    public Battle getCurrentThreat() {
        return this.currentThreat;
    }

    public void addBattleReport(String report) {

        // Moving all one position
        for(int i = battleReports.length - 1; i > 0; i--) {
            System.out.println("Report = " + i);
            battleReports[i] = battleReports[i-1];
        }

        battleReports[0] = report;
    }

    public String[] getBattleReports() {
        return battleReports;
    }

    public String getBattleReport(int n) {
        return battleReports[n];
    }

    public void setNBattles(int n) {
        nBattles = n;
    } 

    public int getNBattles() {
        return nBattles;
    }

    public void newGame() throws ResourceException {
        setActiveThreat(false);
        setDeuterium(40000);
        setMetal(200000);
        setNBattles(0);
        setTechnologyAttack(1);
        setTechnologyDefense(1);
        setCurrentThreat(null);
        upgradeDefenseTechnologyDeuteriumCost = 3000;
        upgradeAttackTechnologyDeuteriumCost = 3000;
        battleReports = new String[5];

        army = new ArrayList[7];
        for(int i = 0; i < army.length; i++) {
            army[i] = new ArrayList<MilitaryUnit>();
        }

        generateDefaultArmy();

    }

    public void generateDefaultArmy() throws ResourceException {
        newLightHunter(4);
        newHeavyHunter(2);
        newIonCannon(3);
        newArmoredShip(1);
    }
    
}