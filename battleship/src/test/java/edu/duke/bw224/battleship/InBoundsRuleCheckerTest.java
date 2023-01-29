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
        assertTrue(inBoundsRuleChecker.checkPlacement(valid_sbm, board));
        //invalid row placement
        Placement h_21_1 = new Placement(new Coordinate(21, 1), 'H');
        Ship<Character> invalid_row = shipFactory.makeSubmarine(h_21_1);
        assertFalse(inBoundsRuleChecker.checkPlacement(invalid_row, board));
        //invalid col placement
        Placement h_9_11 = new Placement(new Coordinate(9, 11), 'H');
        Ship<Character> invalid_col = shipFactory.makeBattleship(h_9_11);
        assertFalse(inBoundsRuleChecker.checkPlacement(invalid_col, board));
        //both invalid
        Placement v_22_12 = new Placement(new Coordinate(22, 12), 'V');
        Ship<Character> invalid_both = shipFactory.makeBattleship(v_22_12);
        assertFalse(inBoundsRuleChecker.checkMyRule(invalid_both, board));
        //partial ship invalid
        Placement h_9_9 = new Placement(new Coordinate(9, 9), 'H');
        Ship<Character> partial_invalid = shipFactory.makeBattleship(h_9_9);
        assertFalse(inBoundsRuleChecker.checkMyRule(partial_invalid, board));
    }
}