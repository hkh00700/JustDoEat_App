package com.example.empty_can;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.empty_can.DTO.tDTO;

public class RouletteActivity extends AppCompatActivity {

        private ImageView mImageView;
        private Button rotate;
        private Bitmap mBitmap;
        private float angle = 0.0f; // 초기 각도
        private final int IMG_DP = 380; // 이미지 DP

        tDTO dto;
        TextView result;
        Button btnSwitch4;

        @Override
        protected void onCreate(@Nullable Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_roulette);
            result=findViewById(R.id.result);
            result.setVisibility(View.GONE);
            btnSwitch4 = findViewById(R.id.btnSwitch4);
            btnSwitch4.setVisibility(View.GONE);


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
            mImageView = findViewById(R.id.wheel);
            mBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.roulette);
            rotate=findViewById(R.id.rotate);
            rotate.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {

                    Runnable r = new onWheelImage();
                    Thread thread = new Thread(r);
                    thread.start();
                    try {
                        thread.join();
                    } catch (Exception e) {
                        // TODO: handle exception
                    }
                }
            });
        }
        private int getRandom(int max){
            return (int) (Math.random()*max);
        }
        private void getTargetEvent(float value){

            if((value>=1046 && value<=1080) || (value>=720 && value<=756)){
                Toast.makeText(getApplicationContext(), dto.getT1()+" 당첨~!!*^^*", Toast.LENGTH_LONG).show();
                result.setText(dto.getT1());
                result.setVisibility(View.VISIBLE);
                rotate.setVisibility(View.GONE);
                btnSwitch4.setText("내주변 " + result.getText() +" 잘하는 집 검색하기");
                btnSwitch4.setVisibility(View.VISIBLE);
                btnSwitch4.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://map.naver.com/v5/search/"+ result.getText()));
                        startActivity(intent);
                    }
                });
            }else if(value>=974 && value<=1045){
                Toast.makeText(getApplicationContext(), dto.getT2()+" 당첨~!!*^^*", Toast.LENGTH_LONG).show();
                result.setText(dto.getT2());
                result.setVisibility(View.VISIBLE);
                rotate.setVisibility(View.GONE);
                btnSwitch4.setVisibility(View.VISIBLE);
                btnSwitch4.setText("내주변 " + result.getText() +" 잘하는 집 검색하기");
                btnSwitch4.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://map.naver.com/v5/search/"+ result.getText()));
                        startActivity(intent);
                    }
                });
            }else if(value>=902 && value<=973){
                Toast.makeText(getApplicationContext(), dto.getT3()+" 당첨~!!*^^*", Toast.LENGTH_LONG).show();
                result.setText(dto.getT3());
                result.setVisibility(View.VISIBLE);
                rotate.setVisibility(View.GONE);
                btnSwitch4.setVisibility(View.VISIBLE);
                btnSwitch4.setText("내주변 " +  result.getText() +" 잘하는 집 검색하기");
                btnSwitch4.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://map.naver.com/v5/search/"+result.getText()));
                        startActivity(intent);
                    }
                });
            }else if(value>=830 && value<=901){
                Toast.makeText(getApplicationContext(), dto.getT4()+" 당첨~!!*^^*", Toast.LENGTH_LONG).show();
                result.setText(dto.getT4());
                result.setVisibility(View.VISIBLE);
                rotate.setVisibility(View.GONE);
                btnSwitch4.setVisibility(View.VISIBLE);
                btnSwitch4.setText("내주변 " + result.getText() +" 잘하는 집 검색하기");
                btnSwitch4.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://map.naver.com/v5/search/"+ result.getText()));
                        startActivity(intent);
                    }
                });
            }else if(value>=757 && value<=829){
                Toast.makeText(getApplicationContext(), dto.getT5()+" 당첨~!!*^^*", Toast.LENGTH_LONG).show();
                result.setText(dto.getT5());
                result.setVisibility(View.VISIBLE);
                rotate.setVisibility(View.GONE);
                btnSwitch4.setVisibility(View.VISIBLE);
                btnSwitch4.setText("내주변 " + result.getText() +" 잘하는 집 검색하기");
                btnSwitch4.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://map.naver.com/v5/search/"+ result.getText()));
                        startActivity(intent);
                    }
                });
            }
        }

    class onWheelImage implements Runnable{

            @Override
            public void run() {
                int random = getRandom(360);
                float fromAngle = random+720+angle; // 회전수 제어(랜덤(0~360)+720도+회전각)
                // 로테이션 애니메이션 초기화
                // 시작각, 종료각, 자기 원을 그리며 회전
                RotateAnimation animation = new RotateAnimation(angle, fromAngle
                        , Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);

                // 초기 시작각도 업데이트
                angle = fromAngle;
                animation.setDuration(2000); // 지속시간이 길수록 느려짐
                animation.setFillEnabled(true); // 애니메이션 종료된 후 상태 고정 옵션
                animation.setFillAfter(true);
                mImageView.startAnimation(animation);

                animation.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) { }
                    @Override
                    public void onAnimationEnd(Animation animation) {
                        getTargetEvent(angle);
                    }
                    @Override
                    public void onAnimationRepeat(Animation animation) { }
                });
            }
        }
}