package com.example.hp1.myfinalproject;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hp1.myfinalproject.Graphs.Circle;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Root_calculator extends AppCompatActivity implements View.OnClickListener{

    EditText etA,etB,etC;
    double result1,result2;
    Button btCalc;
    TextView tvResult;
    FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_root_calculator);
        etA=(EditText)findViewById(R.id.editText_A);//initilaze EditText etA
        etB=(EditText)findViewById(R.id.editText_B);//initilaze EditText etB
        etC=(EditText)findViewById(R.id.editText_C);//initilaze EditText etC
        btCalc=(Button)findViewById(R.id.btCalculate);//initilaze Button btCalc
        btCalc.setOnClickListener(this);//make button clickable
        tvResult=(TextView)findViewById(R.id.tvResult);//show the result
        firebaseAuth= FirebaseAuth.getInstance();//to initialize firebaseAuth
    }

    @Override
    public void onClick(View view) {
        if((!etA.getText().toString().equals(""))&&(!etB.getText().toString().equals(""))&&(!etC.getText().toString().equals("")))//to see if the user inputted all the information
        {
            double a=Double.parseDouble(etA.getText().toString()),b=Double.parseDouble(etB.getText().toString()),c=Double.parseDouble(etC.getText().toString());//add the value to a,b and c
            double delta=b*b-4*a*c;//set the delta value
            if (delta>0) {//to see if delta is bigger than 0
                result1 = (-b + Math.sqrt(delta)) / (2 * a);//get the first result
                result2 = (-b - Math.sqrt(delta)) / (2 * a);////get the second result
                tvResult.setText("X1=" + result1 + "\nX2= " + result2);//show the answer
            }else
                if (delta==0)//to see if delta equals to 0
                {
                    result1 = (-b + Math.sqrt(delta)) / (2 * a);//get the result
                    tvResult.setText("X=" + result1);//show the answer
                }else
                    tvResult.setText("ERROR");//if delta is lower than 0 then there is no answer so show ERROR

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
                startActivity(new Intent(Root_calculator.this, Login.class));
                finish();
                return true;
            case R.id.calendar:
                startActivity(new Intent(Root_calculator.this, CalendarActivity.class));
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
                startActivity(new Intent(Root_calculator.this, Login.class));
                finish();

        }
        return super.onOptionsItemSelected(item);//return the items for the menu
    }

    /**
     * go back to Math page
     */
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(this, Math.class));
    }
}

