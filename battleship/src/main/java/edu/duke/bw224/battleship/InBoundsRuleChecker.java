package edu.duke.bw224.battleship;

import java.time.temporal.ValueRange;

public class InBoundsRuleChecker<T> extends  PlacementRuleChecker<T> {
    protected final String invalidLeftMsg = "That placement is invalid: the ship goes off the left of the board.\n";
    protected final String invalidRightMsg = "That placement is invalid: the ship goes off the right of the board.\n";
    protected final String invalidTopMsg = "That placement is invalid: the ship goes off the top of the board.\n";
    protected final String invalidBottomMsg = "That placement is invalid: the ship goes off the bottom of the board.\n";
    public InBoundsRuleChecker(PlacementRuleChecker<T> next) {
        super(next);
    }

    /**
     * check whether the placement of the ship is in bound of the board
     * @param theShip is the ship we check
     * @param theBoard is the board the ship going to place on
     * @return in bound or not
     */
    @Override
    protected String checkMyRule(Ship<T> theShip, Board<T> theBoard) {
        final ValueRange colRange = ValueRange.of(0, theBoard.getWidth() - 1);
        final ValueRange rowRange = ValueRange.of(0, theBoard.getHeight() - 1);
        for (Coordinate coord : theShip.getCoordinates()) {
            int row = coord.getRow(), col = coord.getCol();
            if (!colRange.isValidIntValue(col) || !rowRange.isValidIntValue(row)) {
                if (col < 0) {
                    return invalidLeftMsg;
                } else if (col > theBoard.getWidth() - 1) {
                    return invalidRightMsg;
                } else if (row < 0) {
                    return invalidTopMsg;
                } else {
                    return invalidBottomMsg;
                }
            }
        }
        return null;
    }
}
