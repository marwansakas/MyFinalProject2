package com.example.hp1.myfinalproject.Graphs;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
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
import android.widget.Toast;

import com.example.hp1.myfinalproject.CalendarActivity;
import com.example.hp1.myfinalproject.Login;
import com.example.hp1.myfinalproject.MainActivity;
import com.example.hp1.myfinalproject.R;
import com.google.firebase.auth.FirebaseAuth;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

public class SinGraph extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    GraphView graph;
    private final int GRAPHSIZE=50000;
    double y,x=-1000;
    FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sin_graph);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        firebaseAuth= FirebaseAuth.getInstance();//to initialize firebaseAuth

        graph = (GraphView) findViewById(R.id.graph);

        // set manual X bounds
        graph.getViewport().setYAxisBoundsManual(true);
        graph.getViewport().setMinY(-1);
        graph.getViewport().setMaxY(1);

        graph.getViewport().setXAxisBoundsManual(true);
        graph.getViewport().setMinX(-5);
        graph.getViewport().setMaxX(5);

        // enable scaling and scrolling
        graph.getViewport().setScalable(true);
        graph.getViewport().setScalableY(true);
        graph.getViewport().setScrollable(true); // enables horizontal scrolling

        LineGraphSeries<DataPoint> series = new LineGraphSeries<>();
        for(int i=0;i<GRAPHSIZE;i++)
        {
            y= Math.sin(x);
            series.appendData(new DataPoint(x,y),true,GRAPHSIZE);
            x=x+0.1;
        }
        graph.addSeries(series);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(SinGraph.this);
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
            startActivity(new Intent(this,LineGraph.class));
            finish();
        } else if (id == R.id.nav_parabol) {
            startActivity(new Intent(this,Parabola.class));
            finish();
        } else if (id == R.id.nav_sin) {
            Toast.makeText(getApplicationContext(),"You are here",Toast.LENGTH_SHORT).show();

        } else if (id == R.id.nav_cos) {
            startActivity(new Intent(this,SinGraph.class));
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
     * if he clicked calendar he will then be sent to calendar activity
     * @param item thid=s parameter is the item that was clicked on
     * @return
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item)//to make the items for the options menu
    {
        switch (item.getItemId()) {
            case R.id.logOut:
                firebaseAuth.signOut();
                startActivity(new Intent(SinGraph.this, Login.class));
                finish();
                return true;
            case R.id.calendar:
                startActivity(new Intent(SinGraph.this, CalendarActivity.class));
                return true;

        }
        return super.onOptionsItemSelected(item);//return the items for the menu
    }
}
