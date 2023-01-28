package edu.duke.bw224.battleship;

import org.checkerframework.checker.units.qual.C;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class V1ShipFactoryTest {
    private void checkShip(Ship<Character> testShip, String expectedName, char expectedLetter, Coordinate... expectedLocs) {
        assertEquals(expectedName, testShip.getName());
        System.out.println(testShip.toString());
        for (Coordinate expectedLoc : expectedLocs) {
            assertTrue(testShip.occupiesCoordinates(expectedLoc));
            assertEquals(expectedLetter, testShip.getDisplayInfoAt(expectedLoc));
        }
    }

    @Test
    void test_ship_factory() {
        V1ShipFactory f = new V1ShipFactory();
        Placement v1_2 = new Placement(new Coordinate(1, 2), 'V');
        Placement h0_1 = new Placement(new Coordinate(0, 1), 'H');
        Ship<Character> sbm = f.makeSubmarine(v1_2);
        checkShip(sbm, "Submarine", 's', new Coordinate(1, 2), new Coordinate(2, 2));

        Ship<Character> dst = f.makeDestroyer(v1_2);
        checkShip(dst, "Destroyer", 'd', new Coordinate(1, 2), new Coordinate(2, 2),
                new Coordinate(3, 2));

        Ship<Character> bts = f.makeBattleship(h0_1);
        checkShip(bts, "Battleship", 'b', new Coordinate(0, 1), new Coordinate(0, 2),
                new Coordinate(0, 3), new Coordinate(0, 4));

        Ship<Character> crr = f.makeCarrier(h0_1);
        checkShip(crr, "Carrier", 'c', new Coordinate(0, 1), new Coordinate(0, 2),
                new Coordinate(0, 3), new Coordinate(0, 4),
                new Coordinate(0, 5), new Coordinate(0, 6));

    }
}