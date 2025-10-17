import greenfoot.*;

public class Water extends Actor {
    private Fire target;

    public Water(Fire target) {
        this.target = target;
        GreenfootImage img = new GreenfootImage("water.png");
        img.scale(200, 130);
        setImage(img);
    }

    public void act() {
        if (getWorld() == null) return;

        // check if the target fire still exists
        if (target == null || !getWorld().getObjects(Fire.class).contains(target)) {
            getWorld().removeObject(this);
            return;
        }

        // move toward the fire
        turnTowards(target.getX(), target.getY());
        move(6);

        // extinguish on contact
        if (intersects(target)) {
            getWorld().removeObject(target);
            getWorld().removeObject(this);
        }
    }
}
