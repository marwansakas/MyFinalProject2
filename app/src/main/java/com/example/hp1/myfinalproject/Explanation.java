package com.example.hp1.myfinalproject;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.hp1.myfinalproject.classes.Arabic;
import com.example.hp1.myfinalproject.classes.Biology;
import com.example.hp1.myfinalproject.classes.Chemistry;
import com.example.hp1.myfinalproject.classes.Computer_Sience;
import com.example.hp1.myfinalproject.classes.English;
import com.example.hp1.myfinalproject.classes.Hebrew;
import com.example.hp1.myfinalproject.classes.History;
import com.example.hp1.myfinalproject.classes.Madaneyat;
import com.example.hp1.myfinalproject.classes.Math;
import com.example.hp1.myfinalproject.classes.Sports;

public class Explanation extends Activity implements OnItemLongClickListener{
ListView lvsubjects;
ArrayAdapter<String> adapter;
ArrayList arrsubjects= new ArrayList<String>();
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_explination);
		adapter=new ArrayAdapter<String>(getApplicationContext(),android.R.layout.simple_list_item_1,arrsubjects);
		lvsubjects=(ListView)findViewById(R.id.lvexplinasions);
		lvsubjects.setOnItemLongClickListener(this);
		lvsubjects.setAdapter(adapter);
		adapter.add("Math");
		adapter.add("Arabic");
		adapter.add("English");
		adapter.add("Computer Sience");
		adapter.add("Hebrew");
		adapter.add("Biology");
		adapter.add("Chemistry");
		adapter.add("History");
		adapter.add("Sports");
		adapter.add("Madaneyat");
	}

	
	@Override
	public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
		Intent[] i={new Intent(this,Math.class),
					new Intent(this,Arabic.class),
					new Intent(this,English.class),
					new Intent(this,Computer_Sience.class),
					new Intent(this,Hebrew.class),
					new Intent(this,Biology.class),
					new Intent(this,Chemistry.class),
					new Intent(this,History.class),
					new Intent(this,Sports.class),
					new Intent(this,Madaneyat.class)};
		startActivity(i[position]);
		Toast.makeText(getApplicationContext(),position+"",Toast.LENGTH_SHORT).show();
		return true;
	}
}
