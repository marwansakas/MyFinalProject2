package com.example.hp1.myfinalproject.classes;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.hp1.myfinalproject.R;
import com.example.hp1.myfinalproject.math_stuff.root_calculator;

import java.util.ArrayList;

public class Math extends Activity implements AdapterView.OnItemClickListener{
	ListView lvmathex;
	ArrayAdapter adapter;
	ArrayList arrmathStuff=new ArrayList();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_math);
		lvmathex=(ListView)findViewById(R.id.lvmathex);//initialize lvmathex
		adapter=new ArrayAdapter<String>(getApplicationContext(),android.R.layout.simple_list_item_1,arrmathStuff);//initialze adapter
		lvmathex.setAdapter(adapter);//set adapet to lvmathex
		lvmathex.setOnItemClickListener(this);//make lvmathex clickable
		adapter.add("Root Calculator");//add to the adapter Root Calculator
		adapter.add("random");//add to the adapter random
		adapter.add("parabola");//add to the adapter parabola
		adapter.add("tashaboh triangels");//add to the adapter tashaboh triangels

	}


    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        Intent intent=new Intent(this,root_calculator.class);//initialize intent
        startActivity(intent);//go to intent
    }
}
