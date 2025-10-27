import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.Collections;
import java.util.ArrayList;
/**
 * 
 * Description
 * Works cited and known bugs
 * <h1>The new and vastly improved 2022 Vehicle Simulation Assignment.</h1>
 * <p> This is the first redo of the 8 year old project. Lanes are now drawn dynamically, allowing for
 *     much greater customization. Pedestrians can now move in two directions. The graphics are better
 *     and the interactions smoother.</p>
 * <p> The Pedestrians are not as dumb as before (they don't want straight into Vehicles) and the Vehicles
 *     do a somewhat better job detecting Pedestrians.</p>
 * 
 * Version Notes - Feb 2023
 * --> Includes grid <--> lane conversion method
 * --> Now starts with 1-way, 5 lane setup (easier)
 * 
 * V2023_021
 * --> Improved Vehicle Repel (still work in progress)
 * --> Implemented Z-sort, disabled paint order between Pedestrians and Vehicles (looks much better now)
 * --> Implemented lane-based speed modifiers for max speed
 * 
 * V2023_04
 * --> Repel has been re-imagined and now takes the sizes of Actors into consideration better, and also only
 *     moves Actors verically. (The code to move in both dimensions is there and works but it's commented out
 *     because this is the effect I was going for).
 * --> TODO -- Improve flow to avoid Removed From World errors when a Vehicle calls super.act() and is removed there.
 * 
 */
public class VehicleWorld extends World
{
    private GreenfootImage background;

    // Color Constants
    public static Color GREY_BORDER = new Color (108, 108, 108);
    public static Color GREY_STREET = new Color (88, 88, 88);
    public static Color YELLOW_LINE = new Color (255, 216, 0);

    public static boolean SHOW_SPAWNERS = false;
    
    // Set Y Positions for Pedestrians to spawn
    public static final int TOP_SPAWN = 190; // Pedestrians who spawn on top
    public static final int BOTTOM_SPAWN = 705; // Pedestrians who spawn on the bottom
    public static final int LANE_HEIGHT = 90;

    //set lane for only firetruck
    private static final int SPECIAL_LANE_INDEX = 0;
    
    
    // Instance variables / Objects
    private boolean twoWayTraffic, splitAtCenter;
    private int laneCount, spaceBetweenLanes;
    private int[] lanePositionsY;
    private VehicleSpawner[] laneSpawners;
    private boolean onFire;
    private boolean fireTruckExists = false;
    private boolean flamesAdded = false;
    private int animalSpawn = 1000;
    private int actCount = 0;
    private int resetCount = 0;
    private boolean smoky = false;
    private boolean resetedLanes = true;
    
    private GreenfootSound backgroundSound;
    private GreenfootSound fireCrackle;
    public GreenfootSound smokeHowl;
    private GreenfootSound siren;
    
    
    /**
     * Constructor for objects of class MyWorld.
     * 
     * Note that the Constrcutor for the default world is always called
     * when you click the reset button in the Greenfoot scenario screen -
     * this is is basically the code that runs when the program start.
     * Anything that should be done FIRST should go here.
     * 
     */
    public VehicleWorld()
    {    
        // Create a new world with 1024x800 pixels, UNBOUNDED
        super(1024, 800, 1, false); 

        // This command (from Greenfoot World API) sets the order in which 
        // objects will be displayed. In this example, Pedestrians will
        // always be on top of everything else, then Vehicles (of all
        // sub class types) and after that, all other classes not listed
        // will be displayed in random order. 
        //setPaintOrder (Pedestrian.class, Vehicle.class); // Commented out to use Z-sort instead
        setPaintOrder(Water.class, Smoke.class, Smoker.class, Fire.class, Tree.class, Forest.class, Flames.class);

        // set up background -- If you change this, make 100% sure
        // that your chosen image is the same size as the World
        background = new GreenfootImage ("grassBackground.png");
        background.scale(1024, 800);
        setBackground (background);
    
        // Set critical variables - will affect lane drawing
        laneCount = 3;
        
        spaceBetweenLanes = 10;
        splitAtCenter = true;
        twoWayTraffic = true;

        // Init lane spawner objects 
        laneSpawners = new VehicleSpawner[laneCount];

        // Prepare lanes method - draws the lanes
        lanePositionsY = prepareLanes (this, background, laneSpawners, 350, LANE_HEIGHT, laneCount, spaceBetweenLanes, twoWayTraffic, splitAtCenter);

        laneSpawners[0].setSpeedModifier(0.8);
        laneSpawners[2].setSpeedModifier(1.2);

        setBackground (background);
        addObject(new Forest(190), 0, 0);
        addObject(new Smoker(-1), 200, BOTTOM_SPAWN);
        
        backgroundSound = new GreenfootSound ("forestAmbient.mp3");
        fireCrackle = new GreenfootSound ("fireCrackle.mp3");
        fireCrackle.setVolume(50);
        smokeHowl = new GreenfootSound ("smokeHowl.mp3");
        smokeHowl.setVolume(30);
        siren = new GreenfootSound ("siren.mp3");
    }
    
