package com.paulnogas.mazesforprogrammers;

import java.util.HashMap;
import java.util.Set;

/**
 * Created by Paul Nogas on 2016-03-14
 */
public class Distances {
    private Cell root;
    private HashMap<Cell, Integer> cells = new HashMap<>();

    public Distances(Cell cell) {
        this.root = cell;
        cells.put(root, 0);
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

    
}