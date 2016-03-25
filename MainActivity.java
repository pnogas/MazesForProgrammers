package com.paulnogas.mazesforprogrammers;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.support.v4.widget.DrawerLayout;
import android.widget.TextView;

import java.util.Map;


public class MainActivity extends ActionBarActivity
        implements NavigationDrawerFragment.NavigationDrawerCallbacks {

    /**
     * Fragment managing the behaviors, interactions and presentation of the navigation drawer.
     */
    private NavigationDrawerFragment mNavigationDrawerFragment;

    /**
     * Used to store the last screen title. For use in {@link #restoreActionBar()}.
     */
    private CharSequence mTitle;
    private String[] mMazeAlgorithms;
    private NormalGrid mGrid;
    private MazeGenerator[] mMazeGenerators;
    private int columns;
    private int rows;
    private boolean showHeatMap;
    private Context context;
    private int currentAlogirthm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context = this;

        prepareAlgorithms();

        //width = 15;
        //length = 9;

        //mGrid = new Grid(4, 4);
        //MazeDrawView mazeDrawView = (MazeDrawView) findViewById(R.id.maze_canvas);
        //mazeDrawView.setGrid(mGrid);
        //DrawerLayout mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        //ListView mDrawerList = (ListView) findViewById(R.id.left_drawer);

        //mDrawerList.setAdapter(new ArrayAdapter<String>(this, R.layout.drawer_list_item, mMazeAlgorithms));
        //mDrawerList.setOnClickListener(new DrawerItemClickListener());

        mNavigationDrawerFragment = (NavigationDrawerFragment)
                getSupportFragmentManager().findFragmentById(R.id.navigation_drawer);
        mTitle = getTitle();

        // Set up the drawer.
        mNavigationDrawerFragment.setUp(
                R.id.navigation_drawer,
                (DrawerLayout) findViewById(R.id.drawer_layout));
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (currentAlogirthm > 0) {
            displayMaze(currentAlogirthm);
        }

    }

    private void prepareAlgorithms() {
        mMazeAlgorithms = getResources().getStringArray(R.array.maze_algorithms_array);
        mMazeGenerators = new MazeGenerator[mMazeAlgorithms.length];
        mMazeGenerators[0] = new BinaryTreeMazeGenerator();
        mMazeGenerators[1] = new SidewinderMazeGenerator();
        mMazeGenerators[2] = new AldousBroderMazeGenerator();
        mMazeGenerators[3] = new WilsonsGenerator();
        mMazeGenerators[4] = new HuntAndKillGenerator();
    }

    @Override
    public void onNavigationDrawerItemSelected(int position) {
        // update the main content by replacing fragments
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.container, PlaceholderFragment.newInstance(position + 1))
                .commit();
    }

    public void onSectionAttached(int number) {
        if (mMazeAlgorithms == null) {
            prepareAlgorithms();
        }


        /*AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setMessage(String.format("Flick to move black ball\nclick maze algorithm to generate new")).
                setCancelable(false).
                setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        //do nothing
                    }
                });
        AlertDialog alert = builder.create();
        alert.show();*/

        displayMaze(number);
        /*Cell start = maze.cellAt(0, 0);

        Distances distances = start.getDistances();
        Cell newStart = distances.getFurthestCell();

        Distances newDistances = newStart.getDistances();
        Cell newGoal = newDistances.getFurthestCell();

        maze.setDistances(newDistances);*/
        /*String displayString = maze.toString();
        displayString += "\n Solution: \n";

        Distances solution = newDistances.pathTo(maze.cellAt(newGoal.row, newGoal.column));
        maze.setDistances(solution);
        displayString += maze.toString();*/

        /*
        MazeDrawView mazeDrawView = (MazeDrawView) findViewById(R.id.maze_canvas);
        mazeDrawView.setGrid(maze);
        mazeDrawView.clearRoute();
        */


        /*
        TextView tv = (TextView) findViewById(R.id.texty);
        tv.setText(displayString);*/


    }

    private void displayMaze(int number) {
        if (context == null) {
            context = this;
        }
        currentAlogirthm = number;
        mTitle = mMazeAlgorithms[number - 1];
        columns = getMazePrefColumns();
        rows = getMazePrefRows();
        showHeatMap = getHeatMapStatus();
        Grid grid = new ColouredGrid(columns, rows, showHeatMap);
        MazeDrawView mazeDrawView = (MazeDrawView) findViewById(R.id.maze_canvas);
        MazeGeneratorTaskParams taskParams = new MazeGeneratorTaskParams(grid, mMazeGenerators[number - 1], mazeDrawView);
        MazeGeneratorTask task = new MazeGeneratorTask();
        task.doInBackground(taskParams);
    }

    private int getMazePrefColumns() {
        SharedPreferences sharedPreferences = context.getSharedPreferences(SettingsActivity.MAZE_PREFS, MODE_PRIVATE);
        return sharedPreferences.getInt(getResources().getString(R.string.columns_pref_title), getResources().getInteger(R.integer.default_maze_columns));
    }

    private int getMazePrefRows() {
        SharedPreferences sharedPreferences = context.getSharedPreferences(SettingsActivity.MAZE_PREFS, MODE_PRIVATE);
        return sharedPreferences.getInt(getResources().getString(R.string.rows_pref_title), getResources().getInteger(R.integer.default_maze_rows));
    }

    private boolean getHeatMapStatus() {
        SharedPreferences sharedPreferences = context.getSharedPreferences(SettingsActivity.MAZE_PREFS, MODE_PRIVATE);
        return sharedPreferences.getBoolean(getResources().getString(R.string.heat_map_pref_title), false);
    }

    public void restoreActionBar() {
        ActionBar actionBar = getSupportActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setTitle(mTitle);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (!mNavigationDrawerFragment.isDrawerOpen()) {
            // Only show items in the action bar relevant to this screen
            // if the drawer is not showing. Otherwise, let the drawer
            // decide what to show in the action bar.
            getMenuInflater().inflate(R.menu.main, menu);
            restoreActionBar();
            return true;
        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Intent intent = new Intent(context, SettingsActivity.class);
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";

        @Override
        public void onCreate(@Nullable Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setReenterTransition(true);
        }

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */


        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_main, container, false);
            return rootView;
        }

        @Override
        public void onAttach(Activity activity) {
            super.onAttach(activity);
            ((MainActivity) activity).onSectionAttached(
                    getArguments().getInt(ARG_SECTION_NUMBER));
        }
    }

}
