package edu.duke.bw224.battleship;

public class BasicShip implements Ship{
    private final Coordinate myLocation;

    public BasicShip(Coordinate myLocation) {
        this.myLocation = myLocation;
    }

    // TODO Auto-generated method stub
    /**
     * @param where is the Coordinate to check if this Ship occupies
     * @return where equals myLocation
     */
    @Override
    public boolean occupiesCoordinates(Coordinate where) {
        return where.equals(myLocation);
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
     * @return
     */
    @Override
    public Object getDisplayInfoAt(Coordinate where) {
        return 's';
    }
}
