package com.example.hp1.myfinalproject;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.MediaController;
import android.widget.VideoView;

public class Madaneyat_Video extends AppCompatActivity{

    VideoView videoView;
    MediaController mediaController;
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_madaneyat__video);
        videoView=(VideoView)findViewById(R.id.videoView);//initialize videoView
        mediaController=new MediaController(this);//initialize mediaController
        intent=getIntent();//get the intent
        String videopath=intent.getStringExtra("videoPath");//get the videoPath from the intent
        Uri uri = Uri.parse(videopath);//convert the string value to uri
        videoView.setVideoURI(uri);//set the uri value in videoView
        videoView.setMediaController(mediaController);//set mediaController
        mediaController.setAnchorView(videoView);//set mediaController for the videoView

    }
}
