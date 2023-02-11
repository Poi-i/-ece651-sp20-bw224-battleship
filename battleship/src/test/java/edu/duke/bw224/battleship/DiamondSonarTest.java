package edu.duke.bw224.battleship;

import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.HashSet;
import java.util.TreeSet;

import static org.junit.jupiter.api.Assertions.*;

class DiamondSonarTest {

    @Test
    void test_sonar_construct() {
        Sonar<Character> sonar = new DiamondSonar<>(new Coordinate(3, 3), 7);
        assertEquals(7, sonar.getSize());
        assertEquals(new Coordinate(3, 3), sonar.getCenter());
        assertTrue(sonar.getScanResult().isEmpty());
        assertTrue(sonar.getReport().isEmpty());
    }

    @Test
    void test_generate_valid_scan_area() {
        BattleShipBoard<Character> b = new BattleShipBoard<>(7, 7, 'X');
        TreeSet<Coordinate> sorted = new TreeSet<>((c1, c2) -> (c1.getRow() != c2.getRow()) ?
                Integer.compare(c1.getRow(), c2.getRow()) : Integer.compare(c1.getCol(), c2.getCol()));
        Sonar<Character> sonar1 = new DiamondSonar<>(new Coordinate(0, 0), 3);
        sorted.addAll(sonar1.generateValidScanArea(b));
        assertEquals("[(0, 0), (0, 1), (1, 0)]", sorted.toString());
        sorted.clear();
        Sonar<Character> sonar2 = new DiamondSonar<>(new Coordinate(2, 3), 3);
        sorted.addAll(sonar2.generateValidScanArea(b));
        assertEquals("[(1, 3), (2, 2), (2, 3), (2, 4), (3, 3)]", sorted.toString());
    }

    @Test
    void test_scan_and_report() {
        V2ShipFactory factory = new V2ShipFactory();
        BattleShipBoard<Character> b = new BattleShipBoard<>(7, 7, 'X');
        b.tryAddShip(factory.makeBattleship(new Placement("D2D")));
        Sonar<Character> sonar1 = new DiamondSonar<>(new Coordinate(3, 3), 3);
        HashMap<String, Integer> expected = new HashMap<>();
        expected.put("Battleship", 4);
        assertEquals(expected, sonar1.scan(b));
        Sonar<Character> sonar2 = new DiamondSonar<>(new Coordinate(3, 2), 3);
        expected.put("Battleship", 2);
        assertEquals(expected, sonar2.scan(b));
        b.tryAddShip(factory.makeCarrier(new Placement("B0U")));
        Sonar<Character> sonar3 = new DiamondSonar<>(new Coordinate(3, 2), 3);
        expected.put("Carrier", 1);
        assertEquals(expected, sonar3.scan(b));
        String expectedReport = "Carrier occupy 1 squares\n" +
                "Battleship occupy 2 squares\n";
        assertEquals(expectedReport, sonar3.generateReport());
    }
}