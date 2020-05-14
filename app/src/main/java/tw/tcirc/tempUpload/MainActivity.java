package tw.tcirc.tempUpload;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private FeverPassAPI feverPassAPI;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        feverPassAPI = new FeverPassAPI(this);
    }

    public void buttonSubmitOnClick(View view) {
        feverPassAPI.submitTemp("710336", "nevikw39", "36");
    }
}
