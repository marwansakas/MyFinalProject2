package com.example.hp1.myfinalproject;

import java.util.ArrayList;
import java.util.Calendar;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
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
	wazefe wazefe;
	public static Activity homeworkActivity;
	DatabaseReference databaseReferenceHomework,databaseReferenceSubHomework;
	FirebaseUser firebaseUser;
    DatabaseReference reference;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_homework);

		btplus=(Button)findViewById(R.id.button1);
		lvsubjects=(ListView)findViewById(R.id.listView1);

		btplus.setOnClickListener(this);
		lvsubjects.setOnItemLongClickListener(this);

		firebaseUser= FirebaseAuth.getInstance().getCurrentUser();
		databaseReferenceHomework= FirebaseDatabase.getInstance().getReference("Homework");
		databaseReferenceSubHomework=databaseReferenceHomework.child(firebaseUser.getUid());

		homeworkActivity=this;
		adapter=new CustomAdapter(this,arrsubjects);
		lvsubjects.setAdapter(adapter);
	}

	@Override
	protected void onStart() {
		super.onStart();
		databaseReferenceSubHomework.addValueEventListener(new ValueEventListener() {
			@Override
			public void onDataChange(DataSnapshot dataSnapshot) {
				for(DataSnapshot homeWorkDataSnapShot: dataSnapshot.getChildren())
				{
						wazefe wazefe = homeWorkDataSnapShot.getValue(wazefe.class);
						arrsubjects.add(wazefe);
						adapter.notifyDataSetChanged();

					if(arrsubjects.size()>0)
						setNotification();

				}
			}

			@Override
			public void onCancelled(DatabaseError databaseError) {

			}
		});
	}

	@Override
	public void onClick(View v) {
		if(v==btplus)
			startActivity(new Intent(this,Homework_Add.class));

	}

	@Override
	public boolean onItemLongClick(final AdapterView<?> parent, View view, final int position, long id) {


		return true;
	}


	public void setNotification(){
		AlarmManager alarmManager=(AlarmManager)getSystemService(ALARM_SERVICE);
		Calendar calendar=Calendar.getInstance();
		calendar.set(Calendar.HOUR_OF_DAY,9);
		calendar.set(Calendar.MINUTE,12);
		calendar.set(Calendar.SECOND,0);
		Intent intent1=new Intent("singh.ajit.action.DISPLAY_NOTIFICATION");
		PendingIntent pendingIntent=PendingIntent.getBroadcast(HomeWork.this,NOTIFICATION_CODE,intent1,PendingIntent.FLAG_UPDATE_CURRENT);
		alarmManager.setExact(AlarmManager.RTC_WAKEUP,calendar.getTimeInMillis()+AlarmManager.INTERVAL_HALF_DAY,pendingIntent);

	}
}
