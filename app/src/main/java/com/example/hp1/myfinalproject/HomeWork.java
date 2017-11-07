package com.example.hp1.myfinalproject;

import java.util.ArrayList;
import java.util.Calendar;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
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

public class HomeWork extends Activity implements OnClickListener,AdapterView.OnItemLongClickListener{

	public static final int NOTIFICATION_CODE=100;

	ListView lvsubjects;
	ArrayAdapter<wazefe> adapter;
	ArrayList arrsubjects= new ArrayList();
	Button btplus;
	public static Activity homeworkActivity;
	DatabaseReference databaseReferenceHomework,databaseReferenceSubHomework;
	FirebaseUser firebaseUser;
    FirebaseAuth firebaseAuth;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_homework);

		btplus=(Button)findViewById(R.id.button1);//initialize btplus
		lvsubjects=(ListView)findViewById(R.id.listView1);//initialize lvsubjects

		btplus.setOnClickListener(this);//make btplus clickable
		lvsubjects.setOnItemLongClickListener(this);//make lvsubjects clickable

		homeworkActivity=this;//to set homeworkActivity
		adapter=new CustomAdapter(this,arrsubjects);//initialize adapter
		lvsubjects.setAdapter(adapter);

        firebaseAuth=FirebaseAuth.getInstance();//initialize firebaseAuth
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
		if(v==btplus)
			startActivity(new Intent(this,Homework_Add.class));//go to Homework_ADd

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

        databaseReferenceSubHomework.removeValue();//remove all homework from user's database
        arrsubjects.remove(position);//to remove from arrsubjects
        adapter.notifyDataSetChanged();//notify Data Set Changed

        for(int i=0;i<arrsubjects.size();i++) {
            databaseReferenceHomework.child(firebaseUser.getUid()).push().setValue(arrsubjects.get(i));//to add back all the removed homework that the user did not want to delete
        }

        arrsubjects.clear();//to clear arrsubjects becuase all the homework was added up there
        adapter.notifyDataSetChanged();//notify data set changed
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
}
