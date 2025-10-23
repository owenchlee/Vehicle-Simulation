import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

public class Tree extends Actor
{
    private boolean onFire = false;
    private int fireTimer = 0;
    private int actCount = 0;
    private GreenfootImage burned;
    private GreenfootImage normal;
    
    public Tree() {
        int treeType = Greenfoot.getRandomNumber(3); // 0, 1, or 2

        if (treeType == 0) {
            normal = new GreenfootImage("smallTree.png");
            burned = new GreenfootImage("smallNoLeaves.png");
            normal.scale(140, 300);  // change to desired width and height
            burned.scale(140, 300); 
            setImage(normal);
        } else if (treeType == 1) {
            normal = new GreenfootImage("medTree.png");
            burned = new GreenfootImage("medNoLeaves.png");
            normal.scale(170, 300);  // change to desired width and height
            burned.scale(170, 300); 
            setImage(normal);
        } else {
            normal = new GreenfootImage("largeTree.png");
            burned = new GreenfootImage("largeNoLeaves.png");
            normal.scale(190, 300);  // change to desired width and height
            burned.scale(190, 300); 
            setImage(normal);
        }
    }
    
    public void act()
    {
        actCount++;
        Fire fire = (Fire) getOneIntersectingObject(Fire.class);
        // check if tree is on fire
        if (onFire && fire != null)
        {
            fireTimer++;

            // after ~3 seconds (assuming 60 act cycles per second)
            if (fireTimer >= 180) 
            {
                int chanceForNoLeaves = Greenfoot.getRandomNumber(5);
                if (chanceForNoLeaves == 0){
                    setImage(burned); 
                } else{
                    fireTimer = 0;
                }
                
            }
        }
        else
        {
            if (fire != null)
            {
                onFire = true;
                fireTimer = 0;
            }
        }
    }
    
    public void reset(){
        int chanceForLeaves = Greenfoot.getRandomNumber(1000);
        if (chanceForLeaves == 0){
            setImage(normal);
        }
        
    }
    
}
