package com.example.hp1.myfinalproject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Login extends Activity implements OnClickListener {

    Button btregister, btlogin;
    EditText etEmail, etpass;
    DataBaseRegister mydb_register;
    FirebaseAuth firebaseAuth;
    ProgressDialog progressDialog;
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        intent = new Intent(this, MainActivity.class);
        progressDialog = new ProgressDialog(this);
        firebaseAuth = FirebaseAuth.getInstance();
        btregister = (Button) findViewById(R.id.btregister);//to set what buttons they are by thier id
        btlogin = (Button) findViewById(R.id.btlogin);
        etEmail = (EditText) findViewById(R.id.etEmail);
        etpass = (EditText) findViewById(R.id.etpass);
        btlogin.setOnClickListener(this);//to make the button clickable
        btregister.setOnClickListener(this);
        mydb_register = new DataBaseRegister(this);//to initialize the sql
    }

    @Override
    public void onClick(View v) {
        if (v == btregister) {
            startActivity(new Intent(this, Register.class));//to start activity Register
        } else {
            /*if(mydb_register.check_login(etEmail.getText().toString(),etpass.getText().toString())==-1)
				Toast.makeText(getApplicationContext(),"username or password are wrong",Toast.LENGTH_SHORT).show();//to check if the user exists in the database
			else{
				Intent intent=new Intent(this,MainActivity.class);
				intent.putExtra("username from register",etEmail.getText().toString());
				startActivity(intent);//to start MainActivity

				finish();*/
            if (!(etpass.getText().toString().equals("")) && !(etEmail.getText().toString().equals(""))) {
                progressDialog.setMessage("Checking for user...");
                progressDialog.show();
                firebaseAuth.signInWithEmailAndPassword(etEmail.getText().toString(), etpass.getText().toString())
                        .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {

                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                progressDialog.dismiss();
                                if (task.isSuccessful())
                                    startActivity(intent);
                            }
                        });
            }
        }
    }

}

