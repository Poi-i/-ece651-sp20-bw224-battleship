package edu.duke.bw224.battleship;

import com.sun.jdi.VoidType;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.function.Function;
import java.util.function.Function;

import static org.junit.jupiter.api.Assertions.*;

public class BattleShipBoardTest {

    private final V1ShipFactory shipFactory = new V1ShipFactory();
    private final V2ShipFactory shipFactory2 = new V2ShipFactory();

    @Test
    public void test_width_and_height_and_miss_info() {
        Board<Character> b1 = new BattleShipBoard<>(10, 20, 'X');
        assertEquals(10, b1.getWidth());
        assertEquals(20, b1.getHeight());
        assertEquals('X', b1.getMissInfo());
    }

    @Test
    void test_get_ships_and_at() {
        Board<Character> b1 = new BattleShipBoard<>(10, 20, 'X');
        Ship<Character> s1 = shipFactory.makeSubmarine(new Placement("A0V"));
        ArrayList<Ship<Character>> expected = new ArrayList<>();
        expected.add(s1);
        b1.tryAddShip(s1);
        // test getShips()
        assertEquals(expected, b1.getShips());
        // test getShipAt()
        assertEquals(s1, b1.getShipAt(new Coordinate(0, 0)));
        assertEquals(null, b1.getShipAt(new Coordinate(5, 3)));
    }


    @Test
    public void test_invalid_dimensions() {
        assertThrows(IllegalArgumentException.class, () -> new BattleShipBoard<>(10, 0, 'X'));
        assertThrows(IllegalArgumentException.class, () -> new BattleShipBoard<>(0, 20, 'X'));
        assertThrows(IllegalArgumentException.class, () -> new BattleShipBoard<>(10, -5, 'X'));
        assertThrows(IllegalArgumentException.class, () -> new BattleShipBoard<>(-8, 20, 'X'));
    }

    @Test
    void test_try_add_ship() {
        BattleShipBoard<Character> b = new BattleShipBoard<>(10, 20, 'X');
        Placement v_1_2 = new Placement(new Coordinate(1, 2), 'V');
        Placement h_1_3 = new Placement(new Coordinate(1, 3), 'H');
        Ship<Character> s1 = shipFactory.makeBattleship(v_1_2);
        assertNull(b.tryAddShip(s1));
        Ship<Character> collide_s2 = shipFactory.makeDestroyer(v_1_2);
        String collideMsg = "That placement is invalid: the ship overlaps another ship.\n";
        assertEquals(collideMsg, b.tryAddShip(collide_s2));
        Ship<Character> s3 = shipFactory.makeDestroyer(h_1_3);
        assertNull(b.tryAddShip(s3));
    }

    /**
     * helper func to check all coordinates in BattleShipBoard have right value
     * @param b is the BattleshipBoard
     * @param expected is expected [][]
     * @param <T> is the typename specify Board's type
     */
    private <T> void checkWhatIsAtBoard(BattleShipBoard<T> b, T[][] expected, boolean isSelf){
//        Function<Coordinate, T> whatIsAtFn;
//        if (isSelf) {
//            whatIsAtFn = b::whatIsAtForSelf;
//        }
//        else {
//            whatIsAtFn = b::whatIsAtForEnemy;
//        }
        for (int i = 0; i < b.getWidth(); i++) {
            for (int j = 0; j < b.getHeight(); j++) {
                assertEquals(expected[i][j], b.whatIsAt(new Coordinate(i, j), isSelf));
            }
        }
    }


    /**
     * helper func to add a new ship to the board, and update expected [][] accordingly
     * @param b is the Board
     * @param r is the row
     * @param c is the column
     * @param expectedSelf is self's expected board
     * @param expectedEnemy is enemy's expected board
     */
    private void checkAddShipToBoard(BattleShipBoard<Character> b, int r, int c, Character[][] expectedSelf,
                                     Character[][] expectedEnemy) {
        Coordinate coordinate = new Coordinate(r,c);
        //add ship to board and test again
        RectangleShip<Character> s = new RectangleShip<>(coordinate, 's', '*');
        assertNull(b.tryAddShip(s));
        expectedSelf[r][c] = s.getDisplayInfoAt(coordinate, true);
        expectedEnemy[r][c] = s.getDisplayInfoAt(coordinate, false);
    }



    @Test
    public void test_what_is_at() {
        BattleShipBoard<Character> b = new BattleShipBoard<>(10, 20, 'X');
        //should be a null board after init
        Character[][] expectedSelf = new Character[b.getWidth()][b.getHeight()];
        Character[][] expectedEnemy = new Character[b.getWidth()][b.getHeight()];
        checkWhatIsAtBoard(b, expectedSelf, true);
        checkWhatIsAtBoard(b, expectedEnemy, false);
        //add ship to board and test again, test self & enemy
        checkAddShipToBoard(b, 1, 1, expectedSelf, expectedEnemy);
        checkWhatIsAtBoard(b, expectedSelf, true);
        checkWhatIsAtBoard(b, expectedEnemy, false);
        //two ships
        checkAddShipToBoard(b, 2, 3, expectedSelf, expectedEnemy);
        checkWhatIsAtBoard(b, expectedSelf, true);
        checkWhatIsAtBoard(b, expectedEnemy, false);
    }

