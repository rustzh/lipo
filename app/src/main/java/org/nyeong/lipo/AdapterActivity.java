package org.nyeong.lipo;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
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
    ArrayList<String> arrayList;
    ArrayAdapter arrayAdapter;
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.adapterview);

        arrayList = new ArrayList<>();
        FirebaseStorage storage = FirebaseStorage.getInstance("gs://lipo-cf566.appspot.com");
        StorageReference storageRef = storage.getReference();

        System.out.println("adapter 시작 전");
        //Adapter 생성
//        arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, arrayList);

//        listView = (ListView) findViewById(R.id.list_view);
//        //listview 에 adapter 연결
//        listView.setAdapter(arrayAdapter);

        System.out.println("listall 시작 전");


        for(int i=0; i<10; i++) {
            arrayList.add("Item " +i);
        }

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,int position, long id) {
                Toast.makeText(AdapterActivity.this, position + "번째 영상", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getApplicationContext(), VideolistActivity.class);
                // putextra의 첫 값은 식별태그, 뒤에는 다음 화면으로 넘길 값
//                intent.putExtra("", Integer.toString(arrayList.get(position).getProfile()));
                startActivity(intent);

//        for(int i=0; i<10; i++) {
//            arrayList.add("Item " +i);
//        }

//        // 업로드 test
//        String fileName = "20220613-035009.mp4";
//        storageRef.child(fileName).getMetadata().addOnSuccessListener(new OnSuccessListener<StorageMetadata>() {
//            @Override
//            public void onSuccess(StorageMetadata storageMetadata) {
//                //String name = storageMetadata.getName(); // 이름 가져오기기
//               arrayList.add(fileName);
//            }
//        });
        storageRef.listAll().addOnSuccessListener(new OnSuccessListener<ListResult>() {
            @Override
            public void onSuccess(ListResult listResult) {
                System.out.println("onsuccess 시작");
//                for (StorageReference prefix : listResult.getPrefixes()){
//                    arrayList.add(prefix.getName());
//                }

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
