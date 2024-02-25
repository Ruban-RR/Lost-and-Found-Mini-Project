package homepagemodule;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
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
    private String selectedValue,selectedplace,selectedheadphone,selectedWatch, selectedBag;
    private LinearLayout llc;
    private EditText brandNameEditText, modelNameEditText, imeiEditText, colorEditText, uniqueEditText, dateOfLostEditText;
    private Button  Postphone, Postwatch, Postbag, Postpurse, Posthead ;
    private Spinner spinHead, spinWatch, spinPlace, typeOfBag;
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
        brandNameEditText = findViewById(R.id.Brand);
        modelNameEditText = findViewById(R.id.Model);
        imeiEditText = findViewById(R.id.IMEI);
        colorEditText = findViewById(R.id.Color);
        uniqueEditText = findViewById(R.id.Unique);
        Postphone = findViewById(R.id.PostPhone);
        spinHead = findViewById(R.id.TypeHeadphone);
        spinWatch = findViewById(R.id.TypeWatch);
        spinPlace = findViewById(R.id.Place);
        dateOfLostEditText = findViewById(R.id.Date);
        typeOfBag = findViewById(R.id.BagType);
        Postwatch = findViewById(R.id.PostWatch);
        Postbag = findViewById(R.id.PostBag);
        Postpurse = findViewById(R.id.PostPurse);
        Posthead = findViewById(R.id.PostHead);

        String[] TypesOfBags = {"-Type of Bag-","Shoulder Bag", "Hand Bag"};
        ArrayAdapter<String> adapter6 = new ArrayAdapter<>(LostForm.this, android.R.layout.simple_spinner_item, TypesOfBags);
        adapter6.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        typeOfBag.setAdapter(adapter6);
        typeOfBag.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedBag = TypesOfBags[position];
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        String[] Placesoflost = {"-Place of lost-", "1", "2", "3"};
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
                llc.setVisibility(View.VISIBLE);
                brandNameEditText.setVisibility(View.VISIBLE);
                modelNameEditText.setVisibility(View.VISIBLE);
                imeiEditText.setVisibility(View.VISIBLE);
                colorEditText.setVisibility(View.VISIBLE);
                uniqueEditText.setVisibility(View.VISIBLE);
                dateOfLostEditText.setVisibility(View.VISIBLE);
                spinWatch.setVisibility(View.VISIBLE);
                spinPlace.setVisibility(View.VISIBLE);
                spinHead.setVisibility(View.VISIBLE);
                typeOfBag.setVisibility(View.VISIBLE);
                Postphone.setVisibility(View.VISIBLE);
                Postwatch.setVisibility(View.VISIBLE);
                Postbag.setVisibility(View.VISIBLE);
                Postpurse.setVisibility(View.VISIBLE);
                Posthead.setVisibility(View.VISIBLE);

                // Update visibility based on selected item
                switch (selectedValue) {
                    case "Mobile":
                        spinWatch.setVisibility(View.GONE);
                        spinHead.setVisibility(View.GONE);
                        typeOfBag.setVisibility(View.GONE);
                        Postwatch.setVisibility(View.GONE);
                        Postbag.setVisibility(View.GONE);
                        Postpurse.setVisibility(View.GONE);
                        Posthead.setVisibility(View.GONE);
                        break;
                    case "Bag":
                        imeiEditText.setVisibility(View.GONE);
                        spinWatch.setVisibility(View.GONE);
                        spinHead.setVisibility(View.GONE);
                        Postwatch.setVisibility(View.GONE);
                        Postphone.setVisibility(View.GONE);
                        Postpurse.setVisibility(View.GONE);
                        Posthead.setVisibility(View.GONE);
                        break;
                    case "Watch":
                        typeOfBag.setVisibility(View.GONE);
                        imeiEditText.setVisibility(View.GONE);
                        spinHead.setVisibility(View.GONE);
                        Postphone.setVisibility(View.GONE);
                        Postbag.setVisibility(View.GONE);
                        Postpurse.setVisibility(View.GONE);
                        Posthead.setVisibility(View.GONE);
                        break;
                    case "Purse":
                        typeOfBag.setVisibility(View.GONE);
                        modelNameEditText.setVisibility(View.GONE);
                        imeiEditText.setVisibility(View.GONE);
                        spinWatch.setVisibility(View.GONE);
                        spinHead.setVisibility(View.GONE);
                        Postwatch.setVisibility(View.GONE);
                        Postbag.setVisibility(View.GONE);
                        Postphone.setVisibility(View.GONE);
                        Posthead.setVisibility(View.GONE);
                        break;
                    case "Headphones":
                        typeOfBag.setVisibility(View.GONE);
                        imeiEditText.setVisibility(View.GONE);
                        spinWatch.setVisibility(View.GONE);
                        Postwatch.setVisibility(View.GONE);
                        Postbag.setVisibility(View.GONE);
                        Postpurse.setVisibility(View.GONE);
                        Postphone.setVisibility(View.GONE);
                        break;
                    default:
                        llc.setVisibility(View.GONE);
                }

            }
            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
            }
        });
        cachefromlogin = getSharedPreferences("MyPreferencesFromLogin",MODE_PRIVATE);
        String registerNum = cachefromlogin.getString("useridfromlogin","");
        Postphone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                postToDBphone(registerNum);
            }
        });
        Postwatch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { postToDBwatch(registerNum); }
        });
        Postbag.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { postToDBbag(registerNum); }
        });
        Postpurse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){ postToDBpurse(registerNum); }
        });
        Posthead.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { postToDBhead(registerNum);}
        });
    }
        private void postToDBphone(String registerNum){
            brandname = brandNameEditText.getText().toString();
            modelname = modelNameEditText.getText().toString();
            imeinum = imeiEditText.getText().toString();
            colorname = colorEditText.getText().toString();
            uniquefeature = uniqueEditText.getText().toString();
            datelost = dateOfLostEditText.getText().toString();
            lostdb.child("users").child(registerNum).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    DatabaseReference ref = lostdb.child("users").child(registerNum).child("lost").child(selectedValue);
                    ref.child("Brand Name").setValue(brandname);
                    ref.child("Model Name").setValue(modelname);
                    ref.child("IMEI Number").setValue(imeinum);
                    ref.child("Color").setValue(colorname);
                    ref.child("Uniqueness").setValue(uniquefeature);
                    ref.child("Date").setValue(datelost);
                    ref.child("Location").setValue(selectedplace);
                }
                public void onCancelled(@NonNull DatabaseError error) {
                }
            });
            intcall();
        }
        private void postToDBwatch(String registerNum){
            brandname = brandNameEditText.getText().toString();
            modelname = modelNameEditText.getText().toString();
            colorname = colorEditText.getText().toString();
            uniquefeature = uniqueEditText.getText().toString();
            datelost = dateOfLostEditText.getText().toString();
            lostdb.child("users").child(registerNum).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    DatabaseReference ref = lostdb.child("users").child(registerNum).child("lost").child(selectedValue);
                    ref.child("Brand Name").setValue(brandname);
                    ref.child("Model Name").setValue(modelname);
                    ref.child("Color").setValue(colorname);
                    ref.child("Uniqueness").setValue(uniquefeature);
                    ref.child("Date").setValue(datelost);
                    ref.child("Watch Type").setValue(selectedWatch);
                    ref.child("Location").setValue(selectedplace);
                }
                public void onCancelled(@NonNull DatabaseError error) {
                }
            });
            intcall();
        }
    private void postToDBbag(String registerNum){
        brandname = brandNameEditText.getText().toString();
        modelname = modelNameEditText.getText().toString();
        colorname = colorEditText.getText().toString();
        uniquefeature = uniqueEditText.getText().toString();
        datelost = dateOfLostEditText.getText().toString();
        lostdb.child("users").child(registerNum).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                DatabaseReference ref = lostdb.child("users").child(registerNum).child("lost").child(selectedValue);
                ref.child("Brand Name").setValue(brandname);
                ref.child("Model Name").setValue(modelname);
                ref.child("Color").setValue(colorname);
                ref.child("Uniqueness").setValue(uniquefeature);
                ref.child("Date").setValue(datelost);
                ref.child("Bag Type").setValue(selectedBag);
                ref.child("Location").setValue(selectedplace);
            }
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
        intcall();
    }

        private void postToDBpurse(String registerNum){
            brandname = brandNameEditText.getText().toString();
            colorname = colorEditText.getText().toString();
            uniquefeature = uniqueEditText.getText().toString();
            datelost = dateOfLostEditText.getText().toString();
            lostdb.child("users").child(registerNum).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    DatabaseReference ref = lostdb.child("users").child(registerNum).child("lost").child(selectedValue);
                    ref.child("Brand Name").setValue(brandname);
                    ref.child("Color").setValue(colorname);
                    ref.child("Uniqueness").setValue(uniquefeature);
                    ref.child("Date").setValue(datelost);
                    ref.child("Location").setValue(selectedplace);
                }
                public void onCancelled(@NonNull DatabaseError error) {
                }
            });
            intcall();
        }
        private void postToDBhead(String registerNum){
            brandname = brandNameEditText.getText().toString();
            modelname = modelNameEditText.getText().toString();
            colorname = colorEditText.getText().toString();
            uniquefeature = uniqueEditText.getText().toString();
            datelost = dateOfLostEditText.getText().toString();
            lostdb.child("users").child(registerNum).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    DatabaseReference ref = lostdb.child("users").child(registerNum).child("lost").child(selectedValue);
                    ref.child("Brand Name").setValue(brandname);
                    ref.child("Model Name").setValue(modelname);
                    ref.child("Color").setValue(colorname);
                    ref.child("Uniqueness").setValue(uniquefeature);
                    ref.child("Date").setValue(datelost);
                    ref.child("Headphone Type").setValue(selectedheadphone);
                    ref.child("Location").setValue(selectedplace);
                }
                public void onCancelled(@NonNull DatabaseError error) {
                }
            });
            intcall();
        }
        private void intcall(){
            Intent tosuccess = new Intent(LostForm.this, SuccessfulPost.class);
            startActivity(tosuccess);
        }

}
