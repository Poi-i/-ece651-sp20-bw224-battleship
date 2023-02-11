package edu.duke.bw224.battleship;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.io.*;

import static org.junit.jupiter.api.Assertions.*;

class TextPlayerTest {

    final String HEADER = "  0|1|2|3|4|5|6|7|8|9\n";
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

    private final V1ShipFactory factory = new V1ShipFactory();


    private TextPlayer createTestPlayer(int w, int h, String userInput, OutputStream bytes) {
        StringReader sr = new StringReader(userInput);
        PrintStream ps = new PrintStream(bytes, true);
        Board<Character> b = new BattleShipBoard<>(w, h, 'X');
        return new TextPlayer("Test", b, new BufferedReader(sr), ps, new V1ShipFactory());
    }

    @Test
    void test_text_player_creation() {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        TextPlayer player = createTestPlayer(10, 20, "B2V\nC8H\na4v\n", bytes);
        assertEquals(3, player.getMoveActionRemaining());
        assertEquals(3, player.getSonarActionRemaining());
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

    @Test
    void test_read_coordinate() throws IOException {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        TextPlayer player = createTestPlayer(10, 20, "B2\n", bytes);
        String prompt = "Player " + player.name + ": Which coordinate do you want to fire at?";
        Coordinate expected = new Coordinate("b2");
        Coordinate coordinate = player.readCoordinate(prompt);
        assertEquals(expected, coordinate); //did we get the right Placement back
        assertEquals(prompt + "\n", bytes.toString()); //should have printed prompt and newline
    }

    @Test
    void test_read_action_choice() throws IOException {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        String inputString = "f\nM\ns\n\nh\n1\nfm\nF";
        TextPlayer player = createTestPlayer(10, 20, inputString, bytes);
        String[] choices = inputString.split("\n");
        String prompt =
                "Possible actions for Player " + player.name + ":\n" +
                        "\n" +
                        " F Fire at a square\n" +
                        " M Move a ship to another square (" + player.getMoveActionRemaining() + " remaining)\n" +
                        " S Sonar scan (" + player.getSonarActionRemaining() + " remaining)\n" +
                        "\n" +
                        "Player " + player.name + ", what would you like to do?";
        StringBuilder invalidExpected = new StringBuilder();
        for (int i = 0; i < choices.length; ++i) {
            //test valid choice: f, M, s
            if(i < 3) {
                assertEquals(Character.toUpperCase(choices[i].charAt(0)), player.readActionChoice(prompt));
                assertEquals(prompt + "\n", bytes.toString());
                bytes.reset();

            }
            else if(i < choices.length - 1){
                //test invalid choice: h, 1, fm, end with valid F
                String errorMsg = "Your action choice: %s is invalid, please try again!\n";
                invalidExpected.append(prompt + "\n" + errorMsg.formatted(choices[i]));
            }
            else {
                invalidExpected.append(prompt + "\n");
                assertEquals(Character.toUpperCase(choices[i].charAt(0)), player.readActionChoice(prompt));
                assertEquals(invalidExpected.toString(), bytes.toString());
                bytes.reset();
            }
        }
    }

    /**
     * Helper function to generate Board body based on new move describer
     *
     * @param descr   is the move describer, e.g. "A0H"
     * @param oldBody is the old body created by last move
     * @return new body string
     */
    private String getExpectBody(String descr, String oldBody) {
        Placement p = new Placement(descr);
        Coordinate c = p.getWhere();
        int[] seps = new int[3];
        for (int i = 0; i < seps.length; i++) {
            if (p.getOrientation() == 'V') {
                // there are 23 char per line, \n per line
                seps[i] = (c.getRow() + i) * 23 + c.getCol() * 2 + 2 + c.getRow() + i;

            } else {
                seps[i] = c.getRow() * 23 + c.getCol() * 2 + 2 + i * 2 + c.getRow();
            }
            oldBody = oldBody.substring(0, seps[i]) + 'd' + oldBody.substring(seps[i] + 1);
        }

        return oldBody;
    }

    private void doOnePlacementHelper(String userInput) throws IOException {
        String ship = "Destroyer";
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        TextPlayer player = createTestPlayer(10, 20, userInput, bytes);
        String prompt = "Player " + player.name + ": Where would you like to place a " + ship + "?";
        String expectedBody = BODY;
        String[] descrs = userInput.split("\n");

        for (String descr : descrs) {
            expectedBody = getExpectBody(descr, expectedBody);
            player.doOnePlacement(ship, player.shipCreationFns.get(ship));
            assertEquals(prompt + "\n" + HEADER + expectedBody + HEADER + "\n", bytes.toString());
            bytes.reset();
        }
    }
    @Test
    void test_do_one_placement() throws IOException {
        String correctUserInput = "C8V\nB2H\nA5V\nD0H\n";
        doOnePlacementHelper(correctUserInput);
    }

    private void doOneFireHelper(TextPlayer self, TextPlayer enemy, String expected, ByteArrayOutputStream bytes) throws IOException {
        self.doOneFire(enemy);    //fire at B2
        assertEquals( expected, bytes.toString());
        bytes.reset();
    }

    @Test
    void test_do_one_fire() throws IOException {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        //test fire at miss
        TextPlayer enemy = createTestPlayer(10, 20, "", bytes);
        TextPlayer self = createTestPlayer(10, 20, "B2\nB2\nA1\nA1\nC3", bytes);
        String prompt = "Player Test: Which coordinate do you want to fire at?\n\n";
        String divideLine = "-".repeat(60) + "\n";
        String miss = divideLine + "You missed!\n" + divideLine + "\n";
        String hit = divideLine + "You hit a Submarine!\n" + divideLine + "\n";
        String invalidMiss = divideLine + "This is a miss that you've already fire at!\n" + divideLine + "\n";
        String invalidCoord = divideLine + "This is a coordinate that you've already fire at!\n" + divideLine + "\n";
        //add a ship at that coordinate and test hit
        Placement B2V = new Placement("b2v");
        assertNull(enemy.theBoard.tryAddShip(factory.makeSubmarine(B2V)));
        doOneFireHelper(self, enemy, prompt + hit, bytes); //fire at B2, hit
        //test invalid fire: already hit
        String expected = prompt + invalidCoord + prompt + miss;
        doOneFireHelper(self, enemy, expected, bytes); //fire at B2(hit invalid), then A1(miss)
        //test invalid fire: already miss
        expected = prompt + invalidMiss + prompt + miss;
        doOneFireHelper(self, enemy, expected, bytes); //fire at A1(invalid), then B2(miss)
    }

    @Disabled
    @Test
    void test_do_one_sonar() throws IOException {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        //test fire at miss
        TextPlayer enemy = createTestPlayer(10, 20, "a0v\na1v\na2v\na3v\na4v\na5d\na7l\nc0r\ng4u\ni0u\n", bytes);
        TextPlayer self = createTestPlayer(10, 20, "s\nc4\ns\nq2\n", bytes);
        enemy.doPlacementPhase();
        self.doOneSonar(enemy);
    }

    @Disabled
    @Test
    void test_play_one_turn() throws IOException {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        String inputString = "M\ns\n";

        //test fire at miss
        TextPlayer enemy = createTestPlayer(10, 20, "", bytes);
        TextPlayer self = createTestPlayer(10, 20, inputString, bytes);
        String prompt =
                "Possible actions for Player " + self.name + ":\n" +
                        "\n" +
                        " F Fire at a square\n" +
                        " M Move a ship to another square (%s remaining)\n" +
                        " S Sonar scan (%s remaining)\n" +
                        "\n" +
                        "Player " + self.name + ", what would you like to do?";
        String result = prompt + "\n" + "%s" + "\n";
        String expected = result.formatted(self.getMoveActionRemaining(), self.getSonarActionRemaining(), "move");
        self.playOneTurn(enemy);
        assertEquals(expected, bytes.toString());
        assertEquals(2, self.getMoveActionRemaining());
        assertEquals(3, self.getSonarActionRemaining());
        bytes.reset();
        expected = result.formatted(self.getMoveActionRemaining(), self.getSonarActionRemaining(), "sonar");
        self.playOneTurn(enemy);
        assertEquals(expected, bytes.toString());
        assertEquals(2, self.getMoveActionRemaining());
        assertEquals(2, self.getSonarActionRemaining());
        bytes.reset();
    }
}