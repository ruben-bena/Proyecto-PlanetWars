package classes;

import java.util.ArrayList;

public class Battle {
    private ArrayList<MilitaryUnit>[] planetArmy; // → para almacenar la flota de nuestro planeta. 
    private ArrayList<MilitaryUnit>[] enemyArmy; // → para almacenar la flota enemiga
    private ArrayList[][] armies; // que es un array de ArrayList de dos filas y siete columnas, donde almacenaremos nuestro ejército en la primera fila, y el ejército enemigo en la segunda fila;
    private String battleDevelopment; // Donde guardamos todo el desarrollo de la batalla paso a paso
    private int[][] initialCostFleet; // coste de metal deuterio de los ejercitos iniciales
    // initialCostFleet = [[metal][deuterio],[metal][deuterio]]; // donde initialCostFleet[0]costes unidades del planeta , initialCostFleet[1] costes unidades enemigas. Lo necesitamos para saber las pérdidas en materiales de cada flota.

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

    public Battle(ArrayList<MilitaryUnit>[] planetArmy, ArrayList<MilitaryUnit>[] enemyArmy, ArrayList[][] armies) {
        this.planetArmy = planetArmy;
        this.enemyArmy = enemyArmy;
        this.armies = new ArrayList[2][7];
        
        for (int i = 0; i < armies.length; i++) {
            for (int j = 0; j < armies[i].length; j++) {
                this.armies[i][j] = new ArrayList<MilitaryUnit>() ;
                this.armies[i][j] = armies[i][j];
            }
        }
        this.initialNumberUnitsPlanet = initialFleetNumber(planetArmy);
        this.initialNumberUnitsEnemy = initialFleetNumber(enemyArmy);
        this.wasteMetalDeuterium = new int[2];
        this.enemyDrops = new int[2];
        this.planetDrops = new int[2];
        initInitialArmies();
        this.actualNumberUnitsPlanet = getArrayValuesFromArrayList(planetArmy);
        this.actualNumberUnitsEnemy = getArrayValuesFromArrayList(enemyArmy);
    }


    public void initInitialArmies() {
        initialArmies = new int[2][7];
        // 0 --> planet
        // 1 --> enemy
        // 0-7 --> Armies

        // for (int i = 0; i < initialArmies.length; i++) {
        //     for (int j = 0; j < initialArmies[i].length; j++) {
        //         initialArmies[i][j] = 0;
        //     }
        // }

        for (int i = 0; i < initialArmies.length; i++) {
                initialArmies[i] = getArrayValuesFromArrayList(armies[i]);
            }
        }
    

    
    public void updateResourceLoses() {
        resourcesLosses[0][0] = initialCostFleet[0][0] - planetDrops[0];
        resourcesLosses[0][1] = initialCostFleet[0][1] - planetDrops[1];
        resourcesLosses[1][0] = initialCostFleet[1][0] - enemyDrops[0];
        resourcesLosses[1][1] = initialCostFleet[1][1] - enemyDrops[1];
        resourcesLosses[0][2] = resourcesLosses[0][0] + 5 * resourcesLosses[0][1];
        resourcesLosses[1][2] = resourcesLosses[1][0] + 5 * resourcesLosses[1][1];
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
        if (army == armies[0]) {
            return (total * 100) / initialNumberUnitsPlanet;
        } else {
            return (total * 100) / initialNumberUnitsEnemy;
        }
    }

    public int getGroupDefender(ArrayList<MilitaryUnit>[] army) {
        // I have to get the defender group of the army, 0-3 if enemy, 0-6 if planet. 
        // Planet is always the one being attacked.
        // How can I differentiate if it's planet or enemy?
        // Maybe it's not necessary to differentiate because it's chosen depending on the number of each army
        // and the attacker doesn't have any from army 4 to 6

        // This algorithm chooses one group randomly between the 7 groups depending on the number
        int[] array = new int[7];
        int totalSum = 0;
        for(int i = 0; i < army.length; i++) {
            array[i] = army[i].size();
            totalSum += army[i].size();
        }
        int randomNumber = (int) (Math.random() * totalSum);

        for(int i = 0; i < array.length; i++) {
            int j = i;
            int sum = 0;
            while (j >= 0) {
                sum += array[j];
                j--;
            }
            if (sum >= randomNumber) {
                return i;
            }
        }

        // If it gets to this, it's an error.
        return -1;
    }

    public int getPlanetGroupAttacker() {
        // IDK if this is what it's asking?
        return getGroupDefender(planetArmy);
    }

    public int getEnemyGroupAttacker() {
        // IDK if this is what it's asking?
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
        enemyArmy = Main.createEnemyArmy();
        System.out.println("NEW THREAT IS COMING");
    }
    public void combat() {
        // Selecting randomly who starts the combat
        int attackingArmy = (int) (Math.random() * 2);
        int defendingArmy = 1;

        if(attackingArmy == 1) {
            defendingArmy = 0;
        }

        int attacking_group;
        int defending_group;
        
        while(remainderPercentageFleet(planetArmy) > 20 && remainderPercentageFleet(enemyArmy) > 20) {
            // Selecting attacking group for the army starting the fight
            if (attackingArmy == 1) {
                attacking_group = getPlanetGroupAttacker();
            } else {
                attacking_group = getEnemyGroupAttacker();
            }
            // Selecting random attacking unit from selected group
            int indexAttackingUnit = (int) (Math.random() * armies[attackingArmy][attacking_group].size());
            MilitaryUnit attackingUnit = (MilitaryUnit) (armies[attackingArmy][attacking_group].get((indexAttackingUnit)));

            // Selecting defending group
            defending_group = getGroupDefender(armies[defendingArmy]);
            int indexDefendingUnit = (int) (Math.random() * armies[attackingArmy][defending_group].size());
            MilitaryUnit defendingUnit = (MilitaryUnit) (armies[attackingArmy][defending_group].get(indexDefendingUnit));

            // Making them fight
            // Applying damage
            defendingUnit.takeDamage(attackingUnit.attack());
            // Checking if it's destroyed
            if(defendingUnit.getActualArmor() <= 0) {
                armies[defendingArmy][defending_group].remove(defendingUnit); // This is just removing it from the ArrayList in armies, not in planetArmy or enemyArmy

                if (planetArmy == armies[defendingArmy]) { // Removing it from the actual ArrayList too, this seems not practical at all.
                    planetArmy[defending_group].remove(defendingUnit);
                } else {
                    enemyArmy[defending_group].remove(defendingUnit);
                }
            }
        }
    }
}
