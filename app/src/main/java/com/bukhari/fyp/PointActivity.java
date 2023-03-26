package com.bukhari.fyp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.Point;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.xml.sax.helpers.XMLReaderAdapter;

import java.util.ArrayList;
import java.util.Map;

public class PointActivity extends AppCompatActivity {
    private static final String TAG = "BUKH";

    private FirebaseDatabase mDatabase;
    private DatabaseReference mRef;

    private RecyclerView mRecyclerView;
//    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    private PointAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admission);

        Log.d(TAG,"onCreate Toolbar Loaded");
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Transport");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        mDatabase = FirebaseDatabase.getInstance();
        mRef = mDatabase.getReference(); // This will create a child Node with the name users



        ArrayList<PointCardData> list = new ArrayList<>();

        list.add(new PointCardData(R.drawable.bus,"Qasimabad","Points carry you Qasimabad"));
        list.add(new PointCardData(R.drawable.bus,"Latifabad 3","Points carry you Latifabad 3"));
        list.add(new PointCardData(R.drawable.bus,"Phuleli","Points carry you Phuleli!"));
        list.add(new PointCardData(R.drawable.bus,"Naseem Nagar","Points carry you Naseem Nagar"));
        list.add(new PointCardData(R.drawable.bus,"Haider chowk","Points carry you Haider chowk"));
        list.add(new PointCardData(R.drawable.bus,"Gull Center","Points carry you Gul center"));
//
//        list.add(new AdmissionCardData(R.drawable.po_img,"Postgradate","If you want to get Admission in Postgraduate!"));
//        list.add(new AdmissionCardData(R.drawable.dr_img,"PhD","If you want to get Admission in Doctor of Philosophy!"));


        mRecyclerView = findViewById(R.id.recylerView);
        mRecyclerView.setHasFixedSize(true); // This keeps the Size fixed and Recycles every Card
        mLayoutManager = new LinearLayoutManager(this); // Manage the Layout in Linear Fashion
//        mAdapter = new PointAdapter(list);
        mAdapter = new PointAdapter(list);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);

        DividerItemDecoration decoration = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        mRecyclerView.addItemDecoration(decoration);

        mRecyclerView.addOnItemTouchListener(
                new RecyclerItemClickListener(this, mRecyclerView ,new RecyclerItemClickListener.OnItemClickListener() {

                    @Override public void onItemClick(View view, int position) {
//                        String title = ((TextView) view ).getText().toString();

//                        view = ( mRecyclerView.findViewHolderForAdapterPosition(position).itemView.findViewById(R.id.title));
//                        String title1 = ((TextView)view).getText().toString();
//                        Toast.makeText(PointActivity.this,"title1 is "+title1,Toast.LENGTH_SHORT).show();
                        switch (position){
                            case 0:
                                //Qasimabad
                                showMessage("Qasimabad Points","Point: 34\nLeaving Time 12pm\nPoint: 10\nLeaving Time 3pm ");
                                break;
                            case 1:
                                //Latifabad 3
                                showMessage("Latifabad 3","Point: 8\nLeaving Time 1pm\nPoint: 21\nLeaving Time 3pm ");
                                break;
                            case 2:
                                //Phuleli
                                showMessage("Phuleli","Point: 11\nLeaving Time 1pm\nPoint: 5\nLeaving Time 3pm ");
                                break;
                            case 3:
                                //Naseem Nagar
                                showMessage("Naseem Nagar","Point: 4\nLeaving Time 1pm\nPoint: 2\nLeaving Time 3pm ");
                                break;
                            case 4:
                                //Haider chowk
                                showMessage("Haider chowk","Point: 22\nLeaving Time 1pm\nPoint: 20\nLeaving Time 3pm ");
                                break;
                            case 5:
                                showMessage("Gull Center","Point: 24\nLeaving Time 1pm\nPoint: 31\nLeaving Time 3pm ");

                                break;
                            case 6:
                                break;
                            case 7:
                                break;
                            case 8:
                                break;
//                                Toast.makeText(PointActivity.this,snapshot.getKey(),Toast.LENGTH_SHORT).show();
//                                Toast.makeText(PointActivity.this,snapshot.getKey(),Toast.LENGTH_SHORT).show();
//                                mRef.child("Bus").addListenerForSingleValueEvent(new ValueEventListener() {
//                                    @Override
//                                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

//                                        for(DataSnapshot snapshot : dataSnapshot.getChildren()){
//                                              Map<String,Object> data = (Map<String, Object>) snapshot.getValue();
//                                              Toast.makeText(PointActivity.this,snapshot.getKey(),Toast.LENGTH_SHORT).show();
//                                              Log.d(TAG,snapshot.getKey());
//                                              String route1 = data.get("Route1").toString();
//                                              String route2 = data.get("Route2").toString();

//                                              Log.d(TAG, (String) data.get("Leave Time"));
//                                              Log.d(TAG, (String) data.get("Route1"));
//                                              Log.d(TAG, (String) data.get("Route2"));

//                                              if(title1.equalsIgnoreCase(route1) || title1.equalsIgnoreCase(route2)){
//                                                Toast.makeText(PointActivity.this,snapshot.getKey(),Toast.LENGTH_SHORT).show();
//                                              }


//                                        }

//                                    }
//
//                                    @Override
//                                    public void onCancelled(@NonNull DatabaseError databaseError) {
//
//                                    }
//                                });

                        }
                    }

                    @Override public void onLongItemClick(View view, int position) {
                        // do whatever on Long Press
                    }
                })
        );
    }

    private void showMessage(String title, String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.show();

    }
    // Search Icon at the Top or at Toolbar for searching a recycler item from all.
//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//
//        Log.d(TAG,"onCreateOptionsMenu  Loaded");
//
//        getMenuInflater().inflate(R.menu.search_menu,menu);
//
//        MenuItem menuItem = menu.findItem(R.id.search_item);
//        SearchView searchView = (SearchView) menuItem.getActionView();
//
//        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
//           @Override
//           public boolean onQueryTextSubmit(String query) {
//               return false;
//           }
//
//           @Override
//           public boolean onQueryTextChange(String newText) {
//               mAdapter.getFilter().filter(newText);
//               return false;
//           }
//       });
//
//
//        return super.onCreateOptionsMenu(menu);
//    }

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

