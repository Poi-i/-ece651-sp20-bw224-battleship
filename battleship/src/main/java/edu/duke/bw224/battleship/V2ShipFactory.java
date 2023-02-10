package edu.duke.bw224.battleship;

public class V2ShipFactory extends V1ShipFactory {

    /**
     * make battleship
     * @param where specifies the location and orientation of the ship to make
     * @return a battleship
     */
    @Override
    public Ship<Character> makeBattleship(Placement where) {
        return new TShip<Character>("Battleship", where.getWhere(), 2, where.getOrientation(), 'b', '*');
    }

    /**
     * make carrier
     * @param where specifies the location and orientation of the ship to make
     * @return a carrier
     */
    @Override
    public Ship<Character> makeCarrier(Placement where) {
        return new ZShip<Character>("Carrier", where.getWhere(), where.getOrientation(), 'c', '*');
    }


}
