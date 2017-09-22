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
		btAdd=(Button)findViewById(R.id.btAdd_Volenteer);
		lvolunteer=(ListView)findViewById(R.id.LvVolenteers_done);

		adapter=new Custom_Volunteer(this,arr1);
		lvolunteer.setAdapter(adapter);

		btAdd.setOnClickListener(this);

		firebaseAuth=FirebaseAuth.getInstance();
		firebaseUser=firebaseAuth.getCurrentUser();
		databaseReferenceVolunteer= FirebaseDatabase.getInstance().getReference("Volunteer");
		databaseReferenceSubVolunteer=databaseReferenceVolunteer.child(firebaseUser.getUid());

		volunteerActivity=this;
	}

	@Override
	protected void onStart() {
		super.onStart();
		databaseReferenceSubVolunteer.addValueEventListener(new ValueEventListener() {
			@Override
			public void onDataChange(DataSnapshot dataSnapshot) {
				for(DataSnapshot volunteerDataSnapShot: dataSnapshot.getChildren())
				{
					rows=volunteerDataSnapShot.getValue(Rows.class);
					arr1.add(rows);
				}
				adapter.notifyDataSetChanged();
			}


			@Override
			public void onCancelled(DatabaseError databaseError) {

			}
		});
	}

	@Override
	public void onClick(View view) {
		startActivity(new Intent(this, Test_Activity.class));//to go to TestActivity
	}
}

