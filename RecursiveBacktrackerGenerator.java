package com.paulnogas.mazesforprogrammers;

import java.util.HashSet;
import java.util.Set;
import java.util.Stack;

/**
 * Created by Paul Nogas on 2016-03-25.
 */
public class RecursiveBacktrackerGenerator implements MazeGenerator {
    @Override
    public Grid generateMaze(Grid grid) {
        Cell startingCell = grid.randomCell();
        Stack<Cell> stack = new Stack<Cell>();
        stack.push(startingCell);
        while (!stack.isEmpty()) {
            Cell currentCell = stack.lastElement();
            Set<Cell> unvisitedNeighbours = new HashSet<>();
            for (Cell neighbour : currentCell.neighbours()) {
                if (neighbour.linkedCells.isEmpty()){
                   unvisitedNeighbours.add(neighbour);
                }
            }
            if (unvisitedNeighbours.isEmpty()){
                stack.pop();
            } else {
                Cell nextCell = (Cell) Utils.getRandomElementFromSet(unvisitedNeighbours);
                currentCell.linkBiDirectional(nextCell);
                stack.push(nextCell);
            }
        }
        return grid;
    }
}
