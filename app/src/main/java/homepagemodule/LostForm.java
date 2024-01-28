package homepagemodule;

import androidx.appcompat.app.AppCompatActivity;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;

import com.example.loginmodule.R;

public class LostForm extends AppCompatActivity {
    final LinearLayout linearLayoutContainer = findViewById(R.id.linearLayoutContainer);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lost_form);

        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);


        String[] items = {"Mobile", "Wrist Watch", "Bag", "Purse"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(LostForm.this, android.R.layout.simple_spinner_item, items);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        Spinner spin = findViewById(R.id.lostthing);
        spin.setAdapter(adapter);



        spin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                // Clear previous EditText fields
                linearLayoutContainer.removeAllViews();

                // Get the selected item
                String selectedValue = items[position];

                // Dynamically create and add EditText fields based on the selected item
                if (selectedValue.equals("Mobile")) {
                    addEditText("Hint1");
                    addEditText("Hint2");
                } else if (selectedValue.equals("Bag")) {
                    addEditText("Hint3");
                    addEditText("Hint4");
                }
                // Add more conditions for other items as needed
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // Do nothing here
            }
        });
    }

    private void addEditText(String hint) {
        EditText editText = new EditText(this);
        editText.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT));
        editText.setHint(hint);
        linearLayoutContainer.addView(editText);
    }
}
