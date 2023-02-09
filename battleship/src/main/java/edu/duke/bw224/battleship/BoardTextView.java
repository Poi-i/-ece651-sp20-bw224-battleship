package edu.duke.bw224.battleship;

import java.util.function.Function;

/**
 * This class handles textual display of
 * a Board<Character> (i.e., converting it to a string to show
 * to the user).
 * It supports two ways to display the Board:
 * one for the player's own board, and one for the
 * enemy's board.
 */
public class BoardTextView {
    /**
     * The Board to display
     */
    private final Board<Character> toDisplay;
    /**
     * Constructs a BoardView, given the board it will display.
     * @param toDisplay is the Board to display
     * @throws IllegalArgumentException if the board is larger than 10x26
     */
    public BoardTextView(Board<Character> toDisplay) {
        this.toDisplay = toDisplay;
        if (toDisplay.getWidth() > 10 || toDisplay.getHeight() > 26) {
            throw new IllegalArgumentException(
                    "Board must be no larger than 10x26, but is " + toDisplay.getWidth() + "x" + toDisplay.getHeight()
            );
        }
    }

    /**
     * This makes the whole board of self
     * @return the String of whole board
     */
    public String displayMyOwnBoard() {
        return displayAnyBoard((c) -> toDisplay.whatIsAtForSelf(c));
    }

    /**
     * This makes the whole board of enemy
     * @return the String of whole board
     */
    public String displayMyEnemyBoard() {
        return displayAnyBoard((c) -> toDisplay.whatIsAtForEnemy(c));
    }

    /**
     * display any board based on the given function
     * @param getSquareFn is the function to get char to display for a square
     * @return string of the board to display
     */
    protected String displayAnyBoard(Function<Coordinate, Character> getSquareFn) {
        String header = makeHeader();
        StringBuilder ans = new StringBuilder(header);
        for (int row = 0; row < toDisplay.getHeight(); row++){
            char rowIdentifier = (char)('A' + row);
            ans.append(rowIdentifier).append(" ");
            String sep = "";
            //ans.append(sep.repeat(Math.max(0, toDisplay.getWidth() - 1)));
            for(int col = 0; col < toDisplay.getWidth(); col ++){
                Coordinate c = new Coordinate(row, col);
                String cell =  getSquareFn.apply(c) == null ? " " : getSquareFn.apply(c).toString();
                ans.append(sep).append(cell);
                sep = "|";
            }
            ans.append(" ").append(rowIdentifier).append("\n");
        }
        ans.append(header);
        return ans.toString();
    }

    /**
     * This makes the header line, e.g. 0|1|2|3|4\n
     *
     * @return the String that is the header line for the given board
     */
    String makeHeader(){
        StringBuilder ans = new StringBuilder(("  "));
        String sep = "";    //start with nothing to separate, then switch to | to separate
        for (int i = 0; i < toDisplay.getWidth(); i++) {
            ans.append(sep);
            ans.append(i);
            sep = "|";
        }
        ans.append("\n");
        return ans.toString();
    }

    /**
     * Display player's board and enemy's board side by side
     * @param enemyView is enemy board view
     * @param myHeader is player's header
     * @param enemyHeader is enemy's header
     * @return the side by side boards in string
     */
    public String displayMyBoardWithEnemyNextToIt(BoardTextView enemyView, String myHeader, String enemyHeader) {
        // split board string by \n
        String[] myStrings = this.displayMyOwnBoard().split("\n");
        String[] enemyStrings = enemyView.displayMyEnemyBoard().split("\n");
        StringBuilder boards = new StringBuilder();
        int w = this.toDisplay.getWidth();
        // build new header
        boards.append(" ".repeat(5)).append(myHeader);
        int interval = 2 * w + 22 - boards.length();
        boards.append(" ".repeat(interval)).append(enemyHeader + "\n");

        // build each new row
        for (int i = 0; i < myStrings.length; ++i) {
            boards.append(myStrings[i]);
            interval = 2 * w + 19 - myStrings[i].length();
            boards.append(" ".repeat(interval)).append(enemyStrings[i] + "\n");
        }
        return boards.toString();
    }

}
