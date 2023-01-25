package edu.duke.bw224.battleship;

import java.util.Objects;

public class Coordinate {
    private final int row;
    private final int col;

    public Coordinate(int row, int col) {
        this.row = row;
        this.col = col;
    }

    /**
     * Generate Coordinate based on String like "A2" -> (0, 2)
     * @param descr is the String of coordinate
     * @throws IllegalArgumentException
     */
    public Coordinate(String descr) {
        // split string into letters and digits
        String[] letters = descr.split("(?<=\\D)(?=\\d)|(?<=\\d)(?=\\D)");

        if (letters.length != 2 || letters[0].length() > 1 || !Character.isLetter(letters[0].charAt(0))){
            throw new IllegalArgumentException("Describer must be combination with a letter and a number like \"A2\" but is " + descr);
        }
        int colLetter = Integer.parseInt(letters[1]);
        if (colLetter > 10){
            throw new IllegalArgumentException("The colum letter must within 0~10 but is " + colLetter);
        }
        char rowLetter = Character.toUpperCase(letters[0].charAt(0));
        this.row = rowLetter - 'A';
        this.col = colLetter;
    }

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }
    @Override
    public boolean equals(Object o) {
        if (o.getClass().equals(getClass())) {
            Coordinate c = (Coordinate) o;
            return row == c.row && col == c.col;
        }
        return false;
    }

    @Override
    public String toString() {
        return "(" + row + ", " + col + ")";
    }

    @Override
    public int hashCode() {
        return toString().hashCode();
    }




}
