package homepagemodule;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.loginmodule.R;

public class SuccessfulPost extends AppCompatActivity {
    private Button HomeButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.successful_post);
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        HomeButton = findViewById(R.id.homeButton);
        HomeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent toHomePage = new Intent(SuccessfulPost.this,HomePage.class);
                toHomePage.putExtra("MyAccountActivity","LoginActivity");
                startActivity(toHomePage);
            }
        });

    }
}