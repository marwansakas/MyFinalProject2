package com.example.hp1.myfinalproject;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.MediaController;
import android.widget.VideoView;

import com.google.firebase.auth.FirebaseAuth;

public class Madaneyat_Video extends AppCompatActivity{

    //this activity has been commented all over

    VideoView videoView;
    MediaController mediaController;
    Intent intent;
    FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_madaneyat__video);
        videoView=(VideoView)findViewById(R.id.videoView);
        mediaController=new MediaController(this);
        intent=getIntent();
        String videopath=intent.getStringExtra("videoPath");
        Uri uri = Uri.parse(videopath);
        videoView.setVideoURI(uri);
        videoView.setMediaController(mediaController);
        mediaController.setAnchorView(videoView);
        firebaseAuth= FirebaseAuth.getInstance();//to initialize firebaseAuth

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
                startActivity(new Intent(Madaneyat_Video.this, Login.class));
                finish();
                return true;
            case R.id.calendar:
                startActivity(new Intent(Madaneyat_Video.this, CalendarActivity.class));
                return true;

        }
        return super.onOptionsItemSelected(item);//return the items for the menu
    }
}
