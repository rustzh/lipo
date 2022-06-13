package org.nyeong.lipo;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.ListResult;
import com.google.firebase.storage.StorageReference;

public class AdapterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.adapterview);

        FirebaseStorage storage = FirebaseStorage.getInstance("gs://lipo-cf566.appspot.com");
        StorageReference storageRef = storage.getReference();

        storageRef.listAll().addOnSuccessListener(new OnSuccessListener<ListResult>() {
            @Override
            public void onSuccess(ListResult listResult) {
                for (StorageReference item : listResult.getItems()){
//                    String filename = item.getName();
//                    arrayList.add(filename);
//                    System.out.println(filename);
                    String filename = item.getName();
                    LinearLayout layout = (LinearLayout) findViewById(R.id.layout);
                    Button btn = new Button(AdapterActivity.this);
//                    btn.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
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

//        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view,int position, long id) {
//                Toast.makeText(AdapterActivity.this, position + "번째 영상", Toast.LENGTH_SHORT).show();
//                Intent intent = new Intent(getApplicationContext(), VideolistActivity.class);
//                startActivity(intent);
//            }
//        });


    }
}
