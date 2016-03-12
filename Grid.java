package com.paulnogas.mazesforprogrammers;

import com.google.common.base.Optional;

import java.util.Random;

/**
 * Created by Debbie on 2016-03-11.
 */
public class Grid {
    Cell[][] cells;
    int rows;
    int columns;
    Random randomGenerator = new Random();

    public Grid(int rows, int columns) {
        this.rows = rows;
        this.columns = columns;
        cells = new Cell[rows][columns];
        prepareGrid();
        configureCells();
    }

    private void prepareGrid() {
        for(int row = 0; row < rows; row++) {
            for (int column = 0; column < columns; column++) {
                cells[row][column] = new Cell(row, column);
            }
        }
    }

    private void configureCells() {
        for(int row = 0; row < rows; row++) {
            for (int column = 0; column < columns; column++) {
                cells[row][column].north = (row-1 < 0) ? Optional.<Cell>absent() : Optional.of(cells[row-1][column]);
                cells[row][column].south = (row+1 >= rows) ? Optional.<Cell>absent() : Optional.of(cells[row+1][column]);
                cells[row][column].east = (column-1 < 0) ? Optional.<Cell>absent() : Optional.of(cells[row][column-1]);
                cells[row][column].west = (column+1 >= columns) ? Optional.<Cell>absent() : Optional.of(cells[row][column+1]);
            }
        }
    }

    public Cell randomCell() {
        int row = randomGenerator.nextInt(rows);
        int column = randomGenerator.nextInt(columns);
        return cells[row][column];
    }

    private int size(){
        return rows*columns;
    }

    private Cell[] getRowFromGrid(int row){
        return cells[row];
    }

    private Cell getCellFromRow(Cell[] row, int column){
        return row[column];
    }
}
