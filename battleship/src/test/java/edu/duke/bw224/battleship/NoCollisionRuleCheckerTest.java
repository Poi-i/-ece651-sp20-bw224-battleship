package edu.duke.bw224.battleship;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class NoCollisionRuleCheckerTest {
    private final V1ShipFactory shipFactory = new V1ShipFactory();


    /**
     * helper function to test chain or isolated NoCollisionRuleChecker
     * @param next the next rule checker
     */
    private void no_collision_rule_helper(PlacementRuleChecker<Character> next) {
        NoCollisionRuleChecker<Character> noCollisionRuleChecker = new NoCollisionRuleChecker<>(next);
        BattleShipBoard<Character> board = new BattleShipBoard<>(10, 20, 'X');
        //test valid placement
        Placement v1_2 = new Placement(new Coordinate(1, 2), 'V');
        Ship<Character> valid_sbm = shipFactory.makeSubmarine(v1_2);
        assertNull(noCollisionRuleChecker.checkPlacement(valid_sbm, board));
        //test collision
        assertNull(board.tryAddShip(valid_sbm));
        Placement v2_2 = new Placement(new Coordinate(2, 2), 'V');
        Ship<Character> collide_sum = shipFactory.makeSubmarine(v1_2);
        assertEquals(noCollisionRuleChecker.message, noCollisionRuleChecker.checkPlacement(collide_sum, board));
    }

    @Test
    void test_no_collision_rule() {
        no_collision_rule_helper(null);
    }

    @Test
    void test_rule_checker_chain() {
        no_collision_rule_helper(new InBoundsRuleChecker<>(null));
    }
}