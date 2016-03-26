package com.paulnogas.mazesforprogrammers;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by Paul Nogas on 2016-03-11
 */
public class BinaryTreeMazeGenerator implements MazeGenerator {

    @Override
    public Grid generateMaze(Grid grid) {
        Random randomGenerator = new Random();
        for (Cell[] column : grid.getCells()) {
            for (Cell cell : column) {
                ArrayList<Cell> neighbours = new ArrayList<>();
                if (cell.north.isPresent()) {
                    neighbours.add(cell.north.get());
                }
                if (cell.east.isPresent()) {
                    neighbours.add(cell.east.get());
                }
                if (neighbours.size() > 0) {
                    int index = randomGenerator.nextInt(neighbours.size());
                    Cell randomNeighbour = neighbours.get(index);
                    cell.linkBiDirectional(randomNeighbour);
                }
            }
        }
        return grid;
    }
}
