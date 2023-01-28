package edu.duke.bw224.battleship;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.parallel.ResourceAccessMode;
import org.junit.jupiter.api.parallel.ResourceLock;
import org.junit.jupiter.api.parallel.Resources;

import java.io.*;

import static org.junit.jupiter.api.Assertions.*;



class AppTest {
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

    /**
     * create an App based on userInput (a series of placement) and output stream
     * @param userInput is the string of placements
     * @param bytes is the output stream
     * @return the created App
     */
    private App createTestApp(String userInput, ByteArrayOutputStream bytes) {
        StringReader sr = new StringReader(userInput);
        PrintStream ps = new PrintStream(bytes, true);
        Board<Character> b = new BattleShipBoard<>(10, 20);
        return new App(b, sr, ps);
    }

    @Test
    void test_read_placement() throws IOException {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        App app = createTestApp("B2V\nC8H\na4v\n", bytes);
        String prompt = "Please enter a location for a ship:";
        Placement[] expected = new Placement[3];
        expected[0] = new Placement(new Coordinate(1, 2), 'V');
        expected[1] = new Placement(new Coordinate(2, 8), 'H');
        expected[2] = new Placement(new Coordinate(0, 4), 'V');

        for (Placement placement : expected) {
            Placement p = app.readPlacement(prompt);
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
                seps[i] = (c.getRow() + i) * 23 + c.getCol() * 2 + 2 + c.getRow() + i;

            }
            else {
                seps[i] =  c.getRow() * 23 + c.getCol() * 2 + 2 + i + c.getRow();
            }
            oldBody = oldBody.substring(0, seps[i]) + 'd' + oldBody.substring(seps[i] + 1);
        }

        // there are 23 char per line, \n per line
//        int sep1 = c.getRow() * 23 + c.getCol() * 2 + 2 + c.getRow();
        return oldBody;
    }

    @Test
    void test_do_one_placement() throws IOException {
        String userInput = "C8V\nB2V\na4v\n";
        String prompt = "Where would you like to put your ship?";
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        App app = createTestApp(userInput, bytes);
        String expectedBody = BODY;
        String[] descrs = userInput.split("\n");

        for(String descr : descrs){
            app.doOnePlacement();
            expectedBody = getExpectBody(descr, expectedBody);
            assertEquals(prompt + "\n" + HEADER + expectedBody + HEADER + "\n", bytes.toString());
            bytes.reset();
        }
    }

    @Test
    @ResourceLock(value = Resources.SYSTEM_OUT, mode = ResourceAccessMode.READ_WRITE)
    void test_main() throws IOException{
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        PrintStream out = new PrintStream(bytes, true);
        // get input and expected output
        InputStream input = getClass().getClassLoader().getResourceAsStream("input.txt");
        assertNotNull(input);
        InputStream expectedStream = getClass().getClassLoader().getResourceAsStream("output.txt");
        assertNotNull(expectedStream);
        // remember old System.in & out
        InputStream oldIn = System.in;
        PrintStream oldOut = System.out;
        try {
            System.setIn(input);
            System.setOut(out);
            App.main(new String[0]);
        }
        finally {
            System.setIn(oldIn);
            System.setOut(oldOut);
        }
        String expected = new String(expectedStream.readAllBytes());
        String actual = bytes.toString();
        assertEquals(expected, actual);

    }
}