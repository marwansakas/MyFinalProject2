package com.example.hp1.myfinalproject.math_stuff;

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
        etA=(EditText)findViewById(R.id.editText_A);
        etB=(EditText)findViewById(R.id.editText_B);
        etC=(EditText)findViewById(R.id.editText_C);
        btCalc=(Button)findViewById(R.id.btCalculate);
        btCalc.setOnClickListener(this);
        tvResult=(TextView)findViewById(R.id.tvResult);
    }

    @Override
    public void onClick(View view) {
        if((!etA.getText().toString().equals(""))&&(!etB.getText().toString().equals(""))&&(!etC.getText().toString().equals("")))
        {
            double a=Double.parseDouble(etA.getText().toString()),b=Double.parseDouble(etB.getText().toString()),c=Double.parseDouble(etC.getText().toString());
            double delta=b*b-4*a*c;
            if (delta>0) {
                result1 = (-b + Math.sqrt(delta)) / (2 * a);
                result2 = (-b - Math.sqrt(delta)) / (2 * a);
                tvResult.setText("X1=" + result1 + "X2=" + result2);
            }else
                if (delta==0)
                {
                    result1 = (-b + Math.sqrt(delta)) / (2 * a);
                    tvResult.setText("X=" + result1);
                }else
                    tvResult.setText("ERROR");

        }
    }
}
