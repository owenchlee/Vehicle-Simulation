import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * The Bus subclass represents a large public transit vehicle.
 * Buses are slower than cars and taller, requiring a y-offset for proper positioning.
 * Currently does not interact with pedestrians (students can implement this).
 *
 * @author Jordan Cohen
 * @version 2023
 */
public class Bus extends Vehicle
{
    int waitTimer = 60;
    private Pedestrian target = null;
    public Bus(VehicleSpawner origin){
        super(origin); // call the superclass' constructor first

        // Set up values for Bus
        maxSpeed = 1.5 + ((Math.random() * 10)/5);
        speed = maxSpeed;
        // because the Bus graphic is tall, offset it up (this may result in some collision check issues)
        yOffset = 15;
        followingDistance = 50; // Buses need more following distance due to size
    }

    /**
     * Act - do whatever the Bus wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    public void act()
    {
       if (getWorld() == null) {
        // Bus has been removed, skip actions
            return;
       }
       super.act();
       checkHitPedestrian();
    }

    /**
     * Check if this Bus hits a Pedestrian. This method is intentionally left empty
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
