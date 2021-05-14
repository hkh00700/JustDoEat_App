package com.example.empty_can;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.MultiAutoCompleteTextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.empty_can.ATask.RandomFive;

import java.util.concurrent.ExecutionException;

public class GameSetActivity extends AppCompatActivity { 
    private static final String TAG = "main:GameSetActivity";

    Button btnRoulette,btnLadder,button3,button4;
    MultiAutoCompleteTextView mTextView1,mTextView2,mTextView3,mTextView4;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gameset);


        btnRoulette=findViewById(R.id.btnRoulette);
        btnLadder= findViewById(R.id.btnLadder);
        button3=findViewById(R.id.button3);
        button4=findViewById(R.id.button4);
        MultiAutoCompleteTextView mTextView1 = (MultiAutoCompleteTextView) findViewById(R.id.mTextView1);   // mTextView1 를 id 값으로 참조하여 객체를 생성한다
        MultiAutoCompleteTextView mTextView2 = (MultiAutoCompleteTextView) findViewById(R.id.mTextView2);
        MultiAutoCompleteTextView mTextView3 = (MultiAutoCompleteTextView) findViewById(R.id.mTextView3);
        MultiAutoCompleteTextView mTextView4 = (MultiAutoCompleteTextView) findViewById(R.id.mTextView4);
        MultiAutoCompleteTextView mTextView5 = (MultiAutoCompleteTextView) findViewById(R.id.mTextView5);


        String food5 = "";
        RandomFive randomFive = new RandomFive();
        try {
            food5 = randomFive.execute().get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }



        Log.d(TAG, "onCreate: " + food5);

        String[] foods = food5.split(",");
        Toast.makeText(GameSetActivity.this, "오늘 5가지 추천음식은 "
                + foods[0]+", " + foods[1] +", "+ foods[2] +", "+ foods[3]+", " + foods[4] +"입니다~!! " +
                "게임을 선택해보세요~^^",Toast.LENGTH_LONG).show();


        Log.d(TAG, "onCreate: " + foods[0]);
        Log.d(TAG, "onCreate: " + foods[1]);
        Log.d(TAG, "onCreate: " + foods[2]);
        Log.d(TAG, "onCreate: " + foods[3]);
        Log.d(TAG, "onCreate: " + foods[4]);

        mTextView1.setText(foods[0]);
        mTextView2.setText(foods[1]);
        mTextView3.setText(foods[2]);
        mTextView4.setText(foods[3]);
        mTextView5.setText(foods[4]);

        btnRoulette.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                String t1 = mTextView1.getText().toString();   // 참조하여 생성한 객체 mTextView1 으로 값을 읽어 들여와 String 형태의 변수 t1에 저장한다
                String t2 = mTextView2.getText().toString();
                String t3 = mTextView3.getText().toString();
                String t4 = mTextView4.getText().toString();
                String t5 = mTextView5.getText().toString();

                Toast.makeText(GameSetActivity.this
                        , t1 +", "+ t2+", " + t3+", " + t4+", " + t5 +
                                " 중에서 어떤 음식이 나올까요? 두구두구~" +
                                "start버튼을 눌러보세요!!", Toast.LENGTH_LONG).show();
                int count = 5;

                Intent intent = new Intent(getApplicationContext(), RouletteActivity.class);
                intent.putExtra("count", count);
                intent.putExtra("t1", t1);
                intent.putExtra("t2", t2);
                intent.putExtra("t3", t3);
                intent.putExtra("t4", t4);
                intent.putExtra("t5", t5);
                startActivityForResult(intent, 1000);

            }
        });

        btnLadder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(GameSetActivity.this,

                    "ⓘ 아직 만들지 않는 서비스입니다.(추후 업데이트 예정) ", Toast.LENGTH_SHORT).show();

            }
        });

        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(GameSetActivity.this,

                        "ⓘ 아직 만들지 않는 서비스입니다.(추후 업데이트 예정) ", Toast.LENGTH_SHORT).show();
            }
        });

        button4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                        Intent intent = new Intent(getApplicationContext(), WorldcupActivity.class);
                        intent.putExtra("foods", foods);
                startActivity(intent);

            }
        });



    }
}
