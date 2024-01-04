package homepagemodule;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ShareCompat;

import android.content.Intent;
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
                Intent logback = new Intent(HomePage.this, Login.class);
                startActivity(logback);
            }
        });
        myaccBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent tomyacc = new Intent(HomePage.this, MyAccount.class);
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



    }
    public void onBackPressedDispatcher(){
        getOnBackPressedDispatcher().onBackPressed();
    }


}