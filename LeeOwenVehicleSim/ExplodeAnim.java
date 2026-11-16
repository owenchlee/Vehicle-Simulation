import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * animation that appears when a explosion happens
 * Uses the built in gif class to work
 */

public class ExplodeAnim extends Actor

{
    private int actCount = 200;
    
    public ExplodeAnim(){
    }

    private static GifImage base;
    private GifImage explode = copyOfBase(); // decode and scale once, share frames
    private static GifImage copyOfBase() {
        if (base == null) {
            base = new GifImage("explode.gif");
            for (GreenfootImage frame : base.getImages()) frame.scale(VehicleWorld.LANE_HEIGHT * 5, VehicleWorld.LANE_HEIGHT * 5);
        }
        return new GifImage(base);
    }
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
