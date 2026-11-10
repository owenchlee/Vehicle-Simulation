import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Monkeys cross the road and 1 in 10 are supermonkeys that walk faster
 */
public class Monkey extends Pedestrian
{
    public Monkey(int direction) {
        super(direction);  // call the Pedestrian constructor
        GreenfootImage img = new GreenfootImage("Monkey.png");
        img.scale(50, 50);  // change to desired width and height
        setImage(img);
    }

    // decided once: 1 in 10 monkeys are supermonkeys with a fixed 2x speed bonus
    private final double bonus = (Greenfoot.getRandomNumber(10) == 0) ? 2 : 1;

    {
        speed = maxSpeed * bonus;
    }

    @Override
    public void setSpeedMultiplier(double multiplier) {
        super.setSpeedMultiplier(multiplier * bonus);
    }

    @Override
    public void healMe() {
        super.healMe();
        speed = maxSpeed * bonus;
    }
}
