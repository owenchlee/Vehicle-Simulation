import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.ArrayList;

/**
 * The truck is a nice vehicle that saves all the monkeys it encounters
 */
public class Truck extends Vehicle
{
    private int pauseCount = 0;
    
    public Truck(VehicleSpawner origin) {
        super(origin); // call the superclass' constructor
        GreenfootImage img = new GreenfootImage("towTruck.png");
        
        img.scale(170, 110);  // change to desired width and height
        setImage(img);
        maxSpeed = 1.5 + ((Math.random() * 10)/5);
        speed = maxSpeed;
        yOffset = 4;
        followingDistance = 6;
    }

    public void act()
    {
        if (getWorld() == null){
            return;
        }
        
        if (pauseCount > 0){
            pauseCount --;
            speed = 0;
            return;
        } else{
            speed = maxSpeed;
            moving = true;
        }
        
        super.act();
        // make car different - put code here
    }

    /**
     * When a Truck hit's a Pedestrian, it should knock it over
     */
    public boolean checkHitPedestrian () {
        //saves the monkey whether they are alive or ran over by a car
        ArrayList<Monkey> monkeys = (ArrayList<Monkey>) getIntersectingObjects(Monkey.class);
        
        if (monkeys != null && !monkeys.isEmpty()){
            for (Monkey m : monkeys){
                getWorld().removeObject(m);
            }
            
            pauseCount = 60;
            speed = 0;
            moving = false;
            return true;
        }
        
        //if no monkeys it acts same as the car and drives forwards
        int frontX = (int)speed + getImage().getWidth()/2;
        int heightSpacing = (getImage().getHeight() / 2) - 25;
        
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
