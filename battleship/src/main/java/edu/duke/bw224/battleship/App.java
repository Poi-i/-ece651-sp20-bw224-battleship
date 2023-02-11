package edu.duke.bw224.battleship;

import java.io.*;

public class App {
    final TextPlayer player1;
    final TextPlayer player2;



    public App(TextPlayer player1, TextPlayer player2) {

        this.player1 = player1;
        this.player2 = player2;
    }


    public void doPlacementPhase() throws IOException{
        player1.doPlacementPhase();
        player2.doPlacementPhase();
    }

    private void printWinMsg(TextPlayer player, String winMsg) {
        String divideLine = "=".repeat(winMsg.length() - 1);
        player.out.println(divideLine);
        player.out.println(winMsg + divideLine);
    }
    public void doAttackingPhase() throws IOException{
        String player1Win = "|   " + "Player " + player1.name + " win!" + "   |\n";
        String player2Win = "|   " + "Player " + player2.name + " win!" + "   |\n";
        while(true) {
            player1.doAttackingPhase(player2);
            if(player2.theBoard.checkAllSunk()) {
                printWinMsg(player1, player1Win);
                return;
            }
            player2.doAttackingPhase(player1);
            if(player1.theBoard.checkAllSunk()) {
                printWinMsg(player2, player2Win);
                return;
            }
        }

    }

    public static void main(String[] args) throws IOException {
        Board<Character> b1 = new BattleShipBoard<Character>(10, 20, 'X');
        Board<Character> b2 = new BattleShipBoard<Character>(10, 20, 'X');
        BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
        V2ShipFactory factory = new V2ShipFactory();
        TextPlayer player1 = new TextPlayer("A", b1, input, System.out, factory);
        TextPlayer player2 = new TextPlayer("B", b2, input, System.out, factory);
        App app = new App(player1, player2);

        app.doPlacementPhase();
        app.doAttackingPhase();
    }
}