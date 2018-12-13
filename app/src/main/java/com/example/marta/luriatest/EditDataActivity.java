package com.example.marta.luriatest;

import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.text.format.DateUtils;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;

import butterknife.BindView;
import butterknife.ButterKnife;

public class EditDataActivity extends AppCompatActivity {

    @BindView(R.id.editTextFirstName)
    EditText editTextFirstName;

    @BindView(R.id.editTextLastName)
    EditText editTextLastName;

    @BindView(R.id.editTextAge)
    EditText editTextAge;

    @BindView(R.id.editTextIDNumber)
    EditText editTextIDNumber;

    @BindView(R.id.editTextDescription)
    EditText editTextDescription;

    @BindView(R.id.buttonTest)
    Button buttonTest;

    @BindView(R.id.stringDate)
    TextView stringDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_data_a);
        ButterKnife.bind(this);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        stringDate.setText(DateUtils.formatDateTime(getApplicationContext(), System.currentTimeMillis(), DateUtils.FORMAT_SHOW_TIME | DateUtils.FORMAT_SHOW_YEAR | DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_NUMERIC_DATE | DateUtils.FORMAT_24HOUR));

        findViewById(R.id.allRelativeLayout).setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
                return true;
            }
        });

        Bundle datafromButton = getIntent().getExtras();
        String totext = datafromButton.getString("KEY");

        checkTestType(totext);
    }

    private void checkTestType(String totext) {
        if(totext.equals("TEST A")) {
            buttonTest.setOnClickListener(new View.OnClickListener(){
                public void onClick(View v){
                    saveData();
                    String test = "TEST A";
                    Intent intent = new Intent(EditDataActivity.this, MoveToFingerPathActivity.class);
                    intent.putExtra("KEY",test);
                    startActivity(intent);
                }
            });
        }
        if(totext.equals("TEST B")) {
            buttonTest.setOnClickListener(new View.OnClickListener(){
                public void onClick(View v){
                    saveData();
                    String test = "TEST B";
                    Intent intent = new Intent(EditDataActivity.this, MoveToFingerPathActivity.class);
                    intent.putExtra("KEY",test);
                    startActivity(intent);
                }
            });
        }
        if(totext.equals("TEST C")) {
            buttonTest.setOnClickListener(new View.OnClickListener(){
                public void onClick(View v){
                    saveData();
                    String test = "TEST C";
                    Intent intent = new Intent(EditDataActivity.this, MoveToFingerPathActivity.class);
                    intent.putExtra("KEY",test);
                    startActivity(intent);
                }
            });
        }
    }

    public void saveData() {
        Long date = System.currentTimeMillis();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd-hh-mm");
        String fileName = sdf.format(date) + ".txt";

        File file = new File(Environment.getExternalStorageDirectory().getAbsolutePath(), fileName);
        try {
            file.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (file.exists() && !file.isDirectory()) {

            try {
                FileOutputStream fos = new FileOutputStream(file);
                fos.write("First name: ".getBytes());
                fos.write(editTextFirstName.getText().toString().getBytes());
                fos.write('\n');
                fos.write("Last name: ".getBytes());
                fos.write(editTextLastName.getText().toString().getBytes());
                fos.write('\n');
                fos.write("Age: ".getBytes());
                fos.write(editTextAge.getText().toString().getBytes());
                fos.write('\n');
                fos.write("ID number: ".getBytes());
                fos.write(editTextIDNumber.getText().toString().getBytes());
                fos.write('\n');
                fos.write("Description: ".getBytes());
                fos.write(editTextDescription.getText().toString().getBytes());
                fos.close();

                Toast.makeText(this, "File saved!", Toast.LENGTH_SHORT).show();
            } catch (FileNotFoundException e) {
                Toast.makeText(this, "Problem: " + e, Toast.LENGTH_SHORT).show();
                e.printStackTrace();
            } catch (IOException e) {
                Toast.makeText(this, "Problem: " + e, Toast.LENGTH_SHORT).show();
                e.printStackTrace();
            }
        } else {
            Toast.makeText(this, "File not saved!", Toast.LENGTH_SHORT).show();
        }
    }
}
