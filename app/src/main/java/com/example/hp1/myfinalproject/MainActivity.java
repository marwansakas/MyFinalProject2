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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.ArrayList;

public class MainActivity extends Activity implements OnClickListener{

	Button btexplination,bttests,btvolenteer,bthomework;
	ImageView imb;
	Intent intent;
    ListView lvNews;
    ArrayList arrNews=new ArrayList();
    ArrayAdapter<String> adapter;
	Uri passedUri;

    DatabaseReference databaseReferenceRegister;
	FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;
    StorageReference storageRef,file_path;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		firebaseAuth=FirebaseAuth.getInstance();
        firebaseUser=firebaseAuth.getCurrentUser();
		databaseReferenceRegister = FirebaseDatabase.getInstance().getReference("Registrations");
        storageRef = FirebaseStorage.getInstance().getReference();
		file_path=storageRef.child("Photos").child(firebaseUser.getUid());
        intent=getIntent();//to get the information for this intent
		btexplination=(Button)findViewById(R.id.btexplinations);
		bttests=(Button)findViewById(R.id.bttests);
		btvolenteer=(Button)findViewById(R.id.btvolenteer);
		bthomework=(Button)findViewById(R.id.bthomework);
		imb=(ImageView) findViewById(R.id.imvProfPic);
		lvNews=(ListView)findViewById(R.id.lvNews);
		btexplination.setOnClickListener(this);
		bttests.setOnClickListener(this);
		btvolenteer.setOnClickListener(this);
		bthomework.setOnClickListener(this);
		imb.setOnClickListener(this);
        adapter=new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,arrNews);
		lvNews.setAdapter(adapter);

	}

    @Override
    protected void onStart() {
        super.onStart();
        if(intent.getBooleanExtra("checking for user",false))
            saveUSerInformation();
		if(intent.getBooleanExtra("check",false)){
			passedUri=Uri.parse(intent.getStringExtra("uri"));
			imb.setImageURI(passedUri);
		}
		else
		{
			file_path.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
				@Override
				public void onSuccess(Uri uri) {
					passedUri=uri;
					Glide.with(MainActivity.this)
							.load(uri)
							.into(imb);

				}
			});
		}
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
						i.putExtra("uriFromMain",passedUri.toString());//to transfer username to Profile
						i.putExtra("mainCheck",true);
						startActivity(i);//to go to Profile

					}

	}
    public void saveUSerInformation(){
        Bundle bundle=intent.getExtras();
        InformationRegistered informationRegistered=(InformationRegistered)bundle.getSerializable("information Registered");
        FirebaseUser user=firebaseAuth.getCurrentUser();
		databaseReferenceRegister.child(user.getUid()).setValue(informationRegistered);
        Uri uri=Uri.parse("android.resource://com.example.hp1.myfinalproject/"+R.drawable.nopicture);

        file_path.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Toast.makeText(getApplication(),"yes",Toast.LENGTH_SHORT).show();
            }
        });
    }
}
