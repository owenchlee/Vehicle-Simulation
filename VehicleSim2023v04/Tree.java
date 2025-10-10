import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

public class Tree extends Actor
{
    public Tree() {
        int treeType = Greenfoot.getRandomNumber(3); // 0, 1, or 2

        if (treeType == 0) {
            GreenfootImage img = new GreenfootImage("smallTree.png");
            img.scale(140, 300);  // change to desired width and height
            setImage(img);
        } else if (treeType == 1) {
            GreenfootImage img = new GreenfootImage("medTree.png");
            img.scale(170, 300);  // change to desired width and height
            setImage(img);
        } else {
            GreenfootImage img = new GreenfootImage("largeTree.png");
            img.scale(190, 300);  // change to desired width and height
            setImage(img);
        }
    }
}
