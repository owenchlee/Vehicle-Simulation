import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.ArrayList;
/**
 * Write a description of class Detector here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
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
    
    public boolean isEmpty(){
        ArrayList<Vehicle> vehicles = (ArrayList<Vehicle>) getIntersectingObjects(Vehicle.class);
        if (vehicles.isEmpty()){
            return true;
        }
        return false;
    }
}
