import android.Manifest;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.marta.luriatest.R;

import java.io.File;
import java.util.ArrayList;

import butterknife.ButterKnife;

public class listViewMemory extends AppCompatActivity {

    private static final String TAG = "listViewMemory";

    private String[] filePathStrings;
    private String[] fileNameStrings;
    private File[] listFile;
    File file;

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
        uploadData = new ArrayList<>();

        checkFilePermissions();
        listViewInternalStorage.setOnItemClickListener(new AdapterView.OnItemClickListener(){

            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

            }
        });
    }

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
    private void toastMessage(String message){
        Toast.makeText(this, message,Toast.LENGTH_SHORT).show();
    }
}
