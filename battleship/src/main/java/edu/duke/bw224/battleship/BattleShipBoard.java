package edu.duke.bw224.battleship;

import java.util.ArrayList;
import java.util.HashSet;

public class BattleShipBoard<T> implements Board<T>{
    private final int width;
    private final int height;
    final T missInfo;
    final ArrayList<Ship<T>> myShips;
    HashSet<Coordinate> enemyMisses;
    private final PlacementRuleChecker<T> placementRuleChecker;


    public int getWidth(){
        return width;
    }
    public int getHeight() {
        return height;
    }

    /**
     * Constructs a BattleShipBoard with the specified width
     * and height
     * @param w is the width of the newly constructed Board<T>.
     * @param h is the height of the newly constructed Board<T>.
     * @throws IllegalArgumentException if the width or height are less than or equal to zero.
     */
    public BattleShipBoard(int w, int h, T missInfo, PlacementRuleChecker<T> placementRuleChecker) {
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
    }

    public BattleShipBoard(int width, int height, T missInfo) {
        this(width, height, missInfo, new NoCollisionRuleChecker<>(new InBoundsRuleChecker<>(null)));
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


}
