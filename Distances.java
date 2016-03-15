package com.paulnogas.mazesforprogrammers;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Created by Paul Nogas on 2016-03-14
 */
public class Distances {
    private Cell root;
    private Cell goal;
    private HashMap<Cell, Integer> cells = new HashMap<>();

    public Distances(Cell cell) {
        this.root = cell;
        cells.put(root, 0);
        goal = null;
    }

    public Integer getDistanceToCell(Cell cell) {
        return cells.get(cell);
    }

    public void setDistanceToCell(Cell cell, Integer distance) {
        cells.put(cell, distance);
    }

    public Set<Cell> getMeasuredCells(){
        return cells.keySet();
    }
    public Distances pathTo(Cell goal) {
        Cell currentCell = goal;
        Distances breadCrumbs = new Distances(root);
        breadCrumbs.setDistanceToCell(currentCell, cells.get(currentCell));

        while (currentCell != root) {
            for (Cell neighbour : currentCell.linkedCells) {
                if (cells.get(neighbour) < cells.get(currentCell)) {
                    breadCrumbs.setDistanceToCell(neighbour, cells.get(neighbour));
                    currentCell = neighbour;
                    break;
                }
            }
        }
        return breadCrumbs;
    }

    public Cell getRoot() {
        return root;
    }

    public Cell getFurthestCell() {
        if (goal != null) {
            return goal;
        }
        int maxDistance = 0;
        Cell maxCell = root;

        for (Cell cell : cells.keySet()) {
            if (cells.get(cell) > maxDistance) {
                maxCell = cell;
                maxDistance = cells.get(cell);
            }
        }
        goal = maxCell;
        return maxCell;
    }
}