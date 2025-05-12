package classes;

import java.util.Timer;
import java.util.TimerTask;

public class ResourceTimer extends TimerTask {
    private Planet planet;
    private Timer timer;
    public ResourceTimer(Planet planet) {
        this.planet = planet;
        timer = new Timer();
        timer.schedule(this, Time.timeBetweenResources, Time.timeBetweenResources);
    }

    @Override
    public void run() {
        planet.setMetal(planet.getMetal() + 2000 * planet.getMetalMineLvl());
        planet.setDeuterium(planet.getDeuterium() + 800 * planet.getDeuteriumMineLvl());

    }
    
}
