package edu.duke.bw224.battleship;

import java.util.HashSet;

public class TShip<T> extends BasicShip<T>{
    private final String name;

    /**
     * T ship constructor
     * @param upperLeft is the upperLeft coordinate of the ship
     * @param myDisplayInfo is the ship display info
     * @param enemyDisplayInfo is the ship display info for enemy
     * @param name is the ship name
     */
    public TShip(String name, Coordinate upperLeft, int height, char orientation, ShipDisplayInfo<T> myDisplayInfo,
                 ShipDisplayInfo<T> enemyDisplayInfo) {
        super(upperLeft, makeCoords(upperLeft, height, orientation), myDisplayInfo, enemyDisplayInfo);
        this.name = name;
    }

    public TShip(String name, Coordinate upperLeft, int height, char orientation, T data, T onHit ) {
        this(name, upperLeft, height, orientation, new SimpleShipDisplayInfo<T>(data, onHit),
                new SimpleShipDisplayInfo<>(null, data));
    }

    /**
     * helper function to generate coordinates for right or left TShip
     * @param height is the height of TShip
     * @param coords is result of coordinates in the ship
     * @param startRow is the row of upperLeft coordinate
     * @param startCol is the col of upperRight coordinate
     * @param TCol is the col of TShip col
     * @param TRow is the row of TShip row
     */
    private static void makeRightOrLeftTShipHelper(int height, HashSet<Coordinate> coords, int startRow, int startCol, int TCol, int TRow) {
        int width = (height + 1) % 2 + height;
        for (int i = startRow; i < startRow + width; ++i) {
            coords.add(new Coordinate(i, TCol));
        }
        for (int j = startCol; j < startCol + height; ++j) {
            coords.add(new Coordinate(TRow, j));
        }
    }

    /**
     * helper function to generate coordinates for up or down TShip
     * @param height is the height of TShip
     * @param coords is result of coordinates in the ship
     * @param startRow is the row of upperLeft coordinate
     * @param startCol is the col of upperRight coordinate
     * @param TCol is the col of TShip col
     * @param TRow is the row of TShip row
     */
    private static void makeUpOrDownTShipHelper(int height, HashSet<Coordinate> coords, int startRow, int startCol, int TCol, int TRow) {
        for (int i = startRow; i < startRow + height; ++i) {
            coords.add(new Coordinate(i, TCol));
        }
        int width = (height + 1) % 2 + height;
        for (int j = startCol; j < startCol + width; ++j) {
            coords.add(new Coordinate(TRow, j));
        }
    }

    /**
     * function to generate coordinates for up TShip
     * @param upperLeft is the upper left coordinate of the TShip
     * @param height is the TShip height
     * @param coords is the result of coordinates
     */
    static void makeUpTShip(Coordinate upperLeft, int height, HashSet<Coordinate> coords) {
        int startRow = upperLeft.getRow(), startCol = upperLeft.getCol();
        int TCol = startCol + height / 2, TRow = startRow + height - 1;
        makeUpOrDownTShipHelper(height, coords, startRow, startCol, TCol, TRow);
    }

    /**
     * function to generate coordinates for up TShip
     * @param upperLeft is the upper left coordinate of the TShip
     * @param height is the TShip height
     * @param coords is the result of coordinates
     */
    static void makeRightTShip(Coordinate upperLeft, int height, HashSet<Coordinate> coords) {
        int startRow = upperLeft.getRow(), startCol = upperLeft.getCol();
        int TRow = startRow + height / 2;
        makeRightOrLeftTShipHelper(height, coords, startRow, startCol, startCol, TRow);
    }

    /**
     * function to generate coordinates for down TShip
     * @param upperLeft is the upper left coordinate of the TShip
     * @param height is the TShip height
     * @param coords is the result of coordinates
     */
    static void makeDownShip(Coordinate upperLeft, int height, HashSet<Coordinate> coords) {
        int startRow = upperLeft.getRow(), startCol = upperLeft.getCol();
        int TCol = startCol + height / 2;
        makeUpOrDownTShipHelper(height, coords, startRow, startCol, TCol, startRow);
    }

    /**
     * function to generate coordinates for left TShip
     * @param upperLeft is the upper left coordinate of the TShip
     * @param height is the TShip height
     * @param coords is the result of coordinates
     */
    static void makeLeftTShip(Coordinate upperLeft, int height, HashSet<Coordinate> coords) {
        int startRow = upperLeft.getRow(), startCol = upperLeft.getCol();
        int TCol = startCol + height - 1, TRow = startRow + height / 2;
        makeRightOrLeftTShipHelper(height, coords, startRow, startCol, TCol, TRow);
    }


    /**
     * Generate a set of coordinates for a rectangle starting at upperLeft point
     * @param upperLeft is the upper left coordinate of the rectangle
     * @param height is the height of T ship, so the width of ship should be height + 1
     * @return a HashSet of coordinates
     */
    static HashSet<Coordinate> makeCoords(Coordinate upperLeft, int height, char orientation) {
        HashSet<Coordinate> coords = new HashSet<>();
        if (height <= 1) {
            throw new IllegalArgumentException("The height should be bigger than 1 but is " + height + "\n");
        }
        switch (orientation) {
            case 'U':
                makeUpTShip(upperLeft, height, coords);
                break;
            case 'L':
                makeLeftTShip(upperLeft, height, coords);
                break;
            case 'R':
                makeRightTShip(upperLeft, height, coords);
                break;
            case 'D':
                makeDownShip(upperLeft, height, coords);
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
        return "TShip{" +
                "name='" + name + '\'' +
                ", myPieces=" + myPieces.toString() +
                '}';
    }

}
