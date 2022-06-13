package org.nyeong.lipo;

import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import android.widget.VideoView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class VideolistActivity extends AppCompatActivity implements View.OnClickListener {

    FirebaseStorage storage = FirebaseStorage.getInstance("gs://lipo-cf566.appspot.com");
    StorageReference storageRef = storage.getReference();

    private VideoView videoView;
    private Button btnStart;
    private Button btnResume;
    private Button btnPause;
    private Button btnStop;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_videolist);

        videoView = findViewById(R.id.videoView);
        btnStart = findViewById(R.id.btnStart);
        btnResume = findViewById(R.id.btnResume);
        btnPause = findViewById(R.id.btnPause);
        btnStop = findViewById(R.id.btnStop);

        Intent intent = getIntent();
        String filename = intent.getStringExtra("filename");
        System.out.println(filename);

        StorageReference videoRef = storageRef.child(filename);
        videoRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                videoView.setVideoURI(uri);
            }
        });

        btnStart.setOnClickListener(this);
        btnResume.setOnClickListener(this);
        btnPause.setOnClickListener(this);
        btnStop.setOnClickListener(this);

        videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                Toast.makeText(getApplicationContext(), "동영상 재생 준비 완료", Toast.LENGTH_SHORT).show();
                videoView.start();      // 동영상 재개
            }
        });

        videoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                Toast.makeText(getApplicationContext(), "동영상 시청 완료", Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnStart:
                videoView.start();      // 동영상 재개
                break;

            case R.id.btnResume:
                videoView.resume();     // 동영상 처음부터 재시작
                break;

            case R.id.btnPause:
                videoView.pause();      // 동영상 일시정지 (Start 버튼 클릭하면 재개)
                break;

            case R.id.btnStop:
                finish();
                break;

            default:
                break;
        }
    }

}