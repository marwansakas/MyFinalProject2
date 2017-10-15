package com.example.hp1.myfinalproject.math_stuff;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.hp1.myfinalproject.R;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

public class Graphs extends AppCompatActivity {

    GraphView graphView;
    LineGraphSeries<DataPoint> series1,series2;
    double x,plusY,minusY;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graphs);
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

    }
}
