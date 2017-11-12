package com.example.hp1.myfinalproject;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.github.sundeepk.compactcalendarview.CompactCalendarView;
import com.github.sundeepk.compactcalendarview.domain.Event;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class CalendarActivity extends AppCompatActivity {

    CompactCalendarView compactCalendar;
    private SimpleDateFormat dateFormatMonth=new SimpleDateFormat("MMMM - yyyy", Locale.getDefault());

    ArrayList<Event> birthdays=new ArrayList<>();
    ArrayList<Event> tests=new ArrayList<>();
    ArrayList<Event> holidays=new ArrayList<>();

    DatabaseReference databaseReferenceTests;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);

        final ActionBar actionBar=getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(false);
        actionBar.setTitle(null);

        compactCalendar=(CompactCalendarView) findViewById(R.id.compactcalendar_view);
        compactCalendar.setUseThreeLetterAbbreviation(true);

        String myDate = "2017/11/29 00:00:00";
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        java.util.Date date = null;
        try {
            date = sdf.parse(myDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        long millis = date.getTime();

        Event ev1 = new Event(Color.BLUE,millis , "Teachers' Professional Day");
        compactCalendar.addEvent(ev1);

        databaseReferenceTests= FirebaseDatabase.getInstance().getReference("Tests");
       /* databaseReferenceTests.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot dataSnapshotTests:dataSnapshot.getChildren()){
                    CalendarEventDetails eventDetails=dataSnapshotTests.getValue(CalendarEventDetails.class);
                    Event eventTest=EventCreator(Color.BLUE,eventDetails);
                    compactCalendar.addEvent(eventTest);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });*/

        compactCalendar.setListener(new CompactCalendarView.CompactCalendarViewListener() {
            @Override
            public void onDayClick(java.util.Date dateClicked) {

                List<Event> events=compactCalendar.getEvents(dateClicked);

                if(events.size()>0)
                {
                    Toast.makeText(getApplicationContext(),events.get(0).getData().toString(),Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onMonthScroll(java.util.Date firstDayOfNewMonth) {
                actionBar.setTitle(dateFormatMonth.format(firstDayOfNewMonth));
            }


        });
    }

    public Event EventCreator(int color,CalendarEventDetails eventDetails){
        String myDate=eventDetails.getDate().convertToString()+" "+eventDetails.getHourClock().convertToString();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        java.util.Date date = null;
        try {
            date = sdf.parse(myDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        long millis = date.getTime();

        Event ev1=new Event(color,millis,eventDetails.getDetails());
        return ev1;
    }
}
