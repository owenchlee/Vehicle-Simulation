import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * The Car subclass represents a standard passenger vehicle.
 * Cars have moderate speed and will knock down pedestrians when they collide.
 *
 * @author Jordan Cohen
 * @version 2023
 */
public class Car extends Vehicle
{
    
    public Car(VehicleSpawner origin) {
        super(origin); // call the superclass' constructor
        maxSpeed = 1.5 + ((Math.random() * 50)/5);
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
     * When a Car hit's a Pedestrian, it should knock it over
     */
    public boolean checkHitPedestrian () {
        int frontX = (int)speed + getImage().getWidth()/2;
        int heightSpacing = (getImage().getHeight() / 2) - 15;
    
        Pedestrian pCenter = (Pedestrian)getOneObjectAtOffset(frontX, 0, Pedestrian.class);
        if (pCenter != null && pCenter.isAwake()) {
            pCenter.knockDown();
            checkHitDeer(pCenter);
            return true;
        }
    
        Pedestrian pTop = (Pedestrian)getOneObjectAtOffset(frontX, -heightSpacing, Pedestrian.class);
        if (pTop != null && pTop.isAwake()) {
            pTop.knockDown();
            checkHitDeer(pTop);
            return true;
        }
    
        Pedestrian pBottom = (Pedestrian)getOneObjectAtOffset(frontX, heightSpacing, Pedestrian.class);
        if (pBottom != null && pBottom.isAwake()) {
            pBottom.knockDown();
            checkHitDeer(pBottom);
            return true;
        }
    
        return false;
    }
}
