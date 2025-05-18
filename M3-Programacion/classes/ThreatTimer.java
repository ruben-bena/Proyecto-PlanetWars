package classes;

import java.util.Timer;
import java.util.TimerTask;

import GUI.AudioPlayer;
import GUI.MainScreen;

public class ThreatTimer extends TimerTask{
    private Planet planet;
    private MainScreen ms;
    private Timer timer;
    public ThreatTimer(Planet planet, MainScreen ms) {
        this.planet = planet;
        this.ms = ms;
        timer = new Timer();
        timer.schedule(this, Time.timeBetweenBattles);
    }

    public ThreatTimer(Planet planet, MainScreen ms, int i) {
        this.planet = planet;
        this.ms = ms;
        timer = new Timer();
        timer.schedule(this, 0);
    }


    public void run() {
                if(planet.getNTroops() > 0) { // The battle only happens if planet has more than 0 troops, otherwise there's a division by 0
                    if(planet.getCurrentThreat() == null) {
                        planet.setCurrentThreat(new Battle(planet, Main.createEnemyPlanet(planet), ms.getMainPanel(), ms));
                        planet.setActiveThreat(true);
                        ms.getMainPanel().getMiddlePanel().doThreatDisplay();
                        AudioPlayer.doAlarm();
                    }
                } else {
                    new ThreatTimer(planet, ms);
                }

                
            }
}
