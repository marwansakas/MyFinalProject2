package com.example.hp1.myfinalproject;

import java.util.ArrayList;

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

import com.example.hp1.myfinalproject.Subjects.Arabic;
import com.example.hp1.myfinalproject.Subjects.Biology;
import com.example.hp1.myfinalproject.Subjects.Chemistry;
import com.example.hp1.myfinalproject.Subjects.Computer_Sience;
import com.example.hp1.myfinalproject.Subjects.English;
import com.example.hp1.myfinalproject.Subjects.Hebrew;
import com.example.hp1.myfinalproject.Subjects.History;
import com.example.hp1.myfinalproject.Subjects.Madaneyat;
import com.example.hp1.myfinalproject.Subjects.Math;
import com.example.hp1.myfinalproject.Subjects.Sports;
import com.google.firebase.auth.FirebaseAuth;

public class Explanation extends Activity implements AdapterView.OnItemClickListener{

	//this activity has been commented all over

	ListView lvsubjects;
	ArrayAdapter<String> adapter;
	ArrayList arrsubjects= new ArrayList<String>();
	FirebaseAuth firebaseAuth;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_explination);
		lvsubjects=(ListView)findViewById(R.id.lvexplinasions);//initialize lvsubjects

		adapter=new ArrayAdapter<String>(getApplicationContext(),android.R.layout.simple_list_item_1,arrsubjects);//initialize adapter
		lvsubjects.setOnItemClickListener(this);//make lvsubjects clickable
		lvsubjects.setAdapter(adapter);//set adapter to lvsubjects

		adapter.add("Math");//add to the adapter Math
		adapter.add("Arabic");//add to the adapter Arabic
		adapter.add("English");//add to the adapter English
		adapter.add("Computer Sience");//add to the adapter Computer Sience
		adapter.add("Hebrew");//add to the adapter Hebrew
		adapter.add("Biology");//add to the adapter Biology
		adapter.add("Chemistry");//add to the adapter Chemistry
		adapter.add("History");//add to the adapter History
		adapter.add("Sports");//add to the adapter Sports
		adapter.add("Madaneyat");//add to the adapter Madaneyat

		firebaseAuth= FirebaseAuth.getInstance();//to initialize firebaseAuth
	}

	/**
	 *
	 * @param adapterView
	 * @param view
	 * @param position
	 * @param l
	 *
	 * this function lets the user go to the ctivity he chose to click on from the list
	 */
	@Override
	public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
		Intent[] i={new Intent(this,Math.class),
				new Intent(this,Arabic.class),
				new Intent(this,English.class),
				new Intent(this,Computer_Sience.class),
				new Intent(this,Hebrew.class),
				new Intent(this,Biology.class),
				new Intent(this,Chemistry.class),
				new Intent(this,History.class),
				new Intent(this,Sports.class),
				new Intent(this,Madaneyat.class)};//all the activities that the user can go to from Explinations
		startActivity(i[position]);//to go to what ever activity the user chose
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
				startActivity(new Intent(Explanation.this, Login.class));
				finish();
				return true;
			case R.id.calendar:
				startActivity(new Intent(Explanation.this, CalendarActivity.class));
				return true;

		}
		return super.onOptionsItemSelected(item);//return the items for the menu
	}
}
