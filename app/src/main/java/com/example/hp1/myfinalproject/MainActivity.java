package com.example.hp1.myfinalproject;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.hp1.myfinalproject.CustomAdapters.CustomNews;
import com.example.hp1.myfinalproject.Graphs.Circle;
import com.example.hp1.myfinalproject.JavaClasses.InformationRegistered;
import com.example.hp1.myfinalproject.JavaClasses.News;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;

public class MainActivity extends Activity implements OnClickListener,AdapterView.OnItemClickListener{

	//this activity has been commented all over

	Button btexplination,bttests,btvolenteer,bthomework;
	ImageView imb;
	Intent intent;
	News myNews;
    ListView lvNews;
    ArrayList arrNews=new ArrayList();
    ArrayAdapter<News> adapter;

	Uri passedUri;

    DatabaseReference databaseReferenceRegister,databaseReferenceNews;
	FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;
	FirebaseMessaging firebaseMessage;
    StorageReference storageRef,file_path;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		btexplination=(Button)findViewById(R.id.btexplinations);//initialize the button
		bttests=(Button)findViewById(R.id.btcalendar);//initialize the button
		btvolenteer=(Button)findViewById(R.id.btvolenteer);//initialize the button
		bthomework=(Button)findViewById(R.id.bthomework);//initialize the button
		imb=(ImageView) findViewById(R.id.imvProfPic);//initialize the ImageView
		lvNews=(ListView)findViewById(R.id.lvNews);//initialize the ListView

		btexplination.setOnClickListener(this);//make button clickable
		bttests.setOnClickListener(this);//make button clickable
		btvolenteer.setOnClickListener(this);//make button clickable
		bthomework.setOnClickListener(this);//make button clickable
		imb.setOnClickListener(this);//make imageView clickable
		lvNews.setOnItemClickListener(this);

		intent=getIntent();//to get the information for this intent
		adapter=new CustomNews(this,arrNews);
		lvNews.setAdapter(adapter);//to set adapter to listView

		firebaseMessage=FirebaseMessaging.getInstance();
		firebaseAuth=FirebaseAuth.getInstance();//to initialize firebaseAuth
        firebaseUser=firebaseAuth.getCurrentUser();//to get the current user in firebase user

		databaseReferenceRegister = FirebaseDatabase.getInstance().getReference("Registrations");//initialize databaseReferenceRegister
		databaseReferenceNews=FirebaseDatabase.getInstance().getReference("News");
		databaseReferenceNews.addValueEventListener(new ValueEventListener() {
			/**
			 *
			 * @param dataSnapshot
			 * gets all the news that was written in the firebase and shows them
			 */
			@Override
			public void onDataChange(DataSnapshot dataSnapshot) {
				for(DataSnapshot dataSnapshotNews:dataSnapshot.getChildren()){
					myNews=dataSnapshotNews.getValue(News.class);
					arrNews.add(myNews);
					adapter.notifyDataSetChanged();
				}
			}

			@Override
			public void onCancelled(DatabaseError databaseError) {

			}
		});

        storageRef = FirebaseStorage.getInstance().getReference();//initialize storageRef
		file_path=storageRef.child("Photos").child(firebaseUser.getUid());//to get the child of storageRef
	}

	/**
	 * it saves the user's information if a new account was formed
	 *
	 * set the profile image
	 */
    @Override
    protected void onStart() {
        super.onStart();

        if(intent.getBooleanExtra("checking for user",false)) {//to see if the usr came from Register
			saveUserInformation();//to save the user's information
		}

		if(intent.getBooleanExtra("checkUri",false)){//to see if the usr came from Profile
			passedUri=Uri.parse(intent.getStringExtra("uri"));//to get the image from storage
			imb.setImageURI(passedUri);//to set imb image as the image from storage
		}else {
            if(intent.getBooleanExtra("checkByte[]",false))
            {
                byte[] bytes=intent.getByteArrayExtra("byte[]");
                Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0,bytes.length);
                imb.setImageBitmap(bitmap);
            }else{
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
			}
			else
				imb.setImageResource(R.drawable.nopicture);//if the user came from register then set his non profile pic
				}
		}

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
				startActivity(new Intent(MainActivity.this, Login.class));
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
				startActivity(new Intent(MainActivity.this, Login.class));
				finish();
				return true;

		}
		return super.onOptionsItemSelected(item);//return the items for the menu
	}

	/**
	 * this function lets you go to the activity the user chose
	 * @param v the view that was clicked on
	 */
	@Override
	public void onClick(View v) {
		if(v==btexplination)
		    startActivity(new Intent(this,Explanation.class));
		else
			if(v==bttests)
				startActivity(new Intent(this,CalendarActivity.class));
			else
				if(v==btvolenteer) {
					Intent i = new Intent(this, Volunteer.class);
					startActivity(i);
				}
				else
					if(v==bthomework){
						Intent i=new Intent(this,HomeWork.class);
						startActivity(i);
                    }
					else {
						Intent i=new Intent(this, Profile.class);
						startActivity(i);
					}
		finish();
	}

	/**
	 *
	 * this function saves the information the new user added when he signed up for an account
	 */
    public void saveUserInformation(){

        Bundle bundle=intent.getExtras();//to get the bundle from Register
        InformationRegistered informationRegistered=(InformationRegistered)bundle.getSerializable("information Registered");//to convert the bundle into an InformationRegistered value

		databaseReferenceRegister.child("Students").child(firebaseUser.getUid()).setValue(informationRegistered);//to add the registered value to firebase database
        Uri uri=Uri.parse("android.resource://com.example.hp1.myfinalproject/"+R.drawable.nopicture);//to give the new user a first profile picture
		firebaseMessage.subscribeToTopic(informationRegistered.getTakhassos());
		file_path.putFile(uri);//to add his first profile pic to database storage
    }

	/**
	 * open an activity displaying the clicked in news
	 * @param adapterView
	 * @param view
	 * @param position
	 * @param l
	 */
	@Override
	public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
		Intent i = new Intent(this,ShowNews.class);
		i.putExtra("Title",adapter.getItem(position).getTitle());
		i.putExtra("Artical",adapter.getItem(position).getArtical());
		startActivity(i);
	}

}
