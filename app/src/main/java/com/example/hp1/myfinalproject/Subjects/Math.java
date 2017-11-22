package com.example.hp1.myfinalproject.Subjects;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.hp1.myfinalproject.CalendarActivity;
import com.example.hp1.myfinalproject.Graphs.LineGraph;
import com.example.hp1.myfinalproject.Login;
import com.example.hp1.myfinalproject.MainActivity;
import com.example.hp1.myfinalproject.R;
import com.example.hp1.myfinalproject.root_calculator;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;

public class Math extends Activity implements AdapterView.OnItemClickListener{

	ListView lvmathex;
	ArrayAdapter adapter;
	ArrayList arrmathStuff=new ArrayList();
	FirebaseAuth firebaseAuth;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_math);

		lvmathex=(ListView)findViewById(R.id.lvmathex);//initialize lvmathex
		adapter=new ArrayAdapter<String>(getApplicationContext(),android.R.layout.simple_list_item_1,arrmathStuff);//initialze adapter
		lvmathex.setAdapter(adapter);//set adapet to lvmathex
		lvmathex.setOnItemClickListener(this);//make lvmathex clickable
		adapter.add("Root Calculator");//add to the adapter Root Calculator
		adapter.add("Graphs");//add to the adapter random
		adapter.add("parabola");//add to the adapter parabola
		adapter.add("tashaboh triangels");//add to the adapter tashaboh triangels
		firebaseAuth= FirebaseAuth.getInstance();//to initialize firebaseAuth

	}


    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
		if(i<2){
			Intent intent[]={new Intent(this,root_calculator.class),new Intent(this,LineGraph.class)};
        	startActivity(intent[i]);//go to intent
    	}
    	else
			Toast.makeText(getApplicationContext(),"Coming Soon",Toast.LENGTH_SHORT).show();
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
				startActivity(new Intent(Math.this, Login.class));
				finish();
				return true;
			case R.id.calendar:
				startActivity(new Intent(Math.this, CalendarActivity.class));
				return true;

		}
		return super.onOptionsItemSelected(item);//return the items for the menu
	}
}
