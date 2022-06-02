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
import android.widget.ImageButton;
<<<<<<< HEAD
=======
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;
>>>>>>> 37b900c18be6262f4cc7e19bc19d6c62961d8018

import androidx.appcompat.app.AppCompatActivity;

public class cctvActivity extends AppCompatActivity {
    final String TAG = "TAG+CCTVFragment";
    ImageButton callpolice, recode, warning;
    //    ToggleButton outhome;
//    WebView webView;
    WebSettings webSettings;
//    TextView callText;

    @SuppressLint({"ClickableViewAccessibility", "SetJavaScriptEnabled"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cctv);

        Log.d(TAG, "Create CCTV Fragment");

//        webView = (WebView) findViewById(R.id.cctvWeb);
//        callText = (TextView) findViewById(R.id.callText);
//        outhome = (ToggleButton) findViewById(R.id.outhome);
        callpolice = (ImageButton) findViewById(R.id.callpolice);
        recode = (ImageButton) findViewById(R.id.recode);
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

//        @Override
//        outhome.setOnClickListener(new View.setOnClickListener() {
////            @Override
//            public void onCheckedChanged(View buttonView, boolean isChecked) {
//                if(isChecked) {
//                    Toast.makeText(getApplicationContext(), "checked", Toast.LENGTH_LONG).show();
//                }
//                else {
//                    Toast.makeText(getApplicationContext(), "unchecked", Toast.LENGTH_LONG).show();
//                }
//            }
//        }); // 외출모드

        ToggleButton toggleButton = (ToggleButton) findViewById(R.id.outhome);
        toggleButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    Toast.makeText(getApplicationContext(), "외출 중", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getApplicationContext(), "집!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        callpolice.setOnClickListener(new View.OnClickListener() {
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
        }); // 경찰 부르기

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

        recode.setOnClickListener(new View.OnClickListener() {
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
    }
}

