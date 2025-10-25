import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class Smoker here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class ExplodeAnim extends Actor

{
    private int actCount = 300;
    private int totalFadeTime = 5;
    
    public ExplodeAnim(){
        for (GreenfootImage frame : explode.getImages()) {
            frame.scale(VehicleWorld.LANE_HEIGHT * 4, VehicleWorld.LANE_HEIGHT * 4); // scale every frame in the GIF
        }
        
    }
    
    private GifImage explode = new GifImage("explode.gif");
    public void act()
    {
        actCount--;
        // System.out.println(getX()); // for testing
        if (actCount == 0){
            getWorld().removeObject(this);
            return;
        }
            
        setImage(explode.getCurrentImage());
        super.act();
    }
}
