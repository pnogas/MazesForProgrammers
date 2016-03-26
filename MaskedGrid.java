package com.paulnogas.mazesforprogrammers;

/**
 * Created by Paul Nogas on 2016-03-25.
 */
public class MaskedGrid extends ColouredGrid {

    private Mask mask;

    public MaskedGrid(boolean showHeatMap, Mask mask) {
        super(mask.getColumns(), mask.getRows(), showHeatMap);
        this.mask = mask;
        prepareGrid(); //these are already called in Super, but we can't initialize mask member before calling super.
        try {
            configureCells();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        //TODO: refactor!
    }

    @Override
    public void prepareGrid() {
        if (mask != null) {
            for (int x = 0; x < mask.getColumns(); x++) {
                for (int y = 0; y < mask.getRows(); y++) {
                    if (mask.isOn(x, y)) {
                        cells[x][y] = new Cell(x, y);
                    }
                }
            }
        }
    }

    @Override
    public Cell randomCell() {
        int[] xy = mask.randomXYLocation();
        int x = xy[0];
        int y = xy[1];
        return cells[x][y];
    }

    @Override
    public int size() {
        return mask.count();
    }
}
