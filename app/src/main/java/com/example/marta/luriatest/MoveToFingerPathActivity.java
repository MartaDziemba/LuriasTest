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
import android.text.format.DateUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;

import butterknife.BindView;

public class MoveToFingerPathActivity extends AppCompatActivity {

    private static final String TAG = "";
    private FingerPathA fingerPathA;
    private FingerPathB fingerPathB;
    private FingerPathC fingerPathC;
    Dialog dialogTutorial;

    @BindView(R.id.buttonOk)
    Button buttonOk;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle datafromButton = getIntent().getExtras();
        String totext = datafromButton.getString("KEY");

        if(totext.equals("TEST A")){
            OnCreateTestA();
        }
        if(totext.equals("TEST B")){
            OnCreateTestB();
        }
        if(totext.equals("TEST C")){
            OnCreateTestC();
        }
    }

    private void OnCreateTestC() {
        setContentView(R.layout.activity_main_c);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        buttonEndOnClick();
        fingerPathC = findViewById(R.id.canvas);

        Long date = System.currentTimeMillis();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd-HH-mm");
        String fileName = sdf.format(date) + ".csv";

        File file = new File(Environment.getExternalStorageDirectory().getAbsolutePath(),fileName);
        try {
            file.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        fingerPathC.setFile(file);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},1000);
        }

        Toolbar myToolbar = findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);
        myToolbar.inflateMenu(R.menu.dotsmenu);

        openTutorialDialogTestC();
    }

    private void OnCreateTestB() {
        setContentView(R.layout.activity_main_b);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        buttonEndOnClick();
        fingerPathB = findViewById(R.id.canvas);

        Long date = System.currentTimeMillis();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd-HH-mm");
        String fileName = sdf.format(date) + ".csv";

        File file = new File(Environment.getExternalStorageDirectory().getAbsolutePath(),fileName);
        try {
            file.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        fingerPathB.setFile(file);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},1000);
        }

        Toolbar myToolbar = findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);
        myToolbar.inflateMenu(R.menu.dotsmenu);

        openTutorialDialogTestB();
    }

    private void OnCreateTestA() {
        setContentView(R.layout.activity_main_a);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        buttonEndOnClick();
        fingerPathA = findViewById(R.id.canvas);

        Long date = System.currentTimeMillis();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd-HH-mm");
        String fileName = sdf.format(date) + ".csv";

        File file = new File(Environment.getExternalStorageDirectory().getAbsolutePath(),fileName);
        try {
            file.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        fingerPathA.setFile(file);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},1000);
        }

        Toolbar myToolbar = findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);
        myToolbar.inflateMenu(R.menu.dotsmenu);

        openTutorialDialogTestA();
    }

    @Override
    public void onBackPressed() {
    }

    private void buttonEndOnClick(){
        Button buttonEnd = findViewById(R.id.buttonEnd);
        buttonEnd.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                openAlertBackDialog();
            }
        });
    }

    public void clearCanvas(){
        Bundle datafromButton = getIntent().getExtras();
        String totext = datafromButton.getString("KEY");

        if(totext.equals("TEST A")){
            fingerPathA.clearCanvas();
        }
        if(totext.equals("TEST B")){
            fingerPathB.clearCanvas();
        }
        if(totext.equals("TEST C")){
            fingerPathC.clearCanvas();
        }
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
                clearTestChoice();
                break;
        }
        return true;
    }

    private void clearTestChoice() {
        Bundle datafromButton = getIntent().getExtras();
        String totext = datafromButton.getString("KEY");

        if(totext.equals("TEST A")){
            openTutorialDialogTestA();
        }
        if(totext.equals("TEST B")){
            openTutorialDialogTestB();
        }
        if(totext.equals("TEST C")){
            openTutorialDialogTestC();
        }
    }

    private void openAlertDialog() {
        AlertDialog alertDialog = new AlertDialog.Builder(MoveToFingerPathActivity.this,android.R.style.Theme_Material_NoActionBar_Fullscreen).create();
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

    private void openTutorialDialogTestA() {
        dialogTutorial = new Dialog(MoveToFingerPathActivity.this,android.R.style.Theme_DeviceDefault_Light_NoActionBar_Fullscreen);
        dialogTutorial.setContentView(R.layout.alert_dialog);
        dialogTutorial.setTitle("Help");
        buttonOk = dialogTutorial.findViewById(R.id.buttonOk);
        buttonOk.setEnabled(true);
        buttonOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogTutorial.dismiss();
            }
        });
        dialogTutorial.show();
    }

    private void openTutorialDialogTestB() {
        dialogTutorial = new Dialog(MoveToFingerPathActivity.this,android.R.style.Theme_DeviceDefault_Light_NoActionBar_Fullscreen);
        dialogTutorial.setContentView(R.layout.tutorial_b);
        dialogTutorial.setTitle("Help");
        buttonOk = (Button)dialogTutorial.findViewById(R.id.buttonOk);
        buttonOk.setEnabled(true);
        buttonOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogTutorial.dismiss();
            }
        });
        dialogTutorial.show();
    }

    private void openTutorialDialogTestC() {
        dialogTutorial = new Dialog(MoveToFingerPathActivity.this, android.R.style.Theme_DeviceDefault_Light_NoActionBar_Fullscreen);
        dialogTutorial.setContentView(R.layout.tutorial_c);
        dialogTutorial.setTitle("Help");
        buttonOk = (Button)dialogTutorial.findViewById(R.id.buttonOk);
        buttonOk.setEnabled(true);
        buttonOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogTutorial.dismiss();
            }
        });
        dialogTutorial.show();
    }

    private void openAlertBackDialog() {
        AlertDialog alertDialog = new AlertDialog.Builder(MoveToFingerPathActivity.this).create();
        alertDialog.setTitle("Warning");
        alertDialog.setMessage("Are you sure you want to finish?");
        alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "Finish",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
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
}
