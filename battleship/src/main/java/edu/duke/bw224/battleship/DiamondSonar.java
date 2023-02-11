package edu.duke.bw224.battleship;

import java.util.HashMap;
import java.util.HashSet;

public class DiamondSonar<T> implements Sonar<T> {
    protected final int size;
    protected final Coordinate center;
    protected HashMap<String, Integer> scanResult;
    protected StringBuilder report;

    public DiamondSonar(Coordinate center, int size) {
        this.center = center;
        this.size = size;
        this.scanResult = new HashMap<>();
        this.report = new StringBuilder();
    }

    public int getSize() {
        return size;
    }

    public Coordinate getCenter() {
        return center;
    }

    public HashMap<String, Integer> getScanResult() {
        return scanResult;
    }

    public StringBuilder getReport() {
        return report;
    }

    /**
     * check coordinate in the board's bound
     * @param i row
     * @param j col
     * @param board the board
     * @return coordinate in bound or not
     */
    protected boolean inBound(int i, int j, Board<T> board) {
        int width = board.getWidth(), height = board.getHeight();
        if (i < 0 || i >= width || j < 0 || j >= height) {
            return false;
        }
        return true;
    }

    /**
     * generate coordinates set in valid diamond area on the board
     * @param theBoard is the board to scan
     * @return set of valid coordinates
     */
    @Override
    public HashSet<Coordinate> generateValidScanArea(Board<T> theBoard) {
        HashSet<Coordinate> validCoords = new HashSet<>();
        int row = center.getRow(), col = center.getCol();
        for (int i = row - size / 2; i <= row + size / 2; i++) { // for number of rows i.e n rows
            int halfWidth = (size - 2 * Math.abs(row - i)) / 2;
            for (int j = col - halfWidth; j <= col + halfWidth; j++) { // 1,3,5,7,5,3,1
                if(inBound(i, j, theBoard)) {
                    validCoords.add(new Coordinate(i, j));
                }
            }
        }
        return validCoords;
    }

    /**
     * @param theBoard is the board we need to scan
     * @return
     */
    @Override
    public HashMap<String, Integer> scan(Board<T> theBoard) {
        HashSet<Coordinate> scanedCoords = generateValidScanArea(theBoard);
        for (Coordinate coordinate : scanedCoords) {
            if (theBoard.whatIsAtForSelf(coordinate) != null) {
                String shipName = theBoard.getShipAt(coordinate).getName();
                scanResult.put(shipName, scanResult.getOrDefault(shipName, 0) + 1 );
            }
        }
        return scanResult;
    }

    /**
     * generate string report or scan result
     * @return string report
     */
    @Override
    public String generateReport() {
        for (String shipName : scanResult.keySet()) {
            report.append(shipName + " occupy " + scanResult.get(shipName) + " squares\n");
        }
        return report.toString();
    }
}
