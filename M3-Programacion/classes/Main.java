package classes;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;

public class Main{
    public static void main(String[] args) {

        ///////////////////////////////////////////////////////////////////
        // CREATING THE PLACEHOLDER ARMY FOR THE PLANET ///////////////////
        ///////////////////////////////////////////////////////////////////
        ArrayList<MilitaryUnit>[] planetArmy = new ArrayList[7];
        for(int i = 0; i < planetArmy.length; i++) {
            planetArmy[i] = new ArrayList<MilitaryUnit>();
        }
        LightHunter lh1 = new LightHunter();
        LightHunter lh2 = new LightHunter();
        LightHunter lh3 = new LightHunter();
        LightHunter lh4 = new LightHunter();
        HeavyHunter hh1 = new HeavyHunter();
        HeavyHunter hh2 = new HeavyHunter();
        HeavyHunter hh3 = new HeavyHunter();
        HeavyHunter hh4 = new HeavyHunter();
        BattleShip bs1 = new BattleShip();
        BattleShip bs2 = new BattleShip();
        BattleShip bs3 = new BattleShip();
        BattleShip bs4 = new BattleShip();
        ArmoredShip as1 = new ArmoredShip();
        ArmoredShip as2 = new ArmoredShip();
        ArmoredShip as3 = new ArmoredShip();
        ArmoredShip as4 = new ArmoredShip();
        MissileLauncher ml1 = new MissileLauncher(100, 20);
        MissileLauncher ml2 = new MissileLauncher(100, 20);
        MissileLauncher ml3 = new MissileLauncher(100, 20);
        IonCannon ic1 = new IonCannon(200,30);
        IonCannon ic2 = new IonCannon(200,30);
        IonCannon ic3 = new IonCannon(200,30);
        PlasmaCannon pc1 = new PlasmaCannon(300,40);
        PlasmaCannon pc2 = new PlasmaCannon(300,40);
        PlasmaCannon pc3 = new PlasmaCannon(300,40);

        planetArmy[0].add(lh1);
        planetArmy[0].add(lh2);
        planetArmy[0].add(lh3);
        planetArmy[0].add(lh4);
        planetArmy[1].add(hh1);
        planetArmy[1].add(hh2);
        planetArmy[1].add(hh3);
        planetArmy[1].add(hh4);
        planetArmy[2].add(bs1);
        planetArmy[2].add(bs2);
        planetArmy[2].add(bs3);
        planetArmy[2].add(bs4);
        planetArmy[3].add(as1);
        planetArmy[3].add(as2);
        planetArmy[3].add(as3);
        planetArmy[3].add(as4);
        planetArmy[4].add(ml1);
        planetArmy[4].add(ml2);
        planetArmy[4].add(ml3);
        planetArmy[5].add(ic1);
        planetArmy[5].add(ic2);
        planetArmy[5].add(ic3);
        planetArmy[6].add(pc1);
        planetArmy[6].add(pc2);
        planetArmy[6].add(pc3);



        ///////////////////////////////////////////////////////////////////
        ///////////////////////////////////////////////////////////////////
        ///////////////////////////////////////////////////////////////////
        
        Planet planet = new Planet(0, 0, 200000, 40000, 3000, 3000);
        Time time = new Time();
        
        System.out.println("a");
        ArrayList<MilitaryUnit>[] enemyArmy = createEnemyArmy();
        System.out.println("LightHunters = " + enemyArmy[0].size());
        System.out.println("HeavyHunters = " + enemyArmy[1].size());
        System.out.println("BattleShips = " + enemyArmy[2].size());
        System.out.println("ArmoredShips = " + enemyArmy[3].size());

        Timer timer = new Timer();
        TimerTask tick = new TimerTask() {
            
            public void run() {
                time.setMiliseconds(time.getMiliseconds() + time.getDeltaTime());
               
                if(time.getMiliseconds() > Time.secInMs) {
                    time.setSeconds(time.getSeconds() + 1);
                    time.setTotalSeconds(time.getTotalSeconds() + 1);
                    System.out.println(time.getTotalSeconds() + " seconds.");
                    time.setMiliseconds(0);
                }

                if(0 == time.getSeconds()%10 && time.getSeconds() != 0 && time.getMiliseconds() == 0) {
                    System.out.println("10 seconds have passed");
                    planet.setMetal(planet.getMetal() + 1000);
                    System.out.println("1000 metal added.");
                }

                if (time.getSeconds() % 60 == 0 && time.getSeconds() != 0 && time.getMiliseconds() == 0) {
                    time.setMinutes(time.getMinutes()+1);
                    time.setSeconds(0);

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
        // timer.schedule(tick, 0, time.getDeltaTime());
        //////////////////////////////////////////////////////////////////////

        // timer.schedule(tenSecTask, Time.secInMs * 10, Time.secInMs * 10);

        Scanner scanner = new Scanner(System.in);

        for(int i = 0; i< 2; i++){
        int choice = scanner.nextInt();

        switch (choice) {
            case 1:
                System.out.println("You have " + planet.getMetal() + " metal");
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
                    case 1: // Light hunters
                        army[0].add(new LightHunter());
                        deuteriumInitialResources -= Variables.DEUTERIUM_COST_LIGTHHUNTER;
                        break;

                    case 2: // Heavy hunters
                        army[1].add(new HeavyHunter());
                        deuteriumInitialResources -= Variables.DEUTERIUM_COST_HEAVYHUNTER;
                        break;

                    case 3: // Battle Ship
                        army[2].add(new BattleShip());
                        deuteriumInitialResources -= Variables.DEUTERIUM_COST_BATTLESHIP;
                        break;
                    
                    case 4: // Armored Ship
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
}

class Time {
    private int miliseconds;
    private int seconds;
    private int minutes;
    private int totalSeconds;
    private int deltaTime;

    static int secInMs = 1000;


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
