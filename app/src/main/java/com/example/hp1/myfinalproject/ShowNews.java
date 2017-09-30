package com.example.hp1.myfinalproject;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class ShowNews extends AppCompatActivity {

    TextView tvTitle,tvArtical;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_news);
        tvTitle=(TextView)findViewById(R.id.title);
        tvArtical=(TextView)findViewById(R.id.artical);
        Intent intent=getIntent();
        tvTitle.setText(intent.getStringExtra("Title"));
        tvArtical.setText(intent.getStringExtra("Artical"));

    }
}
