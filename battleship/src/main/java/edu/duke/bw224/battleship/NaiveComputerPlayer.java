package edu.duke.bw224.battleship;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;

public class NaiveComputerPlayer extends TextPlayer{
    protected final PrintStream showEnemy;


    public NaiveComputerPlayer(String name, Board<Character> theBoard, BufferedReader inputReader, PrintStream out,
                               AbstractShipFactory<Character> factory, PrintStream showEnemy) {
        super(name, theBoard, inputReader, out, factory);
        this.showEnemy = showEnemy;
    }

    public NaiveComputerPlayer(String name, Board<Character> theBoard, BufferedReader inputReader, PrintStream toTell,
                               AbstractShipFactory<Character> factory) {
        this(name, theBoard, inputReader, new PrintStream(OutputStream.nullOutputStream()), factory, toTell);
    }

    /**
     * No board state show, only show action
     * @param enemy
     * @throws IOException
     */
    @Override
    public void doOneFire(TextPlayer enemy) throws IOException {
        Coordinate fireAt = readCoordinate("");
        Ship<Character> firedShip = enemy.theBoard.fireAt(fireAt);
        if (firedShip != null) {
            String message = "Player " + this.name + " hit your " + firedShip.getName() +
                    " at " + fireAt.toString() + "!\n";
            showEnemy.println(message);
        }
    }

    /**
     * Naive computer can only fire
     * @param enemy
     * @throws IOException
     */
    @Override
    public void doAttackingPhase(TextPlayer enemy) throws IOException {
        doOneFire(enemy);
    }

    /**
     *
     */
    @Override
    public void printWinMsg() {
        String winMsg = "|   " + "Player " + this.name + " win!" + "   |\n";
        String divideLine = "=".repeat(winMsg.length() - 1);
        this.showEnemy.println(divideLine);
        this.showEnemy.println(winMsg + divideLine);
    }
}
