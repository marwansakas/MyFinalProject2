package com.example.hp1.myfinalproject;

import java.util.ArrayList;
import java.util.Calendar;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.hp1.myfinalproject.CustomAdapters.CustomAdapter;
import com.example.hp1.myfinalproject.Graphs.Circle;
import com.example.hp1.myfinalproject.JavaClasses.wazefe;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class HomeWork extends AppCompatActivity implements OnClickListener,AdapterView.OnItemLongClickListener{

	//this activity has been commented all over

	public static final int NOTIFICATION_CODE=100;

	ListView lvsubjects;
	ArrayAdapter<wazefe> adapter;
	ArrayList<wazefe> arrsubjects= new ArrayList();
	public static Activity homeworkActivity;
	DatabaseReference databaseReferenceHomework,databaseReferenceSubHomework;
	FirebaseUser firebaseUser;
	FirebaseAuth firebaseAuth;
	FloatingActionButton fab;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_homework);
		fab=(FloatingActionButton)findViewById(R.id.fab);
		lvsubjects=(ListView)findViewById(R.id.listView1);//initialize lvsubjects
		lvsubjects.setOnItemLongClickListener(this);//make lvsubjects clickable
		homeworkActivity=this;//to set homeworkActivity
		adapter=new CustomAdapter(this,arrsubjects);//initialize adapter
		lvsubjects.setAdapter(adapter);
		fab.setOnClickListener(this);

		firebaseAuth=FirebaseAuth.getInstance();//to initialize firebaseAuth
		firebaseUser= FirebaseAuth.getInstance().getCurrentUser();//initialize firebaseUser
		databaseReferenceHomework= FirebaseDatabase.getInstance().getReference("Homework");//initialize databaseReferenceHomework
		databaseReferenceSubHomework=databaseReferenceHomework.child(firebaseUser.getUid());//to get databaseReferenceHomework child
	}

	@Override
	protected void onStart() {
		super.onStart();
		getHomeworkFromFirebase();
	}

	/**
	 *
	 * @param v the view that was clicked on
	 *          and sed the user to te activity Homework_Add
	 */
	@Override
	public void onClick(View v) {
		startActivity(new Intent(this,Homework_Add.class));//go to Homework_Add
		finish();
	}


	/**
	 *
	 * @param parent
	 * @param view
	 * @param position
	 * @param id
	 * @return
	 *
	 * this function lets the user delete a homework from his database
	 */
	@Override
	public boolean onItemLongClick(final AdapterView<?> parent, View view, final int position, long id) {
		Query query=databaseReferenceSubHomework.orderByChild("details").equalTo(arrsubjects.get(position).getDetails());
		query.addListenerForSingleValueEvent(new ValueEventListener() {
			@Override
			public void onDataChange(DataSnapshot dataSnapshot) {
				for (DataSnapshot appleSnapshot: dataSnapshot.getChildren()){
						appleSnapshot.getRef().removeValue();
						arrsubjects.clear();
				}

			}

			@Override
			public void onCancelled(DatabaseError databaseError) {

			}
		});

        return true;
	}

	/**
	 * this function set a notifacation for the user if he has homework
	 */
	public void setNotification(){

		AlarmManager alarmManager=(AlarmManager)getSystemService(ALARM_SERVICE);//initialize
		Calendar calendar=Calendar.getInstance();//get the current time
		calendar.set(Calendar.HOUR_OF_DAY,9);//set the notification to go of at 9 o'clock
		calendar.set(Calendar.MINUTE,12);//12 minutes
		calendar.set(Calendar.SECOND,0);//and 0 seconds
		Intent intent1=new Intent("singh.ajit.action.DISPLAY_NOTIFICATION");//create the intent
		PendingIntent pendingIntent=PendingIntent.getBroadcast(HomeWork.this,NOTIFICATION_CODE,intent1,PendingIntent.FLAG_UPDATE_CURRENT);//create the pending intent that will luached at the wanted time
		alarmManager.setExact(AlarmManager.RTC_WAKEUP,calendar.getTimeInMillis()+AlarmManager.INTERVAL_HALF_DAY,pendingIntent);//set the alarm maneger to go off on the wanted time and start the pending intent

	}

	/**
	 * this function gets all the homework stored in the database and showes them tin the listview
	 */
	public void getHomeworkFromFirebase()
	{
		databaseReferenceSubHomework.addValueEventListener(new ValueEventListener() {
			@Override
			public void onDataChange(DataSnapshot dataSnapshot) {
				for(DataSnapshot homeWorkDataSnapShot: dataSnapshot.getChildren())
				{
					wazefe wazefe = homeWorkDataSnapShot.getValue(wazefe.class);//to get the user's homework from firebase database
					arrsubjects.add(wazefe);//to add the homework to arrsubjects

					if(arrsubjects.size()>0)//if there is homework
						setNotification();//start setNotification function

				}
				adapter.notifyDataSetChanged();//notify Data Set Changed
			}
			@Override
			public void onCancelled(DatabaseError databaseError) {
			}
		});
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
				startActivity(new Intent(HomeWork.this, Login.class));
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
				startActivity(new Intent(HomeWork.this, Login.class));
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
