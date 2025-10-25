import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * The Ambulance subclass represents an emergency medical vehicle.
 * Ambulances are faster than regular cars and have the potential for special behaviors
 * like emergency lights and healing pedestrians (students can implement these features).
 *
 * @author Jordan Cohen
 * @version 2023
 */
public class Ambulance extends Vehicle
{
    private boolean lightsOn;
    
    public Ambulance(VehicleSpawner origin){
        super(origin); // call the superclass' constructor first
        GreenfootImage img = new GreenfootImage("ambulance.png");
        img.scale(100, 80);  // change to desired width and height
        setImage(img);
        maxSpeed = 2.5;
        speed = maxSpeed;
        followingDistance = 8; // Ambulances need more space due to urgency
    }

    /**
     * Act - do whatever the Ambulance wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    public void act()
    {
        super.act();
        
    }

    /**
     * Check if this Ambulance hits a Pedestrian. This method is intentionally left empty
     * for students to implement their own pedestrian interaction logic.
     *
     * @return boolean true if a pedestrian was hit, false otherwise
     */
    public boolean checkHitPedestrian () {
        // TODO: Students should implement Ambulance-specific pedestrian interaction here
        // (Hint: Ambulances might heal pedestrians instead of harming them)
        int frontX = (int)speed + getImage().getWidth()/2;
        int heightSpacing = (getImage().getHeight() / 2) - 5;
    
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
