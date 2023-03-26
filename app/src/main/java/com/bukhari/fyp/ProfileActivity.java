package com.bukhari.fyp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.GridLayout;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;

import static android.view.View.GONE;


public class ProfileActivity extends AppCompatActivity {

    private String userEmail= "";
    private String userType = "";
    private TextView textViewUser;

    private GridLayout gridLayout;

    private CardView cardAdmission;
    private CardView cardSmartCard;
    private CardView cardBookChallan;
    private CardView cardPoint;
    private CardView cardHostel;
    private static final String TAG = "Profile : ";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        gridLayout = findViewById(R.id.gridLayout);

        cardAdmission = findViewById(R.id.cardAdmission);
        cardSmartCard = findViewById(R.id.cardSmartCard);
        cardBookChallan = findViewById(R.id.cardBookChallan);

        cardPoint = findViewById(R.id.cardPoint);
        cardHostel = findViewById(R.id.cardHostel);


        textViewUser = findViewById(R.id.user);
        userEmail = getIntent().getStringExtra("LoginEmail");
        userType = getIntent().getStringExtra("user");
        Log.d("TAG","User Type :"+userType);
//      getSupportActionBar().setTitle(userEmail);


        if(userType.equalsIgnoreCase("Fresh Student")){
            gridLayout.removeView(cardBookChallan);
            gridLayout.removeView(cardHostel);
//            cardBookChallan.setVisibility(View.GONE);
//            cardHostel.setVisibility(View.GONE);
        }else{
            gridLayout.removeView(cardAdmission);
//            cardAdmission.setVisibility(View.GONE);
//            gridLayout.removeView(cardBookChallan);

        }


        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
//      getSupportActionBar().setTitle(userEmail);
        textViewUser.setText(userEmail);
        getSupportActionBar().setLogo(R.drawable.ic_person);


        cardAdmission.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Admission
                startActivity(new Intent(ProfileActivity.this,AdmissionActivity.class));
            }
        });

        cardSmartCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Smart Card
                startActivity(new Intent(ProfileActivity.this, SmartCardActivity1.class));
            }
        });



        cardBookChallan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Book Challan
                startActivity(new Intent(ProfileActivity.this,BookChallanActivity.class));
            }
        });

        // using Lambda expression.
        cardPoint.setOnClickListener(e->startActivity(new Intent(ProfileActivity.this,PointActivity.class)));

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.logout:
                FirebaseAuth.getInstance().signOut();
                finish();
                startActivity(new Intent(ProfileActivity.this,MainActivity.class));
                break;
        }
        return true;
    }
}

