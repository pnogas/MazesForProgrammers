package com.paulnogas.mazesforprogrammers;

/**
 * Created by Paul Nogas on 2016-03-25.
 */
public class KillingCells {

    private Mask mask;

    public KillingCells() {
        mask = new Mask(5,5);
        mask.set(0, 0, false);
        mask.set(2,2, false);
        mask.set(4,4, false);
    }

    public Grid makeGrid(boolean showHeatMap){
        Grid grid = new MaskedGrid(showHeatMap, mask);
        return grid;
    }

}
