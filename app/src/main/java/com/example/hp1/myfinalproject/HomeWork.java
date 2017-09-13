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
import android.widget.Toast;

public class HomeWork extends Activity implements OnClickListener,AdapterView.OnItemLongClickListener{

	public static final int NOTIFICATION_CODE=100;

	ListView lvsubjects;
	ArrayAdapter<wazefe> adapter;
	ArrayList arrsubjects= new ArrayList();
	Button btplus;
	wazefe wazefe;
	DataBaseHomeWork myDb;
	String subject,details,day,month,year;
	String Id;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_homework);
		myDb = new DataBaseHomeWork(this.getBaseContext());
		Intent intent=getIntent();
		lvsubjects=(ListView)findViewById(R.id.listView1);
		lvsubjects.setOnItemLongClickListener(this);
		adapter=new CustomAdapter(this,arrsubjects);
		lvsubjects.setAdapter(adapter);
		btplus=(Button)findViewById(R.id.button1);
		btplus.setOnClickListener(this);

		boolean f=intent.getBooleanExtra("is_there", false);

		Cursor res=myDb.getAllDataFromHomeWork();
		if(res!=null&&res.getCount()>0)
		{
			while (res.moveToNext()){
		//		if(res.getString(0).equals(intent.getStringExtra("username from mainActivity"))){
				wazefe=new wazefe(res.getString(1)
						,res.getString(2)
						,new Date(Integer.parseInt(res.getString(3)),Integer.parseInt(res.getString(4)),Integer.parseInt(res.getString(5))));
				arrsubjects.add(wazefe);
				adapter.notifyDataSetChanged();
				}
			}


		if(f){
			wazefe=new wazefe(intent.getStringExtra("sub"), intent.getStringExtra("Ds"),new Date(intent.getIntExtra("day", 0),intent.getIntExtra("month", 0),intent.getIntExtra("year", 0)));
			arrsubjects.add(wazefe);
			subject=wazefe.getSubject();
			details=wazefe.getDetails();
			day=Integer.toString(wazefe.getD().getDay());
			month=Integer.toString(wazefe.getD().getMonth());
			year=Integer.toString(wazefe.getD().getYear());
			Boolean result=myDb.insertDataToHomeWork(intent.getStringExtra("username from mainActivity"),subject,details,day,month,year);
			if(result)
				Toast.makeText(this,"yes",Toast.LENGTH_SHORT).show();
			adapter.notifyDataSetChanged();
		}
		f=false;
		if(arrsubjects.size()>0)
		{
			AlarmManager alarmManager=(AlarmManager)getSystemService(ALARM_SERVICE);
			Calendar calendar=Calendar.getInstance();
			calendar.set(Calendar.HOUR_OF_DAY,9);
			calendar.set(Calendar.MINUTE,12);
			calendar.set(Calendar.SECOND,0);
			Intent intent1=new Intent("singh.ajit.action.DISPLAY_NOTIFICATION");
			PendingIntent pendingIntent=PendingIntent.getBroadcast(this,NOTIFICATION_CODE,intent1,PendingIntent.FLAG_UPDATE_CURRENT);
			alarmManager.setExact(AlarmManager.RTC_WAKEUP,calendar.getTimeInMillis(),pendingIntent);
		}

	}
	@Override
	public void onClick(View v) {
		if(v==btplus)
		startActivity(new Intent(this,Homework_Add.class)
		);
	}

	@Override
	public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {

		Cursor res = myDb.getAllDataFromHomeWork();
		res.move(position);
		myDb.deleteData(adapter.getItem(position).getDetails());
		arrsubjects.remove(position);
		adapter.notifyDataSetChanged();
		return true;
	}
}
