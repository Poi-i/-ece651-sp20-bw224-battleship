package edu.duke.bw224.battleship;

public class AllSunkWinCheck<T> extends WinChecker<T>{

    public AllSunkWinCheck(WinChecker<T> next) {
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
