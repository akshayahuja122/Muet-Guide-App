package com.bukhari.fyp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

public class SmartCardActivity3 extends AppCompatActivity {

    // The data From Previous Activity
    private String cardType = "";
    private String fullName = "";
    private String fatherName = "";
    private String nic = "";
    private String gender = "";
    private String dob = "";

    // Data  of this Activity
    private String email = "";
    private String phone ="";
    private String rollnumber = "";
    private String program = "";
    private String province = "";
    private String blood = "";
    private String department = "";

    // This Activity data

    private String conName = "";
    private String conPhone = "";

    private ProgressBar progressBar;
    private ProgressBar circularProgress;

    private EditText edtName;
    private EditText edtPhone;


    // Firebase Stuff
    private FirebaseAuth mAuth;;
    private FirebaseDatabase mDatabase;
    private DatabaseReference mRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_smart_card3);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Smart Card Registration");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        progressBar = findViewById(R.id.progressbar);
        progressBar.getProgressDrawable().setColorFilter(
                getResources().getColor(R.color.colorOrange), android.graphics.PorterDuff.Mode.SRC_IN);
        progressBar.setProgress(66);

        circularProgress = findViewById(R.id.progress_circular);

        edtName = findViewById(R.id.edtConName);
        edtPhone = findViewById(R.id.edtConPhone);


        Intent intent = getIntent();

        cardType = intent.getStringExtra(SmartCardActivity1.CARDTYPE);
        fullName = intent.getStringExtra(SmartCardActivity1.FULLNAME);
        fatherName = intent.getStringExtra(SmartCardActivity1.FATHERNAME);
        nic = intent.getStringExtra(SmartCardActivity1.NIC);
        gender = intent.getStringExtra(SmartCardActivity1.GENDER);
        dob = intent.getStringExtra(SmartCardActivity1.DOB);


        email = intent.getStringExtra(SmartCardActivity2.EMAIL);
        phone = intent.getStringExtra(SmartCardActivity2.PHONE);
        rollnumber = intent.getStringExtra(SmartCardActivity2.ROLLNUM);
        program = intent.getStringExtra(SmartCardActivity2.PROGRAM);
        province = intent.getStringExtra(SmartCardActivity2.PROVINCE);
        blood = intent.getStringExtra(SmartCardActivity2.BLODD);
        department = intent.getStringExtra(SmartCardActivity2.DEPARTMENT);

        //
        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance();
        mRef = mDatabase.getReference("smart card"); // This will create a child Node with the name users


    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return false;
    }

    public void onSubmit(View view) {
        if(edtName.getText().toString().trim().isEmpty()){
            edtName.setError("Name is required!");
            edtName.requestFocus();
            return;
        }
        if(edtPhone.getText().toString().trim().isEmpty()){
            edtPhone.setError("Phone is required!");
            edtPhone.requestFocus();
            return;
        }

        // If the Phone is not Valid
        conPhone = edtPhone.getText().toString().trim();
        if(!Pattern.matches("[0-9]{11}",conPhone )){
            edtPhone.setError("Please provide a valid Phone Number!");
            edtPhone.requestFocus();
            return;
        }

        progressBar.setProgress(100);

        if(!cardType.equals("Renew")){
            Log.d("TAG","It is not Renew");
            //  Check for the Data duplication
//            Map <String, Object>  data = data();


//            if(data.get("Roll Number").equals(rollnumber)){
//                Log.d("TAG","The Roll Number Already Exists");
//                Toast.makeText(this,"You have Already Applied For the Smart Card",Toast.LENGTH_LONG).show();
//                return;
//            }

        }


        String key = mRef.push().getKey();
        Map<String , Object> data = new HashMap<>();
        data.put("Card Type",cardType);
        data.put("Full Name",fullName);
        data.put("Father Name",fatherName);
        data.put("NIC",nic);

        data.put("Gender",gender);
        data.put("Date of Birth",dob);
        data.put("Email",email);
        data.put("Phone",phone);
        data.put("Roll Number",rollnumber);
        data.put("Program",program);
        data.put("Province",province);
        data.put("Blodd",blood);
        data.put("Department",department);

        data.put("Emergency Contact Name",conName);
        data.put("Emergency Contact Phone",conPhone);

        mRef.child(key).setValue(data);


        Toast.makeText(SmartCardActivity3.this,
                "Data Submitted Successfully. You will get your Smart Card in a few Working Days",
                Toast.LENGTH_LONG).show();


    }

    public Map <String, Object>  data(){
        Map <String, Object> smartCardData = null;
        mRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot dataSnapshot : snapshot.getChildren()){
                    // All the Data is inserted into the Data Map
                    Map <String, Object> smartCardData = (Map<String, Object>) dataSnapshot.getValue();

                    Log.d("TAG","Full Name "+ smartCardData.get("Full Name"));
                    Log.d("TAG","Father Name "+ smartCardData.get("Father Name"));
                    Log.d("TAG","Roll Number "+smartCardData.get("Roll Number"));
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        return smartCardData;

    }
}
