package com.example.sudokusolver;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.Nullable;

import java.util.ArrayList;

public class Board extends View {
    private final int boardColor;
    private final int letterColorSolve;
    private final int letterColor;
    private final int cellHighlightCollor;
    private final Paint boardColorPaint = new Paint();
    private final Paint cellHighlightCollorPaint = new Paint();
    private int cellSize;
    private final Solver solver = new Solver();
    private final Paint letterPaint = new Paint();
    private final Rect letterPaintBounds = new Rect();

    public Board(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        TypedArray ta = context.getTheme().obtainStyledAttributes(attrs, R.styleable.Board,0,0);

        try{
            boardColor = ta.getInteger(R.styleable.Board_boardColor,0);
            letterColorSolve = ta.getInteger(R.styleable.Board_letterColorSolve,0);
            letterColor = ta.getInteger(R.styleable.Board_letterColor,0);
            cellHighlightCollor = ta.getInteger(R.styleable.Board_cellHighlightCollor,0);
        }finally{
            ta.recycle();
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouchEvent(MotionEvent event){
        boolean isValid;

        float x=event.getX();
        float y=event.getY();

        int action = event.getAction();

        if(action == MotionEvent.ACTION_DOWN){
            solver.setSelected_row((int)Math.ceil(y/cellSize));
            solver.setSelected_column((int)Math.ceil(x/cellSize));
            isValid = true;
        }
        else {
            isValid = false;
        }


        return isValid;
    }

    @Override
    protected void onMeasure(int width, int height){
        super.onMeasure(width, height);

        int dimension=Math.min(this.getMeasuredWidth(),this.getMeasuredHeight());
        cellSize=dimension/9;

        setMeasuredDimension(dimension, dimension);
    }

    @Override
    protected void onDraw(Canvas canvas){
        boardColorPaint.setStyle(Paint.Style.STROKE);
        boardColorPaint.setStrokeWidth(16);
        boardColorPaint.setColor(boardColor);
        boardColorPaint.setAntiAlias(true);

        letterPaint.setStyle(Paint.Style.FILL);
        letterPaint.setAntiAlias(true);
        letterPaint.setColor(letterColor);

        cellHighlightCollorPaint.setStyle(Paint.Style.FILL);
        cellHighlightCollorPaint.setAntiAlias(true);
        cellHighlightCollorPaint.setColor(cellHighlightCollor);

        highlightCell(canvas, solver.getSelected_row(), solver.getSelected_column());

        canvas.drawRect(0,0, getWidth(), getHeight(), boardColorPaint);

        drawBoard(canvas);
        drawNumbers(canvas);
    }
    private void highlightCell(Canvas canvas, int r, int c){
        if(solver.getSelected_column() != -1 && solver.getSelected_row() != -1){
            canvas.drawRect((c-1)*cellSize, (r-1)*cellSize, c*cellSize, r*cellSize, cellHighlightCollorPaint);
        }
        invalidate();

    }

    private void drawNumbers(Canvas canvas){
        letterPaint.setTextSize(cellSize);
        for(int r=0; r<9; r++){
            for(int c=0; c<9; c++){
                if(solver.getBoard()[r][c] != 0){
                    drawNumberSub(canvas, r, c);
                }
            }
        }

        letterPaint.setColor(letterColorSolve);
        for(ArrayList<Object> letter : solver.getEmptyBoxIndex()){
            int r = (int)letter.get(0);
            int c = (int)letter.get(1);

            drawNumberSub(canvas, r, c);
        }
    }

    private void drawNumberSub(Canvas canvas, int r, int c){
        String text = Integer.toString(solver.getBoard()[r][c]);
        float width, height;

        letterPaint.getTextBounds(text, 0, text.length(), letterPaintBounds);
        width = letterPaint.measureText(text);
        height = letterPaintBounds.height();

        canvas.drawText(text, (c*cellSize) + ((cellSize-width)/2), (r*cellSize+cellSize) - ((cellSize-height)/2), letterPaint);
    }

    private void drawThickLine(){
        boardColorPaint.setStyle(Paint.Style.STROKE);
        boardColorPaint.setStrokeWidth(10);
        boardColorPaint.setColor(boardColor);
    }

    private void drawThinLine(){
        boardColorPaint.setStyle(Paint.Style.STROKE);
        boardColorPaint.setStrokeWidth(4);
        boardColorPaint.setColor(boardColor);
    }

    private void drawBoard(Canvas canvas){
        for(int c=0;c<9;c++){
            if(c%3==0){
                drawThickLine();
            }else{
                drawThinLine();
            }
            canvas.drawLine(cellSize * c,0, cellSize * c, getWidth(), boardColorPaint);
        }
        for(int r=0;r<9;r++){
            if(r%3==0){
                drawThickLine();
            }else{
                drawThinLine();
            }
            canvas.drawLine(0, cellSize * r, getWidth(), cellSize * r, boardColorPaint);
        }
    }

    public Solver getSolver(){
        return solver;
    }
}
