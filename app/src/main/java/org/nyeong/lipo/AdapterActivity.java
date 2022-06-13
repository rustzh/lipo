package org.nyeong.lipo;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.ListResult;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;

public class AdapterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.adapterview);

        FirebaseStorage storage = FirebaseStorage.getInstance("gs://lipo-cf566.appspot.com");
        StorageReference storageRef = storage.getReference();

        System.out.println("adapter 시작 전");


        System.out.println("listall 시작 전");


        storageRef.listAll().addOnSuccessListener(new OnSuccessListener<ListResult>() {
            @Override
            public void onSuccess(ListResult listResult) {
                System.out.println("onsuccess 시작");

                for (StorageReference item : listResult.getItems()){

                    String filename = item.getName();
                    LinearLayout layout = (LinearLayout) findViewById(R.id.layout);
                    Button btn = new Button(AdapterActivity.this);
                    btn.setText(filename);
                    btn.setTextSize(30);
                    btn.setTextColor(Color.BLACK);
                    layout.addView(btn);
                    System.out.println(filename);
                    btn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Toast.makeText(getApplicationContext(), "영상 선택", Toast.LENGTH_SHORT).show();
                            System.out.println(filename);
                            Intent intent = new Intent(getApplicationContext(), VideolistActivity.class);
                            intent.putExtra("filename", filename);
                            startActivity(intent);
                        }
                    });
                }

            }
        });



    }
}
