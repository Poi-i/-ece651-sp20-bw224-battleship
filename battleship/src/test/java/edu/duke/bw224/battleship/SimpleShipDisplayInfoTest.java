package edu.duke.bw224.battleship;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SimpleShipDisplayInfoTest {
    @Test
    void test_get_info() {
        Coordinate c = new Coordinate(0, 1);
        SimpleShipDisplayInfo<Character>  displayInfo = new SimpleShipDisplayInfo<>('s', 'x');
        assertEquals('s', displayInfo.getInfo(c, false));
        assertEquals('x', displayInfo.getInfo(c, true));

    }
}