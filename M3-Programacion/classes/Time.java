package classes;

public class Time {
    private int miliseconds;
    private int seconds;
    private int minutes;
    private int totalSeconds;
    private int deltaTime;

    public static int secInMs = 1000;
    public static int countdownBattleTime = secInMs * 20;
    public static int timeBetweenBattles = secInMs * 10;
    public static int timeBetweenResources = secInMs * 10;


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

    