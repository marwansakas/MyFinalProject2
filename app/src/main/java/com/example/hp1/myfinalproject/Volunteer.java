package com.example.hp1.myfinalproject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class Volunteer extends Activity implements View.OnClickListener{
	ListView lvolunteer;
	ArrayList arr1=new ArrayList();
	ArrayAdapter<Rows> adapter;
	Button btAdd;
	Rows rows;

	DatabaseReference databaseReferenceVolunteer,databaseReferenceSubVolunteer;
	FirebaseAuth firebaseAuth;
	FirebaseUser firebaseUser;

	public static Activity volunteerActivity;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_volunteer);
		btAdd=(Button)findViewById(R.id.btAdd_Volenteer);//initialize btAdd
		lvolunteer=(ListView)findViewById(R.id.LvVolenteers_done);//initialize lvolunteer

		adapter=new Custom_Volunteer(this,arr1);//initialize adapter
		lvolunteer.setAdapter(adapter);//set the adapter to lvolunteer

		btAdd.setOnClickListener(this);//make button clickable

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
					rows=volunteerDataSnapShot.getValue(Rows.class);//get the  value
					arr1.add(rows);//add the value to the array list
				}
				adapter.notifyDataSetChanged();//notify Data Set Changed
			}

			@Override
			public void onCancelled(DatabaseError databaseError) {

			}
		});
	}

	/**
	 * takes the user to Test_Activity
	 * @param view the view that was clicked on
	 */
	@Override
	public void onClick(View view) {
		startActivity(new Intent(this, Test_Activity.class));//to go to TestActivity
	}
}

