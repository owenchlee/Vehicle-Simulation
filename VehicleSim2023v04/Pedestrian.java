import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * A Pedestrian that tries to walk across the street.
 * Pedestrians move vertically across lanes, avoiding vehicles when possible.
 * They can be knocked down by vehicles and later healed.
 *
 * NOTE: This class is designed to be converted into a superclass for different
 * types of pedestrians (e.g., Person, Animal, etc.) as part of the student assignment.
 *
 * @author Jordan Cohen
 * @version 2023
 */
public abstract class Pedestrian extends SuperSmoothMover
{
    protected double speed;
    protected double maxSpeed;
    protected int direction; // direction is always -1 or 1, for moving down or up, respectively
    protected boolean awake;
    protected boolean isPicked = false;
    public Pedestrian(int direction) {
        // choose a random speed
        maxSpeed = Math.random() * 2 + 1;
        speed = maxSpeed;
        // start as awake
        awake = true;
        this.direction = direction;
    }

    /**
     * Act - do whatever the Pedestrian wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    public void act()
    {
        // Awake is false if the Pedestrian is "knocked down"
        if (awake){
            // Check in the direction I'm moving vertically for a Vehicle -- and only move if there is no Vehicle in front of me.
            if (getOneObjectAtOffset(0, (int)(direction * getImage().getHeight()/2 + (int)(direction * speed)), Vehicle.class) == null){
                setLocation (getX(), getY() + (speed*direction));
            }
            if (direction == -1 && getY() < 200){
                getWorld().removeObject(this);
            } else if (direction == 1 && getY() > getWorld().getHeight() - 30){
                getWorld().removeObject(this);
            }

        }
    }
    
    public void setSpeedMultiplier(double multiplier) {
        speed = maxSpeed * multiplier;
    }
    
    /**
     * Method to cause this Pedestrian to become knocked down - stop moving, turn onto side
     */
    public void knockDown () {
        speed = 0;
        setRotation (direction * 90);
        awake = false;
    }

    public void setPicked(){
        isPicked = true;
    }
    
    public boolean getPicked(){
        return isPicked;
    }

    /**
     * Method to allow a downed Pedestrian to be healed
     */
    
    public void healMe () {
        speed = maxSpeed;
        setRotation (0);
        awake = true;
    }

    public boolean isAwake () {
        return awake;
    }
}
