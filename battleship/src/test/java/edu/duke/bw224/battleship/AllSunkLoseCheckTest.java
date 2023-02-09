package edu.duke.bw224.battleship;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AllSunkLoseCheckTest {
    private final V1ShipFactory factory = new V1ShipFactory();

    @Test
    void test_all_sunk_win_check() {
        AllSunkLoseCheck<Character> allSunkLoseCheck = new AllSunkLoseCheck<>(null);
        BattleShipBoard<Character> board = new BattleShipBoard<>(10, 20, 'X');
        // empty board
        assertTrue(allSunkLoseCheck.checkLose(board));
        //add ships
        Placement pA0H = new Placement("a0h");
        Ship<Character> s1 = factory.makeSubmarine(pA0H);
        assertNull(board.tryAddShip(s1));
        for (Coordinate c : s1.getCoordinates()) {
            assertFalse(s1.isSunk());
            assertFalse(allSunkLoseCheck.checkLose(board));
            board.fireAt(c);
        }
        assertTrue(allSunkLoseCheck.checkLose(board));

        //test chaining
        AllSunkLoseCheck<Character> allSunkWinCheckChain = new AllSunkLoseCheck<>(allSunkLoseCheck);
        assertTrue(allSunkWinCheckChain.checkLose(board));
    }
}