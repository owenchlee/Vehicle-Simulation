    import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

public class Fire extends Actor
{
    public Fire() {
        int treeType = Greenfoot.getRandomNumber(3); // 0, 1, or 2

        if (treeType == 0) {
            GreenfootImage img = new GreenfootImage("smallFire.png");
            img.scale(140, 300);  // change to desired width and height
            setImage(img);
        } else if (treeType == 1) {
            GreenfootImage img = new GreenfootImage("medFire.png");
            img.scale(170, 300);  // change to desired width and height
            setImage(img);
        } else {
            GreenfootImage img = new GreenfootImage("largeFire.png");
            img.scale(190, 300);  // change to desired width and height
            setImage(img);
        }
    }
}
