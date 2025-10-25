import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.ArrayList;
/**
 * Write a description of class Explosion here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Explosion extends Effect
{
    private int myDiameter;
    
    public Explosion (){
        myDiameter = VehicleWorld.LANE_HEIGHT * 4;
        drawImage();
        actCount = 300;
        totalFadeTime = 5;
    }
    public void act()
    {
        super.act();
        
        if (getWorld() == null){
            return;
        }
            
        ArrayList<Pedestrian> peds = (ArrayList<Pedestrian>)getObjectsInRange(myDiameter/2, Pedestrian.class);
        for (Pedestrian p : peds){
            getWorld().removeObject(p);
        }
        
        ArrayList<Vehicle> vehicle = (ArrayList<Vehicle>)getObjectsInRange(myDiameter/2, Vehicle.class);
        for (Vehicle v : vehicle){
            if (v instanceof FireTruck){
                return;
            }
            getWorld().removeObject(v);
        }
    }
    
    public void drawImage(){
        image = new GreenfootImage (myDiameter, myDiameter);
        image.setColor (new Color (190, 40, 0));
        image.fillOval(0, 0, myDiameter-2, myDiameter -2);
        image.setTransparency (50);
        setImage(image);
    }
}
