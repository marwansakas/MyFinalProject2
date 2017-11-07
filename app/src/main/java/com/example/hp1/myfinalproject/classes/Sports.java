package com.example.hp1.myfinalproject.classes;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.hp1.myfinalproject.R;

public class Sports extends AppCompatActivity implements View.OnClickListener{

    EditText etweight,etheight;
    Button btCalc;
    TextView tvresualt;
    ImageView shape;

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
}
