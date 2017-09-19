package com.example.hp1.myfinalproject;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;

import com.example.hp1.myfinalproject.classes.Madaneyat;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends Activity implements OnClickListener{
	Button btexplination,bttests,btvolenteer,bthomework;
	ImageView imb;
	Intent intent;
    DataBaseRegister dbRegister;
    DatabaseReference databaseReference;
	FirebaseAuth firebaseAuth;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		firebaseAuth=FirebaseAuth.getInstance();
		databaseReference = FirebaseDatabase.getInstance().getReference();
		intent=getIntent();//to get the information for this intent
		btexplination=(Button)findViewById(R.id.btexplinations);
		bttests=(Button)findViewById(R.id.bttests);
		btvolenteer=(Button)findViewById(R.id.btvolenteer);
		bthomework=(Button)findViewById(R.id.bthomework);
		imb=(ImageView) findViewById(R.id.imvProfPic);
		imb.setImageResource(R.drawable.nopicture);
		btexplination.setOnClickListener(this);
		bttests.setOnClickListener(this);
		btvolenteer.setOnClickListener(this);
		bthomework.setOnClickListener(this);
		imb.setOnClickListener(this);
		dbRegister=new DataBaseRegister(this);
		Cursor res=dbRegister.getAllData();
		if(res!=null&&res.getCount()>0)
			while (res.moveToNext())
				if(res.getString(3).equals(intent.getStringExtra("username from register")))
				{
					Bitmap b = BitmapFactory.decodeByteArray(res.getBlob(9),0,res.getBlob(9).length);
					imb.setImageBitmap(b);
				}

	}

    @Override
    protected void onStart() {
        super.onStart();
        if(intent.getBooleanExtra("checking for user",false))
            saveUSerInformation();
    }

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {//to create an options menu
		super.onCreateOptionsMenu(menu);
		MenuInflater menuInflater = getMenuInflater();
		menuInflater.inflate(R.menu.menu_main, menu);

		return super.onCreateOptionsMenu(menu);
	}

    @Override
    public boolean onOptionsItemSelected(MenuItem item)//to make the iteams for the options menu
    {
        switch (item.getItemId()) {
			case R.id.logOut:
				firebaseAuth.signOut();
				startActivity(new Intent(this,Login.class));
				return true;
			case R.id.suggestion:
				startActivity(new Intent(this, Explanation.class));
				return true;

		}
		return super.onOptionsItemSelected(item);
    }


	@Override
	public void onClick(View v) {
		if(v==btexplination)
		    startActivity(new Intent(this,Explanation.class));//to go to Explanation
		else
			if(v==bttests)
				startActivity(new Intent(this,Tests.class));//to go to Tests
			else
				if(v==btvolenteer) {
					Intent i = new Intent(this, Volunteer.class);
					i.putExtra("username from mainActivity", intent.getStringExtra("username from register"));//to transfer username to Volunteer
					startActivity(i);//to go to Volunteer
				}
				else
					if(v==bthomework){
						Intent i=new Intent(this,HomeWork.class);
						i.putExtra("username from mainActivity",intent.getStringExtra("username from register"));//to transfer username to HomeWork
						startActivity(i);//to go to HomeWork
                    }
					else {
						Intent i=new Intent(this, Profile.class);
						i.putExtra("username from mainActivity",intent.getStringExtra("username from register"));//to transfer username to Profile
						startActivity(i);//to go to Profile

					}

	}
    public void saveUSerInformation(){
        Bundle bundle=intent.getExtras();
        InformationRegistered informationRegistered=(InformationRegistered)bundle.getSerializable("information Registered");
        FirebaseUser user=firebaseAuth.getCurrentUser();
        databaseReference.child(user.getUid()).setValue(informationRegistered);
    }
}
