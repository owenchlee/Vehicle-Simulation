import greenfoot.*;  
import java.util.ArrayList;

public class FireTruck extends Vehicle {
    int waitTimer = 60;
    private boolean hasStopped = false;
    private boolean firefighterSpawned = false;

    public FireTruck(VehicleSpawner origin) {
        super(origin);
        GreenfootImage img = new GreenfootImage("fireTruck.png");
        img.mirrorHorizontally();
        img.scale(250, 120);
        setImage(img);
        maxSpeed = 1.5 + ((Math.random() * 10) / 5);
        speed = maxSpeed;
        yOffset = 15;
        followingDistance = 50;
    }

    public void act() {
        if (getWorld() == null) return;

        if (!hasStopped) {
            super.act();
            checkStopPosition();
        } else {
            spawnFirefighter();
        }

        checkHitPedestrian();
    }

    private void checkStopPosition() {
        World world = getWorld();
        if (world != null && getX() <= world.getWidth() / 2) {
            hasStopped = true;
            speed = 0;
        }
    }

    private void spawnFirefighter() {
        if (!firefighterSpawned) {
            World world = getWorld();
            if (world == null) return;

            // find all fires
            ArrayList<Fire> fires = (ArrayList<Fire>) world.getObjects(Fire.class);
            if (fires.isEmpty()) return;

            // find nearest fire
            Fire nearestFire = fires.get(0);
            double nearestDist = getDistanceTo(nearestFire);
            for (Fire fire : fires) {
                double d = getDistanceTo(fire);
                if (d < nearestDist) {
                    nearestFire = fire;
                    nearestDist = d;
                }
            }

            int offsetX;
            if (nearestFire.getX() < getX()) {
                // fire is on the left
                offsetX = -200;
            } else {
                // fire is on the right
                offsetX = 200;
            }

            world.addObject(new Firefighter(), getX() + offsetX, getY());
            firefighterSpawned = true;
        }
    }

    /** Helper method to calculate distance to another actor */
    private double getDistanceTo(Actor other) {
        int dx = other.getX() - getX();
        int dy = other.getY() - getY();
        return Math.sqrt(dx * dx + dy * dy);
    }

    public boolean checkHitPedestrian() {
        Pedestrian p = (Pedestrian) getOneIntersectingObject(Pedestrian.class);
        return p != null;
    }
    
    public void reset(){
        speed = maxSpeed;
    }
}
