import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * The ambulance heals pedestrians hit by cars
 */
public class Ambulance extends Vehicle
{
    private boolean lightsOn;
    
    public Ambulance(VehicleSpawner origin){
        super(origin); // call the superclass' constructor first
        GreenfootImage img = new GreenfootImage("ambulance.png");
        img.scale(100, 80);  // change to desired width and height
        setImage(img);
        maxSpeed = 5;
        speed = maxSpeed;
        followingDistance = 8; // Ambulances need more space due to urgency
    }

    public void act()
    {
        super.act();
    }

    //check if a pedestrian is hit to heal them
    public boolean checkHitPedestrian () {
        int frontX = (int)speed + getImage().getWidth()/2;
        int heightSpacing = (getImage().getHeight() / 2) - 5;
    
        //three point collision check
        Pedestrian pCenter = (Pedestrian)getOneObjectAtOffset(frontX, 0, Pedestrian.class);
        if (pCenter != null) {
            pCenter.healMe();
            return true;
        }
    
        Pedestrian pTop = (Pedestrian)getOneObjectAtOffset(frontX, -heightSpacing, Pedestrian.class);
        if (pTop != null) {
            pTop.healMe();
            return true;
        }
    
        Pedestrian pBottom = (Pedestrian)getOneObjectAtOffset(frontX, heightSpacing, Pedestrian.class);
        if (pBottom != null) {
            pBottom.healMe();
            return true;
        }
    
        return false;
    }
}
