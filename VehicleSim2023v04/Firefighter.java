import greenfoot.*;
import java.util.ArrayList;

public class Firefighter extends Actor {
    private boolean flipped = false;
    private int shootCooldown = 0; // delay between shots

    public Firefighter() {
        GreenfootImage img = new GreenfootImage("Firefighter.png");
        img.scale(160, 200);
        setImage(img);
    }

    public void act() {
        if (getWorld() == null) return;

        ArrayList<Fire> fires = (ArrayList<Fire>) getWorld().getObjects(Fire.class);

        // stop if no fires remain
        if (fires.isEmpty()) return;

        Fire nearestFire = getNearestFire(fires);
        if (nearestFire == null) return;

        faceFire(nearestFire);

        // shoot every 30 frames
        if (shootCooldown <= 0) {
            shootWater(nearestFire);
            shootCooldown = 15; // adjust for faster/slower shooting
        } else {
            shootCooldown--;
        }
    }

    private Fire getNearestFire(ArrayList<Fire> fires) {
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

    private double getDistanceTo(Actor other) {
        int dx = other.getX() - getX();
        int dy = other.getY() - getY();
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
        getWorld().addObject(new Water(fire), getX(), getY() - 60);
    }
}
