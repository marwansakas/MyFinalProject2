package com.example.hp1.myfinalproject;


import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.ByteArrayOutputStream;
import java.util.Calendar;

import static com.example.hp1.myfinalproject.Volunteer.volunteerActivity;
import static java.lang.Integer.parseInt;


public class Test_Activity extends AppCompatActivity implements View.OnClickListener {

    private PaintView paintView;
    Button bt;
    EditText etplace, etactivity, ethours;
    TextView tvDate;
    private DatePickerDialog.OnDateSetListener mDatesetListener;
    int year = 0, month = 0, day = 0;
    String strplace,stractivity,strhours;

    DatabaseReference databaseReferenceVolunteer;
    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_);
        paintView = (PaintView) findViewById(R.id.paintview);
        DisplayMetrics metrics = new DisplayMetrics();// to initialize metrics
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        paintView.init(metrics);
        bt = (Button) findViewById(R.id.btsend);
        bt.setOnClickListener(this);
        etplace = (EditText) findViewById(R.id.etplace);
        etactivity = (EditText) findViewById(R.id.etactivity);
        ethours = (EditText) findViewById(R.id.ethours);
        tvDate = (TextView) findViewById(R.id.tvdate);
        tvDate.setOnClickListener(this);
        mDatesetListener = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                month += 1;// to increase moths by 1 becuase they start at zero
                tvDate.setText("Date:" + day + "/" + month + "/" + year);//to set tvDate text as the inputted Text
            }
        };
        firebaseAuth=FirebaseAuth.getInstance();
        firebaseUser=firebaseAuth.getCurrentUser();
        databaseReferenceVolunteer= FirebaseDatabase.getInstance().getReference("Volunteer");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {//to create options menu
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.main, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {//to put iteams in the options menu
        switch (item.getItemId()) {
            case R.id.clear:
                paintView.clear();//to clear the paintview
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View view) {
        if (view == tvDate) {
            Calendar cal = Calendar.getInstance();// to return a Calendar instance based on the current time in the default time zone with the default locale.
            year = cal.get(Calendar.YEAR);//to get the this year
            month = cal.get(Calendar.MONTH);//to get this month
            day = cal.get(Calendar.DAY_OF_MONTH);//to get this day of the month

            DatePickerDialog dialog = new DatePickerDialog(this, android.R.style.Theme_Holo_Light_DarkActionBar, mDatesetListener, year, month, day);//to build the DatePickerDialog
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));//to get dialog window and to st the background color of the dialog as transparent
            dialog.show();//to show the DatePickerDialog
        }
        if (view == bt) {
            strplace = etplace.getText().toString();//to get etplace Text
            stractivity = etactivity.getText().toString();//to get etactivity text
            strhours=ethours.getText().toString();//to get ethours text
            if (!stractivity.equals("") && !strplace.equals("")&&!strhours.equals("")&&!tvDate.getText().toString().equals("Date: ??/??/??")) {//to check that all the requarments are full
                Intent i = new Intent(this, Volunteer.class);//to set the i variable
                Bitmap b = paintView.mBitmap;//to set the b variable
                ByteArrayOutputStream bs = new ByteArrayOutputStream();//to set the bs variable
                b.compress(Bitmap.CompressFormat.PNG, 50, bs);//to compress b into the bs variable
                i.putExtra("byteArray", bs.toByteArray());//to put bs.toByteArray() into the intent
                i.putExtra("place", strplace);//to put strplace into the intent
                i.putExtra("activity", stractivity);//to put stractivity into the intent
                i.putExtra("hours", parseInt(strhours));//to put strhours in the intent as a int variable
                i.putExtra("day", day);//to put day in the intent
                i.putExtra("month", month);//to put month in the intent
                i.putExtra("year", year);//to put year in the intent
                i.putExtra("is There", true);//to prove that ther is an intent
                volunteerActivity.finish();
                saveUserInformation();
                startActivity(i);//to start activity
                finish();//to finish this activity
            }
        }
    }

    public void saveUserInformation(){
        Rows rows=new Rows(strplace,stractivity,Integer.parseInt(strhours),new Date(day,month,year),paintView.mBitmap);
        databaseReferenceVolunteer.child(firebaseUser.getUid()).push().setValue(rows);
    }
}
