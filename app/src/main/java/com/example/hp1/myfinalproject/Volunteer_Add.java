package com.example.hp1.myfinalproject;


import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hp1.myfinalproject.JavaClasses.Date;
import com.example.hp1.myfinalproject.JavaClasses.PaintView;
import com.example.hp1.myfinalproject.JavaClasses.VolunteerHours;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.ByteArrayOutputStream;
import java.util.Calendar;

import static java.lang.Integer.parseInt;


public class Volunteer_Add extends AppCompatActivity implements View.OnClickListener {

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
        setContentView(R.layout.activity_volunteer_add_);
        bt = (Button) findViewById(R.id.btsend);
        etplace = (EditText) findViewById(R.id.etplace);
        etactivity = (EditText) findViewById(R.id.etactivity);
        ethours = (EditText) findViewById(R.id.ethours);
        tvDate = (TextView) findViewById(R.id.tvdate);
        paintView = (PaintView) findViewById(R.id.paintview);

        DisplayMetrics metrics = new DisplayMetrics();// to initialize metrics
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        paintView.init(metrics);

        bt.setOnClickListener(this);
        tvDate.setOnClickListener(this);

        mDatesetListener = new DatePickerDialog.OnDateSetListener() {
            /**
             * displays the chosen date
             * @param datePicker the datpicker
             * @param year the chosen year
             * @param month the chosen month
             * @param day the chosen day
             */
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

    /**
     * creates the on options menu
     * @param menu the munu that was created
     * @return
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {//to create options menu
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.main, menu);

        return super.onCreateOptionsMenu(menu);
    }

    /**
     * allows the user to clear the signuture
     * @param item the item that was clicked on
     * @return
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {//to put iteams in the options menu
        switch (item.getItemId()) {
            case R.id.clear:
                paintView.clear();//to clear the paintview
                return true;
            case R.id.logOut:
                firebaseAuth.signOut();
                startActivity(new Intent(Volunteer_Add.this, Login.class));
                finish();
                return true;
            case R.id.delete:
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                user.delete()
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    Toast.makeText(getApplicationContext(),"user was deleted",Toast.LENGTH_SHORT);
                                }
                            }
                        });
                startActivity(new Intent(Volunteer_Add.this, Login.class));
                finish();

        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * if the view was tvDate opens the datePicker Dialog
     * if the view that was clicked on bt saves information and goes back to Volunteer
     * @param view the view that was clicked on
     */
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
                saveUserInformation();
                Intent i = new Intent(this, Volunteer.class);//to set the i variable
                startActivity(i);//to start activity
                finish();//to finish this activity
            }
        }
    }

    /**
     * go back to Volunteer page
     */
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(this, Volunteer.class));
    }

    /**
     * save the information into the database
     */
    public void saveUserInformation(){
        VolunteerHours volunteerHours =new VolunteerHours(strplace,stractivity,Integer.parseInt(strhours),new Date(day,month+1,year),BitMapToString(paintView.getmBitmap()));
        databaseReferenceVolunteer.child(firebaseUser.getUid()).push().setValue(volunteerHours);
    }

    /**
     * this function converts Bitmap to String
     * @param bitmap this is the signature
     * @return return the bitmap but in type String
     */
    public String BitMapToString(Bitmap bitmap){
        ByteArrayOutputStream baos=new  ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG,100, baos);
        byte [] b=baos.toByteArray();
        String temp= Base64.encodeToString(b, Base64.DEFAULT);
        return temp;
    }
}
