package com.example.hp1.myfinalproject.Subjects;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.hp1.myfinalproject.CalendarActivity;
import com.example.hp1.myfinalproject.Explanation;
import com.example.hp1.myfinalproject.Graphs.Circle;
import com.example.hp1.myfinalproject.Login;
import com.example.hp1.myfinalproject.MainActivity;
import com.example.hp1.myfinalproject.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Computer_Sience extends Activity implements AdapterView.OnItemClickListener{

	FirebaseAuth firebaseAuth;
	ListView lvComponents;
	ArrayAdapter<String> adapter;
	String[] Widgets={"TextView","EditText","Button","ImageView","Toast","RadioButton","Checkbox","RatingBar","ProgressBar","more will be added in the near future"};//the widgets

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_computer__sience);
		lvComponents=(ListView)findViewById(R.id.lvComponents);//initialize lvComponents
		adapter=new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,Widgets);//initialize adapter
		lvComponents.setAdapter(adapter);//set adapter to lvComponents
		lvComponents.setOnItemClickListener(this);//make lvCompunents clickacble
		firebaseAuth= FirebaseAuth.getInstance();//to initialize firebaseAuth

	}

	@Override
	public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
		Toast.makeText(this,"be sure to check the next update if they are added",Toast.LENGTH_SHORT).show();//show toast that ther is nothing yet about thee chosen widget
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
	 * if he clicked calendar he will then be sent to calendar activity
	 * @param item thid=s parameter is the item that was clicked on
	 * @return
	 */
	@Override
	public boolean onOptionsItemSelected(MenuItem item)//to make the items for the options menu
	{
		switch (item.getItemId()) {
			case R.id.logOut:
				firebaseAuth.signOut();
				startActivity(new Intent(Computer_Sience.this, Login.class));
				finish();
				return true;
			case R.id.calendar:
				startActivity(new Intent(Computer_Sience.this, CalendarActivity.class));
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
				startActivity(new Intent(Computer_Sience.this, Login.class));
				finish();

		}
		return super.onOptionsItemSelected(item);//return the items for the menu
	}
	/**
	 * go back to explinations page
	 */
	@Override
	public void onBackPressed() {
		super.onBackPressed();
		startActivity(new Intent(this, Explanation.class));
	}
}
