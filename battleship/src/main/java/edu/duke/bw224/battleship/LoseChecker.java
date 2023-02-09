package edu.duke.bw224.battleship;

/**
 * Abstract class for win state chaining check
 * @param <T>
 */
public abstract class LoseChecker<T> {
    private LoseChecker<T> next;

    public LoseChecker(LoseChecker<T> next) {
        this.next = next;
    }

    protected abstract boolean checkMyRule(Board<T> theBoard);

    public boolean checkLose (Board<T> theBoard) {
        //if we fail our own rule: not win
        if (!checkMyRule(theBoard)) {
            return false;
        }
        //otherwise, ask the rest of the chain.
        if (next != null) {
            return next.checkLose(theBoard);
        }
        //if there are no more rules, then we won
        return true;
    }
}
