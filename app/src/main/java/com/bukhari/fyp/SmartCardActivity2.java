package com.bukhari.fyp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.Spinner;

import java.util.regex.Pattern;

public class SmartCardActivity2 extends AppCompatActivity {


    private EditText edtEmail , edtPhone , edtRollNum;
    private Spinner spinnerProvinces , spinnerBloodGroup, spinnerDepartment;
    private RadioButton rdbUndergraduate , rdbPostgraduate , rdbPHD;

    private ProgressBar progressbar;

    // The data From Previous Activity
    private String cardType = "";
    private String fullName = "";
    private String fatherName = "";
    private String nic = "";
    private String gender = "";
    private String dob = "";

    // Data  of this Activity
    private String email = "";      public static final String EMAIL = "EMAIL";
    private String phone ="";       public static final String PHONE = "PHONE";
    private String rollnumber = ""; public static final String ROLLNUM = "ROLLNUM";
    private String program = "";    public static final String PROGRAM = "PROGRAM";
    private String province = "";   public static final String PROVINCE = "PROVINCE";
    private String blood = "";      public static final String BLODD = "BLOOD";
    private String department = ""; public static final String DEPARTMENT = "DEPARTMENT";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_smart_card2);
        initialize();

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Smart Card Registration");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        Intent intent = getIntent();
        cardType = intent.getStringExtra(SmartCardActivity1.CARDTYPE);
        fullName = intent.getStringExtra(SmartCardActivity1.FULLNAME);
        fatherName = intent.getStringExtra(SmartCardActivity1.FATHERNAME);
        nic = intent.getStringExtra(SmartCardActivity1.NIC);
        gender = intent.getStringExtra(SmartCardActivity1.GENDER);
        dob = intent.getStringExtra(SmartCardActivity1.DOB);






    }

    private void initialize(){
        edtEmail = findViewById(R.id.edtEmail);
        edtPhone = findViewById(R.id.edtPhone);
        edtRollNum = findViewById(R.id.edtRollNum);
        spinnerProvinces = findViewById(R.id.spinnerProvinces);
        spinnerBloodGroup = findViewById(R.id.spinnerBloodGroup);
        spinnerDepartment = findViewById(R.id.spinnerDepartment);
        rdbUndergraduate = findViewById(R.id.rdbUndergraduate);
        rdbPostgraduate = findViewById(R.id.rdbPostgraduate);
        rdbPHD = findViewById(R.id.rdbPHD);


        progressbar = findViewById(R.id.progressbar);

        progressbar.getProgressDrawable().setColorFilter(
                getResources().getColor(R.color.colorOrange), android.graphics.PorterDuff.Mode.SRC_IN);
        progressbar.setProgress(33);
    }

    public void onNext(View view) {
        if(edtEmail.getText().toString().trim().isEmpty()){
            edtEmail.setError("Email is required!");
            edtEmail.requestFocus();
            return;
        }
        if(edtPhone.getText().toString().trim().isEmpty()){
            edtPhone.setError("Phone is required!");
            edtPhone.requestFocus();
            return;
        }

        if(edtRollNum.getText().toString().isEmpty()){
            edtRollNum.setError("Roll Number is required!");
            edtRollNum.requestFocus();
            return;
        }

        email = edtEmail.getText().toString().trim();
        // If the Email is not Valid
        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            edtEmail.setError("Please provide a valid Email!");
            edtEmail.requestFocus();
            return;
        }

        // If the Phone is not Valid
        phone = edtPhone.getText().toString().trim();
        if(!Pattern.matches("[0-9]{11}",phone )){
            edtPhone.setError("Please provide a valid Phone Number!");
            edtPhone.requestFocus();
            return;
        }
        rollnumber = edtRollNum.getText().toString().trim();

        if(rdbPostgraduate.isChecked()){
            program = rdbPostgraduate.getText().toString();
        }else if(rdbUndergraduate.isChecked()){
            program = rdbUndergraduate.getText().toString();
        }else{
            program = rdbPHD.getText().toString();
        }


        department = spinnerDepartment.getSelectedItem().toString();
        blood = spinnerBloodGroup.getSelectedItem().toString();
        province = spinnerProvinces.getSelectedItem().toString();

//        Now WE send the Data to the Third Activity and there we  save the data into the Firebase Database
        Intent intent = new Intent(SmartCardActivity2.this, SmartCardActivity3.class);

        intent.putExtra(SmartCardActivity1.FULLNAME,fullName);
        intent.putExtra(SmartCardActivity1.FATHERNAME,fatherName);
        intent.putExtra(SmartCardActivity1.NIC,nic);
        intent.putExtra(SmartCardActivity1.DOB,dob);
        intent.putExtra(SmartCardActivity1.GENDER,gender);
        intent.putExtra(SmartCardActivity1.CARDTYPE,cardType);

        // This Activity Data

        intent.putExtra(EMAIL,email);
        intent.putExtra(PHONE,phone);
        intent.putExtra(ROLLNUM,rollnumber);
        intent.putExtra(PROGRAM,program);
        intent.putExtra(PROVINCE,province);
        intent.putExtra(BLODD,blood);
        intent.putExtra(DEPARTMENT,department);

        startActivity(intent);




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
}
