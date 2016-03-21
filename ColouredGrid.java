package com.paulnogas.mazesforprogrammers;

import android.graphics.Color;
import android.util.Log;

/**
 * Created by Paul Nogas on 2016-03-14
 */
public class ColouredGrid extends DistanceGrid {

    private boolean showHeatMap;

    public ColouredGrid(int columns, int rows, boolean showHeatMap) {
        super(columns, rows);
        this.showHeatMap = showHeatMap;
    }

    public int getCellBackgroundColour(Cell cell) {
        if (distances == null || !showHeatMap) {
            return Color.WHITE;
        }
        int distance = distances.getDistanceToCell(cell);
        if (distance == -1) {
            return Color.BLACK;
        }
        float intensity = (float)(maxDistance - distance)/maxDistance;
        int GB = Math.round(255 * intensity);
        int R = Math.round(180 + 75 * intensity);
        return Color.rgb(R,GB,GB);
    }
}
