package classes;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;

public class Main{
    public static void main(String[] args) throws ResourceException {
        
        Planet planet = new Planet(0, 0, 200000, 40000, 3000, 3000);
        planet.newLightHunter(4);
        planet.newHeavyHunter(2);
        planet.newIonCannon(3);
        planet.newArmoredShip(5);

        ArrayList<MilitaryUnit>[] enemyArmy = createEnemyArmy();
        // ArrayList<MilitaryUnit>[] enemyArmy;

        ArrayList[][] armies = new ArrayList[2][7];
        
        armies[0] = planet.getArmy();
        armies[1] = enemyArmy;

        // TODO I should add a "elligible for combat" mechanic, for instance if planet doesn't have any MilitaryUnits, to not be threatened.

        // Battle battle = new Battle(planet.getArmy(), enemyArmy, armies);

        // System.out.println("Enemy Attacket group = " + battle.getEnemyGroupAttacker());
        // System.out.println("Planet Attacker group = " + battle.getPlanetGroupAttacker());
        // System.out.println("Planet army number = " + battle.initialFleetNumber(planetArmy));
        // System.out.println("Enemy army number = " + battle.initialFleetNumber(enemyArmy));
        // System.out.println("Planet army cost = " + battle.fleetResourceCost(planetArmy));
        // System.out.println("Enemy army cost = " + battle.fleetResourceCost(enemyArmy));


        Time time = new Time();
        
        Timer timer = new Timer();
        TimerTask tick = new TimerTask() {
            
            public void run() {
                boolean isBattleAnnounced = false;
                time.setMiliseconds(time.getMiliseconds() + time.getDeltaTime());

                if(time.getMiliseconds() > Time.secInMs) { // Every second
                    time.setSeconds(time.getSeconds() + 1);
                    time.setTotalSeconds(time.getTotalSeconds() + 1);
                    // System.out.println(time.getTotalSeconds() + " seconds.");
                    time.setMiliseconds(0);
                }

                
                if(0 == time.getSeconds()%10 && time.getSeconds() != 0 && time.getMiliseconds() == 0) { // Every 10 seconds
                    // System.out.println("10 seconds have passed");
                    planet.setMetal(planet.getMetal() + 1000);
                    // System.out.println("1000 metal added.");
                }

                if (time.getSeconds() % 60 == 0 && time.getSeconds() != 0 && time.getMiliseconds() == 0) { // Every minute
                    time.setMinutes(time.getMinutes()+1);
                    time.setSeconds(0);

                    if(isBattleAnnounced) { // This doesn't work for some reason
                        System.out.println("COMBAT HAS BEGUN");
                        // battle.combat();
                        isBattleAnnounced = false;
                    }

                    System.out.println("A minute has passed");
                }

            }
        };

        // The problem with timers and why idk how to implement it it's because
        // variables declared outside of the timer on main are not accessible in the timer
        

        // One option is to have multiple timers for everything
        // Another option is to figure out how to do it with one timer and checking time conditions in that one.

        //////////////////////////////////////////////////////////////////////
        /// MAIN TIMER ///////////////////////////////////////////////////////
        timer.schedule(tick, 0, time.getDeltaTime());
        //////////////////////////////////////////////////////////////////////
        //////////////////////////////////////////////////////////////////////
        TimerTask countdownBattleTimerTask = new TimerTask() {
            public void run() {
                System.out.println("Battle has started!");
                
            }
        };

        TimerTask threatTimer = new TimerTask() {
            public void run() {
                if (!planet.isActiveThreat()) {
                    // battle.announceCombat();
                    planet.setCurrentThreat(new Battle(planet));
                    planet.setActiveThreat(true);

                    // timer.schedule(new TimerTaskBattleClass(battle, planet), Time.countdownBattleTime);
                }
                
            }
        };
        
        timer.schedule(threatTimer, Time.timeBetweenBattles, Time.timeBetweenBattles);

        // I could do a method inside the Time class that executes an order after a certain time using the current time and doing the sum with the desired wait time

        String menu = """
                1) View Planet Stats
                2) Build
                3) Upgrade Technology
                4) View Battle Reports
                0) Exit 
                """;
        if (planet.isActiveThreat()) {
            menu = """
                1) View Planet Stats
                2) Build
                3) Upgrade Technology
                4) View Battle Reports
                0) Exit 
                """;
        }
        
        String buildMenu = """
                1) Build Light Hunter
                2) Build Heavy Hunter
                3) Build Battle Ship
                4) Build Armored Ship
                5) Go back
                """;
        Scanner scanner = new Scanner(System.in);

        for(int i = 0; i < 100; i++){
            System.out.println(menu);
            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    planet.printStats();
                    break;

                case 2:
                    System.out.println(buildMenu);
                    int amount = 0;
                    switch (scanner.nextInt()) {
                        case 1:
                            System.out.println("Amount of Units");
                            System.out.println("Amount: ");
                            amount = scanner.nextInt();
                            planet.newLightHunter(amount);
                            break;
                        case 2:
                            System.out.println("Amount of Units");
                            System.out.println("Amount: ");
                            amount = scanner.nextInt();
                            planet.newHeavyHunter(amount);
                            break;
                        case 3:
                            System.out.println("Amount of Units");
                            System.out.println("Amount: ");
                            amount = scanner.nextInt();
                            planet.newBattleShip(amount);
                            break;
                        case 4:
                            System.out.println("Amount of Units");
                            System.out.println("Amount: ");
                            amount = scanner.nextInt();
                            planet.newArmoredShip(amount);
                            break;
                        case 5:
                            
                            break;

                        default:
                            break;
                    }
                    break;
                
                case 3:
                    System.out.println("Upgrade Technology");
                    System.out.println("Actual Defense Technology: " + planet.getTechnologyDefense());
                    System.out.println("Actual Attack Technology: " + planet.getTechnologyAttack());
                    System.out.println("1) Upgrade Defense Technology. Cost: " + planet.getUpgradeDefenseTechnologyDeuteriumCost() + " deuterium");
                    System.out.println("2) Upgrade Attack Technology. Cost: " + planet.getUpgradeAttackTechnologyDeuteriumCost() + " deuterium");
                    switch (scanner.nextInt()) {
                        case 1:
                            planet.upgradeTechnologyDefense();
                            break;
                        case 2:
                            planet.upgradeTechnologyAttack();
                            break;
                        case 3:
                            break;
                        default:
                            break;
                    }
                    break;
                
                case 4:
                    System.out.println("View Battle Reports");
                    break;
                case 5:
                    if(planet.isActiveThreat()) {
                        planet.getCurrentThreat().printEnemyStats();
                    }
                    break;
                default:
                    break;
            }

    }
    scanner.close();


    }


    public static ArrayList<MilitaryUnit>[] createEnemyArmy() {
        ArrayList<MilitaryUnit>[] army = new ArrayList[7];
        // Enemy doesn't have defenses so the army array won't go past index 3.
        // Create the army

        //Init the army
        for (int i = 0; i < army.length; i++) {
            army[i] = new ArrayList<MilitaryUnit>();
        }
        ////////

        int metalInitialResources = 300000;
        int deuteriumInitialResources = 4000;
        int option = -1;

        // light hunter [0]
        // heavy hunter [1]
        // battle ship [2]
        // Armored ship [3]

        while(metalInitialResources > Variables.METAL_COST_LIGTHHUNTER || deuteriumInitialResources > Variables.DEUTERIUM_COST_LIGTHHUNTER) {
            int[] array = new int[4];

                array[0] = 35;
                array[1] = 25;
                array[2] = 20;
                array[3] = 20;

                int randomNumber = (int) (Math.random() * 100);

                for(int i = 0; i < array.length; i++) {
                    int sum = array[0];
                    int j = i;
                    while (j > 0 && i != 0) {
                        sum += array[j];
                        j--;
                    }

                    // For some reason it only gives values to option 3
                    if(sum > randomNumber) {
                        option = i;
                        break;
                    }
                }

            if (metalInitialResources > Variables.METAL_COST_LIGTHHUNTER) {
                
                switch (option) {
                    case 0: // Light hunters
                        army[0].add(new LightHunter());
                        metalInitialResources -= Variables.METAL_COST_LIGTHHUNTER;
                        break;

                    case 1: // Heavy hunters
                        army[1].add(new HeavyHunter());
                        metalInitialResources -= Variables.METAL_COST_HEAVYHUNTER;
                        break;

                    case 2: // Battle Ship
                        army[2].add(new BattleShip());
                        metalInitialResources -= Variables.METAL_COST_BATTLESHIP;
                        break;
                    
                    case 3: // Armored Ship
                        army[3].add(new ArmoredShip());
                        metalInitialResources -= Variables.METAL_COST_ARMOREDSHIP;
                        break;
                
                    default:
                        System.out.println("Error: Option = " + option);
                        break;
                }
            } else if (deuteriumInitialResources > Variables.DEUTERIUM_COST_LIGTHHUNTER) {
                switch (option) {
                    case 0: // Light hunters
                        army[0].add(new LightHunter());
                        deuteriumInitialResources -= Variables.DEUTERIUM_COST_LIGTHHUNTER;
                        break;

                    case 1: // Heavy hunters
                        army[1].add(new HeavyHunter());
                        deuteriumInitialResources -= Variables.DEUTERIUM_COST_HEAVYHUNTER;
                        break;

                    case 2: // Battle Ship
                        army[2].add(new BattleShip());
                        deuteriumInitialResources -= Variables.DEUTERIUM_COST_BATTLESHIP;
                        break;
                    
                    case 3: // Armored Ship
                        army[3].add(new ArmoredShip());
                        deuteriumInitialResources -= Variables.DEUTERIUM_COST_ARMOREDSHIP;
                        break;
                
                    default:
                    System.out.println("Error: Option = " + option);
                        break;
                }
            }
        }
        
        return army;
    }

    public void announceCombat(ArrayList<MilitaryUnit>[] enemyArmy) {
        enemyArmy = createEnemyArmy();
        System.out.println("NEW THREAT IS COMING");

    }

    
}

