package edu.duke.bw224.battleship;

public class NoCollisionRuleChecker<T> extends PlacementRuleChecker<T>{

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
    protected boolean checkMyRule(Ship<T> theShip, Board<T> theBoard) {
        for (Coordinate coord : theShip.getCoordinates()) {
            if (theBoard.whatIsAt(coord) != null) {
                return false;
            }
        }
        return true;
    }
}
