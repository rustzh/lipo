package org.nyeong.lipo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

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

        //Adapter 생성
        arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, arrayList);

        listView = (ListView) findViewById(R.id.list_view);
        //listview 에 adapter 연결
        listView.setAdapter(arrayAdapter);

//        for(int i=0; i<10; i++) {
//            arrayList.add("Item " +i);
//        }



        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,int position, long id) {
                Toast.makeText(AdapterActivity.this, position + "번째 영상", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getApplicationContext(), VideolistActivity.class);
                startActivity(intent);
            }
        });
    }
}
