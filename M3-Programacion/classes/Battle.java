package classes;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class Battle {
    private ArrayList<MilitaryUnit>[] planetArmy; // → para almacenar la flota de nuestro planeta. 
    private ArrayList<MilitaryUnit>[] enemyArmy; // → para almacenar la flota enemiga
    private ArrayList[][] armies; // que es un array de ArrayList de dos filas y siete columnas, donde almacenaremos nuestro ejército en la primera fila, y el ejército enemigo en la segunda fila;
    private String battleDevelopment; // Donde guardamos todo el desarrollo de la batalla paso a paso
    private int[][] initialCostFleet; // coste de metal deuterio de los ejercitos iniciales
    // initialCostFleet = [[metal][deuterio],[metal][deuterio]]; // donde initialCostFleet[0] costes unidades del planeta , initialCostFleet[1] costes unidades enemigas. Lo necesitamos para saber las pérdidas en materiales de cada flota.

    private int initialNumberUnitsPlanet, initialNumberUnitsEnemy;
    // La batalla se
    // acabará cuando uno de los dos ejércitos se quede con el 20% o menos de sus
    // unidades iniciales, por tanto es necesario saber la cantidad de unidades iniciales de
    // cada ejército.
    private int[] wasteMetalDeuterium;
    // residuos generados en la batalla [metal, deuterio].

    private int[] enemyDrops;
    private int[] planetDrops; 
    // necesarios para generar reporte de batalla, y
    // para calcular las pérdidas materiales de cada ejército.
    private int[][] resourcesLosses;
    // array de dos filas y tres columnas, resourcesLosses[0] =
    // {perdidas metal planeta, perdidas deuterio planeta, perdidas metal planeta + 5*
    // perdidas deuterio planeta}, resourcesLosses[1] lo mismo pero para el ejercito
    // enemigo.
    // Lo de multiplicar por 5 las pérdidas de deuterio, es debido al mayor valor de este
    // material. Para decidir el ganador, será que que tenga el numero menor en la tercera
    // columna. ResourcesLosses[0][2] y ResourcesLosses[1][2], que representan las
    // pérdidas ponderadas.
    private int[][] initialArmies;
    // Array de dos filas y 7 columnas. Servirá para cuantificar
    // cada tipo de unidad de los ejércitos iniciales.
    // InitialArmies[0] serán las unidades iniciales de nuestro planeta y InitialArmies[1] las
    // unidades iniciales enemigas.
    // InitialArmies[0][0] cazadores ligeros en nuestro planeta antes de iniciar batalla.
    // InitialArmies[0][1] cazadores pesados en nuestro planeta antes de iniciar batalla..
    // Este array nos ayudará a calcular los costes de las flotas iniciales y por tanto, las
    // pérdidas.
    private int[] actualNumberUnitsPlanet;
    private int[] actualNumberUnitsEnemy; 
    // arrays que
    // cuantifican las unidades actuales de cada grupo, tanto para el planeta, como para el
    // enemigo. El orden seria:
    // actualNumberUnitsPlanet[0] --> cazadores ligeros
    // actualNumberUnitsPlanet[1] --> cazadores pesados
    // actualNumberUnitsPlanet[2] --> Naves de battalla
    // actualNumberUnitsPlanet[3] --> Acorazados
    // actualNumberUnitsPlanet[4] --> Lanzamisiles
    // actualNumberUnitsPlanet[5] --> Cañones de iones
    // actualNumberUnitsPlanet[6] --> Cañones de Plasma

    public Battle(Planet planet) {
        this.planetArmy = planet.getArmy();
        this.enemyArmy = Main.createEnemyArmy(planet);
        this.initialCostFleet = new int[2][2];
        this.resourcesLosses = new int[2][3];
        announceCombat();
        TimerTask task = new TimerTask() {
            public void run() {
                combat(planet);
                planet.addBattleReport(battleDevelopment);
                planet.setActiveThreat(false);
                planet.setNBattles(planet.getNBattles() + 1);
                
            }
        };
        Timer timer = new Timer();

        timer.schedule(task, Time.countdownBattleTime);
        
    }


    public void initInitialArmies() {
        initialArmies = new int[2][7];
        // 0 --> planet
        // 1 --> enemy
        // 0-7 --> Armies

        for (int i = 0; i < initialArmies.length; i++) {
                initialArmies[i] = getArrayValuesFromArrayList(armies[i]);
            }
        }
    

    
    public void updateResourceLoses() {
        resourcesLosses[0][0] = initialCostFleet[0][0] + planetDrops[0];
        resourcesLosses[0][1] = initialCostFleet[0][1] + planetDrops[1];
        resourcesLosses[1][0] = initialCostFleet[1][0] + enemyDrops[0];
        resourcesLosses[1][1] = initialCostFleet[1][1] + enemyDrops[1];
        resourcesLosses[0][2] = resourcesLosses[0][0] + 5 * resourcesLosses[0][1];
        resourcesLosses[1][2] = resourcesLosses[1][0] + 5 * resourcesLosses[1][1];
        System.out.println("Initial cost fleet planet metal = " + initialCostFleet[0][0]);
        System.out.println("Initial cost fleet planet deut = " + initialCostFleet[0][1]);
        System.out.println("Initial cost fleet enemy metal = " + initialCostFleet[1][0]);
        System.out.println("Initial cost fleet enemy deut = " + initialCostFleet[1][1]);
        System.out.println("Losses by planet = " + resourcesLosses[0][2]);
        System.out.println("Losses by enemy = " + resourcesLosses[1][2]);
    }

    public int fleetResourceCost(ArrayList<MilitaryUnit>[] army) {
        int cost = 0;
        for (int i = 0; i < army.length; i++) {
            for (int j = 0; j < army[i].size(); j++) {
                cost += army[i].get(j).getMetalCost();
            }
        }
        return cost;
    }

    public int initialFleetNumber(ArrayList<MilitaryUnit>[] army) {
        int total = 0;

        for (int i = 0; i < army.length; i++) {
            total += army[i].size();
        }

        return total;
    }

    public int remainderPercentageFleet(ArrayList<MilitaryUnit>[] army) {
        int total = 0;
        for (int i = 0; i < army.length; i++) {
            total += army[i].size();
        }
        // System.out.println("Total amount alive = " + total);

        if (army.equals(armies[0])) {
            // System.out.println("initialNumberUnitsPlanet = " + initialNumberUnitsPlanet);
            // System.out.println("percentage planet army remaining = " + (total * 100) / initialNumberUnitsPlanet);
            return (total * 100) / initialNumberUnitsPlanet;

        } else {
            // System.out.println("initialNumberUnitsEnemy = " + initialNumberUnitsEnemy);
            // System.out.println("percentage enemy army remaining = " + (total * 100) / initialNumberUnitsEnemy);
            return (total * 100) / initialNumberUnitsEnemy;
        }
    }

    public int remainderPercentageFleetPlanet() {
        int total = 0;
        for (int i = 0; i < planetArmy.length; i++) {
            total += planetArmy[i].size();
        }
        // System.out.println("Total amount planet alive = " + total);
        // System.out.println("initial number units planet = " + initialNumberUnitsPlanet);
        // System.out.println("Percentage planet fleet alive = " + (total * 100) / initialNumberUnitsPlanet);

        return (total * 100) / initialNumberUnitsPlanet;

    }

    public int remainderPercentageFleetEnemy() {
        int total = 0;
        for (int i = 0; i < enemyArmy.length; i++) {
            total += enemyArmy[i].size();
        }
        // System.out.println("Total amount enemy alive = " + total);
        // System.out.println("Initial number units enemy = " + initialNumberUnitsEnemy);
        // System.out.println("Percentage enemy fleet alive = " + (total * 100) / initialNumberUnitsEnemy);

        return (total * 100) / initialNumberUnitsEnemy;

    }


    public int getGroupDefender(ArrayList<MilitaryUnit>[] army) {
        // I have to get the defender group of the army, 0-3 if enemy, 0-6 if planet. 
        // Planet is always the one being attacked.
        // How can I differentiate if it's planet or enemy?
        // Maybe it's not necessary to differentiate because it's chosen depending on the number of each army
        // and the attacker doesn't have any from army 4 to 6
        float randMultiplier;
        // This algorithm chooses one group randomly between the 7 groups depending on the number
        int[] array = new int[7];
        int totalSum = 0;
        for(int i = 0; i < army.length; i++) {
            array[i] = army[i].size() * 20; // These *20 values are necessary to avoid unwanted 0 values when calculating the randomNumber
            totalSum += army[i].size() * 20; // These *20 values are necessary to avoid unwanted 0 values when calculating the randomNumber
        }
        // System.out.println("Total sum group pick function = " + totalSum);
        do {
            randMultiplier = (float) Math.random();
        } while(randMultiplier < 0.04);
        
        // System.out.println("Random multiplier = " + randMultiplier);
        // System.out.println("Random number mult result int = " + (int) (randMultiplier * totalSum));
        // System.out.println("Random number mult result float = " + (float) (randMultiplier * totalSum));
        int randomNumber = (int) (randMultiplier * totalSum);

        // System.out.println("Random number group pick function = " + randomNumber);
        for(int i = 0; i < array.length; i++) {
            int j = i;
            int sum = 0;
            while (j >= 0) {
                sum += array[j];
                j--;
            }
            if (sum >= randomNumber) {
                // System.out.println("Result group picked = " + i);
                return i;
            }
        }

        // If it gets to this, it's an error.
        return -1;
    }

    public int getPlanetGroupAttacker() {
        return getGroupDefender(planetArmy);
    }

    public int getEnemyGroupAttacker() {
        return getGroupDefender(enemyArmy);
    }

    public void resetArmyArmor() {
        for (int i = 0; i < planetArmy.length; i++) {
            for (int j = 0; j < planetArmy[i].size(); j++) {
                planetArmy[i].get(j).resetArmor();
            }
        }
    }

    public int[] getArrayValuesFromArrayList(ArrayList[] array) {
        int[] result = new int[array.length];

        for(int i = 0; i < array.length; i++) {
            result[i] = array[i].size();
        }

        return result;
    }
    public void announceCombat() {
        // enemyArmy = Main.createEnemyArmy();
        this.armies = new ArrayList[2][7];
        
        for (int i = 0; i < armies.length; i++) {
            for (int j = 0; j < armies[i].length; j++) {
                this.armies[i][j] = new ArrayList<MilitaryUnit>() ;
                this.armies[i][j] = armies[i][j];
            }
        }
        initialNumberUnitsPlanet = initialFleetNumber(planetArmy);
        initialNumberUnitsEnemy = initialFleetNumber(enemyArmy);
        battleDevelopment = "";
        wasteMetalDeuterium = new int[2];
        enemyDrops = new int[2];
        planetDrops = new int[2];
        initInitialArmies();
        actualNumberUnitsPlanet = getArrayValuesFromArrayList(planetArmy);
        actualNumberUnitsEnemy = getArrayValuesFromArrayList(enemyArmy);

        initialCostFleet[0][0] = getMetalCostOfArmy(planetArmy);
        initialCostFleet[0][1] = getDeuteriumCostOfArmy(planetArmy);
        initialCostFleet[1][0] = getMetalCostOfArmy(enemyArmy);
        initialCostFleet[1][1] = getDeuteriumCostOfArmy(enemyArmy);


        System.out.println("NEW THREAT IS COMING");
    }
    public void combat(Planet planet) {
        if(planet.isActiveThreat()){
        String winner = "";
        // Selecting randomly who starts the combat
        int attackingArmy = (int) (Math.random() * 2);
        int defendingArmy = 1;

        if(attackingArmy == 1) {
            defendingArmy = 0;
        }

        int attacking_group;
        int defending_group;
        String attackerStr;
        String defenderStr;
        boolean isAttackingAgain; // Variable to check if it gets to attack again in the same turn
        
        // System.out.println("Initial fleet number Planet = " + initialFleetNumber(planetArmy));
        // System.out.println(remainderPercentageFleetPlanet() > 20 && remainderPercentageFleetEnemy() > 20);
        while(remainderPercentageFleetPlanet() > 20 && remainderPercentageFleetEnemy() > 20) { ///////////////////////////////////////////////////////
            // System.out.println("Percentage Planet army = " + remainderPercentageFleet(armies[0]));
            // System.out.println("Percentage Enemy army = " + remainderPercentageFleet(armies[1]));
            isAttackingAgain = true;
            // Selecting attacking group for the army starting the fight

            

            if (attackingArmy == 0) {
                attacking_group = getPlanetGroupAttacker();
                attackerStr = "Planet";
                defenderStr = "Enemy";
            } else {
                attacking_group = getEnemyGroupAttacker();
                attackerStr = "Enemy";
                defenderStr = "Planet";
            }

            while(isAttackingAgain && (remainderPercentageFleetPlanet() > 20 && remainderPercentageFleetEnemy() > 20)) {
                int indexAttackingUnit;
                int indexDefendingUnit;
                // System.out.println(attackerStr + " attacks");
                // Selecting random attacking unit from selected group
                // System.out.println("Attacking group = " + attacking_group);
                MilitaryUnit attackingUnit;

                // DISPLAYING BATTLE INFO FOR DEBUGGING
                // for(int i = 0; i < planetArmy.length; i++) {
                //     System.out.println("Planet Units " + i + ": " + planetArmy[i].size());
                //     System.out.println("Enemy Units " + i + ": " + enemyArmy[i].size());
                // }
                //
                if (attackingArmy == 0) {
                    // System.out.println("Attacking group size = " + planetArmy[attacking_group].size());
                    indexAttackingUnit = (int) (Math.random() * planetArmy[attacking_group].size());
                    attackingUnit = (MilitaryUnit) (planetArmy[attacking_group].get((indexAttackingUnit)));
                } else {
                    // System.out.println("Attacking group size = " + enemyArmy[attacking_group].size());
                    indexAttackingUnit = (int) (Math.random() * enemyArmy[attacking_group].size());
                    attackingUnit = (MilitaryUnit) (enemyArmy[attacking_group].get((indexAttackingUnit)));
                }
                
                // System.out.println("Attacking unit index = " + indexAttackingUnit);
                
                MilitaryUnit defendingUnit;
                // Selecting defending group
                if (defendingArmy == 0) {
                    defending_group = getGroupDefender(planetArmy);
                    indexDefendingUnit = (int) (Math.random() * planetArmy[defending_group].size());
                    // System.out.println("Defending unit index = " + indexDefendingUnit);
                    defendingUnit = (MilitaryUnit) (planetArmy[defending_group].get(indexDefendingUnit));
                    // System.out.println("defending group size = " + planetArmy[defending_group].size());
                } else {
                    defending_group = getGroupDefender(enemyArmy);
                    indexDefendingUnit = (int) (Math.random() * enemyArmy[defending_group].size());
                    // System.out.println("Defending unit index = " + indexDefendingUnit);
                    defendingUnit = (MilitaryUnit) (enemyArmy[defending_group].get(indexDefendingUnit));
                    // System.out.println("defending group size = " + enemyArmy[defending_group].size());
                }
                
                // System.out.println("Defending group = " + defending_group);
                
                

                // Making them fight
                // Applying damage
                battleDevelopment += attackerStr + " attacks " + defenderStr + "\n" + defendingUnit.getName() + " receives " + attackingUnit.attack() + " dmg.\n\n";
                defendingUnit.takeDamage(attackingUnit.attack());
                // Checking if it's destroyed
                if(defendingUnit.getActualArmor() <= 0) {
                    battleDevelopment += defendingUnit.getName() + " has " + defendingUnit.getActualArmor() + ", it gets destroyed.\n";

                    // Before elmininating it, cheking if waste gets generated
                    boolean isGeneratingWaste = false;
                    if (Math.random()*100 <= defendingUnit.getChanceGeneratinWaste()) {
                        isGeneratingWaste = true;
                    }
                    if(isGeneratingWaste) {
                        wasteMetalDeuterium[0] += defendingUnit.getMetalCost() * Variables.PERCENTATGE_WASTE / 100;
                        wasteMetalDeuterium[1] += defendingUnit.getDeuteriumCost() * Variables.PERCENTATGE_WASTE / 100;
                        battleDevelopment += defendingUnit.getMetalCost() * Variables.PERCENTATGE_WASTE / 100 + " metal waste has been generated\n";
                        battleDevelopment += defendingUnit.getDeuteriumCost() * Variables.PERCENTATGE_WASTE / 100 + " deuterium has been generated\n";
                    }

                    // Adding it to planetDrops and enemyDrops
                    if (defendingArmy == 0) { //I'm assuming the 0 is for metal and 1 is for deuterium
                        planetDrops[0] += defendingUnit.getMetalCost();
                        planetDrops[1] += defendingUnit.getDeuteriumCost();
                    } else {
                        enemyDrops[0] += defendingUnit.getMetalCost();
                        enemyDrops[1] += defendingUnit.getDeuteriumCost();
                    }


                    // Removing from arrays
                    armies[defendingArmy][defending_group].remove(defendingUnit); // This is just removing it from the ArrayList in armies, not in planetArmy or enemyArmy

                    if (defendingArmy == 0) { // Removing it from the actual ArrayList too, this seems not practical at all.
                        planetArmy[defending_group].remove(defendingUnit);
                    } else {
                        enemyArmy[defending_group].remove(defendingUnit);
                    }

                    
                }

                // If it's not destroyed / or after destroying it

                // If it attacks again
                if( (int) (Math.random() * 100) <= attackingUnit.getChanceAttackAgain() && (remainderPercentageFleetPlanet() > 20 && remainderPercentageFleetEnemy() > 20)) {
                    // System.out.println("attacks again");
                    battleDevelopment += attackerStr + " gets to attack again!\n";
                    isAttackingAgain = true;
                } else { // if it doesn't attack again
                    if (remainderPercentageFleetPlanet() > 20 && remainderPercentageFleetEnemy() > 20){
                        battleDevelopment += "--------------------------------------------\nTURN OVER, SWITCHING ROLES\n--------------------------------------------\n\n";
                    }
                    isAttackingAgain = false;
                    
                    // Switching the turns
                    int var = attackingArmy;
                    attackingArmy = defendingArmy;
                    defendingArmy = var;
                }

        }

        
        }
        // After the combat is over
        updateResourceLoses();

        battleDevelopment += "\n\n------------------- COMBAT RESULTS -----------------------\n";
        battleDevelopment += "Resources lost by Planet: " + resourcesLosses[0][2] + "\n";
        battleDevelopment += "Resources lost by Enemy: " + resourcesLosses[1][2] + "\n\n";

        if (getWinner() == 0) {
            winner = "Planet";
            battleDevelopment += "Planet collects " + wasteMetalDeuterium[0] + " metal and " + wasteMetalDeuterium[1] + " deuterium";
            planet.setMetal(planet.getMetal() + wasteMetalDeuterium[0]);
            planet.setDeuterium(planet.getDeuterium() + wasteMetalDeuterium[1]);
        } else {
            winner = "Invader";
        }

        battleDevelopment += winner + " wins!";
        // System.out.println(battleDevelopment);
    }
        return;
    }

    public void printEnemyStats() {
        System.out.println("\nCURRENT THREAT\n");
        System.out.println("Light Hunters = " + enemyArmy[0].size());
        System.out.println("Heavy Hunters = " + enemyArmy[1].size());
        System.out.println("Battle Ships = " + enemyArmy[2].size());
        System.out.println("Armored Ships = " + enemyArmy[3].size());
        System.out.println();
    }

    public int getCostOfGroup(ArrayList<MilitaryUnit> group) {
        int total = 0;

        for (int i = 0; i < group.size(); i++) {
            total += group.get(i).getMetalCost();
        }
        return total;
    }

    public int getMetalCostOfArmy(ArrayList<MilitaryUnit>[] army) {
        int total = 0;
        for (int i = 0; i < army.length; i++) {
            for(int j = 0; i < army[i].size(); i++) {
                total += army[i].get(j).getMetalCost();
            }
        }
        return total;
    }

    public int getDeuteriumCostOfArmy(ArrayList<MilitaryUnit>[] army) {
        int total = 0;
        for (int i = 0; i < army.length; i++) {
            for(int j = 0; i < army[i].size(); i++) {
                total += army[i].get(j).getDeuteriumCost();
            }
        }
        return total;
    }

    public int getWinner() {
        if (resourcesLosses[0][2] < resourcesLosses[1][2]) {
            return 0;
        } else {
            return 1;
        }
    }
}
