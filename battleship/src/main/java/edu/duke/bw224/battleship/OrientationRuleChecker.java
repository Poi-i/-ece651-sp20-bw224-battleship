package edu.duke.bw224.battleship;

public class OrientationRuleChecker<T> {
    protected final String message = "That placement is invalid: the orientation of %s should be %s!\n";

    /**
     * check theShip orientation validity
     * @param shipName is the ship we check
     * @param orientation is the orientation of the ship
     * @return wrong message or null
     */
    protected String checkMyRule(String shipName, char orientation) {
        if (shipName.equals("Submarine") || shipName.equals("Destroyer")) {
            if("VH".indexOf(orientation) == -1){
                return String.format(message, shipName, "V or H");
            }
        }
        else {
            if("ULRD".indexOf(orientation) == -1) {
                return String.format(message, shipName, "U, L, R, or D");
            }
        }
        return null;
    }
}
