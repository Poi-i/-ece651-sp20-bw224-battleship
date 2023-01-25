package edu.duke.bw224.battleship;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class BattleShipBoardTest {

    @Test
    public void test_width_and_height() {
        Board b1 = new BattleShipBoard(10, 20);
        assertEquals(10, b1.getWidth());
        assertEquals(20, b1.getHeight());
    }

    @Test
    public void test_invalid_dimensions() {
        assertThrows(IllegalArgumentException.class, () -> new BattleShipBoard(10, 0));
        assertThrows(IllegalArgumentException.class, () -> new BattleShipBoard(0, 20));
        assertThrows(IllegalArgumentException.class, () -> new BattleShipBoard(10, -5));
        assertThrows(IllegalArgumentException.class, () -> new BattleShipBoard(-8, 20));
    }


}