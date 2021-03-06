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
import android.os.Handler;
import android.os.SystemClock;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toolbar;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.RoundingMode;
import java.nio.charset.Charset;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.ButterKnife;

public class FingerPathB extends View{

    private Bitmap mBitmap;
    private Canvas mCanvas;
    private Path mPath;
    private Paint mPaint;
    private float mX, mY;
    private static final float TOLERANCE = 5;
    Context context;
    private File file;
    long timeInMillis=0, startTime = SystemClock.uptimeMillis();

    public FingerPathB(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.context=context;
        mPath = new Path();
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setColor(Color.BLACK);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeJoin(Paint.Join.ROUND);
        mPaint.setStrokeWidth(4f);
    }

    private void createSample(Canvas canvas) {
        canvas.drawLine(10, 350, 10, 300, mPaint);
        canvas.drawLine(10, 300, 50, 300, mPaint);
        canvas.drawLine(50, 300, 50, 350, mPaint);

        canvas.drawLine(50, 350, 75, 300, mPaint);
        canvas.drawLine(75, 300, 100, 350, mPaint);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mBitmap = Bitmap.createBitmap(w,h,Bitmap.Config.ARGB_8888);
        mCanvas = new Canvas(mBitmap);
        saveAsFile("widthX=" + mBitmap.getWidth() + " heightY=" + mBitmap.getHeight() + " radius=652.799988\n");
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        createSample(canvas);
        canvas.drawPath(mPath, mPaint);
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
        saveAsFile(".COMMENT CLEAR");
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
                writer.append(' ');
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
                Log.wtf("FingerPathB","Error saving as file: " + e);
                e.printStackTrace();
            }
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        float x = event.getX();
        float y = event.getY();
        int valueX = (int) Math.rint(x*10000);
        int valueY = (int) Math.rint(y*10000);

        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                startTouch(x,y);
                //otwarcie pliku
                saveAsFile(".PEN_DOWN");
                timeInMillis = SystemClock.uptimeMillis() - startTime;
                saveAsFile(valueX + " " + valueY + " " + String.format("%3d",timeInMillis));
                invalidate();
                break;
            case MotionEvent.ACTION_MOVE:
                moveTouch(x,y);
                //zapisywanie
                timeInMillis = SystemClock.uptimeMillis() - startTime;
                saveAsFile(valueX + " " + valueY + " " + String.format("%3d",timeInMillis));
                invalidate();
                break;
            case MotionEvent.ACTION_UP:
                upTouch();
                //koniec zapisu
                saveAsFile(".PEN_UP" + '\n');
                invalidate();
                break;
        }
        return true;
    }

    public void setFile(File file) {
        this.file = file;
        saveAsFile(".VERSION 1.0 0\n" +
                ".COORD X Y T\n" +
                ".HIERARCHY CHARACTER\n" +
                ".DATA_SOURCE minicog 20160802_105856\n" +
                ".X_POINTS_PER_INCH 300000\n" +
                ".Y_POINTS_PER_INCH 300000\n" +
                ".COMMENT\n");
    }
}