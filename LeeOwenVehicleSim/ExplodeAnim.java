import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * animation that appears when a explosion happens
 * Uses the built in gif class to work
 */

public class ExplodeAnim extends Actor

{
    private int actCount = 200;
    
    public ExplodeAnim(){
        for (GreenfootImage frame : explode.getImages()) {
            frame.scale(VehicleWorld.LANE_HEIGHT * 5, VehicleWorld.LANE_HEIGHT * 5); // scale every frame in the GIF
        }
        
    }
    
    private GifImage explode = new GifImage("explode.gif");
    public void act()
    {
        actCount--;
    
        if (actCount == 0){
            getWorld().removeObject(this);
            return;
        }
            
        setImage(explode.getCurrentImage());
        super.act();
    }
}
