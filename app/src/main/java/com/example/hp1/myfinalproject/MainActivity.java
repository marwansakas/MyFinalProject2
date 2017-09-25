package com.example.hp1.myfinalproject;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.ArrayList;

public class MainActivity extends Activity implements OnClickListener{

	Button btexplination,bttests,btvolenteer,bthomework;
	ImageView imb;
	Intent intent,intentShowNews;
	String title,first_sentence;
	News myNews;

    ListView lvNews;
    ArrayList arrNews=new ArrayList();
    ArrayAdapter<News> adapter;

	Uri passedUri;

    DatabaseReference databaseReferenceRegister
			,databaseReferenceNews;
	FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;
    StorageReference storageRef,file_path;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		btexplination=(Button)findViewById(R.id.btexplinations);//initialize the button
		bttests=(Button)findViewById(R.id.bttests);//initialize the button
		btvolenteer=(Button)findViewById(R.id.btvolenteer);//initialize the button
		bthomework=(Button)findViewById(R.id.bthomework);//initialize the button
		imb=(ImageView) findViewById(R.id.imvProfPic);//initialize the ImageView
		lvNews=(ListView)findViewById(R.id.lvNews);//initialize the ListView

		btexplination.setOnClickListener(this);//make button clickable
		bttests.setOnClickListener(this);//make button clickable
		btvolenteer.setOnClickListener(this);//make button clickable
		bthomework.setOnClickListener(this);//make button clickable
		imb.setOnClickListener(this);//make imageView clickable

		intent=getIntent();//to get the information for this intent
		adapter=new CustomNews(this,arrNews);
		lvNews.setAdapter(adapter);//to set adapter to listView

		firebaseAuth=FirebaseAuth.getInstance();//to initialize firebaseAuth
        firebaseUser=firebaseAuth.getCurrentUser();//to get the current user in firebase user
		databaseReferenceRegister = FirebaseDatabase.getInstance().getReference("Registrations");//initialize databaseReferenceRegister

		databaseReferenceNews=FirebaseDatabase.getInstance().getReference("News");
		databaseReferenceNews.addChildEventListener(new ChildEventListener() {
			@Override
			public void onChildAdded(DataSnapshot dataSnapshot, String s) {
				//intentShowNews=new Intent(MainActivity.this,ShowNews.class);
				myNews=dataSnapshot.getValue(News.class);
				arrNews.add(myNews);
				adapter.notifyDataSetChanged();

			}

			@Override
			public void onChildChanged(DataSnapshot dataSnapshot, String s) {

			}

			@Override
			public void onChildRemoved(DataSnapshot dataSnapshot) {

			}

			@Override
			public void onChildMoved(DataSnapshot dataSnapshot, String s) {

			}

			@Override
			public void onCancelled(DatabaseError databaseError) {

			}
		});

        storageRef = FirebaseStorage.getInstance().getReference();//initialize storageRef
		file_path=storageRef.child("Photos").child(firebaseUser.getUid());//to get the child of storageRef
	}

    @Override
    protected void onStart() {
        super.onStart();

        if(intent.getBooleanExtra("checking for user",false)) {//to see if the usr came from Register
			saveUserInformation();//to save the user's information

		}
		if(intent.getBooleanExtra("check",false)){//to see if the usr came from Profile
			passedUri=Uri.parse(intent.getStringExtra("uri"));//to get the image from storage
			imb.setImageURI(passedUri);//to set imb image as the image from storage
		}else {
			if(!intent.getBooleanExtra("checking for user",false)){
				file_path.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
					@Override
					public void onSuccess(Uri uri) {
						passedUri = uri;
						Glide.with(MainActivity.this)
								.load(uri)
								.into(imb);
					}
				});
			}else
				imb.setImageResource(R.drawable.nopicture);//if the user came from register then set his non profile pic
		}

    }

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {//to create an options menu
		super.onCreateOptionsMenu(menu);
		MenuInflater menuInflater = getMenuInflater();//initialize menuInflater
		menuInflater.inflate(R.menu.menu_main, menu);//to create the three dot menu

		return super.onCreateOptionsMenu(menu);//to return if the result
	}

    @Override
    public boolean onOptionsItemSelected(MenuItem item)//to make the items for the options menu
    {
        switch (item.getItemId()) {
			case R.id.logOut://if the user chose logOut
				firebaseAuth.signOut();//to signout of the account
				startActivity(new Intent(this,Login.class));//to go to Login
				return true;
			case R.id.suggestion://if the user chose suggestion
				//startActivity(new Intent(this, Explanation.class));
				return true;

		}
		return super.onOptionsItemSelected(item);//return the items for the menu
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
					startActivity(i);//to go to Volunteer
				}
				else
					if(v==bthomework){
						Intent i=new Intent(this,HomeWork.class);
						startActivity(i);//to go to HomeWork
                    }
					else {
						Intent i=new Intent(this, Profile.class);
						startActivity(i);//to go to Profile
					}

	}
    public void saveUserInformation(){

        Bundle bundle=intent.getExtras();//to get the bundle from Register
        InformationRegistered informationRegistered=(InformationRegistered)bundle.getSerializable("information Registered");//to convert the bundle into an InformationRegistered value

		databaseReferenceRegister.child(firebaseUser.getUid()).setValue(informationRegistered);//to add the registered value to firebase database
        Uri uri=Uri.parse("android.resource://com.example.hp1.myfinalproject/"+R.drawable.nopicture);//to give the new user a first profile picture
        file_path.putFile(uri);//to add his first profile pic to database storage
    }
}
