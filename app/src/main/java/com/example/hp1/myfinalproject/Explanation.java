package com.example.hp1.myfinalproject;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hp1.myfinalproject.CustomAdapters.CustomGrid;
import com.example.hp1.myfinalproject.Graphs.Circle;
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
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Explanation extends Activity implements AdapterView.OnItemClickListener{

	ListView lvsubjects;
	ArrayAdapter<String> adapter;
	ArrayList arrsubjects= new ArrayList<String>();
	FirebaseAuth firebaseAuth;

	/*int[] images={R.drawable.math,R.drawable.arabic,R.drawable.english,R.drawable.coding,R.drawable.hebrew,R.drawable.bio,R.drawable.chemistry,R.drawable.history,R.drawable.sports,R.drawable.madaneyat};
	String[] subjects={"MAth","Arabic","English","Computer Science","Hebrew","Biology","Chemistry","History","Sports","Madaneyat"};
	GridView gridView;
	CustomGrid customGrid;
*/
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_explination);
		lvsubjects=(ListView)findViewById(R.id.lvexplinasions);//initialize lvsubjects

		/*gridView=(GridView)findViewById(R.id.gridView);
		customGrid=new CustomGrid(this,images,subjects);
		gridView.setAdapter(customGrid);
		gridView.setOnItemClickListener(this);
*/
		adapter=new ArrayAdapter<String>(getApplicationContext(),android.R.layout.simple_list_item_1,arrsubjects){
			@Override
			public View getView(int position, View convertView, ViewGroup parent)
			{
				// Get the Item from ListView
				View view = super.getView(position, convertView, parent);

				// Initialize a TextView for ListView each Item
				TextView tv = (TextView) view.findViewById(android.R.id.text1);

				// Set the text color of TextView (ListView Item)
				tv.setTextColor(Color.WHITE);

				// Generate ListView Item using TextView
				return view;
			}
	};;//initialize adapter
		lvsubjects.setOnItemClickListener(this);//make lvsubjects clickable
		lvsubjects.setAdapter(adapter);//set adapter to lvsubjects

		adapter.add("Math");//add to the adapter Math
		adapter.add("Arabic");//add to the adapter Arabic
		adapter.add("English");//add to the adapter English
		adapter.add("Computer Science");//add to the adapter Computer Sience
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
		finish();
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
	 * if he chooses delete his account will be deleted
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
			case R.id.delete:
				FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
				user.delete()
						.addOnCompleteListener(new OnCompleteListener<Void>() {
							@Override
							public void onComplete(@NonNull Task<Void> task) {
								if (task.isSuccessful()) {
									Toast.makeText(getApplicationContext(),"user was deleted",Toast.LENGTH_SHORT);
								}
							}
						});
				startActivity(new Intent(Explanation.this, Login.class));
				finish();
				return true;

		}
		return super.onOptionsItemSelected(item);//return the items for the menu
	}

	/**
	 * go back to MainActivity page
	 */
	@Override
	public void onBackPressed() {
		super.onBackPressed();
		startActivity(new Intent(this, MainActivity.class));
	}

}
