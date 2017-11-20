package com.example.hp1.myfinalproject;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.hp1.myfinalproject.R;

public class root_calculator extends AppCompatActivity implements View.OnClickListener{

    EditText etA,etB,etC;
    double result1,result2;
    Button btCalc;
    TextView tvResult;

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
}
