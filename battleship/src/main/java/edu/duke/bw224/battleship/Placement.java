package edu.duke.bw224.battleship;

import java.util.Objects;

public class Placement {
    final Coordinate where;
    final char orientation;

    /**
     * default constructor
     * @param where
     * @param orientation
     * @throws IllegalArgumentException if orientation is not in "vVhH"
     */
    public Placement(Coordinate where, char orientation) {
        this.where = where;
        orientation = Character.toUpperCase(orientation);
        if("VHULDR".indexOf(orientation) == -1){
            throw new IllegalArgumentException("the orientation should be 'V' or 'H', 'U', 'L', 'R', 'D' but is "
                    + orientation + "\n");
        }
        this.orientation = orientation;
    }

    /**
     * Generate Placement based on String like "A2H" -> (0, 2): H
     * @param descr is the describer string
     * @throws IllegalArgumentException
     */
    public Placement(String descr) {
        if (descr.length() < 3) {
            throw new IllegalArgumentException("Describer must be like \"A2V\" but is " + descr + "\n");
        }
        String coordinateDescr = descr.substring(0, 2);
        this.where = new Coordinate(coordinateDescr);
        String orientationDescr = descr.substring(2);
        char orientation = Character.toUpperCase(orientationDescr.charAt(0));
        if(orientationDescr.length() != 1 || !Character.isLetter(orientation) || "VHULDR".indexOf(orientation) == -1){
            throw new IllegalArgumentException("the orientation should be either 'V' or 'H' but is "
                    + orientationDescr + "\n");
        }
        this.orientation = orientation;
    }

    public Coordinate getWhere() {
        return where;
    }

    public char getOrientation() {
        return orientation;
    }

    @Override
    public boolean equals(Object o) {
        if (o.getClass().equals(getClass())) {
            Placement p = (Placement) o;
            return where.equals(p.where) && orientation == p.orientation;
        }
        return false;
    }

    @Override
    public String toString() {
        return where.toString() + ": " + orientation;
    }

    @Override
    public int hashCode() {
        return (where.toString() + orientation).hashCode();
    }
}
