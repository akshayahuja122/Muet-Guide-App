package com.bukhari.fyp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.Calendar;
import java.util.regex.Pattern;

public class SmartCardActivity1 extends AppCompatActivity {

    private RadioButton rdbNew , rdbRenew;
    private RadioButton rdbMale, rdbFenale;
    private RadioButton rdbUndergraduate , rdbPostgraduate , rdgPHD;
    private EditText edtName , edtFName , edtNIC , edtEmail , edtPhone , edtRollNum , edtConName , edtConPhone;
    private TextView textViewDOB;
    private Spinner spinnerProvinces , spinnerBloodGroup , spinnerDepartment;

    private Button btnDOB , btnNext;
    private ProgressBar progressBar;

    // For Date of Birth
    private DatePickerDialog.OnDateSetListener mDateSetListener;
    // wE WILL PASS THE DATA TO THE NEXT ACTIVITY
    private String cardType = ""; public static final String CARDTYPE = "CARDTYPE";
    private String fullName = ""; public static final String FULLNAME = "FULLNAME";
    private String fatherName = ""; public static final String FATHERNAME = "FATHERNAME";
    private String nic = "";  public static final String NIC = "NIC";
    private String gender = ""; public static final String GENDER = "GENDER";
    private String dob = ""; public static final String DOB = "DOB";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_smart_card);
        // Assigning the ids or initializing the controls
        initialize();

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Smart Card Registration");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        // Dialog When Date of Birth TextView is clicked
        btnDOB.setOnClickListener(view -> {
            Calendar cal = Calendar.getInstance();
            int year = cal.get(Calendar.YEAR);
            int month = cal.get(Calendar.MONTH);
            int day = cal.get(Calendar.DAY_OF_MONTH);

            DatePickerDialog dialog = new DatePickerDialog(
                    SmartCardActivity1.this,
                    android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                    mDateSetListener,
                    year,month,day);
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            dialog.show();
        });

        mDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                month = month + 1;
                String date = month + "/" + day + "/" + year;
                textViewDOB.setText(date);
            }
        };

        btnNext.setOnClickListener(e->{
            if(edtName.getText().toString().isEmpty()){
                edtName.setError("Name is required!");
                edtName.requestFocus();
                return;
            }

            if(edtFName.getText().toString().isEmpty()){
                edtFName.setError("Father's Name is required!");
                edtFName.requestFocus();
                return;
            }

            if(edtNIC.getText().toString().isEmpty()){
                edtNIC.setError("NIC is required!");
                edtNIC.requestFocus();
                return;
            }

            String cnic = edtNIC.getText().toString().trim();
            if(!Pattern.matches("[0-9]{13}",cnic )){
                edtNIC.setError("Please provide a valid CNIC!");
                edtNIC.requestFocus();
                return;
            }

            if(!Character.isDigit(textViewDOB.getText().toString().charAt(0))){
                textViewDOB.setText("Please Select Date of Birth");
                return;
            }


            fullName = edtName.getText().toString().trim();
            fatherName = edtFName.getText().toString().trim();
            nic = edtNIC.getText().toString().trim();
            dob = textViewDOB.getText().toString().trim();


            if(rdbNew.isChecked()){
                cardType = "New";
            }else{
                cardType = "Renew";
            }

            if(rdbMale.isChecked()){
                gender = "Male";
            }else{
                gender = "Female";
            }

//            progressBar.getProgressDrawable().setColorFilter(
//                    getResources().getColor(R.color.colorOrange), android.graphics.PorterDuff.Mode.SRC_IN);
//            progressBar.setProgress(33);

            Intent intent = new Intent(SmartCardActivity1.this,SmartCardActivity2.class);

            intent.putExtra(FULLNAME,fullName);
            intent.putExtra(FATHERNAME,fatherName);
            intent.putExtra(NIC,nic);
            intent.putExtra(DOB,dob);
            intent.putExtra(GENDER,gender);
            intent.putExtra(CARDTYPE,cardType);

            startActivity(intent);

        });

    }

    private void initialize(){
        rdbNew = findViewById(R.id.rdbNew);
        rdbRenew = findViewById(R.id.rdbRenew);
        rdbMale  = findViewById(R.id.rdbMale);
        rdbFenale = findViewById(R.id.rdbFemale);

//        rdbUndergraduate = findViewById(R.id.rdbUndergraduate);
//        rdbPostgraduate = findViewById(R.id.rdbPostgraduate);
//        rdgPHD = findViewById(R.id.rdgPHD);

        edtName = findViewById(R.id.edtName);
        edtFName = findViewById(R.id.edtFName);
        edtNIC = findViewById(R.id.edtNIC);
        edtEmail = findViewById(R.id.edtEmail);
        edtPhone = findViewById(R.id.edtPhone);

        edtRollNum = findViewById(R.id.edtRollNum);
//        edtConName = findViewById(R.id.edtConName);
//        edtConPhone = findViewById(R.id.edtConPhone);
//
//        spinnerProvinces = findViewById(R.id.spinnerProvinces);
//        spinnerBloodGroup = findViewById(R.id.spinnerBloodGroup);
//        spinnerDepartment = findViewById(R.id.spinnerDepartment);


        textViewDOB = findViewById(R.id.textViewDOB);

        btnDOB = findViewById(R.id.btnDOB);
        btnNext = findViewById(R.id.btnNext);
        progressBar = findViewById(R.id.progressbar);

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
