package com.paulnogas.mazesforprogrammers;

import java.util.Arrays;

/**
 * Created by Paul Nogas on 2016-03-25.
 */
public class Mask {

    private int columns;
    private int rows;
    private boolean[][] bits;

    public Mask(int columns, int rows) {
        this.columns = columns;
        this.rows = rows;
        bits = new boolean[columns][rows];
        initializeBitsToTrue();
    }

    private void initializeBitsToTrue() {
        for (boolean[] row : bits) {
            Arrays.fill(row, true);
        }
    }

    public int getColumns() {
        return columns;
    }

    public int getRows() {
        return rows;
    }

    public void set(int x, int y, boolean on){
        bits[x][y] = on;
    }

    public boolean isOn(int x, int y) {
        ////return x > 0 && x < columns && y > 0 && y < rows && bits[x][y];
        if (x > -1 && x < columns && y > -1 && y < rows) {
            return bits[x][y];
        }
        return false;
    }

    public int count(){
        int count = 0;
        for (int x = 0; x < columns; x ++) {
            for (int y=0; y < rows; y++) {
                if (bits[x][y]) {
                    count++;
                }
            }
        }
        return count;
    }

    public int[] randomXYLocation(){
        ensureAtLeastOneTrueBit(); //inifite loops are dangerous....
        while (true) {
            int x = Utils.randomInt(columns);
            int y = Utils.randomInt(rows);
            if (bits[x][y]) {
                return new int[]{x, y};
            }
        }
    }

    private void ensureAtLeastOneTrueBit() {
        if (count() < 1) {
            throw new RuntimeException("There are no true bits in the mask. This should not be possible");
        }
    }


}
