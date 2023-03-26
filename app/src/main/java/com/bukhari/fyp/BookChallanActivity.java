package com.bukhari.fyp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.nio.channels.CancelledKeyException;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;

public class BookChallanActivity extends AppCompatActivity {

    private EditText edtAccountNumber;
    private EditText edtBookName;
    private EditText edtBookId;
    private EditText edtRollNum;
    private Button btnIssued;
    private TextView bookIssueDate;

    private TextView payableAmount;
    private DatePickerDialog.OnDateSetListener mDateSetListener;

    private static final String TAG = "bukh";

    int year = 0;
    int month = 0;
    int day = 0;

    int total_fine = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_challan);

        edtAccountNumber = findViewById(R.id.edtAccountNumber);
        edtBookName = findViewById(R.id.edtBookName);
        edtBookId = findViewById(R.id.edtBookId);
        edtRollNum = findViewById(R.id.edtRollNum);


        btnIssued = findViewById(R.id.btnIssued);
        bookIssueDate = findViewById(R.id.bookIssueDate);
        payableAmount = findViewById(R.id.payableAmount);


        // Dialog When Date of Birth TextView is clicked
        btnIssued.setOnClickListener(view -> {
            Calendar cal = Calendar.getInstance();
            year = cal.get(Calendar.YEAR);
            month = cal.get(Calendar.MONTH);
            day = cal.get(Calendar.DAY_OF_MONTH);

            DatePickerDialog dialog = new DatePickerDialog(
                    BookChallanActivity.this,
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
                bookIssueDate.setText(date);



                Date today = new Date(); // Fri Jun 17 14:54:28 PDT 2016
                Calendar cal = Calendar.getInstance();
                cal.setTime(today); // don't forget this if date is arbitrary e.g. 01-01-2014
                int dayOfWeek = cal.get(Calendar.DAY_OF_WEEK); // 6
                int curdayOfMonth = cal.get(Calendar.DAY_OF_MONTH) + 1; //because it start from 0
                int dayOfYear = cal.get(Calendar.DAY_OF_YEAR); //169
                int curmonth = cal.get(Calendar.MONTH) + 1; //because it start from 0
                int curyear = cal.get(Calendar.YEAR); // 2016

                if(curdayOfMonth < day || curmonth < month || curyear < year){
                    Toast.makeText(BookChallanActivity.this, "invalid Date Input", Toast.LENGTH_SHORT).show();
                    return;
                }

                Log.d(TAG,"previous  ");
                Log.d(TAG,"DAY IS "+day);
                Log.d(TAG,"MONTH IS "+month);
                Log.d(TAG,"YEAR IS "+year);

                Log.d(TAG,"Current  ");
                Log.d(TAG,"DAY IS "+curdayOfMonth);
                Log.d(TAG,"MONTH IS "+curmonth);
                Log.d(TAG,"YEAR IS "+curyear);


                int DAY = curdayOfMonth - day;
                int MONTH = curmonth - month;
                int YEAR = curyear - year;

                Log.d(TAG,"DAY IS "+day);
                Log.d(TAG,"MONTH IS "+MONTH);
                Log.d(TAG,"YEAR IS "+YEAR);

                int days_in_month = 0;
                if(MONTH>0){
                    days_in_month =  MONTH * 30;
                }
                int days_in_year = 0;
                if(YEAR >0){
                    days_in_year = YEAR * 365;
                }

                total_fine = DAY + days_in_month + days_in_year;

                payableAmount.setText(""+total_fine);
            }
        };


    }

    public void onPay(View view) {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_TEXT,total_fine);
        startActivity(Intent.createChooser(intent,"Share"));
    }

//    void payUsingUpi(String amount, String upiId, String name, String note) {
//
//
//
//        Intent upiPayIntent = new Intent(Intent.ACTION_VIEW);
//        upiPayIntent.setData(uri);
//
//        // will always show a dialog to user to choose an app
//        Intent chooser = Intent.createChooser(upiPayIntent, "Pay with");
//
//        // check if intent resolves
//        if(null != chooser.resolveActivity(getPackageManager())) {
//            startActivity(chooser, UPI_PAYMENT);
//        } else {
//            Toast.makeText(BookChallanActivity.this,"No UPI app found, please install one to continue",Toast.LENGTH_SHORT).show();
//        }
//
//    }

//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//
//        switch (requestCode) {
//            case UPI_PAYMENT:
//                if ((RESULT_OK == resultCode) || (resultCode == 11)) {
//                    if (data != null) {
//                        String trxt = data.getStringExtra("response");
//                        Log.d("UPI", "onActivityResult: " + trxt);
//                        ArrayList<String> dataList = new ArrayList<>();
//                        dataList.add(trxt);
//                        upiPaymentDataOperation(dataList);
//                    } else {
//                        Log.d("UPI", "onActivityResult: " + "Return data is null");
//                        ArrayList<String> dataList = new ArrayList<>();
//                        dataList.add("nothing");
//                        upiPaymentDataOperation(dataList);
//                    }
//                } else {
//                    Log.d("UPI", "onActivityResult: " + "Return data is null"); //when user simply back without payment
//                    ArrayList<String> dataList = new ArrayList<>();
//                    dataList.add("nothing");
//                    upiPaymentDataOperation(dataList);
//                }
//                break;
//        }
//    }

//    private void upiPaymentDataOperation(ArrayList<String> data) {
//        if (isConnectionAvailable(BookChallanActivity.this)) {
//            String str = data.get(0);
//            Log.d("UPIPAY", "upiPaymentDataOperation: "+str);
//            String paymentCancel = "";
//            if(str == null) str = "discard";
//            String status = "";
//            String approvalRefNo = "";
//            String response[] = str.split("&");
//            for (int i = 0; i < response.length; i++) {
//                String equalStr[] = response[i].split("=");
//                if(equalStr.length >= 2) {
//                    if (equalStr[0].toLowerCase().equals("Status".toLowerCase())) {
//                        status = equalStr[1].toLowerCase();
//                    }
//                    else if (equalStr[0].toLowerCase().equals("ApprovalRefNo".toLowerCase()) || equalStr[0].toLowerCase().equals("txnRef".toLowerCase())) {
//                        approvalRefNo = equalStr[1];
//                    }
//                }
//                else {
//                    paymentCancel = "Payment cancelled by user.";
//                }
//            }
//
//            if (status.equals("success")) {
//                //Code to handle successful transaction here.
//                Toast.makeText(BookChallanActivity.this, "Transaction successful.", Toast.LENGTH_SHORT).show();
//                Log.d("UPI", "responseStr: "+approvalRefNo);
//            }
//            else if("Payment cancelled by user.".equals(paymentCancel)) {
//                Toast.makeText(BookChallanActivity.this, "Payment cancelled by user.", Toast.LENGTH_SHORT).show();
//            }
//            else {
//                Toast.makeText(BookChallanActivity.this, "Transaction failed.Please try again", Toast.LENGTH_SHORT).show();
//            }
//        } else {
//            Toast.makeText(BookChallanActivity.this, "Internet connection is not available. Please check and try again", Toast.LENGTH_SHORT).show();
//        }
//    }
//
//    public static boolean isConnectionAvailable(Context context) {
//        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
//        if (connectivityManager != null) {
//            NetworkInfo netInfo = connectivityManager.getActiveNetworkInfo();
//            if (netInfo != null && netInfo.isConnected()
//                    && netInfo.isConnectedOrConnecting()
//                    && netInfo.isAvailable()) {
//                return true;
//            }
//        }
//        return false;
//    }
}