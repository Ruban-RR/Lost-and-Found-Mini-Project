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
import android.util.Log;
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
    private String flostPersonEmail,fselectedValue, fselectedheadphone, fselectedWatch, fselectedplace, fselectedBag, imageURL;
    private EditText fbrandNameEditText, fmodelNameEditText, fimeiEditText, fcolorEditText, funiqueEditText, fdateOfLostEditText;
    private Button fImage,fPostphone, fPostwatch, fPostbag, fPostpurse, fPosthead;
    private ImageView fimageview;
    private static final int fPICK_IMAGE_REQUEST = 1;
    private Spinner fspinHead,fspinWatch, fspinPlace, ftypeOfBag;
    private SharedPreferences fcachefromlogin, fcacheForFoundFromLogin, fcacheForFoundFromCreateAccount;
    private DatabaseReference founddb,fcompareRef;
    private Uri imageUri;
    public static String fbrandname, fmodelname, fimeinum, fcolorname, funiquefeature, fvaluabledetails, fdatelost;
    private FirebaseStorage fbStorage;
    private StorageReference storageRef;
    private String ffromLoginOrCreateAccount;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.found_form);

        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        founddb = FirebaseDatabase.getInstance().getReference();
        fbStorage = FirebaseStorage.getInstance();
        storageRef = fbStorage.getReference();
        FirebaseDatabase fdatabase = FirebaseDatabase.getInstance();
        fcompareRef = fdatabase.getReference("users");
        ffromLoginOrCreateAccount = getIntent().getStringExtra("MyAccountActivity");
        if(ffromLoginOrCreateAccount.equals("LoginActivity")||ffromLoginOrCreateAccount.equals(" ")) {
            fcacheForFoundFromLogin = getSharedPreferences("MyPreferencesFromLogin", MODE_PRIVATE);
            flostPersonEmail = fcacheForFoundFromLogin.getString("emailfromlogin","");
        }else{
            fcacheForFoundFromCreateAccount = getSharedPreferences("MyPreferencesFromCreateAccount",MODE_PRIVATE);
            flostPersonEmail = fcacheForFoundFromCreateAccount.getString("cacheEmail","");
        }
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
                fref.child("Imageurl").setValue(imageURL);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });

        fintcall();}
        Log.d("before post to db","pass");
        fmatchItemsPhone(fbrandname,fmodelname,fimeinum,fcolorname,
                funiquefeature,fdatelost,fselectedplace,imageURL);
    }

    private void fmatchItemsPhone(String fbrandname, String fmodelname, String fimeinum,
                                  String fcolorname, String funiquefeature,
                                  String fdatelost, String fselectedplace, String imageURL) {
        fcompareRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Log.d("inside match function","pass");
                for (DataSnapshot registerSnapshot : snapshot.getChildren()) {
                    String registerid = registerSnapshot.getKey();
                    Log.d("for1",registerid);
                    boolean found = registerSnapshot.hasChild("lost");
                    if (found) {
                        Log.d("Boolean","True");
                        for (DataSnapshot foundSnapshot : registerSnapshot.child("lost").getChildren()) {
                            String itemName = foundSnapshot.getKey();
                            Log.d("for2", itemName);
                            if (itemName.equals(fselectedValue)) {
                                Log.d("item","equals");
                                String fBRAND = foundSnapshot.child("Brand Name").getValue(String.class);
                                String fMODEL = foundSnapshot.child("Model Name").getValue(String.class);
                                String fCOLOR = foundSnapshot.child("Color").getValue(String.class);
                                String fUNIQUE = foundSnapshot.child("Uniqueness").getValue(String.class);
                                String fPLACE = foundSnapshot.child("Location").getValue(String.class);
                                String fDATE = foundSnapshot.child("Date").getValue(String.class);
                                String fIMEI = foundSnapshot.child("IMEI Number").getValue(String.class);

                                if (fBRAND.equals(fbrandname) && fMODEL.equals(fmodelname)
                                        && fCOLOR.equals(fcolorname) && fUNIQUE.equals(funiquefeature)
                                        && fPLACE.equals(fselectedplace) && fIMEI.equals(fimeinum)
                                        && fDATE.equals(fdatelost)) {
                                    ffromLoginOrCreateAccount = getIntent().getStringExtra("MyAccountActivity");
                                    if (ffromLoginOrCreateAccount.equals("LoginActivity") || ffromLoginOrCreateAccount.equals(" ")) {

                                        fcacheForFoundFromLogin = getSharedPreferences("MyPreferencesFromLogin", MODE_PRIVATE);
                                        String foundPersonRegId = fcacheForFoundFromLogin.getString("useridfromlogin", "");
                                        String foundPersonEmail = fcacheForFoundFromLogin.getString("emailfromlogin", "");
                                        String foundPersonDept = fcacheForFoundFromLogin.getString("deptfromlogin", "");
                                        String foundPersonSec = fcacheForFoundFromLogin.getString("sectionfromlogin", "");
                                        Log.d("inside if from login", "pass");
                                        Log.d("image", imageURL);
                                        String fLostEmail = registerSnapshot.child("email").getValue(String.class);
                                        sendEmailFound(foundPersonRegId, foundPersonEmail, foundPersonDept, foundPersonSec, fLostEmail, imageURL);
                                    }
                                    else{
                                        fcacheForFoundFromCreateAccount = getSharedPreferences("MyPreferencesFromCreateAccount",MODE_PRIVATE);
                                        String foundPersonRegId = fcacheForFoundFromCreateAccount.getString("cacheUserID","");
                                        String foundPersonEmail = fcacheForFoundFromCreateAccount.getString("cacheEmail","");
                                        String foundPersonDept =fcacheForFoundFromCreateAccount.getString("cacheDepartment","");
                                        String foundPersonSec = fcacheForFoundFromCreateAccount.getString("cacheSection","");
                                        String foundImageLink = foundSnapshot.child("Imageurl").getValue(String.class);
                                        Log.d("inside if from createacc","pass");
                                        String fLostEmail = registerSnapshot.child("email").getValue(String.class);
                                        sendEmailFound(foundPersonRegId, foundPersonEmail, foundPersonDept, foundPersonSec, fLostEmail,imageURL);
                                    }
                                }
                                Log.d("outside if","not pass");}}}}}
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    private void fmatchItemsWatch(String fbrandname, String fmodelname, String fcolorname,
                                  String funiquefeature,
                                  String fdatelost, String fselectedWatch,String fselectedplace, String imageURL) {
        fcompareRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Log.d("inside match function","pass");
                for (DataSnapshot registerSnapshot : snapshot.getChildren()) {
                    String registerid = registerSnapshot.getKey();
                    Log.d("for1",registerid);
                    boolean found = registerSnapshot.hasChild("lost");
                    if (found) {
                        Log.d("Boolean","True");
                        for (DataSnapshot foundSnapshot : registerSnapshot.child("lost").getChildren()) {
                            String itemName = foundSnapshot.getKey();
                            Log.d("for2", itemName);
                            if (itemName.equals(fselectedValue)) {
                                Log.d("item","equals");
                                String fBRAND = foundSnapshot.child("Brand Name").getValue(String.class);
                                String fMODEL = foundSnapshot.child("Model Name").getValue(String.class);
                                String fCOLOR = foundSnapshot.child("Color").getValue(String.class);
                                String fUNIQUE = foundSnapshot.child("Uniqueness").getValue(String.class);
                                String fPLACE = foundSnapshot.child("Location").getValue(String.class);
                                String fDATE = foundSnapshot.child("Date").getValue(String.class);
                                String fWATCHTYPE = foundSnapshot.child("Watch Type").getValue(String.class);

                                if (fBRAND.equals(fbrandname) && fMODEL.equals(fmodelname)
                                        && fCOLOR.equals(fcolorname) && fUNIQUE.equals(funiquefeature)
                                        && fPLACE.equals(fselectedplace) && fWATCHTYPE.equals(fselectedWatch)
                                        && fDATE.equals(fdatelost)) {
                                    ffromLoginOrCreateAccount = getIntent().getStringExtra("MyAccountActivity");
                                    if (ffromLoginOrCreateAccount.equals("LoginActivity") || ffromLoginOrCreateAccount.equals(" ")) {

                                        fcacheForFoundFromLogin = getSharedPreferences("MyPreferencesFromLogin", MODE_PRIVATE);
                                        String foundPersonRegId = fcacheForFoundFromLogin.getString("useridfromlogin", "");
                                        String foundPersonEmail = fcacheForFoundFromLogin.getString("emailfromlogin", "");
                                        String foundPersonDept = fcacheForFoundFromLogin.getString("deptfromlogin", "");
                                        String foundPersonSec = fcacheForFoundFromLogin.getString("sectionfromlogin", "");
                                        Log.d("inside if from login", "pass");
                                        Log.d("image", imageURL);
                                        String fLostEmail = registerSnapshot.child("email").getValue(String.class);
                                        sendEmailFound(foundPersonRegId, foundPersonEmail, foundPersonDept, foundPersonSec, fLostEmail, imageURL);
                                    }
                                    else{
                                        fcacheForFoundFromCreateAccount = getSharedPreferences("MyPreferencesFromCreateAccount",MODE_PRIVATE);
                                        String foundPersonRegId = fcacheForFoundFromCreateAccount.getString("cacheUserID","");
                                        String foundPersonEmail = fcacheForFoundFromCreateAccount.getString("cacheEmail","");
                                        String foundPersonDept =fcacheForFoundFromCreateAccount.getString("cacheDepartment","");
                                        String foundPersonSec = fcacheForFoundFromCreateAccount.getString("cacheSection","");
                                        Log.d("inside if from createacc","pass");
                                        String fLostEmail = registerSnapshot.child("email").getValue(String.class);
                                        sendEmailFound(foundPersonRegId, foundPersonEmail, foundPersonDept, foundPersonSec, fLostEmail,imageURL);
                                    }
                                }
                                Log.d("outside if","not pass");}}}}}
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    private void fmatchItemsBag(String fbrandname, String fmodelname,
                                  String fcolorname, String funiquefeature,
                                  String fdatelost, String fselectedplace,String fselectedBag,
                                String imageURL) {
        fcompareRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Log.d("inside match function","pass");
                for (DataSnapshot registerSnapshot : snapshot.getChildren()) {
                    String registerid = registerSnapshot.getKey();
                    Log.d("for1",registerid);
                    boolean found = registerSnapshot.hasChild("lost");
                    if (found) {
                        Log.d("Boolean","True");
                        for (DataSnapshot foundSnapshot : registerSnapshot.child("lost").getChildren()) {
                            String itemName = foundSnapshot.getKey();
                            Log.d("for2", itemName);
                            if (itemName.equals(fselectedValue)) {
                                Log.d("item","equals");
                                String fBRAND = foundSnapshot.child("Brand Name").getValue(String.class);
                                String fMODEL = foundSnapshot.child("Model Name").getValue(String.class);
                                String fCOLOR = foundSnapshot.child("Color").getValue(String.class);
                                String fUNIQUE = foundSnapshot.child("Uniqueness").getValue(String.class);
                                String fPLACE = foundSnapshot.child("Location").getValue(String.class);
                                String fDATE = foundSnapshot.child("Date").getValue(String.class);
                                String fBAGTYPE = foundSnapshot.child("Bag Type").getValue(String.class);

                                if (fBRAND.equals(fbrandname) && fMODEL.equals(fmodelname)
                                        && fCOLOR.equals(fcolorname) && fUNIQUE.equals(funiquefeature)
                                        && fPLACE.equals(fselectedplace) && fBAGTYPE.equals(fselectedBag)
                                        && fDATE.equals(fdatelost)) {
                                    ffromLoginOrCreateAccount = getIntent().getStringExtra("MyAccountActivity");
                                    if (ffromLoginOrCreateAccount.equals("LoginActivity") || ffromLoginOrCreateAccount.equals(" ")) {

                                        fcacheForFoundFromLogin = getSharedPreferences("MyPreferencesFromLogin", MODE_PRIVATE);
                                        String foundPersonRegId = fcacheForFoundFromLogin.getString("useridfromlogin", "");
                                        String foundPersonEmail = fcacheForFoundFromLogin.getString("emailfromlogin", "");
                                        String foundPersonDept = fcacheForFoundFromLogin.getString("deptfromlogin", "");
                                        String foundPersonSec = fcacheForFoundFromLogin.getString("sectionfromlogin", "");
                                        Log.d("inside if from login", "pass");
                                        Log.d("image", imageURL);
                                        String fLostEmail = registerSnapshot.child("email").getValue(String.class);
                                        sendEmailFound(foundPersonRegId, foundPersonEmail, foundPersonDept, foundPersonSec, fLostEmail, imageURL);
                                    }
                                    else{
                                        fcacheForFoundFromCreateAccount = getSharedPreferences("MyPreferencesFromCreateAccount",MODE_PRIVATE);
                                        String foundPersonRegId = fcacheForFoundFromCreateAccount.getString("cacheUserID","");
                                        String foundPersonEmail = fcacheForFoundFromCreateAccount.getString("cacheEmail","");
                                        String foundPersonDept =fcacheForFoundFromCreateAccount.getString("cacheDepartment","");
                                        String foundPersonSec = fcacheForFoundFromCreateAccount.getString("cacheSection","");
                                        String foundImageLink = foundSnapshot.child("Imageurl").getValue(String.class);
                                        Log.d("inside if from createacc","pass");
                                        String fLostEmail = registerSnapshot.child("email").getValue(String.class);
                                        sendEmailFound(foundPersonRegId, foundPersonEmail, foundPersonDept, foundPersonSec, fLostEmail,imageURL);
                                    }
                                }
                                Log.d("outside if","not pass");}}}}}
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    private void fmatchItemsPurse(String fbrandname,
                                  String fcolorname, String funiquefeature,
                                  String fdatelost, String fselectedplace, String imageURL) {
        fcompareRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Log.d("inside match function","pass");
                for (DataSnapshot registerSnapshot : snapshot.getChildren()) {
                    String registerid = registerSnapshot.getKey();
                    Log.d("for1",registerid);
                    boolean found = registerSnapshot.hasChild("lost");
                    if (found) {
                        Log.d("Boolean","True");
                        for (DataSnapshot foundSnapshot : registerSnapshot.child("lost").getChildren()) {
                            String itemName = foundSnapshot.getKey();
                            Log.d("for2", itemName);
                            if (itemName.equals(fselectedValue)) {
                                Log.d("item","equals");
                                String fBRAND = foundSnapshot.child("Brand Name").getValue(String.class);
                                String fCOLOR = foundSnapshot.child("Color").getValue(String.class);
                                String fUNIQUE = foundSnapshot.child("Uniqueness").getValue(String.class);
                                String fPLACE = foundSnapshot.child("Location").getValue(String.class);
                                String fDATE = foundSnapshot.child("Date").getValue(String.class);

                                if (fBRAND.equals(fbrandname) && fCOLOR.equals(fcolorname) && fUNIQUE.equals(funiquefeature)
                                        && fPLACE.equals(fselectedplace)
                                        && fDATE.equals(fdatelost)) {
                                    ffromLoginOrCreateAccount = getIntent().getStringExtra("MyAccountActivity");
                                    if (ffromLoginOrCreateAccount.equals("LoginActivity") || ffromLoginOrCreateAccount.equals(" ")) {

                                        fcacheForFoundFromLogin = getSharedPreferences("MyPreferencesFromLogin", MODE_PRIVATE);
                                        String foundPersonRegId = fcacheForFoundFromLogin.getString("useridfromlogin", "");
                                        String foundPersonEmail = fcacheForFoundFromLogin.getString("emailfromlogin", "");
                                        String foundPersonDept = fcacheForFoundFromLogin.getString("deptfromlogin", "");
                                        String foundPersonSec = fcacheForFoundFromLogin.getString("sectionfromlogin", "");
                                        Log.d("inside if from login", "pass");
                                        Log.d("image", imageURL);
                                        String fLostEmail = registerSnapshot.child("email").getValue(String.class);
                                        sendEmailFound(foundPersonRegId, foundPersonEmail, foundPersonDept, foundPersonSec, fLostEmail, imageURL);
                                    }
                                    else{
                                        fcacheForFoundFromCreateAccount = getSharedPreferences("MyPreferencesFromCreateAccount",MODE_PRIVATE);
                                        String foundPersonRegId = fcacheForFoundFromCreateAccount.getString("cacheUserID","");
                                        String foundPersonEmail = fcacheForFoundFromCreateAccount.getString("cacheEmail","");
                                        String foundPersonDept =fcacheForFoundFromCreateAccount.getString("cacheDepartment","");
                                        String foundPersonSec = fcacheForFoundFromCreateAccount.getString("cacheSection","");
                                        String foundImageLink = foundSnapshot.child("Imageurl").getValue(String.class);
                                        Log.d("inside if from createacc","pass");
                                        String fLostEmail = registerSnapshot.child("email").getValue(String.class);
                                        sendEmailFound(foundPersonRegId, foundPersonEmail, foundPersonDept, foundPersonSec, fLostEmail,imageURL);
                                    }
                                }
                                Log.d("outside if","not pass");}}}}}
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    private void fmatchItemsHeadphone(String fbrandname, String fmodelname,
                                  String fcolorname, String funiquefeature,
                                  String fdatelost, String fselectedheadphone,String fselectedplace, String imageURL) {
        fcompareRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Log.d("inside match function","pass");
                for (DataSnapshot registerSnapshot : snapshot.getChildren()) {
                    String registerid = registerSnapshot.getKey();
                    Log.d("for1",registerid);
                    boolean found = registerSnapshot.hasChild("lost");
                    if (found) {
                        Log.d("Boolean","True");
                        for (DataSnapshot foundSnapshot : registerSnapshot.child("lost").getChildren()) {
                            String itemName = foundSnapshot.getKey();
                            Log.d("for2", itemName);
                            if (itemName.equals(fselectedValue)) {
                                Log.d("item","equals");
                                String fBRAND = foundSnapshot.child("Brand Name").getValue(String.class);
                                String fMODEL = foundSnapshot.child("Model Name").getValue(String.class);
                                String fCOLOR = foundSnapshot.child("Color").getValue(String.class);
                                String fUNIQUE = foundSnapshot.child("Uniqueness").getValue(String.class);
                                String fPLACE = foundSnapshot.child("Location").getValue(String.class);
                                String fDATE = foundSnapshot.child("Date").getValue(String.class);
                                String fHEADTYPE = foundSnapshot.child("Headphone Type").getValue(String.class);

                                if (fBRAND.equals(fbrandname) && fMODEL.equals(fmodelname)
                                        && fCOLOR.equals(fcolorname) && fUNIQUE.equals(funiquefeature)
                                        && fPLACE.equals(fselectedplace) && fHEADTYPE.equals(fselectedheadphone)
                                        && fDATE.equals(fdatelost)) {
                                    ffromLoginOrCreateAccount = getIntent().getStringExtra("MyAccountActivity");
                                    if (ffromLoginOrCreateAccount.equals("LoginActivity") || ffromLoginOrCreateAccount.equals(" ")) {

                                        fcacheForFoundFromLogin = getSharedPreferences("MyPreferencesFromLogin", MODE_PRIVATE);
                                        String foundPersonRegId = fcacheForFoundFromLogin.getString("useridfromlogin", "");
                                        String foundPersonEmail = fcacheForFoundFromLogin.getString("emailfromlogin", "");
                                        String foundPersonDept = fcacheForFoundFromLogin.getString("deptfromlogin", "");
                                        String foundPersonSec = fcacheForFoundFromLogin.getString("sectionfromlogin", "");
                                        Log.d("inside if from login", "pass");
                                        Log.d("image", imageURL);
                                        String fLostEmail = registerSnapshot.child("email").getValue(String.class);
                                        sendEmailFound(foundPersonRegId, foundPersonEmail, foundPersonDept, foundPersonSec, fLostEmail, imageURL);
                                    }
                                    else{
                                        fcacheForFoundFromCreateAccount = getSharedPreferences("MyPreferencesFromCreateAccount",MODE_PRIVATE);
                                        String foundPersonRegId = fcacheForFoundFromCreateAccount.getString("cacheUserID","");
                                        String foundPersonEmail = fcacheForFoundFromCreateAccount.getString("cacheEmail","");
                                        String foundPersonDept =fcacheForFoundFromCreateAccount.getString("cacheDepartment","");
                                        String foundPersonSec = fcacheForFoundFromCreateAccount.getString("cacheSection","");
                                        String foundImageLink = foundSnapshot.child("Imageurl").getValue(String.class);
                                        Log.d("inside if from createacc","pass");
                                        String fLostEmail = registerSnapshot.child("email").getValue(String.class);
                                        sendEmailFound(foundPersonRegId, foundPersonEmail, foundPersonDept, foundPersonSec, fLostEmail,imageURL);
                                    }
                                }
                                Log.d("outside if","not pass");}}}}}
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }


    private void sendEmailFound(String foundPersonRegId, String foundPersonEmail,
                                String foundPersonDept, String foundPersonSec,
                                String flostPersonEmail, String foundImageLink) {
        String Subject = "LostAndFound";
        String Message = "Hey, it's from Lost and Found. It seems like that I have found the thing you've lost. See below for the founded person details:\nRegister ID: "+
                foundPersonRegId+"\nDepartment: "+foundPersonDept+"\nSection: "+foundPersonSec+"\nEmail: "+foundPersonEmail+
                "\nImage Link: "+foundImageLink+"\n\nIGNORE IF THIS IS NOT YOUR THING!!!";
        JavaMailAPI fjavaMailAPI = new JavaMailAPI(this, flostPersonEmail, Subject, Message);
        fjavaMailAPI.execute();

    }
