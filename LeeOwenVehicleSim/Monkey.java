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

    @Override
    public void act() {
        super.act();  // reuse general pedestrian behavior
        int superMonkey = Greenfoot.getRandomNumber(5);
        if (superMonkey == 0){
            speed = speed * 2;
        }
    }
}