class Time {
    private int miliseconds;
    private int seconds;
    private int minutes;
    private int totalSeconds;
    private int deltaTime;

    static int secInMs = 1000;
    static int countdownBattleTime = secInMs * 10;
    static int timeBetweenBattles = secInMs * 20;


    public Time() {
        this.miliseconds = 0;
        this.seconds = 0;
        this.minutes = 0;
        this.totalSeconds = 0;
        this.deltaTime = secInMs/50;
    }

    public int getSeconds() {
        return seconds;
    }

    public void setSeconds(int seconds) {
        this.seconds = seconds;
    }

    public int getMinutes() {
        return minutes;
    }

    public void setMinutes(int minutes) {
        this.minutes = minutes;
    }

    public int getTotalSeconds() {
        return totalSeconds;
    }

    public void setTotalSeconds(int totalSeconds) {
        this.totalSeconds = totalSeconds;
    }

    public int getDeltaTime() {
        return deltaTime;
    }

    public void setDeltaTime(int deltaTime) {
        this.deltaTime = deltaTime;
    }

    public int getMiliseconds() {
        return miliseconds;
    }

    public void setMiliseconds(int miliseconds) {
        this.miliseconds = miliseconds;
    }

    
}

class TimerTaskBattleClass extends TimerTask {
    private Battle battle;
    private Planet planet;

    TimerTaskBattleClass(Battle battle, Planet planet) {
        this.battle = battle;
        this.planet = planet;
    }
    @Override
    public void run() {
        battle.combat();
        planet.setActiveThreat(false);
            
    }

}