package com.example.empty_can;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.empty_can.DTO.tDTO;

import java.util.Random;

public class drawinglotsActivity extends AppCompatActivity {

    tDTO dto;
    Button btnAll,btnOpen;
    ImageButton imageButton1,imageButton2,imageButton3,imageButton4,imageButton5;
    ImageView openlot,closelot;
    TextView lotResult;
    int value;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.drawinglots);



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

        btnAll = findViewById(R.id.btnOpen);
        imageButton1=findViewById(R.id.imageButton1);
        imageButton2=findViewById(R.id.imageButton2);
        imageButton3=findViewById(R.id.imageButton3);
        imageButton4=findViewById(R.id.imageButton4);
        imageButton5=findViewById(R.id.imageButton5);
        openlot= findViewById(R.id.openlot);
        closelot= findViewById(R.id.closelot);
        btnOpen= findViewById(R.id.btnOpen);
        lotResult = findViewById(R.id.lotResult);
        closelot.setVisibility(View.GONE);
        openlot.setVisibility(View.GONE);
        lotResult.setVisibility(View.GONE);


        imageButton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imageButton1.setVisibility(View.GONE);
                closelot.setVisibility(View.VISIBLE);
                //Toast.makeText(getApplicationContext(), dto.getT1()+" 당첨~!!*^^*", Toast.LENGTH_LONG).show();
            }
        });
        imageButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imageButton2.setVisibility(View.GONE);
                closelot.setVisibility(View.VISIBLE);
                //Toast.makeText(getApplicationContext(), dto.getT1()+" 당첨~!!*^^*", Toast.LENGTH_LONG).show();
            }
        });
        imageButton3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imageButton3.setVisibility(View.GONE);
                closelot.setVisibility(View.VISIBLE);
                //Toast.makeText(getApplicationContext(), dto.getT1()+" 당첨~!!*^^*", Toast.LENGTH_LONG).show();
            }
        });
        imageButton4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imageButton4.setVisibility(View.GONE);
                closelot.setVisibility(View.VISIBLE);
                //Toast.makeText(getApplicationContext(), dto.getT1()+" 당첨~!!*^^*", Toast.LENGTH_LONG).show();
            }
        });
        imageButton5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imageButton5.setVisibility(View.GONE);
                closelot.setVisibility(View.VISIBLE);
                //Toast.makeText(getApplicationContext(), dto.getT1()+" 당첨~!!*^^*", Toast.LENGTH_LONG).show();
            }
        });

        btnOpen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                closelot.setVisibility(View.GONE);
                openlot.setVisibility(View.VISIBLE);

                Random rnd = new Random();

                value = rnd.nextInt(5);

                if(value == 0){
                    //Toast.makeText(getApplicationContext(), dto.getT1(), Toast.LENGTH_LONG).show();
                    lotResult.setText(t1);
                    lotResult.setVisibility(View.VISIBLE);

                }else if(value == 1){
                   // Toast.makeText(getApplicationContext(), dto.getT2()+" 당첨~!!*^^*", Toast.LENGTH_LONG).show();
                    lotResult.setText(t2);
                    lotResult.setVisibility(View.VISIBLE);
                }else if(value == 2){
                    //Toast.makeText(getApplicationContext(), dto.getT3()+" 당첨~!!*^^*", Toast.LENGTH_LONG).show();
                    lotResult.setText(t3);
                    lotResult.setVisibility(View.VISIBLE);
                }else if(value== 3){
                    //Toast.makeText(getApplicationContext(), dto.getT4()+" 당첨~!!*^^*", Toast.LENGTH_LONG).show();
                    lotResult.setText(t4);
                    lotResult.setVisibility(View.VISIBLE);
                }else if( value == 4){
                    //Toast.makeText(getApplicationContext(), dto.getT5()+" 당첨~!!*^^*", Toast.LENGTH_LONG).show();
                    lotResult.setText(t5);
                    lotResult.setVisibility(View.VISIBLE);
                }

            }
        });



    }
}