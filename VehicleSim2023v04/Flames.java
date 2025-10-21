import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

public class Flames extends Actor
{
    private int yLimit;

    private int totalFlames = 50;    // total flames to spawn
    private int createdFlames = 0;   // flames spawned so far
    private int timer = 0;           // counts act() cycles
    private int interval = 150;      // starting interval between spawns
    private int minInterval = 20;    // minimum interval
    private double decay = 0.95;     // interval decreases by 5% each spawn
    private boolean firstSpawnDone = false; // track instant first spawn
    private int originX = -1;        // original X of the first flame
    private int spreadRange = 150;    // how far flames can spread horizontally from origin
    private static boolean fireDone = false;

    public Flames(int yLimit)
    {
        this.yLimit = yLimit;
    }
    
    public void start(){
        fireDone = false;
    }
    
    @Override
    public void act()
    {
        World world = getWorld();
        if (world == null) return;

        // spawn the first flame instantly
        if (!firstSpawnDone) {
            spawnFire();
            fireDone = false;
            firstSpawnDone = true;
            return; // wait next act cycle for timer
        }
        
        if (world.getObjects(Fire.class).isEmpty()) {
            fireDone = true;
            return;
        }
        
        if (createdFlames >= totalFlames) {
            fireDone = true;
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
    
    public static boolean getFireStatus(){
        return fireDone;
    }

    private void spawnFire()
    {
        World world = getWorld();
        if (world == null) return;

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

        y = Greenfoot.getRandomNumber(yLimit - 10 + 1) + 10;
        world.addObject(new Fire(), x, y);
        originX = x;
        createdFlames++;
    }
}
