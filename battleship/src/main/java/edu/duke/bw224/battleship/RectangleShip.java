package edu.duke.bw224.battleship;

import java.util.HashSet;

public class RectangleShip<T> extends BasicShip<T>{

    final String name;

    @Override
    public String getName() {
        return name;
    }

    /**
     * Generate a set of coordinates for a rectangle starting at upperLeft point
     * @param upperLeft is the upper left coordinate of the rectangle
     * @param width is the rectangle width
     * @param height is the rectangle height
     * @return a HashSet of coordinates
     */
    static HashSet<Coordinate> makeCoords(Coordinate upperLeft, int width, int height) {
        HashSet<Coordinate> coords = new HashSet<>();
        if (width <= 0 || height <= 0) {
            throw new IllegalArgumentException("width and height should be positive integer but are " + width + " and "
                    + height);
        }
        int startRow = upperLeft.getRow(), startCol = upperLeft.getCol();
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                coords.add(new Coordinate(startRow + i, startCol + j));
            }
        }
        return coords;
    }



    public RectangleShip(String name, Coordinate upperLeft, int width, int height, ShipDisplayInfo<T> myDisplayInfo,
                         ShipDisplayInfo<T> enemyDisplayInfo) {
        super(makeCoords(upperLeft, width, height), myDisplayInfo, enemyDisplayInfo);
        this.name = name;
    }

    public RectangleShip(String name, Coordinate upperLeft, int width, int height, T data, T onHit ) {
        this(name, upperLeft, width, height, new SimpleShipDisplayInfo<T>(data, onHit),
                new SimpleShipDisplayInfo<>(null, data));
    }

    public RectangleShip(Coordinate upperLeft, T data, T onHit) {
        this("testShip", upperLeft, 1, 1, data, onHit);
    }

    @Override
    public String toString() {
        return "RectangleShip{" +
                "name='" + name + '\'' +
                ", myPieces=" + myPieces.toString() +
                '}';
    }
}
