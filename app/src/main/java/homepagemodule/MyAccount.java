package homepagemodule;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.loginmodule.R;

public class MyAccount extends AppCompatActivity {

    private TextView regFieldBox, departmentFieldBox, sectionFieldBox, emailFieldBox;
    private SharedPreferences loginCache, createAccountCache;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_account);
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        initializeViews();
        displayUserInfo();
    }

    private void initializeViews() {
        regFieldBox = findViewById(R.id.regnumber);
        departmentFieldBox = findViewById(R.id.deptfield);
        sectionFieldBox = findViewById(R.id.sectionn);
        emailFieldBox = findViewById(R.id.rn);
    }

    private void displayUserInfo() {
        Intent intent = getIntent();
        String loginOrCreateAccount = getIntent().getStringExtra("MyAccountActivity");
        if (loginOrCreateAccount.equals("LoginActivity") || loginOrCreateAccount.equals(" ")) {
            loginCache = getSharedPreferences("MyPreferencesFromLogin", MODE_PRIVATE);
            String userId = loginCache.getString("useridfromlogin", "");
            String department = loginCache.getString("deptfromlogin", "");
            String section = loginCache.getString("sectionfromlogin", "");
            String email = loginCache.getString("emailfromlogin", "");

            regFieldBox.setText(userId);
            departmentFieldBox.setText(department);
            sectionFieldBox.setText(section);
            emailFieldBox.setText(email);
        } else {
            createAccountCache = getSharedPreferences("MyPreferencesFromCreateAccount", MODE_PRIVATE);

            String userId = createAccountCache.getString("cacheUserID", "");
            String department = createAccountCache.getString("cacheDepartment", "");
            String section = createAccountCache.getString("cacheSection", "");
            String email = createAccountCache.getString("cacheEmail", "");

            regFieldBox.setText(userId);
            departmentFieldBox.setText(department);
            sectionFieldBox.setText(section);
            emailFieldBox.setText(email);
        }
    }
}
