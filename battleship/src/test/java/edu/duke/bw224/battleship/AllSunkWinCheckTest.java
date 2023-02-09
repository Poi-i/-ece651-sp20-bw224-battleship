package edu.duke.bw224.battleship;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AllSunkWinCheckTest {
    private final V1ShipFactory factory = new V1ShipFactory();

    @Test
    void test_all_sunk_win_check() {
        AllSunkWinCheck<Character> allSunkWinCheck = new AllSunkWinCheck<>(null);
        BattleShipBoard<Character> board = new BattleShipBoard<>(10, 20, 'X');
        // empty board
        assertTrue(allSunkWinCheck.checkWin(board));
        //add ships
        Placement pA0H = new Placement("a0h");
        Ship<Character> s1 = factory.makeSubmarine(pA0H);
        assertNull(board.tryAddShip(s1));
        for (Coordinate c : s1.getCoordinates()) {
            assertFalse(s1.isSunk());
            assertFalse(allSunkWinCheck.checkWin(board));
            board.fireAt(c);
        }
        assertTrue(allSunkWinCheck.checkWin(board));

        //test chaining
        AllSunkWinCheck<Character> allSunkWinCheckChain = new AllSunkWinCheck<>(allSunkWinCheck);
        assertTrue(allSunkWinCheckChain.checkWin(board));
    }
}