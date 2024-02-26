package homepagemodule;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
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
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.UUID;

public class FoundForm extends AppCompatActivity {

    private LinearLayout fllc;
    private String fselectedValue, fselectedheadphone, fselectedWatch, fselectedplace, fselectedBag, imageURL;
    private EditText fbrandNameEditText, fmodelNameEditText, fimeiEditText, fcolorEditText, funiqueEditText, fdateOfLostEditText;
    private Button fImage,fPostphone, fPostwatch, fPostbag, fPostpurse, fPosthead;
    private ImageView fimageview;
    private static final int fPICK_IMAGE_REQUEST = 1;
    private Spinner fspinHead,fspinWatch, fspinPlace, ftypeOfBag;
    private SharedPreferences fcachefromlogin;
    private DatabaseReference founddb;
    private Uri imageUri;
    public static String fbrandname, fmodelname, fimeinum, fcolorname, funiquefeature, fvaluabledetails, fdatelost;
    private FirebaseStorage fbStorage;
    private StorageReference storageRef;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.found_form);

        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        founddb = FirebaseDatabase.getInstance().getReference();
        fbStorage = FirebaseStorage.getInstance();
        storageRef = fbStorage.getReference();
        // Initialize layout elements after setting the content view
        fllc = findViewById(R.id.flinearLayoutContainer);
        fbrandNameEditText = findViewById(R.id.fBrand);
        fmodelNameEditText = findViewById(R.id.fModel);
        fimeiEditText = findViewById(R.id.fIMEI);
        fcolorEditText = findViewById(R.id.fColor);
        funiqueEditText = findViewById(R.id.fUnique);
        fspinHead = findViewById(R.id.fTypeHeadphone);
        fspinWatch = findViewById(R.id.fTypeWatch);
        fspinPlace = findViewById(R.id.fPlace);
        fdateOfLostEditText = findViewById(R.id.fDate);
        fImage = findViewById(R.id.fImageUpload);
        fimageview = findViewById(R.id.fimageView);
        ftypeOfBag = findViewById(R.id.fBagType);
        fPostphone = findViewById(R.id.fPostPhone);
        fPostwatch = findViewById(R.id.fPostWatch);
        fPostbag = findViewById(R.id.fPostBag);
        fPostpurse = findViewById(R.id.fPostPurse);
        fPosthead = findViewById(R.id.fPostHead);
        fImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openFileChooser();
            }
        });
        String[] fTypesOfBags = {"-Type of Bag-","Shoulder Bag", "Hand Bag"};
        ArrayAdapter<String> adapter6 = new ArrayAdapter<>(FoundForm.this, android.R.layout.simple_spinner_item, fTypesOfBags);
        adapter6.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        ftypeOfBag.setAdapter(adapter6);
        ftypeOfBag.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                fselectedBag = fTypesOfBags[position];
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

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
                fllc.setVisibility(View.VISIBLE);
                fbrandNameEditText.setVisibility(View.VISIBLE);
                fmodelNameEditText.setVisibility(View.VISIBLE);
                fimeiEditText.setVisibility(View.VISIBLE);
                fcolorEditText.setVisibility(View.VISIBLE);
                funiqueEditText.setVisibility(View.VISIBLE);
                fdateOfLostEditText.setVisibility(View.VISIBLE);
                fspinWatch.setVisibility(View.VISIBLE);
                fspinPlace.setVisibility(View.VISIBLE);
                fspinHead.setVisibility(View.VISIBLE);
                ftypeOfBag.setVisibility(View.VISIBLE);
                fPostphone.setVisibility(View.VISIBLE);
                fPostwatch.setVisibility(View.VISIBLE);
                fPostbag.setVisibility(View.VISIBLE);
                fPostpurse.setVisibility(View.VISIBLE);
                fPosthead.setVisibility(View.VISIBLE);
                // Update visibility based on selected item
                switch (fselectedValue) {
                    case "Mobile":
                        fspinWatch.setVisibility(View.GONE);
                        fspinHead.setVisibility(View.GONE);
                        ftypeOfBag.setVisibility(View.GONE);
                        fPostwatch.setVisibility(View.GONE);
                        fPostbag.setVisibility(View.GONE);
                        fPostpurse.setVisibility(View.GONE);
                        fPosthead.setVisibility(View.GONE);
                        break;
                    case "Bag":
                        fimeiEditText.setVisibility(View.GONE);
                        fspinWatch.setVisibility(View.GONE);
                        fspinHead.setVisibility(View.GONE);
                        fPostwatch.setVisibility(View.GONE);
                        fPostphone.setVisibility(View.GONE);
                        fPostpurse.setVisibility(View.GONE);
                        fPosthead.setVisibility(View.GONE);
                        break;
                    case "Watch":
                        ftypeOfBag.setVisibility(View.GONE);
                        fimeiEditText.setVisibility(View.GONE);
                        fspinHead.setVisibility(View.GONE);
                        fPostphone.setVisibility(View.GONE);
                        fPostbag.setVisibility(View.GONE);
                        fPostpurse.setVisibility(View.GONE);
                        fPosthead.setVisibility(View.GONE);
                        break;
                    case "Purse":
                        ftypeOfBag.setVisibility(View.GONE);
                        fmodelNameEditText.setVisibility(View.GONE);
                        fimeiEditText.setVisibility(View.GONE);
                        fspinWatch.setVisibility(View.GONE);
                        fspinHead.setVisibility(View.GONE);
                        fPostwatch.setVisibility(View.GONE);
                        fPostbag.setVisibility(View.GONE);
                        fPostphone.setVisibility(View.GONE);
                        fPosthead.setVisibility(View.GONE);
                        break;
                    case "Headphones":
                        ftypeOfBag.setVisibility(View.GONE);
                        fimeiEditText.setVisibility(View.GONE);
                        fspinWatch.setVisibility(View.GONE);
                        fPostwatch.setVisibility(View.GONE);
                        fPostbag.setVisibility(View.GONE);
                        fPostpurse.setVisibility(View.GONE);
                        fPostphone.setVisibility(View.GONE);
                        break;
                    default:
                        fllc.setVisibility(View.GONE);
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // Do nothing here
            }
        });
        fcachefromlogin = getSharedPreferences("MyPreferencesFromLogin",MODE_PRIVATE);
        String fregisterNumber = fcachefromlogin.getString("useridfromlogin","");
        fPostphone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fpostToDBphone(fregisterNumber);
            }
        });
        fPostwatch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { fpostToDBwatch(fregisterNumber); }
        });
        fPostbag.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { fpostToDBbag(fregisterNumber); }
        });
        fPostpurse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){ fpostToDBpurse(fregisterNumber); }
        });
        fPosthead.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { fpostToDBhead(fregisterNumber);}
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
            imageUri = data.getData();
            fimageview.setVisibility(View.VISIBLE);
            fimageview.setImageURI(imageUri);
            uploadPicture();
        }
    }

    private void uploadPicture() {
        final ProgressDialog pdBox = new  ProgressDialog(this);
        pdBox.setTitle("Uploading Image.....");
        pdBox.show();
        final String randomKey = UUID.randomUUID().toString();
        StorageReference riversRef = storageRef.child("images/"+randomKey);
        riversRef.putFile(imageUri)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot skSnapshot) {
                        pdBox.dismiss();
                        riversRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                imageURL = uri.toString();
                            }
                        });
                        Snackbar.make(findViewById(android.R.id.content),"Image Uploaded",Snackbar.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        pdBox.dismiss();
                        Toast.makeText(getApplicationContext(),"Failed to upload :(",Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {
                        double progressPercent = (100.00 * snapshot.getBytesTransferred() / snapshot.getTotalByteCount());
                        pdBox.setMessage("Progress:" + (int) progressPercent + "%");
                    }
                });
    }

    private void fpostToDBphone(String fregisterNumber){
        fbrandname = fbrandNameEditText.getText().toString();
        fmodelname = fmodelNameEditText.getText().toString();
        fimeinum = fimeiEditText.getText().toString();
        fcolorname = fcolorEditText.getText().toString();
        funiquefeature = funiqueEditText.getText().toString();
        fdatelost = fdateOfLostEditText.getText().toString();

        if (fbrandname.isEmpty() || fmodelname.isEmpty() || fimeinum.isEmpty() || fcolorname.isEmpty() ||
                funiquefeature.isEmpty() || fdatelost.isEmpty() || fselectedplace.isEmpty() || fimageview.getDrawable() == null){
            Toast.makeText(FoundForm.this, "Enter the blank fields! ", Toast.LENGTH_SHORT).show();
        }else{
        founddb.child("users").child(fregisterNumber).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                DatabaseReference fref = founddb.child("users").child(fregisterNumber).child("found").child(fselectedValue);
                fref.child("Brand Name").setValue(fbrandname);
                fref.child("Model Name").setValue(fmodelname);
                fref.child("IMEI Number").setValue(fimeinum);
                fref.child("Color").setValue(fcolorname);
                fref.child("Uniqueness").setValue(funiquefeature);
                fref.child("Date").setValue(fdatelost);
                fref.child("Location").setValue(fselectedplace);
                fref.child("Image URL").setValue(imageURL);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });

        fintcall();}
    }
    private void fpostToDBwatch(String registerNum){
        fbrandname = fbrandNameEditText.getText().toString();
        fmodelname = fmodelNameEditText.getText().toString();
        fcolorname = fcolorEditText.getText().toString();
        funiquefeature = funiqueEditText.getText().toString();
        fdatelost = fdateOfLostEditText.getText().toString();

        if (fbrandname.isEmpty() || fmodelname.isEmpty() || fcolorname.isEmpty() || fselectedplace.isEmpty() ||
                funiquefeature.isEmpty() || fdatelost.isEmpty() || fselectedWatch.isEmpty() || fimageview.getDrawable() == null){
            Toast.makeText(FoundForm.this, "Enter the blank fields! ", Toast.LENGTH_SHORT).show();
        }else{
        founddb.child("users").child(registerNum).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                DatabaseReference fref = founddb.child("users").child(registerNum).child("found").child(fselectedValue);
                fref.child("Brand Name").setValue(fbrandname);
                fref.child("Model Name").setValue(fmodelname);
                fref.child("Color").setValue(fcolorname);
                fref.child("Uniqueness").setValue(funiquefeature);
                fref.child("Date").setValue(fdatelost);
                fref.child("Watch Type").setValue(fselectedWatch);
                fref.child("Location").setValue(fselectedplace);
                fref.child("Image URL").setValue(imageURL);
            }
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
        fintcall();}
    }
    private void fpostToDBbag(String registerNum){
        fbrandname = fbrandNameEditText.getText().toString();
        fmodelname = fmodelNameEditText.getText().toString();
        fcolorname = fcolorEditText.getText().toString();
        funiquefeature = funiqueEditText.getText().toString();
        fdatelost = fdateOfLostEditText.getText().toString();

        if (fbrandname.isEmpty() || fmodelname.isEmpty() || fcolorname.isEmpty() || funiquefeature.isEmpty() ||
                fdatelost.isEmpty() || fselectedplace.isEmpty() || fselectedBag.isEmpty()|| fimageview.getDrawable() == null){
            Toast.makeText(FoundForm.this, "Enter the blank fields! ", Toast.LENGTH_SHORT).show();
        }
        else{
        founddb.child("users").child(registerNum).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                DatabaseReference fref = founddb.child("users").child(registerNum).child("found").child(fselectedValue);
                fref.child("Brand Name").setValue(fbrandname);
                fref.child("Model Name").setValue(fmodelname);
                fref.child("Color").setValue(fcolorname);
                fref.child("Uniqueness").setValue(funiquefeature);
                fref.child("Date").setValue(fdatelost);
                fref.child("Bag Type").setValue(fselectedBag);
                fref.child("Location").setValue(fselectedplace);
                fref.child("Image URL").setValue(imageURL);
            }
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
            fintcall();}

    }

    private void fpostToDBpurse(String registerNum){
        fbrandname = fbrandNameEditText.getText().toString();
        fcolorname = fcolorEditText.getText().toString();
        funiquefeature = funiqueEditText.getText().toString();
        fdatelost = fdateOfLostEditText.getText().toString();

        if (fbrandname.isEmpty() || fcolorname.isEmpty() ||
                funiquefeature.isEmpty() || fdatelost.isEmpty() || fselectedplace.isEmpty() || fimageview.getDrawable() == null){
            Toast.makeText(FoundForm.this, "Enter the blank fields! ", Toast.LENGTH_SHORT).show();
        }else{
        founddb.child("users").child(registerNum).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                DatabaseReference fref = founddb.child("users").child(registerNum).child("found").child(fselectedValue);
                fref.child("Brand Name").setValue(fbrandname);
                fref.child("Color").setValue(fcolorname);
                fref.child("Uniqueness").setValue(funiquefeature);
                fref.child("Date").setValue(fdatelost);
                fref.child("Location").setValue(fselectedplace);
                fref.child("Image URL").setValue(imageURL);
            }

            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
        fintcall();}
    }
    private void fpostToDBhead(String registerNum){
        fbrandname = fbrandNameEditText.getText().toString();
        fmodelname = fmodelNameEditText.getText().toString();
        fcolorname = fcolorEditText.getText().toString();
        funiquefeature = funiqueEditText.getText().toString();
        fdatelost = fdateOfLostEditText.getText().toString();

        if (fbrandname.isEmpty() || fmodelname.isEmpty() || fcolorname.isEmpty() ||
                funiquefeature.isEmpty() || fdatelost.isEmpty() || fselectedplace.isEmpty() || fselectedheadphone.isEmpty() || fimageview.getDrawable() == null){
            Toast.makeText(FoundForm.this, "Enter the blank fields! ", Toast.LENGTH_SHORT).show();
        }else{
        founddb.child("users").child(registerNum).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                DatabaseReference fref = founddb.child("users").child(registerNum).child("found").child(fselectedValue);
                fref.child("Brand Name").setValue(fbrandname);
                fref.child("Model Name").setValue(fmodelname);
                fref.child("Color").setValue(fcolorname);
                fref.child("Uniqueness").setValue(funiquefeature);
                fref.child("Date").setValue(fdatelost);
                fref.child("Headphone Type").setValue(fselectedheadphone);
                fref.child("Location").setValue(fselectedplace);
            }
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
        fintcall();}
    }
    private void fintcall(){
        Intent tosuccesspost = new Intent(FoundForm.this, SuccessfulPost.class);
        startActivity(tosuccesspost);
    }

}