//    private void sendEmailNotFound() {
//        String subjectNotFound = "LostAndFound";
//        String messageNotFound = "Hello, it's from Lost and Found. Sorry to inform that it seems " +
//                "like there is no match with your lost thing details as of now! But don't lose hope you will be emailed if someone found " +
//                "your thing and post it in the application! \nThank You.";
//        JavaMailAPI javaMailAPINotFound = new JavaMailAPI(this, flostPersonEmail, subjectNotFound,messageNotFound);
//        javaMailAPINotFound.execute();
//    }

    private void fpostToDBwatch (String registerNum){
            fbrandname = fbrandNameEditText.getText().toString();
            fmodelname = fmodelNameEditText.getText().toString();
            fcolorname = fcolorEditText.getText().toString();
            funiquefeature = funiqueEditText.getText().toString();
            fdatelost = fdateOfLostEditText.getText().toString();

            if (fbrandname.isEmpty() || fmodelname.isEmpty() || fcolorname.isEmpty() || fselectedplace.isEmpty() ||
                    funiquefeature.isEmpty() || fdatelost.isEmpty() || fselectedWatch.isEmpty() || fimageview.getDrawable() == null) {
                Toast.makeText(FoundForm.this, "Enter the blank fields! ", Toast.LENGTH_SHORT).show();
            } else {
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
                        fref.child("Imageurl").setValue(imageURL);
                    }

                    public void onCancelled(@NonNull DatabaseError error) {
                    }
                });
                fintcall();
            }
            fmatchItemsWatch(fbrandname,fmodelname,fcolorname,funiquefeature,fdatelost,fselectedWatch,fselectedplace,imageURL);
        }
        private void fpostToDBbag (String registerNum){
            fbrandname = fbrandNameEditText.getText().toString();
            fmodelname = fmodelNameEditText.getText().toString();
            fcolorname = fcolorEditText.getText().toString();
            funiquefeature = funiqueEditText.getText().toString();
            fdatelost = fdateOfLostEditText.getText().toString();

            if (fbrandname.isEmpty() || fmodelname.isEmpty() || fcolorname.isEmpty() || funiquefeature.isEmpty() ||
                    fdatelost.isEmpty() || fselectedplace.isEmpty() || fselectedBag.isEmpty() || fimageview.getDrawable() == null) {
                Toast.makeText(FoundForm.this, "Enter the blank fields! ", Toast.LENGTH_SHORT).show();
            } else {
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
                        fref.child("Imageurl").setValue(imageURL);
                    }

                    public void onCancelled(@NonNull DatabaseError error) {
                    }
                });
                fintcall();
            }
            fmatchItemsBag(fbrandname,fmodelname,fcolorname,funiquefeature,fdatelost,fselectedBag,fselectedplace,imageURL);

        }

        private void fpostToDBpurse (String registerNum){
            fbrandname = fbrandNameEditText.getText().toString();
            fcolorname = fcolorEditText.getText().toString();
            funiquefeature = funiqueEditText.getText().toString();
            fdatelost = fdateOfLostEditText.getText().toString();

            if (fbrandname.isEmpty() || fcolorname.isEmpty() ||
                    funiquefeature.isEmpty() || fdatelost.isEmpty() || fselectedplace.isEmpty() || fimageview.getDrawable() == null) {
                Toast.makeText(FoundForm.this, "Enter the blank fields! ", Toast.LENGTH_SHORT).show();
            } else {
                founddb.child("users").child(registerNum).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        DatabaseReference fref = founddb.child("users").child(registerNum).child("found").child(fselectedValue);
                        fref.child("Brand Name").setValue(fbrandname);
                        fref.child("Color").setValue(fcolorname);
                        fref.child("Uniqueness").setValue(funiquefeature);
                        fref.child("Date").setValue(fdatelost);
                        fref.child("Location").setValue(fselectedplace);
                        fref.child("Imageurl").setValue(imageURL);
                    }

                    public void onCancelled(@NonNull DatabaseError error) {
                    }
                });
                fintcall();
            }
            fmatchItemsPurse(fbrandname,fcolorname,funiquefeature,fdatelost,fselectedplace,imageURL);
        }
        private void fpostToDBhead (String registerNum){
            fbrandname = fbrandNameEditText.getText().toString();
            fmodelname = fmodelNameEditText.getText().toString();
            fcolorname = fcolorEditText.getText().toString();
            funiquefeature = funiqueEditText.getText().toString();
            fdatelost = fdateOfLostEditText.getText().toString();

            if (fbrandname.isEmpty() || fmodelname.isEmpty() || fcolorname.isEmpty() ||
                    funiquefeature.isEmpty() || fdatelost.isEmpty() || fselectedplace.isEmpty() || fselectedheadphone.isEmpty() || fimageview.getDrawable() == null) {
                Toast.makeText(FoundForm.this, "Enter the blank fields! ", Toast.LENGTH_SHORT).show();
            } else {
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
                        fref.child("Imageurl").setValue(imageURL);
                    }

                    public void onCancelled(@NonNull DatabaseError error) {
                    }
                });
                fintcall();
            }
            fmatchItemsHeadphone(fbrandname,fmodelname,fcolorname,funiquefeature,fdatelost,fselectedheadphone,fselectedplace,imageURL);
        }
        private void fintcall () {
            Intent tosuccesspost = new Intent(FoundForm.this, SuccessfulPost.class);
            startActivity(tosuccesspost);
        }
    }

