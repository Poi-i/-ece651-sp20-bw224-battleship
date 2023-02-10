package edu.duke.bw224.battleship;

import java.util.HashMap;

public abstract class BasicShip<T> implements Ship<T>{
    // record coordinates the ship occupies, and track which ones have been hit
    protected Coordinate upperLeft;
    protected HashMap<Coordinate, Boolean> myPieces;
    protected ShipDisplayInfo<T> myDisplayInfo;
    protected ShipDisplayInfo<T> enemyDisplayInfo;

    /**
     * initialize myPieces to have each Coordinate in where mapped to false
     * @param where is the Coordinate iterable
     */
    public BasicShip(Coordinate upperLeft, Iterable<Coordinate> where, ShipDisplayInfo<T> myDisplayInfo, ShipDisplayInfo<T> enemyDisplayInfo) {
        myPieces = new HashMap<>();
        for (Coordinate c : where) {
            myPieces.put(c, false);
        }
        this.myDisplayInfo = myDisplayInfo;
        this.enemyDisplayInfo = enemyDisplayInfo;
        this.upperLeft = upperLeft;
    }

    /**
     * Get all of the Coordinates that this Ship occupies.
     * @return An Iterable with the coordinates that this Ship occupies
     */
    @Override
    public Iterable<Coordinate> getCoordinates() {
        return myPieces.keySet();
    }

    /**
     * helper method to check coordinate in the ship
     * @param c is the coordinate to check
     */
    protected void checkCoordinateInThisShip(Coordinate c) {
        if (!myPieces.containsKey(c)) {
            throw new IllegalArgumentException("the coordinate should belong to the ship but is " + c.toString());
        }
    }


    /**
     * @param where is the Coordinate to check if this Ship occupies
     * @return where equals myLocation
     */
    @Override
    public boolean occupiesCoordinates(Coordinate where) {
        return myPieces.containsKey(where);
    }

    /**
     * check every piece status in the ship and decide if sunk
     * @return the ship is sunk or not
     */
    @Override
    public boolean isSunk() {
        return !myPieces.containsValue(false);
    }

    /**
     * record hit status on given coordinate
     * @param where specifies the coordinates that were hit.
     */
    @Override
    public void recordHitAt(Coordinate where) {
        checkCoordinateInThisShip(where);
        myPieces.put(where,true);

    }

    /**
     * check whether the given coordinate is hit
     * @param where is the coordinates to check.
     * @return hit or not
     */
    @Override
    public boolean wasHitAt(Coordinate where) {
        checkCoordinateInThisShip(where);
        return myPieces.get(where);
    }

    /**
     * @param where is the coordinate to return information for
     * @return the information we are going to display to user with type T
     */
    @Override
    public T getDisplayInfoAt(Coordinate where, boolean myShip) {
        checkCoordinateInThisShip(where);
        if (myShip) {
            return myDisplayInfo.getInfo(where, myPieces.get(where));
        }
        return enemyDisplayInfo.getInfo(where, myPieces.get(where));
    }

}
