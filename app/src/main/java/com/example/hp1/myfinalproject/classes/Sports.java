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

    EditText etweight,etheigth;
    Button btCalc;
    TextView tvresualt;
    ImageView shape;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sports);
        etheigth=(EditText)findViewById(R.id.etmeters);
        etweight=(EditText)findViewById(R.id.etkg);
        tvresualt=(TextView)findViewById(R.id.tvBMI_Resualt);
        btCalc=(Button)findViewById(R.id.btCalc);
        btCalc.setOnClickListener(this);
        shape=(ImageView)findViewById(R.id.size);
    }

    @Override
    public void onClick(View view) {
        if(!(etweight.getText().toString().equals(""))&&!(etheigth.getText().toString().equals("")))
        {
            double bmi=Double.parseDouble(etweight.getText().toString())/(Double.parseDouble(etheigth.getText().toString())*Double.parseDouble(etheigth.getText().toString()));

            if(bmi<18.5) {
                shape.setImageResource(R.drawable.size1);
                tvresualt.setText(bmi+"underweight");
            }else
                if(bmi>=18.5&&bmi<24.9) {
                    shape.setImageResource(R.drawable.size2);
                    tvresualt.setText(bmi+"good");
            }else
                if(bmi>=24.9&&bmi<29.9) {
                    shape.setImageResource(R.drawable.size3);
                    tvresualt.setText(bmi+"overweight");
                }else
                if(bmi>=29.9) {
                    shape.setImageResource(R.drawable.size4);
                    tvresualt.setText(bmi+"obese");
                }
        }
    }
}
