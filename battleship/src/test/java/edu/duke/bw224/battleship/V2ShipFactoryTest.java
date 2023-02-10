package edu.duke.bw224.battleship;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class V2ShipFactoryTest {

    private void checkShip(Ship<Character> testShip, String expectedName, char expectedLetter, Coordinate... expectedLocs) {
        assertEquals(expectedName, testShip.getName());
        for (Coordinate expectedLoc : expectedLocs) {
            assertTrue(testShip.occupiesCoordinates(expectedLoc));
            assertEquals(expectedLetter, testShip.getDisplayInfoAt(expectedLoc, true));
        }
    }

    @Test
    void test_ship_factory() {
        V1ShipFactory f = new V2ShipFactory();
        Placement v1_2 = new Placement(new Coordinate(1, 2), 'V');
        Placement h0_1 = new Placement(new Coordinate(0, 1), 'H');
        Placement u0_1 = new Placement(new Coordinate(0, 1), 'u');
        Placement d3_2 = new Placement(new Coordinate(3, 2), 'd');
        Ship<Character> sbm = f.makeSubmarine(v1_2);
        checkShip(sbm, "Submarine", 's', new Coordinate(1, 2), new Coordinate(2, 2));

        Ship<Character> dst = f.makeDestroyer(v1_2);
        checkShip(dst, "Destroyer", 'd', new Coordinate(1, 2), new Coordinate(2, 2),
                new Coordinate(3, 2));

        Ship<Character> bts = f.makeBattleship(u0_1);
        checkShip(bts, "Battleship", 'b', new Coordinate(0, 2), new Coordinate(1, 1),
                new Coordinate(1, 2), new Coordinate(1, 3));

        Ship<Character> crr = f.makeCarrier(d3_2);
        checkShip(crr, "Carrier", 'c', new Coordinate(3, 2), new Coordinate(4, 2),
                new Coordinate(5, 2), new Coordinate(4, 3), new Coordinate(5, 3),
                new Coordinate(6, 3), new Coordinate(7, 3));

    }

}