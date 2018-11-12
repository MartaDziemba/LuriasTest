package com.example.marta.luriatest;

import android.app.ActionBar;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toolbar;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import butterknife.ButterKnife;

public class FingerPath extends View{

    private Bitmap mBitmap;
    private Canvas mCanvas;
    private Path mPath;
    private Paint mPaint;
    private float mX, mY;
    private static final float TOLERANCE = 5;
    Context context;
    long startTime = System.currentTimeMillis();
    private File file;

    public FingerPath(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.context=context;
        mPath = new Path();
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setColor(Color.BLACK);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeJoin(Paint.Join.ROUND);
        mPaint.setStrokeWidth(4f);
        //this.setBackground(getResources().getDrawable(R.drawable.background));
        //createSample(mCanvas);
        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);
    }

    private void createSample(Canvas canvas) {
//        canvas.drawLine(10, 250, 100, 250, mPaint);
//        canvas.drawLine(100, 250, 100, 150, mPaint);
//        canvas.drawLine(100, 150, 200, 150, mPaint);
//        canvas.drawLine(200, 150, 200, 250, mPaint);
//
//        canvas.drawLine(200, 250, 300, 250, mPaint);
//        canvas.drawLine(300, 250, 350, 150, mPaint);
//        canvas.drawLine(350, 150, 400, 250, mPaint);
//        canvas.drawLine(400, 250, 500, 250, mPaint);


        canvas.drawLine(10, 400, 60, 400, mPaint);
        canvas.drawLine(60, 400, 60, 340, mPaint);
        canvas.drawLine(60, 340, 120, 340, mPaint);
        canvas.drawLine(120, 340, 120, 400, mPaint);

        canvas.drawLine(120, 400, 180, 400, mPaint);
        canvas.drawLine(180, 400, 210, 340, mPaint);
        canvas.drawLine(210, 340, 240, 400, mPaint);
        canvas.drawLine(240, 400, 300, 400, mPaint);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        mBitmap = Bitmap.createBitmap(w,h,Bitmap.Config.ARGB_8888);
        mCanvas = new Canvas(mBitmap);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.drawPath(mPath, mPaint);
        createSample(canvas);

        canvas.drawCircle(600, 200, 1, mPaint);
    }

    private void startTouch(float x, float y){
        mPath.moveTo(x,y);
        mX=x;
        mY=y;
    }

    private void moveTouch(float x, float y){
        float dx = Math.abs(x-mX);
        float dy = Math.abs(y-mY);
        if(dx>=TOLERANCE || dy>=TOLERANCE){
            mPath.quadTo(mX, mY, (x+mX)/2, (y+mY)/2);
            mX=x;
            mY=y;
        }
    }

    public void clearCanvas(){
        mPath.reset();
        invalidate();
    }

    private void upTouch(){
        mPath.lineTo(mX,mY);
    }

    private void saveAsFile(String content){
        if(file.exists() && !file.isDirectory()){
            try {
                FileWriter writer = new FileWriter(file,true);
                writer.append(content);
                writer.append(',');
                writer.append('\n');
                writer.flush();
                writer.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e){
                e.printStackTrace();
            }
        }
        else{
            try {
                FileOutputStream fos = new FileOutputStream(file);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        float x = event.getX();
        float y = event.getY();
        long millisStart = System.currentTimeMillis() - startTime;
        int secondsStart = (int) (millisStart / 1000);

        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                startTouch(x,y);
                //otwarcie pliku
                saveAsFile(x + "," + y + "," + String.format("%d:%02d", secondsStart, millisStart));
                invalidate();
                break;
            case MotionEvent.ACTION_MOVE:
                moveTouch(x,y);
                //zapisywanie
                long millis = System.currentTimeMillis() - startTime;
                int seconds = (int) (millis / 1000);
                saveAsFile(x + "," + y + "," + String.format("%d:%02d", seconds, millis));
                invalidate();
                break;
            case MotionEvent.ACTION_UP:
                upTouch();
                //koniec zapisu
                saveAsFile("ACTION UP - PRZERWANIE PISANIA.");
                invalidate();
                break;
        }
        return true;
    }

    public void setFile(File file) {
        this.file = file;
    }
}