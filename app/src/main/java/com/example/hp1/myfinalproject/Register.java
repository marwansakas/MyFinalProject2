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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.ByteArrayOutputStream;


public class Register extends Activity implements OnClickListener, RadioGroup.OnCheckedChangeListener {

    Button btsubmit;
    String First, Last, EmailString, Pass, Grade, TT, engpoints, mathpoints, Id;
    RadioGroup rgtakhasos, rgmath, rgeng;
    EditText First_Name, Last_Name, Email, Password, safe, ID;
    RadioButton rbtakhasos, rbmath, rbeng;
    Intent intent;

    ProgressDialog progressDialog;
    FirebaseAuth firebaseAuth;
    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        btsubmit = (Button) findViewById(R.id.btsubmit);

        First_Name = (EditText) findViewById(R.id.first);
        Last_Name = (EditText) findViewById(R.id.Last_Name);
        Email = (EditText) findViewById(R.id.Email);
        Password = (EditText) findViewById(R.id.Pass);
        safe = (EditText) findViewById(R.id.Grade);
        ID = (EditText) findViewById(R.id.editText3);

        rgtakhasos = (RadioGroup) findViewById(R.id.radiogroup);
        rgmath = (RadioGroup) findViewById(R.id.mathpoints);
        rgeng = (RadioGroup) findViewById(R.id.englishpoints);

        rbeng = (RadioButton) findViewById(rgeng.getCheckedRadioButtonId());
        rbmath = (RadioButton) findViewById(rgmath.getCheckedRadioButtonId());
        rbtakhasos = (RadioButton) findViewById(rgtakhasos.getCheckedRadioButtonId());

        intent = new Intent(Register.this, MainActivity.class);

        btsubmit.setOnClickListener(this);
        rgtakhasos.setOnCheckedChangeListener(this);
        rgmath.setOnCheckedChangeListener(this);
        rgeng.setOnCheckedChangeListener(this);

        firebaseAuth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference();
        progressDialog = new ProgressDialog(this);
    }

    /**
     *
     * @param v the view that was clicked on
     *          this function lets the user store all the iformation that he added to the firebase to create a new user for him else shows a message
     */
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
                && !(safe.getText().toString().equals("")))
        {
            Pass = Password.getText().toString();
            if(Pass.length()>=6){
            First = First_Name.getText().toString();
            Last = Last_Name.getText().toString();
            EmailString = Email.getText().toString();
            Grade = safe.getText().toString();
            Id = ID.getText().toString();

                progressDialog.setMessage("Registering user...");
                progressDialog.show();
                firebaseAuth.createUserWithEmailAndPassword(EmailString, Pass)
                        .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    progressDialog.dismiss();
                                    Intent intent = new Intent(Register.this, MainActivity.class);
                                    Bundle bundle = new Bundle();
                                    InformationRegistered informationRegistered = new InformationRegistered(Id, First, Last, EmailString, Pass, TT, engpoints, mathpoints, Grade);
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

        //}
        else
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
                    TT = "Chemistry";//to set TT as Chemistry
                    break;
                case R.id.Computer_Seince:
                    TT = "Computer_Science";//to set TT as Computer Science
                    break;
                case R.id.Physics:
                    TT = "Physics";//to set TT as Physics
                    break;
                default:
                    TT = "None";
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