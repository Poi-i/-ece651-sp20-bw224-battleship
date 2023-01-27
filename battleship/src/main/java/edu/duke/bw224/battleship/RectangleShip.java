package edu.duke.bw224.battleship;

import java.util.HashSet;

public class RectangleShip<T> extends BasicShip<T>{
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
            throw new IllegalArgumentException("width and height should be positive integer but are " + width + " and " + height);
        }
        int startRow = upperLeft.getRow(), startCol = upperLeft.getCol();
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                coords.add(new Coordinate(startRow + i, startCol + j));
            }
        }
        return coords;
    }



    public RectangleShip(Coordinate upperLeft, int width, int height, ShipDisplayInfo<T> myDisplayInfo) {
        super(makeCoords(upperLeft, width, height), myDisplayInfo);
    }

    public RectangleShip(Coordinate upperLeft, int width, int height, T data, T onHit ) {
        this(upperLeft, width, height, new SimpleShipDisplayInfo<T>(data, onHit));
    }

    public RectangleShip(Coordinate upperLeft, T data, T onHit) {
        this(upperLeft, 1, 1, data, onHit);
    }

}
