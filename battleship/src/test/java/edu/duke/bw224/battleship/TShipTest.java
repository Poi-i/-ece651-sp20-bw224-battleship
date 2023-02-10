package edu.duke.bw224.battleship;

import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.HashSet;

import static edu.duke.bw224.battleship.RectangleShip.makeCoords;
import static org.junit.jupiter.api.Assertions.*;

class TShipTest {
    private final char myData = 's';
    private final char onHit = '*';
    private final String testShip = "testShip";

    @Test
    void test_make_up_TShip() {
        Coordinate upperLeft1 = new Coordinate(1, 2);
        HashSet<Coordinate> expected = new HashSet<>();
        expected.add(new Coordinate(1, 3));
        expected.add(new Coordinate(2, 3));
        expected.add(new Coordinate(2, 2));
        expected.add(new Coordinate(2, 4));
        assertEquals(expected, TShip.makeCoords(upperLeft1, 2, 'U'));
        expected.clear();
        expected.add(new Coordinate(1, 3));
        expected.add(new Coordinate(2, 3));
        expected.add(new Coordinate(3, 3));
        expected.add(new Coordinate(3, 2));
        expected.add(new Coordinate(3, 4));
        assertEquals(expected, TShip.makeCoords(upperLeft1, 3, 'U'));
        Coordinate upperLeft2 = new Coordinate(0, 1);
        expected.add(new Coordinate(0, 3));
        expected.add(new Coordinate(3, 1));
        expected.add(new Coordinate(3, 5));
        assertEquals(expected, TShip.makeCoords(upperLeft2, 4, 'U'));
    }

    @Test
    void test_make_right_TShip() {
        Coordinate upperLeft1 = new Coordinate(1, 1);
        HashSet<Coordinate> expected = new HashSet<>();
        expected.add(new Coordinate(1, 1));
        expected.add(new Coordinate(2, 1));
        expected.add(new Coordinate(3, 1));
        expected.add(new Coordinate(2, 2));
        assertEquals(expected, TShip.makeCoords(upperLeft1, 2, 'R'));
        expected.add(new Coordinate(2, 3));
        assertEquals(expected, TShip.makeCoords(upperLeft1, 3, 'R'));
        Coordinate upperLeft2 = new Coordinate(0, 1);
        expected.add(new Coordinate(0, 1));
        expected.add(new Coordinate(4, 1));
        expected.add(new Coordinate(2, 4));
        assertEquals(expected, TShip.makeCoords(upperLeft2, 4, 'R'));
    }

    @Test
    void test_make_down_TShip() {
        Coordinate upperLeft1 = new Coordinate(2, 3);
        HashSet<Coordinate> expected = new HashSet<>();
        expected.add(new Coordinate(2, 3));
        expected.add(new Coordinate(2, 4));
        expected.add(new Coordinate(2, 5));
        expected.add(new Coordinate(3, 4));
        assertEquals(expected, TShip.makeCoords(upperLeft1, 2, 'D'));
        expected.add(new Coordinate(4, 4));
        assertEquals(expected, TShip.makeCoords(upperLeft1, 3, 'D'));
        Coordinate upperLeft2 = new Coordinate(2, 2);
        expected.add(new Coordinate(2, 2));
        expected.add(new Coordinate(2, 6));
        expected.add(new Coordinate(5, 4));
        assertEquals(expected, TShip.makeCoords(upperLeft2, 4, 'D'));
    }

    @Test
    void test_make_left_TShip() {
        Coordinate upperLeft1 = new Coordinate(3, 2);
        HashSet<Coordinate> expected = new HashSet<>();
        expected.add(new Coordinate(3, 3));
        expected.add(new Coordinate(4, 3));
        expected.add(new Coordinate(5, 3));
        expected.add(new Coordinate(4, 2));
        assertEquals(expected, TShip.makeCoords(upperLeft1, 2, 'L'));
        expected.add(new Coordinate(4, 1));
        Coordinate upperLeft2 = new Coordinate(3, 1);
        assertEquals(expected, TShip.makeCoords(upperLeft2, 3, 'L'));
        Coordinate upperLeft3 = new Coordinate(2, 0);
        expected.add(new Coordinate(2, 3));
        expected.add(new Coordinate(6, 3));
        expected.add(new Coordinate(4, 0));
        assertEquals(expected, TShip.makeCoords(upperLeft3, 4, 'L'));
    }

    @Test
    void test_make_coords() {
        Coordinate upperLeft1 = new Coordinate(3, 2);
        //test wrong height
        assertThrows(IllegalArgumentException.class, () -> TShip.makeCoords(upperLeft1, 0, 'R'));
        //test wrong orientation
        assertThrows(IllegalArgumentException.class, () -> TShip.makeCoords(upperLeft1, 2, 'F'));
    }

    @Test
    void test_T_ship_pieces() {
        Coordinate upperLeft1 = new Coordinate(3, 2);
        HashSet<Coordinate> coords = new HashSet<>();
        coords.add(new Coordinate(3, 3));
        coords.add(new Coordinate(4, 3));
        coords.add(new Coordinate(5, 3));
        coords.add(new Coordinate(4, 2));
        TShip<Character> s1 = new TShip<Character>(testShip, upperLeft1, 2, 'L', myData, onHit);
        HashMap<Coordinate, Boolean> expected = new HashMap<Coordinate, Boolean>();
        for (Coordinate coord : coords) {
            expected.put(coord, false);
        }
        assertEquals(expected, s1.myPieces);
        assertEquals(testShip, s1.getName());
    }

    @Test
    void test_to_string() {
        Coordinate c1 = new Coordinate(1, 1);
        TShip<Character> s1 = new TShip<Character>(testShip, c1, 2, 'U', myData, onHit);
        String expected = "TShip{" +
                "name='" + testShip + '\'' +
                ", myPieces=" + s1.myPieces.toString() +
                '}';
        assertEquals(expected, s1.toString());
    }


}