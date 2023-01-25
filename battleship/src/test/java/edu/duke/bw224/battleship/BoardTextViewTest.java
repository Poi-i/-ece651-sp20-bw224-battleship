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
        Board<Character> b1 = new BattleShipBoard<Character>(w, h);
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


    private void addShipToBoard(Board<Character> b, String descr){
        Coordinate c = new Coordinate(descr);
        Ship<Character> ship = new BasicShip(c);
        b.tryAddShip(ship);
    }

    private void nonEmptyBoardHelper(String expectedHeader, String expectedBody, BoardTextView view){
        assertEquals(expectedHeader, view.makeHeader());
        String expected = expectedHeader + expectedBody + expectedHeader;
        assertEquals(expected, view.displayMyOwnBoard());
    }
    @Test
    void test_display_with_ship_4by3() {
        Board<Character> b = new BattleShipBoard<Character>(4, 3);
        String expectedHeader = "  0|1|2|3\n";
        String expectedBody =
                "A s| | |  A\n"+
                "B  | | |  B\n"+
                "C  | | |  C\n";
        addShipToBoard(b,"A0");
        BoardTextView view = new BoardTextView(b);
        nonEmptyBoardHelper(expectedHeader, expectedBody, view);
        addShipToBoard(b,"B1");
        expectedBody =
                "A s| | |  A\n"+
                "B  |s| |  B\n"+
                "C  | | |  C\n";
        view = new BoardTextView(b);
        nonEmptyBoardHelper(expectedHeader, expectedBody, view);

    }



    @Test
    void test_invalid_board_size() {
        Board<Character> wideBoard = new BattleShipBoard<Character>(11, 20);
        Board<Character> tallBoard = new BattleShipBoard<Character>(10, 27);
        assertThrows(IllegalArgumentException.class, () -> new BoardTextView(wideBoard));
        assertThrows(IllegalArgumentException.class, () -> new BoardTextView(tallBoard));
    }
}