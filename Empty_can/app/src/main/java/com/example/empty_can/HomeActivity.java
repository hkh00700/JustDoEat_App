package com.example.empty_can;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class HomeActivity extends AppCompatActivity {


    Button btnMain, btnMyPage, btnMap, btnGame, btnList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home);

        btnMain = findViewById(R.id.btnMain);
        btnMyPage = findViewById(R.id.btnMyPage);
        btnMap = findViewById(R.id.btnMap);
        btnGame = findViewById(R.id.btnGame);
        btnList = findViewById(R.id.btnList);

        btnMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }
        });

    }
}