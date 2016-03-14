package com.paulnogas.mazesforprogrammers;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by Paul Nogas on 2016-03-14
 */
public class SidewinderMazeGenerator implements MazeGenerator {
    @Override
    public NormalGrid generateMaze(NormalGrid grid) {
        Random randomGenerator = new Random();
        ArrayList<Cell> run = new ArrayList<>();
        for (Cell[] row : grid.cells) {
            run.clear();
            for (Cell cell : row) {
                run.add(cell);
                boolean atEasternBoundry = !cell.east.isPresent();
                boolean atNorthernBoundry = !cell.north.isPresent();
                boolean shouldCloseOut = atEasternBoundry || (!atNorthernBoundry && randomGenerator.nextInt(2) != 0);
                if (shouldCloseOut) {
                    Cell member = run.get(randomGenerator.nextInt(run.size()));
                    if (member.north.isPresent()) {
                        member.link(member.north.get(), true);
                    }
                    run.clear();
                } else {
                    if (cell.east.isPresent()) {
                        cell.link(cell.east.get(), true);
                    }
                }
            }
        }
        return grid;
    }
}
