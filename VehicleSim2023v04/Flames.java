import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

public class Flames extends Actor
{
    private int yLimit;
    private boolean hasCreated = false;

    public Flames(int yLimit)
    {
        this.yLimit = yLimit;
    }

    private void createForest()
    {
        World world = getWorld();
        if (world == null) {
            return;  // Not yet added to world, cannot add trees
        }
        
        if (hasCreated){
            return;
        }
        
        hasCreated = true;

        int numFire = 15;
        for (int i = 0; i < numFire; i++)
        {
            int x = Greenfoot.getRandomNumber(world.getWidth());
            int y = Greenfoot.getRandomNumber(yLimit -10 + 1) + 10;
            world.addObject(new Tree(), x, y);
        }
    }

    @Override
    protected void addedToWorld(World world)
    {
        createForest();  // Called once this Flames has been added to the world
    }
}
