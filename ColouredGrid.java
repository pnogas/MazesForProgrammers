package com.paulnogas.mazesforprogrammers;

import android.graphics.Color;
import android.util.Log;

/**
 * Created by Paul Nogas on 2016-03-14
 */
public class ColouredGrid extends DistanceGrid {

    public ColouredGrid(int columns, int rows) {
        super(columns, rows);
    }

    public int getCellBackgroundColour(Cell cell) {
        if (distances == null) {
            return Color.WHITE;
        }
        int distance = distances.getDistanceToCell(cell);
        if (distance == -1) {
            return Color.RED;
        }
        float intensity = (float)(maxDistance - distance)/maxDistance;
        int RB = Math.round(255 * intensity);
        int G = Math.round(128 + 127 * intensity);
        return Color.rgb(RB,G,RB);
    }
}
