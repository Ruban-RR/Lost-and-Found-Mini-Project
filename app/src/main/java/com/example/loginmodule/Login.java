package com.example.loginmodule;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import homepagemodule.HomePage;


public class Login extends AppCompatActivity {

    Button back,login, createnewbutton;
    EditText userid;
    EditText password;
    TextView signUp;
    TextView accountCreation;

    private DatabaseReference logindb;
    private SharedPreferences cache;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        logindb = FirebaseDatabase.getInstance().getReference();

        back = (Button) findViewById(R.id.back2);
        login = (Button) findViewById(R.id.loginBtn);
        createnewbutton = (Button) findViewById(R.id.createbutton);
        userid = (EditText) findViewById(R.id.userID);
        password = (EditText) findViewById(R.id.pass);
        signUp = (TextView) findViewById(R.id.signup);
        accountCreation = (TextView) findViewById(R.id.newAcc);



        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent backButton = new Intent(Login.this, MainActivity.class);
                startActivity(backButton);
            }
        });

        cache = getSharedPreferences("MyPreferences",MODE_PRIVATE);
        String useridCache = cache.getString("cacheUserId","");
        String passCache = cache.getString("cachePassword","");
        if (!useridCache.isEmpty() && !passCache.isEmpty()){
            loginFunc(useridCache, passCache);
        }
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String numberfield = userid.getText().toString();
                String passwordfield = password.getText().toString();

                if (numberfield.isEmpty()) {
                    Toast.makeText(Login.this, "Enter Register Number!", Toast.LENGTH_SHORT).show();
                    return;
                }
                else if (passwordfield.isEmpty()) {
                    Toast.makeText(Login.this, "Enter Password", Toast.LENGTH_SHORT).show();
                    return;
                }

                loginFunc(numberfield, passwordfield);

            }
        });
        createnewbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent buttonforcreatenew = new Intent(Login.this, CreateAccountPage.class);
                startActivity(buttonforcreatenew);
            }
        });







    }
    public void loginFunc(String numberfield, String passwordfield){
        logindb.child("users").child(numberfield).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    String passondb = snapshot.child("password").getValue(String.class);
                    if (passwordfield.equals(passondb)){
                        Toast.makeText(Login.this, "Successfully Logged in",Toast.LENGTH_SHORT).show();
                        Intent loginButton = new Intent(Login.this, HomePage.class);
                        startActivity(loginButton);
                    }else {
                        Toast.makeText(Login.this, "Incorrect password",Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(Login.this, "User ID not exist",Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(Login.this, "Some error on logging in", Toast.LENGTH_SHORT).show();
            }
        });
    }

}
