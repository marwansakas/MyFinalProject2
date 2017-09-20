package com.example.hp1.myfinalproject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.ByteArrayOutputStream;


public class Register extends Activity implements OnClickListener, RadioGroup.OnCheckedChangeListener {
    Button btsubmit;
    String First, Last, EmailString, Pass, Grade, TT, engpoints, mathpoints, Id;
    RadioGroup rgtakhasos, rgmath, rgeng;
    EditText First_Name, Last_Name, Email, Password, alame, ID;
    RadioButton rbtakhasos, rbmath, rbeng;
    Intent intent;
    DataBaseRegister myDb;
    byte[] image;

    ProgressDialog progressDialog;
    FirebaseAuth firebaseAuth;
    DatabaseReference databaseReference;

    String[] exists = {"id", "username", "password"};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        firebaseAuth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference();
        progressDialog = new ProgressDialog(this);
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.nopicture);//to set bitmap as no picture
        ByteArrayOutputStream bs = new ByteArrayOutputStream();//to initialize ByteArrayOutputStream
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bs);//to compress bitmap to bs(ByteArrayOutputStream)
        image = bs.toByteArray();//to turn the ByteArrayOutputStream to byte Array
        myDb = new DataBaseRegister(this);//to initialize myDb
        intent = new Intent(Register.this, MainActivity.class);
        btsubmit = (Button) findViewById(R.id.btsubmit);
        btsubmit.setOnClickListener(this);
        rgtakhasos = (RadioGroup) findViewById(R.id.radiogroup);
        rgmath = (RadioGroup) findViewById(R.id.mathpoints);
        rgeng = (RadioGroup) findViewById(R.id.englishpoints);
        rgtakhasos.setOnCheckedChangeListener(this);
        rgmath.setOnCheckedChangeListener(this);
        rgeng.setOnCheckedChangeListener(this);
        First_Name = (EditText) findViewById(R.id.first);
        Last_Name = (EditText) findViewById(R.id.Last_Name);
        Email = (EditText) findViewById(R.id.Email);
        Password = (EditText) findViewById(R.id.Pass);
        alame = (EditText) findViewById(R.id.Grade);
        rbeng = (RadioButton) findViewById(rgeng.getCheckedRadioButtonId());
        rbmath = (RadioButton) findViewById(rgmath.getCheckedRadioButtonId());
        rbtakhasos = (RadioButton) findViewById(rgtakhasos.getCheckedRadioButtonId());
        ID = (EditText) findViewById(R.id.editText3);

    }

    @Override
    public void onClick(View v) {
        if ((!First_Name.getText().toString().equals(""))
                && !(Last_Name.getText().toString().equals(""))
                && !(Email.getText().toString().equals(""))
                && !(Password.getText().toString().equals(""))
                && !(rgtakhasos.getCheckedRadioButtonId() == -1)
                && !(rgeng.getCheckedRadioButtonId() == -1)
                && !(rgmath.getCheckedRadioButtonId() == -1)
                && !(ID.getText().toString().equals(""))
                && !(alame.getText().toString().equals(""))
                && !(ID.getText().toString().equals("")))//to check if the inputted all the information
        {
            Pass = Password.getText().toString();
            if(Pass.length()>=6){
            First = First_Name.getText().toString();
            Last = Last_Name.getText().toString();
            EmailString = Email.getText().toString();
            Grade = alame.getText().toString();
            Id = ID.getText().toString();//to turn all the information to string
            int find = myDb.search_nomatch(Id, EmailString, Pass);
            if (find > 0) //to check if there is not another match
            {
                Toast.makeText(getApplicationContext(), exists[find - 1] + "already exists", Toast.LENGTH_SHORT).show();//if there is a match tell him and change what matches
            } else {

                progressDialog.setMessage("Registering user...");
                progressDialog.show();
                firebaseAuth.createUserWithEmailAndPassword(EmailString, Pass)
                        .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    Intent intent = new Intent(Register.this, MainActivity.class);
                                    Bundle bundle = new Bundle();
                                    InformationRegistered informationRegistered = new InformationRegistered(Id, First, Last, EmailString, Pass, TT, Integer.parseInt(engpoints), Integer.parseInt(mathpoints), Integer.parseInt(Grade));
                                    bundle.putSerializable("information Registered",informationRegistered);
                                    intent.putExtras(bundle);
                                    intent.putExtra("checking for user",true);
                                    startActivity(intent);//to start activity
                                    finish();
                                }
                            }
                        });

                intent.putExtra("username from register", EmailString);

                }
            }

        } else
            Toast.makeText(getApplicationContext(), "Please fill all the required information", Toast.LENGTH_SHORT).show();//to tell the user to fill all the required information

    }

    @Override
    public void onCheckedChanged(RadioGroup radioGroup, @IdRes int i) {
        if (radioGroup == rgtakhasos) {//to check what he selected in the radioGroup rgtakhasos
            switch (i) {
                case R.id.Media:
                    TT = "Media";//to set TT as Media
                    break;
                case R.id.Biology:
                    TT = "Biology";//to set TT as Biology
                    break;
                case R.id.Chemistry:
                    TT = "Chemistry";
                    break;
                case R.id.Computer_Seince:
                    TT = "Computer Seince";//to set TT as Chemistry
                    break;
                case R.id.Physics:
                    TT = "Physics";//to set TT as Physics
                    break;
            }
        } else if (radioGroup == rgeng)//to check what he selected in the radioGroup rgeng
        {
            switch (i) {
                case R.id.eng4:
                    engpoints = "4";//to set engpoints as 4
                    break;
                case R.id.eng5:
                    engpoints = "5";//to set engpoints as 5
                    break;
            }
        } else if (radioGroup == rgmath) {//to check what he selected in the radioGroup rgmath
            switch (i) {
                case R.id.math4:
                    mathpoints = "4";//to set mathpoints as 4
                    break;
                case R.id.math5:
                    mathpoints = "5";//to set mathpoints as 4
            }
        }
    }
}