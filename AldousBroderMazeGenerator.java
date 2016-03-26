package com.paulnogas.mazesforprogrammers;

import android.util.Log;

import java.util.HashSet;
import java.util.Random;

/**
 * Created by Paul Nogas on 2016-03-19
 */
public class AldousBroderMazeGenerator implements MazeGenerator {

    @Override
    public Grid generateMaze(Grid grid) throws InterruptedException {
        Cell cell = grid.randomCell();
        int unvisitedCellCount = grid.size() - 1;
        Cell neighbor;
        while (unvisitedCellCount > 0) {
            HashSet<Cell> neighbors = cell.neighbours();
            neighbor = getRandomNeighbour(neighbors);
            if (neighbor.linkedCells.isEmpty()) {
                Log.e("PAUL", "linked cells: " + cell.simpleString() + ", " + neighbor.simpleString());
                cell.linkBiDirectional(neighbor);
                unvisitedCellCount -= 1;
            }
            cell = neighbor;
        }
        return grid;
    }

    private Cell getRandomNeighbour(HashSet<Cell> neighbors) {
        Cell neighbor = null;
        int i = 0;
        int hashSetSize = neighbors.size();
        int item = Utils.randomInt(hashSetSize);
        for (Cell obj : neighbors) {
            if (i == item) {
                neighbor = obj;
            }
            i = i + 1;
        }
        return neighbor;
    }
}
