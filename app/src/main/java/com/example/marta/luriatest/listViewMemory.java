package com.example.marta.luriatest;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.example.marta.luriatest.R;

import org.apache.poi.ss.usermodel.FormulaEvaluator;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import butterknife.ButterKnife;

public class listViewMemory extends AppCompatActivity {

    private static final String TAG = "listViewMemory";

    private String[] FilePathStrings;
    private String[] FileNameStrings;
    private File[] listFile;
    File file;

    Button buttonUpDirectiry, buttonSDCard;

    ArrayList<String> pathHistory;
    String lastDirectory;
    int count = 0;

    ArrayList<XYValue> uploadData;
    ListView listViewInternalStorage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.item);
        //ButterKnife.bind(this);
        listViewInternalStorage = (ListView) findViewById(R.id.listViewInternalStorage);
        buttonUpDirectiry = (Button) findViewById(R.id.buttonBack);
        buttonSDCard = (Button) findViewById(R.id.buttonMemory);
        uploadData = new ArrayList<>();

        //checkFilePermissions();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{
                    Manifest.permission.WRITE_EXTERNAL_STORAGE},1000);
        }

        listViewInternalStorage.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                lastDirectory = pathHistory.get(count);
                if(lastDirectory.equals(adapterView.getItemAtPosition(i))){
                    Log.d(TAG, "listViewInternalStorage: Selected a file for upload: " + lastDirectory);
                    readExcelData(lastDirectory);
                }
                else{
                    count++;
                    pathHistory.add(count, (String) adapterView.getItemAtPosition(i));
                    checkInternalStorage();
                    Log.d(TAG,  "listViewInternalStorage: "+pathHistory.get(count));
                }
            }
        });
    }

    private void readExcelData(String filePath) {
        Log.d(TAG, "readExcelData: Reading Excel File.");
        File inputFile = new File(filePath);
        try {
            InputStream inputStream = new FileInputStream(inputFile);
            XSSFWorkbook workbook = new XSSFWorkbook(inputStream);
            XSSFSheet sheet = workbook.getSheetAt(0);
            int rowCount = sheet.getPhysicalNumberOfRows();
            FormulaEvaluator formulaEvaluator = workbook.getCreationHelper().createFormulaEvaluator();

        }catch (FileNotFoundException e){
            Log.d(TAG, "readExcelData: FileNotFoundException. " + e.getMessage());
        }catch (IOException e){
            Log.d(TAG, "readExcelData: Error reading inputstream. " + e.getMessage());
        }
    }

    private void checkInternalStorage() {
        Log.d(TAG, "checkInternalStorage: Started.");
        try{
            if(!Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)){
                toastMessage("No SD card found.");
            }
            else {
                file = new File(pathHistory.get(count));
                Log.d(TAG, "checkInternalStorage: directory path: " + pathHistory.get(count));
            }
            listFile = file.listFiles();
            FilePathStrings = new String[listFile.length];
            FileNameStrings = new String[listFile.length];

            for(int i=0; i<listFile.length; i++){
                FilePathStrings[i] = listFile[i].getAbsolutePath();
                FileNameStrings[i] = listFile[i].getName();
            }

            for(int i=0; i<listFile.length; i++){
                Log.d(TAG, "FileName: " + listFile[i].getName());
            }

            ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, FilePathStrings);
            listViewInternalStorage.setAdapter(adapter);

        }catch(NullPointerException e){
            Log.e(TAG, "checkInternalStorage: NullPointerException " + e.getMessage());
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
    /*
    private void checkFilePermissions() {
        if(Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP){
            int permissionCheck = this.checkSelfPermission("Manifest.permission.READ_EXTERNAL_STORAGE");
            permissionCheck += this.checkSelfPermission("Manifest.permission.WRITE_EXTERNAL_STORAGE");
            if (permissionCheck != 0){
                this.requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.READ_EXTERNAL_STORAGE},1001);
            }
        }else{
            Log.d(TAG, "checkBTPermissions: No need to check permissions. SDK version < LOLLIPOP.");
        }
    }
    */
    private void toastMessage(String message){
        Toast.makeText(this, message,Toast.LENGTH_SHORT).show();
    }

}
