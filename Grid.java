package com.paulnogas.mazesforprogrammers;

import java.util.Set;

/**
 * Created by Paul Nogas on 2016-03-14
 */
public interface Grid {
    public Cell cellAt(int x, int y);

    public void setDistances(Distances distances);

    public int getColumns();

    public int getRows();

    public int size();

    public int getCellBackgroundColour(Cell cell);

    public boolean isStartCell(Cell currentCell);

    public Cell getStartCell();

    public boolean isFinishCell(Cell currentCell);

    public Cell getFinishCell();

    public Cell[][] getCells();

    public Cell randomCell();

    public Set<Cell> deadEnds();
}
