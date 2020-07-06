package com.example.grupo9pdm115.Activities.Utilidades;

import androidx.appcompat.app.AppCompatActivity;

import android.net.Uri;
import android.os.Bundle;
import android.widget.MediaController;
import android.widget.VideoView;

import com.example.grupo9pdm115.R;

public class VideoActivity extends AppCompatActivity {

    VideoView video;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video);
        video = (VideoView) findViewById(R.id.video);
        video.setVideoURI(Uri.parse("android.resource://"+ getPackageName() + "/"+R.raw.pdm));
        MediaController media = new MediaController(this);
        video.setMediaController(media);
        video.start();
    }
}
