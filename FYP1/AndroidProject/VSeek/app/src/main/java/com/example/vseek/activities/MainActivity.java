package com.example.vseek.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;


//import com.example.vseek.model.Video;
//import com.example.vseek.model.VideoList;
import com.example.vseek.R;
//import com.example.vseek.model.VideoList;
import com.example.vseek.adapters.ThumbnailListAdapter;
import com.example.vseek.models.Video;
import com.example.vseek.models.VideoList;
import com.example.vseek.permissions.Permissions;

import java.util.List;

public class MainActivity extends AppCompatActivity implements ThumbnailListAdapter.OnThumbnailClickListener {

    TextView textView;
    VideoList videoList;
    Permissions permissions;

    RecyclerView recyclerView;
    ThumbnailListAdapter thumbnailListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView=findViewById(R.id.text);

        videoList=new VideoList();

        permissions=new Permissions();
        boolean perm=permissions.mediaPermissions(this,this);
        if (perm) {
            videoList.queryVideos(MainActivity.this);

            List <Video>listOfVideos = videoList.getVideoList();
            Toast.makeText(getApplicationContext(),""+listOfVideos.size(),Toast.LENGTH_SHORT).show();
            for (int i = 0; i < listOfVideos.size(); i++) {
             //   textView.setText(textView.getText()+listOfVideos.get(i).getName() + "\n");
            }

            initRecyclerView();

        }


    }

    private void initRecyclerView() {

        RecyclerView rcv=findViewById(R.id.recycler_view);
        thumbnailListAdapter=new ThumbnailListAdapter(videoList.getVideoList(),this,this);
        rcv.setAdapter(thumbnailListAdapter);
        rcv.setLayoutManager(new GridLayoutManager(this,2));

    }

    @Override
    public void onThumbnailClick(int position) {
        Intent intent=new Intent(MainActivity.this,VideoActivity.class);

        intent.putExtra("uri",videoList.getVideoList().get(position).getUri().toString());

        startActivity(intent);

    }
}
