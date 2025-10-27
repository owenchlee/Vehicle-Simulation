import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class Smoker here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Smoker extends Pedestrian
{
    private GreenfootSound smoke;
    
    public Smoker (int direction){
        super(direction);
        for (GreenfootImage frame : smokeMan.getImages()) {
            frame.scale(80, 80); // scale every frame in the GIF
        }
        smoke = new GreenfootSound ("smoking.mp3");
        smoke.setVolume(30);
    }
    
    private GifImage smokeMan = new GifImage("smoker.gif");
    public void act()
    {
        setImage(smokeMan.getCurrentImage());
        smoke.play();
        super.act();
    }
}
