package edu.duke.bw224.battleship;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class OrientationRuleCheckerTest {
    private final V1ShipFactory factory = new V1ShipFactory();

    @Test
    void test_orientation_rule() {
        OrientationRuleChecker<Character> orientationRuleChecker = new OrientationRuleChecker();
        String battleship = "Battleship";
        String submarine = "Submarine";
        String carrier = "Carrier";
        String destroyer = "Destroyer";
        // correct: test ULRD for battleship & carrier
        assertNull(orientationRuleChecker.checkMyRule(battleship, 'U'));
        assertNull(orientationRuleChecker.checkMyRule(battleship, 'L'));
        assertNull(orientationRuleChecker.checkMyRule(carrier, 'R'));
        assertNull(orientationRuleChecker.checkMyRule(carrier, 'D'));
        // correct: test VH for submarine & destroyer
        assertNull(orientationRuleChecker.checkMyRule(submarine, 'V'));
        assertNull(orientationRuleChecker.checkMyRule(destroyer, 'H'));
        // wrong: test invalid direction for battleship & carrier
        String battleshipMsg = orientationRuleChecker.message.formatted("Battleship", "U, L, R, or D");
        String carrierMsg = orientationRuleChecker.message.formatted("Carrier", "U, L, R, or D");
        assertEquals(battleshipMsg, orientationRuleChecker.checkMyRule(battleship, 'V'));
        assertEquals(battleshipMsg, orientationRuleChecker.checkMyRule(battleship, 'C'));
        assertEquals(carrierMsg, orientationRuleChecker.checkMyRule(carrier, 'H'));
        // wrong: test invalid direction for submarine & destroyer
        String submarineMsg = orientationRuleChecker.message.formatted("Submarine", "V or H");
        String destroyerMsg = orientationRuleChecker.message.formatted("Destroyer", "V or H");
        assertEquals(submarineMsg, orientationRuleChecker.checkMyRule(submarine, 'U'));
        assertEquals(submarineMsg, orientationRuleChecker.checkMyRule(submarine, 'O'));
        assertEquals(destroyerMsg, orientationRuleChecker.checkMyRule(destroyer, 'D'));
    }
}