import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.ArrayList;
/**
 * The detector is instantiated to check for whether or not a lane is occupied
 */

public class Detector extends Actor
{
    private GreenfootImage image;
    
    public Detector(int width, int height){
        GreenfootImage img = new GreenfootImage(width,height);
        img.setColor(new Color(225,0,0,20));
        img.fill();
        setImage(img);
        
    }
    
    //checks for vehicle in the detector
    public boolean isEmpty(){
        ArrayList<Vehicle> vehicles = (ArrayList<Vehicle>) getIntersectingObjects(Vehicle.class);
        if (vehicles.isEmpty()){
            return true;
        }
        return false;
    }
}
