package com.example.marta.luriatest;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import org.w3c.dom.Text;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    //Button buttonNewTest, buttonRepeatTest, buttonEditData;
    TextView name;

//    @OnClick(R.id.textTime)
//    void OnClicktextTime(View v){
//        TextView textTime = (TextView) v;
//        String buttonText = textTime.getText().toString();
//        Intent intent = new Intent(MainActivity.this, EditDataActivityA.class);
//        intent.putExtra("KEY",buttonText);
//        startActivity(intent);
//    }

//    @OnClick(R.id.name)
//    void OnClicktextTime(View v){
//        Intent intent = new Intent( MainActivity.this, EditDataActivityA.class);
//        Bundle bundle = new Bundle();
//        //String wpisanyTekst = name.getText().toString();
//        String test = "TEST A";
//        bundle.putString("KEY", test);
//        intent.putExtras(bundle);
//        startActivity(intent);
//    }

    @OnClick(R.id.buttonNewTest)
    void OnClickNewTest(View v){
        //Button b = (Button)v;
        //String buttonText = b.getText().toString();
        String test = "TEST A";
        Intent intent = new Intent(MainActivity.this, EditDataActivityA.class);
        intent.putExtra("KEY",test);
        startActivity(intent);
    }

    @OnClick(R.id.buttonRepeatTest)
    void OnClickRepeatTest(View v){
//        Button b = (Button)v;
//        String buttonText = b.getText().toString();
        String test = "TEST B";
        Intent intent = new Intent(MainActivity.this, EditDataActivityA.class);
        intent.putExtra("KEY",test);
        startActivity(intent);
    }

    @OnClick(R.id.buttonEditData)
    void OnClickEditData(View v){
//        Button b = (Button)v;
//        String buttonText = b.getText().toString();
        String test = "TEST C";
        Intent intent = new Intent(MainActivity.this, EditDataActivityA.class);
        intent.putExtra("KEY",test);
        startActivity(intent);
    }

//    @OnClick(R.id.buttonNewTest)
//    void OnClickNewTest(){
//        Intent intent = new Intent(MainActivity.this, EditDataActivityA.class);
//        //String testChoice = "1";
//        //intent.putExtra("TEST_KEY", testChoice);
//        startActivity(intent);
//    }
//
//    @OnClick(R.id.buttonRepeatTest)
//    void OnClickRepeatTest(){
//        Intent intent = new Intent(MainActivity.this, EditDataActivityB.class);
//        //String testChoice = "2";
//        //intent.putExtra("TEST_KEY", testChoice);
//        startActivity(intent);
//    }
//
//    @OnClick(R.id.buttonEditData)
//    void OnClickEditData(){
//        Intent intent = new Intent(MainActivity.this, EditDataActivityC.class);
//        //String testChoice = "3";
//        //intent.putExtra("TEST_KEY", testChoice);
//        startActivity(intent);
//    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.intro);
        ButterKnife.bind(this);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }
}