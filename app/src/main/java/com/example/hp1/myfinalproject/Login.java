package com.example.hp1.myfinalproject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
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
    FirebaseAuth firebaseAuth;
    ProgressDialog progressDialog;
    Intent intent;
    SharedPreferences pref;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        btregister = (Button) findViewById(R.id.btregister);//to set what buttons they are by their id
        btlogin = (Button) findViewById(R.id.btlogin);
        etEmail = (EditText) findViewById(R.id.etEmail);
        etpass = (EditText) findViewById(R.id.etpass);

        btlogin.setOnClickListener(this);//to make the button clickable
        btregister.setOnClickListener(this);

        intent = new Intent(this, MainActivity.class);//initialize intent
        pref = getSharedPreferences("mypref",MODE_PRIVATE);
        String em=pref.getString("email",null);
        String pwd=pref.getString("password",null);
        etEmail.setText(em);
        etpass.setText(pwd);
        editor= pref.edit();
        progressDialog = new ProgressDialog(this);//initialize progressDialog
        firebaseAuth = FirebaseAuth.getInstance();//initialize firebaseAuth
    }

    @Override
    public void onClick(View v) {
        if (v == btregister) {
            startActivity(new Intent(this, Register.class));//to start activity Register
            finish();
        } else {
            Login();
           }
    }

    /**
     * this function check if has a user in the database and if so loges the in else shows an apropriat messege
     */
    public void Login(){
        if (!(etpass.getText().toString().equals("")) && !(etEmail.getText().toString().equals(""))) {//checking if the Password or Email are empty
            progressDialog.setMessage("Checking for user...");//set progressDialog messege
            progressDialog.show();//show progressDialog
            firebaseAuth.signInWithEmailAndPassword(etEmail.getText().toString(), etpass.getText().toString())//to see if the user exists
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {

                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            progressDialog.dismiss();//dismissing the progressDialog
                            if (task.isSuccessful()){//if task is successful continue
                                editor.putString("email",etEmail.getText().toString());
                                editor.putString("password",etpass.getText().toString());
                                editor.commit();
                                startActivity(intent);//start MainActivity
                                finish();}
                                else{
                                progressDialog.dismiss();//dismissing the progressDialog
                                Toast.makeText(getApplicationContext(),"Wrong Email Or PassWord",Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        }
        else
            Toast.makeText(Login.this,"fill PassWord And Email",Toast.LENGTH_SHORT).show();//to tell the user that he did not input the password and email

    }
}

