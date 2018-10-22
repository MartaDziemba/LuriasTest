package com.example.marta.luriatest;

import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class editData extends AppCompatActivity {

    EditText editTextFirstName, editTextLastName, editTextAge, editTextIDNumber, editTextDescription;

    @OnClick(R.id.buttonTest)
    void OnClickEditData(){

        Intent intent = new Intent(editData.this, moveToFingerPath.class);
        startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.editdata);
        ButterKnife.bind(this);

        editTextFirstName = findViewById(R.id.editTextFirstName);
        editTextLastName = findViewById(R.id.editTextLastName);
        editTextAge = findViewById(R.id.editTextAge);
        editTextIDNumber = findViewById(R.id.editTextIDNumber);
        editTextDescription = findViewById(R.id.editTextDescription);
    }

    public void saveData(View v){
        //Long date = System.currentTimeMillis();
        //SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy-hh-mm-ss");
        //String fileName = sdf.format(date) + ".txt";
        String fileName = "DaneOsobowe.txt";

        File file = new File(Environment.getExternalStorageDirectory().getAbsolutePath(),fileName);

        if(file.exists() && !file.isDirectory()){

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

                //Toast.makeText(this, "File saved!", Toast.LENGTH_SHORT).show();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e){
                e.printStackTrace();
            }
        }
        else{
            //Toast.makeText(this, "File not saved!", Toast.LENGTH_SHORT).show();
        }
    }
}
