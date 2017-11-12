package com.example.hp1.myfinalproject.Subjects;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.hp1.myfinalproject.R;

public class Computer_Sience extends Activity implements AdapterView.OnItemClickListener{

	ListView lvComponents;
	ArrayAdapter<String> adapter;
	String[] Widgets={"TextView","EditText","Button","ImageView","Toast","RadioButton","Checkbox","RatingBar","ProgressBar","more will be added in the near future"};//the widgets

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_computer__sience);
		lvComponents=(ListView)findViewById(R.id.lvComponents);//initialize lvComponents
		adapter=new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,Widgets);//initialize adapter
		lvComponents.setAdapter(adapter);//set adapter to lvComponents
		lvComponents.setOnItemClickListener(this);//make lvCompunents clickacble

	}

	@Override
	public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
		Toast.makeText(this,"be sure to check the next update if they are added",Toast.LENGTH_SHORT).show();//show toast that ther is nothing yet about thee chosen widget
	}
}
