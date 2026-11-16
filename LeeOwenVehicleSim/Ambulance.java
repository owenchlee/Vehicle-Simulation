import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * The ambulance heals pedestrians hit by cars
 */
public class Ambulance extends Vehicle
{
    private boolean lightsOn;
    private boolean resetRun; //true for the two ambulances added by VehicleWorld.resetLanes
    
    public Ambulance(VehicleSpawner origin){
        super(origin); // call the superclass' constructor first
        GreenfootImage img = new GreenfootImage("ambulance.png");
        img.scale(100, 80);  // change to desired width and height
        setImage(img);
        maxSpeed = 5;
        speed = maxSpeed;
        followingDistance = 8; // Ambulances need more space due to urgency
    }

    //reset ambulances must stay in their lane so both lanes get cleared
    public Ambulance stayInLane(){
        switchedLanes = true;
        resetRun = true;
        return this;
    }

    public void act()
    {
        World w = getWorld();
        super.act();
        //when the first reset ambulance leaves, the paramedics behind it heal anyone still down (e.g. stuck behind a slow vehicle)
        if (resetRun && getWorld() == null){
            for (Object p : w.getObjects(Pedestrian.class)){
                if (!((Pedestrian)p).isAwake()){
                    ((Pedestrian)p).healMe();
                }
            }
        }
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
