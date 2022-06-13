package org.nyeong.lipo;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import android.widget.VideoView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.io.IOException;

public class VideolistActivity extends AppCompatActivity implements View.OnClickListener {

//    public static final String VIDEO_URL = "http://commondatastorage.googleapis.com/gtv-videos-bucket/sample/BigBuckBunny.mp4";

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


        try
        {
            File path = new File("C:\\Users\\user\\StudioProjects\\m_project\\last_lipo\\app\\src\\main\\res");
            final File file = new File(path, filename);
            try {
                if (!path.exists()) {
                    //저장할 폴더가 없으면 생성
                    path.mkdirs();
                }
                file.createNewFile();

                //파일을 다운로드하는 Task 생성, 비동기식으로 진행
                final FileDownloadTask fileDownloadTask = storageRef.getFile(file);
                fileDownloadTask.addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
//                        mImageView.setImageURI(Uri.fromFile(file));
                        System.out.println("완료");
//                        videoView = findViewById(R.id.videoView);
////                        Uri videoUri = Uri.parse("android.resource://"+getPackageName()+ "/"+ )
//                        Uri videoUri = file.getPath();


                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception exception) {
                    }
                }).addOnProgressListener(new OnProgressListener<FileDownloadTask.TaskSnapshot>() {
                    @Override
                    //진행상태 표시
                    public void onProgress(FileDownloadTask.TaskSnapshot taskSnapshot) {
                    }
                });
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch(Exception e){
            e.printStackTrace();
        }

        System.out.println("전달된 filename = " + filename);


        // 에뮬레이터로 확인하려면 내 프로젝트에 동영상 파일이 있어야 됨
//        String VIDEO_PATH = "android.resource://" + getPackageName() + "/" + R.raw.night;

//        getStringExtra 로 받아옴!!!

//        Uri uri = Uri.parse(VIDEO_PATH);
//        videoView.setVideoURI(uri);
//        videoView.setMediaController(new MediaController(this));
//        videoView.requestFocus();    // 준비하는 과정을 미리함

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
                videoView.pause();
                videoView.stopPlayback();   // 동영상 정지 (Resume 버튼 클릭하면 새로 실행)
                break;

            default:
                break;
        }
    }

}


//import android.content.Intent;
//import android.net.Uri;
//import android.net.UrlQuerySanitizer;
//import android.os.Bundle;
//import android.widget.ImageView;
//import android.widget.Toast;
//import android.widget.VideoView;
//
//import androidx.annotation.NonNull;
//import androidx.appcompat.app.AppCompatActivity;
//
//import com.bumptech.glide.Glide;
//import com.google.android.gms.tasks.Continuation;
//import com.google.android.gms.tasks.OnCompleteListener;
//import com.google.android.gms.tasks.OnFailureListener;
//import com.google.android.gms.tasks.OnSuccessListener;
//import com.google.android.gms.tasks.Task;
//import com.google.firebase.storage.FirebaseStorage;
//import com.google.firebase.storage.StorageReference;
//import com.google.firebase.storage.UploadTask;
//
//import java.security.KeyStore;
//
//public class VideolistActivity extends AppCompatActivity {
//    VideoView load;
//    //    ImageView load;
//    Intent videoIntent = getIntent();
////    videoIntent.getIntExtra("key", value);
//
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_videolist);
//        FirebaseStorage storage = FirebaseStorage.getInstance();  // 스토리지 인스턴스를 만들고, 다운로드는 주소를 넣는다.
//        StorageReference storageReference = storage.getReference(); // 스토리지 참조
////        StorageReference pathReference = storageReference.child("video");
////        load = findViewById(R.id.)
////        if (pathReference == null) {
////            Toast.makeText(this, "저장소에 영상이 없습니다.", Toast.LENGTH_SHORT).show();
////        }
////        else {
//        storageReference.child("여기 우리 영상 제목").getDownloadUrl().addOnFailureListener(new OnSuccessListener<Uri>() {
//            //            StorageReference submitProfile = storageReference.child("여기 우리 영상 변수이름");
////            submitProfile.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
//            @Override
//            public void onSuccess(Uri uri) {
//                Glide.with(VideolistActivity.this).load(uri).into(load);
//            }
//        }).addOnFailureListener(new OnFailureListener() {
//            @Override
//            public void onFailure(@NonNull Exception e) {
//            }
//        });
//    }
//}
//        else{
//            uploadTask = pathReference.putFile(file);
//
//            Task<Uri> urlTask = uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
//                @Override
//                public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
//                    if (!task.isSuccessful()) {
//                        throw task.getException();
//                    }
//
//                    // Continue with the task to get the download URL
//                    return pathReference.getDownloadUrl();
//                }
//            }).addOnCompleteListener(new OnCompleteListener<Uri>() {
//                @Override
//                public void onComplete(@NonNull Task<Uri> task) {
//                    if (task.isSuccessful()) {
//                        Uri downloadUri = task.getResult();
//                    } else {
//                        // Handle failures
//                        // ...
//                    }
//                }
//            });
//        }

