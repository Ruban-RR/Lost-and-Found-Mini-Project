package homepagemodule;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.loginmodule.CreateAccountPage;
import com.example.loginmodule.R;

public class MyAccount extends AppCompatActivity {
    private TextView regfieldbox, departmentfieldbox, sectionfieldbox, rollnumfieldbox;
    private SharedPreferences cache;
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
        cache = getSharedPreferences("MyPreferences",MODE_PRIVATE);
        regfieldbox.setText(cache.getString("cacheUserID",""));
        departmentfieldbox.setText(cache.getString("cacheDepartment",""));
        sectionfieldbox.setText(cache.getString("cacheSection",""));
        rollnumfieldbox.setText(cache.getString("cacheRoll",""));


    }


}
