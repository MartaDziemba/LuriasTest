package com.example.marta.luriatest;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import java.text.SimpleDateFormat;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class editData extends AppCompatActivity {

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
    }

}
