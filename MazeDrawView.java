package com.paulnogas.mazesforprogrammers;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Parcelable;
import android.support.v4.view.GestureDetectorCompat;
import android.util.Log;
import android.view.GestureDetector;
import android.view.View;
import android.graphics.Bitmap;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.MotionEvent;

import com.google.common.base.Optional;

import java.util.Date;

public class MazeDrawView extends View {

    private static final int SECOND_IN_MS = 1000;
    private static final int MINUTE_IN_MS = SECOND_IN_MS * 60;
    public int width;
    public int height;
    private Bitmap mBitmap;
    private Canvas mCanvas;
    private Path tracePath;
    private Optional<Grid> grid = Optional.absent();
    private Context context;
    private Paint routePainter;
    private Paint wallPainter;
    private Paint colourPainter;
    private Paint playerPainter;
    private Paint finishPainter;
    private PlayerCircle playerCircle;
    private float mX, mY;
    private static final float TOLERANCE = 5;
    private GestureDetector mDetector   ;

    int leftPadding = 10;
    int rightPadding = 10;
    int topPadding = 10;
    int bottomPadding = 10;
    int cellWidth = 10;
    int cellHeight = 10;
    MazeDimensions mazeDimensions = new MazeDimensions(leftPadding, topPadding, rightPadding, bottomPadding, cellWidth, cellHeight);
    private Date startTime;
    private Date completeTime;

    public MazeDrawView(Context c, AttributeSet attrs) {
        super(c, attrs);
        context = c;

        // we set a new Path
        tracePath = new Path();

        // and we set a new Paint with the desired attributes
        routePainter = makeLinePainter();
        routePainter.setColor(Color.RED);
        wallPainter = makeLinePainter();
        wallPainter.setColor(Color.BLACK);
        colourPainter = makeLinePainter();
        colourPainter.setColor(Color.WHITE);
        colourPainter.setStyle(Paint.Style.FILL);
        playerPainter = makeLinePainter();
        playerPainter.setColor(Color.BLACK);
        playerPainter.setStyle(Paint.Style.FILL);
        finishPainter = makeLinePainter();
        finishPainter.setColor(Color.GREEN);
        finishPainter.setStyle(Paint.Style.FILL);

        mDetector = new GestureDetector(context, new mListener());
    }

    public void setGrid(Grid grid) {
        this.grid = Optional.of(grid);
        mazeDimensions = new MazeDimensions(leftPadding, topPadding, rightPadding, bottomPadding, cellWidth, cellHeight);

        if (mCanvas != null) {
            cellWidth = (mCanvas.getWidth() - leftPadding - rightPadding) / grid.getColumns();
            cellHeight = (mCanvas.getHeight() - topPadding - bottomPadding) / grid.getRows();
        }
        mazeDimensions = new MazeDimensions(leftPadding, topPadding, rightPadding, bottomPadding, cellWidth, cellHeight);
        playerCircle = new PlayerCircle(grid.getStartCell(), mazeDimensions, (int) wallPainter.getStrokeWidth());
        invalidate();
        startTime = new Date();
    }


