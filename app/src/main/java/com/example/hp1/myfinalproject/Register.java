package com.example.hp1.myfinalproject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
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

import com.example.hp1.myfinalproject.JavaClasses.InformationRegistered;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class Register extends Activity implements OnClickListener, RadioGroup.OnCheckedChangeListener {

    Button btsubmit;
    String First, Last, EmailString, Pass, Grade, TT, engpoints, mathpoints, Id,section;
    RadioGroup rgtakhasos, rgmath, rgeng,rggrade,rgsection;
    EditText First_Name, Last_Name, Email, Password, ID;
    RadioButton rbtakhasos, rbmath, rbeng,rbgrade,rbsection;
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
        ID = (EditText) findViewById(R.id.editText3);

        rgtakhasos = (RadioGroup) findViewById(R.id.radiogroup);
        rgmath = (RadioGroup) findViewById(R.id.mathpoints);
        rgeng = (RadioGroup) findViewById(R.id.englishpoints);
        rggrade = (RadioGroup) findViewById(R.id.grade);
        rgsection = (RadioGroup) findViewById(R.id.section);

        rbeng = (RadioButton) findViewById(rgeng.getCheckedRadioButtonId());
        rbmath = (RadioButton) findViewById(rgmath.getCheckedRadioButtonId());
        rbtakhasos = (RadioButton) findViewById(rgtakhasos.getCheckedRadioButtonId());
        rbgrade = (RadioButton) findViewById(rggrade.getCheckedRadioButtonId());
        rbsection = (RadioButton) findViewById(rgsection.getCheckedRadioButtonId());


        intent = new Intent(Register.this, MainActivity.class);

        btsubmit.setOnClickListener(this);
        rgtakhasos.setOnCheckedChangeListener(this);
        rgmath.setOnCheckedChangeListener(this);
        rgeng.setOnCheckedChangeListener(this);
        rggrade.setOnCheckedChangeListener(this);
        rgsection.setOnCheckedChangeListener(this);

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
                && !(rggrade.getCheckedRadioButtonId() == -1)
                && !(rgsection.getCheckedRadioButtonId() == -1))
        {
            Pass = Password.getText().toString();
            if(Pass.length()>=6){
            First = First_Name.getText().toString();
            Last = Last_Name.getText().toString();
            EmailString = Email.getText().toString();
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
                                    InformationRegistered informationRegistered = new InformationRegistered(Id, First, Last, EmailString, Pass, TT, engpoints, mathpoints, Grade,section);
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

        else
            Toast.makeText(getApplicationContext(), "Please fill all the required information", Toast.LENGTH_SHORT).show();//to tell the user to fill all the required information

    }

    /*
     * allows the user to choose what subject he specialises in, nume of math points, number of english points
     */
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
        else
        if (radioGroup == rggrade) {//to check what he selected in the radioGroup rgtakhasos
            switch (i) {
                case R.id.grade9:
                    Grade = "9";//to set TT as Media
                    break;
                case R.id.grade10:
                    Grade = "10";//to set TT as Biology
                    break;
                case R.id.grade11:
                    Grade = "11";//to set TT as Chemistry
                    break;
                case R.id.grade12:
                    Grade = "12";//to set TT as Computer Science
                    break;
                default:
                    Grade = "None";
                    break;
            }}
            else
            if (radioGroup == rgsection) {//to check what he selected in the radioGroup rgtakhasos
                switch (i) {
                    case R.id.sectionA:
                        section = "A";//to set TT as Media
                        break;
                    case R.id.sectionB:
                        section = "B";//to set TT as Media
                        break;
                    case R.id.sectionC:
                        section = "C";//to set TT as Media
                        break;
                    case R.id.sectionD:
                        section = "D";//to set TT as Media
                        break;
                    case R.id.sectionH:
                        section = "H";//to set TT as Media
                        break;
                    default:
                        section = "X";
                        break;
                }
            }
        }
    }
