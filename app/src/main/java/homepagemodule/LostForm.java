package homepagemodule;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;

import com.example.loginmodule.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class LostForm extends AppCompatActivity {

    private LinearLayout llc;
    private EditText BrandName, ModelName, ImeiNumber, ColorName, UniqueIn, ValuablesIn, dateoflost;
    private Button Image, PostButton;
    private static final int PICK_IMAGE_REQUEST = 1;
    private Spinner spinHead,spinWatch, spinPlace;
    private DatabaseReference lostdb;
    private ImageView imageview;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lost_form);

        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        lostdb = FirebaseDatabase.getInstance().getReference();

        // Initialize layout elements after setting the content view
        llc = findViewById(R.id.linearLayoutContainer);
        BrandName = findViewById(R.id.Brand);
        ModelName = findViewById(R.id.Model);
        ImeiNumber = findViewById(R.id.IMEI);
        ColorName = findViewById(R.id.Color);
        UniqueIn = findViewById(R.id.Unique);
        Image = findViewById(R.id.ImageUpload);
        PostButton = findViewById(R.id.Post);
        ValuablesIn = findViewById(R.id.Valuables);
        spinHead = findViewById(R.id.TypeHeadphone);
        spinWatch = findViewById(R.id.TypeWatch);
        spinPlace = findViewById(R.id.Place);
        dateoflost = findViewById(R.id.Date);
        imageview = findViewById(R.id.imageView);
        PostButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                postToDB();
            }
        });

        hideViews();
        Image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openFileChooser();
            }
        });
        String[] Placesoflost = {"-Select Place-", "1", "2", "3"};
        ArrayAdapter<String> adapter4 = new ArrayAdapter<>(LostForm.this, android.R.layout.simple_spinner_item, Placesoflost);
        adapter4.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinPlace.setAdapter(adapter4);
        spinPlace.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedplace = Placesoflost[position];
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        String[] headphonesType = {"-Select Type-", "Wired", "Neckband", "Earpod"};
        ArrayAdapter<String> adapter2 = new ArrayAdapter<>(LostForm.this, android.R.layout.simple_spinner_item, headphonesType);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinHead.setAdapter(adapter2);
        spinHead.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedheadphone = headphonesType[position];
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        String[] watchType = {"-Select Type-", "Analog", "Digital", "Smart"};
        ArrayAdapter<String> adapter3 = new ArrayAdapter<>(LostForm.this, android.R.layout.simple_spinner_item, watchType);
        adapter3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinWatch.setAdapter(adapter3);
        spinWatch.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedWatch = watchType[position];
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        String[] items = {"-Select Thing-","Mobile", "Watch", "Bag", "Purse", "Headphones"};
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
                    if(selectedValue.equals("-Select Thing-")){
                        hideViews();

                    }
                    else if (selectedValue.equals("Mobile")) {
                        llc.setVisibility(View.VISIBLE);
                        BrandName.setVisibility(View.VISIBLE);
                        ModelName.setVisibility(View.VISIBLE);
                        ImeiNumber.setVisibility(View.VISIBLE);
                        ColorName.setVisibility(View.VISIBLE);
                        UniqueIn.setVisibility(View.VISIBLE);
                        Image.setVisibility(View.VISIBLE);
                        spinPlace.setVisibility(View.VISIBLE);
                        dateoflost.setVisibility((View.VISIBLE));
                        ValuablesIn.setVisibility(View.GONE);
                        spinHead.setVisibility(View.GONE);
                        spinWatch.setVisibility(View.GONE);


                    } else if (selectedValue.equals("Bag")) {
                        llc.setVisibility(View.VISIBLE);
                        BrandName.setVisibility(View.VISIBLE);
                        ModelName.setVisibility(View.VISIBLE);
                        ImeiNumber.setVisibility(View.GONE);
                        ColorName.setVisibility(View.VISIBLE);
                        UniqueIn.setVisibility(View.VISIBLE);
                        Image.setVisibility(View.VISIBLE);
                        ValuablesIn.setVisibility(View.VISIBLE);
                        spinPlace.setVisibility(View.VISIBLE);
                        spinHead.setVisibility(View.GONE);
                        dateoflost.setVisibility((View.VISIBLE));
                        spinWatch.setVisibility(View.GONE);

                    }
                    else if (selectedValue.equals("Watch")) {
                        llc.setVisibility(View.VISIBLE);
                        BrandName.setVisibility(View.VISIBLE);
                        ModelName.setVisibility(View.VISIBLE);
                        ImeiNumber.setVisibility(View.GONE);
                        ColorName.setVisibility(View.VISIBLE);
                        UniqueIn.setVisibility(View.VISIBLE);
                        Image.setVisibility(View.VISIBLE);
                        ValuablesIn.setVisibility(View.GONE);
                        spinHead.setVisibility(View.GONE);
                        spinPlace.setVisibility(View.VISIBLE);
                        spinWatch.setVisibility(View.VISIBLE);
                        dateoflost.setVisibility((View.VISIBLE));


                    }
                    else if (selectedValue.equals("Purse")) {
                        llc.setVisibility(View.VISIBLE);
                        BrandName.setVisibility(View.VISIBLE);
                        ModelName.setVisibility(View.GONE);
                        ImeiNumber.setVisibility(View.GONE);
                        ColorName.setVisibility(View.VISIBLE);
                        UniqueIn.setVisibility(View.VISIBLE);
                        Image.setVisibility(View.VISIBLE);
                        ValuablesIn.setVisibility(View.VISIBLE);
                        spinHead.setVisibility(View.GONE);
                        spinPlace.setVisibility(View.VISIBLE);
                        spinWatch.setVisibility(View.GONE);
                        dateoflost.setVisibility((View.VISIBLE));


                    }
                    else if (selectedValue.equals("Headphones")) {
                        llc.setVisibility(View.VISIBLE);
                        BrandName.setVisibility(View.VISIBLE);
                        ModelName.setVisibility(View.VISIBLE);
                        ImeiNumber.setVisibility(View.GONE);
                        ColorName.setVisibility(View.VISIBLE);
                        UniqueIn.setVisibility(View.VISIBLE);
                        Image.setVisibility(View.VISIBLE);
                        spinPlace.setVisibility(View.VISIBLE);
                        ValuablesIn.setVisibility(View.GONE);
                        spinHead.setVisibility(View.VISIBLE);
                        spinWatch.setVisibility(View.GONE);
                        dateoflost.setVisibility((View.VISIBLE));

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
    private void openFileChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, PICK_IMAGE_REQUEST);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK
                && data != null && data.getData() != null) {
            Uri imageUri = data.getData();
            imageview.setVisibility(View.VISIBLE);
            imageview.setImageURI(imageUri);
        }
    }


    private void postToDB(){

    }

    private void hideViews(){
        llc.setVisibility(View.GONE);
        BrandName.setVisibility(View.GONE);
        ModelName.setVisibility(View.GONE);
        ImeiNumber.setVisibility(View.GONE);
        ColorName.setVisibility(View.GONE);
        UniqueIn.setVisibility(View.GONE);
        Image.setVisibility(View.GONE);
        ValuablesIn.setVisibility(View.GONE);
        spinHead.setVisibility(View.GONE);
        spinWatch.setVisibility(View.GONE);
        spinPlace.setVisibility(View.GONE);
        dateoflost.setVisibility((View.GONE));
    }

}