    // override onSizeChanged
    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        // your Canvas will draw onto the defined Bitmap
        mBitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
        mCanvas = new Canvas(mBitmap);
    }

    @Override
    protected void onRestoreInstanceState(Parcelable state) {
        super.onRestoreInstanceState(state);
        setGrid(grid.orNull());
    }

    // override onDraw
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //this.setWillNotDraw(false); //necessary??!!
        drawMaze(canvas);
        //drawPlayerCircle(canvas);
        //canvas.drawPath(tracePath, routePainter);
    }

    private void drawPlayerCircle(Canvas canvas) {

        if (playerCircle != null) {
            canvas.drawCircle(playerCircle.getX(), playerCircle.getY(), playerCircle.getRadius(), playerPainter);
            if (playerCircle.currentCell == grid.get().getFinishCell()) {
                completeTime = new Date();
                long time = completeTime.getTime() - startTime.getTime();
                long minutes = time / MINUTE_IN_MS;
                long seconds = (time - MINUTE_IN_MS * minutes) / SECOND_IN_MS;
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setMessage(String.format("Completed in %02d:%02d", minutes, seconds)).
                        setCancelable(false).
                        setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                if (grid.isPresent()) {
                                    setGrid(grid.get()); //should be new grid?
                                }
                            }
                        });
                AlertDialog alert = builder.create();
                alert.show();

            }
        }

    }

    public void drawMaze(Canvas canvas) {
        if (grid.isPresent()) {
            Grid maze = grid.get();
            cellWidth = (canvas.getWidth() - leftPadding - rightPadding) / maze.getColumns();
            cellHeight = (canvas.getHeight() - topPadding - bottomPadding) / maze.getRows();
            for (int x = 0; x < maze.getColumns(); x++) {
                for (int y = 0; y < maze.getRows(); y++) {
                    int x1 = x * cellWidth + leftPadding;
                    int y1 = y * cellHeight + topPadding;
                    int x2 = x1 + cellWidth;
                    int y2 = y1 + cellHeight;
                    Cell currentCell = maze.cellAt(x, y);
                    colourPainter.setColor(maze.getCellBackgroundColour(currentCell));
                    canvas.drawRect(x1,y1,x2,y2,colourPainter);
                    if (!currentCell.linkedCells.contains(currentCell.north.orNull())) {
                        canvas.drawLine(x1, y1, x2, y1, wallPainter);
                    }
                    if (!currentCell.linkedCells.contains(currentCell.west.orNull())) {
                        canvas.drawLine(x1, y1, x1, y2, wallPainter);
                    }
                    if (!currentCell.linkedCells.contains(currentCell.east.orNull())) {
                        canvas.drawLine(x2, y1, x2, y2, wallPainter);
                    }
                    if (!currentCell.linkedCells.contains(currentCell.south.orNull())) {
                        canvas.drawLine(x1, y2, x2, y2, wallPainter);
                    }
                    if(maze.isFinishCell(currentCell)) {
                        int cx = (x1+x2)/2;
                        int cy = (y1+y2)/2;
                        canvas.drawCircle(cx, cy, Math.min(y2-cy, x2-cx)-wallPainter.getStrokeWidth(), finishPainter);
                    }
                }
            }
            mazeDimensions = new MazeDimensions(leftPadding, topPadding, rightPadding, bottomPadding, cellWidth, cellHeight);
            playerCircle.setMazeDimensions(mazeDimensions);
            drawPlayerCircle(canvas);
        }
    }

    private Paint makeLinePainter() {
        Paint painter = new Paint();
        painter.setAntiAlias(true);
        painter.setStyle(Paint.Style.STROKE);
        painter.setStrokeJoin(Paint.Join.ROUND);
        painter.setStrokeWidth(4f);
        return painter;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return mDetector.onTouchEvent(event);
    }

    class mListener extends GestureDetector.SimpleOnGestureListener {
        @Override
        public boolean onDown(MotionEvent e) {
            return true;
        }

        @Override
        public boolean onFling(MotionEvent event1, MotionEvent event2,
                               float velocityX, float velocityY) {
            Log.d(null,"Fling");
            boolean result = false;
            if (Math.abs(velocityX) > Math.abs(velocityY)) {
                if (velocityX > 0){
                    //right
                    result = playerCircle.tryMoveRight();
                } else {
                    //left
                    result = playerCircle.tryMoveLeft();
                }
            } else {
                if (velocityY > 0) {
                    //down
                    result = playerCircle.tryMoveDown();
                }else {
                    //up
                    result = playerCircle.tryMoveUp();
                }
            }
            if (result) {
                invalidate();
            }
            return true;
        }
    }

    /*@Override
    public boolean onTouchEvent(MotionEvent event){
        this.gestureDetector.onTouchEvent(event);
        // Be sure to call the superclass implementation
        return super.onTouchEvent(event);
    }

    @Override
    public boolean onDown(MotionEvent motionEvent) {
        return true;
    }

    @Override
    public void onShowPress(MotionEvent motionEvent) {

    }

    @Override
    public boolean onSingleTapUp(MotionEvent motionEvent) {
        return false;
    }

    @Override
    public boolean onScroll(MotionEvent motionEvent, MotionEvent motionEvent2, float v, float v2) {
        return true;
    }

    @Override
    public void onLongPress(MotionEvent motionEvent) {
        Log.e("PAUL", "here");

    }

    @Override
    public boolean onFling(MotionEvent motionEvent, MotionEvent motionEvent2, float velocityX, float velocityY) {
        boolean result = false;
        if (velocityX > velocityY) {
            if (velocityX > 0){
                //right
                result = playerCircle.tryMoveRight();
            } else {
                //left
                result = playerCircle.tryMoveLeft();
            }
        } else {
            if (velocityY > 0) {
                //down
                result = playerCircle.tryMoveDown();
            }else {
                //up
                result = playerCircle.tryMoveUp();
            }
        }
        if (result) {
            invalidate();
        }
        return true;
    }*/
}