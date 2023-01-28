package edu.duke.bw224.battleship;

import java.io.*;

public class App {
    final Board<Character> theBoard;
    final BoardTextView view;
    final BufferedReader inputReader;
    final PrintStream out;
    final AbstractShipFactory<Character> shipFactory;

    public App(Board<Character> theBoard, Reader inputSource, PrintStream out) {
        this.theBoard = theBoard;
        this.view = new BoardTextView(theBoard);
        this.inputReader = new BufferedReader(inputSource);
        this.out = out;
        this.shipFactory = new V1ShipFactory();
    }

    /**
     * print prompt to user and read a placement from user input
     * @param prompt is the prompt to show to user
     * @return a placement object
     * @throws IOException
     */
    public Placement readPlacement(String prompt) throws IOException {
        out.println(prompt);
        String s = inputReader.readLine();
        return new Placement(s);
    }

    /**
     * read a placement from user and place the ship on board accordingly
     * @throws IOException
     */
    public void doOnePlacement() throws IOException {
        Placement placement = readPlacement("Where would you like to put your ship?");
        Ship<Character> s = shipFactory.makeDestroyer(placement);
        theBoard.tryAddShip(s);
        out.println(view.displayMyOwnBoard());

    }


    public static void main(String[] args) throws IOException {
        Board<Character> b = new BattleShipBoard<>(10, 20);
        App app = new App(b, new InputStreamReader(System.in), System.out);
        app.doOnePlacement();
    }
}