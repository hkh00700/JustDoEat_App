package com.example.empty_can;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private BottomNavigationView bottomNavigationView;
    private FloatingActionButton fab;
    int num = 0;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mainlayout);

        fab = findViewById(R.id.fab);


        // 로그인 되었을 때 정보창에
        // 홈 액티비티에서 무슨버튼이 눌렸는지 가져오기
        Intent intent = getIntent();
        if(intent != null){
            num = intent.getIntExtra("num", 0);
        }



        bottomNavigationView = findViewById(R.id.bottom_navigation);
        getSupportFragmentManager().beginTransaction().add(R.id.container, new Main()).commit();

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.tab1 :
                        getSupportFragmentManager().beginTransaction().replace(R.id.container,new Main()).commit();
                        break;
                    case R.id.tab2:
                        getSupportFragmentManager().beginTransaction().replace(R.id.container,new Game()).commit();
                        break;
                    case R.id.tab3:
                        getSupportFragmentManager().beginTransaction().replace(R.id.container,new Map()).commit();
                        break;
                    case R.id.tab4:
                        getSupportFragmentManager().beginTransaction().replace(R.id.container,new MyPage()).commit();
                        break;
                    case R.id.tab5:
                        getSupportFragmentManager().beginTransaction().replace(R.id.container,new List()).commit();
                        break;
                }
                return true;
            }
        });

        // 눌린 버튼에 대한 동적으로 이동
        if(num == 1){
            bottomNavigationView.setSelectedItemId(R.id.tab1);
        }else if(num == 2){
            bottomNavigationView.setSelectedItemId(R.id.tab4);
        }else if(num == 3){
            bottomNavigationView.setSelectedItemId(R.id.tab3);
        }else if(num == 4){
            bottomNavigationView.setSelectedItemId(R.id.tab2);
        }else if(num == 5){
            bottomNavigationView.setSelectedItemId(R.id.tab5);
        }

        // floatingActionButton 눌렸을 때
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getSupportFragmentManager().beginTransaction().replace(R.id.container,new Main()).commit();
                bottomNavigationView.setSelectedItemId(R.id.tab1);
            }
        });

    }
}
