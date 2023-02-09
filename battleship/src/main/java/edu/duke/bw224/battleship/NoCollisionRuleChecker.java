package edu.duke.bw224.battleship;

public class NoCollisionRuleChecker<T> extends PlacementRuleChecker<T>{

    protected final String message = "That placement is invalid: the ship overlaps another ship.\n";
    public NoCollisionRuleChecker(PlacementRuleChecker<T> next) {
        super(next);
    }

    /**
     * check theShip does not collide with anything else on theBoard
     * @param theShip is the ship we check
     * @param theBoard is the board the ship going to place on
     * @return in bound or not
     */
    @Override
    protected String checkMyRule(Ship<T> theShip, Board<T> theBoard) {
        for (Coordinate coord : theShip.getCoordinates()) {
            if (theBoard.whatIsAtForSelf(coord) != null) {
                return message;
            }
        }
        return null;
    }
}
