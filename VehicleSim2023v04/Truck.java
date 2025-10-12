import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * The Truck subclass represents a larget passenger vehicle.
 * trucks have moderate speed and will knock down pedestrians when they collide.
 *
 * @author Jordan Cohen
 * @version 2023
 */
public class Truck extends Vehicle
{
    
    public Truck(VehicleSpawner origin) {
        super(origin); // call the superclass' constructor
        GreenfootImage img = new GreenfootImage("towTruck.png");
        
        img.scale(170, 110);  // change to desired width and height
        setImage(img);
        maxSpeed = 1 + ((Math.random() * 30)/5);
        speed = maxSpeed;
        yOffset = 4;
        followingDistance = 6;
    }

    public void act()
    {
        super.act();
        // make car different - put code here
    }

    /**
     * When a Truck hit's a Pedestrian, it should knock it over
     */
    public boolean checkHitPedestrian () {
        Pedestrian p = (Pedestrian)getOneObjectAtOffset((int)speed + getImage().getWidth()/2, 0, Pedestrian.class);
        if (p != null)
        {
            p.knockDown();
            return true;
        }
        return false;
        
    }
}
