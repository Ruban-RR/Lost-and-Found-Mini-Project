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

    private LinearLayout llc;
    private EditText editTextMobile, editTextWatch, editTextWatch2, editTextBag, editTextBag2, editTextBag3, editTextPurse, editTextPurse2, editTextPurse3, editTextPurse4 ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lost_form);

        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        // Initialize layout elements after setting the content view
        llc = findViewById(R.id.linearLayoutContainer);
        editTextMobile = findViewById(R.id.ED1);
        editTextWatch = findViewById(R.id.ED2);
        editTextWatch2 = findViewById(R.id.ED3);
        editTextBag = findViewById(R.id.ED3);
        editTextBag2 = findViewById(R.id.ED4);
        editTextBag3 = findViewById(R.id.ED5);
        editTextPurse = findViewById(R.id.ED4);

        // Initially hide the EditText views
        llc.setVisibility(View.GONE);
        editTextMobile.setVisibility(View.GONE);
        editTextWatch.setVisibility(View.GONE);
        editTextWatch2.setVisibility(View.GONE);
        editTextBag.setVisibility(View.GONE);
        editTextBag2.setVisibility(View.GONE);
        editTextBag3.setVisibility(View.GONE);
        editTextPurse.setVisibility(View.GONE);

        String[] items = {"Select an Item","Mobile", "Wrist Watch", "Bag", "Purse"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(LostForm.this, android.R.layout.simple_spinner_item, items);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        Spinner spin = findViewById(R.id.lostthing);
        spin.setAdapter(adapter);
        spin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                // Get the selected item
                String selectedValue = items[position];

                // Show/hide the EditText views based on the selected
                    if(selectedValue.equals("Select an Item")){
                        llc.setVisibility(View.GONE);
                        editTextMobile.setVisibility(View.GONE);
                        editTextBag.setVisibility(View.GONE);
                        editTextBag2.setVisibility(View.GONE);
                        editTextWatch.setVisibility(View.GONE);
                        editTextWatch2.setVisibility(View.GONE);
                        editTextBag3.setVisibility(View.GONE);
                        editTextPurse.setVisibility(View.GONE);
                    }
                    else if (selectedValue.equals("Mobile")) {
                        llc.setVisibility(View.VISIBLE);
                        editTextMobile.setVisibility(View.VISIBLE);
                        editTextBag.setVisibility(View.GONE);
                        editTextBag2.setVisibility(View.GONE);
                        editTextWatch.setVisibility(View.GONE);
                        editTextWatch2.setVisibility(View.GONE);
                        editTextBag3.setVisibility(View.GONE);
                        editTextPurse.setVisibility(View.GONE);
                    } else if (selectedValue.equals("Bag")) {
                        llc.setVisibility(View.VISIBLE);
                        editTextMobile.setVisibility(View.GONE);
                        editTextBag.setVisibility(View.VISIBLE);
                        editTextBag2.setVisibility(View.VISIBLE);
                        editTextWatch.setVisibility(View.GONE);
                        editTextWatch2.setVisibility(View.GONE);
                        editTextBag3.setVisibility(View.VISIBLE);
                        editTextPurse.setVisibility(View.GONE);
                    }
                    else if (selectedValue.equals("Wrist Watch")) {
                        llc.setVisibility(View.VISIBLE);
                        editTextMobile.setVisibility(View.GONE);
                        editTextBag.setVisibility(View.GONE);
                        editTextBag2.setVisibility(View.GONE);
                        editTextWatch.setVisibility(View.VISIBLE);
                        editTextWatch2.setVisibility(View.VISIBLE);
                        editTextBag3.setVisibility(View.GONE);
                        editTextPurse.setVisibility(View.GONE);
                    } else if (selectedValue.equals("Bag")) {
                        llc.setVisibility(View.VISIBLE);
                        editTextMobile.setVisibility(View.GONE);
                        editTextBag.setVisibility(View.VISIBLE);
                        editTextBag2.setVisibility(View.VISIBLE);
                        editTextWatch.setVisibility(View.GONE);
                        editTextWatch2.setVisibility(View.GONE);
                        editTextBag3.setVisibility(View.VISIBLE);
                        editTextPurse.setVisibility(View.GONE);
                    }
                    else if (selectedValue.equals("Purse")) {
                        llc.setVisibility(View.VISIBLE);
                        editTextMobile.setVisibility(View.GONE);
                        editTextBag.setVisibility(View.GONE);
                        editTextBag2.setVisibility(View.GONE);
                        editTextWatch.setVisibility(View.GONE);
                        editTextWatch2.setVisibility(View.GONE);
                        editTextBag3.setVisibility(View.GONE);
                        editTextPurse.setVisibility(View.VISIBLE);
                    }else {
                        // For other items, hide all EditText views
                        llc.setVisibility(View.GONE);
                    }
                }


            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // Do nothing here
            }
        });
    }
}
