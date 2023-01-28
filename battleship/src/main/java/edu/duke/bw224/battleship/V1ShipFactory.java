package edu.duke.bw224.battleship;

public class V1ShipFactory implements AbstractShipFactory<Character> {

    protected Ship<Character> createShip(Placement where, int w, int h, char letter, String name) {
        if (Character.toUpperCase(where.getOrientation()) == 'V') {
            return new RectangleShip<>(name, where.getWhere(), h, w, letter, '*');
        }
        return new RectangleShip<>(name, where.getWhere(), w, h, letter, '*');
    }

    /**
     * @param where specifies the location and orientation of the ship to make
     * @return Submarine:   1x2 's'
     */
    @Override
    public Ship<Character> makeSubmarine(Placement where) {
        return createShip(where, 1, 2, 's', "Submarine");
    }

    /**
     * @param where specifies the location and orientation of the ship to make
     * @return Battleship:  1x4 'b'
     */
    @Override
    public Ship<Character> makeBattleship(Placement where) {
        return createShip(where, 1, 4, 'b', "Battleship");
    }

    /**
     * @param where specifies the location and orientation of the ship to make
     * @return Carrier:     1x6 'c'
     */
    @Override
    public Ship<Character> makeCarrier(Placement where) {
        return createShip(where, 1, 6, 'c', "Carrier");
    }

    /**
     * @param where specifies the location and orientation of the ship to make
     * @return Destroyer:   1x3 'd'
     */
    @Override
    public Ship<Character> makeDestroyer(Placement where) {
        return createShip(where, 1, 3, 'd', "Destroyer");
    }
}
