package classes;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;

public class Main{
    public static void main(String[] args) {
        int deltaTime = 1000/50;
        Planet planet = new Planet(0, 0, 200000, 40000, 3000, 3000);
        
        Timer timer = new Timer();
        TimerTask tick = new TimerTask() {
            int time = 0;
            int seconds = 0;
            int minutes = 0;
            public void run() {
                time += deltaTime;
                seconds += deltaTime;

                if (seconds > 1000*50) {
                    minutes++;
                    seconds = 0;
                    System.out.println("A minute has passed");
                }
            }
        };
        
        TimerTask tenSecTask = new TimerTask() {
            public void run() {
                planet.setMetal(planet.getMetal() + 5000);
            }
        };
        
        TimerTask minuteTask = new TimerTask() {
            public void run() {
                System.out.println("A minute has passed");
            }
        };
        // The problem with timers and why idk how to implement it it's because
        // variables declared outside of the timer on main are not accessible in the timer

        timer.schedule(tick, 0, deltaTime);
        timer.schedule(tenSecTask, 1000 * 10, 1000 * 10);
        Scanner scanner = new Scanner(System.in);

        for(int i = 0; i< 100; i++){
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


    public ArrayList<MilitaryUnit>[] createEnemyArmy() {
        ArrayList<MilitaryUnit>[] army = new ArrayList[7];
        // Create the army
        
        return army;
    }
}
