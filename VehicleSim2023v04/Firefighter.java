import greenfoot.*;
import java.util.ArrayList;

public class Firefighter extends Actor {
    private boolean extinguished = false;
    private boolean flipped = false;  // track whether image has been flipped
    private boolean hasShot = false;
    
    public Firefighter(){
        GreenfootImage img = new GreenfootImage("Firefighter.png");
        img.scale(80, 100); // adjust size to match your world
        setImage(img);
    }
    public void act() {
        if (!hasShot) {
            Fire nearestFire = getNearestFire();
            if (nearestFire != null) {
                faceFire(nearestFire);
                shootWater(nearestFire);
            }
        }
    }

    private Fire getNearestFire() {
        ArrayList<Fire> fires = (ArrayList<Fire>) getWorld().getObjects(Fire.class);
        if (fires.isEmpty()) return null;

        Fire nearest = fires.get(0);
        double nearestDistance = getDistanceTo(nearest);

        for (Fire fire : fires) {
            double distance = getDistanceTo(fire);
            if (distance < nearestDistance) {
                nearest = fire;
                nearestDistance = distance;
            }
        }

        return nearest;
    }
    
    private double getDistanceTo(Fire fire) {
        int dx = fire.getX() - getX();
        int dy = fire.getY() - getY();
        return Math.sqrt(dx * dx + dy * dy);
    }
    
    private void flipImage() {
        GreenfootImage img = getImage();
        img.mirrorHorizontally();
        setImage(img);
    }

    private void faceFire(Fire fire) {
        if (fire.getX() < getX() && !flipped) {
            flipImage();
            flipped = true;
        } else if (fire.getX() > getX() && flipped) {
            flipImage();
            flipped = false;
        }
    }
    
    private void shootWater(Fire fire) {
        // Create a simple water animation or effect
        getWorld().addObject(new Water(), getX(), getY() - 40); // above firefighter
        Greenfoot.delay(30); // wait a short time before putting out fire
        getWorld().removeObject(fire); // remove the fire
        hasShot = true;
    }
}
