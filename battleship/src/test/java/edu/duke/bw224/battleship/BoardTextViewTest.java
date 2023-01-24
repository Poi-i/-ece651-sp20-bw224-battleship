package edu.duke.bw224.battleship;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BoardTextViewTest {

    /**
     * This is a helper function to test empty board
     * @param w is the width of the constructed board
     * @param h is the height of the constructed board
     * @param expectedHeader is the expected string header line
     * @param expectedBody is the expected string body line(s)
     */
    private void emptyBoardHelper(int w, int h, String expectedHeader, String expectedBody){
        Board b1 = new BattleShipBoard(w, h);
        BoardTextView view = new BoardTextView(b1);
        assertEquals(expectedHeader, view.makeHeader());
        String expected = expectedHeader + expectedBody + expectedHeader;
        assertEquals(expected, view.displayMyOwnBoard());
    }

    @Test
    void test_display_empty_2by2() {
        String expectedHeader = "  0|1\n";
        String expectedBody =
                "A  |  A\n"+
                "B  |  B\n";
        emptyBoardHelper(2, 2, expectedHeader, expectedBody);
    }

    @Test
    public void test_display_empty_3by2() {
        String expectedHeader = "  0|1|2\n";
        String expectedBody =
                "A  | |  A\n"+
                "B  | |  B\n";
        emptyBoardHelper(3, 2, expectedHeader, expectedBody);
    }

    @Test
    public void test_display_empty_3by5() {
        String expectedHeader = "  0|1|2\n";
        String expectedBody =
                "A  | |  A\n"+
                "B  | |  B\n"+
                "C  | |  C\n"+
                "D  | |  D\n"+
                "E  | |  E\n";
        emptyBoardHelper(3, 5, expectedHeader, expectedBody);
    }

    @Test
    void test_invalid_board_size() {
        Board wideBoard = new BattleShipBoard(11, 20);
        Board tallBoard = new BattleShipBoard(10, 27);
        assertThrows(IllegalArgumentException.class, () -> new BoardTextView(wideBoard));
        assertThrows(IllegalArgumentException.class, () -> new BoardTextView(tallBoard));
    }
}