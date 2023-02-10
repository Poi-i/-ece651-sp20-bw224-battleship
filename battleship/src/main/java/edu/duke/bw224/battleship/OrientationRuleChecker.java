package edu.duke.bw224.battleship;

public class OrientationRuleChecker {
    protected final String message = "That placement is invalid: the orientation of %s should be %s!\n";

    /**
     * check theShip orientation validity
     * @param theShip is the ship we check
     * @param orientation is the orientation of the ship
     * @return wrong message or null
     */
    protected String checkMyRule(Ship<Character> theShip, char orientation) {
        String shipName = theShip.getName();
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
