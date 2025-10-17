import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class Deer here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Deer extends Pedestrian
{
    public Deer(int direction) {
        super(direction);  // call the Pedestrian constructor
        GreenfootImage img = new GreenfootImage("Deer.png");
        img.scale(120, 120);  // change to desired width and height
        setImage(img);
    }

    @Override
    public void act() {
        super.act();  // reuse general pedestrian behavior
        // Additional behavior specific to Person, if any
    }
}
