package com.example.hp1.myfinalproject;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.Notification;
import android.app.PendingIntent;
import android.content.Intent;
import java.util.Calendar;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import static com.example.hp1.myfinalproject.HomeWork.homeworkActivity;

public class Homework_Add extends Activity implements OnClickListener{
	EditText ettheDs;
	Button btadd;
	Spinner s;
	String[] arraySpinner={"Choose a subject","Math","Arabic","Hebrew","English","Computers","Biology","Chemistry","History"};
	private static final String TAG="mainActivity";
	private TextView mDisplayDate;
	private DatePickerDialog.OnDateSetListener mDatesetListener;
	int year=0,month=0,day=0;
	int selectDay,selectMonth,selectYear;
	DataBaseHomeWork myDb;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_homework__show);
		myDb=new DataBaseHomeWork(this);
		ettheDs=(EditText)findViewById(R.id.editText2);
		btadd=(Button)findViewById(R.id.btAdd);
		btadd.setOnClickListener(this);
		s = (Spinner) findViewById(R.id.spinner1);
        ArrayAdapter<String> spinneradapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, arraySpinner);
        s.setAdapter(spinneradapter);
		mDisplayDate=(TextView)findViewById(R.id.textView10);
		mDisplayDate.setOnClickListener(this);
		mDatesetListener=new DatePickerDialog.OnDateSetListener(){

			@Override
			public void onDateSet(DatePicker datePicker, int year, int month, int day) {
				month+=1;
				Log.d(TAG,"onDateSet: mm/dd/yyy:"+day+"/"+month+"/"+year);
				mDisplayDate.setText("Date:"+day+"/"+month+"/"+year);
				selectDay=day;
				selectYear=year;
				selectMonth=month;

			}
		};
	}
	@Override
	public void onClick(View v) {
		if(v==mDisplayDate)
		{
			Calendar cal= Calendar.getInstance();
			year=cal.get(Calendar.YEAR);
			month=cal.get(Calendar.MONTH);
			day=cal.get(Calendar.DAY_OF_MONTH);
			DatePickerDialog dialog=new DatePickerDialog(Homework_Add.this,android.R.style.Theme_Holo_Light_DarkActionBar,mDatesetListener,year,month,day);
			dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
			dialog.show();
		}
		if(v==btadd)
		if((!ettheDs.getText().toString().equals(""))&&year!=0&&day!=0&&month!=0&&(!s.getSelectedItem().toString().equals("Choose a subject")))
		{

			Intent i = new Intent(this,HomeWork.class);
			/*i.putExtra("day", selectDay).putExtra("month",selectMonth).putExtra("year", selectYear);
			i.putExtra("Ds", ettheDs.getText().toString());
			i.putExtra("sub", s.getSelectedItem().toString());
			i.putExtra("is_there", true);*/

			myDb.insertDataToHomeWork("madrya123",s.getSelectedItem().toString(),ettheDs.getText().toString(),selectDay+"",selectMonth+"",selectYear+"");
			startActivity(i);
			homeworkActivity.finish();
			finish();
		}
	}
}
