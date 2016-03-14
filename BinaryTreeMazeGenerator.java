package com.paulnogas.mazesforprogrammers;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by Paul Nogas on 2016-03-11
 */
public class BinaryTreeMazeGenerator implements MazeGenerator {

    public NormalGrid generateMaze(NormalGrid grid) {
        Random randomGenerator = new Random();
        for (Cell[] row : grid.cells) {
            for (Cell cell : row) {
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
                    cell.link(randomNeighbour, true); //is it ever false?
                }
            }
        }
        return grid;
    }
}
