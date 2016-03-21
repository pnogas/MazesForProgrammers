package com.paulnogas.mazesforprogrammers;

/**
 * Created by Paul Nogas on 2016-03-20.
 */
public class PlayerCircle {
    Cell currentCell;
    private int wallWidth;
    private int x;
    private int y;
    private int radius;
    private MazeDimensions mazeDimensions;

    public PlayerCircle(Cell startCell, MazeDimensions mazeDimensions, int wallWidth) {
        currentCell = startCell;
        this.mazeDimensions = mazeDimensions;
        this.wallWidth = wallWidth;
        updateParams();
    }

    public void setMazeDimensions(MazeDimensions mazeDimensions) {
        this.mazeDimensions = mazeDimensions;
        updateParams();
    }

    private void updateParams() {
        int x1 = mazeDimensions.xOrigin + currentCell.column * mazeDimensions.CellWidth;
        int y1 = mazeDimensions.yOrigin + currentCell.row * mazeDimensions.CellHeight;
        int x2 = x1 + mazeDimensions.CellWidth;
        int y2 = y1 + mazeDimensions.CellHeight;
        x = (x1+x2)/2;
        y = (y1+y2)/2;
        radius = Math.min(mazeDimensions.CellHeight/2, mazeDimensions.CellWidth/2) - wallWidth;
    }

    public boolean tryMoveDown() {
        if (currentCell.linkedCells.contains(currentCell.south.orNull())) {
            currentCell = currentCell.south.get();
            updateParams();
            return true;
        }
        return false;
    }

    public boolean tryMoveUp() {
        if (currentCell.linkedCells.contains(currentCell.north.orNull())) {
            currentCell = currentCell.north.get();
            updateParams();
            return true;
        }
        return false;
    }

    public boolean tryMoveRight() {
        if (currentCell.linkedCells.contains(currentCell.east.orNull())) {
            currentCell = currentCell.east.get();
            updateParams();
            return true;
        }
        return false;
    }

    public boolean tryMoveLeft() {
        if (currentCell.linkedCells.contains(currentCell.west.orNull())) {
            currentCell = currentCell.west.get();
            updateParams();
            return true;
        }
        return false;
    }


    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getRadius() {
        return radius;
    }
}
