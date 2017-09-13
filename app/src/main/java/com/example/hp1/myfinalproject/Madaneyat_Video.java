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
        videoView=(VideoView)findViewById(R.id.videoView);
        mediaController=new MediaController(this);
        intent=getIntent();
        String videopath=intent.getStringExtra("videoPath");
        Uri uri = Uri.parse(videopath);
        videoView.setVideoURI(uri);
        videoView.setMediaController(mediaController);
        mediaController.setAnchorView(videoView);

    }
}
