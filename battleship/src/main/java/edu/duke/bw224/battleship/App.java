package edu.duke.bw224.battleship;

import java.io.*;

public class App {
    final TextPlayer player1;
    final TextPlayer player2;



    public App(TextPlayer player1, TextPlayer player2) {

        this.player1 = player1;
        this.player2 = player2;
    }

    public void doPlacementPhase() throws IOException {
        player1.doPlacementPhase();
        player2.doPlacementPhase();
    }

//    private void printWinMsg(TextPlayer player, String winMsg) {
//        String divideLine = "=".repeat(winMsg.length() - 1);
//        player.out.println(divideLine);
//        player.out.println(winMsg + divideLine);
//    }
    public void doAttackingPhase() throws IOException{

        while(true) {
            player1.doAttackingPhase(player2);
            if(player2.theBoard.checkAllSunk()) {
                player1.printWinMsg();
                return;
            }
            player2.doAttackingPhase(player1);
            if(player1.theBoard.checkAllSunk()) {
                player2.printWinMsg();
                return;
            }
        }
    }

    private static Character readRoleChoice(String prompt, BufferedReader input, PrintStream out) throws IOException {
        System.out.println(prompt);
        String s = input.readLine();
        if(s.length() != 1 || "CcPp".indexOf(s.charAt(0)) == -1) {
            out.println("Your action choice: " + s + " is invalid, please try again!");
            return readRoleChoice(prompt, input, out);
        }
        return Character.toUpperCase(s.charAt(0));
    }

    private static String setPlayerRoles(BufferedReader input, PrintStream out) throws IOException {
        String promptA = "Please select play role for the first Player:\n" +
                "C Computer Player\nP Person Player\n";
        char roleA = readRoleChoice(promptA, input, out);
        String promptB = "Please select play role for the second Player:\n" +
                "C Computer Player\nP Person Player\n";
        char roleB = readRoleChoice(promptB, input, out);
        return roleA + String.valueOf(roleB);
    }

    private static BufferedReader generateComputerInput(Board<Character> theBoard) {
        StringBuilder input = new StringBuilder("a0v\na1v\na2v\na3v\na4v\na5d\na7l\nc0r\nf0d\nq0r\n");
        for (int i = 0; i < theBoard.getHeight(); ++i) {
            for (int j = 0; j < theBoard.getWidth(); ++j) {
                input.append((char) ('A' + i) + String.valueOf(j) + "\n");
            }
        }
        return new BufferedReader(new StringReader(input.toString()));
    }

    public static void main(String[] args) throws IOException {
        Board<Character> b1 = new BattleShipBoard<Character>(10, 20, 'X');
        Board<Character> b2 = new BattleShipBoard<Character>(10, 20, 'X');
        BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
        V2ShipFactory factory = new V2ShipFactory();
        String roleChoices = setPlayerRoles(input, System.out);
        TextPlayer player1 = roleChoices.charAt(0) == 'C' ?
                new NaiveComputerPlayer("A", b1, generateComputerInput(b1), System.out, factory)
                : new TextPlayer("A", b1, input, System.out, factory);
        TextPlayer player2 = roleChoices.charAt(1) == 'C' ?
                new NaiveComputerPlayer("B", b2, generateComputerInput(b2), System.out, factory)
                : new TextPlayer("B", b2, input, System.out, factory);
        App app = new App(player1, player2);
        app.doPlacementPhase();
        app.doAttackingPhase();
    }
}