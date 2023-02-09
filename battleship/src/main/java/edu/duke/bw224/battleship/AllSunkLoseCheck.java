package edu.duke.bw224.battleship;

public class AllSunkLoseCheck<T> extends LoseChecker<T>{

    public AllSunkLoseCheck(LoseChecker<T> next) {
        super(next);
    }

    /**
     * @param theBoard
     * @return
     */
    @Override
    protected boolean checkMyRule(Board<T> theBoard) {
        for (Ship<T> ship : theBoard.getShips()) {
            if (!ship.isSunk()) {
                return false;
            }
        }
        return true;
    }
}
