import greenfoot.*;  
import java.util.ArrayList;

/**
 * The firetruck only appears when there is a fire and stops in the middle to allow the firefighter to take out the fire
 */

public class FireTruck extends Vehicle {
    int waitTimer = 60;
    private boolean hasStopped = false;
    private boolean stoppedBefore = false;
    private boolean firefighterSpawned = false;
    private ArrayList<FireTruck> firetrucks = new ArrayList<>();

    public FireTruck(VehicleSpawner origin) {
        super(origin);
        GreenfootImage img = new GreenfootImage("fireTruck.png");
        img.mirrorHorizontally();
        img.scale(250, 120);
        setImage(img);
        maxSpeed = 7;
        speed = maxSpeed;
        yOffset = 15;
        followingDistance = 50;
        
    }

    public void act() {
        if (getWorld() == null) return;
        
        if (!hasStopped) {
            super.act();
            if (!stoppedBefore){
                checkStopPosition();
            }
        } else {
            stoppedBefore = true;
            spawnFirefighter();
        }
    }
    
    //stops near the middle
    private void checkStopPosition() {
        World world = getWorld();
        if (world != null && getX() <= world.getWidth() / 2) {
            hasStopped = true;
            speed = 0;
        }
    }
    
    //Create firefighter and uses math to find out where to spawn him so that he is nearest to fire
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

    //Calculate distance to another actor
    private double getDistanceTo(Actor other) {
        int dx = other.getX() - getX();
        int dy = other.getY() - getY();
        return Math.sqrt(dx * dx + dy * dy);
    }

    //firetruck doesnt known down pedestrians
    public boolean checkHitPedestrian() {
        int frontX = (int)speed + getImage().getWidth()/2;
        int heightSpacing = (getImage().getHeight() / 2) - 5;
    
        Pedestrian pCenter = (Pedestrian)getOneObjectAtOffset(frontX, 0, Pedestrian.class);
        if (pCenter != null && pCenter.isAwake()) {
            return true;
        }
    
        Pedestrian pTop = (Pedestrian)getOneObjectAtOffset(frontX, -heightSpacing, Pedestrian.class);
        if (pTop != null && pTop.isAwake()) {
            return true;
        }
    
        Pedestrian pBottom = (Pedestrian)getOneObjectAtOffset(frontX, heightSpacing, Pedestrian.class);
        if (pBottom != null && pBottom.isAwake()) {
            return true;
        }
    
        return false;
    }
    
    //When reset it just drives again until it gets removed from the world
    public void reset(){
        hasStopped = false;
        speed = maxSpeed;
    }
}
