import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.ArrayList;

/**
 * This is the world wide effect that appears when the world is on fire
 */

public class Smoke extends Effect
{
    private ArrayList<Pedestrian> pedestrians;
    
    private boolean firstAct;
    private int position, direction, duration;
    
    private double speed;
    private final int LOWEST_POSITION = -512;
    private final int HIGHEST_POSITION = 512;
    
    public Smoke () {
        drawimage();
        actCount = 240;
        fadeTime = 90;
        firstAct = true;
        position = 0;
        direction = 1;
        duration = 50;
        speed = 1.5;
    }

    public void act () {
        VehicleWorld world = (VehicleWorld) getWorld();
        
        if (!world.isOnFire()){
            super.act();
        }
        
        if (getWorld() == null){
            return;
        }
        
        // Move around back and forth
        if (duration > 0){
            setLocation (getPreciseX() + (speed * direction), getPreciseY());
            duration--;
        } else { //duration has run out
            direction *= -1;
            duration = 50;
        }
        
        //speed up pedestrians
        ArrayList<Pedestrian> peds = (ArrayList<Pedestrian>)getIntersectingObjects( Pedestrian.class);
        for (Pedestrian p : peds){
            p.setSpeedMultiplier(1.5);
        }
        
        //slow down vehicles
        ArrayList<Vehicle> vehicle = (ArrayList<Vehicle>)getIntersectingObjects(Vehicle.class);
        for (Vehicle v : vehicle){
            if (v instanceof FireTruck){
                return;
            }
            v.setSpeedMultiplier(0.8);
        }
    }

    private void drawimage() {
        image = new GreenfootImage(2048, 800);
        image.setColor(new Color(80, 80, 80, 40)); // gray background
        image.fill();
    
        // Create smoke blobs effect
        image.setColor(new Color(80, 80, 80, 40));
        for (int i = 0; i < 3000; i++) { // fewer, larger wisps than rain
            int randX = Greenfoot.getRandomNumber(image.getWidth());
            int randY = Greenfoot.getRandomNumber(image.getHeight());
            int randSize = 30 + Greenfoot.getRandomNumber(20); 
            image.fillOval(randX, randY, randSize, randSize);
        }
    
        setImage(image);
    }

}
