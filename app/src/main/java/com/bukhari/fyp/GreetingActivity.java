package com.bukhari.fyp;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.TextView;

public class GreetingActivity extends AppCompatActivity {

    private static final String TAG = "Ac";
    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_greeting);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        textView = findViewById(R.id.textViewDepartment);
        String department = getIntent().getStringExtra("Dpt");
        String dpt = department.substring(0,department.indexOf(" E"));
        textView.setText(dpt+" Engineering");
    }
}
