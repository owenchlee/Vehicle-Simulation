import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

public class Flames extends Actor
{
    private int yLimit;

    private int totalFlames = 50;    // total flames to spawn
    private int createdFlames = 0;   // flames spawned so far
    private int timer = 0;           // counts act() cycles
    private int interval = 150;       // starting interval between spawns
    private int minInterval = 20;     // minimum interval
    private double decay = 0.95;     // interval decreases by 5% each spawn
    private boolean firstSpawnDone = false; // track instant first spawn

    public Flames(int yLimit)
    {
        this.yLimit = yLimit;
    }

    @Override
    public void act()
    {
        World world = getWorld();
        if (world == null) return;

        // spawn the first flame instantly
        if (!firstSpawnDone) {
            spawnFire();
            firstSpawnDone = true;
            return; // wait next act cycle for timer
        }

        if (createdFlames >= totalFlames) {
            return; // stop when all flames spawned
        }

        timer++;
        if (timer >= interval) {
            spawnFire();
            timer = 0;

            // decrease interval so fire spreads faster
            interval = (int)Math.max(minInterval, interval * decay);
        }
    }

    private void spawnFire()
    {
        World world = getWorld();
        if (world == null) return;

        int x = Greenfoot.getRandomNumber(world.getWidth());
        int y = Greenfoot.getRandomNumber(yLimit - 10 + 1) + 10;
        world.addObject(new Fire(), x, y);

        createdFlames++;
    }
}
