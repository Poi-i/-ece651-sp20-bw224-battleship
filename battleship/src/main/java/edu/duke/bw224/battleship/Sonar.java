package edu.duke.bw224.battleship;

import java.util.HashMap;
import java.util.HashSet;

public interface Sonar<T> {

    public int getSize();

    public Coordinate getCenter();

    public HashMap<String, Integer> getScanResult();

    public StringBuilder getReport();

    /**
     * scan ship information on the board, using a sonar at specified coordinate
     * @param theBoard is the board we need to scan
     * @return (shipName, occupied square number)
     */
    public HashMap<String, Integer> scan(Board<T> theBoard);

    /**
     * generate valid scan area
     * @param theBoard is the board to scan
     * @return set of valid coordinates
     */
    public HashSet<Coordinate> generateValidScanArea(Board<T> theBoard);

    /**
     * generate string report or scan result
     * @return string report
     */
    public String generateReport();
}