    public void act () {
        actCount++;
        spawnSmoker();
        
        ArrayList<Smoker> smokers = new ArrayList<Smoker>(getObjects(Smoker.class));
        
        if (!smokers.isEmpty())
        {
            Smoker s = smokers.get(0); // check the first smoker
            if (s.getY() < 205) 
            {
                onFire = true;
            }
        }
        
        if (onFire && !flamesAdded) {
            addObject(new Flames(100), 0, 0);
            flamesAdded = true;
        }

        if (isOnFire()){
            spawn();
            fireCrackle.playLoop();
            resetCount = 0;
            resetedLanes = false;
        } else if (!isOnFire()){
            onFire = false;
            fireCrackle.stop();
            smokeHowl.stop();
            siren.stop();
            resetWorld();
        }        
        
        if (animalSpawn > 20){
            animalSpawn--;
        }
        
        zSort ((ArrayList<Actor>)(getObjects(Actor.class)), this);
    }
    
    public void started(){
        backgroundSound.playLoop();
    }
    
    public void stopped(){
        backgroundSound.stop();
        fireCrackle.stop();
        smokeHowl.stop();
        siren.stop();
    }
    
    public void resetWorld(){
        resetCount++;
        for (Object obj : getObjects(FireTruck.class)) {
            ((FireTruck)obj).reset();
        }
        for (Object tree: getObjects(Tree.class)){
            ((Tree)tree).reset();
        }
        for (Object fireMan: getObjects(Firefighter.class)){
            ((Firefighter)fireMan).reset();
        }
        if (!resetedLanes && resetCount % 200 == 0){
            resetLanes();
            resetedLanes = true;
            actCount = -500;
            flamesAdded = false;
            fireTruckExists = false;
            animalSpawn = 1000;
            smoky = false;
        }
    }
    
    public void resetLanes(){
        addObject(new Ambulance(laneSpawners[1]), 0, 0);
        addObject(new Ambulance(laneSpawners[2]), 0, 0);
    }
    
    public boolean isOnFire(){
        return !getObjects(Fire.class).isEmpty();
    }
    
    private void spawn () {
        if (actCount % 720 == 0 && !smoky){
            addObject(new Smoke(), 512, 400);
            smokeHowl.play();
            smoky = true;
        }
        // Chance to spawn a vehicle
        if (Greenfoot.getRandomNumber (laneCount * 40) == 0){
            int lane = Greenfoot.getRandomNumber(laneCount);
            
            if (lane == SPECIAL_LANE_INDEX && !fireTruckExists){
                int getRidFire = Greenfoot.getRandomNumber(3);
                if (getRidFire == 0){
                    addObject(new FireTruck(laneSpawners[lane]),0,0);
                    siren.playLoop();
                    fireTruckExists = true;
                    return;
                } else{
                    return;
                }
            } else if (lane == SPECIAL_LANE_INDEX){
                return;
            }
            
            if (!laneSpawners[lane].isTouchingVehicle()){
                int vehicleType = Greenfoot.getRandomNumber(10);
                if (vehicleType <= 5){
                    addObject(new Car(laneSpawners[lane]), 0, 0);
                } else if (vehicleType == 6){
                    addObject(new Ambulance(laneSpawners[lane]), 0, 0);
                } else if (vehicleType >= 7){
                    addObject(new Truck(laneSpawners[lane]), 0, 0);
                }
            }
        }

        // Chance to spawn a Pedestrian
        if (Greenfoot.getRandomNumber(animalSpawn) == 0){
            int xSpawnLocation = Greenfoot.getRandomNumber(getWidth() - 100) + 100;
            if (xSpawnLocation > getWidth()/2 -120 && xSpawnLocation < getWidth()/2 + 250){
                return;
            }
            boolean spawnAtTop = Greenfoot.getRandomNumber(3) == 0; //less animals
            
            int pedestrianType = Greenfoot.getRandomNumber(2); // 0 = Monkey, 1 = Deer
        
            if (spawnAtTop){
                if (pedestrianType == 0) {
                    addObject(new Monkey(1), xSpawnLocation, TOP_SPAWN);
                } else {
                    addObject(new Deer(1), xSpawnLocation, TOP_SPAWN);
                }
            } /*else {
                if (pedestrianType == 0) {
                    addObject(new Monkey(-1), xSpawnLocation, BOTTOM_SPAWN);
                } else {
                    addObject(new Deer(-1), xSpawnLocation, BOTTOM_SPAWN);
                }
            }*/
        }

    }
    
