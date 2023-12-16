package com.example.loginmodule;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import homepagemodule.HomePage;
public class CreateAccountPage extends AppCompatActivity {

    public static String num,password,confirmPassword,dept, sec,roll;
    private Button backto, creatingbutton;
    private EditText registerfield, passwordfield, repasswordfield, department, section, rollno;
    private DatabaseReference db;
    private SharedPreferences cache;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account_page);
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);


        backto = findViewById(R.id.back2);
        creatingbutton = findViewById(R.id.createAcc2);
        registerfield = findViewById(R.id.regNo);
        passwordfield = findViewById(R.id.regPass);
        repasswordfield = findViewById(R.id.regRepass);
        department = findViewById(R.id.deptField);
        section = findViewById(R.id.secField);
        rollno = findViewById(R.id.rollField);
        db = FirebaseDatabase.getInstance().getReference();

        backto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent tologin = new Intent(CreateAccountPage.this, Login.class);
                startActivity(tologin);
            }
        });

        creatingbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Validate input
               creatingaccount();
            }
        });
    }
    public void creatingaccount(){
        num = registerfield.getText().toString().trim();
        password = passwordfield.getText().toString().trim();
        confirmPassword = repasswordfield.getText().toString().trim();
        dept = department.getText().toString().trim();
        sec = section.getText().toString().trim();
        roll = rollno.getText().toString().trim();

        if (num.isEmpty() || password.isEmpty()) {
            Toast.makeText(CreateAccountPage.this, "Fill the empty fields", Toast.LENGTH_SHORT).show();
            return;
        }
        else if(password.length()<8){
            Toast.makeText(CreateAccountPage.this, "Password must contain 8 characters", Toast.LENGTH_SHORT).show();
            return;
        }
        else if(!password.equals(confirmPassword)){
            Toast.makeText(CreateAccountPage.this, "Password doesn't match", Toast.LENGTH_SHORT).show();
            return;
        }
        else if (dept.isEmpty()){
            Toast.makeText(CreateAccountPage.this, "Enter department name", Toast.LENGTH_SHORT).show();
            return;
        }
        else if (sec.isEmpty()){
            Toast.makeText(CreateAccountPage.this, "Enter section", Toast.LENGTH_SHORT).show();
            return;
        }
        else if (roll.isEmpty()){
            Toast.makeText(CreateAccountPage.this, "Enter Roll Number", Toast.LENGTH_SHORT).show();
            return;
        }

        // The uniqueKey generation should be based on the user's registration number, not the password
        String uniqueKey = String.valueOf(db.child("users").child(num).push().getKey());

        // Check if the user with the given registration number already exists
        db.child("users").child(num).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (!snapshot.exists()) {
                    // User does not exist, add new user data
                    db.child("users").child(num).child("password").setValue(password);
                    db.child("users").child(num).child("department").setValue(dept);
                    db.child("users").child(num).child("section").setValue(sec);
                    db.child("users").child(num).child("roll number").setValue(roll);
                    SharedPreferences.Editor edit = cache.edit();
                    edit.putString("cacheUserID",num);
                    edit.putString("cachePassword",password);
                    edit.putString("cacheDepartment",dept);
                    edit.putString("cacheSection",sec);
                    edit.putString("cacheRoll",roll);
                    edit.apply();


                    Intent tohome = new Intent(CreateAccountPage.this, HomePage.class);
                    startActivity(tohome);
                    finish();
                } else {
                    // User already exists
                    Toast.makeText(CreateAccountPage.this, "User Id already exist!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                error.getCode();
            }
        });
    }

}
