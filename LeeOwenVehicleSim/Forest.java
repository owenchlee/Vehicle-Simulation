import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
/**
 * The forest class adds all the trees into the world when the program begins
 */
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
        //check if the world exists yet
        World world = getWorld();
        if (world == null) {
            return;  
        }
        
        if (hasCreated){
            return;
        }
        
        hasCreated = true;

        int numTrees = 40;
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
