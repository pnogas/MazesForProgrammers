package com.paulnogas.mazesforprogrammers;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;

import org.apache.commons.lang3.StringUtils;

import com.google.common.base.Optional;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

/**
 * Created by Paul Nogas on 2016-03-11
 */
public class NormalGrid implements Grid {
    Cell[][] cells;
    int rows;
    int columns;
    Random randomGenerator = new Random();

    public NormalGrid(int columns, int rows) {
        this.rows = rows;
        this.columns = columns;
        cells = new Cell[columns][rows];
        prepareGrid();
        try {
            configureCells();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    protected void prepareGrid() {
        for (int column = 0; column < columns; column++) {
            for (int row = 0; row < rows; row++) {
                cells[column][row] = new Cell(column, row);
            }
        }
    }

    protected void configureCells() throws InterruptedException {
        for (int x = 0; x < columns; x++) {
            for (int y = 0; y < rows; y++) {
                if (cells[x][y] != null) { //TODO: refactor this too to deal with masks!
                    cells[x][y].north = (y - 1 < 0) ? Optional.<Cell>absent() : Optional.fromNullable(cells[x][y - 1]);
                    cells[x][y].south = (y + 1 >= rows) ? Optional.<Cell>absent() : Optional.fromNullable(cells[x][y + 1]);
                    cells[x][y].west = (x - 1 < 0) ? Optional.<Cell>absent() : Optional.fromNullable(cells[x - 1][y]);
                    cells[x][y].east = (x + 1 >= columns) ? Optional.<Cell>absent() : Optional.fromNullable(cells[x + 1][y]);
                }
            }
        }
    }

    public Cell randomCell() {
        int column = randomGenerator.nextInt(columns);
        int row = randomGenerator.nextInt(rows);
        return cells[column][row];
    }

    @Override
    public Set<Cell> deadEnds() {
        Set<Cell> deadEnds = new HashSet<>();
        for (int x = 0; x < getColumns(); x++) {
            for (int y = 0; y < getRows(); y++) {
                if (cells[x][y].linkedCells.size() == 1) {
                    deadEnds.add(cells[x][y]);
                }
            }
        }
        return deadEnds;
    }

    public int size() {
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

    public String cellMapString() {
        String s = "";
        for (Cell[] column : cells) {
            for (Cell cell : column) {
                s += cell.toString();
            }
        }
        return s;
    }

    public String toString() {
        String output;
        try {
            output = "+" + StringUtils.repeat("---+", columns) + "\n";
            for (Cell[] column : cells) {
                String top = "|";
                String bottom = "+";
                for (Cell cell : column) {
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

        for (int x = 0; x < columns; x++) {
            for (int y = 0; y < rows; y++) {
                int x1 = x * cellSize;
                int y1 = y * cellSize;
                int x2 = x1 + cellSize;
                int y2 = y1 + cellSize;
                if (!cells[x][y].north.isPresent()) {
                    canvas.drawLine(x1, y1, x2, y1, wallPainter);
                }
                if (!cells[x][y].west.isPresent()) {
                    canvas.drawLine(x1, y1, x1, y2, wallPainter);
                }
                if (!cells[x][y].east.isPresent()) {
                    canvas.drawLine(x2, y1, x2, y2, wallPainter);
                }
                if (!cells[x][y].south.isPresent()) {
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

    @Override
    public int getCellBackgroundColour(Cell cell) {
        return Color.WHITE;
    }

    @Override
    public boolean isStartCell(Cell currentCell) {
        return false;
    }

    @Override
    public Cell getStartCell() {
        return null;
    }

    @Override
    public boolean isFinishCell(Cell currentCell) {
        return false;
    }

    @Override
    public Cell getFinishCell() {
        return null;
    }

    @Override
    public Cell[][] getCells() {
        return cells;
    }
}
