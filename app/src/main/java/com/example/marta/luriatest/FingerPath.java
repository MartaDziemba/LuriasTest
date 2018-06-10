package com.example.marta.luriatest;

import android.Manifest;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.opengl.GLSurfaceView;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.text.SimpleDateFormat;

public class FingerPath extends View{

    public int width;
    public int height;
    private Bitmap mBitmap;
    private Canvas mCanvas;
    private Path mPath;
    private Paint mPaint;
    private float mX, mY;
    private static final float TOLERANCE = 5;
    Context context;
    Button buttonSave;






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

    String baseDir = android.os.Environment.getExternalStorageDirectory().getAbsolutePath();
    String fileName = "AnalysisDataSaveCanvas.csv";
    String filePath = baseDir + File.separator + fileName;
    File f = new File(filePath);
    //CSVWriter writer;

    FileWriter writer;

    private void createCSVfile() {
        File root = Environment.getExternalStorageDirectory();
        File file = new File(root, "NazwaPliku.csv");
        try {
            writer = new FileWriter(file, true);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void writeToCsvFile(String string) {
        try {
            writer.write(string);
            writer.flush();
        } catch (IOException error) {
            Log.d("writetoCSV", error.getMessage());
        }
    }

    private void saveAsFile(String content){
        String fileName = "AnalysisData.csv";
        File file = new File(Environment.getExternalStorageDirectory().getAbsolutePath(),fileName);

        if(file.exists() && !file.isDirectory()){
            try {
                //FileOutputStream fos = new FileOutputStream(file);
                FileWriter writer = new FileWriter(file);
                writer.append(content);
                writer.append(System.getProperty("line.separator"));
                writer.append(System.getProperty("line.separator") + content);
                writer.flush();
                writer.close();

//                fos.write(content.getBytes());
//                fos.write('\n');
//                fos.write(',');
//
//                fos.flush();
//                fos.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e){
                e.printStackTrace();
            }
        }
        else{
            try {
                FileOutputStream fos = new FileOutputStream(file);
                //writer = new CSVWriter(new FileWriter(filePath));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        float x = event.getX();
        float y = event.getY();

        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                startTouch(x,y);
                //otwarcie pliku

                saveAsFile(x + "\n" + y + ";");
                //writeToCsvFile(x + "\n" + y + ";");
                invalidate();
                break;
            case MotionEvent.ACTION_MOVE:
                moveTouch(x,y);
                //zapisywanie

                saveAsFile(x + "\n" + y + ";");
                //writeToCsvFile(x + "\n" + y + ";");
                invalidate();
                break;
            case MotionEvent.ACTION_UP:
                upTouch();
                //koniec zapisu

                //saveAsFile("ACTION UP - PRZERWANIE PISANIA.");
                saveAsFile(x + "\n" + y  + ";");
                //writeToCsvFile(x + "\n" + y + ";");
                invalidate();
                break;
        }
        return true;
    }
}