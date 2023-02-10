package edu.duke.bw224.battleship;

import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.*;

class ZShipTest {
    private final char myData = 's';
    private final char onHit = '*';
    private final String testShip = "testShip";


    private HashSet<Coordinate> generateLeftZShipCoordinates(Coordinate coordinate) {
        int row = coordinate.getRow(), col = coordinate.getCol();
        HashSet<Coordinate> coords = new HashSet<>();
        coords.add(new Coordinate(row, col + 2));
        coords.add(new Coordinate(row, col + 3));
        coords.add(new Coordinate(row, col + 4));
        coords.add(new Coordinate(row + 1, col));
        coords.add(new Coordinate(row + 1, col + 1));
        coords.add(new Coordinate(row + 1, col + 2));
        coords.add(new Coordinate(row + 1, col + 3));
        return coords;
    }

    private HashSet<Coordinate> generateRightZShipCoordinates(Coordinate coordinate) {
        int row = coordinate.getRow(), col = coordinate.getCol();
        HashSet<Coordinate> coords = new HashSet<>();
        coords.add(new Coordinate(row, col + 1));
        coords.add(new Coordinate(row, col + 2));
        coords.add(new Coordinate(row, col + 3));
        coords.add(new Coordinate(row, col + 4));
        coords.add(new Coordinate(row + 1, col));
        coords.add(new Coordinate(row + 1, col + 1));
        coords.add(new Coordinate(row + 1, col + 2));
        return coords;
    }

    private HashSet<Coordinate> generateUpZShipCoordinates(Coordinate coordinate) {
        int row = coordinate.getRow(), col = coordinate.getCol();
        HashSet<Coordinate> coords = new HashSet<>();
        coords.add(new Coordinate(row, col));
        coords.add(new Coordinate(row + 1, col));
        coords.add(new Coordinate(row + 2, col));
        coords.add(new Coordinate(row + 3, col));
        coords.add(new Coordinate(row + 2, col + 1));
        coords.add(new Coordinate(row + 3, col + 1));
        coords.add(new Coordinate(row + 4, col + 1));
        return coords;
    }

    private HashSet<Coordinate> generateDownZShipCoordinates(Coordinate coordinate) {
        int row = coordinate.getRow(), col = coordinate.getCol();
        HashSet<Coordinate> coords = new HashSet<>();
        coords.add(new Coordinate(row, col));
        coords.add(new Coordinate(row + 1, col));
        coords.add(new Coordinate(row + 2, col));
        coords.add(new Coordinate(row + 1, col + 1));
        coords.add(new Coordinate(row + 2, col + 1));
        coords.add(new Coordinate(row + 3, col + 1));
        coords.add(new Coordinate(row + 4, col + 1));
        return coords;
    }

    @Test
    void test_make_coords() {
        Coordinate upperLeft = new Coordinate(4, 3);
        HashSet<Coordinate> upCoords = generateUpZShipCoordinates(upperLeft);
        HashSet<Coordinate> downCoords = generateDownZShipCoordinates(upperLeft);
        HashSet<Coordinate> leftCoords = generateLeftZShipCoordinates(upperLeft);
        HashSet<Coordinate> rightCoords = generateRightZShipCoordinates(upperLeft);
        assertEquals(upCoords, ZShip.makeCoords(upperLeft, 'U'));
        assertEquals(downCoords, ZShip.makeCoords(upperLeft, 'D'));
        assertEquals(leftCoords, ZShip.makeCoords(upperLeft, 'L'));
        assertEquals(rightCoords, ZShip.makeCoords(upperLeft, 'R'));
        assertThrows(IllegalArgumentException.class, () -> ZShip.makeCoords(upperLeft, 'F'));
    }

    @Test
    void test_Z_ship_pieces() {
        Coordinate upperLeft1 = new Coordinate(2, 3);
        HashSet<Coordinate> coords = generateLeftZShipCoordinates(upperLeft1);
        ZShip<Character> s1 = new ZShip<>(testShip, upperLeft1, 'L', myData, onHit);
        HashMap<Coordinate, Boolean> expected = new HashMap<>();
        for (Coordinate coord : coords) {
            expected.put(coord, false);
        }
        assertEquals(expected, s1.myPieces);
        assertEquals(testShip, s1.getName());
    }

    @Test
    void test_to_string() {
        Coordinate c1 = new Coordinate(1, 1);
        ZShip<Character> s1 = new ZShip<Character>(testShip, c1,'U', myData, onHit);
        String expected = "ZShip{" +
                "name='" + testShip + '\'' +
                ", myPieces=" + s1.myPieces.toString() +
                '}';
        assertEquals(expected, s1.toString());
    }

}