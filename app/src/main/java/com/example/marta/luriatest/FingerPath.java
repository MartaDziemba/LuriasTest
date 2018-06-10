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
    CSVWriter writer;

    public void saveCanvas(float x, float y){
        if(f.exists() && !f.isDirectory()){
            FileWriter mFileWriter = null;
            try {
                mFileWriter = new FileWriter(filePath, true);
            } catch (IOException e) {
                e.printStackTrace();
            }
            writer = new CSVWriter(mFileWriter);
        }
        else{
            try {
                writer = new CSVWriter(new FileWriter(filePath));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        Float[] data = {x,y};
        for (int i = 0; i < data.length - 2; i++) {
            writer.write(data[i] + ";");
            writer.write(data[i + 1] + ";");
            writer.write(data[i + 2] + ";" + "\n");

        }
        //writer.writeNext(data.toString());
        invalidate();
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


    public void save(float x, float y){

        File path= Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
        File file = new File(path, "AnalysisDataSave.csv");
        FileOutputStream outputStream = null;
        try {
            outputStream = new FileOutputStream(file,true);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        OutputStreamWriter streamWriter = new OutputStreamWriter(outputStream);

        float[] data ={x,y};
        for (int i=0; i<data.length-2; i++){
            try {
                streamWriter.write(data[i]+";");
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                streamWriter.write(data[i+1]+";");
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                streamWriter.write(data[i+2]+";");
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
        try {
            streamWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
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

                saveCanvas(x, y);
                save(x,y);

                saveAsFile(x + "\n" + y);

                invalidate();
                break;
            case MotionEvent.ACTION_MOVE:
                moveTouch(x,y);
                //zapisywanie

                saveCanvas(x, y);
                save(x,y);

                saveAsFile(x + "\n" + y);

                invalidate();
                break;
            case MotionEvent.ACTION_UP:
                upTouch();
                //koniec zapisu

                //writer.close();
                saveCanvas(x, y);

                //saveAsFile("ACTION UP - PRZERWANIE PISANIA.");

                saveAsFile(x + "\n" + y );
                save(x,y);

                invalidate();
                break;
        }
        return true;
    }
}