package org.nyeong.lipo;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    FirebaseDatabase database;
    DatabaseReference stateRef;
    int IsRecording;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        database = FirebaseDatabase.getInstance("https://lipo-cf566-default-rtdb.firebaseio.com/");
        stateRef = database.getReference("state");

        stateRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                IsRecording = snapshot.child("recording").getValue(Integer.class);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        // 버튼 받아오기
        ImageButton cctvOnButton = (ImageButton) findViewById(R.id.cctvOnButton);
        Button videoList = (Button) findViewById(R.id.videoList);

        // cctv 화면으로 넘어가는 기능
        cctvOnButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                stateRef.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        IsRecording = snapshot.child("recording").getValue(Integer.class);
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

                if (IsRecording == 0) {
                    Intent intent = new Intent(getApplicationContext(), cctvActivity.class);
                    startActivity(intent);
                }
                else{
                    Intent intent = new Intent(getApplicationContext(), recordingActivity.class);
                    startActivity(intent);
                }

            }
        });

        // 녹화 목록 화면으로 넘어가는 기능
        videoList.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent intent = new Intent(getApplicationContext(), AdapterActivity.class);
                startActivity(intent);
            }
        });

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
    }

    public String getCurrentTime(){
        long time = System.currentTimeMillis();
        SimpleDateFormat dayTime = new SimpleDateFormat("MM-dd hh:mm");
        String str = dayTime.format(new Date(time));
        return str;
    }

}