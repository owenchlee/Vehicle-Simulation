import greenfoot.*;

public class Water extends Actor {
    private int life = 30; // how long the water lasts

    public Water() {
        GreenfootImage img = new GreenfootImage("water.png");
        img.scale(50, 50);
        setImage(img);
    }

    public void act() {
        life--;
        if (life <= 0) {
            getWorld().removeObject(this);
        }
    }
}
