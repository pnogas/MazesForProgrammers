package com.paulnogas.mazesforprogrammers;

import java.util.HashSet;

/**
 * Created by Paul Nogas on 2016-03-25.
 */
public class HuntAndKillGenerator implements MazeGenerator {
    @Override
    public Grid generateMaze(Grid grid) {
        Cell startingCell = grid.randomCell();
        Cell currentCell = startingCell;
        while (currentCell != null) {
            HashSet<Cell> unvisitedNeighbours = getUnvistedNeighbours(currentCell);

            if (unvisitedNeighbours.isEmpty()) {
                currentCell = scanGridForNewPathStart(grid);
            } else {
                Cell chosenNeighbour = (Cell) Utils.getRandomElementFromSet(unvisitedNeighbours);
                currentCell.linkBiDirectional(chosenNeighbour);
                currentCell = chosenNeighbour;
            }
        }
        return grid;
    }

    private Cell scanGridForNewPathStart(Grid grid) {
        Cell newPathStart;
        for (int x = 0; x < grid.getColumns(); x++) {
            for (int y = 0; y < grid.getRows(); y++) {
                Cell cell = grid.cellAt(x,y);
                newPathStart = returnCellIfValidForNewPathStart(cell);
                if (newPathStart != null) {
                    return newPathStart;
                }
            }
        }
        return null;
    }

    private Cell returnCellIfValidForNewPathStart(Cell candidateCell) {
        HashSet<Cell> visitedNeighbours = new HashSet<>();
        for (Cell neighbour : candidateCell.neighbours()) {
            if (isPartOfExistingPath(neighbour)) {
                visitedNeighbours.add(neighbour);
            }
            if (!isPartOfExistingPath(candidateCell) && !visitedNeighbours.isEmpty()) {
                neighbour = (Cell) Utils.getRandomElementFromSet(visitedNeighbours);
                candidateCell.linkBiDirectional(neighbour);
                return candidateCell;
            }

        }
        return null;
    }

    private boolean isPartOfExistingPath(Cell cell) {
        return !cell.linkedCells.isEmpty();
    }

    private HashSet<Cell> getUnvistedNeighbours(Cell cell) {
        HashSet<Cell> result = new HashSet<>();
        for (Cell neighbour : cell.neighbours()) {
            if (neighbour.linkedCells.isEmpty()) {
                result.add(neighbour);
            }
        }
        return result;
    }
}
