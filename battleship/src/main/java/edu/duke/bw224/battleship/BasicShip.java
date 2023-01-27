package edu.duke.bw224.battleship;

import java.util.HashMap;

public abstract class BasicShip<T> implements Ship<T>{
    // record coordinates the ship occupies, and track which ones have been hit
    protected HashMap<Coordinate, Boolean> myPieces;
    protected ShipDisplayInfo<T> myDisplayInfo;

    /**
     * initialize myPieces to have each Coordinate in where mapped to false
     * @param where is the Coordinate iterable
     */
    public BasicShip(Iterable<Coordinate> where, ShipDisplayInfo<T> myDisplayInfo) {
        myPieces = new HashMap<>();
        for (Coordinate c : where) {
            myPieces.put(c, false);
        }
        this.myDisplayInfo = myDisplayInfo;
    }

    // TODO Auto-generated method stub
    /**
     * @param where is the Coordinate to check if this Ship occupies
     * @return where equals myLocation
     */


    @Override
    public boolean occupiesCoordinates(Coordinate where) {
        return myPieces.containsKey(where);
    }

    /**
     * @return
     */
    @Override
    public boolean isSunk() {
        return false;
    }

    /**
     * @param where specifies the coordinates that were hit.
     */
    @Override
    public void recordHitAt(Coordinate where) {

    }

    /**
     * @param where is the coordinates to check.
     * @return
     */
    @Override
    public boolean wasHitAt(Coordinate where) {
        return false;
    }

    /**
     * @param where is the coordinate to return information for
     * @return the information we are going to display to user with type T
     */
    @Override
    public T getDisplayInfoAt(Coordinate where) {
        //TODO this is not right. We need to look up the hit status of this coordinate
        return myDisplayInfo.getInfo(where, false);

    }
}
