package edu.duke.bw224.battleship;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class BattleShipBoardTest {

    @Test
    public void test_width_and_height() {
        Board<Character> b1 = new BattleShipBoard<>(10, 20);
        assertEquals(10, b1.getWidth());
        assertEquals(20, b1.getHeight());
    }

    @Test
    public void test_invalid_dimensions() {
        assertThrows(IllegalArgumentException.class, () -> new BattleShipBoard<>(10, 0));
        assertThrows(IllegalArgumentException.class, () -> new BattleShipBoard<>(0, 20));
        assertThrows(IllegalArgumentException.class, () -> new BattleShipBoard<>(10, -5));
        assertThrows(IllegalArgumentException.class, () -> new BattleShipBoard<>(-8, 20));
    }

    /**
     * helper func to check all coordinates in BattleShipBoard have right value
     * @param b is the BattleshipBoard
     * @param expected is expected [][]
     * @param <T> is the typename specify Board's type
     */
    private <T> void checkWhatIsAtBoard(BattleShipBoard<T> b, T[][] expected){
        for (int i = 0; i < b.getWidth(); i++) {
            for (int j = 0; j < b.getHeight(); j++) {
                assertEquals(expected[i][j], b.whatIsAt(new Coordinate(i, j)));
            }
        }
    }

    /**
     * helper func to add a new ship to the board, and update expected [][] accordingly
     * @param b is the Board
     * @param r is the row
     * @param c is the column
     * @param expected is the expected [][]
     */
    private void checkAddShipToBoard(BattleShipBoard<Character> b, int r, int c, Character[][] expected) {
        Coordinate coordinate = new Coordinate(r,c);
        //add ship to board and test again
        RectangleShip<Character> s = new RectangleShip<>(coordinate, 's', '*');
        assertTrue(b.tryAddShip(s));
        expected[r][c] = s.getDisplayInfoAt(coordinate);
    }

    @Test
    public void test_whatIsAt() {
        BattleShipBoard<Character> b = new BattleShipBoard<>(10, 20);
        //should be a null board after init
        Character[][] expected = new Character[b.getWidth()][b.getHeight()];
        checkWhatIsAtBoard(b, expected);
        //add ship to board and test again
        checkAddShipToBoard(b, 1, 1, expected);
        checkWhatIsAtBoard(b, expected);
        //two ships
        checkAddShipToBoard(b, 2, 3, expected);
        checkWhatIsAtBoard(b, expected);
    }
}