package classes;

import java.util.ArrayList;

public class Battle {
    private ArrayList<MilitaryUnit>[] planetArmy; // → para almacenar la flota enemiga
    private ArrayList<MilitaryUnit>[] enemyArmy; // → para almacenar la flota de nuestro planeta.
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
    // perdidas deuterio planeta}, resourcesLooses[1] lo mismo pero para el ejercito
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
    // actualNumberUnitsPlanet[0] --> cazadores pesados
    // actualNumberUnitsPlanet[0] --> Naves de battalla
    // actualNumberUnitsPlanet[0] --> Acorazados
    // actualNumberUnitsPlanet[0] --> Lanzamisiles
    // actualNumberUnitsPlanet[0] --> Cañones de iones
    // actualNumberUnitsPlanet[0] --> Cañones de Plasma

    public void initInitialArmies() {
        initialArmies = new int[2][7];
        // 0 --> planet
        // 1 --> enemy
        // 0-7 --> Armies
        for (int i = 0; i < initialArmies.length; i++) {
            for (int j = 0; j < initialArmies[i].length; j++) {
                initialArmies[i][j] = 0;
            }
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

    public int fleetResourceCost(ArrayList<MilitaryUnit> army) {
        int cost = 0;
        for (int i = 0; i < army.size(); i++) {
            cost += army.get(i).getMetalCost();
        }
        return cost;
    }
}