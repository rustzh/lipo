package org.nyeong.lipo;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Date;

public class recordingActivity extends AppCompatActivity {

    FirebaseDatabase database;
    DatabaseReference stateRef;

    ImageButton recordingOffButton;

    int IsRecording = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recording);

        database = FirebaseDatabase.getInstance("https://lipo-cf566-default-rtdb.firebaseio.com/");
        stateRef = database.getReference("state");

        recordingOffButton = (ImageButton) findViewById(R.id.recordingOffButton);

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

        recordingOffButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stateRef.child("recording").setValue(0);
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }
        });

    }

    public String getCurrentTime(){
        long time = System.currentTimeMillis();
        SimpleDateFormat dayTime = new SimpleDateFormat("MM-dd hh:mm");
        String str = dayTime.format(new Date(time));
        return str;
    }
}
