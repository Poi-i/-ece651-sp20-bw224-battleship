package edu.duke.bw224.battleship;

import java.io.*;

public class App {
    final TextPlayer player1;
    final TextPlayer player2;



    public App(TextPlayer player1, TextPlayer player2) {

        this.player1 = player1;
        this.player2 = player2;
    }


//    public App(Reader inputSource, PrintStream out) {
//        Board<Character> b1 = new BattleShipBoard<Character>(10, 20);
//        Board<Character> b2 = new BattleShipBoard<Character>(10, 20);
//        this.player1 = new TextPlayer("A", b1, new BufferedReader(inputSource), out, factory);
//        this.player2 = new TextPlayer("B", b2, new BufferedReader(inputSource), out, factory);
//    }


    public void doPlacementPhase() throws IOException{
        player1.doPlacementPhase();
        player2.doPlacementPhase();
    }

    public static void main(String[] args) throws IOException {
        Board<Character> b1 = new BattleShipBoard<Character>(10, 20);
        Board<Character> b2 = new BattleShipBoard<Character>(10, 20);
        BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
        V1ShipFactory factory = new V1ShipFactory();
        TextPlayer player1 = new TextPlayer("A", b1, input, System.out, factory);
        TextPlayer player2 = new TextPlayer("B", b2, input, System.out, factory);
        App app = new App(player1, player2);
        app.doPlacementPhase();
    }
}