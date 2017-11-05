package com.example.hp1.myfinalproject;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

public class LineGraph extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener,View.OnClickListener {

    GraphView graph;
    FloatingActionButton fab;
    private final int GRAPHSIZE=20000000;
    double y,x=-GRAPHSIZE/2;
    EditText etA,etB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_line_graph);

        etA=(EditText)findViewById(R.id.etA);
        etB=(EditText)findViewById(R.id.etB);
        graph = (GraphView) findViewById(R.id.graph);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(this);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onClick(View view) {
        Toast.makeText(getApplicationContext(),"Please fill all the requird information",Toast.LENGTH_SHORT).show();
        String stA,stB;
        stA=etA.getText().toString();
        stB=etB.getText().toString();
        if(stA.equals("")&&stB.equals("")){
            Toast.makeText(getApplicationContext(),"Please fill all the requird information",Toast.LENGTH_SHORT).show();
        }
        else {
            double a=Double.parseDouble(stA),b=Double.parseDouble(stB);
            LineGraphSeries<DataPoint> series = new LineGraphSeries<>();
            for(int i=0;i<GRAPHSIZE;i++)
            {
                y=a*x+b;
                series.appendData(new DataPoint(x,y),true,GRAPHSIZE);
                x=x+0.1;
            }
            graph.addSeries(series);
        }
    }
}
