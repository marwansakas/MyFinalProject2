package com.example.hp1.myfinalproject.math_stuff;

import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TableLayout;

import com.example.hp1.myfinalproject.R;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

public class Graphs extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    GraphView graphView;
    LineGraphSeries<DataPoint> series1,series2;
    double x,plusY,minusY;

    DrawerLayout drawerLayout;
    ActionBarDrawerToggle actionBarDrawerToggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graphs);

        drawerLayout=(DrawerLayout)findViewById(R.id.drawerLayout);
        actionBarDrawerToggle=new ActionBarDrawerToggle(this, drawerLayout,R.string.navigation_drawer_open,R.string.navigation_drawer_close);

        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        graphView=(GraphView)findViewById(R.id.GraphView);//initialize graphView
        x=-5.1;//set the first x
        series1=new LineGraphSeries<>();//initialize series1
        series2=new LineGraphSeries<>();//initialize series2
        for (int i=0; i<500;i++){//this is to add the points in the graph
            x+=0.1;//to increas x by 0.1
            plusY=Math.sqrt(25-Math.pow(x,2));//to set plusY
            minusY=-plusY;//to set minnusY
            series1.appendData(new DataPoint(x,plusY),true,500);//to add the point (x,plusY)in series1
            series2.appendData(new DataPoint(x,minusY),true,500);//to add the point (x,minusY)in series2
        }
        graphView.addSeries(series1);//to add series1 to graphView
        graphView.addSeries(series2);//to add series2 to graphView

        NavigationView navigationView=(NavigationView)findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        actionBarDrawerToggle.onOptionsItemSelected(item);
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        return true;
    }
}
