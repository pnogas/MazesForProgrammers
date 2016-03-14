package com.paulnogas.mazesforprogrammers;

import java.util.HashMap;

/**
 * Created by Paul Nogas on 2016-03-14.
 */
public class DistanceGrid extends NormalGrid {

    private Distances distances;

    public DistanceGrid(int rows, int columns) {
        super(rows, columns);
    }

    @Override
    public String contentsOf(Cell cell) {
        if (distances != null && distances.getDistanceToCell(cell) != null) {
            return Integer.toString(distances.getDistanceToCell(cell), Character.MAX_RADIX);
        } else {
            return super.contentsOf(cell);
        }
    }

    public void setDistances(Distances distances) {
        this.distances = distances;
    }
}
