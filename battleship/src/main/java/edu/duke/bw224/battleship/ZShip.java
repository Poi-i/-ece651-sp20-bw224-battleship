package edu.duke.bw224.battleship;

import java.util.HashSet;

public class ZShip<T> extends BasicShip<T>{
    private final String name;

    /**
     * Z ship constructor
     * @param upperLeft is the upperLeft coordinate of the ship
     * @param myDisplayInfo is the ship display info
     * @param enemyDisplayInfo is the ship display info for enemy
     * @param name is the ship name
     */
    public ZShip(String name, Coordinate upperLeft, char orientation, ShipDisplayInfo<T> myDisplayInfo,
                 ShipDisplayInfo<T> enemyDisplayInfo) {
        super(upperLeft, makeCoords(upperLeft, orientation), myDisplayInfo, enemyDisplayInfo);
        this.name = name;
    }

    public ZShip(String name, Coordinate upperLeft, char orientation, T data, T onHit ) {
        this(name, upperLeft, orientation, new SimpleShipDisplayInfo<T>(data, onHit),
                new SimpleShipDisplayInfo<>(null, data));
    }

    /**
     * function to generate coordinates for up or down TShip
     * @param upperLeft is the upper left coordinate of the TShip
     * @param coords is the result of coordinates
     */
    static void makeUpOrDownTShip(Coordinate upperLeft, HashSet<Coordinate> coords, boolean isUp) {
        int startRow = upperLeft.getRow(), startCol = upperLeft.getCol();
        int endRow1, startRow2;
        if (isUp) {
            endRow1 = startRow + 4;
            startRow2 = startRow + 2;
        }
        else {
            endRow1 = startRow + 3;
            startRow2 = startRow + 1;
        }
        for (int i = startRow; i < endRow1; ++i) {
            coords.add(new Coordinate(i, startCol));
        }
        for (int i = startRow2; i < startRow + 5; ++i) {
            coords.add(new Coordinate(i, startCol + 1));
        }
    }


    /**
     * function to generate coordinates for right or left TShip
     * @param upperLeft is the upper left coordinate of the TShip
     * @param coords is the result of coordinates
     */
    static void makeRightOrLeftTShip(Coordinate upperLeft, HashSet<Coordinate> coords, boolean isRight) {
        int startRow = upperLeft.getRow(), startCol = upperLeft.getCol();
        int startCol1, endCol2;
        if (isRight) {
            startCol1 = startCol + 1;
            endCol2 = startCol + 3;
        }
        else {
            startCol1 = startCol + 2;
            endCol2 = startCol + 4;
        }
        for (int j = startCol1; j < startCol + 5; ++j) {
            coords.add(new Coordinate(startRow, j));
        }
        for (int j = startCol; j < endCol2; ++j) {
            coords.add(new Coordinate(startRow + 1, j));
        }
    }

    /**
     * Generate a set of coordinates for a rectangle starting at upperLeft point
     * @param upperLeft is the upper left coordinate of the rectangle
     * @return a HashSet of coordinates
     */
    static HashSet<Coordinate> makeCoords(Coordinate upperLeft, char orientation) {
        HashSet<Coordinate> coords = new HashSet<>();
        switch (orientation) {
            case 'U':
                makeUpOrDownTShip(upperLeft, coords, true);
                break;
            case 'L':
                makeRightOrLeftTShip(upperLeft, coords, false);
                break;
            case 'R':
                makeRightOrLeftTShip(upperLeft, coords, true);
                break;
            case 'D':
                makeUpOrDownTShip(upperLeft, coords, false);
                break;
            default:
                throw new IllegalArgumentException("The orientation of T Ship can only be U, L, R, or D");
        }
        return coords;
    }

    /** return TShip name
     * @return ship name
     */
    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public String toString() {
        return "ZShip{" +
                "name='" + name + '\'' +
                ", myPieces=" + myPieces.toString() +
                '}';
    }
}