    private void spawnSmoker(){
        if (actCount == 0){
            addObject(new Smoker(-1), 200, BOTTOM_SPAWN);
        }
    }

    /**
     *  Given a lane number (zero-indexed), return the y position
     *  in the centre of the lane. (doesn't factor offset, so 
     *  watch your offset, i.e. with Bus).
     *  
     *  @param lane the lane number (zero-indexed)
     *  @return int the y position of the lane's center, or -1 if invalid
     */
    public int getLaneY (int lane){
        if (lane < lanePositionsY.length){
            return lanePositionsY[lane];
        } 
        return -1;
    }

    /**
     * Given a y-position, return the lane number (zero-indexed).
     * Note that the y-position must be valid, and you should 
     * include the offset in your calculations before calling this method.
     * For example, if a Bus is in a lane at y=100, but is offset by -20,
     * it is actually in the lane located at y=80, so you should send
     * 80 to this method, not 100.
     * 
     * @param y - the y position of the lane the Vehicle is in
     * @return int the lane number, zero-indexed
     * 
     */
    public int getLane (int y){
        for (int i = 0; i < lanePositionsY.length; i++){
            if (y == lanePositionsY[i]){
                return i;
            }
        }
        return -1;
    }

    public static int[] prepareLanes (World world, GreenfootImage target, VehicleSpawner[] spawners, int startY, int heightPerLane, int lanes, int spacing, boolean twoWay, boolean centreSplit, int centreSpacing)
    {
        // Declare an array to store the y values as I calculate them
        int[] lanePositions = new int[lanes];
        // Pre-calculate half of the lane height, as this will frequently be used for drawing.
        // To help make it clear, the heightOffset is the distance from the centre of the lane (it's y position)
        // to the outer edge of the lane.
        int heightOffset = heightPerLane / 2;
        // draw top border
        target.setColor (GREY_BORDER);
        target.fillRect (0, startY, target.getWidth(), spacing);

        // Main Loop to Calculate Positions and draw lanes
        for (int i = 0; i < lanes; i++){
            // calculate the position for the lane
            lanePositions[i] = startY + spacing + (i * (heightPerLane+spacing)) + heightOffset ;

            // draw lane
            if (i == VehicleWorld.SPECIAL_LANE_INDEX) {
                target.setColor(new Color(10, 115, 30)); // a green lane
            } else {
                target.setColor(GREY_STREET);
            }
            // the lane body
            target.fillRect (0, lanePositions[i] - heightOffset, target.getWidth(), heightPerLane);
            // the lane spacing - where the white or yellow lines will get drawn
            target.fillRect(0, lanePositions[i] + heightOffset, target.getWidth(), spacing);

            // Place spawners and draw lines depending on whether its 2 way and centre split
            if (twoWay && centreSplit){
                // first half of the lanes go rightward (no option for left-hand drive, sorry UK students .. ?)
                if ( i < lanes / 2){
                    spawners[i] = new VehicleSpawner(false, heightPerLane, i);
                    world.addObject(spawners[i], target.getWidth(), lanePositions[i]);
                } else { // second half of the lanes go leftward
                    spawners[i] = new VehicleSpawner(true, heightPerLane, i);
                    world.addObject(spawners[i], 0, lanePositions[i]);
                }

                // draw yellow lines if middle 
                if (i == lanes / 2){
                    target.setColor(YELLOW_LINE);
                    target.fillRect(0, lanePositions[i] - heightOffset - spacing, target.getWidth(), spacing);

                } else if (i > 0){ // draw white lines if not first lane
                    for (int j = 0; j < target.getWidth(); j += 120){
                        target.setColor (Color.WHITE);
                        target.fillRect (j, lanePositions[i] - heightOffset - spacing, 60, spacing);
                    }
                } 

            } else if (twoWay){ // not center split
                if ( i % 2 == 0){
                    spawners[i] = new VehicleSpawner(false, heightPerLane, i);
                    world.addObject(spawners[i], target.getWidth(), lanePositions[i]);
                } else {
                    spawners[i] = new VehicleSpawner(true, heightPerLane, i);
                    world.addObject(spawners[i], 0, lanePositions[i]);
                }

                // draw Grey Border if between two "Streets"
                if (i > 0){ // but not in first position
                    if (i % 2 == 0){
                        target.setColor(GREY_BORDER);
                        target.fillRect(0, lanePositions[i] - heightOffset - spacing, target.getWidth(), spacing);

                    } else { // draw dotted lines
                        for (int j = 0; j < target.getWidth(); j += 120){
                            target.setColor (YELLOW_LINE);
                            target.fillRect (j, lanePositions[i] - heightOffset - spacing, 60, spacing);
                        }
                    } 
                }
            } else { // One way traffic
                spawners[i] = new VehicleSpawner(true, heightPerLane, i);
                world.addObject(spawners[i], 0, lanePositions[i]);
                if (i > 0){
                    for (int j = 0; j < target.getWidth(); j += 120){
                        target.setColor (Color.WHITE);
                        target.fillRect (j, lanePositions[i] - heightOffset - spacing, 60, spacing);
                    }
                }
            }
        }
        // draws bottom border
        target.setColor (GREY_BORDER);
        target.fillRect (0, lanePositions[lanes-1] + heightOffset, target.getWidth(), spacing);

        return lanePositions;
    }

