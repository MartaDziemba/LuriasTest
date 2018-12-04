package com.example.marta.luriatest;

import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
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

public class EditDataActivityC extends AppCompatActivity {

    EditText editTextFirstName, editTextLastName, editTextAge, editTextIDNumber, editTextDescription;

    @OnClick(R.id.buttonTest)
    void OnClickEditData() {
        saveData();
        Intent intent = new Intent(EditDataActivityC.this, MoveToFingerPathActivityC.class);
        startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_data_c);
        ButterKnife.bind(this);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        editTextFirstName = findViewById(R.id.editTextFirstName);
        editTextLastName = findViewById(R.id.editTextLastName);
        editTextAge = findViewById(R.id.editTextAge);
        editTextIDNumber = findViewById(R.id.editTextIDNumber);
        editTextDescription = findViewById(R.id.editTextDescription);

        findViewById(R.id.allRelativeLayout).setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
                return true;
            }
        });

//        buttonSave = (Button) findViewById(R.id.buttonSave);
//        buttonSave.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Pattern psFN = Pattern.compile("^[a-zA-Z ąćęłńóśźżĄĆĘŁŃÓŚŹŻ]+");
//                Matcher msFN = psFN.matcher(editTextFirstName.getText().toString());
//                boolean bsFN = msFN.matches();
//
//                Pattern psLN = Pattern.compile("^[a-zA-Z ąćęłńóśźżĄĆĘŁŃÓŚŹŻ]+");
//                Matcher msLN = psLN.matcher(editTextLastName.getText().toString());
//                boolean bsLN = msLN.matches();
//
//                if(editTextFirstName.length() == 0 & editTextFirstName.length() == 0 & editTextLastName.length()==0 & editTextAge.length()==0 & editTextIDNumber.length()==0){
//                    editTextFirstName.setError("Enter your name!");
//                    editTextFirstName.setError("Enter your name!");
//                    editTextLastName.setError("Enter your last name!");
//                    editTextAge.setError("Enter your age!");
//                    editTextIDNumber.setError("Enter your ID number!");
//                }
//                else if(editTextFirstName.length() == 0){
//                    editTextFirstName.setError("Enter your name!");
//                }
//                else if(!bsFN) {
//                    editTextFirstName.setError("Your name should consist of letters, not numbers!");
//                }
//                else if(editTextLastName.length()==0){
//                    editTextLastName.setError("Enter your last name!");
//                }
//                else if(!bsLN) {
//                    editTextLastName.setError("Your name should consist of letters, not numbers!");
//                }
//                else if(editTextAge.length()==0){
//                    editTextAge.setError("Enter your age!");
//                }
//                else if(editTextIDNumber.length()==0){
//                    editTextIDNumber.setError("Enter your ID number!");
//                }
//                else {
//                    saveData();
//                    Intent intent = new Intent(EditDataActivityC.this, MoveToFingerPathActivityC.class);
//                    startActivity(intent);
//                }
//            }
//        });
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
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            Toast.makeText(this, "File not saved!", Toast.LENGTH_SHORT).show();
        }
    }
}
