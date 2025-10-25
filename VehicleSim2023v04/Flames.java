import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

public class Flames extends Actor
{
    private int yLimit;
    private boolean firstFire = false;
    private int totalFlames = 60;    // total flames to spawn
    private int createdFlames = 0;   // flames spawned so far
    private int actCount = 0;           // counts act() cycles
    private int interval = 180;      // starting interval between spawns
    private int minInterval = 15;    // minimum interval
    private double decay = 0.95;     // interval decreases by 5% each spawn
    private int originX = -1;        // original X of the first flame
    private int spreadRange = 150;    // how far flames can spread horizontally from origin

    public Flames(int yLimit)
    {
        this.yLimit = yLimit;
    }
    
    @Override
    public void act()
    {   
        actCount++;
        VehicleWorld world = (VehicleWorld) getWorld();
        
        if (world == null){
            return;
        }
        
        if (!firstFire && actCount % 120 == 0){
            spawnFire();
            firstFire = true;
            actCount = 0;
        }
        
        if (!world.isOnFire()){
            return;
        }
        
        if (createdFlames >= totalFlames) {
            return; // stop when all flames spawned
        }

        if (actCount >= interval) {
            spawnFire();
            actCount = 0;

            // decrease interval so fire spreads faster
            interval = (int)Math.max(minInterval, interval * decay);
        }
    }

    private void spawnFire()
    {
        World world = getWorld();
        if (world == null) {
            return;
        }

        int x, y;
        if (originX == -1) {
            // first flame
            x = Greenfoot.getRandomNumber(world.getWidth());
            originX = x;
        } else {
            // subsequent flames spread around originX
            x = originX + Greenfoot.getRandomNumber(spreadRange * 2 + 1) - spreadRange;
            x = Math.max(0, Math.min(world.getWidth(), x)); // keep within world bounds
        }

        y = Greenfoot.getRandomNumber(yLimit - 8) + 10;
        world.addObject(new Fire(), x, y);
        originX = x;
        createdFlames++;
    }
}
