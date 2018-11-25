package com.example.marta.luriatest;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class EditDataActivityA extends AppCompatActivity implements TextWatcher {

    EditText editTextFirstName, editTextLastName, editTextAge, editTextIDNumber, editTextDescription;

    @OnClick(R.id.buttonTest)
    void OnClickTest() {
        //saveData();
        Intent intent = new Intent(EditDataActivityA.this, MoveToFingerPathActivityA.class);
        startActivity(intent);
    }

    @OnClick(R.id.buttonSave)
    void OnClickSave() {
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

        //Tutorial 1
        editTextFirstName.addTextChangedListener(this);
        editTextLastName.addTextChangedListener(this);
        editTextAge.addTextChangedListener(this);
        editTextIDNumber.addTextChangedListener(this);
        editTextDescription.addTextChangedListener(this);

        //Tutorial 2
        editTextFirstName.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                String editTextFirstNameString = editTextFirstName.getText().toString();
                if(editTextFirstNameString==""){
                   editTextFirstName.setError("Pole nie moze byc puste!");
                 }
            }
        });

        editTextLastName.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                String editTextLastNameString = editTextLastName.getText().toString();
                if(editTextLastNameString==""){
                    editTextLastName.setError("Pole nie moze byc puste!");
                }
            }
        });

        editTextAge.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                String editTextAgeString = editTextAge.getText().toString();
                if(editTextAgeString==""){
                    editTextAge.setError("Pole nie moze byc puste!");
                }
            }
        });

        editTextIDNumber.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                String editTextIDNumberString = editTextIDNumber.getText().toString();
                if(editTextIDNumberString==""){
                    editTextIDNumber.setError("Pole nie moze byc puste!");
                }
            }
        });

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
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy-hh-mm");
        String fileName = sdf.format(date) + ".txt";
        //String fileName = "DaneOsobowe.txt";

        File file = new File(Environment.getExternalStorageDirectory().getAbsolutePath(), fileName);
        try {
            file.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (file.exists() && !file.isDirectory()) {

            try {
                FileOutputStream fos = new FileOutputStream(file);
                fos.write(editTextFirstName.getText().toString().getBytes());
                fos.write('\n');
                fos.write(editTextLastName.getText().toString().getBytes());
                fos.write('\n');
                fos.write(editTextAge.getText().toString().getBytes());
                fos.write('\n');
                fos.write(editTextIDNumber.getText().toString().getBytes());
                fos.write('\n');
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

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @SuppressLint("ShowToast")
    @Override
    public void afterTextChanged(Editable editable) {
        String editTextFirstNameString = editTextFirstName.getText().toString();
        if(editTextFirstNameString==""){
            Toast.makeText(this,"Wpisz imie.",Toast.LENGTH_SHORT);
        }
        String editTextLastNameString = editTextLastName.getText().toString();
        if(editTextLastNameString==""){
            Toast.makeText(this,"Wpisz imie.",Toast.LENGTH_SHORT);
        }
        String editTextAgeString = editTextAge.getText().toString();
        if(editTextAgeString==""){
            Toast.makeText(this,"Wpisz imie.",Toast.LENGTH_SHORT);
        }
        String editTextIDNumberString = editTextIDNumber.getText().toString();
        if(editTextIDNumberString==""){
            Toast.makeText(this,"Wpisz imie.",Toast.LENGTH_SHORT);
        }
    }
}
