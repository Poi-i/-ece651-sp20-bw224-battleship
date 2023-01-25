package edu.duke.bw224.battleship;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PlacementTest {
    @Test
    void test_where_and_orientation() {
        Coordinate c = new Coordinate("A0");
        Placement p1 = new Placement(c, 'V');
        assertEquals(c, p1.getWhere());
        assertEquals('V', p1.getOrientation());
        Placement p2 = new Placement(c, 'h');
        assertEquals('H', p2.getOrientation());
        assertThrows(IllegalArgumentException.class, () -> new Placement(c, 'A'));
        assertDoesNotThrow(() -> new Placement(c, 'H'));
    }

    @Test
    void test_string_constructor_valid_cases() {
        Coordinate c1 = new Coordinate("A0");
        Placement p1 = new Placement("A0v");
        assertEquals(c1, p1.getWhere());
        assertEquals('V', p1.getOrientation());
        Coordinate c2 = new Coordinate(2, 3);
        Placement p2 = new Placement("C3h");
        assertEquals(c2, p2.getWhere());
        assertEquals('H', p2.getOrientation());
        Coordinate c3 = new Coordinate("Z0");
        Placement p3 = new Placement("Z0V");
        assertEquals(c3, p3.getWhere());
        assertEquals('V', p3.getOrientation());

    }

    @Test
    public void test_string_constructor_error_cases() {
        assertThrows(IllegalArgumentException.class, () -> new Placement("00V"));
        assertThrows(IllegalArgumentException.class, () -> new Placement("AAV"));
        assertThrows(IllegalArgumentException.class, () -> new Placement("@0V"));
        assertThrows(IllegalArgumentException.class, () -> new Placement("[0H"));
        assertThrows(IllegalArgumentException.class, () -> new Placement("A/H"));
        assertThrows(IllegalArgumentException.class, () -> new Placement("A:H"));
        assertThrows(IllegalArgumentException.class, () -> new Placement("A"));
        assertThrows(IllegalArgumentException.class, () -> new Placement("A1:"));
        assertThrows(IllegalArgumentException.class, () -> new Placement("A1G"));
        assertThrows(IllegalArgumentException.class, () -> new Placement("A1G"));
        assertThrows(IllegalArgumentException.class, () -> new Placement("A1HH"));
    }

    @Test
    void test_equals() {
        Coordinate c1 = new Coordinate(1, 2);
        Coordinate c2 = new Coordinate(1, 3);
        Placement p1 = new Placement(c1, 'H');
        Placement p2 = new Placement(c1, 'h');
        Placement p3 = new Placement(c1, 'V');
        Placement p4 = new Placement(c2, 'H');
        assertEquals(p1, p1);   //equals should be reflexive
        assertEquals(p1, p2);   //diff objs but same contents
        //diff contents
        assertNotEquals(p1, p3);    //same coor, diff orientation
        assertNotEquals(p1, p4);    //same orientation, diff coor
        assertNotEquals(p3, p4);    //diff coor, diff orientation
        assertNotEquals(p1, "12H");  //diff types
    }

    @Test
    void test_toString() {
        Coordinate c = new Coordinate(1,1);
        Placement p = new Placement(c, 'V');
        assertEquals("(1, 1): V", p.toString());
    }

    @Test
    void test_hashCode() {
        Coordinate c1 = new Coordinate(1, 2);
        Coordinate c2 = new Coordinate(1, 3);
        Placement p1 = new Placement(c1, 'H');
        Placement p2 = new Placement(c1, 'H');
        Placement p3 = new Placement(c1, 'V');
        Placement p4 = new Placement(c2, 'H');
        assertEquals(p1.hashCode(), p2.hashCode());
        assertNotEquals(p1.hashCode(), p3.hashCode());
        assertNotEquals(p1.hashCode(), p4.hashCode());
    }
}