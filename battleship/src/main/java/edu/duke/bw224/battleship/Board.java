package edu.duke.bw224.battleship;

public interface Board<T> {
    /**
     * Return the board's width
     * @return int width of the board
     */
    public int getWidth();

    /**
     * Return the board's height
     * @return int height of the board
     */
    public int getHeight();

    /**
     * Return the iterator of ships on the board
     * @return ship container
     */
    public Iterable<Ship<T>> getShips();

    /**
     * Return the ship on the coordinate, or null if no ship
     * @param coordinate is the coordinate we need to check
     * @return the ship or null
     */
    public Ship<T> getShipAt(Coordinate coordinate);

    /**
     * Return the miss info of the board
     * @return the board's miss info
     */
    public T getMissInfo();
    /**
     * try to add a ship on board
     * @param toAdd is the ship we are about to add
     * @return error msg if add failed, or null if success
     */
    public String tryAddShip(Ship<T> toAdd);

    /**
     * return display info for self on the coordinate
     * @param where is the coordinate
     * @return display info
     */
    public T whatIsAtForSelf(Coordinate where);

    /**
     * return display info for enemy on the coordinate
     * @param where is the coordinate
     * @return display info
     */
    public T whatIsAtForEnemy(Coordinate where);

    /**
     * fire ar a coordinate on the board
     * @param c is the coordinate to fire at
     * @return the ship if we hit, or null if we miss
     */
    public Ship<T> fireAt(Coordinate c);

    /**
     * check all ships on the board are sunk
     * @return true or false
     */
    public boolean checkAllSunk();

    String checkPlacementOrientation(String shipName, Placement placement);

}
