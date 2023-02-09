package edu.duke.bw224.battleship;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BoardTextViewTest {

    private final V1ShipFactory factory = new V1ShipFactory();

    /**
     * This is a helper function to test empty board
     * @param w is the width of the constructed board
     * @param h is the height of the constructed board
     * @param expectedHeader is the expected string header line
     * @param expectedBody is the expected string body line(s)
     */
    private void emptyBoardHelper(int w, int h, String expectedHeader, String expectedBody){
        Board<Character> b1 = new BattleShipBoard<Character>(w, h, 'X');
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
        RectangleShip<Character> ship = new RectangleShip<>(c, 's', '*');
        b.tryAddShip(ship);
    }

    private void nonEmptyBoardHelper(String expectedHeader, String expectedBody, String enemyBody, BoardTextView view){
        assertEquals(expectedHeader, view.makeHeader());
        String expectedSelf = expectedHeader + expectedBody + expectedHeader;
        String expectedEnemy = expectedHeader + enemyBody + expectedHeader;
        assertEquals(expectedSelf, view.displayMyOwnBoard());
        assertEquals(expectedEnemy, view.displayMyEnemyBoard());
    }

    @Test
    void test_display_with_ship_4by3() {
        Board<Character> b = new BattleShipBoard<Character>(4, 3, 'X');
        String expectedHeader = "  0|1|2|3\n";
        String expectedBody =
                "A s| | |  A\n"+
                "B  | | |  B\n"+
                "C  | | |  C\n";
        String enemyBody =
                "A  | | |  A\n"+
                "B  | | |  B\n"+
                "C  | | |  C\n";
        addShipToBoard(b,"A0");
        BoardTextView view = new BoardTextView(b);
        nonEmptyBoardHelper(expectedHeader, expectedBody, enemyBody, view);
        addShipToBoard(b,"B1");
        expectedBody =
                "A s| | |  A\n"+
                "B  |s| |  B\n"+
                "C  | | |  C\n";
        view = new BoardTextView(b);
        nonEmptyBoardHelper(expectedHeader, expectedBody, enemyBody, view);

    }



    @Test
    void test_invalid_board_size() {
        Board<Character> wideBoard = new BattleShipBoard<Character>(11, 20, 'X');
        Board<Character> tallBoard = new BattleShipBoard<Character>(10, 27, 'X');
        assertThrows(IllegalArgumentException.class, () -> new BoardTextView(wideBoard));
        assertThrows(IllegalArgumentException.class, () -> new BoardTextView(tallBoard));
    }

    @Test
    void test_display_my_board_with_enemy_next_to_it() {
        Board<Character> myBoard = new BattleShipBoard<Character>(4, 3, 'X');
        Board<Character> enemyBoard = new BattleShipBoard<Character>(4, 3, 'X');
        Placement pA0H = new Placement("a0h");
        Placement pB1H = new Placement("b1h");
        Placement pA0V = new Placement("a0v");
        Placement pA1H = new Placement("a1H");
        assertNull(myBoard.tryAddShip(factory.makeSubmarine(pA0H)));
        assertNull(myBoard.tryAddShip(factory.makeDestroyer(pB1H)));
        assertNull(enemyBoard.tryAddShip(factory.makeSubmarine(pA0V)));
        assertNull(enemyBoard.tryAddShip(factory.makeDestroyer(pA1H)));
        BoardTextView myView = new BoardTextView(myBoard);
        BoardTextView enemyView = new BoardTextView(enemyBoard);
        String expected =
                "     Your ocean               Player B's ocean\n" +
                "  0|1|2|3                    0|1|2|3\n" +
                "A s|s| |  A                A s|d|d|d A\n"+
                "B  |d|d|d B                B s| | |  B\n"+
                "C  | | |  C                C  | | |  C\n"+
                "  0|1|2|3                    0|1|2|3\n";
        assertEquals(expected, myView.displayMyBoardWithEnemyNextToIt(enemyView, "Your ocean",
                "Player B's ocean"));

    }
}