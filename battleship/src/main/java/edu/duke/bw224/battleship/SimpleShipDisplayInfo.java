package edu.duke.bw224.battleship;

public class SimpleShipDisplayInfo<T> implements ShipDisplayInfo<T>{
    T myData;
    T onHit;

    public SimpleShipDisplayInfo(T myData, T onHit) {
        this.myData = myData;
        this.onHit = onHit;
    }

    /**
     * get ship info on the given coordinate that we should display to user
     * @param where is the coordinate
     * @param hit means whether this coordinate is hit or not
     * @return data we should display
     */
    @Override
    public T getInfo(Coordinate where, boolean hit) {
        if (hit) return onHit;
        return myData;
    }
}
