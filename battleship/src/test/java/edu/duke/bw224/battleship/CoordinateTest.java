package edu.duke.bw224.battleship;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CoordinateTest {

    @Test
    void test_row_and_col() {
        Coordinate c1 = new Coordinate(1,2);
        assertEquals(1, c1.getRow());
        assertEquals(2, c1.getCol());
    }

    @Test
    void test_string_constructor_valid_cases() {
        Coordinate c1 = new Coordinate("B3");
        assertEquals(1, c1.getRow());
        assertEquals(3, c1.getCol());
        Coordinate c2 = new Coordinate("D5");
        assertEquals(3, c2.getRow());
        assertEquals(5, c2.getCol());
        Coordinate c3 = new Coordinate("A9");
        assertEquals(0, c3.getRow());
        assertEquals(9, c3.getCol());
        Coordinate c4 = new Coordinate("Z0");
        assertEquals(25, c4.getRow());
        assertEquals(0, c4.getCol());
        assertDoesNotThrow(() -> new Coordinate("a9"));
    }
    @Test
    public void test_string_constructor_error_cases() {
        assertThrows(IllegalArgumentException.class, () -> new Coordinate("00"));
        assertThrows(IllegalArgumentException.class, () -> new Coordinate("AA"));
        assertThrows(IllegalArgumentException.class, () -> new Coordinate("@0"));
        assertThrows(IllegalArgumentException.class, () -> new Coordinate("[0"));
        assertThrows(IllegalArgumentException.class, () -> new Coordinate("A/"));
        assertThrows(IllegalArgumentException.class, () -> new Coordinate("A:"));
        assertThrows(IllegalArgumentException.class, () -> new Coordinate("A"));
        assertThrows(IllegalArgumentException.class, () -> new Coordinate("AB1"));
        assertThrows(IllegalArgumentException.class, () -> new Coordinate("A12"));

    }


    @Test
    void test_equals() {
        Coordinate c1 = new Coordinate(1, 2);
        Coordinate c2 = new Coordinate(1, 2);
        Coordinate c3 = new Coordinate(1, 3);
        Coordinate c4 = new Coordinate(3, 2);
        assertEquals(c1, c1);   //equals should be reflexive
        assertEquals(c1, c2);   //diff objs but same contents
        assertNotEquals(c1, c3);    //diff contents
        assertNotEquals(c1, c4);
        assertNotEquals(c3, c4);
        assertNotEquals(c1, "(1, 2)");  //diff types
    }

    @Test
    void test_toString() {
        Coordinate c1 = new Coordinate(1, -1);
        assertEquals("(1, -1)", c1.toString());
    }

    @Test
    void test_hashCode() {
        Coordinate c1 = new Coordinate(1, 2);
        Coordinate c2 = new Coordinate(1, 2);
        Coordinate c3 = new Coordinate(0, 3);
        Coordinate c4 = new Coordinate(2, 1);
        assertEquals(c1.hashCode(), c2.hashCode());
        assertNotEquals(c1.hashCode(), c3.hashCode());
        assertNotEquals(c1.hashCode(), c4.hashCode());
    }
}