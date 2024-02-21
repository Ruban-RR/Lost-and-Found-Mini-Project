package homepagemodule;

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

public class FoundForm extends AppCompatActivity {

    private LinearLayout fllc;
    private String fselectedValue, fselectedheadphone, fselectedWatch, fselectedplace;
    private EditText fBrandName, fModelName, fImeiNumber, fColorName, fUniqueIn, fValuablesIn, fdateoflost;
    private Button fImage,postbtn;
    private ImageView fimageview;
    private static final int fPICK_IMAGE_REQUEST = 1;
    private Spinner fspinHead,fspinWatch, fspinPlace;
    private SharedPreferences fcachefromlogin;
    private DatabaseReference founddb;
    public static String fbrandname, fmodelname, fimeinum, fcolorname, funiquefeature, fvaluabledetails, fdatelost;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.found_form);

        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        founddb = FirebaseDatabase.getInstance().getReference();
        // Initialize layout elements after setting the content view
        fllc = findViewById(R.id.flinearLayoutContainer);
        fBrandName = findViewById(R.id.fBrand);
        fModelName = findViewById(R.id.fModel);
        fImeiNumber = findViewById(R.id.fIMEI);
        fColorName = findViewById(R.id.fColor);
        fUniqueIn = findViewById(R.id.fUnique);
        fValuablesIn = findViewById(R.id.fValuables);
        fspinHead = findViewById(R.id.fTypeHeadphone);
        fspinWatch = findViewById(R.id.fTypeWatch);
        fspinPlace = findViewById(R.id.fPlace);
        fdateoflost = findViewById(R.id.fDate);
        postbtn = findViewById(R.id.fPost);
        fImage = findViewById(R.id.fImageUpload);
        fimageview = findViewById(R.id.fimageView);

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
                 fselectedplace = Placesoflost[position];
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
                fselectedheadphone = headphonesType[position];
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
                 fselectedWatch = watchType[position];
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
                fselectedValue = items[position];

                // Show/hide the EditText views based on the selected
                if(fselectedValue.equals("-Select Thing-")){
                    hideViews();

                }
                else if (fselectedValue.equals("Mobile")) {
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


                } else if (fselectedValue.equals("Bag")) {
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
                else if (fselectedValue.equals("Watch")) {
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
                else if (fselectedValue.equals("Purse")) {
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
                else if (fselectedValue.equals("Headphones")) {
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
        postbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fpostToDB();
            }
        });
    }
    private void openFileChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, fPICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == fPICK_IMAGE_REQUEST && resultCode == RESULT_OK
                && data != null && data.getData() != null) {
            Uri imageUri = data.getData();
            fimageview.setVisibility(View.VISIBLE);
            fimageview.setImageURI(imageUri);
        }
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
    private void fpostToDB(){
        fbrandname = fBrandName.getText().toString();
        fmodelname = fModelName.getText().toString();
        fimeinum = fImeiNumber.getText().toString();
        fcolorname = fColorName.getText().toString();
        funiquefeature = fUniqueIn.getText().toString();
        fvaluabledetails = fValuablesIn.getText().toString();
        fdatelost = fdateoflost.getText().toString();

        fcachefromlogin = getSharedPreferences("MyPreferencesFromLogin",MODE_PRIVATE);
        String fregisterNumber = fcachefromlogin.getString("useridfromlogin","");
        founddb.child("users").child(fregisterNumber).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                founddb.child("users").child(fregisterNumber).child("found").child(fselectedValue).child("Brand Name").setValue(fbrandname);
                founddb.child("users").child(fregisterNumber).child("found").child(fselectedValue).child("Model Name").setValue(fmodelname);
                founddb.child("users").child(fregisterNumber).child("found").child(fselectedValue).child("IMEI Number").setValue(fimeinum);
                founddb.child("users").child(fregisterNumber).child("found").child(fselectedValue).child("Color").setValue(fcolorname);
                founddb.child("users").child(fregisterNumber).child("found").child(fselectedValue).child("Uniqueness").setValue(funiquefeature);
                founddb.child("users").child(fregisterNumber).child("found").child(fselectedValue).child("ValuablesInside").setValue(fvaluabledetails);
                founddb.child("users").child(fregisterNumber).child("found").child(fselectedValue).child("Date").setValue(fdatelost);
                founddb.child("users").child(fregisterNumber).child("found").child(fselectedValue).child("Watch Type").setValue(fselectedWatch);
                founddb.child("users").child(fregisterNumber).child("found").child(fselectedValue).child("Headphone Type").setValue(fselectedheadphone);
                founddb.child("users").child(fregisterNumber).child("found").child(fselectedValue).child("Location").setValue(fselectedplace);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        Toast.makeText(FoundForm.this,"Posted Successfully :)",Toast.LENGTH_SHORT).show();

    }

}
