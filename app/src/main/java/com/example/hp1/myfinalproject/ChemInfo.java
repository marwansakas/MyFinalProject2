package com.example.hp1.myfinalproject;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.support.v7.widget.Toolbar;
import android.widget.Toast;

import com.example.hp1.myfinalproject.Graphs.Circle;
import com.example.hp1.myfinalproject.Subjects.Chemistry;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public class ChemInfo extends AppCompatActivity {

    //this activity has been commented all over

    Toolbar tb;
    CollapsingToolbarLayout ctl;
    AppBarLayout appBarLayout;
    FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chem_info);

        Intent intent=getIntent();
        int num=intent.getIntExtra("num",0);
        String[] symbols=getResources().getStringArray(R.array.element_name_eng);
        String symbol=symbols[num];

        tb = (Toolbar) findViewById(R.id.tb);
        ctl = (CollapsingToolbarLayout) findViewById(R.id.collapseit);
        appBarLayout=(AppBarLayout)findViewById(R.id.appbar);
        setSupportActionBar(tb);
        ctl.setTitle(symbol);

        appBarLayout.setBackgroundResource(getResources().getIdentifier(symbol.toLowerCase(),"mipmap",getPackageName()));

        ArrayAdapter<String> adapter=new ArrayAdapter<String>(this,R.layout.support_simple_spinner_dropdown_item);

        String[] infos=getResources().getStringArray(R.array.info);
        String[][] details={getResources().getStringArray(R.array.element_name_eng),getResources().getStringArray(R.array.element_name_latin),getResources().getStringArray(R.array.year),getResources().getStringArray(R.array.CAS_Number)};
        for(int i=0;i<infos.length;i++)
            adapter.add(infos[i]+":"+"\n"+details[i][num]);
        ListView lv=(ListView)findViewById(R.id.lvKnowledge);
        lv.setAdapter(adapter);
        firebaseAuth= FirebaseAuth.getInstance();//to initialize firebaseAuth
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
                startActivity(new Intent(ChemInfo.this, Login.class));
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
                startActivity(new Intent(ChemInfo.this, Login.class));
                finish();
                return true;

        }
        return super.onOptionsItemSelected(item);//return the items for the menu
    }

    /**
     * go back to Chemistry page
     */
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(this, Chemistry.class));
    }

}
