import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class Monkey here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
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
        // Additional behavior specific to Person, if any
    }
}
