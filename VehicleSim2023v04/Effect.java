import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class Effect here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Effect extends SuperSmoothMover
{
    protected int actCount;
    protected int totalFadeTime;
    protected GreenfootImage image;
    
    protected void fadeOut (int timeLeft, int totalFadeTime){
        double percent = timeLeft / (double)totalFadeTime;
        if (percent > 1.00) return;
        int newTransparency = (int)(percent * 255);
        image.setTransparency (newTransparency);
    }
    
    protected void fadeIn (int timeLeft, int totalFadeTime){
        double percent = timeLeft / (double)totalFadeTime;
        if (percent > 1.00) return;
        int newTransparency = (int)(percent * 255);
        image.setTransparency (newTransparency);
    }

    /**
     * Act - do whatever the Effect wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    public void act()
    {
        actCount--;
        fadeOut(actCount, totalFadeTime);
        // System.out.println(getX()); // for testing
        if (actCount == 0){
            getWorld().removeObject(this);
            return;
        }
    }
}
