    import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

public class Fire extends Actor
{
    private GreenfootImage[] fireFrames = new GreenfootImage[11];
    private int frame = 0;
    private int animationCounter = 0;
    private final int ANIMATION_SPEED = 5;
    int fireSize = Greenfoot.getRandomNumber(5); 
    
    public Fire() {
        for (int i = 0; i < fireFrames.length; i++) {
            fireFrames[i] = new GreenfootImage("Fire_" + i + ".png");
            if (fireSize == 0) {
                GreenfootImage img = new GreenfootImage("fire2.png");
                fireFrames[i].scale(30, 50);
            } else if (fireSize == 1) {
                GreenfootImage img = new GreenfootImage("fire2.png");
                fireFrames[i].scale(50, 70);
            }else if (fireSize == 2){
                GreenfootImage img = new GreenfootImage("fire3.png");
                fireFrames[i].scale(70, 90);
            }else if (fireSize == 3){
                GreenfootImage img = new GreenfootImage("fire4.png");
                fireFrames[i].scale(80, 150);
            }
            else {
                GreenfootImage img = new GreenfootImage("fire5.png");
                fireFrames[i].scale(180, 150);
            }
        }
        setImage(fireFrames[0]);


        
    }
    
    public void act(){
        animateFire();
    }
    
    private void animateFire() {
        animationCounter++;
        if (animationCounter >= ANIMATION_SPEED) {
            frame = (frame + 1) % fireFrames.length;
            setImage(fireFrames[frame]);
            animationCounter = 0;
        }
    }
}
