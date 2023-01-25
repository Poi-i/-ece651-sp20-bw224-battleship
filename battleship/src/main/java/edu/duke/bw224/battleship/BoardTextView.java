package edu.duke.bw224.battleship;

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
     * This makes the whole board
     * @return the String of whole board
     */
    public String displayMyOwnBoard() {
        String header = makeHeader();
        StringBuilder ans = new StringBuilder(header);
        for (int row = 0; row < toDisplay.getHeight(); row++){
            char rowIdentifier = (char)('A' + row);
            ans.append(rowIdentifier).append(" ");
            String sep = "";
            //ans.append(sep.repeat(Math.max(0, toDisplay.getWidth() - 1)));
            for(int col = 0; col < toDisplay.getWidth(); col ++){
                Coordinate c = new Coordinate(row, col);
                String cell = toDisplay.whatIsAt(c) == null ? " " : toDisplay.whatIsAt(c).toString();
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

}
