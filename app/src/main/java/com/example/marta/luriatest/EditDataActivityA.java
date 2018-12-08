package com.example.marta.luriatest;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
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
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class EditDataActivityA extends AppCompatActivity {

    EditText editTextFirstName, editTextLastName, editTextAge, editTextIDNumber, editTextDescription;

    @OnClick(R.id.buttonTest)
    void OnClickTest() {
        saveData();
        Intent intent = new Intent(EditDataActivityA.this, MoveToFingerPathActivityA.class);
        startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_data_a);
        ButterKnife.bind(this);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        editTextFirstName = findViewById(R.id.editTextFirstName);
        editTextLastName = findViewById(R.id.editTextLastName);
        editTextAge = findViewById(R.id.editTextAge);
        editTextIDNumber = findViewById(R.id.editTextIDNumber);
        editTextDescription = findViewById(R.id.editTextDescription);

        TextView stringDate = (TextView) findViewById(R.id.stringDate);
        stringDate.setText(DateUtils.formatDateTime(getApplicationContext(), System.currentTimeMillis(), DateUtils.FORMAT_SHOW_TIME | DateUtils.FORMAT_SHOW_YEAR | DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_NUMERIC_DATE | DateUtils.FORMAT_24HOUR));

        findViewById(R.id.allRelativeLayout).setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
                return true;
            }
        });
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
