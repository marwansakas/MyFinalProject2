package com.example.hp1.myfinalproject.Subjects;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.hp1.myfinalproject.CalendarActivity;
import com.example.hp1.myfinalproject.Login;
import com.example.hp1.myfinalproject.MainActivity;
import com.example.hp1.myfinalproject.R;
import com.google.firebase.auth.FirebaseAuth;

public class Sports extends AppCompatActivity implements View.OnClickListener{

    EditText etweight,etheight;
    Button btCalc;
    TextView tvresualt;
    ImageView shape;
    FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sports);
        etheight=(EditText)findViewById(R.id.etmeters);//initialize etheight
        etweight=(EditText)findViewById(R.id.etkg);//initialize etweight
        tvresualt=(TextView)findViewById(R.id.tvBMI_Resualt);//initialze tvresult
        btCalc=(Button)findViewById(R.id.btCalc);//initialze btCalc
        btCalc.setOnClickListener(this);//make bt Calc clickable
        shape=(ImageView)findViewById(R.id.size);//initialize shape
        firebaseAuth= FirebaseAuth.getInstance();//to initialize firebaseAuth
    }

    /**
     * showz the user his bmi and health
     * @param view
     */
    @Override
    public void onClick(View view) {
        if(!(etweight.getText().toString().equals(""))&&!(etheight.getText().toString().equals("")))//to se if the inputted the required information
        {
            double bmi=Double.parseDouble(etweight.getText().toString())/(Double.parseDouble(etheight.getText().toString())*Double.parseDouble(etheight.getText().toString()));//calculate bmi

            if(bmi<18.5) {//if bmi is smaller than 18.5
                shape.setImageResource(R.drawable.size1);//set image
                tvresualt.setText(bmi+" underweight");//show result
            }else
                if(bmi>=18.5&&bmi<24.9) {//if bmi is smaller than 24.9 and bigger than 18.5
                    shape.setImageResource(R.drawable.size2);//set image
                    tvresualt.setText(bmi+" good");//show result
            }else
                if(bmi>=24.9&&bmi<29.9) {//if bmi is smaller than 29.9 and bigger than 24.9
                    shape.setImageResource(R.drawable.size3);//set image
                    tvresualt.setText(bmi+" overweight");//show result
                }else
                if(bmi>=29.9) {//if bmi is bigger than 29.9
                    shape.setImageResource(R.drawable.size4);//set image
                    tvresualt.setText(bmi+" obese");//show result
                }
        }
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
     * if he clicked calendar he will then be sent to calendar activity
     * @param item thid=s parameter is the item that was clicked on
     * @return
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item)//to make the items for the options menu
    {
        switch (item.getItemId()) {
            case R.id.logOut:
                firebaseAuth.signOut();
                startActivity(new Intent(Sports.this, Login.class));
                finish();
                return true;
            case R.id.calendar:
                startActivity(new Intent(Sports.this, CalendarActivity.class));
                return true;

        }
        return super.onOptionsItemSelected(item);//return the items for the menu
    }
}
