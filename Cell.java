package com.paulnogas.mazesforprogrammers;

import com.google.common.base.Optional;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by Paul Nogas on 2016-03-11
 */
public class Cell {
    public int row;
    public int column;
    Optional<Cell> north = Optional.absent();
    Optional<Cell> south = Optional.absent();
    Optional<Cell> east = Optional.absent();
    Optional<Cell> west = Optional.absent();
    Set<Cell> linkedCells = new HashSet<>();

    public Cell(int column, int row) {
        this.column = column;
        this.row = row;
        linkedCells = new HashSet<>();
    }

    public void linkBiDirectional(Cell cell) {
        linkedCells.add(cell);
        cell.linkUniDirectional(this);
    }

    private void linkUniDirectional(Cell cell) {
        linkedCells.add(cell);
    }

    public void unlinkBiDirectional(Cell cell) {
        linkedCells.remove(cell);
        cell.unlinkUniDirectional(this);
    }

    private void unlinkUniDirectional(Cell cell) {
        linkedCells.remove(cell);
    }

    public Set<Cell> getLinks() {
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

    public Distances getDistances(){
        Distances distances = new Distances(this);
        Set<Cell> frontierCells = new HashSet<>();
        frontierCells.add(this);
        while (!frontierCells.isEmpty()) {
            Set<Cell> newFrontier = new HashSet<>();
            for (Cell currentCell : frontierCells) {
                for(Cell linkedCell : currentCell.linkedCells){
                    if(!distances.getMeasuredCells().contains(linkedCell)) {
                        distances.setDistanceToCell(linkedCell, distances.getDistanceToCell(currentCell) + 1);
                        newFrontier.add(linkedCell);
                    }
                }
            }
            frontierCells = newFrontier;
        }
        return distances;
    }

    public String simpleString(){
        return String.format("CELL(%s,%s):", column, row);
    }

    @Override
    public String toString() {
        String s = "";
        s += String.format("CELL(%s,%s):", column, row);
        if (north.isPresent()) {
            s += String.format("N:(%s,%s)", north.get().column, north.get().row);
            if (linkedCells.contains(north.get())) {
                s += "L";
            }
        }
        if (south.isPresent()) {
            s += String.format("S:(%s,%s)", south.get().column, south.get().row);
            if (linkedCells.contains(south.get())) {
                s += "L";
            }
        }
        if (west.isPresent()) {
            s += String.format("W:(%s,%s)", west.get().column, west.get().row);
            if (linkedCells.contains(west.get())) {
                s += "L";
            }
        }
        if (east.isPresent()) {
            s += String.format("E:(%s,%s)", east.get().column, east.get().row);
            if (linkedCells.contains(east.get())) {
                s += "L";
            }
        }
        return s;
    }
}
