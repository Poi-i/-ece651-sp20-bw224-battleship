package edu.duke.bw224.battleship;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class InBoundsRuleCheckerTest {

    @Test
    void test_in_bound_rule() {
        InBoundsRuleChecker<Character> inBoundsRuleChecker= new InBoundsRuleChecker<>(null);
        BattleShipBoard<Character> board = new BattleShipBoard<>(10, 20);
        V1ShipFactory shipFactory = new V1ShipFactory();
        //test valid placement
        Placement v1_2 = new Placement(new Coordinate(1, 2), 'V');
        Ship<Character> valid_sbm = shipFactory.makeSubmarine(v1_2);
        assertNull(inBoundsRuleChecker.checkPlacement(valid_sbm, board));
        //invalid top placement
        Placement h_minus1_1 = new Placement(new Coordinate(-1, 1), 'H');
        Ship<Character> invalid_top = shipFactory.makeSubmarine(h_minus1_1);
        assertEquals(inBoundsRuleChecker.invalidTopMsg, inBoundsRuleChecker.checkPlacement(invalid_top, board));
        //invalid bottom placement
        Placement h_21_1 = new Placement(new Coordinate(21, 1), 'H');
        Ship<Character> invalid_bottom = shipFactory.makeSubmarine(h_21_1);
        assertEquals(inBoundsRuleChecker.invalidBottomMsg, inBoundsRuleChecker.checkPlacement(invalid_bottom, board));
        //invalid right placement
        Placement h_9_11 = new Placement(new Coordinate(9, 11), 'H');
        Ship<Character> invalid_right = shipFactory.makeBattleship(h_9_11);
        assertEquals(inBoundsRuleChecker.invalidRightMsg, inBoundsRuleChecker.checkPlacement(invalid_right, board));
        //invalid left placement
        Placement h_9_minus1 = new Placement(new Coordinate(9, -1), 'H');
        Ship<Character> invalid_left = shipFactory.makeBattleship(h_9_minus1);
        assertEquals(inBoundsRuleChecker.invalidLeftMsg, inBoundsRuleChecker.checkPlacement(invalid_left, board));
        //row and col both invalid
        Placement v_22_12 = new Placement(new Coordinate(22, 12), 'V');
        Ship<Character> invalid_both = shipFactory.makeBattleship(v_22_12);
        assertEquals(inBoundsRuleChecker.invalidRightMsg, inBoundsRuleChecker.checkMyRule(invalid_both, board));
        //partial ship invalid
        Placement h_9_9 = new Placement(new Coordinate(9, 9), 'H');
        Ship<Character> partial_invalid = shipFactory.makeBattleship(h_9_9);
        assertEquals(inBoundsRuleChecker.invalidRightMsg, inBoundsRuleChecker.checkMyRule(partial_invalid, board));
    }
}