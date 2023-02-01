package edu.duke.bw224.battleship;

import org.junit.jupiter.api.Test;

import java.io.*;

import static org.junit.jupiter.api.Assertions.*;

class TextPlayerTest {

    final String HEADER =  "  0|1|2|3|4|5|6|7|8|9\n";
    final String BODY =
            "A  | | | | | | | | |  A\n" +
                    "B  | | | | | | | | |  B\n" +
                    "C  | | | | | | | | |  C\n" +
                    "D  | | | | | | | | |  D\n" +
                    "E  | | | | | | | | |  E\n" +
                    "F  | | | | | | | | |  F\n" +
                    "G  | | | | | | | | |  G\n" +
                    "H  | | | | | | | | |  H\n" +
                    "I  | | | | | | | | |  I\n" +
                    "J  | | | | | | | | |  J\n" +
                    "K  | | | | | | | | |  K\n" +
                    "L  | | | | | | | | |  L\n" +
                    "M  | | | | | | | | |  M\n" +
                    "N  | | | | | | | | |  N\n" +
                    "O  | | | | | | | | |  O\n" +
                    "P  | | | | | | | | |  P\n" +
                    "Q  | | | | | | | | |  Q\n" +
                    "R  | | | | | | | | |  R\n" +
                    "S  | | | | | | | | |  S\n" +
                    "T  | | | | | | | | |  T\n";


    private TextPlayer createTestPlayer(int w, int h, String userInput, OutputStream bytes) {
        StringReader sr = new StringReader(userInput);
        PrintStream ps = new PrintStream(bytes, true);
        Board<Character> b = new BattleShipBoard<>(w, h);
        return new TextPlayer("Test", b, new BufferedReader(sr), ps, new V1ShipFactory());
    }
    @Test
    void test_read_placement() throws IOException {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        TextPlayer player = createTestPlayer(10, 20, "B2V\nC8H\na4v\n", bytes);
        String prompt = "Please enter a location for a ship:";
        Placement[] expected = new Placement[3];
        expected[0] = new Placement(new Coordinate(1, 2), 'V');
        expected[1] = new Placement(new Coordinate(2, 8), 'H');
        expected[2] = new Placement(new Coordinate(0, 4), 'V');

        for (Placement placement : expected) {
            Placement p = player.readPlacement(prompt);
            assertEquals(p, placement); //did we get the right Placement back
            assertEquals(prompt + "\n", bytes.toString()); //should have printed prompt and newline
            bytes.reset(); //clear out bytes for next time around
        }
    }

    /**
     * Helper function to generate Board body based on new move describer
     * @param descr is the move describer, e.g. "A0H"
     * @param oldBody is the old body created by last move
     * @return new body string
     */
    private String getExpectBody(String descr, String oldBody){
        Placement p = new Placement(descr);
        Coordinate c = p.getWhere();
        int[] seps = new int[3];
        for(int i = 0; i < seps.length; i++) {
            if (p.getOrientation() == 'V') {
                // there are 23 char per line, \n per line
                seps[i] = (c.getRow() + i) * 23 + c.getCol() * 2 + 2 + c.getRow() + i;

            }
            else {
                seps[i] =  c.getRow() * 23 + c.getCol() * 2 + 2 + i + c.getRow();
            }
            oldBody = oldBody.substring(0, seps[i]) + 'd' + oldBody.substring(seps[i] + 1);
        }

        return oldBody;
    }

    @Test
    void test_do_one_placement() throws IOException {
        String userInput = "C8V\nB2V\na4v\n";
        String prompt = "Player Test: Where would you like to put your ship?";
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        TextPlayer player = createTestPlayer(10, 20, userInput, bytes);
        String expectedBody = BODY;
        String[] descrs = userInput.split("\n");
        for(String descr : descrs){
            player.doOnePlacement();
            expectedBody = getExpectBody(descr, expectedBody);
            assertEquals(prompt + "\n" + HEADER + expectedBody + HEADER + "\n", bytes.toString());
            bytes.reset();
        }
    }

    @Test
    void test_do_placement_phase() throws IOException{
        String userInput = "C8V";
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        TextPlayer player = createTestPlayer(10, 20, userInput, bytes);
        String prompt = "Player " + player.name + ": Where would you like to put your ship?";

        String instructionMsg = "--------------------------------------------------------------------------------\n" +
                "Player  "+ player.name + ": you are going to place the following ships (which are all\n" +
                "rectangular). For each ship, type the coordinate of the upper left\n" +
                "side of the ship, followed by either H (for horizontal) or V (for\n" +
                "vertical).  For example M4H would place a ship horizontally starting\n" +
                "at M4 and going to the right.  You have\n" +
                "\n" +
                "2 \"Submarines\" ships that are 1x2 \n" +
                "3 \"Destroyers\" that are 1x3\n" +
                "3 \"Battleships\" that are 1x4\n" +
                "2 \"Carriers\" that are 1x6\n" +
                "--------------------------------------------------------------------------------\n";
        String expectedBody = BODY;
        String preMsg = HEADER + BODY + HEADER + "\n" + instructionMsg + "\n";
        player.doPlacementPhase();
        expectedBody = getExpectBody(userInput, expectedBody);
        assertEquals( preMsg+ prompt + "\n" + HEADER + expectedBody + HEADER + "\n", bytes.toString());
        bytes.reset();
    }
}