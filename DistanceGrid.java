package com.paulnogas.mazesforprogrammers;

/**
 * Created by Paul Nogas on 2016-03-14
 */
public class DistanceGrid extends NormalGrid {

    protected Distances distances;
    protected int maxDistance;

    public DistanceGrid(int columns, int rows) {
        super(columns, rows);
    }

    @Override
    public String contentsOf(Cell cell) {
        if (distances != null && distances.getDistanceToCell(cell) != null) {
            return Integer.toString(distances.getDistanceToCell(cell), Character.MAX_RADIX);
        } else {
            return super.contentsOf(cell);
        }
    }

    public void setDistances(Distances distances) {
        this.distances = distances;
        Cell furthestCell = distances.getFurthestCell();
        maxDistance = distances.getDistanceToCell(furthestCell);
    }

    @Override
    public boolean isStartCell(Cell currentCell) {
        return currentCell != null && currentCell.equals(distances.getRoot());
    }

    @Override
    public Cell getStartCell() {
        return distances.getRoot();
    }

    @Override
    public boolean isFinishCell(Cell currentCell) {
        return currentCell != null && currentCell.equals(distances.getFurthestCell());
    }

    @Override
    public Cell getFinishCell() {
        return distances.getFurthestCell();
    }
}
