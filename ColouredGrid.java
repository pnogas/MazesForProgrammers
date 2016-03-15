package com.paulnogas.mazesforprogrammers;

import android.graphics.Color;
import android.util.Log;

/**
 * Created by Paul Nogas on 2016-03-14
 */
public class ColouredGrid extends DistanceGrid {

    public ColouredGrid(int rows, int columns) {
        super(rows, columns);
    }

    public int getCellBackgroundColour(Cell cell) {
        if (distances == null) {
            return Color.WHITE;
        }
        int distance = distances.getDistanceToCell(cell);
        float intensity = (float)(maxDistance - distance)/maxDistance;
        int RB = Math.round(255 * intensity);
        int G = Math.round(128 + 127 * intensity);
        return Color.rgb(RB,G,RB);
    }
}
