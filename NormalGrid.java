package com.paulnogas.mazesforprogrammers;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import org.apache.commons.lang3.StringUtils;

import com.google.common.base.Optional;

import java.util.Random;

/**
 * Created by Paul Nogas on 2016-03-11
 */
public class NormalGrid implements Grid {
    Cell[][] cells;
    int rows;
    int columns;
    Random randomGenerator = new Random();

    public NormalGrid(int rows, int columns) {
        this.rows = rows;
        this.columns = columns;
        cells = new Cell[rows][columns];
        prepareGrid();
        configureCells();
    }

    private void prepareGrid() {
        for (int row = 0; row < rows; row++) {
            for (int column = 0; column < columns; column++) {
                cells[row][column] = new Cell(row, column);
            }
        }
    }

    private void configureCells() {
        for (int row = 0; row < rows; row++) {
            for (int column = 0; column < columns; column++) {
                cells[row][column].north = (row - 1 < 0) ? Optional.<Cell>absent() : Optional.of(cells[row - 1][column]);
                cells[row][column].south = (row + 1 >= rows) ? Optional.<Cell>absent() : Optional.of(cells[row + 1][column]);
                cells[row][column].west = (column - 1 < 0) ? Optional.<Cell>absent() : Optional.of(cells[row][column - 1]);
                cells[row][column].east = (column + 1 >= columns) ? Optional.<Cell>absent() : Optional.of(cells[row][column + 1]);
            }
        }
    }

    public Cell randomCell() {
        int row = randomGenerator.nextInt(rows);
        int column = randomGenerator.nextInt(columns);
        return cells[row][column];
    }

    private int size() {
        return rows * columns;
    }

    private Cell[] getRowFromGrid(int row) {
        return cells[row];
    }

    private Cell getCellFromRow(Cell[] row, int column) {
        return row[column];
    }

    public String contentsOf(Cell cell) {
        return " ";
    }


    public String toString() {
        String output;
        try {
            output = "+" + StringUtils.repeat("---+", columns) + "\n";
            for (Cell[] row : cells) {
                String top = "|";
                String bottom = "+";
                for (Cell cell : row) {
                    String body = " " + contentsOf(cell) + " ";
                    String east_boundary = cell.east.isPresent() && cell.linkedCells.contains(cell.east.get()) ? " " : "|";
                    top += body;
                    top += east_boundary;
                    String south_boundary = cell.south.isPresent() && cell.linkedCells.contains(cell.south.get()) ? "   " : "---";
                    String corner = "+";
                    bottom += south_boundary;
                    bottom += corner;
                }
                output += top;
                output += "\n";
                output += bottom;
                output += "\n";
            }
        } catch (IllegalStateException e) {
            output = e.toString();
        }
        return output;
    }

    public void drawToCanvas(Canvas canvas) {
        int cellSize = 10;
        //int image_width = cellSize * columns;
        //int image_height = cellSize * rows;
        //canvas.onSizeChanged();
        Paint wallPainter = makeWallPainter();

        for (int row = 0; row < rows; row++) {
            for (int column = 0; column < columns; column++) {
                int x1 = column * cellSize;
                int y1 = row * cellSize;
                int x2 = x1 + cellSize;
                int y2 = y1 + cellSize;
                if (!cells[row][column].north.isPresent()) {
                    canvas.drawLine(x1, y1, x2, y1, wallPainter);
                }
                if (!cells[row][column].west.isPresent()) {
                    canvas.drawLine(x1, y1, x1, y2, wallPainter);
                }
                if (!cells[row][column].east.isPresent()) {
                    canvas.drawLine(x2, y1, x2, y2, wallPainter);
                }
                if (!cells[row][column].south.isPresent()) {
                    canvas.drawLine(x1, y2, x2, y2, wallPainter);
                }
            }
        }
    }

    private Paint makeWallPainter() {
        Paint mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setColor(Color.BLACK);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeJoin(Paint.Join.ROUND);
        mPaint.setStrokeWidth(4f);
        return mPaint;
    }

    public void drawToCanvas(MazeDrawView mazeDrawView) {

    }

    @Override
    public Cell cellAt(int x, int y) {
        return cells[x][y];
    }

    @Override
    public void setDistances(Distances distances) {
        //do nothing
    }

    @Override
    public int getColumns() {
        return columns;
    }

    @Override
    public int getRows() {
        return rows;
    }
}