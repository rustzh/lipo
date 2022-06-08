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
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import androidx.appcompat.app.AppCompatActivity;

import java.text.SimpleDateFormat;
import java.util.Date;

public class cctvActivity extends AppCompatActivity {
    final String TAG = "TAG+CCTVFragment";
    ImageButton callpolice, warning;
//    WebView webView;
    WebSettings webSettings;
    boolean i = true;

    @SuppressLint({"ClickableViewAccessibility", "SetJavaScriptEnabled"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cctv);

        Log.d(TAG, "Create CCTV Fragment");

//        webView = (WebView) findViewById(R.id.cctvWeb);
        callpolice = (ImageButton) findViewById(R.id.callpolice);
        warning = (ImageButton) findViewById(R.id.warning);

//        webSettings = webView.getSettings();
//        webSettings.setJavaScriptEnabled(true);

//        webView.loadData("<html><head><style type='text/css'>body{margin:auto auto;text-align:center;} " +
//                        "img{width:100%25;} div{overflow: hidden;} </style></head>" +
//                        "<body><div><img src='http://" + ((MainActivity)MainActivity.context).tcpThread.ip + ":8082/'/></div></body></html>",
//                "text/html", "UTF-8");
        // WebView 에 CCTV 화면 띄움
//        webView.setOnTouchListener(new View.OnTouchListener() {
//            @Override
//            public boolean onTouch(View v, MotionEvent event) {
//                if (event.getAction() == MotionEvent.ACTION_DOWN) {
//                    webView.reload();
//                }
//                return true;
//            }
//        }); // WebView 터치 시 새로고침

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

        ToggleButton toggleButton = (ToggleButton) findViewById(R.id.out_home_home);
        toggleButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (toggleButton.isChecked() && i==true) {
                    toggleButton.setBackgroundDrawable(getResources().getDrawable(R.drawable.home));
                    i = false;
                } else {
                    toggleButton.setBackgroundDrawable(getResources().getDrawable(R.drawable.out_home));
                    i = true;
                }
            }
        }); // 외출모드

//        out_home_home.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                new Thread(new Runnable() {
//                    @Override
//                    public void run() {
////                        try {
////                            ((MainActivity)MainActivity.context).tcpThread.cctvOff();
////                        } catch (IOException e) {
////                            e.printStackTrace();
////                        }
//                    }
//                }).start();
//            }
//        }); // 경고음 출력

        ToggleButton toggleButton2 = (ToggleButton) findViewById(R.id.recode);
        toggleButton2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    Toast.makeText(getApplicationContext(), "녹화 중", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getApplicationContext(), "정지", Toast.LENGTH_SHORT).show();
                }
            }
        });  // 녹화

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
                new Thread(new Runnable() {
                    @Override
                    public void run() {
//                        try {
//                            ((MainActivity)MainActivity.context).tcpThread.cctvOff();
//                        } catch (IOException e) {
//                            e.printStackTrace();
//                        }
                    }
                }).start();
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

