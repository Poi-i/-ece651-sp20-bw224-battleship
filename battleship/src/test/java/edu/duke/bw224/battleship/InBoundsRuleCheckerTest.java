package edu.duke.bw224.battleship;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class InBoundsRuleCheckerTest {
    @Test
    void test_in_bound_rule_checker() {
        InBoundsRuleChecker<Character> inBoundsRuleChecker= new InBoundsRuleChecker<>(null);
        BattleShipBoard<Character> board = new BattleShipBoard<>(10, 20);
        V1ShipFactory shipFactory = new V1ShipFactory();
        //test valid placement
        Placement v1_2 = new Placement(new Coordinate(1, 2), 'V');
        Ship<Character> valid_sbm = shipFactory.makeSubmarine(v1_2);
        assertTrue(inBoundsRuleChecker.checkPlacement(valid_sbm, board));
        //invalid row placement
        Placement h_11_10 = new Placement(new Coordinate(11, 10), 'H');
        Ship<Character> invalid_row = shipFactory.makeSubmarine(h_11_10);
        assertFalse(inBoundsRuleChecker.checkPlacement(invalid_row, board));
        //invalid col placement
        Placement h_9_21 = new Placement(new Coordinate(9, 21), 'H');
        Ship<Character> invalid_col = shipFactory.makeBattleship(h_9_21);
        assertFalse(inBoundsRuleChecker.checkPlacement(invalid_col, board));
        //both invalid
        Placement v_12_22 = new Placement(new Coordinate(12, 22), 'V');
        Ship<Character> invalid_both = shipFactory.makeBattleship(v_12_22);
        assertFalse(inBoundsRuleChecker.checkMyRule(invalid_both, board));
    }
}