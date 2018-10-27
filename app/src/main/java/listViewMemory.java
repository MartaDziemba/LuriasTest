import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.example.marta.luriatest.R;

import butterknife.ButterKnife;

public class listViewMemory extends AppCompatActivity {
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_existing_data);
        ButterKnife.bind(this);
    }
}
