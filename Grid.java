package com.paulnogas.mazesforprogrammers;

import java.util.HashMap;

/**
 * Created by Paul Nogas on 2016-03-14.
 */
public interface Grid {
    public Cell cellAt(int x, int y);

    public void setDistances(Distances distances);

    int getColumns();

    int getRows();
}
