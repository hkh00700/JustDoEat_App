package com.example.empty_can;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.empty_can.DTO.tDTO;

public class ladderActivity extends AppCompatActivity {

    tDTO dto;
    TextView tView1,tView2,tView3,tView4,tView5;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ladder);

        // 받기
        Intent intent = getIntent();
        int count = intent.getIntExtra("count", 0);
        String t1 = intent.getStringExtra("t1");
        String t2 = intent.getStringExtra("t2");
        String t3 = intent.getStringExtra("t3");
        String t4 = intent.getStringExtra("t4");
        String t5  = intent.getStringExtra("t5");

        dto = new tDTO(t1, t2, t3, t4, t5);
        this.dto = dto;

        tView1 = findViewById(R.id.tView1);
        tView2 = findViewById(R.id.tView2);
        tView3 = findViewById(R.id.tView3);
        tView4 = findViewById(R.id.tView4);
        tView5 = findViewById(R.id.tView5);


    }


}
