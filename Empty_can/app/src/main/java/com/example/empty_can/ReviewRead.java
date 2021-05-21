package com.example.empty_can;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.empty_can.Common.CommonMethod;
import com.example.empty_can.DTO.MemberReviewDTO;

import java.io.File;

public class ReviewRead extends AppCompatActivity {

    TextView utitle, ucontent, m_id;
    ImageView imageView2;
    String s_title, s_content, s_id;
    Button btnback;

    CommonMethod commonMethod = new CommonMethod();
    MemberReviewDTO selItem;

    String imagePath = null;
    public String pImgDbPathU;
    public String imageRealPathU = "", imageDbPathU = "";

    File file = null;
    long fileSize = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review_read);

        utitle = findViewById(R.id.utitle);
        ucontent = findViewById(R.id.ucontent);
        imageView2 = findViewById(R.id.imageView2);
        m_id = findViewById(R.id.m_id);

        Intent intent = getIntent();
        selItem = (MemberReviewDTO) intent.getSerializableExtra("selItem");

        s_id = selItem.getId();
        s_title = selItem.getTitle();
        s_content = selItem.getContent();


        utitle.setText(s_title);
        ucontent.setText(s_content);
        m_id.setText(s_id + "님의 글");


        imagePath = selItem.getPhoto_path();
        pImgDbPathU = imagePath;
        imageDbPathU = imagePath;

        imageView2.setVisibility(View.VISIBLE);
        if (selItem.getPhoto_path().equals("null") || selItem.getPhoto_path() == null){
            Glide.with(this).load("http://192.168.0.74:9191/justdo_eat/resources/blank.jpg").into(imageView2);
        }else{
            Glide.with(this).load(selItem.getPhoto_path()).into(imageView2);
        }


        findViewById(R.id.btnback).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


    }

}