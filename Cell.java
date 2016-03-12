package com.paulnogas.mazesforprogrammers;

import com.google.common.base.Optional;

import java.util.HashSet;

/**
 * Created by Debbie on 2016-03-11.
 */
public class Cell {
    int row;
    int column;
    Optional<Cell> north;
    Optional<Cell> south;
    Optional<Cell> east;
    Optional<Cell> west;
    HashSet<Cell> linkedCells;

    public Cell(int row, int column) {
        this.row = row;
        this.column = column;
        linkedCells = new HashSet<>();
    }

    public void link(Cell cell, boolean biDirectional) {
        linkedCells.add(cell);
        if (biDirectional) {
            cell.link(this, false);
        }
    }

    public void unlink(Cell cell, boolean biDirectional) {
        linkedCells.remove(cell);
        if (biDirectional) {
            cell.unlink(this, false);
        }
    }

    public HashSet<Cell> getLinks() {
        return linkedCells;
    }

    public boolean isLinked(Cell cell) {
        return linkedCells.contains(cell);
    }

    public HashSet<Cell> neighbours() {
        HashSet<Cell> neighbourCells = new HashSet<>();
        if (north.isPresent()) {
            neighbourCells.add(north.get());
        }
        if (south.isPresent()) {
            neighbourCells.add(south.get());
        }
        if (east.isPresent()) {
            neighbourCells.add(east.get());
        }
        if (west.isPresent()) {
            neighbourCells.add(west.get());
        }
        return neighbourCells;
    }
}
