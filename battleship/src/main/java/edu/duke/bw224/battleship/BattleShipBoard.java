package edu.duke.bw224.battleship;

import java.util.ArrayList;
import java.util.HashSet;

public class BattleShipBoard<T> implements Board<T>{
    private final int width;
    private final int height;
    final T missInfo;
    private final ArrayList<Ship<T>> myShips;
    HashSet<Coordinate> enemyMisses;
    private final PlacementRuleChecker<T> placementRuleChecker;
    private final OrientationRuleChecker<T> orientationRuleChecker;
    private final LoseChecker<T> loseChecker;


    public int getWidth(){
        return width;
    }
    public int getHeight() {
        return height;
    }

    /** Return the board's ships
     * @return the board's ships
     */
    @Override
    public Iterable<Ship<T>> getShips() {
        return myShips;
    }

    /**
     * @param coordinate is the coordinate we need to check
     * @return
     */
    @Override
    public Ship<T> getShipAt(Coordinate coordinate) {
        for (Ship<T> s : myShips) {
            if (s.occupiesCoordinates(coordinate)) {
                return s;
            }
        }
        return null;
    }

    /** Return the board's miss info
     * @return the board's miss info
     */
    @Override
    public T getMissInfo() {
        return missInfo;
    }

    /**
     * Constructs a BattleShipBoard with the specified width
     * and height
     * @param w is the width of the newly constructed Board<T>.
     * @param h is the height of the newly constructed Board<T>.
     * @throws IllegalArgumentException if the width or height are less than or equal to zero.
     */
    public BattleShipBoard(int w, int h, T missInfo, PlacementRuleChecker<T> placementRuleChecker,
                           OrientationRuleChecker<T> orientationRuleChecker, LoseChecker<T> loseChecker) {
        if (w <= 0) {
            throw new IllegalArgumentException("BattleShipBoard's width must be positive but is " + w);
        }
        if (h <= 0) {
            throw new IllegalArgumentException("BattleShipBoard's height must be positive but is " + h);
        }
        this.width = w;
        this.height = h;
        this.missInfo = missInfo;
        this.myShips = new ArrayList<>();
        this.enemyMisses = new HashSet<>();
        this.placementRuleChecker = placementRuleChecker;
        this.orientationRuleChecker = orientationRuleChecker;
        this.loseChecker = loseChecker;
    }

    public BattleShipBoard(int width, int height, T missInfo) {
        this(width, height, missInfo, new NoCollisionRuleChecker<>(new InBoundsRuleChecker<>(null)),
                new OrientationRuleChecker<>(), new AllSunkLoseCheck<>(null));
    }

    /**
     * This method takes a coordinate, see which (if any) Ship occupies that coordinate
     * @param where is the coordinate we want to check
     * @return displayInfo at those coordinates, or null
     */
    @Override
    public T whatIsAtForSelf(Coordinate where) {
        return whatIsAt(where, true);
    }

    public T whatIsAtForEnemy(Coordinate where) {
        return whatIsAt(where, false);
    }

    /**
     * This method check what should we display on a coordinate for ourselves or our enemy
     * @param where is the coordinate we want to check
     * @param isSelf indicates display for ourselves or not
     * @return the data we should display
     */
    protected T whatIsAt(Coordinate where, boolean isSelf) {
        for (Ship<T> s : myShips) {
            if (s.occupiesCoordinates(where)) {
                return s.getDisplayInfoAt(where, isSelf);
            }
        }
        if (!isSelf) {
            if (enemyMisses.contains(where)) {
                return missInfo;
            }
        }
        return null;
    }

    /**
     * try to add a ship to myShip list
     * @param toAdd is the ship we are about to add
     * @return successfully add ship or not
     */
    @Override
    public String tryAddShip(Ship<T> toAdd) {
        String message = this.placementRuleChecker.checkPlacement(toAdd, this);
        if (message == null) {
            myShips.add(toAdd);
        }
        return message;
    }

    /**
     * search for any ship that occupies coordinate c
     * If one is found, hit that Ship and record it
     * @param c is the coordinate we are going to hit at
     * @return the ship if we hit
     */
    @Override
    public Ship<T> fireAt(Coordinate c) {
        //If no ships are at this coordinate, record the miss
        if (whatIsAtForSelf(c) != null) {
            Ship<T> theShip = null;
            for (Ship<T> ship : myShips) {
                if (ship.occupiesCoordinates(c)) {
                    ship.recordHitAt(c);
                    theShip = ship;
                    break;
                }
            }
            return theShip;
        }
        else{
            enemyMisses.add(c);
            return null;
        }
    }

    /** check ship with correct orientation
     * @param shipName is the ship to check
     * @param placement the placement to check
     * @return error message or null
     */
    @Override
    public String checkPlacementOrientation(String shipName, Placement placement) {
        return orientationRuleChecker.checkMyRule(shipName, placement.getOrientation());
    }

    /**
     * @return
     */
    @Override
    public boolean checkAllSunk() {
        return loseChecker.checkLose(this);
    }
}
