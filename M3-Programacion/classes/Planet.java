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
        for(int i=0; i<n; i++) {
            if(Variables.DEUTERIUM_COST_LIGTHHUNTER < deuterium) {
                deuterium -= Variables.DEUTERIUM_COST_LIGTHHUNTER;
                army[0].add(new LightHunter());
                continue;

            }
            else if(Variables.METAL_COST_LIGTHHUNTER < metal) {
                metal -= Variables.METAL_COST_LIGTHHUNTER;
                army[0].add(new LightHunter());
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
        for(int i=0; i<n; i++) {
            if(Variables.DEUTERIUM_COST_HEAVYHUNTER < deuterium) {
                deuterium -= Variables.DEUTERIUM_COST_HEAVYHUNTER;
                army[1].add(new HeavyHunter());
                continue;

            }
            else if(Variables.DEUTERIUM_COST_HEAVYHUNTER < metal) {
                metal -= Variables.DEUTERIUM_COST_HEAVYHUNTER;
                army[1].add(new HeavyHunter());
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
        for(int i=0; i<n; i++) {
            if(Variables.DEUTERIUM_COST_BATTLESHIP < deuterium) {
                deuterium -= Variables.DEUTERIUM_COST_BATTLESHIP;
                army[2].add(new BattleShip());
                continue;

            }
            else if(Variables.METAL_COST_BATTLESHIP < metal) {
                metal -= Variables.METAL_COST_BATTLESHIP;
                army[2].add(new BattleShip());
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
        for(int i=0; i<n; i++) {
            if(Variables.DEUTERIUM_COST_ARMOREDSHIP < deuterium) {
                deuterium -= Variables.DEUTERIUM_COST_ARMOREDSHIP;
                army[3].add(new ArmoredShip());
                continue;

            }
            else if(Variables.METAL_COST_ARMOREDSHIP < metal) {
                metal -= Variables.METAL_COST_ARMOREDSHIP;
                army[3].add(new ArmoredShip());
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
        for(int i=0; i<n; i++) {
            if(Variables.DEUTERIUM_COST_MISSILELAUNCHER < deuterium) {
                deuterium -= Variables.DEUTERIUM_COST_MISSILELAUNCHER;
                // IDK if the variables should be calculated here or not? is this for the player or the attacker?
                army[4].add(new MissileLauncher(Variables.ARMOR_MISSILELAUNCHER, Variables.BASE_DAMAGE_MISSILELAUNCHER));
                continue;

            }
            else if(Variables.METAL_COST_MISSILELAUNCHER < metal) {
                metal -= Variables.METAL_COST_MISSILELAUNCHER;
                army[4].add(new MissileLauncher(Variables.ARMOR_MISSILELAUNCHER, Variables.BASE_DAMAGE_MISSILELAUNCHER));   
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
        for(int i=0; i<n; i++) {
            if(Variables.DEUTERIUM_COST_IONCANNON < deuterium) {
                deuterium -= Variables.DEUTERIUM_COST_IONCANNON;
                army[5].add(new IonCannon(Variables.ARMOR_IONCANNON, Variables.BASE_DAMAGE_IONCANNON));
                continue;

            }
            else if(Variables.METAL_COST_IONCANNON < metal) {
                metal -= Variables.METAL_COST_IONCANNON;
                army[5].add(new IonCannon(Variables.ARMOR_IONCANNON, Variables.BASE_DAMAGE_IONCANNON));
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
        for(int i=0; i<n; i++) {
            if(Variables.DEUTERIUM_COST_PLASMACANNON < deuterium) {
                deuterium -= Variables.DEUTERIUM_COST_PLASMACANNON;
                army[6].add(new PlasmaCannon(Variables.ARMOR_PLASMACANNON, Variables.BASE_DAMAGE_PLASMACANNON));
                continue;

            }
            else if(Variables.METAL_COST_PLASMACANNON < metal) {
                metal -= Variables.METAL_COST_PLASMACANNON;
                army[6].add(new PlasmaCannon(Variables.ARMOR_PLASMACANNON, Variables.BASE_DAMAGE_PLASMACANNON));
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
        // pos 0 is the newest report, 5 is the oldest, 5 gets replaced by 4 when a new one is added
        // for(int i = 0; i < battleReports.length; i++) {
        //     if(battleReports[i] == null) {
        //         battleReports[i] = report;
        //     }

        //     if(battleReports[i] != null) {
                
        //     }
        // }

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
    
}