        /**
     * Z-sort so actors with higher Y (lower on screen) render in front.
     * Uses precise Y for SuperSmoothMover when available. Stable for ties.
     */
    public static void zSort(java.util.ArrayList<greenfoot.Actor> actorsToSort, greenfoot.World world) {
        // Local container class (scoped to this method only).
        class Entry implements java.lang.Comparable<Entry> {
            final greenfoot.Actor actor;
            final boolean superSmooth;
            final int order;     // preserve original order for stable ties
            final int xi, yi;    // integer coords snapshot
            final double xd, yd; // precise coords snapshot
    
            // int-based actor
            Entry(greenfoot.Actor a, int x, int y, int order) {
                this.actor = a; this.superSmooth = false; this.order = order;
                this.xi = x; this.yi = y;
                this.xd = x; this.yd = y;
            }
            // precise-based actor
            Entry(greenfoot.Actor a, double x, double y, int order) {
                this.actor = a; this.superSmooth = true; this.order = order;
                this.xi = (int) x; this.yi = (int) y;
                this.xd = x; this.yd = y;
            }
    
            @Override
            public int compareTo(Entry other) {
                double thisY  = superSmooth ? yd : yi;
                double otherY = other.superSmooth ? other.yd : other.yi;
    
                // Handle rare NaN robustly: treat NaN as far back
                if (java.lang.Double.isNaN(thisY) && java.lang.Double.isNaN(otherY)) return java.lang.Integer.compare(order, other.order);
                if (java.lang.Double.isNaN(thisY)) return -1;
                if (java.lang.Double.isNaN(otherY)) return 1;
    
                int cmp = java.lang.Double.compare(thisY, otherY);
                if (cmp != 0) return cmp;
                return java.lang.Integer.compare(this.order, other.order); // stable tie-break
            }
        }
    
        // Snapshot actors and positions first.
        java.util.ArrayList<Entry> list = new java.util.ArrayList<Entry>(actorsToSort.size());
        int order = 0;
        for (greenfoot.Actor a : actorsToSort) {
            if (a instanceof SuperSmoothMover) {
                SuperSmoothMover s = (SuperSmoothMover) a;
                list.add(new Entry(a, s.getPreciseX(), s.getPreciseY(), order++));
            } else {
                list.add(new Entry(a, a.getX(), a.getY(), order++));
            }
        }
    
        // Sort farthest-back (smallest Y) first.
        java.util.Collections.sort(list);
    
        // Re-add in paint order with consistent rounding, then restore precise coords.
        for (Entry e : list) {
            // Remove if currently in any world to ensure paint-order reset
            if (e.actor.getWorld() != null) {
                world.removeObject(e.actor);
            }
            if (e.superSmooth) {
                int rx = roundAwayFromZero(e.xd);
                int ry = roundAwayFromZero(e.yd);
                world.addObject(e.actor, rx, ry);
                // Restore exact double-precision location to avoid drift
                ((SuperSmoothMover) e.actor).setLocation(e.xd, e.yd);
            } else {
                world.addObject(e.actor, e.xi, e.yi);
            }
        }
    }

