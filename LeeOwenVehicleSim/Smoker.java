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
        smoke = new GreenfootSound ("smoking.mp3");
        smoke.setVolume(30);
    }
    
    private static GifImage base;
    private GifImage smokeMan = copyOfBase(); // decode and scale once, share frames
    private static GifImage copyOfBase() {
        if (base == null) {
            base = new GifImage("smoker.gif");
            for (GreenfootImage frame : base.getImages()) frame.scale(80, 80);
        }
        return new GifImage(base);
    }
    public void act()
    {
        setImage(smokeMan.getCurrentImage()); //change image during acts to create animation
        smoke.play(); //plays smoking sound when the smoker is present
        super.act();
    }
}
