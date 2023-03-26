package com.bukhari.fyp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class UnAdmissionInstructActivity extends AppCompatActivity implements OnMapReadyCallback {


    // Google Maps
    private static final int PLAY_SERVICES_ERROR_CODE = 102;
    private static final String TAG = "Maps ==>";
    private static final int REQUEST_CODE = 15;

    private FusedLocationProviderClient mLocationClient;

    private boolean mLocationPermissionGranted;
    public static final int PERMISSION_REQUEST_CODE = 100;
    private GoogleMap mGoogleMap;
    /////////////////////



    String departments[] = {"Select", "Architecture Eng", "Biomedical Eng", "Civil Eng", "Computer Systems Eng"
            , "Environmental Eng", "Industrial Eng", "Mining Eng", "Metallurgy Eng", "Software Eng","Telecom Eng"};


    final double ARCLATLNG[] = {25.410614, 68.259558};
    final double BIOLATLNG[] = {25.404556, 68.260234};
    final double CIVLATLNG[] = {25.399978, 68.256175};
    final double CSDPTLATLNG [] = {25.406825, 68.262682};
    final double ENVLATLNG [] = {};
    final double INDLATLNG [] = {25.405214, 68.258100};
    final double MINLATLNG [] = {25.405781, 68.258856};
    final double METLATLNG [] = {};
    final double SOFTLATLNG [] = {25.404424, 68.261544};
    final double TELELATLNG [] = {25.407079, 68.263303};
    final double BANKLATLNG [] = {25.406485, 68.265798};
    final double ADMINBLOCKLATLNG [] = {25.401800, 68.259715};

    // Controls
    private Spinner spinnerDepartments;

    private CheckBox checkBoxAdmitSlip;
    private CheckBox checkBoxBankChallan;
    private CheckBox checkBoxAdminBlock;

    private String department = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_un_admission_instruct);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Instructions");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        spinnerDepartments = findViewById(R.id.spinner);

        checkBoxAdmitSlip = findViewById(R.id.checkboxAdmitSlip);
        checkBoxBankChallan = findViewById(R.id.checkboxBankChallan);
        checkBoxAdminBlock = findViewById(R.id.checkboxAdminBlock);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, departments);
        spinnerDepartments.setAdapter(adapter);
        spinnerDepartments.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                int index = spinnerDepartments.getSelectedItemPosition();
                if (index != 0) {
                    department = departments[index];
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        // Initializing the Google Maps
        initGoogleMap();
        mLocationClient = new FusedLocationProviderClient(this);
        getCurrentLocation();


    }

    public void onCheckBoxChecked(View view){
        Log.d(TAG,"Checkbox Checked ");
        CheckBox checkBox = (CheckBox) view;
        switch (checkBox.getId()){
            case R.id.checkboxAdmitSlip:
                if(department.isEmpty()){
                    Log.d(TAG,"department String is Empty");
                    Toast.makeText(this, "Please Select the Department First", Toast.LENGTH_SHORT).show();
                }else {
                    Log.d(TAG,"department String is not Empty");
                    if(checkBox.isChecked()){
                        // SHOWING THE MARKER TO THE SELECTED DEPARTMENT IN THE CHECKBOX Architecture Eng
                        mGoogleMap.clear();
                        if(department.equalsIgnoreCase("Architecture Eng")){
                            showMarker(new LatLng(ARCLATLNG[0], ARCLATLNG[1]), "Architecture Department");
                        }else if(department.equalsIgnoreCase("Biomedical Eng")) {
                            showMarker(new LatLng(BIOLATLNG[0], BIOLATLNG[1]), "Biomedical Department");
                        }else if(department.equalsIgnoreCase("Civil Eng")) {
                            showMarker(new LatLng(CIVLATLNG[0], CIVLATLNG[1]), "Civil Department");
                        }else if(department.equalsIgnoreCase("Industrial Eng")) {
                            showMarker(new LatLng(INDLATLNG[0], INDLATLNG[1]), "Industrial Department");
                        }else if(department.equalsIgnoreCase("Mining Eng")) {
                            showMarker(new LatLng(MINLATLNG[0], MINLATLNG[1]), "Mining Department");
                        }else if(department.equalsIgnoreCase("Software Eng")) {
                            showMarker(new LatLng(SOFTLATLNG[0], SOFTLATLNG[1]), "Software Department");
                        }else if(department.equalsIgnoreCase("Telecom Eng")) {
                            showMarker(new LatLng(TELELATLNG[0], TELELATLNG[1]), "Telecom Department");
                        }else if(department.equalsIgnoreCase("Computer Systems Eng")) {
                            showMarker(new LatLng(CSDPTLATLNG[0], CSDPTLATLNG[1]), "CS Department");
                        }else if(department.equalsIgnoreCase("Computer Systems Eng")) {
                            showMarker(new LatLng(CSDPTLATLNG[0], CSDPTLATLNG[1]), "CS Department");
                        }
                    }else{
                        mGoogleMap.clear();
                    }
                }
                break;
            case R.id.checkboxBankChallan:
                if(department.isEmpty()){
                    Toast.makeText(this, "Please Select the Department First", Toast.LENGTH_SHORT).show();
                }else {
                    if(checkBox.isChecked()){
                        // The bank Challan or Fees is irrespective of the Department like location for the Bank is Constant
                        mGoogleMap.clear();
                        showMarker(new LatLng(BANKLATLNG[0], BANKLATLNG[1]), "HBL Bank");

                    }else{
                        mGoogleMap.clear();
                    }
                }
                break;
            case R.id.checkboxAdminBlock:
                if(department.isEmpty()){
                    Toast.makeText(this, "Please Select the Department First", Toast.LENGTH_SHORT).show();
                }else {
                    if(checkBox.isChecked()){
                        // This is the Admission Process for the CS Department
                        if(department.equalsIgnoreCase("Computer Systems Eng")) {
                            mGoogleMap.clear();
                            showMarker(new LatLng(ADMINBLOCKLATLNG[0], ADMINBLOCKLATLNG[1]), "Admin Block");
                        }
                    }else{
                        mGoogleMap.clear();
                    }
                }
                break;
        }
    }

    private void initGoogleMap() {
        Log.d(TAG, "Initialize the Map");
        if(isServiceOk() == true){
            // If the GPS is Enabled
            if(isGPSEnabled()){
                //Checking the permission
                if(checkLocationPermission() == true){
                    Toast.makeText(this, "Map is Ready", Toast.LENGTH_SHORT).show();

                    SupportMapFragment supportMapFragment = (SupportMapFragment)getSupportFragmentManager()
                            .findFragmentById(R.id.map_fragment);
                    supportMapFragment.getMapAsync(this);

                    Log.d(TAG, "Map is Ready");
                }else{
                    requestLocationPermission();
                    Toast.makeText(this, "Requesting Permissions", Toast.LENGTH_SHORT).show();
                }// End of else
            }

        }
    }

    // Checking the Permission is granted or not
    private boolean checkLocationPermission() {
        Log.d(TAG, "Chekcing the Permission value is true");
        return ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) ==
                PackageManager.PERMISSION_GRANTED;
    }

    private boolean isServiceOk(){
        Log.d(TAG, "Checking Services");
        //Checking whether the Google Play Services are avaialble or not
        GoogleApiAvailability googleApiAvailability = GoogleApiAvailability.getInstance();

        int result = googleApiAvailability.isGooglePlayServicesAvailable(this);

        // If the google play services are availabe
        if(result == ConnectionResult.SUCCESS){
            Log.d(TAG, "Services are OK ");
            return true;
        }
        // If the google play services are not available and Error is resolvable
        else if (googleApiAvailability.isUserResolvableError(result)){
            Log.d(TAG, "Services are Not OK but Resolvable");
            // show the Message of the Error
            googleApiAvailability.getErrorDialog(this,result , PLAY_SERVICES_ERROR_CODE,task->
                    Toast.makeText(this,"Dialog is Cancelled by User",Toast.LENGTH_SHORT).show());
        }
        // If the google play services are not available and Error is also not resolvable
        else{
            Toast.makeText(this,"Play Services are required by this Application",Toast.LENGTH_SHORT).show();
            Log.d(TAG, "Services are Not OK and Not Resolvable");
        }
        return false;
    }

    private void requestLocationPermission(){
        Log.d(TAG, "Reequsting the Location Permission");
        // Again checking if the permission is not granted
        if(ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED){
            Log.d(TAG, "If Permissions not Granted");
            // this will check if the API LEVEL is 23 or 23+ then it will run otherwise or below devices it will not recall for the permission again
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                // This is a callback method to request for the permission again.
                requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION},PERMISSION_REQUEST_CODE);
            }
        }
    }

    // Call back method for the permission
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        Log.d(TAG, "CallBAck Method for Location Permission to check values");
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        // grantResults[0] is the firsst index because we know ew have stored only on first index of the string array
        if(requestCode == PERMISSION_REQUEST_CODE && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            mLocationPermissionGranted = true;
            Toast.makeText(this, "Permission Granted", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(this,"Permission Denied",Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        Log.d(TAG,"onMapReady: The Map is Read");
        // Showing marker on the Google Maps
        mGoogleMap = googleMap;

        //The MyLocation button will get me my Location
        mGoogleMap.setMyLocationEnabled(true);
        mGoogleMap.getUiSettings().setZoomControlsEnabled(true);
        mGoogleMap.getUiSettings().setMapToolbarEnabled(true);

    }

    // To get the Current Location of the Device
    public void getCurrentLocation() {
        mLocationClient.getLastLocation().addOnCompleteListener(task->{
            if(task.isSuccessful()){
                // if this is successful means we get the Location of the Device
                Location location = task.getResult(); // This will return the Location object
                gotoLocation(location.getLatitude(),location.getLongitude());

//                showMarker(new LatLng(location.getLatitude(),location.getLongitude()));

            }else{
                // if this is not successful means we have not obtained the Location of the Devicef
                Toast.makeText(this,task.getException().getMessage(),Toast.LENGTH_SHORT).show();
            }
        });
    }
    // To move the Camera to a given lat lng position
    private void gotoLocation(double lat , double lng){
        // Showing the Camera on a given LatLong Position
        LatLng latLng = new LatLng(lat,lng);

        // Zoom value is from 0 to 20 so 10 is a city level Zoom
        // if Zoom is  1=> world  5=>continent 10=>City 15=>Street 20=>Buildings
        CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(latLng,15);

        // If we are loadin the Maps First Time we use moveCamers() method
        mGoogleMap.moveCamera(cameraUpdate);
    }

    // used to show the Marker
    public void showMarker(LatLng latlng , String title){
        // and Location where Marker will appear
        MarkerOptions markerOptions = new MarkerOptions().title(title).position(latlng);
        mGoogleMap.addMarker(markerOptions);

    }

    private boolean isGPSEnabled(){
        LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        boolean providerEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        if(providerEnabled){
            return true;
        }else{
            AlertDialog dialog = new AlertDialog.Builder(this)
                    .setTitle("GPS Permission")
                    .setMessage("GPs is required for this App. Please Enable the GPS")
                    .setPositiveButton("Yes",(builderInterface,i)->{
                        // if he clicks yes then this is performed
                        Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                        startActivityForResult(intent,REQUEST_CODE);
                    })
                    .setCancelable(false) // Disabling the Cancel button in the Dialog so that the user can not cancel the GPS
                    .show();
        }
        return false;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE) {
            LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
            boolean providerEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
            // provider is Eanbled
            if (providerEnabled) {
                Toast.makeText(this, "GPS is Enabled", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "GPS is not Enabled to show the Location", Toast.LENGTH_SHORT).show();
            }
        }
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

    public void onAllDone(View view) {
        if(checkBoxAdmitSlip.isChecked() && checkBoxBankChallan.isChecked() && checkBoxAdminBlock.isChecked()){
            if(!spinnerDepartments.getSelectedItem().toString().equalsIgnoreCase("select")){
                Intent intent = new Intent(UnAdmissionInstructActivity.this,GreetingActivity.class);
                intent.putExtra("Dpt",spinnerDepartments.getSelectedItem().toString());
                startActivity(intent);
            }
        }
    }
}