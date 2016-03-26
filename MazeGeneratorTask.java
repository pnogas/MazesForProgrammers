package com.paulnogas.mazesforprogrammers;

import android.os.AsyncTask;

import java.net.URL;

/**
 * Created by Paul Nogas on 2016-03-20.
 */
public class MazeGeneratorTask extends AsyncTask<MazeGeneratorTaskParams, Integer, Grid> {

    MazeDrawView mazeDrawView;

    @Override
    protected Grid doInBackground(MazeGeneratorTaskParams... mazeGeneratorTaskParamses) {
        mazeDrawView = mazeGeneratorTaskParamses[0].getMazeDrawView();
        Grid grid = mazeGeneratorTaskParamses[0].getGrid();
        MazeGenerator mazeGenerator = mazeGeneratorTaskParamses[0].getMazeGenerator();
        Grid completedMaze = null;
        try {
            completedMaze = mazeGenerator.generateMaze(grid);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        Cell start = completedMaze.randomCell();

        Distances distances = start.getDistances();
        Cell newStart = distances.getFurthestCell();
        Distances newDistances = newStart.getDistances();

        completedMaze.setDistances(newDistances);

        if (mazeDrawView != null) {
            mazeDrawView.setGrid(completedMaze);
        }
        //mazeDrawView.clearRoute();
        return completedMaze;
    }

    @Override
    protected void onPostExecute(Grid completedMaze) {
        super.onPostExecute(completedMaze);
        mazeDrawView.setGrid(completedMaze); //why isn't this called?
        //mazeDrawView.clearRoute();
    }
}
