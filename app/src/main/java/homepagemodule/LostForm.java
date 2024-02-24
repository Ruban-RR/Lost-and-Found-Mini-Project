package homepagemodule;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
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
import android.widget.Toast;

import com.example.loginmodule.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class LostForm extends AppCompatActivity {
    private String selectedValue,selectedplace,selectedheadphone,selectedWatch, selectedValuable;
    private LinearLayout llc,llc2;
    private EditText BrandName, ModelName, ImeiNumber, ColorName, UniqueIn, dateoflost, AadharName,AadharNumber,CardName,LicenseName, PANname,DOBinPAN,OtherThing, DOBinLicense;
    private Button  PostButton;
    private static final int PICK_IMAGE_REQUEST = 1;
    private Spinner spinHead,spinWatch, spinPlace, ValuablesIn, BankName;
    private DatabaseReference lostdb;
    private SharedPreferences cachefromlogin;
    public static String brandname, modelname, imeinum, colorname, uniquefeature, datelost;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lost_form);

        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        lostdb = FirebaseDatabase.getInstance().getReference();

        // Initialize layout elements after setting the content view
        llc = findViewById(R.id.linearLayoutContainer);
        llc2= findViewById(R.id.linearLayoutContainer2);
        BrandName = findViewById(R.id.Brand);
        ModelName = findViewById(R.id.Model);
        ImeiNumber = findViewById(R.id.IMEI);
        ColorName = findViewById(R.id.Color);
        UniqueIn = findViewById(R.id.Unique);
        PostButton = findViewById(R.id.Post);
        ValuablesIn = findViewById(R.id.Valuables);
        spinHead = findViewById(R.id.TypeHeadphone);
        spinWatch = findViewById(R.id.TypeWatch);
        spinPlace = findViewById(R.id.Place);
        dateoflost = findViewById(R.id.Date);
        AadharName = findViewById(R.id.aadharname);
        AadharNumber = findViewById(R.id.aadharnumber);
        BankName = findViewById(R.id.bankname);
        CardName = findViewById(R.id.nameincard);
        LicenseName = findViewById(R.id.licensename);
        PANname = findViewById(R.id.nameinpan);
        DOBinPAN = findViewById(R.id.dobinpan);
        OtherThing= findViewById(R.id.otherthing);
        DOBinLicense = findViewById(R.id.licencedob);
        hideViews();
        hideViews2();
        String[] Placesoflost = {"-Select Place-", "1", "2", "3"};
        ArrayAdapter<String> adapter4 = new ArrayAdapter<>(LostForm.this, android.R.layout.simple_spinner_item, Placesoflost);
        adapter4.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinPlace.setAdapter(adapter4);
        spinPlace.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedplace = Placesoflost[position];
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        String[] ValuablesInside = {"-Valuables Inside-", "Aadhar", "Bank Card", "Driving License","PAN Card","Others"};
        ArrayAdapter<String> adapterValuable = new ArrayAdapter<>(LostForm.this, android.R.layout.simple_spinner_item, ValuablesInside);
        adapterValuable.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        ValuablesIn.setAdapter(adapterValuable);
        ValuablesIn.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedValuable = ValuablesInside[position];
                if (selectedValuable.equals("-Valuables Inside-")){
                    hideViews2();
                } else if (selectedValue.equals("Aadhar")) {
                    AadharName.setVisibility(View.VISIBLE);
                    AadharNumber.setVisibility(View.VISIBLE);
                    BankName.setVisibility(View.GONE);
                    CardName.setVisibility(View.GONE);
                    LicenseName.setVisibility(View.GONE);
                    DOBinPAN.setVisibility(View.GONE);
                    PANname.setVisibility(View.GONE);
                    DOBinLicense.setVisibility(View.GONE);
                    OtherThing.setVisibility(View.GONE);
                } else if (selectedValue.equals("Bank Card")) {
                    AadharName.setVisibility(View.GONE);
                    AadharNumber.setVisibility(View.GONE);
                    BankName.setVisibility(View.VISIBLE);
                    CardName.setVisibility(View.VISIBLE);
                    LicenseName.setVisibility(View.GONE);
                    DOBinPAN.setVisibility(View.GONE);
                    PANname.setVisibility(View.GONE);
                    DOBinLicense.setVisibility(View.GONE);
                    OtherThing.setVisibility(View.GONE);
                } else if (selectedValue.equals("Driving License")) {
                    AadharName.setVisibility(View.GONE);
                    AadharNumber.setVisibility(View.GONE);
                    BankName.setVisibility(View.GONE);
                    CardName.setVisibility(View.GONE);
                    LicenseName.setVisibility(View.VISIBLE);
                    DOBinPAN.setVisibility(View.GONE);
                    PANname.setVisibility(View.GONE);
                    DOBinLicense.setVisibility(View.VISIBLE);
                    OtherThing.setVisibility(View.GONE);
                } else if (selectedValue.equals("PAN Card")) {
                    AadharName.setVisibility(View.GONE);
                    AadharNumber.setVisibility(View.GONE);
                    BankName.setVisibility(View.GONE);
                    CardName.setVisibility(View.GONE);
                    LicenseName.setVisibility(View.GONE);
                    DOBinPAN.setVisibility(View.VISIBLE);
                    PANname.setVisibility(View.VISIBLE);
                    DOBinLicense.setVisibility(View.GONE);
                    OtherThing.setVisibility(View.GONE);
                } else if (selectedValue.equals("Others")) {
                    AadharName.setVisibility(View.GONE);
                    AadharNumber.setVisibility(View.GONE);
                    BankName.setVisibility(View.GONE);
                    CardName.setVisibility(View.GONE);
                    LicenseName.setVisibility(View.GONE);
                    DOBinLicense.setVisibility(View.GONE);
                    DOBinPAN.setVisibility(View.GONE);
                    PANname.setVisibility(View.GONE);
                    OtherThing.setVisibility(View.VISIBLE);
                }else {
                    llc2.setVisibility(View.GONE);
                }
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
                 selectedheadphone = headphonesType[position];
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
                 selectedWatch = watchType[position];
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
                selectedValue = items[position];

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
        PostButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                postToDB();
            }
        });
    }
    private void openFileChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, PICK_IMAGE_REQUEST);
    }
    private void hideViews(){
        llc.setVisibility(View.GONE);
        BrandName.setVisibility(View.GONE);
        ModelName.setVisibility(View.GONE);
        ImeiNumber.setVisibility(View.GONE);
        ColorName.setVisibility(View.GONE);
        UniqueIn.setVisibility(View.GONE);
        ValuablesIn.setVisibility(View.GONE);
        spinHead.setVisibility(View.GONE);
        spinWatch.setVisibility(View.GONE);
        spinPlace.setVisibility(View.GONE);
        dateoflost.setVisibility((View.GONE));
    }
    private void hideViews2(){
        AadharName.setVisibility(View.GONE);
        AadharNumber.setVisibility(View.GONE);
        BankName.setVisibility(View.GONE);
        CardName.setVisibility(View.GONE);
        LicenseName.setVisibility(View.GONE);
        DOBinPAN.setVisibility(View.GONE);
        DOBinLicense.setVisibility(View.GONE);
        PANname.setVisibility(View.GONE);
        OtherThing.setVisibility(View.GONE);
    }

        private void postToDB(){
            brandname = BrandName.getText().toString();
            modelname = ModelName.getText().toString();
            imeinum = ImeiNumber.getText().toString();
            colorname = ColorName.getText().toString();
            uniquefeature = UniqueIn.getText().toString();
            datelost = dateoflost.getText().toString();


            cachefromlogin = getSharedPreferences("MyPreferencesFromLogin",MODE_PRIVATE);
            String registerNum = cachefromlogin.getString("useridfromlogin","");

            lostdb.child("users").child(registerNum).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    lostdb.child("users").child(registerNum).child("lost").child(selectedValue).child("Brand Name").setValue(brandname);
                    lostdb.child("users").child(registerNum).child("lost").child(selectedValue).child("Model Name").setValue(modelname);
                    lostdb.child("users").child(registerNum).child("lost").child(selectedValue).child("IMEI Number").setValue(imeinum);
                    lostdb.child("users").child(registerNum).child("lost").child(selectedValue).child("Color").setValue(colorname);
                    lostdb.child("users").child(registerNum).child("lost").child(selectedValue).child("Uniqueness").setValue(uniquefeature);
                    lostdb.child("users").child(registerNum).child("lost").child(selectedValue).child("ValuablesInside").setValue(selectedValuable);
                    lostdb.child("users").child(registerNum).child("lost").child(selectedValue).child("Date").setValue(datelost);
                    lostdb.child("users").child(registerNum).child("lost").child(selectedValue).child("Watch Type").setValue(selectedWatch);
                    lostdb.child("users").child(registerNum).child("lost").child(selectedValue).child("Headphone Type").setValue(selectedheadphone);
                    lostdb.child("users").child(registerNum).child("lost").child(selectedValue).child("Location").setValue(selectedplace);

                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
            Toast.makeText(LostForm.this,"Posted Successfully :)",Toast.LENGTH_SHORT).show();
        }


}
