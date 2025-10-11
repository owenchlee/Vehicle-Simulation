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
    private Pedestrian target = null;
    public FireTruck(VehicleSpawner origin){
        super(origin); // call the superclass' constructor first

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
       super.act();
       checkHitPedestrian();
    }

    /**
     * Check if this FireTruck hits a Pedestrian. This method is intentionally left empty
     * for students to implement their own pedestrian interaction logic.
     *
     * @return boolean true if a pedestrian was hit, false otherwise
     */
    public boolean checkHitPedestrian () {
        if (getWorld() == null) {
            return false; // bus no longer in world, skip
        }

        // TODO: Students should implement Bus-specific pedestrian interaction here
        target = (Pedestrian) getOneIntersectingObject(Pedestrian.class);
        if (target != null && !(target.getPicked())&& target.isAwake()) {
            target.setPicked();
            moving = false;
            sleepFor(60);
            moving = true;
            getWorld().removeObject(target);
            return true;
        }
        return false;
    }
    
    public void stopMe () {
        moving = false;
        speed = 0;
    }
}
