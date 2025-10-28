import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Deer cross the road and also make noise occasionally
 */

public class Deer extends Pedestrian
{
    private GreenfootSound call;
    
    public Deer(int direction) {
        super(direction);  // call the Pedestrian constructor
        GreenfootImage img = new GreenfootImage("Deer.png");
        img.scale(120, 120);  // change to desired width and height
        setImage(img);
        call = new GreenfootSound("deerCall1.mp3");
    }

    @Override
    public void act() {
        super.act();  // reuse general pedestrian behavior
        int chanceToCall = Greenfoot.getRandomNumber(6000);
        if (chanceToCall == 0 && !call.isPlaying()){
            call.play();
        } 
    }
}
