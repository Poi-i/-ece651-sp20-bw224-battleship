package edu.duke.bw224.battleship;

import java.time.temporal.ValueRange;

public class InBoundsRuleChecker<T> extends  PlacementRuleChecker<T> {
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
    protected boolean checkMyRule(Ship<T> theShip, Board<T> theBoard) {
        final ValueRange colRange = ValueRange.of(0, theBoard.getWidth() - 1);
        final ValueRange rowRange = ValueRange.of(0, theBoard.getHeight() - 1);
        for (Coordinate coord : theShip.getCoordinates()) {
            int row = coord.getRow(), col = coord.getCol();
            if (!colRange.isValidIntValue(col) || !rowRange.isValidIntValue(row)) {
                return false;
            }
        }
        return true;
    }
}
