package com.example.hp1.myfinalproject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Login extends Activity implements OnClickListener{

	Button btregister,btlogin;
	EditText etuser,etpass;
	DataBaseRegister mydb_register;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		btregister=(Button)findViewById(R.id.btregister);//to set what buttons they are by thier id
		btlogin=(Button)findViewById(R.id.btlogin);
		etuser=(EditText)findViewById(R.id.etuser);
		etpass=(EditText)findViewById(R.id.etpass);
		btlogin.setOnClickListener(this);//to make the button clickable
		btregister.setOnClickListener(this);
		mydb_register=new DataBaseRegister(this);//to initialize the sql
	}

	@Override
	public void onClick(View v) {
		if(v==btregister)
		{
			startActivity(new Intent(this,Register.class));//to start activity Register
		}else
		{
			if(mydb_register.check_login(etuser.getText().toString(),etpass.getText().toString())==-1)
				Toast.makeText(getApplicationContext(),"username or password are wrong",Toast.LENGTH_SHORT).show();//to check if the user exists in the database
			else{
				Intent intent=new Intent(this,MainActivity.class);
				intent.putExtra("username from register",etuser.getText().toString());
				startActivity(intent);//to start MainActivity
		}
		}
		
	}
}
