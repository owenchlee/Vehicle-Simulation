import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * The smokers start each cycle by walking into the forest with their cigarette
 * This class also uses functionality from the gif class
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
        setImage(smokeMan.getCurrentImage()); //change image during acts to create animation
        smoke.play(); //plays smoking sound when the smoker is present
        super.act();
    }
}
