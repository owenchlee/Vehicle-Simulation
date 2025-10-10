import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

public class Forest extends Actor
{
    private int yLimit;
    private boolean hasCreated = false;

    public Forest(int yLimit)
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

        int numTrees = 15;
        for (int i = 0; i < numTrees; i++)
        {
            int x = Greenfoot.getRandomNumber(world.getWidth());
            int y = Greenfoot.getRandomNumber(yLimit -10 + 1) + 10;
            world.addObject(new Tree(), x, y);
        }
    }

    @Override
    protected void addedToWorld(World world)
    {
        createForest();  // Called once this Forest has been added to the world
    }
}
