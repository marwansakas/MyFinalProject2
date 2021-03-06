package com.example.hp1.myfinalproject;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;

import java.util.Calendar;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hp1.myfinalproject.Graphs.Circle;
import com.example.hp1.myfinalproject.JavaClasses.Date;
import com.example.hp1.myfinalproject.JavaClasses.wazefe;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import static com.example.hp1.myfinalproject.HomeWork.homeworkActivity;

public class Homework_Add extends Activity implements OnClickListener {

    EditText ettheDs;
    Button btadd;
    Spinner s;
    String[] arraySpinner = {"Choose a subject", "Math", "Arabic", "Hebrew", "English", "Computers", "Biology", "Chemistry", "History"};
    private TextView mDisplayDate;
    private DatePickerDialog.OnDateSetListener mDatesetListener;
    int year = 0, month = 0, day = 0;
    int selectDay, selectMonth, selectYear;

    DatabaseReference databaseReferenceHomework;
    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homework__show);

        mDisplayDate = (TextView) findViewById(R.id.textView10);//givethe textview an id
        ettheDs = (EditText) findViewById(R.id.editText2);//give the edittext an id
        btadd = (Button) findViewById(R.id.btAdd);//give the button an id
        s = (Spinner) findViewById(R.id.spinner1);//give the spinner an id

        ArrayAdapter<String> spinneradapter = new ArrayAdapter<String>(this, R.layout.spinner_item, arraySpinner);//initialize the array adapter
        spinneradapter.setDropDownViewResource(R.layout.spinner_menu);
        s.setAdapter(spinneradapter);//set the adapter to the spinner

        btadd.setOnClickListener(this);//make the button clickable
        mDisplayDate.setOnClickListener(this);//make the textview clickable
        mDatesetListener = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {//so the user can pick te date on the datePicker dialog
                month += 1;//becuase the month starts at zero so we increase it by 1
                mDisplayDate.setText("Date:" + day + "/" + month + "/" + year);//display the chosen date on textview
                selectDay = day;//make the selected day
                selectYear = year;//make the selected year
                selectMonth = month;//make the selected month
            }
        };

        databaseReferenceHomework = FirebaseDatabase.getInstance().getReference("Homework");//go to the firebase database foldr homework
        firebaseAuth = FirebaseAuth.getInstance();//initialize firebaseAuth
        firebaseUser = firebaseAuth.getCurrentUser();//initialize firebaseUser by the firebaseAuth
    }

    /**
     * aloows the user to save the hoemwork information and set hte date on the calendar
     * @param v
     */
    @Override
    public void onClick(View v) {
        if (v == mDisplayDate) {//if the user clicked on the textview mDisplayDate
            Calendar cal = Calendar.getInstance();//get the current time
            year = cal.get(Calendar.YEAR);//set the year as the current year
            month = cal.get(Calendar.MONTH);//set the month as the current month
            day = cal.get(Calendar.DAY_OF_MONTH);//set the day as the current day
            DatePickerDialog dialog = new DatePickerDialog(Homework_Add.this, android.R.style.Theme_Holo_Light_DarkActionBar, mDatesetListener, year, month, day);//set the datePicker dialog appearence
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));//set the background color
            dialog.show();//show the datePicker dialog
        }
        if (v == btadd) {

            if ((!ettheDs.getText().toString().equals("")) && year != 0 && day != 0 && month != 0 && (!s.getSelectedItem().toString().equals("Choose a subject"))) {//check if the user filled all the required information

                Intent i = new Intent(this, HomeWork.class);
                saveUserInformation();
                startActivity(i);
                finish();
            }
        }
    }

    /**
     * saves the homework info in firebas
     */
    public void saveUserInformation() {
        wazefe wazefe = new wazefe(s.getSelectedItem().toString(), ettheDs.getText().toString(), new Date(selectDay, selectMonth, selectYear));//add all the information to the wazefe variable
        databaseReferenceHomework.child(firebaseUser.getUid()).push().setValue(wazefe);
    }

    /**
     *
     * @param menu the menu
     * @return
     * this function create the menu
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {//to create an options menu
        super.onCreateOptionsMenu(menu);
        MenuInflater menuInflater = getMenuInflater();//initialize menuInflater
        menuInflater.inflate(R.menu.menu_main, menu);//to create the three dot menu

        return super.onCreateOptionsMenu(menu);//to return if the result
    }

    /**
     * if the user clicked logout then the user will be logged out of the application
     * if he chooses delete his account will be deleted
     * @param item thid=s parameter is the item that was clicked on
     * @return
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item)//to make the items for the options menu
    {
        switch (item.getItemId()) {
            case R.id.logOut:
                firebaseAuth.signOut();
                startActivity(new Intent(Homework_Add.this, Login.class));
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
                startActivity(new Intent(Homework_Add.this, Login.class));
                finish();
                return true;

        }
        return super.onOptionsItemSelected(item);//return the items for the menu
    }

    /**
     * go back to HomeWork page
     */
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(this, HomeWork.class));
    }
}