    @Test
    void test_fire_at() {
        BattleShipBoard<Character> b = new BattleShipBoard<>(10, 20, 'X');
        Coordinate c_1_2 = new Coordinate(1, 2);
        Coordinate c_1_3 = new Coordinate(1, 3);
        Placement v_1_2 = new Placement(c_1_2, 'V');
        Placement h_1_3 = new Placement(c_1_3, 'H');
        Ship<Character> s1 = shipFactory.makeBattleship(v_1_2);
        Ship<Character> s2 = shipFactory.makeBattleship(h_1_3);
        assertNull(b.tryAddShip(s1));
        assertNull(b.tryAddShip(s2));
        // test valid fire at (1, 3)
        assertSame(s2, b.fireAt(c_1_3)); //test if object are the same
        assertTrue(s2.wasHitAt(c_1_3)); //test side effect
        assertFalse(s2.isSunk());
        // test invalid fire at (0, 0)
        Coordinate c_0_0 = new Coordinate(0, 0);
        assertNull(b.fireAt(c_0_0));
        assertFalse(s1.isSunk());
        assertFalse(s2.isSunk());
        //check recording misses
        assertEquals(s2.getDisplayInfoAt(c_1_3, false), b.whatIsAt(c_1_3, false));
        assertEquals(b.missInfo, b.whatIsAt(c_0_0, false));
    }

    @Test
    void test_check_placement_orientation() {
        String message = "That placement is invalid: the orientation of %s should be %s!\n";
        String battleship = "Battleship";
        String submarine = "Submarine";
        String carrier = "Carrier";
        String destroyer = "Destroyer";
        BattleShipBoard<Character> b = new BattleShipBoard<>(10, 20, 'X');
        Placement a1v = new Placement("a1v");
        Placement b2h = new Placement("b2h");
        Placement c6U = new Placement("c6U");
        assertNull(b.checkPlacementOrientation(battleship, c6U));
        assertNull(b.checkPlacementOrientation(carrier, c6U));
        assertNull(b.checkPlacementOrientation(destroyer, a1v));
        assertNull(b.checkPlacementOrientation(submarine, b2h));
        assertEquals(message.formatted(battleship, "U, L, R, or D"), b.checkPlacementOrientation(battleship, a1v));
        assertEquals(message.formatted(carrier, "U, L, R, or D"), b.checkPlacementOrientation(carrier, b2h));
        assertEquals(message.formatted(destroyer, "V or H"), b.checkPlacementOrientation(destroyer, c6U));
        assertEquals(message.formatted(submarine, "V or H"), b.checkPlacementOrientation(submarine, c6U));
    }

    @Test
    void test_check_all_sunk() {
        BattleShipBoard<Character> b = new BattleShipBoard<>(10, 20, 'X');
        assertTrue(b.checkAllSunk());
        Ship<Character> s1 = shipFactory.makeSubmarine(new Placement("A0V"));
        assertNull(b.tryAddShip(s1));
        assertFalse(b.checkAllSunk());
    }

    @Test
    void test_try_move_ship() {
        BattleShipBoard<Character> b = new BattleShipBoard<>(5, 4, 'X');
        Ship<Character> battleship = shipFactory2.makeBattleship(new Placement("A0U"));
        Ship<Character> newBattleship = shipFactory2.makeBattleship(new Placement("A2r"));
        Ship<Character> invalidShip = shipFactory2.makeCarrier(new Placement("A3r"));
        Ship<Character> invalidBattleShip = shipFactory2.makeBattleship(new Placement("A9U"));
        BoardTextView view = new BoardTextView(b);
        // test valid move
        assertNull(b.tryAddShip(battleship));
        Coordinate hitAt = new Coordinate("a1");
        b.fireAt(hitAt);
        assertTrue(battleship.wasHitAt(hitAt));
        assertNull(b.tryMoveShip(battleship, newBattleship));
//        System.out.println(view.displayMyOwnBoard());
//        System.out.println(view.displayMyEnemyBoard());
        for (int i = 0; i < b.getHeight(); ++i) {
            for (int j = 0; j < b.getWidth(); ++j) {
                Coordinate curr = new Coordinate(i, j);
                if ((j == 2 && i < 3)) {
                    assertEquals('b', b.whatIsAt(curr, true));
                    assertNull(b.whatIsAt(curr, false));
                    continue;
                }
                else if (i == 1 && j == 3) {
                    assertEquals('*', b.whatIsAt(curr, true));
                    assertNull(b.whatIsAt(curr, false));
                    continue;
                }
                else if (i == 0 && j == 1) {
                    assertNull(b.whatIsAt(curr, true));
                    assertEquals('b', b.whatIsAt(curr, false));
                    continue;
                }
                assertNull(b.whatIsAt(curr, true));
                assertNull(b.whatIsAt(curr, false));

            }
        }
        // test invalid move
        String diffShipMsg = "The ship type of two ships should be same!\n";
        String outOfBoundMsg = "That placement is invalid: the ship goes off the right of the board.\n";
        assertEquals(diffShipMsg, b.tryMoveShip(battleship, invalidShip));
        assertEquals(outOfBoundMsg, b.tryMoveShip(battleship, invalidBattleShip));
        //test right to up
        hitAt = new Coordinate("a2");
        b.fireAt(hitAt);
        assertTrue(newBattleship.wasHitAt(hitAt));
        Ship<Character> upBattleship = shipFactory2.makeBattleship(new Placement("A0U"));
        assertNull(b.tryMoveShip(newBattleship, upBattleship));


    }
}