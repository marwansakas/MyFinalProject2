package com.example.hp1.myfinalproject.Graphs;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import com.example.hp1.myfinalproject.R;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

public class LineGraph extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener,View.OnClickListener {

    GraphView graph;
    FloatingActionButton fab;
    Toolbar toolbar;
    private final int GRAPHSIZE=50000;
    double y,x=-1000;
    EditText etA,etB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_line_graph);

        etA=(EditText)findViewById(R.id.etA);
        etB=(EditText)findViewById(R.id.etB);
        graph = (GraphView) findViewById(R.id.graph);
        toolbar = (Toolbar) findViewById(R.id.toolbar);

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

        if (id == R.id.nav_line_graph) {
            Toast.makeText(getApplicationContext(),"You are here",Toast.LENGTH_SHORT).show();
        } else if (id == R.id.nav_parabol) {
            startActivity(new Intent(this,Parabola.class));
            finish();
        } else if (id == R.id.nav_sin) {
            startActivity(new Intent(this,SinGraph.class));
            finish();
        } else if (id == R.id.nav_cos) {
            finish();
        } else if (id == R.id.nav_tan) {
            finish();
        } else if (id == R.id.nav_parabola) {
            finish();
        }
        else if (id == R.id.nav_circle) {
            finish();
        }
        else if (id == R.id.nav_elipsa) {
            finish();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    /**
     * shows the graph
     * @param view the view that was clicked on
     */
    @Override
    public void onClick(View view) {
        String stA,stB;
        stA=etA.getText().toString();
        stB=etB.getText().toString();
        if(stA.equals("")||stB.equals("")){
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
