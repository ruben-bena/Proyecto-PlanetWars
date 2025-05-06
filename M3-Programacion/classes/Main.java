package classes;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;

public class Main{
    public static void main(String[] args) {
        
        Planet planet = new Planet(0, 0, 200000, 40000, 3000, 3000);
        Time time = new Time();
        
        Timer timer = new Timer();
        TimerTask tick = new TimerTask() {
            
            public void run() {
                time.setMiliseconds(time.getMiliseconds() + time.getDeltaTime());
               
                if(time.getMiliseconds() > Time.secInMs) {
                    time.setSeconds(time.getSeconds() + 1);
                    time.setTotalSeconds(time.getTotalSeconds() + 1);
                    System.out.println("A second has passed");
                    time.setMiliseconds(0);
                }

                if(time.getSeconds() >= 10) {
                    System.out.println("10 seconds have passed");
                }

                if (time.getSeconds() > 60) {
                    time.setMinutes(time.getMinutes()+1);
                    time.setSeconds(0);

                    System.out.println("A minute has passed");
                }

            }
        };
        
        TimerTask tenSecTask = new TimerTask() {
            int increment = 5000;
            public void run() {
                planet.setMetal(planet.getMetal() + increment);
                System.out.println(increment + " metal added.");

            }
        };
        
        TimerTask minuteTask = new TimerTask() {
            public void run() {
                System.out.println("A minute has passed");

            }
        };
        // The problem with timers and why idk how to implement it it's because
        // variables declared outside of the timer on main are not accessible in the timer


        // One option is to have multiple timers for everything
        // Another option is to figure out how to do it with one timer and checking time conditions in that one.

        timer.schedule(tick, 0, time.getDeltaTime());
        timer.schedule(tenSecTask, Time.secInMs * 10, Time.secInMs * 10);
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


    public ArrayList<MilitaryUnit>[] createEnemyArmy() {
        ArrayList<MilitaryUnit>[] army = new ArrayList[7];
        // Create the army
        
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
        this.deltaTime = 1000/50;
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
