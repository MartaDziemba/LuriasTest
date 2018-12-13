package com.example.marta.luriatest;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    @OnClick(R.id.buttonNewTest)
    void OnClickNewTest(View v){
        String test = "TEST A";
        Intent intent = new Intent(MainActivity.this, EditDataActivity.class);
        intent.putExtra("KEY",test);
        startActivity(intent);
    }

    @OnClick(R.id.buttonRepeatTest)
    void OnClickRepeatTest(View v){
        String test = "TEST B";
        Intent intent = new Intent(MainActivity.this, EditDataActivity.class);
        intent.putExtra("KEY",test);
        startActivity(intent);
    }

    @OnClick(R.id.buttonEditData)
    void OnClickEditData(View v){
        String test = "TEST C";
        Intent intent = new Intent(MainActivity.this, EditDataActivity.class);
        intent.putExtra("KEY",test);
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