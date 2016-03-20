package com.paulnogas.mazesforprogrammers;

import android.util.Log;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by Paul Nogas on 2016-03-14
 */
public class SidewinderMazeGenerator implements MazeGenerator {
    @Override
    public Grid generateMaze(Grid grid) {
        Random randomGenerator = new Random();
        ArrayList<Cell> run = new ArrayList<>();
        for (Cell[] column : grid.getCells()) {
            run.clear();
            for (Cell cell : column) {
                run.add(cell);
                boolean atEasternBoundry = !cell.east.isPresent();
                boolean atSouthernBoundry = !cell.south.isPresent();
                boolean shouldCloseOut = atSouthernBoundry || (! atEasternBoundry && randomGenerator.nextInt(2) != 0);
                if (shouldCloseOut) {
                    Cell member = run.get(randomGenerator.nextInt(run.size()));
                    if (member.east.isPresent()) {
                        member.link(member.east.get(), true);
                    }
                    run.clear();
                } else {
                    if (cell.south.isPresent()) {
                        cell.link(cell.south.get(), true);
                    }
                }
            }
        }
        return grid;
    }
}
