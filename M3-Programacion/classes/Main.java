package classes;
import GUI.*;
import ddbb.Database;
import ddbb.PlanetStatsTable;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;


public class Main{
    public static void main(String[] args) throws ResourceException {

        // Establish connection to local vm ddbb
        String url = "jdbc:oracle:thin:@//localhost:1521/freepdb1"; // Local VM Oracle ddbb
        String username = "planetWars";
        String pass = "planetWars";
        GlobalContext.database = new Database(url, username, pass);

        Planet planet = new Planet(1, 1, 200000, 40000, 3000, 3000);
        planet.newLightHunter(4);
        planet.newHeavyHunter(2);
        planet.newIonCannon(3);
        planet.newArmoredShip(1);

        // Create PlanetStatsTable object with planet info, and inserting it in ddbb
        GlobalContext.planetStatsTable = new PlanetStatsTable(GlobalContext.database, planet);
        GlobalContext.planetStatsTable.insertRow();

        // Things to add: The ability to "fix" damaged troops.

        MainScreen ms = new MainScreen(planet);
        ms.getMainPanel().getMiddlePanel().requestFocusInWindow();
       
        new ThreatTimer(planet, ms);
        new ResourceTimer(planet);


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
    public static void newGame(Planet planet) throws ResourceException {
        planet.newGame();
        
    }

    public static ArrayList<MilitaryUnit>[] createEnemyArmy(Planet planet) {
        ArrayList<MilitaryUnit>[] army = new ArrayList[7];
        // Enemy doesn't have defenses so the army array won't go past index 3.
        // Create the army

        //Init the army
        for (int i = 0; i < army.length; i++) {
            army[i] = new ArrayList<MilitaryUnit>();
        }
        ////////

        int metalInitialResources = 300000 + (60000 * planet.getNBattles() * planet.getDifficulty()); // This way it gets progressively harder
        int deuteriumInitialResources = 24000 + (7000 * planet.getNBattles() * planet.getDifficulty());
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

    // closeGame --> Saves all data in DDBB and then closes the program. It's called from the "Exit" button and closing the MainScreen
    public void closeGame() {
        // TODO: Store data in DDBB before saving (need the DDBB methods finished beforehand)
        System.exit(0);
    }
}



