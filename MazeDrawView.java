package com.paulnogas.mazesforprogrammers;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;
import android.view.View;
import android.graphics.Bitmap;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.MotionEvent;

import com.google.common.base.Optional;

public class MazeDrawView extends View {

    public int width;
    public int height;
    private Bitmap mBitmap;
    private Canvas mCanvas;
    private Path tracePath;
    private Optional<Grid> grid = Optional.absent();
    Context context;
    private Paint routePainter;
    private Paint wallPainter;
    private Paint colourPainter;
    private Paint startPainter;
    private Paint finishPainter;
    private float mX, mY;
    private static final float TOLERANCE = 5;

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
        startPainter = makeLinePainter();
        startPainter.setColor(Color.BLUE);
        startPainter.setStyle(Paint.Style.FILL);
        finishPainter = makeLinePainter();
        finishPainter.setColor(Color.YELLOW);
        finishPainter.setStyle(Paint.Style.FILL);
    }

    public void setGrid(Grid grid) {
        this.grid = Optional.of(grid);
        invalidate();
    }


    // override onSizeChanged
    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        // your Canvas will draw onto the defined Bitmap
        mBitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
        mCanvas = new Canvas(mBitmap);
    }

    // override onDraw
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //this.setWillNotDraw(false); //necessary??!!
        drawMaze(canvas);
        canvas.drawPath(tracePath, routePainter);
    }

    public void drawMaze(Canvas canvas) {
        if (grid.isPresent()) {
            Grid maze = grid.get();
            int leftPadding = 10;
            int rightPadding = 10;
            int topPadding = 10;
            int bottomPadding = 10;
            int cellWidth = (canvas.getWidth() - leftPadding - rightPadding) / maze.getColumns();
            int cellHeight = (canvas.getHeight() - topPadding - bottomPadding) / maze.getRows();
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
                    if(maze.isStartCell(currentCell)) {
                        int cx = (x1+x2)/2;
                        int cy = (y1+y2)/2;
                        canvas.drawCircle(cx, cy, Math.min(y2-cy, x2-cx)-wallPainter.getStrokeWidth(), startPainter);
                    }
                    if(maze.isFinishCell(currentCell)) {
                        int cx = (x1+x2)/2;
                        int cy = (y1+y2)/2;
                        canvas.drawCircle(cx, cy, Math.min(y2-cy, x2-cx)-wallPainter.getStrokeWidth(), finishPainter);
                    }
                }
            }
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

    // when ACTION_DOWN start touch according to the x,y values
    private void startTouch(float x, float y) {
        tracePath.moveTo(x, y);
        mX = x;
        mY = y;
    }

    // when ACTION_MOVE move touch according to the x,y values
    private void moveTouch(float x, float y) {
        float dx = Math.abs(x - mX);
        float dy = Math.abs(y - mY);
        if (dx >= TOLERANCE || dy >= TOLERANCE) {
            tracePath.quadTo(mX, mY, (x + mX) / 2, (y + mY) / 2);
            mX = x;
            mY = y;
        }
    }

    public void clearRoute() {
        tracePath.reset();
        invalidate();
    }

    // when ACTION_UP stop touch
    private void upTouch() {
        tracePath.lineTo(mX, mY);
    }

    //override the onTouchEvent
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float x = event.getX();
        float y = event.getY();

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                startTouch(x, y);
                invalidate();
                break;
            case MotionEvent.ACTION_MOVE:
                moveTouch(x, y);
                invalidate();
                break;
            case MotionEvent.ACTION_UP:
                upTouch();
                invalidate();
                break;
        }
        return true;
    }
}