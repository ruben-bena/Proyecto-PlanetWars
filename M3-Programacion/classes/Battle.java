package classes;
import GUI.*;
import ddbb.*;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class Battle {
    private ArrayList<MilitaryUnit>[] planetArmy; // → para almacenar la flota de nuestro planeta. 
    private ArrayList<MilitaryUnit>[] enemyArmy; // → para almacenar la flota enemiga
    private ArrayList[][] armies; // que es un array de ArrayList de dos filas y siete columnas, donde almacenaremos nuestro ejército en la primera fila, y el ejército enemigo en la segunda fila;
    private String battleDevelopment; // Donde guardamos todo el desarrollo de la batalla paso a paso
    private String battleReport;
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

    private boolean hasCombatStarted;
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

    private int planetArmyPercRemaining;
    private int enemyArmyPercRemaining;
    private int attackingArmy;
    private boolean skipBattle;
    private Planet userPlanet;
    private Planet enemyPlanet;
    private int battleType;
    private Battle thisBattle;
    private MainScreen ms;

    // Constructor for defending planet
    public Battle(Planet planet, Planet enemyPlanet, MainPanel mp, MainScreen ms) {
        this.planetArmy = planet.getArmy();
        this.enemyArmy = enemyPlanet.getAttackerArmy();
        this.initialCostFleet = new int[2][2];
        this.resourcesLosses = new int[2][3];
        this.hasCombatStarted = false;
        this.planetArmyPercRemaining = 100;
        this.enemyArmyPercRemaining = 100;
        this.ms = ms;
        skipBattle = false;
        this.userPlanet = planet;
        this.enemyPlanet = enemyPlanet;
        this.enemyPlanet.setActiveThreat(true);
        this.battleType = 0;
        this.battleReport = "";
        thisBattle = this;
        announceCombat();
        TimerTask task = new TimerTask() {
            public void run() {

                updateArmies();
                initInitialArmies();
                combat(planet, enemyPlanet, mp, battleType);
                planet.addBattleReport(thisBattle);
                planet.setActiveThreat(false);
                hasCombatStarted = false;

                planet.setNBattles(planet.getNBattles() + 1);
                mp.getMiddlePanel().changeScreenToDefaultScene();
                planet.setCurrentThreat(null);

                System.out.println("Constructor Battle (AMENAZA EXTERNA)");
                System.out.println(planet);

                // DDBB operations
                bbddOperations(planet);

                // Generate battle XML
                BattleXmlGenerator.generateXml(Battle.this);

                // Transform battle XML to HTML
                BattleHtmlTransformator.transform(GlobalContext.num_battle);


                
            }
        };
        Timer timer = new Timer();

        timer.schedule(task, Time.countdownBattleTime);
        
    }

    // Constructor for invading enemy planet
    public Battle(Planet planet, Planet enemyPlanet, MainPanel mp, MainScreen ms, int i) { // int i is only to differentiate the constructors, there's no use for it
        this.planetArmy = enemyPlanet.getArmy();
        this.enemyArmy = planet.getAttackerArmy();
        this.initialCostFleet = new int[2][2];
        this.resourcesLosses = new int[2][3];
        this.hasCombatStarted = false;
        this.planetArmyPercRemaining = 100;
        this.enemyArmyPercRemaining = 100;
        skipBattle = false;
        this.userPlanet = planet;
        this.enemyPlanet = enemyPlanet;
        this.enemyPlanet.setActiveThreat(true);
        this.battleType = 1;
        this.battleReport = "";
        thisBattle = this;
        announceCombat();
        
        TimerTask task = new TimerTask() {
            public void run() {
                updateArmies();
                initInitialArmies();
                planet.setIsInvading(true);
                combat(enemyPlanet, planet, mp, battleType);
                planet.addBattleReport(thisBattle);
                planet.setActiveThreat(false);
                hasCombatStarted = false;

                planet.setNBattles(planet.getNBattles() + 1);
                mp.getMiddlePanel().changeScreenToDefaultScene();
                planet.setCurrentThreat(null);
                planet.setIsInvading(false);

                System.out.println("Constructor Battle (INVASIÓN)");
                System.out.println(planet);

                // DDBB operations
                bbddOperations(planet);

                // Generate battle XML
                BattleXmlGenerator.generateXml(Battle.this);

                // Transform battle XML to HTML
                BattleHtmlTransformator.transform(GlobalContext.num_battle);


                
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
        // resourcesLosses[0][0] = initialCostFleet[0][0] + planetDrops[0];
        // resourcesLosses[0][1] = initialCostFleet[0][1] + planetDrops[1];
        // resourcesLosses[1][0] = initialCostFleet[1][0] + enemyDrops[0];
        // resourcesLosses[1][1] = initialCostFleet[1][1] + enemyDrops[1];
        // resourcesLosses[0][2] = resourcesLosses[0][0] + 5 * resourcesLosses[0][1];
        // resourcesLosses[1][2] = resourcesLosses[1][0] + 5 * resourcesLosses[1][1];
        
        // resourcesLosses[0][0] = initialCostFleet[0][0];
        // resourcesLosses[0][1] = initialCostFleet[0][1];

        resourcesLosses[0][0] += Variables.METAL_COST_LIGTHHUNTER * planetDrops[0];
        resourcesLosses[0][0] += Variables.METAL_COST_HEAVYHUNTER * planetDrops[1];
        resourcesLosses[0][0] += Variables.METAL_COST_BATTLESHIP * planetDrops[2];
        resourcesLosses[0][0] += Variables.METAL_COST_ARMOREDSHIP * planetDrops[3];
        resourcesLosses[0][0] += Variables.METAL_COST_MISSILELAUNCHER * planetDrops[4];
        resourcesLosses[0][0] += Variables.METAL_COST_IONCANNON * planetDrops[5];
        resourcesLosses[0][0] += Variables.METAL_COST_PLASMACANNON * planetDrops[6];

        resourcesLosses[0][1] += Variables.DEUTERIUM_COST_LIGTHHUNTER * planetDrops[0];
        resourcesLosses[0][1] += Variables.DEUTERIUM_COST_HEAVYHUNTER * planetDrops[1];
        resourcesLosses[0][1] += Variables.DEUTERIUM_COST_BATTLESHIP * planetDrops[2];
        resourcesLosses[0][1] += Variables.DEUTERIUM_COST_ARMOREDSHIP * planetDrops[3];
        resourcesLosses[0][1] += Variables.DEUTERIUM_COST_MISSILELAUNCHER * planetDrops[4];
        resourcesLosses[0][1] += Variables.DEUTERIUM_COST_IONCANNON * planetDrops[5];
        resourcesLosses[0][1] += Variables.DEUTERIUM_COST_PLASMACANNON * planetDrops[6];

        resourcesLosses[1][0] += Variables.METAL_COST_LIGTHHUNTER * enemyDrops[0];
        resourcesLosses[1][0] += Variables.METAL_COST_HEAVYHUNTER * enemyDrops[1];
        resourcesLosses[1][0] += Variables.METAL_COST_BATTLESHIP * enemyDrops[2];
        resourcesLosses[1][0] += Variables.METAL_COST_ARMOREDSHIP * enemyDrops[3];
        resourcesLosses[1][0] += Variables.METAL_COST_MISSILELAUNCHER * enemyDrops[4];
        resourcesLosses[1][0] += Variables.METAL_COST_IONCANNON * enemyDrops[5];
        resourcesLosses[1][0] += Variables.METAL_COST_PLASMACANNON * enemyDrops[6];

        resourcesLosses[1][1] += Variables.DEUTERIUM_COST_LIGTHHUNTER * enemyDrops[0];
        resourcesLosses[1][1] += Variables.DEUTERIUM_COST_HEAVYHUNTER * enemyDrops[1];
        resourcesLosses[1][1] += Variables.DEUTERIUM_COST_BATTLESHIP * enemyDrops[2];
        resourcesLosses[1][1] += Variables.DEUTERIUM_COST_ARMOREDSHIP * enemyDrops[3];
        resourcesLosses[1][1] += Variables.DEUTERIUM_COST_MISSILELAUNCHER * enemyDrops[4];
        resourcesLosses[1][1] += Variables.DEUTERIUM_COST_IONCANNON * enemyDrops[5];
        resourcesLosses[1][1] += Variables.DEUTERIUM_COST_PLASMACANNON * enemyDrops[6];

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
    
    public Planet getUserPlanet() {
        return userPlanet;
    }

    public void setUserPlanet(Planet userPlanet) {
        this.userPlanet = userPlanet;
    }

    public Planet getEnemyPlanet() {
        return enemyPlanet;
    }

    public void setEnemyPlanet(Planet enemyPlanet) {
        this.enemyPlanet = enemyPlanet;
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
        } while(randMultiplier < 0.1);
        
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
    public void updateArmies() {
        armies[0] = new ArrayList[7];
        for(int i = 0; i < planetArmy.length; i++) {
            armies[0][i] = new ArrayList<>(planetArmy[i]);
        }
        armies[1] = new ArrayList[7];
        for(int i = 0; i < enemyArmy.length; i++) {
            armies[1][i] = new ArrayList<>(enemyArmy[i]);
        }
    }

    public void announceCombat() {
        // enemyArmy = Main.createEnemyArmy();
        this.armies = new ArrayList[2][7];
        

        armies[0] = new ArrayList[7];
        for(int i = 0; i < planetArmy.length; i++) {
            armies[0][i] = new ArrayList<>(planetArmy[i]);
        }
        armies[1] = new ArrayList[7];
        for(int i = 0; i < enemyArmy.length; i++) {
            armies[1][i] = new ArrayList<>(enemyArmy[i]);
        }


        initialNumberUnitsPlanet = initialFleetNumber(planetArmy);
        initialNumberUnitsEnemy = initialFleetNumber(enemyArmy);
        battleDevelopment = "";
        wasteMetalDeuterium = new int[2];
        enemyDrops = new int[7];
        planetDrops = new int[7];

        initInitialArmies();


        initialCostFleet[0][0] = getMetalCostOfArmy(planetArmy);
        initialCostFleet[0][1] = getDeuteriumCostOfArmy(planetArmy);
        initialCostFleet[1][0] = getMetalCostOfArmy(enemyArmy);
        initialCostFleet[1][1] = getDeuteriumCostOfArmy(enemyArmy);

        if(battleType == 0) {
            System.out.println("NEW THREAT IS COMING");
        } else {
            System.out.println("INVADING ANOTHER PLANET");
        }
    }
    public void combat(Planet planet, Planet enemyPlanet, MainPanel mainPanel, int battleType) {
        // battleType 0 = getting invaded, 1 = invading.
        initialNumberUnitsPlanet = initialFleetNumber(planetArmy);
        MiddlePanel screen = mainPanel.getMiddlePanel();
        if(planet.isActiveThreat() || enemyPlanet.isActiveThreat()){
            System.out.println("adsjdskd");
            hasCombatStarted = true;
            
            
            //Change the screen
            screen.changeScreenToBattleScene();

            ///////////////////

            
            String winner = "";
            // Selecting randomly who starts the combat
            attackingArmy = (int) (Math.random() * 2);
            int defendingArmy = 1;

            if(attackingArmy == 1) {
                defendingArmy = 0;
            } else if (attackingArmy == 0) {
                defendingArmy = 1;
            }

            int attacking_group;
            int defending_group;
            String attackerStr;
            String defenderStr;
            boolean isAttackingAgain; // Variable to check if it gets to attack again in the same turn
            
            // System.out.println("Initial fleet number Planet = " + initialFleetNumber(planetArmy));
            // System.out.println(remainderPercentageFleetPlanet() > 20 && remainderPercentageFleetEnemy() > 20);
            while(remainderPercentageFleetPlanet() > 20 && remainderPercentageFleetEnemy() > 20) { /////////////////////////
                mainPanel.getMiddlePanel().requestFocusInWindow();
                planetArmyPercRemaining = remainderPercentageFleetPlanet();
                enemyArmyPercRemaining = remainderPercentageFleetEnemy();
                // System.out.println("Percentage Planet army = " + remainderPercentageFleet(armies[0]));
                // System.out.println("Percentage Enemy army = " + remainderPercentageFleet(armies[1]));
                isAttackingAgain = true;
                // Selecting attacking group for the army starting the fight

                

                

                while(isAttackingAgain && (remainderPercentageFleetPlanet() > 20 && remainderPercentageFleetEnemy() > 20)) {
                    int indexAttackingUnit;
                    int indexDefendingUnit;
                    // System.out.println(attackerStr + " attacks");
                    // Selecting random attacking unit from selected group
                    // System.out.println("Attacking group = " + attacking_group);
                    MilitaryUnit attackingUnit;

                    // UPDATING THESE VALUES AGAIN SO IT GET'S REFRESHED VISUALLY
                    planetArmyPercRemaining = remainderPercentageFleetPlanet();
                    enemyArmyPercRemaining = remainderPercentageFleetEnemy();
                    //////////////////////////
                    /// 
                    if (attackingArmy == 0) {
                    attacking_group = getPlanetGroupAttacker();
                    attackerStr = planet.getPlanetName();
                    defenderStr = enemyPlanet.getPlanetName();
                    } else {
                    attacking_group = getEnemyGroupAttacker();
                    attackerStr = enemyPlanet.getPlanetName();
                    defenderStr = planet.getPlanetName();
                    }

                    // DISPLAYING BATTLE INFO FOR DEBUGGING
                    // for(int i = 0; i < planetArmy.length; i++) {
                    //     System.out.println("Planet Units " + i + ": " + planetArmy[i].size());
                    //     System.out.println("Enemy Units " + i + ": " + enemyArmy[i].size());
                    // }
                    // System.out.println("attacking army = " + attackingArmy);
                    if (attackingArmy == 0) {
                        // System.out.println("Attacking group size = " + planetArmy[attacking_group].size());
                        indexAttackingUnit = (int) (Math.random() * planetArmy[attacking_group].size());
                        attackingUnit = (MilitaryUnit) (planetArmy[attacking_group].get((indexAttackingUnit)));
                    } else {
                        // System.out.println("Attacking group size = " + enemyArmy[attacking_group].size());
                        indexAttackingUnit = (int) (Math.random() * enemyArmy[attacking_group].size());
                        // System.out.println("enemy attacking group = " + attacking_group);
                        attackingUnit = (MilitaryUnit) (enemyArmy[attacking_group].get((indexAttackingUnit)));
                        // System.out.println(attackingUnit.getName());
                    }
                    
                    // System.out.println("Attacking unit index = " + indexAttackingUnit);


                    MilitaryUnit defendingUnit;
                    // Selecting defending group
                    if (defendingArmy == 0) {
                        defending_group = getGroupDefender(planetArmy);

                        indexDefendingUnit = (int) (Math.random() * planetArmy[defending_group].size());
                        defendingUnit = (MilitaryUnit) (planetArmy[defending_group].get(indexDefendingUnit));
                    } else {
                        defending_group = getGroupDefender(enemyArmy);
                        indexDefendingUnit = (int) (Math.random() * enemyArmy[defending_group].size());
                        defendingUnit = (MilitaryUnit) (enemyArmy[defending_group].get(indexDefendingUnit));
                        // System.out.println("enemy defending unit = " + defendingUnit.getName());

                    }


                    
                    
                   
                    
                    
                    // Making them fight
                    // Applying damage
                    battleDevelopment += attackerStr + " attacks " + defenderStr + "\n" + defendingUnit.getName() + " receives " + attackingUnit.attack() + " dmg.\n\n";
                    defendingUnit.takeDamage(attackingUnit.attack());

                    if (attackingArmy == 0) {
                        mainPanel.getMiddlePanel().paintCurrentBattleState(this, attackingUnit, defendingUnit);
                    } else {
                        mainPanel.getMiddlePanel().paintCurrentBattleState(this, defendingUnit, attackingUnit);
                    }

                    

                    // Checking if it's destroyed
                    if(defendingUnit.getActualArmor() <= 0) {
                        battleDevelopment += defendingUnit.getName() + " has " + defendingUnit.getActualArmor() + " armor, it gets destroyed.\n";

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
                        // if (defendingArmy == 0) { //I'm assuming the 0 is for metal and 1 is for deuterium
                        //     planetDrops[0] += defendingUnit.getMetalCost();
                        //     planetDrops[1] += defendingUnit.getDeuteriumCost();
                        // } else {
                        //     enemyDrops[0] += defendingUnit.getMetalCost();
                        //     enemyDrops[1] += defendingUnit.getDeuteriumCost();
                        // }


                        // Removing from arrays
                        // armies[defendingArmy][defending_group].remove(defendingUnit); // This is just removing it from the ArrayList in armies, not in planetArmy or enemyArmy

                        if (defendingArmy == 0) { // Removing it from the actual ArrayList too, this seems not practical at all.
                            planetArmy[defending_group].remove(defendingUnit);
                        } else {
                            enemyArmy[defending_group].remove(defendingUnit);
                        }

                        // Playing explosion sound
                        if(!isSkipBattle()){
                            AudioPlayer.doExplosion();
                        }
                        
                    }

                    // If it's not destroyed / or after destroying it

                    if (attackingArmy == 0) {
                        mainPanel.getMiddlePanel().paintCurrentBattleState(this, attackingUnit, defendingUnit);
                    } else {
                        mainPanel.getMiddlePanel().paintCurrentBattleState(this, defendingUnit, attackingUnit);
                    }

                    // If it attacks again
                    if( (int) (Math.random() * 100) <= attackingUnit.getChanceAttackAgain() && (remainderPercentageFleetPlanet() > 20 && remainderPercentageFleetEnemy() > 20)) {
                        // System.out.println("attacks again");
                        battleDevelopment += attackerStr + " gets to attack again!\n";
                        isAttackingAgain = true;
                    } else { // if it doesn't attack again
                        if (remainderPercentageFleetPlanet() > 20 && remainderPercentageFleetEnemy() > 20){
                            battleDevelopment += "--------------------------------------------\nTURN OVER, SWITCHING ROLES\n--------------------------------------------\n\n";
                        } else{
                            break;
                        }
                        isAttackingAgain = false;
                        
                        // Switching the turns
                        int var = attackingArmy;
                        attackingArmy = defendingArmy;
                        defendingArmy = var;
                    }

                    try {

                        if (!skipBattle){
                            Thread.sleep(500);
                        }
                    } catch (InterruptedException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }

                }

            }
            // After the combat is over
            updateDrops();
            updateResourceLoses();
            battleDevelopment += "\n\n------------------- COMBAT RESULTS -----------------------\n";
            battleDevelopment += "Resources lost by " + planet.getPlanetName() + ": " + resourcesLosses[0][2] + "\n";
            battleDevelopment += "Resources lost by " + enemyPlanet.getPlanetName() +": " + resourcesLosses[1][2] + "\n\n";

            if (getWinner() == 0) {
                winner = planet.getPlanetName();
                battleDevelopment += planet.getPlanetName() + " collects " + wasteMetalDeuterium[0] + " metal and " + wasteMetalDeuterium[1] + " deuterium\n\n";
                planet.setMetal(planet.getMetal() + wasteMetalDeuterium[0]);
                planet.setDeuterium(planet.getDeuterium() + wasteMetalDeuterium[1]);
            } else {
                if(battleType == 1) {
                    winner = enemyPlanet.getPlanetName();
                    battleDevelopment += enemyPlanet.getPlanetName() + " collects " + wasteMetalDeuterium[0] + " metal and " + wasteMetalDeuterium[1] + " deuterium\n\n";
                    enemyPlanet.setMetal(enemyPlanet.getMetal() + wasteMetalDeuterium[0]);
                    enemyPlanet.setDeuterium(enemyPlanet.getDeuterium() + wasteMetalDeuterium[1]);
                }
                else {
                    winner = planet.getPlanetName();
                }
            }

            battleDevelopment += winner + " wins!";

        }
            

            updateBattleReport();
            System.out.println(battleReport);
            new ResultFrame(this);
            new ThreatTimer(planet, ms);

            return;
        }

        public void updateBattleReport() {
            String divider = "----------------------------------------------------\n";
            battleReport += divider;
            if(battleType == 0) {
                battleReport += "Army Planet " + userPlanet.getPlanetName() + "\n";
            }
            else {
                battleReport += "Army Planet " + enemyPlanet.getPlanetName() + "\n";
            }
            for(int i = 0; i < armies[0].length; i++) {
                if(armies[0][i].size() > 0){
                    battleReport +=  ((MilitaryUnit) armies[0][i].get(0)).getName() + ": " + armies[0][i].size() + " -" + planetDrops[i] +"\n";
                }
            }
            battleReport += divider;

            if(battleType == 0) {
                battleReport += "Army Planet " + enemyPlanet.getPlanetName() + "\n";
            }
            else {
                battleReport += "Army Planet " + userPlanet.getPlanetName() + "\n";
            }
            
            for(int i = 0; i < armies[1].length; i++) {
                if(armies[1][i].size() > 0){
                    battleReport +=  ((MilitaryUnit) armies[1][i].get(0)).getName() + ": " + armies[1][i].size() + " -" + enemyDrops[i] +"\n";
                }
            }

            battleReport += divider;

            battleReport += "Cost Army " + userPlanet.getPlanetName() + "\n";
            battleReport += "Metal: " + initialCostFleet[0][0] + "\n";
            battleReport += "Deuterium: " + initialCostFleet[0][1] + "\n";

            battleReport += divider;

            battleReport += "Cost Army " + enemyPlanet.getPlanetName() + "\n";
            battleReport += "Metal: " + initialCostFleet[1][0] + "\n";
            battleReport += "Deuterium: " + initialCostFleet[1][1] + "\n";

            battleReport += divider;

            if (battleType == 0) {
                battleReport += "Losses Army " + userPlanet.getPlanetName() + "\n";
            } else {
                battleReport += "Losses Army " + enemyPlanet.getPlanetName() + "\n";
            }
            battleReport += "Metal: " + resourcesLosses[0][0] + "\n";
            battleReport += "Deuterium: " + resourcesLosses[0][1] + "\n";
            battleReport += "Weighted: " + resourcesLosses[0][2] + "\n";

            battleReport += divider;

            if(battleType == 0) {
                battleReport += "Losses Army " + enemyPlanet.getPlanetName() + "\n";
            } else {
                battleReport += "Losses Army " + userPlanet.getPlanetName() + "\n";
            }

            battleReport += "Metal: " + resourcesLosses[1][0] + "\n";
            battleReport += "Deuterium: " + resourcesLosses[1][1] + "\n";
            battleReport += "Weighted: " + resourcesLosses[1][2] + "\n";

            battleReport += divider;

            battleReport += "Waste Generated:\n";
            battleReport += "Metal: " + wasteMetalDeuterium[0] + "\n";
            battleReport += "Deuterium: " + wasteMetalDeuterium[1] + "\n";

        }

        public void printEnemyStats() {
            System.out.println("\nCURRENT THREAT\n");
            System.out.println("Light Hunters = " + enemyArmy[0].size());
            System.out.println("Heavy Hunters = " + enemyArmy[1].size());
            System.out.println("Battle Ships = " + enemyArmy[2].size());
            System.out.println("Armored Ships = " + enemyArmy[3].size());
            System.out.println();
        }

        public void printArmies() {
            for(int i = 0; i < armies.length; i++) {
                for(int j = 0; j < armies[i].length; j++) {
                    if(armies[i][j] != null) {
                        System.out.println(armies[i][j].size());
                    }
                }
            }
        }

        public void updateDrops() {
            planetDrops[0] = initialArmies[0][0] - planetArmy[0].size();
            planetDrops[1] = initialArmies[0][1] - planetArmy[1].size();
            planetDrops[2] = initialArmies[0][2] - planetArmy[2].size();
            planetDrops[3] = initialArmies[0][3] - planetArmy[3].size();
            planetDrops[4] = initialArmies[0][4] - planetArmy[4].size();
            planetDrops[5] = initialArmies[0][5] - planetArmy[5].size();
            planetDrops[6] = initialArmies[0][6] - planetArmy[6].size();

            enemyDrops[0] = initialArmies[1][0] - enemyArmy[0].size();
            enemyDrops[1] = initialArmies[1][1] - enemyArmy[1].size();
            enemyDrops[2] = initialArmies[1][2] - enemyArmy[2].size();
            enemyDrops[3] = initialArmies[1][3] - enemyArmy[3].size();
            enemyDrops[4] = initialArmies[1][4] - enemyArmy[4].size();
            enemyDrops[5] = initialArmies[1][5] - enemyArmy[5].size();
            enemyDrops[6] = initialArmies[1][6] - enemyArmy[6].size();
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
                for(int j = 0; j < army[i].size(); j++) {
                    total += army[i].get(j).getMetalCost();
                }
            }
            return total;
        }

        public int getDeuteriumCostOfArmy(ArrayList<MilitaryUnit>[] army) {
            int total = 0;
            for (int i = 0; i < army.length; i++) {
                for(int j = 0; j < army[i].size(); j++) {
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


        public boolean isHasCombatStarted() {
            return hasCombatStarted;
        }


        public int getAttackingArmy() {
            return attackingArmy;
        }

        
        public int getBattleType() {
			return battleType;
		}

        

		public String getBattleReport() {
			return battleReport;
		}

		public void setBattleReport(String battleReport) {
			this.battleReport = battleReport;
		}

		public void setBattleType(int battleType) {
			this.battleType = battleType;
		}

		public int getPlanetArmyPercRemaining() {
            return planetArmyPercRemaining;
        }


        public int getEnemyArmyPercRemaining() {
            return enemyArmyPercRemaining;
        }

        public int[][] getInitialArmies() {
            return initialArmies;
        }

        public boolean isSkipBattle() {
            return skipBattle;
        }


        public void setSkipBattle(boolean skipBattle) {
            this.skipBattle = skipBattle;
        }


        public String getBattleDevelopment() {
            return battleDevelopment;
        }


        public ArrayList<MilitaryUnit>[] getEnemyArmy() {
            return enemyArmy;
        }

        public ArrayList<MilitaryUnit>[] getPlanetArmy() {
            return planetArmy;
        }

        public int[][] getInitialCostFleet() {
            return initialCostFleet;
        }

        public int[][] getResourcesLosses() {
            return resourcesLosses;
        }

        public int[] getWasteMetalDeuterium() {
            return wasteMetalDeuterium;
        }

    public void bbddOperations(Planet planet) {
        // PlanetStatsTable
        GlobalContext.planetStatsTable.updateAttributes(planet);
        // BattleStatsTable
        GlobalContext.battleStatsTable = new BattleStatsTable(
            GlobalContext.database,
            GlobalContext.planet_id,
            wasteMetalDeuterium[0],
            wasteMetalDeuterium[1]
        );
        GlobalContext.battleStatsTable.insertRow();
        // BattleLogTable
        GlobalContext.battleLogTable = new BattleLogTable(
            GlobalContext.database,
            GlobalContext.num_battle,
            battleDevelopment
        );
        GlobalContext.battleLogTable.insertRow();
        // PlanetBattleDefense
        GlobalContext.planetBattleDefenseTable = new PlanetBattleDefenseTable(
            GlobalContext.database,
            GlobalContext.num_battle,
            Battle.this // This way we pass the Battle and not the TimerTask
        );
        GlobalContext.planetBattleDefenseTable.insertRow();
        // PlanetBattleArmy
        GlobalContext.planetBattleArmyTable = new PlanetBattleArmyTable(
            GlobalContext.database,
            GlobalContext.num_battle,
            Battle.this
        );
        GlobalContext.planetBattleArmyTable.insertRow();
        // EnemyArmyTable
        GlobalContext.enemyArmyTable = new EnemyArmyTable(
            GlobalContext.database,
            GlobalContext.num_battle,
            Battle.this
        );
        GlobalContext.enemyArmyTable.insertRow();
    }
}
