package edu.duke.bw224.battleship;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

import static edu.duke.bw224.battleship.RectangleShip.makeCoords;
import static org.junit.jupiter.api.Assertions.*;

class RectangleShipTest {

    private final char myData = 's';
    private final char onHit = '*';

    private HashSet<Coordinate> produceRectangleCoords(Coordinate upperLeft, int width, int height) {
        HashSet<Coordinate> expected = new HashSet<>();
        int rowStart = upperLeft.getRow(), colStart = upperLeft.getCol();
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                expected.add(new Coordinate(rowStart + i, colStart + j));
            }
        }
        return expected;
    }
    private void makeCoordsHelper(Coordinate upperLeft, int width, int height) {
        if (width <= 0 || height <= 0) {
            assertThrows(IllegalArgumentException.class, () -> makeCoords(upperLeft, width, height));
            return;
        }
        HashSet<Coordinate> expected = produceRectangleCoords(upperLeft, width, height);
        assertEquals(expected, makeCoords(upperLeft, width, height));
    }
    @Test
    void test_make_coords() {
        //test one row
        Coordinate upperLeft1 = new Coordinate(0, 0);
        makeCoordsHelper(upperLeft1, 1, 3);
        //test one col
        Coordinate upperLeft2 = new Coordinate(2, 3);
        makeCoordsHelper(upperLeft2, 3, 1);
        //test block
        makeCoordsHelper(upperLeft1, 4, 4);
        //test invalid arg
        makeCoordsHelper(upperLeft1, -1, 5);
        makeCoordsHelper(upperLeft1, 1, -5);
        makeCoordsHelper(upperLeft1, 0, 5);

    }

    @Test
    void test_rectangle_ship_pieces() {
        Coordinate c1 = new Coordinate(1,1);
        int w = 1, h = 3;
        // test valid ship
        RectangleShip<Character> s1 = new RectangleShip<>(c1, w, h, myData, onHit);
        HashSet<Coordinate> coords = produceRectangleCoords(c1, w, h);
        HashMap<Coordinate, Boolean> expected = new HashMap<>();
        for (Coordinate coord : coords) {
            expected.put(coord, false);
        }
        assertEquals(expected, s1.myPieces);
        // test invalid ship
        assertThrows(IllegalArgumentException.class, () -> new RectangleShip<Character>(c1, 0, -1, myData, onHit));
    }

    @Test
    void test_record_hit_at() {
        Coordinate upperLeft1 = new Coordinate(1, 1);
        RectangleShip<Character> s1 = new RectangleShip<>(upperLeft1, 1, 3, myData, onHit);
        ArrayList<Coordinate> coords = new ArrayList<>(produceRectangleCoords(upperLeft1, 1, 3));
        assertThrows(IllegalArgumentException.class, () -> s1.recordHitAt(new Coordinate(0, 0)));
        for(int i = 0; i < coords.size(); i++){
            s1.recordHitAt(coords.get(i));
            assertTrue(s1.myPieces.get(coords.get(i)));
            for(int j = i + 1; j < coords.size(); j++){
                assertFalse(s1.myPieces.get(coords.get(j)));
            }
        }
    }

    @Test
    void test_was_hit_at() {
        Coordinate upperLeft1 = new Coordinate(1, 1);
        RectangleShip<Character> s1 = new RectangleShip<>(upperLeft1, 1, 3, myData, onHit);
        ArrayList<Coordinate> coords = new ArrayList<>(s1.myPieces.keySet());
        //test illegal coordinate
        assertThrows(IllegalArgumentException.class, () -> s1.recordHitAt(new Coordinate(0, 0)));
        for(int i = 0; i < coords.size(); i++){
            s1.recordHitAt(coords.get(i));
            assertTrue(s1.wasHitAt(coords.get(i)));
            for(int j = i + 1; j < coords.size(); j++){
                assertFalse(s1.wasHitAt(coords.get(j)));
            }
        }
    }

    @Test
    void test_is_sunk() {
        Coordinate upperLeft1 = new Coordinate(1, 1);
        RectangleShip<Character> s1 = new RectangleShip<>(upperLeft1, 1, 3, myData, onHit);
        ArrayList<Coordinate> coords = new ArrayList<>(s1.myPieces.keySet());
        for(int i = 0; i < coords.size(); i++){
            s1.recordHitAt(coords.get(i));
            if (i != coords.size() - 1) {
                assertFalse(s1.isSunk());
            }
            else{
                assertTrue(s1.isSunk());
            }
        }
    }

    @Test
    void test_get_display_info() {
        Coordinate upperLeft1 = new Coordinate(1, 1);
        RectangleShip<Character> s1 = new RectangleShip<>(upperLeft1, 1, 3, myData, onHit);
        ArrayList<Coordinate> coords = new ArrayList<>(s1.myPieces.keySet());
        for(int i = 0; i < coords.size(); i++){
            s1.recordHitAt(coords.get(i));
            assertEquals(onHit, s1.getDisplayInfoAt(coords.get(i)));
            for(int j = i + 1; j < coords.size(); j++){
                assertEquals(myData, s1.getDisplayInfoAt(coords.get(j)));
            }
        }
    }
}