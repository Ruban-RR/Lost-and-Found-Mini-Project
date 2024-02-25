package homepagemodule;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ShareCompat;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import com.example.loginmodule.Login;
import com.example.loginmodule.R;

public class HomePage extends AppCompatActivity {
    private Button logoutbtton, lostButton, foundButton;
    private ImageButton myaccBtn;
    private SharedPreferences clearcache, clearcache2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        logoutbtton = findViewById(R.id.logoutBtn);
        myaccBtn = findViewById(R.id.myacc);
        lostButton = findViewById(R.id.lostBtn);
        foundButton = findViewById(R.id.foundBtn);


        logoutbtton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                clearcache = getSharedPreferences("MyPreferencesFromLogin", MODE_PRIVATE);
                clearcache2 = getSharedPreferences("MyPreferencesFromCreateAccount",MODE_PRIVATE);
                SharedPreferences.Editor editorLogout = clearcache2.edit();
                SharedPreferences.Editor editor = clearcache.edit();
                editor.clear();
                editor.apply();
                editorLogout.clear();
                editorLogout.apply();
                Intent logback = new Intent(HomePage.this, Login.class);
                startActivity(logback);
            }
        });
        myaccBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)     {
                    Intent tomyacc = new Intent(HomePage.this, MyAccount.class);
                    tomyacc.putExtra("MyAccountActivity", getIntent().getStringExtra("MyAccountActivity"));
                    startActivity(tomyacc);
            }
        });

        lostButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent lostB = new Intent(HomePage.this, LostForm.class);
                startActivity(lostB);
            }
        });

        foundButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent foundB = new Intent(HomePage.this, FoundForm.class);
                startActivity(foundB);
            }
        });


    }
    public void onBackPressedDispatcher(){
        getOnBackPressedDispatcher().onBackPressed();
    }


}