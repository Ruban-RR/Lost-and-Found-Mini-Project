package homepagemodule;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.loginmodule.CreateAccountPage;
import com.example.loginmodule.R;

public class MyAccount extends AppCompatActivity {
    private TextView regfieldbox, departmentfieldbox, sectionfieldbox, rollnumfieldbox;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_account);
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        regfieldbox = findViewById(R.id.regnumber);
        departmentfieldbox = findViewById(R.id.deptfield);
        sectionfieldbox = findViewById(R.id.sectionn);
        rollnumfieldbox = findViewById(R.id.rn);
        CreateAccountPage c = new CreateAccountPage();
        Intent datastorage = getIntent();
        String data1 = datastorage.getStringExtra("registernumber");
        regfieldbox.setText(data1);

    }


}
