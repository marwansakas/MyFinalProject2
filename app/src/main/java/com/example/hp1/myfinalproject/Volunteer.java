package com.example.hp1.myfinalproject;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;


public class Volunteer extends Activity implements View.OnClickListener{
	ListView lvolunteer;
	ArrayList arr1=new ArrayList();
	ArrayAdapter<Rows> adapter;
	Intent intent;
	Rows volunteer_note;
	Button btAdd;
	DataBaseVolunteerFarde volunteerFarde;
	Rows rows;

	public static Activity volunteerActivity;

	DatabaseReference databaseReferenceVolunteer,databaseReferenceSubVolunteer;
	FirebaseAuth firebaseAuth;
	FirebaseUser firebaseUser;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_volunteer);
		volunteerActivity=this;
		volunteerFarde=new DataBaseVolunteerFarde(this);
		lvolunteer=(ListView)findViewById(R.id.LvVolenteers_done);
		adapter=new Custom_Volunteer(this,arr1);
		lvolunteer.setAdapter(adapter);
		btAdd=(Button)findViewById(R.id.btAdd_Volenteer);
		btAdd.setOnClickListener(this);
		intent=getIntent();
		Boolean f=intent.getBooleanExtra("is There",false);
		Cursor res=volunteerFarde.getAllData();//to get all the data from SQLite
		if(res!=null&&res.getCount()>0) {
		while(res.moveToNext())//to add to the listview
		{
			//if(res.getString(3).equals(intent.getStringExtra("username from mainActivity"))){//to add the volunteer by this user
			volunteer_note=new Rows(res.getString(1),res.getString(2),Integer.parseInt(res.getString(3)),new Date(Integer.parseInt(res.getString(4)),Integer.parseInt(res.getString(5)),Integer.parseInt(res.getString(6))),BitmapFactory.decodeByteArray(res.getBlob(7),0,res.getBlob(7).length));//to set volunteer note
			arr1.add(volunteer_note);//to add volunteer note to arr1
			adapter.notifyDataSetChanged();//to notify adapter that something new ha been added to arr1
		}
		}
		if(f){
			byte[] image=intent.getByteArrayExtra("byteArray");//to get the byte[] from
			Bitmap b = BitmapFactory.decodeByteArray(image,0,image.length);//to set bitmap as the byte[]we got from Test_Activity
			Date date=new Date(intent.getIntExtra("day",0),intent.getIntExtra("month",0),intent.getIntExtra("year",0));//to make new ate
			volunteer_note=new Rows(intent.getStringExtra("place"),intent.getStringExtra("activity"),intent.getIntExtra("hours",0),date,b);//to set volunteer_note
			volunteerFarde.insertData("username from mainActivity",volunteer_note,image);//to insertData to SQL
			arr1.add(volunteer_note);//to add volunteer_note to arr1
			adapter.notifyDataSetChanged();//to notify adapter that something new ha been added to arr1
		}
		firebaseAuth=FirebaseAuth.getInstance();
		firebaseUser=firebaseAuth.getCurrentUser();
		databaseReferenceVolunteer= FirebaseDatabase.getInstance().getReference("Volunteer");
		databaseReferenceSubVolunteer=databaseReferenceVolunteer.child(firebaseUser.getUid());
	}

	@Override
	protected void onStart() {
		super.onStart();
		databaseReferenceSubVolunteer.addValueEventListener(new ValueEventListener() {
			@Override
			public void onDataChange(DataSnapshot dataSnapshot) {
				for(DataSnapshot volunteerDataSnapShot: dataSnapshot.getChildren())
				{
					rows=volunteerDataSnapShot.getValue(Rows.class);//the problem is that bitmap does not have a constructor with no argument try changing to byte array
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

