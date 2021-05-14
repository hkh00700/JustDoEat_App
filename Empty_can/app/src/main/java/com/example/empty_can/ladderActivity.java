package com.example.empty_can;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.empty_can.DTO.tDTO;

import java.util.Random;

public class ladderActivity extends AppCompatActivity {
    private static final String TAG = "main:ladderActivity";
    Random rnd;
    tDTO dto;
    TextView tView1,tView2,tView3,tView4,tView5;
    Button button2;
    ImageView imageView2;
    ImageButton imgButton1,imgButton2,imgButton3,imgButton4,imgButton5;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ladder);

        TextView tView1 = findViewById(R.id.tView1);
        TextView tView2 = findViewById(R.id.tView2);
        TextView tView3 = findViewById(R.id.tView3);
        TextView tView4 = findViewById(R.id.tView4);
        TextView tView5 = findViewById(R.id.tView5);

      /*  String food5 = "";
        RandomFive randomFive = new RandomFive();
        try {
            food5 = randomFive.execute().get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        String[] foods = food5.split(",");
        tView1.setText(foods[0]);
        tView2.setText(foods[1]);
        tView3.setText(foods[2]);
        tView4.setText(foods[3]);
        tView5.setText(foods[4]);*/

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

        tView1.setText(t1);
        tView2.setText(t2);
        tView3.setText(t3);
        tView4.setText(t4);
        tView5.setText(t5);
        //버튼을 누르면 사다리를 따라 해당되는 음식에 도착
        imgButton1 = findViewById(R.id.imgButton1);
        imageView2 = findViewById(R.id.imageView2);
        button2 = findViewById(R.id.button2);

        button2.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                //Toast.makeText(getApplicationContext(), dto.getT1()+" 당첨~!!*^^*", Toast.LENGTH_LONG).show();
                
                Random rnd = new Random();

                int value = rnd.nextInt(5);
                Log.d("random", "onClick: random" + value);

                    if(value == 0){
                        Toast.makeText(getApplicationContext(), dto.getT1()+" 당첨~!!*^^*", Toast.LENGTH_LONG).show();
                    }else if(value == 1){
                        Toast.makeText(getApplicationContext(), dto.getT2()+" 당첨~!!*^^*", Toast.LENGTH_LONG).show();
                    }else if(value == 2){
                        Toast.makeText(getApplicationContext(), dto.getT3()+" 당첨~!!*^^*", Toast.LENGTH_LONG).show();
                    }else if(value == 3){
                        Toast.makeText(getApplicationContext(), dto.getT4()+" 당첨~!!*^^*", Toast.LENGTH_LONG).show();
                    }else if( value == 4){
                        Toast.makeText(getApplicationContext(), dto.getT5()+" 당첨~!!*^^*", Toast.LENGTH_LONG).show();
                    }
                }
        });
    }
}



