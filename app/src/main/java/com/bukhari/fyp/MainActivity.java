package com.bukhari.fyp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {

    private EditText edtemail;
    private EditText edtpass;

    private FirebaseAuth mAuth;
    private ProgressBar progressBar;
    private Button btnLogin;
    private Button btnRegister;
    private Button btnForgotPass;

    private Spinner spinnerUserType;
    private String userType [] = {"Fresh Student","Student"};
    private String user = "Fresh Student";
    private String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//      getSupportActionBar().hide(); // To hide the Actionbar
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        edtemail = findViewById(R.id.edtEmail);
        edtpass = findViewById(R.id.edtPass);
        progressBar = findViewById(R.id.progressbar);

        btnLogin = findViewById(R.id.btnLogin);
        btnRegister = findViewById(R.id.btnRegister);
        // For underlining the Button
        btnForgotPass = findViewById(R.id.btnForgotPass);
        btnForgotPass.setPaintFlags(btnForgotPass.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);

        spinnerUserType = findViewById(R.id.spinnerUserType);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, userType);
        spinnerUserType.setAdapter(adapter);
        spinnerUserType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                int index = spinnerUserType.getSelectedItemPosition();

                user = userType[index];


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        btnForgotPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,ForgotPasswordActivity.class));
            }
        });

        mAuth = FirebaseAuth.getInstance();
    }


    public void onRegister(View view) {
        Intent intent = new Intent(MainActivity.this, RegisterActivity.class);
        if(intent != null){
            finish();
            startActivity(intent);
        }
    }

    public void onValidate(View view) {
        if((edtemail.getText().toString().trim().isEmpty() || edtpass.getText().toString().trim().isEmpty())){
            Toast.makeText(this,"Please Fill the Fields", Toast.LENGTH_SHORT).show();
        }else{
           userLogin();
        }
    }

    public void userLogin(){
        String email = edtemail.getText().toString().trim();
        String pass =  edtpass.getText().toString(); // Password can not be trimmed because it has to be perfect

        progressBar.setVisibility(View.VISIBLE);
        btnLogin.setClickable(false);
        btnRegister.setClickable(false);
        mAuth.signInWithEmailAndPassword(email,pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                progressBar.setVisibility(View.GONE);
                btnLogin.setClickable(true);
                btnRegister.setClickable(true);

                // Sign in success
                if (task.isSuccessful()) {
                    //IF THE EMAIL IS VERIFIED

                    if(mAuth.getCurrentUser().isEmailVerified()){
                        Intent intent = new Intent(MainActivity.this,ProfileActivity.class);
                        // If the user clicks back he should not go to Login screen but App is exited
                        intent.putExtra("LoginEmail",edtemail.getText().toString().trim());
                        intent.putExtra("user",user);
                        intent.getExtras().toString();
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

                        startActivity(intent);
                    }else{
                        Toast.makeText(MainActivity.this, "Please Verify your Email", Toast.LENGTH_SHORT).show();
                    }

                } else {
                    // If sign in fails, display a message to the user.
                    Toast.makeText(MainActivity.this,task.getException().getMessage(),Toast.LENGTH_LONG).show();

                }

            }
        });
    }


}
