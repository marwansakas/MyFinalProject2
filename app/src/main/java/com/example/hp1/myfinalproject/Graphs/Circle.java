package com.example.hp1.myfinalproject.Graphs;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.hp1.myfinalproject.CalendarActivity;
import com.example.hp1.myfinalproject.Explanation;
import com.example.hp1.myfinalproject.Login;
import com.example.hp1.myfinalproject.MainActivity;
import com.example.hp1.myfinalproject.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

public class Circle extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    EditText etA,etB,etR;
    GraphView graph;
    private final int GRAPHSIZE=50000;
    double plusY,minusY,x=-1000;
    FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_circle);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        etA=(EditText)findViewById(R.id.etA);
        etB=(EditText)findViewById(R.id.etB);
        etR=(EditText)findViewById(R.id.etR);
        graph = (GraphView) findViewById(R.id.graph);

        firebaseAuth= FirebaseAuth.getInstance();//to initialize firebaseAuth

        // set manual X bounds
        graph.getViewport().setYAxisBoundsManual(true);
        graph.getViewport().setMinY(-150);
        graph.getViewport().setMaxY(150);

        graph.getViewport().setXAxisBoundsManual(true);
        graph.getViewport().setMinX(4);
        graph.getViewport().setMaxX(80);

        // enable scaling and scrolling
        graph.getViewport().setScalable(true);
        graph.getViewport().setScalableY(true);
        graph.getViewport().setScrollable(true); // enables horizontal scrolling
        graph.getViewport().setScrollableY(true); // enables vertical scrolling

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String stA,stB,stR;
                stA=etA.getText().toString();
                stB=etB.getText().toString();
                stR=etR.getText().toString();
                if(stA.equals("")||stB.equals("")){
                    Toast.makeText(getApplicationContext(),"Please fill all the requird information",Toast.LENGTH_SHORT).show();
                }
                else {
                    double a=Double.parseDouble(stA);
                    double b=Double.parseDouble(stB);
                    double r=Double.parseDouble(stR);
                    LineGraphSeries<DataPoint> series1 = new LineGraphSeries<>(),series2 = new LineGraphSeries<>();
                    for(int i=0;i<GRAPHSIZE;i++)
                    {
                        plusY=b+Math.sqrt(r*r-Math.pow(x-a,2));
                        minusY=b+Math.sqrt(r*r+Math.pow(x-a,2));
                        series1.appendData(new DataPoint(x,plusY),true,2*GRAPHSIZE);
                        series2.appendData(new DataPoint(x,minusY),true,2*GRAPHSIZE);
                        x=x+0.1;
                    }
                    graph.addSeries(series1);
                    graph.addSeries(series2);
                }
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    /*
     * if navigation drawer is opened close it else go to the last open activity
     */
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
            startActivity(new Intent(this, Explanation.class));
        }
    }

    /**
     * open navigation drawer and choose what activity to go to
     * @param item
     * @return
     */
    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_line_graph) {
            startActivity(new Intent(this,LineGraph.class));
            finish();
        } else if (id == R.id.nav_parabol) {
            Toast.makeText(getApplicationContext(),"You are here",Toast.LENGTH_SHORT).show();
        } else if (id == R.id.nav_sin) {
            startActivity(new Intent(this,SinGraph.class));
            finish();
        } else if (id == R.id.nav_cos) {
            startActivity(new Intent(this,CosGraph.class));
            finish();
        } else if (id == R.id.nav_tan) {
            startActivity(new Intent(this,TanGraph.class));
            finish();
        } else if (id == R.id.nav_parabola) {
            startActivity(new Intent(this,ParabolaGraph.class));
            finish();
        }
        else if (id == R.id.nav_circle) {
            startActivity(new Intent(this,Circle.class));
            finish();
        }
        else if (id == R.id.nav_elipsa) {
            startActivity(new Intent(this,Elipsa.class));
            finish();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
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
                startActivity(new Intent(Circle.this, Login.class));
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
                startActivity(new Intent(Circle.this, Login.class));
                finish();
                return true;

        }
        return super.onOptionsItemSelected(item);//return the items for the menu
    }


}
