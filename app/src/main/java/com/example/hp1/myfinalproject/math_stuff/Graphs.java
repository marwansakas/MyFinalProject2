package com.example.hp1.myfinalproject.math_stuff;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.hp1.myfinalproject.R;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

public class Graphs extends AppCompatActivity {

    GraphView graphView;
    LineGraphSeries<DataPoint> series;
    double x,y;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graphs);
        graphView=(GraphView)findViewById(R.id.GraphView);
        x=-5;
        series=new LineGraphSeries<>();
        for (int i=0; i<500;i++){
            x+=0.1;
            y=x*x-10;
            series.appendData(new DataPoint(x,y),true,500);
        }
        graphView.addSeries(series);

    }
}
