package homepagemodule;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
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
    private DatabaseReference lostdb, compareRef;
    private SharedPreferences cachefromlogin;
    public static String brandname, modelname, imeinum, colorname, uniquefeature, datelost;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lost_form);

        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        lostdb = FirebaseDatabase.getInstance().getReference();
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        compareRef = database.getReference("users");

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
            brandname = brandNameEditText.getText().toString().toLowerCase().trim();
            modelname = modelNameEditText.getText().toString().toLowerCase().trim();
            imeinum = imeiEditText.getText().toString().toLowerCase().trim();
            colorname = colorEditText.getText().toString().toLowerCase().trim();
            uniquefeature = uniqueEditText.getText().toString().toLowerCase().trim();
            datelost = dateOfLostEditText.getText().toString().toLowerCase().trim();

            if (brandname.isEmpty()||modelname.isEmpty()||imeinum.isEmpty()||colorname.isEmpty()||
                    uniquefeature.isEmpty()|| datelost.isEmpty() || selectedplace.isEmpty()) {
                Toast.makeText(LostForm.this, "Enter the blank fields!", Toast.LENGTH_SHORT).show();
            }else{
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
            Log.d("before method call","yes");
            matchItemsPhone(selectedValue,brandname,modelname,imeinum,colorname,
                    uniquefeature, datelost,selectedplace);

        }
        private void postToDBwatch(String registerNum){
            brandname = brandNameEditText.getText().toString().toLowerCase().trim();
            modelname = modelNameEditText.getText().toString().toLowerCase().trim();
            colorname = colorEditText.getText().toString().toLowerCase().trim();
            uniquefeature = uniqueEditText.getText().toString().toLowerCase().trim();
            datelost = dateOfLostEditText.getText().toString().toLowerCase().trim();
            if (brandname.isEmpty()||modelname.isEmpty()||colorname.isEmpty()||selectedWatch.isEmpty()||
                    uniquefeature.isEmpty()|| datelost.isEmpty() || selectedplace.isEmpty()) {
                Toast.makeText(LostForm.this, "Enter the blank fields!", Toast.LENGTH_SHORT).show();
            }else{
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
            intcall();}
            matchItemsWatch(selectedValue,brandname,modelname,colorname,uniquefeature,datelost,selectedplace,selectedWatch);
        }

    private void matchItemsWatch(String selectedValue,String brandname, String modelname, String colorname, String uniquefeature, String datelost, String selectedplace, String selectedWatch) {
        compareRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot registerSnapshot : snapshot.getChildren()){
                    String registerid = registerSnapshot.getKey();
                    // Log.d("for1",registerid);
                    boolean found = registerSnapshot.hasChild("found");
                    if (found) {
                        //Log.d("Boolean","True");
                        for (DataSnapshot foundSnapshot : registerSnapshot.child("found").getChildren()) {
                            String itemName = foundSnapshot.getKey();
                            //Log.d("for2", itemName);
                            if (itemName.equals(selectedValue)) {
                                // Log.d("item","equals");
                                String BRAND = foundSnapshot.child("Brand Name").getValue(String.class);
                                String MODEL = foundSnapshot.child("Model Name").getValue(String.class);
                                String COLOR = foundSnapshot.child("Color").getValue(String.class);
                                String UNIQUE =foundSnapshot.child("Uniqueness").getValue(String.class);
                                String PLACE = foundSnapshot.child("Location").getValue(String.class);
                                String DATE = foundSnapshot.child("Date").getValue(String.class);
                                String WATCH = foundSnapshot.child("Watch Type").getValue(String.class);

                                if (BRAND.equals(brandname) && MODEL.equals(modelname)
                                        && COLOR.equals(colorname) && UNIQUE.equals(uniquefeature)
                                        && PLACE.equals(selectedplace) && WATCH.equals(selectedWatch)
                                        && DATE.equals(datelost)){
                                    Log.d("item", "found");
                                } else {
                                    Log.d("item", "not found");
                                }
                            }

                        }
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    private void postToDBbag(String registerNum){
        brandname = brandNameEditText.getText().toString().toLowerCase().trim();
        modelname = modelNameEditText.getText().toString().toLowerCase().trim();
        colorname = colorEditText.getText().toString().toLowerCase().trim();
        uniquefeature = uniqueEditText.getText().toString().toLowerCase().trim();
        datelost = dateOfLostEditText.getText().toString().toLowerCase().trim();
        if (brandname.isEmpty()||modelname.isEmpty()||colorname.isEmpty()||
                uniquefeature.isEmpty()|| datelost.isEmpty() || selectedplace.isEmpty()||selectedBag.isEmpty()) {
            Toast.makeText(LostForm.this, "Enter the blank fields!", Toast.LENGTH_SHORT).show();
        }else{
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
        intcall();}
        matchItemsBag(selectedValue,brandname,datelost,selectedBag,colorname,uniquefeature);
    }

    private void matchItemsBag(String selectedValue, String brandname, String datelost, String selectedBag, String colorname, String uniquefeature) {
        compareRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot registerSnapshot : snapshot.getChildren()){
                    String registerid = registerSnapshot.getKey();
                    // Log.d("for1",registerid);
                    boolean found = registerSnapshot.hasChild("found");
                    if (found) {
                        //Log.d("Boolean","True");
                        for (DataSnapshot foundSnapshot : registerSnapshot.child("found").getChildren()) {
                            String itemName = foundSnapshot.getKey();
                            //Log.d("for2", itemName);
                            if (itemName.equals(selectedValue)) {
                                // Log.d("item","equals");
                                String BRAND = foundSnapshot.child("Brand Name").getValue(String.class);
                                String COLOR = foundSnapshot.child("Color").getValue(String.class);
                                String UNIQUE =foundSnapshot.child("Uniqueness").getValue(String.class);
                                String PLACE = foundSnapshot.child("Location").getValue(String.class);
                                String DATE = foundSnapshot.child("Date").getValue(String.class);
                                String BAG = foundSnapshot.child("Bag Type").getValue(String.class);

                                if (BRAND.equals(brandname)
                                        && COLOR.equals(colorname) && UNIQUE.equals(uniquefeature)
                                        && PLACE.equals(selectedplace) && BAG.equals(selectedBag)
                                        && DATE.equals(datelost)){
                                    Log.d("item", "found");
                                } else {
                                    Log.d("item", "not found");
                                }
                            }

                        }
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void postToDBpurse(String registerNum){
            brandname = brandNameEditText.getText().toString().toLowerCase().trim();
            colorname = colorEditText.getText().toString().toLowerCase().trim();
            uniquefeature = uniqueEditText.getText().toString().toLowerCase().trim();
            datelost = dateOfLostEditText.getText().toString().toLowerCase().trim();
        if (brandname.isEmpty()||colorname.isEmpty()||
                uniquefeature.isEmpty()|| datelost.isEmpty() || selectedplace.isEmpty()) {
            Toast.makeText(LostForm.this, "Enter the blank fields!", Toast.LENGTH_SHORT).show();
        }else{
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
            intcall();}
        matchItemsPurse(selectedplace,selectedValue,colorname,brandname,datelost,uniquefeature);
        }

    private void matchItemsPurse(String selectedplace, String selectedValue, String colorname, String brandname, String datelost, String uniquefeature) {
        compareRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot registerSnapshot : snapshot.getChildren()){
                    String registerid = registerSnapshot.getKey();
                    // Log.d("for1",registerid);
                    boolean found = registerSnapshot.hasChild("found");
                    if (found) {
                        //Log.d("Boolean","True");
                        for (DataSnapshot foundSnapshot : registerSnapshot.child("found").getChildren()) {
                            String itemName = foundSnapshot.getKey();
                            //Log.d("for2", itemName);
                            if (itemName.equals(selectedValue)) {
                                // Log.d("item","equals");
                                String BRAND = foundSnapshot.child("Brand Name").getValue(String.class);
                                String COLOR = foundSnapshot.child("Color").getValue(String.class);
                                String UNIQUE =foundSnapshot.child("Uniqueness").getValue(String.class);
                                String PLACE = foundSnapshot.child("Location").getValue(String.class);
                                String DATE = foundSnapshot.child("Date").getValue(String.class);

                                if (BRAND.equals(brandname)
                                        && COLOR.equals(colorname) && UNIQUE.equals(uniquefeature)
                                        && PLACE.equals(selectedplace)
                                        && DATE.equals(datelost)){
                                    Log.d("item", "found");
                                } else {
                                    Log.d("item", "not found");
                                }
                            }

                        }
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void postToDBhead(String registerNum){
            brandname = brandNameEditText.getText().toString().toLowerCase().trim();
            modelname = modelNameEditText.getText().toString().toLowerCase().trim();
            colorname = colorEditText.getText().toString().toLowerCase().trim();
            uniquefeature = uniqueEditText.getText().toString().toLowerCase().trim();
            datelost = dateOfLostEditText.getText().toString().toLowerCase().trim();
            if (brandname.isEmpty()||modelname.isEmpty()||colorname.isEmpty()||
                    uniquefeature.isEmpty()|| datelost.isEmpty() || selectedplace.isEmpty()||selectedheadphone.isEmpty()) {
                Toast.makeText(LostForm.this, "Enter the blank fields!", Toast.LENGTH_SHORT).show();
            }else{
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
            intcall();}
            matchItemsHeadphone(selectedValue,brandname,modelname,colorname,uniquefeature,datelost,selectedheadphone,selectedplace);
        }

    private void matchItemsHeadphone(String selectedValue, String brandname, String modelname, String colorname, String uniquefeature, String datelost, String selectedheadphone, String selectedplace) {
        compareRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot registerSnapshot : snapshot.getChildren()){
                    String registerid = registerSnapshot.getKey();
                    // Log.d("for1",registerid);
                    boolean found = registerSnapshot.hasChild("found");
                    if (found) {
                        //Log.d("Boolean","True");
                        for (DataSnapshot foundSnapshot : registerSnapshot.child("found").getChildren()) {
                            String itemName = foundSnapshot.getKey();
                            //Log.d("for2", itemName);
                            if (itemName.equals(selectedValue)) {
                                // Log.d("item","equals");
                                String BRAND = foundSnapshot.child("Brand Name").getValue(String.class);
                                String MODEL = foundSnapshot.child("Model Name").getValue(String.class);
                                String COLOR = foundSnapshot.child("Color").getValue(String.class);
                                String UNIQUE =foundSnapshot.child("Uniqueness").getValue(String.class);
                                String PLACE = foundSnapshot.child("Location").getValue(String.class);
                                String DATE = foundSnapshot.child("Date").getValue(String.class);
                                String HEAD = foundSnapshot.child("Headphone Type").getValue(String.class);

                                if (BRAND.equals(brandname) && MODEL.equals(modelname)
                                        && COLOR.equals(colorname) && UNIQUE.equals(uniquefeature)
                                        && PLACE.equals(selectedplace) && HEAD.equals(selectedheadphone)
                                        && DATE.equals(datelost)){
                                    Log.d("item", "found");
                                } else {
                                    Log.d("item", "not found");
                                }
                            }

                        }
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void matchItemsPhone(String selectedValue, String brandname,String modelname, String imeinum,
                                 String colorname,String uniquefeature, String datelost,
                                 String selectedplace) {
        //Log.d("inside method","yes");
        compareRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot registerSnapshot : snapshot.getChildren()){
                    String registerid = registerSnapshot.getKey();
                   // Log.d("for1",registerid);
                    boolean found = registerSnapshot.hasChild("found");
                    if (found) {
                        //Log.d("Boolean","True");
                        for (DataSnapshot foundSnapshot : registerSnapshot.child("found").getChildren()) {
                            String itemName = foundSnapshot.getKey();
                            //Log.d("for2", itemName);
                            if (itemName.equals(selectedValue)) {
                               // Log.d("item","equals");
                                        String BRAND = foundSnapshot.child("Brand Name").getValue(String.class);
                                        String MODEL = foundSnapshot.child("Model Name").getValue(String.class);
                                        String COLOR = foundSnapshot.child("Color").getValue(String.class);
                                        String UNIQUE =foundSnapshot.child("Uniqueness").getValue(String.class);
                                        String PLACE = foundSnapshot.child("Location").getValue(String.class);
                                        String DATE = foundSnapshot.child("Date").getValue(String.class);
                                        String IMEI = foundSnapshot.child("IMEI Number").getValue(String.class);

                                        if (BRAND.equals(brandname) && MODEL.equals(modelname)
                                                && COLOR.equals(colorname) && UNIQUE.equals(uniquefeature)
                                                && PLACE.equals(selectedplace) && IMEI.equals(imeinum)
                                                && DATE.equals(datelost)){
                                            Log.d("item", "found");
                                        } else {
                                            Log.d("item", "not found");
                                        }




                            }

                        }
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void intcall(){
            Intent tosuccess = new Intent(LostForm.this, SuccessfulPost.class);
            startActivity(tosuccess);
        }

}
