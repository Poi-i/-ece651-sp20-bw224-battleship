package edu.duke.bw224.battleship;

import java.util.ArrayList;

public class BattleShipBoard<T> implements Board<T>{
    private final int width;
    private final int height;
    final ArrayList<Ship<T>> myShips;

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
    public BattleShipBoard(int w, int h, PlacementRuleChecker<T> placementRuleChecker) {
        if (w <= 0) {
            throw new IllegalArgumentException("BattleShipBoard's width must be positive but is " + w);
        }
        if (h <= 0) {
            throw new IllegalArgumentException("BattleShipBoard's height must be positive but is " + h);
        }
        this.width = w;
        this.height = h;
        this.myShips = new ArrayList<>();
        this.placementRuleChecker = placementRuleChecker;
    }

    public BattleShipBoard(int width, int height) {
        this(width, height, new InBoundsRuleChecker<T>(null));
    }

    /**
     * This method takes a coordinate, see which (if any) Ship occupies that coordinate
     * @param where is the coordinate we want to check
     * @return displayInfo at those coordinates, or null
     */
    @Override
    public T whatIsAt(Coordinate where) {
        for (Ship<T> s : myShips) {
            if (s.occupiesCoordinates(where)) {
                return s.getDisplayInfoAt(where);
            }
        }
        return null;
    }

    /**
     * add a ship to myShip list and return true
     * @param toAdd is the ship we are about to add
     * @return
     */
    // TODO update later (tasks 13-15), check validity of placement
    @Override
    public boolean tryAddShip(Ship<T> toAdd) {
        myShips.add(toAdd);
        return true;
    }


}
