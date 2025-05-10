package classes;
import GUI.*;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;


public class Main{
    public static void main(String[] args) throws ResourceException {
        Planet planet = new Planet(1, 1, 200000, 40000, 3000, 3000);
        planet.newLightHunter(4);
        planet.newHeavyHunter(2);
        planet.newIonCannon(3);
        planet.newArmoredShip(1);

        // TODO I should add a "elligible for combat" mechanic, for instance if planet doesn't have any MilitaryUnits, to not be threatened.
        // Add battle report, add skip battle mechanic
        // Things to add: Resource mines that you can level up, the ability to "fix" damaged troops.
        // Thing to fix: 1. Skip using space doesn't work if you have bought or done something, because it thinks you're "clicking" with spacebar

        MainScreen ms = new MainScreen(planet);
        ms.getMainPanel().getMiddlePanel().requestFocusInWindow();

        Timer timer = new Timer();
        

        TimerTask threatTimer = new TimerTask() {
            public void run() {
                if (!planet.isActiveThreat() && planet.isElligibleForCombat()) {
                    planet.setCurrentThreat(new Battle(planet, ms.getMainPanel()));
                    planet.setActiveThreat(true);
                    

                }
                
            }
        };

        TimerTask resourceTask = new TimerTask() {
            public void run() {
                planet.setMetal(planet.getMetal() + 2000);
                planet.setDeuterium(planet.getDeuterium() + 200);
                
            }
        };
        
        timer.schedule(threatTimer, Time.timeBetweenBattles, Time.timeBetweenBattles);
        timer.schedule(resourceTask, Time.timeBetweenResources, Time.timeBetweenResources);

        String menu = """
                1) View Planet Stats
                2) Build
                3) Upgrade Technology
                4) View Battle Reports
                0) Exit 
                """;
        
        String buildMenu = """
                1) Build Light Hunter
                2) Build Heavy Hunter
                3) Build Battle Ship
                4) Build Armored Ship
                5) Go back
                """;

        Scanner scanner = new Scanner(System.in);

        for(int i = 0; i < 100; i++){
            if (planet.isActiveThreat()) {
                menu = """
                    1) View Planet Stats
                    2) Build
                    3) Upgrade Technology
                    4) View Battle Reports
                    0) Exit 
                    """;
            } else {
                menu = """
                1) View Planet Stats
                2) Build
                3) Upgrade Technology
                4) View Battle Reports
                0) Exit 
                """;
            }
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
                    for(int j = 0; j < planet.getBattleReports().length; j++) {
                        if(planet.getBattleReports()[j] != null) {
                            System.out.println(j+ ") Battle N-" + j);
                        }
                    }
                    System.out.println(planet.getBattleReport(scanner.nextInt()));
                    break;
                case 5:
                    if(planet.isActiveThreat()) {
                        planet.getCurrentThreat().printEnemyStats();
                    }
                    break;
                case 6:
                    // newGame(planet, threatTimer, resourceTask, timer);
                    break;
                default:
                    break;
            }

    }
    scanner.close();


    }
    // public static void newGame(Planet planet, TimerTask threatTask, TimerTask resourceTask, Timer timer) {
    //     planet = new Planet(1, 1, 300000, 40000, 3000, 3000);
    //     threatTask.cancel();
    //     resourceTask.cancel();
    //     timer.schedule(resourceTask, Time.timeBetweenResources, Time.timeBetweenResources);
    //     timer.schedule(threatTask, Time.timeBetweenBattles, Time.timeBetweenBattles);
    // }

    public static ArrayList<MilitaryUnit>[] createEnemyArmy(Planet planet) {
        ArrayList<MilitaryUnit>[] army = new ArrayList[7];
        // Enemy doesn't have defenses so the army array won't go past index 3.
        // Create the army

        //Init the army
        for (int i = 0; i < army.length; i++) {
            army[i] = new ArrayList<MilitaryUnit>();
        }
        ////////

        int metalInitialResources = 300000 + 20000 * planet.getNBattles(); // This way it gets progressively harder
        int deuteriumInitialResources = 4000 + 300 * planet.getNBattles();
        int option = -1;

        // light hunter [0]
        // heavy hunter [1]
        // battle ship [2]
        // Armored ship [3]

        while(metalInitialResources > Variables.METAL_COST_LIGTHHUNTER && deuteriumInitialResources > Variables.DEUTERIUM_COST_LIGTHHUNTER) {
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

                    if(sum > randomNumber) {
                        option = i;
                        break;
                    }
                }

            if (metalInitialResources > Variables.METAL_COST_LIGTHHUNTER && deuteriumInitialResources > Variables.DEUTERIUM_COST_LIGTHHUNTER) {
                
                switch (option) {
                    case 0: // Light hunters
                        army[0].add(new LightHunter());
                        metalInitialResources -= Variables.METAL_COST_LIGTHHUNTER;
                        deuteriumInitialResources -= Variables.DEUTERIUM_COST_LIGTHHUNTER;
                        break;

                    case 1: // Heavy hunters
                        army[1].add(new HeavyHunter());
                        metalInitialResources -= Variables.METAL_COST_HEAVYHUNTER;
                        deuteriumInitialResources -= Variables.DEUTERIUM_COST_HEAVYHUNTER;
                        break;

                    case 2: // Battle Ship
                        army[2].add(new BattleShip());
                        metalInitialResources -= Variables.METAL_COST_BATTLESHIP;
                        deuteriumInitialResources -= Variables.DEUTERIUM_COST_BATTLESHIP;
                        break;
                    
                    case 3: // Armored Ship
                        army[3].add(new ArmoredShip());
                        metalInitialResources -= Variables.METAL_COST_ARMOREDSHIP;
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
        // enemyArmy = createEnemyArmy();
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
    static int countdownBattleTime = secInMs * 5;
    static int timeBetweenBattles = secInMs * 5;
    static int timeBetweenResources = secInMs * 10;


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

