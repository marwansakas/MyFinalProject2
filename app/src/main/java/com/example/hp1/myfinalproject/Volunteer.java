package com.example.hp1.myfinalproject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.hp1.myfinalproject.CustomAdapters.Custom_Volunteer;
import com.example.hp1.myfinalproject.Graphs.Circle;
import com.example.hp1.myfinalproject.JavaClasses.VolunteerHours;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class Volunteer extends AppCompatActivity implements View.OnClickListener{
	ListView lvolunteer;
	ArrayList arr1=new ArrayList();
	ArrayAdapter<VolunteerHours> adapter;
	FloatingActionButton fab;
	VolunteerHours volunteerHours;

	DatabaseReference databaseReferenceVolunteer,databaseReferenceSubVolunteer;
	FirebaseAuth firebaseAuth;
	FirebaseUser firebaseUser;

	public static Activity volunteerActivity;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_volunteer);

		fab=(FloatingActionButton) findViewById(R.id.fab);//initialize btAdd
		lvolunteer=(ListView)findViewById(R.id.LvVolenteers_done);//initialize lvolunteer

		adapter=new Custom_Volunteer(this,arr1);//initialize adapter
		lvolunteer.setAdapter(adapter);//set the adapter to lvolunteer

		fab.setOnClickListener(this);//make button clickable

		firebaseAuth=FirebaseAuth.getInstance();//initialze firebaseAuth
		firebaseUser=firebaseAuth.getCurrentUser();//get hte current user from firebaseAuth
		databaseReferenceVolunteer= FirebaseDatabase.getInstance().getReference("Volunteer");//go to folder Volunteer
		databaseReferenceSubVolunteer=databaseReferenceVolunteer.child(firebaseUser.getUid());//go to the user information in volunteer

		volunteerActivity=this;//set volunteerActivity as this activity
	}

	/**
	 * shows the volunteer notes that the user added
	 */
	@Override
	protected void onStart() {
		super.onStart();
		databaseReferenceSubVolunteer.addValueEventListener(new ValueEventListener() {
			@Override
			public void onDataChange(DataSnapshot dataSnapshot) {
				for(DataSnapshot volunteerDataSnapShot: dataSnapshot.getChildren())//add all the volunteers in the arraylist
				{
					volunteerHours =volunteerDataSnapShot.getValue(VolunteerHours.class);//get the  value
					arr1.add(volunteerHours);//add the value to the array list
				}
				adapter.notifyDataSetChanged();//notify Data Set Changed
			}

			@Override
			public void onCancelled(DatabaseError databaseError) {

			}
		});
	}

	/**
	 * takes the user to Volunteer_Add
	 * @param view the view that was clicked on
	 */
	@Override
	public void onClick(View view) {
		startActivity(new Intent(this, Volunteer_Add.class));//to go to TestActivity
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
				startActivity(new Intent(Volunteer.this, Login.class));
				finish();
				return true;
			case R.id.calendar:
				startActivity(new Intent(Volunteer.this, CalendarActivity.class));
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
				startActivity(new Intent(Volunteer.this, Login.class));
				finish();

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

