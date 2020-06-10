package com.example.vseek.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.PixelFormat;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.view.autofill.AutofillValue;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.example.vseek.R;

import java.io.IOException;

public class VideoActivity extends AppCompatActivity implements SurfaceHolder.Callback, MediaPlayer.OnPreparedListener, MediaPlayer.OnCompletionListener, View.OnClickListener {

    VideoView videoView;
    MediaPlayer mp;
    TextView textView;
    Uri uri;
    private SurfaceView surfaceViewFrame;
    private SurfaceHolder holder;
    //  private ProgressBar progressBarWait;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video);
        //videoView = findViewById(R.id.videoView);
        textView=findViewById(R.id.temptext);
        Intent intent = getIntent();
        String uristr = intent.getStringExtra("uri");
        uri = Uri.parse(uristr);
        mp = new MediaPlayer();

        surfaceViewFrame = (SurfaceView) findViewById(R.id.surfaceViewFrame);
        surfaceViewFrame.setOnClickListener(this);
        surfaceViewFrame.setClickable(false);

      //  progressBarWait = (ProgressBar) findViewById(R.id.progressBarWait);

        getWindow().setFormat(PixelFormat.UNKNOWN);


        holder = surfaceViewFrame.getHolder();
        holder.addCallback(this);
        holder.setFixedSize(176, 144);
        holder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);

        mp.setOnPreparedListener(this);
        mp.setOnCompletionListener(this);
        mp.setScreenOnWhilePlaying(true);
      //  mp.setDisplay(holder);



      //  videoView.setVideoURI(uri);
        //videoView.start();

        try {
            mp.setDataSource(getApplicationContext(),uri);
        } catch (IOException e) {
            e.printStackTrace();
        }




    }

    @Override
    public void onCompletion(MediaPlayer mp) {
        mp.stop();

    }




    @Override
    public void onPrepared(MediaPlayer mp) {



        /*
         *  Handle aspect ratio
         */
        int surfaceView_Width = surfaceViewFrame.getWidth();
        int surfaceView_Height = surfaceViewFrame.getHeight();

        float video_Width = mp.getVideoWidth();
        float video_Height = mp.getVideoHeight();

        float ratio_width = surfaceView_Width/video_Width;
        float ratio_height = surfaceView_Height/video_Height;
        float aspectratio = video_Width/video_Height;

        ViewGroup.LayoutParams layoutParams = surfaceViewFrame.getLayoutParams();

        if (ratio_width > ratio_height){
            layoutParams.width = (int) (surfaceView_Height * aspectratio);
            layoutParams.height = surfaceView_Height;
        }else{
            layoutParams.width = surfaceView_Width;
            layoutParams.height = (int) (surfaceView_Width / aspectratio);
        }

        surfaceViewFrame.setLayoutParams(layoutParams);


        if (!mp.isPlaying()) {
            mp.start();
        }
        surfaceViewFrame.setClickable(true);

    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        playVideo();

    }

    private void playVideo() {
        mp.setDisplay(holder);
        mp.reset();
        new Thread(new Runnable() {
            public void run() {
                try {
                    mp.setDataSource(getApplicationContext(), uri);
                    mp.prepare();
                } catch (Exception e) { // I can split the exceptions to get which error i need.
                  //  showToast("Error while playing video");
                   // Log.i(TAG, "Error");
                    e.printStackTrace();
                }
            }
        }).start();
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        mp.stop();
    }

    @Override
    public void onClick(View v) {

    }


    @Override
    protected void onDestroy() {
        mp.stop();
        super.onDestroy();

    }
}



