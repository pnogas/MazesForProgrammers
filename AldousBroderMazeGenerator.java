package com.paulnogas.mazesforprogrammers;

import java.util.HashSet;
import java.util.Random;

/**
 * Created by Paul Nogas on 2016-03-19.
 */
public class AldousBroderMazeGenerator implements MazeGenerator {

    @Override
    public Grid generateMaze(Grid grid) {
        Random random = new Random();
        int columns = grid.getColumns();
        int rows = grid.getRows();
        Cell cell = grid.cellAt(random.nextInt(columns), random.nextInt(rows));
        int unvisitedCellCount = columns * rows - 1;

        Cell neighbor = new Cell(-1, -1); //just to avoid null pointer
        int hashSetSize;
        int item;
        int i;

        while (unvisitedCellCount > 0) {
            HashSet<Cell> neighbors = cell.neighbours();
            hashSetSize = neighbors.size();
            item = random.nextInt(hashSetSize);
            i = 0;
            for (Cell obj : neighbors) {
                if (i == item) {
                    neighbor = obj;
                }
                i = i + 1;
            }
            if (neighbor.linkedCells.isEmpty()) {
                cell.link(neighbor, true);
                unvisitedCellCount -= 1;
            }
            cell = neighbor;
        }
        return grid;
    }
}
