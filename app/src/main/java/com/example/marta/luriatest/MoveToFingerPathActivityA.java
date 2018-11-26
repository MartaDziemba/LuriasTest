package com.example.marta.luriatest;

import android.Manifest;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;

public class MoveToFingerPathActivityA extends AppCompatActivity {

    private static final String TAG = "";
    private FingerPathA fingerPath;
    Dialog dialogTutorial;
    Button buttonOk;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_a);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        fingerPath = (FingerPathA) findViewById(R.id.canvas);
        Long date = System.currentTimeMillis();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd-hh-mm");
        String fileName = sdf.format(date) + ".csv";

        File file = new File(Environment.getExternalStorageDirectory().getAbsolutePath(),fileName);
        try {
            file.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        fingerPath.setFile(file);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{
                    Manifest.permission.WRITE_EXTERNAL_STORAGE},1000);
        }

        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);
        myToolbar.inflateMenu(R.menu.dotsmenu);

        openTutorialDialog();
    }

    public void clearCanvas(){
        fingerPath.clearCanvas();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode){
            case 1000:
                if(grantResults[0]==PackageManager.PERMISSION_GRANTED){
                    Toast.makeText(this,"Permission granted!", Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(this, "Permission not granted!", Toast.LENGTH_SHORT).show();
                    finish();
                }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.dotsmenu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        int id = item.getItemId();
        switch (id){
            case R.id.menuButtonClear:
                openAlertDialog();
                break;
            case R.id.menuButtonMore:
                openTutorialDialog();
                break;
        }
        return true;
    }

    private void openAlertDialog() {
        AlertDialog alertDialog = new AlertDialog.Builder(MoveToFingerPathActivityA.this).create();
        alertDialog.setTitle("Warning");
        alertDialog.setMessage("Are you sure you want to clear canvas?");
        alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "Clear",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        clearCanvas();
                    }
                });
        alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "Cancel",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        alertDialog.show();
    }

    private void openTutorialDialog() {
        dialogTutorial = new Dialog(MoveToFingerPathActivityA.this);
        dialogTutorial.setContentView(R.layout.alert_dialog);
        dialogTutorial.setTitle("Help");
        buttonOk = (Button)dialogTutorial.findViewById(R.id.buttonOk);
        buttonOk.setEnabled(true);
        buttonOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(),"Let's get started.", Toast.LENGTH_SHORT).show();
                dialogTutorial.dismiss();
            }
        });
        dialogTutorial.show();
    }
}