    /** Helper: symmetric rounding that rounds halves away from zero. */
    private static int roundAwayFromZero(double v) {
        return (int)(v + Math.signum(v) * 0.5);
    }

    /**
     * <p>The prepareLanes method is a static (standalone) method that takes a list of parameters about the desired roadway and then builds it.</p>
     * 
     * <p><b>Note:</b> So far, Centre-split is the only option, regardless of what values you send for that parameters.</p>
     *
     * <p>This method does three things:</p>
     * <ul>
     *  <li> Determines the Y coordinate for each lane (each lane is centered vertically around the position)</li>
     *  <li> Draws lanes onto the GreenfootImage target that is passed in at the specified / calculated positions. 
     *       (Nothing is returned, it just manipulates the object which affects the original).</li>
     *  <li> Places the VehicleSpawners (passed in via the array parameter spawners) into the World (also passed in via parameters).</li>
     * </ul>
     * 
     * <p> After this method is run, there is a visual road as well as the objects needed to spawn Vehicles. Examine the table below for an
     * in-depth description of what the roadway will look like and what each parameter/component represents.</p>
     * 
     * <pre>
     *                  <=== Start Y
     *  ||||||||||||||  <=== Top Border
     *  /------------\
     *  |            |  
     *  |      Y[0]  |  <=== Lane Position (Y) is the middle of the lane
     *  |            |
     *  \------------/
     *  [##] [##] [##| <== spacing ( where the lane lines or borders are )
     *  /------------\
     *  |            |  
     *  |      Y[1]  |
     *  |            |
     *  \------------/
     *  ||||||||||||||  <== Bottom Border
     * </pre>
     * 
     * @param world     The World that the VehicleSpawners will be added to
     * @param target    The GreenfootImage that the lanes will be drawn on, usually but not necessarily the background of the World.
     * @param spawners  An array of VehicleSpawner to be added to the World
     * @param startY    The top Y position where lanes (drawing) should start
     * @param heightPerLane The height of the desired lanes
     * @param lanes     The total number of lanes desired
     * @param spacing   The distance, in pixels, between each lane
     * @param twoWay    Should traffic flow both ways? Leave false for a one-way street (Not Yet Implemented)
     * @param centreSplit   Should the whole road be split in the middle? Or lots of parallel two-way streets? Must also be two-way street (twoWay == true) or else NO EFFECT
     * 
     */
    public static int[] prepareLanes (World world, GreenfootImage target, VehicleSpawner[] spawners, int startY, int heightPerLane, int lanes, int spacing, boolean twoWay, boolean centreSplit){
        return prepareLanes (world, target, spawners, startY, heightPerLane, lanes, spacing, twoWay, centreSplit, spacing);
    }

}

/**
 * Container to hold and Actor and an LOCAL position (so the data isn't lost when the Actor is temporarily
 * removed from the World).
 */
class ActorContent implements Comparable <ActorContent> {
    private Actor actor;
    private int xx, yy;
    public ActorContent(Actor actor, int xx, int yy){
        this.actor = actor;
        this.xx = xx;
        this.yy = yy;
    }

    public void setLocation (int x, int y){
        xx = x;
        yy = y;
    }

    public int getX() {
        return xx;
    }

    public int getY() {
        return yy;
    }

    public Actor getActor(){
        return actor;
    }

    public String toString () {
        return "Actor: " + actor + " at " + xx + ", " + yy;
    }

    public int compareTo (ActorContent a){
        return this.getY() - a.getY();
    }

}