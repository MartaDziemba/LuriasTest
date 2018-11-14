package com.example.marta.luriatest;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.WindowManager;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    @OnClick(R.id.buttonNewTest)
    void OnClickNewTest(){
        Intent intent = new Intent(MainActivity.this, editData.class);
        startActivity(intent);
    }

    @OnClick(R.id.buttonRepeatTest)
    void OnClickRepeatTest(){
        Intent intent = new Intent(MainActivity.this, ListViewMemory.class);
        startActivity(intent);
    }

    @OnClick(R.id.buttonEditData)
    void OnClickEditData(){
        Intent intent = new Intent(MainActivity.this, editExistingData.class);
        startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.intro);
        ButterKnife.bind(this);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }
}