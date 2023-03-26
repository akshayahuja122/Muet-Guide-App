package com.bukhari.fyp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

public class RegisterActivity extends AppCompatActivity {

    private static final String TAG = "RegisterActivity";
    private EditText edtName;
    private EditText edtPhone;
    private EditText edtEmail;
    private EditText edtPass;
    private EditText edtConPass;
    private String studentType;

    RadioButton rdbFreshie;
    RadioButton rdbStudent;

    private FirebaseAuth mAuth;;
    private FirebaseDatabase mDatabase;
    private DatabaseReference mRef;

    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

//        getSupportActionBar().hide(); // To hide the Actionba
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        edtName = findViewById(R.id.edtName);
        edtPhone = findViewById(R.id.edtPhone);
        edtEmail = findViewById(R.id.edtEmail);
        edtPass =  findViewById(R.id.edtPass);
        edtConPass = findViewById(R.id.edtConPass);

        rdbFreshie = findViewById(R.id.rdbFreshie);
        rdbStudent = findViewById(R.id.rdbStudent);

        progressBar = findViewById(R.id.progressbar);

        mAuth = FirebaseAuth.getInstance();

        mDatabase = FirebaseDatabase.getInstance();
        mRef = mDatabase.getReference("users"); // This will create a child Node with the name users

    }

    public void onButtonClicked(View view) {
        Button btn = (Button)view;
        switch (btn.getId()){
            case R.id.btnLogin:
                // Going back to Login Activity
                finish();
                startActivity(new Intent(this,MainActivity.class));
                break;
            case R.id.btnSignup:
                signup();
                break;
        }
    }

    public void signup(){
        // Data Validation
        if(edtName.getText().toString().trim().isEmpty()){
            edtName.setError("Name is required!");
            edtName.requestFocus();
            return;
        }
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
        if(edtPass.getText().toString().trim().isEmpty()){
            edtPass.setError("Password is required!");
            edtPass.requestFocus();
            return;
        }
        if(edtPass.getText().toString().trim().length() <=6 ){
            edtPass.setError("Password should be more than 6 characters!");
            edtPass.requestFocus();
            return;
        }

        if(edtConPass.getText().toString().trim().isEmpty()){
            edtConPass.setError("Please Confirm the Password!");
            edtConPass.requestFocus();
            return;
        }

        String email = edtEmail.getText().toString().trim();
        // If the Email is not Valid
        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            edtEmail.setError("Please provide a valid Email!");
            edtEmail.requestFocus();
            return;
        }

        // If the Phone is not Valid
        String phone = edtPhone.getText().toString().trim();
        if(!Pattern.matches("[0-9]{11}",phone )){
            edtPhone.setError("Please provide a valid Phone Number!");
            edtPhone.requestFocus();
            return;
        }

        String pass = edtPass.getText().toString();
        String conpass = edtConPass.getText().toString();
        // If Password does not match
        if(!pass.equals(conpass)){
            edtConPass.setError("Password does not Match");
            edtConPass.requestFocus();
            return;
        }

        if(rdbFreshie.isSelected()){
            studentType = "Fresh Student";
        }else{
            studentType = "Student";
        }

        // Setting the visibility of the Progressbar after validation
        progressBar.setVisibility(View.VISIBLE);

        mAuth.createUserWithEmailAndPassword(email, pass)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        // Setting the visibility of the Progressbar gone after it is leaded
                        progressBar.setVisibility(View.GONE);

                        if (task.isSuccessful()) {
                            // iF THE USER HAS CREATED ACCOUNT SEND THE EMAIL VERIFICATION
                            mAuth.getCurrentUser().sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if(task.isSuccessful()){


                                        String name = edtName.getText().toString().trim();
                                        String email = edtEmail.getText().toString().trim();
                                        String phone = edtPhone.getText().toString().trim();
                                        String pass = edtPass.getText().toString().trim();
                                        // Inserting the Data into this Key
                                        String key = mRef.push().getKey();

                                        // ONE WAY OF INSERTING THE DATA
//                                      mRef.child(key).child("Name").setValue(name);
//                                      mRef.child(key).child("Email").setValue(email);
//                                      mRef.child(key).child("Phone").setValue(phone);

//                                      NOTE: Professionaly we don't store the exact String for the Password so here we also dont
//                                      mRef.child(key).child("Password").setValue(pass);

                                        // OTHER WAY OF INSERTING THE DATA THIS IS MORE APPROPRIATE AND PROFESSIONAL
                                        Map <String , Object> data = new HashMap<>();
                                        data.put("Name",name);
                                        data.put("Email",email);
                                        data.put("Phone",phone);
                                        data.put("Student Type",studentType);
                                        mRef.child(key).setValue(data);





                                        edtName.setText("");
                                        edtEmail.setText("");
                                        edtPhone.setText("");
                                        edtPass.setText("");
                                        edtConPass.setText("");

                                        Toast.makeText(RegisterActivity.this,
                                                "Account Created Successfully! Please check your email for verification!",
                                                Toast.LENGTH_LONG).show();

                                        Intent intent = new Intent(RegisterActivity.this,MainActivity.class);
                                        // If the user clicks back the stack should be empty so that he can not back
                                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                        startActivity(intent);

                                    }else{
                                        Toast.makeText(RegisterActivity.this,
                                                task.getException().getMessage(),
                                                Toast.LENGTH_LONG).show();
                                    }
                                }
                            });



                            // Sign in success, update UI with the signed-in user's information

//                          FirebaseUser user = mAuth.getCurrentUser();
//                          updateUI(user);


                        } else {
                            // If sign in fails, display a message to the user.

                            // If the User has already created the Account
                            if(task.getException() instanceof FirebaseAuthUserCollisionException){
                                Toast.makeText(getApplicationContext(), "You have Already Registered With this Email",Toast.LENGTH_SHORT).show();
                            }else{
                                Toast.makeText(RegisterActivity.this,"You got an Error"+task.getException(),Toast.LENGTH_LONG).show();
                            }

                            Log.w(TAG, "Some Error Occured ", task.getException());

//                            updateUI(null);
                        }


                    }
                });


    }

}
