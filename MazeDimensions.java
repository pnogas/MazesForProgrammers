package com.paulnogas.mazesforprogrammers;

/**
 * Created by Paul Nogas on 2016-03-20.
 */
public class MazeDimensions {
    public int xOrigin;
    public int yOrigin;
    public int xLimit;
    public int yLimit;
    public int CellWidth;
    public int CellHeight;

    public MazeDimensions(int xOrigin, int yOrigin, int xLimit, int yLimit, int cellWidth, int cellHeight) {
        this.xOrigin = xOrigin;
        this.yOrigin = yOrigin;
        this.xLimit = xLimit;
        this.yLimit = yLimit;
        CellWidth = cellWidth;
        CellHeight = cellHeight;
    }
}
