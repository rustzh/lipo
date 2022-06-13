package org.nyeong.lipo;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Date;

public class cctvActivity extends AppCompatActivity {

    FirebaseDatabase database;
    DatabaseReference stateRef;

    final String TAG = "TAG+CCTVFragment";
    ImageButton callpolice, recording, warning;
    ToggleButton toggleButton;

    WebView cctvWebView;
    WebSettings cctvWebSettings;
    int IsOutHome = 0;
    boolean IsWarning = false;
    int IsRecording = 0;

    @SuppressLint({"ClickableViewAccessibility", "SetJavaScriptEnabled"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cctv);

        database = FirebaseDatabase.getInstance("https://lipo-cf566-default-rtdb.firebaseio.com/");
        stateRef = database.getReference("state");

        Log.d(TAG, "Create CCTV Fragment");

        cctvWebView = (WebView) findViewById(R.id.cctvWeb);
        callpolice = (ImageButton) findViewById(R.id.callpolice);
        recording = (ImageButton) findViewById(R.id.recoding);
        warning = (ImageButton) findViewById(R.id.warning);
        toggleButton = (ToggleButton) findViewById(R.id.out_home_home);

        stateRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                IsOutHome = snapshot.child("outhome").getValue(Integer.class);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        if (IsOutHome==0) {
            toggleButton.setBackgroundDrawable(getResources().getDrawable(R.drawable.home));
        } else {
            toggleButton.setBackgroundDrawable(getResources().getDrawable(R.drawable.out_home));
        }

//        cctvWebSettings = cctvWebView.getSettings();
//        cctvWebSettings.setUseWideViewPort(true);
//        cctvWebSettings.setJavaScriptEnabled(true);
//        cctvWebSettings.setLoadWithOverviewMode(true);
//        cctvWebView.loadUrl("http://192.168.0.48:8080/stream");
//        // 터치 시 새로고침
//        cctvWebView.setOnTouchListener(new View.OnTouchListener() {
//            @Override
//            public boolean onTouch(View v, MotionEvent event) {
//                if (event.getAction() == MotionEvent.ACTION_DOWN) {
//                    cctvWebView.reload();
//                }
//                return true;
//            }
//        });

        final TextView clock = (TextView) findViewById(R.id.clock);

        (new Thread(new Runnable()
        {
            @Override
            public void run()
            {
                while (!Thread.interrupted())
                    try{
                        Thread.sleep(1000);
                        runOnUiThread(new Runnable(){
                            @Override
                            public void run()
                            {
                                clock.setText(getCurrentTime());
                            }
                        });
                    }
                    catch (InterruptedException e)
                    {

                    }
            }
        })).start();

        toggleButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (toggleButton.isChecked()&&IsOutHome==0) {
                    toggleButton.setBackgroundDrawable(getResources().getDrawable(R.drawable.out_home));
                    IsOutHome = 1;
                    stateRef.child("outhome").setValue(1);
                    Toast.makeText(getApplicationContext(), "외출 중", Toast.LENGTH_SHORT).show();
                } else {
                    toggleButton.setBackgroundDrawable(getResources().getDrawable(R.drawable.home));
                    IsOutHome = 0;
                    stateRef.child("outhome").setValue(0);
                    Toast.makeText(getApplicationContext(), "귀가", Toast.LENGTH_SHORT).show();
                }
            }
        }); // 외출모드

        recording.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stateRef.child("recording").setValue(1);
                IsRecording = 1;
                Toast.makeText(getApplicationContext(), "녹화 중", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getApplicationContext(), recordingActivity.class);
                startActivity(intent);
            }
        }); // 녹화

        callpolice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(cctvActivity.this);
                builder.setTitle("신고");
                builder.setMessage("신고하시겠습니까?");
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:112"));
                        startActivity(intent);
                    }
                });
                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                AlertDialog alertDialog = builder.create();
                alertDialog.show();
            }
        }); // 신고하기 버튼 클릭 시 112 전화걸기로 이동

        warning.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(IsWarning==false) {
                    stateRef.child("warning").setValue(1);
                    IsWarning = true;
                } else {
                    stateRef.child("warning").setValue(0);
                    IsWarning = false;
                }
            }
        }); // 경고음 출력

    }

    public String getCurrentTime(){
        long time = System.currentTimeMillis();
        SimpleDateFormat dayTime = new SimpleDateFormat("MM-dd hh:mm");
        String str = dayTime.format(new Date(time));
        return str;
    }
}

