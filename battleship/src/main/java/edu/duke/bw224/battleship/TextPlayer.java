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
        this.out.println(prompt);
        String s = inputReader.readLine();
        Placement placement = null;
        try{
            placement = new Placement(s);
        } catch (IllegalArgumentException e) {
            this.out.println(e.getMessage());
            return readPlacement(prompt);
        }
        return placement;
    }


    /**
     * print prompt to user and read a coordinate to fire at from user input
     * @param prompt is the prompt to show to user
     * @return a coordinate object
     * @throws IOException
     */
    public Coordinate readFireCoordinate(String prompt) throws IOException {
        this.out.println(prompt);
        String s = inputReader.readLine();
        Coordinate coordinate = null;
        try{
            coordinate = new Coordinate(s);
        } catch (IllegalArgumentException e) {
            this.out.println(e.getMessage());
            return readFireCoordinate(prompt);
        }
        return coordinate;
    }




    /**
     * read a placement from user and place the ship on board accordingly
     * @throws IOException
     */
    public void doOnePlacement(String shipName, Function<Placement, Ship<Character>> createFn) throws IOException {
        String prompt = "Player " + name + ": Where would you like to place a " + shipName + "?";
        Placement placement = readPlacement(prompt);
        String message = theBoard.checkPlacementOrientation(shipName, placement);
        if (message != null) {
            this.out.println(message);
            doOnePlacement(shipName, createFn);
            return;
        }
        Ship<Character> s = createFn.apply(placement);
        message = theBoard.tryAddShip(s);
        if (message != null) {
            this.out.println(message);
            doOnePlacement(shipName, createFn);
        }
        else {
            this.out.println(view.displayMyOwnBoard());
        }
    }

    /**
     * enter placement phase or player
     * @throws IOException
     */
    public void doPlacementPhase() throws IOException {
        //(a) display the starting (empty) board
        this.out.println(view.displayMyOwnBoard());
        String instructionMsg = "--------------------------------------------------------------------------------\n" +
                "Player  "+ name + ": you are going to place the following ships (which are all\n" +
                "rectangular). For each ship, type the coordinate of the upper left\n" +
                "side of the ship, followed by either H (for horizontal) or V (for\n" +
                "vertical).  For example M4H would place a ship horizontally starting\n" +
                "at M4 and going to the right.  You have\n" +
                "\n" +
                "2 \"Submarines\" ships that are 1x2 \n" +
                "3 \"Destroyers\" that are 1x3\n" +
                "3 \"Battleships\" that are T-shaped (4 cells)\n" +
                "2 \"Carriers\" that are Z-shaped (7 cells)\n" +
                "--------------------------------------------------------------------------------\n";
        //(b) print the instructions message
        this.out.println(instructionMsg);
        //(c) call doOnePlacement to place one ship
        for (String ship : shipsToPlace) {
            doOnePlacement(ship, shipCreationFns.get(ship));
        }
    }

    public void playOneTurn(TextPlayer enemy) throws IOException {
        String divideLine = "-".repeat(60) + "\n";
        String prompt = "Player " + this.name + ": Which coordinate do you want to fire at?\n";
        Coordinate fireAt = readFireCoordinate(prompt);
        // check fire coordinate validity, already hit/miss
        Character fireAtStatus = enemy.theBoard.whatIsAtForEnemy(fireAt);
        if (fireAtStatus != null) {
            if (fireAtStatus.equals(this.theBoard.getMissInfo())) {
                this.out.println(divideLine + "This is a miss that you've already fire at!\n" + divideLine);
            }
            else {
                this.out.println(divideLine + "This is a coordinate that you've already fire at!\n" + divideLine);
            }
            playOneTurn(enemy);
        }
        // valid fire coordinate
        else {
            Ship<Character> targetShip = enemy.theBoard.fireAt(fireAt);
            if (targetShip == null) {
                this.out.println(divideLine + "You missed!\n" + divideLine);
            }
            else {
                this.out.println(divideLine + "You hit a " + targetShip.getName() + "!\n" + divideLine);
            }
        }

    }

    public void doAttackingPhase(TextPlayer enemy) throws IOException {
        this.out.println("Player " + this.name + "'s turn:\n");
        String myHeader = "Your ocean";
        String enemyHeader = "Player " + enemy.name + "'s ocean";
        this.out.println(this.view.displayMyBoardWithEnemyNextToIt(enemy.view, myHeader, enemyHeader));
        playOneTurn(enemy);
    }
}
