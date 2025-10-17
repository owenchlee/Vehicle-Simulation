import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * The FireTruck subclass represents a large public transit vehicle.
 * Buses are slower than cars and taller, requiring a y-offset for proper positioning.
 * Currently does not interact with pedestrians (students can implement this).
 *
 * @author Jordan Cohen
 * @version 2023
 */
public class FireTruck extends Vehicle
{
    int waitTimer = 60;
    
    private boolean hasStopped = false;
    private boolean firefighterSpawned = false;
    public FireTruck(VehicleSpawner origin){
        super(origin); // call the superclass' constructor first
        GreenfootImage img = new GreenfootImage("fireTruck.png");
        img.mirrorHorizontally();
        img.scale(250, 120);  // change to desired width and height
        setImage(img);
        // Set up values for FireTruck
        maxSpeed = 1.5 + ((Math.random() * 10)/5);
        speed = maxSpeed;
        // because the FireTruck graphic is tall, offset it up (this may result in some collision check issues)
        yOffset = 15;
        followingDistance = 50; // Buses need more following distance due to size
    }

    /**
     * Act - do whatever the FireTruck wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    public void act()
    {
        if (getWorld() == null) {
            // FireTruck has been removed, skip actions
            return;
        }

        // Call Vehicle's movement and logic if not stopped
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
            getWorld().addObject(new Firefighter(), getX(), getY() + 50); // below the truck
            firefighterSpawned = true;
        }
    }
    
    /**
     * @return boolean true if a pedestrian was hit, false otherwise
     */
    public boolean checkHitPedestrian(){
        Pedestrian p = (Pedestrian) getOneIntersectingObject(Pedestrian.class);
        if (p != null) {
            return true;
        }
        return false;
    }
}
