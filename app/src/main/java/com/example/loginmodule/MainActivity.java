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

    private SharedPreferences cache;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_main);

        cache = getSharedPreferences("MyPreferences", MODE_PRIVATE);

        // Check for cached data when the app is opened
        String cachedUserId = cache.getString("cacheUserId", "");
        String cachedPassword = cache.getString("cachePassword", "");

        if (!cachedUserId.isEmpty() && !cachedPassword.isEmpty()) {
            // Cached credentials exist, navigate to HomePage
            Intent homeIntent = new Intent(MainActivity.this, HomePage.class);
            homeIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(homeIntent);
            finish();  // Finish MainActivity to prevent going back to it using the back button
        }

        // Continue with the rest of your onCreate method...
        Button btn1 = findViewById(R.id.btn1);
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
