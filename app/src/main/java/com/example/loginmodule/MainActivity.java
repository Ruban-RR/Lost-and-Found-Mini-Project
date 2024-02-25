package com.example.loginmodule;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import homepagemodule.HomePage;

public class MainActivity extends AppCompatActivity {

    private SharedPreferences cacheforautologin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_main);
        Button btn1 = findViewById(R.id.btnGetStarted);
        cacheforautologin = getSharedPreferences("MyPreferencesFromLogin", MODE_PRIVATE);

        // Check for cached data when the app is opened
        String cachedUserId = cacheforautologin.getString("useridfromlogin", "");
        String cachedPassword = cacheforautologin.getString("passwordfromlogin", "");

        if (!cachedUserId.isEmpty() && !cachedPassword.isEmpty()) {
            // Cached credentials exist, navigate to HomePage
            Intent homeIntent = new Intent(MainActivity.this, HomePage.class);
            homeIntent.putExtra("MyAccountActivity","LoginActivity");
            homeIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(homeIntent);
            finish();  // Finish MainActivity to prevent going back to it using the back button
        } else {

            // Continue with the rest of your onCreate method...

            btn1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // If no cached credentials, navigate to Login
                    Intent loginIntent = new Intent(MainActivity.this, Login.class);
                    startActivity(loginIntent);
                }
            });
        }
    }
}
