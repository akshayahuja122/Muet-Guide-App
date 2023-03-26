package com.bukhari.fyp;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.appcompat.widget.ViewStubCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import java.util.ArrayList;

public class AdmissionActivity extends AppCompatActivity {


    public static final String TAG = "Admissionss";

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admission);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Admissions");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


//        getSupportActionBar().setSelectedNavigationItem(R.drawable.ic_back_icon);

        ArrayList<AdmissionCardData> list = new ArrayList<>();
        list.add(new AdmissionCardData(R.drawable.un_img,"Undergraduate","If you want to get Admission in Undergraduate!"));
        list.add(new AdmissionCardData(R.drawable.po_img,"Postgradate","If you want to get Admission in Postgraduate!"));
        list.add(new AdmissionCardData(R.drawable.dr_img,"PhD","If you want to get Admission in Doctor of Philosophy!"));


        mRecyclerView = findViewById(R.id.recylerView);
        mRecyclerView.setHasFixedSize(true); // This keeps the Size fixed and REcycles every Card
        mLayoutManager = new LinearLayoutManager(this);
        mAdapter = new AdmissionAdapter(list);

        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);

        mRecyclerView.addOnItemTouchListener(
                new RecyclerItemClickListener(this, mRecyclerView ,new RecyclerItemClickListener.OnItemClickListener() {
                    @Override public void onItemClick(View view, int position) {
                        switch (position){
                            case 0:
                                Intent intent = new Intent(AdmissionActivity.this,UndergraduateRequirementsActivity.class);
                                startActivity(intent);
                                break;
                            case 1:
                                break;
                            case 2:
                                break;
                        }
                    }

                    @Override public void onLongItemClick(View view, int position) {
                        // do whatever
                    }
                })
        );
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

