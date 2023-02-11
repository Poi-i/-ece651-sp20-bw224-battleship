package edu.duke.bw224.battleship;

import edu.duke.bw224.battleship.App;
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

//    /**
//     * create an App based on userInput (a series of placement) and output stream
//     * @param userInput is the string of placements
//     * @param bytes is the output stream
//     * @return the created App
//     */
//    private App createTestApp(String userInput, ByteArrayOutputStream bytes) {
//        StringReader sr = new StringReader(userInput);
//        PrintStream ps = new PrintStream(bytes, true);
//        Board<Character> b = new BattleShipBoard<>(10, 20);
//        return new App(sr, ps);
//    }

//    @Disabled

    private void mainHelper(String inputFileName, String outputFileName) throws IOException {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        PrintStream out = new PrintStream(bytes, true);
        // get input and expected output
        InputStream input = getClass().getClassLoader().getResourceAsStream(inputFileName);
        assertNotNull(input);
        InputStream expectedStream = getClass().getClassLoader().getResourceAsStream(outputFileName);
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

//    @Disabled
    @Test
    @ResourceLock(value = Resources.SYSTEM_OUT, mode = ResourceAccessMode.READ_WRITE)
    void test_main() throws IOException{
        mainHelper("input_Computer_Vs_Player.txt",
                "output_Computer_Vs_Player.txt");
        mainHelper("input_Computer_Vs_Computer.txt",
                "output_Computer_Vs_Computer.txt");
        mainHelper("input_PvC.txt", "output_PvC.txt");
        mainHelper("input_PvP.txt", "output_PvP.txt");
    }
}