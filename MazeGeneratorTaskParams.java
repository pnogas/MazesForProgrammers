package com.paulnogas.mazesforprogrammers;

/**
 * Created by Paul Nogas on 2016-03-20.
 */
public class MazeGeneratorTaskParams {
    private Grid grid;
    private MazeGenerator mazeGenerator;
    private MazeDrawView mazeDrawView;

    public MazeGeneratorTaskParams(Grid grid, MazeGenerator mazeGenerator, MazeDrawView mazeDrawView) {
        this.grid = grid;
        this.mazeGenerator = mazeGenerator;
        this.mazeDrawView = mazeDrawView;
    }

    public Grid getGrid() {
        return grid;
    }

    public MazeGenerator getMazeGenerator() {
        return mazeGenerator;
    }

    public MazeDrawView getMazeDrawView() {
        return mazeDrawView;
    }
}
