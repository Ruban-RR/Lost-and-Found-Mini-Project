package homepagemodule;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;

import com.example.loginmodule.R;

public class FoundForm extends AppCompatActivity {

    private LinearLayout fllc;
    private EditText fBrandName, fModelName, fImeiNumber, fColorName, fUniqueIn, fValuablesIn, fdateoflost;
    private Button fImage;
    private static final int fPICK_IMAGE_REQUEST = 1;
    private Spinner fspinHead,fspinWatch, fspinPlace;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.found_form);

        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        // Initialize layout elements after setting the content view
        fllc = findViewById(R.id.flinearLayoutContainer);
        fBrandName = findViewById(R.id.fBrand);
        fModelName = findViewById(R.id.fModel);
        fImeiNumber = findViewById(R.id.fIMEI);
        fColorName = findViewById(R.id.fColor);
        fUniqueIn = findViewById(R.id.fUnique);
        fImage = findViewById(R.id.fImageUpload);
        fValuablesIn = findViewById(R.id.fValuables);
        fspinHead = findViewById(R.id.fTypeHeadphone);
        fspinWatch = findViewById(R.id.fTypeWatch);
        fspinPlace = findViewById(R.id.fPlace);
        fdateoflost = findViewById(R.id.fDate);

        hideViews();
        fImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openFileChooser();
            }
        });
        String[] Placesoflost = {"-Select Place-", "1", "2", "3"};
        ArrayAdapter<String> adapter4 = new ArrayAdapter<>(FoundForm.this, android.R.layout.simple_spinner_item, Placesoflost);
        adapter4.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        fspinPlace.setAdapter(adapter4);
        fspinPlace.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedplace = Placesoflost[position];
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        String[] headphonesType = {"-Select Type-", "Wired", "Neckband", "Earpod"};
        ArrayAdapter<String> adapter2 = new ArrayAdapter<>(FoundForm.this, android.R.layout.simple_spinner_item, headphonesType);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        fspinHead.setAdapter(adapter2);
        fspinHead.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedheadphone = headphonesType[position];
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        String[] watchType = {"-Select Type-", "Analog", "Digital", "Smart"};
        ArrayAdapter<String> adapter3 = new ArrayAdapter<>(FoundForm.this, android.R.layout.simple_spinner_item, watchType);
        adapter3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        fspinWatch.setAdapter(adapter3);
        fspinWatch.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedWatch = watchType[position];
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        String[] items = {"-Select Thing-","Mobile", "Watch", "Bag", "Purse", "Headphones"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(FoundForm.this, android.R.layout.simple_spinner_item, items);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        Spinner spin = findViewById(R.id.foundthing);
        spin.setAdapter(adapter);
        spin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                // Get the selected item
                String selectedValue = items[position];

                // Show/hide the EditText views based on the selected
                if(selectedValue.equals("-Select Thing-")){
                    hideViews();

                }
                else if (selectedValue.equals("Mobile")) {
                    fllc.setVisibility(View.VISIBLE);
                    fBrandName.setVisibility(View.VISIBLE);
                    fModelName.setVisibility(View.VISIBLE);
                    fImeiNumber.setVisibility(View.VISIBLE);
                    fColorName.setVisibility(View.VISIBLE);
                    fUniqueIn.setVisibility(View.VISIBLE);
                    fImage.setVisibility(View.VISIBLE);
                    fspinPlace.setVisibility(View.VISIBLE);
                    fdateoflost.setVisibility((View.VISIBLE));
                    fValuablesIn.setVisibility(View.GONE);
                    fspinHead.setVisibility(View.GONE);
                    fspinWatch.setVisibility(View.GONE);


                } else if (selectedValue.equals("Bag")) {
                    fllc.setVisibility(View.VISIBLE);
                    fBrandName.setVisibility(View.VISIBLE);
                    fModelName.setVisibility(View.VISIBLE);
                    fImeiNumber.setVisibility(View.GONE);
                    fColorName.setVisibility(View.VISIBLE);
                    fUniqueIn.setVisibility(View.VISIBLE);
                    fImage.setVisibility(View.VISIBLE);
                    fValuablesIn.setVisibility(View.VISIBLE);
                    fspinPlace.setVisibility(View.VISIBLE);
                    fspinHead.setVisibility(View.GONE);
                    fdateoflost.setVisibility((View.VISIBLE));
                    fspinWatch.setVisibility(View.GONE);

                }
                else if (selectedValue.equals("Watch")) {
                    fllc.setVisibility(View.VISIBLE);
                    fBrandName.setVisibility(View.VISIBLE);
                    fModelName.setVisibility(View.VISIBLE);
                    fImeiNumber.setVisibility(View.GONE);
                    fColorName.setVisibility(View.VISIBLE);
                    fUniqueIn.setVisibility(View.VISIBLE);
                    fImage.setVisibility(View.VISIBLE);
                    fValuablesIn.setVisibility(View.GONE);
                    fspinHead.setVisibility(View.GONE);
                    fspinPlace.setVisibility(View.VISIBLE);
                    fspinWatch.setVisibility(View.VISIBLE);
                    fdateoflost.setVisibility((View.VISIBLE));


                }
                else if (selectedValue.equals("Purse")) {
                    fllc.setVisibility(View.VISIBLE);
                    fBrandName.setVisibility(View.VISIBLE);
                    fModelName.setVisibility(View.GONE);
                    fImeiNumber.setVisibility(View.GONE);
                    fColorName.setVisibility(View.VISIBLE);
                    fUniqueIn.setVisibility(View.VISIBLE);
                    fImage.setVisibility(View.VISIBLE);
                    fValuablesIn.setVisibility(View.VISIBLE);
                    fspinHead.setVisibility(View.GONE);
                    fspinPlace.setVisibility(View.VISIBLE);
                    fspinWatch.setVisibility(View.GONE);
                    fdateoflost.setVisibility((View.VISIBLE));


                }
                else if (selectedValue.equals("Headphones")) {
                    fllc.setVisibility(View.VISIBLE);
                    fBrandName.setVisibility(View.VISIBLE);
                    fModelName.setVisibility(View.VISIBLE);
                    fImeiNumber.setVisibility(View.GONE);
                    fColorName.setVisibility(View.VISIBLE);
                    fUniqueIn.setVisibility(View.VISIBLE);
                    fImage.setVisibility(View.VISIBLE);
                    fspinPlace.setVisibility(View.VISIBLE);
                    fValuablesIn.setVisibility(View.GONE);
                    fspinHead.setVisibility(View.VISIBLE);
                    fspinWatch.setVisibility(View.GONE);
                    fdateoflost.setVisibility((View.VISIBLE));

                }else {
                    // For other items, hide all EditText views
                    fllc.setVisibility(View.GONE);
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // Do nothing here
            }
        });
    }
    private void openFileChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, fPICK_IMAGE_REQUEST);
    }
    private void hideViews(){
        fllc.setVisibility(View.GONE);
        fBrandName.setVisibility(View.GONE);
        fModelName.setVisibility(View.GONE);
        fImeiNumber.setVisibility(View.GONE);
        fColorName.setVisibility(View.GONE);
        fUniqueIn.setVisibility(View.GONE);
        fImage.setVisibility(View.GONE);
        fValuablesIn.setVisibility(View.GONE);
        fspinHead.setVisibility(View.GONE);
        fspinWatch.setVisibility(View.GONE);
        fspinPlace.setVisibility(View.GONE);
        fdateoflost.setVisibility((View.GONE));
    }

}
