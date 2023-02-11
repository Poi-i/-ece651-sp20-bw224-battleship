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
    private int moveActionRemaining;
    private int sonarActionRemaining;


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
        this.moveActionRemaining = 3;
        this.sonarActionRemaining = 3;
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

    public int getMoveActionRemaining() {
        return moveActionRemaining;
    }

    public int getSonarActionRemaining() {
        return sonarActionRemaining;
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
    public Coordinate readCoordinate(String prompt) throws IOException {
        this.out.println(prompt);
        String s = inputReader.readLine();
        Coordinate coordinate = null;
        try{
            coordinate = new Coordinate(s);
        } catch (IllegalArgumentException e) {
            this.out.println(e.getMessage());
            return readCoordinate(prompt);
        }
        return coordinate;
    }


    /**
     * read an action choice : F, M or S from user input
     * @param prompt is the prompt message show to user
     * @return the action choice Char
     * @throws IOException
     */
    public Character readActionChoice(String prompt) throws IOException {
        this.out.println(prompt);
        String s = inputReader.readLine();
        if(s.length() != 1 || "FMSfms".indexOf(s.charAt(0)) == -1) {
            this.out.println("Your action choice: " + s + " is invalid, please try again!");
            return readActionChoice(prompt);
        }
        return Character.toUpperCase(s.charAt(0));
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

    public void doOneFire(TextPlayer enemy) throws IOException {
        String divideLine = "-".repeat(60) + "\n";
        String prompt = "Player " + this.name + ": Which coordinate do you want to fire at?\n";
        Coordinate fireAt = readCoordinate(prompt);
        // check fire coordinate validity, already hit/miss
        Character fireAtStatus = enemy.theBoard.whatIsAtForEnemy(fireAt);
        if (fireAtStatus != null) {
            if (fireAtStatus.equals(this.theBoard.getMissInfo())) {
                if (enemy.theBoard.whatIsAtForSelf(fireAt) == null) {
                    this.out.println(divideLine + "This is a miss that you've already fire at!\n" + divideLine);
                }else{
                    Ship<Character> targetShip = enemy.theBoard.fireAt(fireAt);
                    this.out.println(divideLine + "You hit a " + targetShip.getName() + "!\n" + divideLine);
                    return;
                }
            }
            else {
                if (enemy.theBoard.whatIsAtForSelf(fireAt) != null) {
                    this.out.println(divideLine + "This is a coordinate that you've already fire at!\n" + divideLine);
                }else{
                    Ship<Character> targetShip = enemy.theBoard.fireAt(fireAt);
                    this.out.println(divideLine + "You missed!\n" + divideLine);
                    return;
                }
            }
            doOneFire(enemy);
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

    //TODO
    public void doOneMove(TextPlayer enemy) throws IOException {
        String coordinatePrompt = "Player " + this.name + ": Please choose a coordinate in the ship you want to move:";
        Coordinate coordInShip = readCoordinate(coordinatePrompt);
        Ship<Character> shipToMove = this.theBoard.getShipAt(coordInShip);
        if (shipToMove == null) {
            this.out.println("This coordinate has no ship on it! Please try again!");
            doOneMove(enemy);
            return;
        }
        String placementPrompt = "Player " + this.name + ": Please enter a new placement for this ship " +
                "(coordinate + orientation):";
        Placement moveTo = readPlacement(placementPrompt);
        String message = theBoard.checkPlacementOrientation(shipToMove.getName(), moveTo);
        if (message != null) {
            this.out.println(message);
            doOneMove(enemy);
            return;
        }
        message = this.theBoard.tryMoveShip(shipToMove, shipCreationFns.get(shipToMove.getName()).apply(moveTo));
        if (message != null) {
            this.out.println(message);
            doOneMove(enemy);
            return;
        }
        this.out.println("Move " + shipToMove.getName() + " to " + moveTo.toString() + " successfully!");
        this.moveActionRemaining--;
    }

    //TODO
    public void doOneSonar(TextPlayer enemy) throws IOException {
        int sonarSize = 7;
        String prompt = "Player " + this.name + ": Please enter the center coordinate of your diamond sonar " +
                "(size = " + sonarSize + "):";
        Coordinate sonarCenter = readCoordinate(prompt);
        Sonar<Character> sonar = new DiamondSonar<>(sonarCenter, sonarSize);
        sonar.scan(enemy.theBoard);
        String divideLine = "-".repeat(60) + "\n";
        this.out.println(divideLine + sonar.generateReport() + divideLine);
        this.sonarActionRemaining--;
    }

    /**
     * current player round, can choose to fire, move or sonar scan
     * @param enemy is the player's enemy
     * @throws IOException
     */
    public void playOneTurn(TextPlayer enemy) throws IOException {
        String prompt =
                "Possible actions for Player " + this.name + ":\n" +
                "\n" +
                " F Fire at a square\n" +
                " M Move a ship to another square (" + this.moveActionRemaining + " remaining)\n" +
                " S Sonar scan (" + this.sonarActionRemaining + " remaining)\n" +
                "\n" +
                "Player " + this.name + ", what would you like to do?";
        Character choice = readActionChoice(prompt);
        switch (choice){
            case 'F':
                doOneFire(enemy);
                break;
            case 'M':
                if (this.moveActionRemaining > 0) {
                    doOneMove(enemy);
                }
                else {
                    this.out.println("You have run out of move actions! Please choose another action.");
                    playOneTurn(enemy);
                }
                break;
            case 'S':
                if (this.sonarActionRemaining > 0) {
                    doOneSonar(enemy);
                }
                else {
                    this.out.println("You have run out of sonar scan actions! Please choose another action.");
                    playOneTurn(enemy);
                }
                break;
        }
    }

    public void printWinMsg() {
        String winMsg = "|   " + "Player " + this.name + " win!" + "   |\n";
        String divideLine = "=".repeat(winMsg.length() - 1);
        this.out.println(divideLine);
        this.out.println(winMsg + divideLine);
    }


    public void doAttackingPhase(TextPlayer enemy) throws IOException {
        this.out.println("Player " + this.name + "'s turn:\n");
        String myHeader = "Your ocean";
        String enemyHeader = "Player " + enemy.name + "'s ocean";
        this.out.println(this.view.displayMyBoardWithEnemyNextToIt(enemy.view, myHeader, enemyHeader));
        playOneTurn(enemy);
    }
}
