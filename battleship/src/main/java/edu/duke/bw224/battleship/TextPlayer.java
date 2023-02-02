package edu.duke.bw224.battleship;

import org.checkerframework.checker.units.qual.A;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.function.Function;

public class TextPlayer {
    final String name;
    final Board<Character> theBoard;
    final BoardTextView view;
    final BufferedReader inputReader;
    final PrintStream out;
    final AbstractShipFactory<Character> shipFactory;
    final ArrayList<String> shipsToPlace;
    final HashMap<String, Function<Placement, Ship<Character>>> shipCreationFns;


    public TextPlayer(String name, Board<Character> theBoard, BufferedReader inputReader, PrintStream out, AbstractShipFactory<Character> factory) {
        this.name = name;
        this.theBoard = theBoard;
        this.view = new BoardTextView(theBoard);
        this.inputReader = inputReader;
        this.out = out;
        this.shipFactory = factory;
        this.shipsToPlace = new ArrayList<>();
        this.shipCreationFns = new HashMap<>();
        setupShipCreationList();
        setupShipCreationMap();
    }

    protected void setupShipCreationMap() {
        shipCreationFns.put("Submarine", (p) -> shipFactory.makeSubmarine(p));
        shipCreationFns.put("Destroyer", (p) -> shipFactory.makeDestroyer(p));
        shipCreationFns.put("Carrier", (p) -> shipFactory.makeCarrier(p));
        shipCreationFns.put("Battleship", (p) -> shipFactory.makeBattleship(p));
    }

    protected void setupShipCreationList() {
        shipsToPlace.addAll(Collections.nCopies(2, "Submarine"));
        shipsToPlace.addAll(Collections.nCopies(3, "Destroyer"));
        shipsToPlace.addAll(Collections.nCopies(3, "Battleship"));
        shipsToPlace.addAll(Collections.nCopies(2, "Carrier"));
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
    public void doOnePlacement(String shipName, Function<Placement, Ship<Character>> createFn) throws IOException {
        Placement placement = readPlacement("Player " + name + ": Where would you like to place a " + shipName + "?");
        Ship<Character> s = createFn.apply(placement);
        theBoard.tryAddShip(s);
        out.println(view.displayMyOwnBoard());

    }

    /**
     * enter placement phase or player
     * @throws IOException
     */
    public void doPlacementPhase() throws IOException {
        //(a) display the starting (empty) board
        out.println(view.displayMyOwnBoard());
        String instructionMsg = "--------------------------------------------------------------------------------\n" +
                "Player  "+ name + ": you are going to place the following ships (which are all\n" +
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
        //(b) print the instructions message
        out.println(instructionMsg);
        //(c) call doOnePlacement to place one ship
        for (String ship : shipsToPlace) {
            doOnePlacement(ship, shipCreationFns.get(ship));
        }
    }
}
