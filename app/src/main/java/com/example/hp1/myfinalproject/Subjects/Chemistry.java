package com.example.hp1.myfinalproject.Subjects;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ScrollView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hp1.myfinalproject.CalendarActivity;
import com.example.hp1.myfinalproject.ChemInfo;
import com.example.hp1.myfinalproject.Explanation;
import com.example.hp1.myfinalproject.Graphs.Circle;
import com.example.hp1.myfinalproject.JavaClasses.ChemView;
import com.example.hp1.myfinalproject.Login;
import com.example.hp1.myfinalproject.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Chemistry extends AppCompatActivity implements View.OnClickListener{

	TextView tvVEB;
	TableRow tr;
	TableLayout t1;
	TableRow.LayoutParams tableRowParams;
	ScrollView scr;
	final int EL_SIZE=200;
	int num=0;
	FirebaseAuth firebaseAuth;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_chemistry);
		tvVEB=(TextView)findViewById(R.id.tv_VEB_School);//initialize tvVEB
		tvVEB.setClickable(true);//make tvVEB clickable
		tvVEB.setMovementMethod(LinkMovementMethod.getInstance());
		String text = "<a href='https://www.facebook.com/groups/VEBSchool.chemistry/'> VEB School </a>";//the web location
		tvVEB.setText(Html.fromHtml(text));//set the Html
		firebaseAuth= FirebaseAuth.getInstance();//to initialize firebaseAuth

		scr=(ScrollView)findViewById(R.id.scrollView1);

		t1=new TableLayout(this);

		tableRowParams=new TableRow.LayoutParams(TableRow.LayoutParams.FILL_PARENT,TableRow.LayoutParams.FILL_PARENT);
		tableRowParams.gravity= Gravity.RIGHT | Gravity.CENTER_VERTICAL;

		First_Row();
		SecondThird();
		Rest_Of_The_Rows();
		SeparateTable();
		scr.addView(t1);
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
				startActivity(new Intent(Chemistry.this, Login.class));
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
				startActivity(new Intent(Chemistry.this, Login.class));
				finish();
				return true;

		}
		return super.onOptionsItemSelected(item);//return the items for the menu
	}

	public void First_Row(){
		tr=new TableRow(this);

		ChemView chemView1=new ChemView(this,num);
		chemView1.setLayoutParams(new TableRow.LayoutParams(EL_SIZE,EL_SIZE));
		chemView1.setId(num);
		chemView1.setOnClickListener(this);
		num++;

		ChemView chemView2=new ChemView(this,num);
		chemView2.setLayoutParams(new TableRow.LayoutParams(EL_SIZE,EL_SIZE));
		chemView2.setX(EL_SIZE*16);
		chemView2.setId(num);
		chemView2.setOnClickListener(this);
		num++;

		tr.addView(chemView1);
		tr.addView(chemView2);
		t1.addView(tr,tableRowParams);
	}

	public void SecondThird(){
		for(int j=0;j<2;j++) {
			tr = new TableRow(this);
			for (int i = 0; i < 8; i++) {
				ChemView chemView1 = new ChemView(this,num);
				chemView1.setLayoutParams(new TableRow.LayoutParams(EL_SIZE, EL_SIZE));
				if(i>1)
					chemView1.setX(EL_SIZE*10);
				chemView1.setId(num);
				chemView1.setOnClickListener(this);
				tr.addView(chemView1);
				num++;
			}
			t1.addView(tr, tableRowParams);
		}
	}

	public void Rest_Of_The_Rows(){
		for(int j=0;j<4;j++) {
			tr = new TableRow(this);
			for (int i = 0; i < 18; i++) {
				ChemView chemView1 = new ChemView(this,num);
				chemView1.setLayoutParams(new TableRow.LayoutParams(EL_SIZE, EL_SIZE));
				chemView1.setId(num);
				chemView1.setOnClickListener(this);
				tr.addView(chemView1);
				num++;
				if(num==57)
					num=71;
				if(num==89)
					num=103;
			}
			t1.addView(tr, tableRowParams);
		}
	}

	public void SeparateTable(){
		num=57;
		for(int j=0;j<2;j++) {
			tr = new TableRow(this);
			for (int i = 0; i < 14; i++) {
				ChemView chemView1 = new ChemView(this,num);
				chemView1.setLayoutParams(new TableRow.LayoutParams(EL_SIZE, EL_SIZE));
				chemView1.setX(EL_SIZE);
				chemView1.setY(40);
				chemView1.setId(num);
				chemView1.setOnClickListener(this);
				tr.addView(chemView1);
				num++;
			}
			num=89;
			t1.addView(tr, tableRowParams);
		}
	}

	@Override
	public void onClick(View view) {
		Intent intent=new Intent(this,ChemInfo.class);
		for(int i=0;i<118;i++)
			if(view.getId()==i)
				intent.putExtra("num",i);
		startActivity(intent);
		finish();
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
