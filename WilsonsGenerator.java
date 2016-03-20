package com.paulnogas.mazesforprogrammers;

import android.util.Log;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.Random;
import java.util.Set;

/**
 * Created by Paul Nogas on 2016-03-19.
 */
public class WilsonsGenerator implements MazeGenerator {
    @Override
    public Grid generateMaze(Grid grid) {
        Set<Cell> unvisitedCells = new HashSet<>();
        for (int x = 0; x < grid.getColumns(); x++) {
            //confirm should be able to replace
            //unvisitedCells.addAll(Arrays.asList(grid.cells[x]).subList(0, grid.rows));
            for (int y = 0; y < grid.getRows(); y++) {
                unvisitedCells.add(grid.cellAt(x,y));
            }
        }
        Cell firstCell = (Cell) Utils.getRandomElementFromSet(unvisitedCells);
        unvisitedCells.remove(firstCell);
        LinkedList<Cell> path = new LinkedList<>();
        Cell cell;
        while (!unvisitedCells.isEmpty()) {
            int position;
            cell = (Cell) Utils.getRandomElementFromSet(unvisitedCells);
            path.add(cell);
            while (unvisitedCells.contains(cell)) {
                cell = (Cell) Utils.getRandomElementFromSet(cell.neighbours());
                position = path.indexOf(cell);
                String pathString = "";
                for (Cell a : path) {
                    pathString += a.simpleString();
                }
                if (position > -1) {
                    path = new LinkedList<>(path.subList(0, position));
                    if (path.size() > 0) {
                        cell = path.get(path.size()-1); // this was the missing part
                    } else {
                        cell = (Cell) Utils.getRandomElementFromSet(unvisitedCells);
                    }
                } else {
                    path.add(cell);
                }
            }
            for (int i = 0; i < path.size() - 1; i++) {
                path.get(i).link(path.get(i + 1), true);
                unvisitedCells.remove(path.get(i));
            }
            path.clear();
            for (Cell a : unvisitedCells) {
            }
        }
        return grid;
    }
}
