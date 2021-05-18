package com.example.empty_can;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    public BottomNavigationView bottomNavigationView;
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

        Log.d(TAG, "onCreate: 2번째 a값 : " + num);
        bottomNavigationView = findViewById(R.id.bottom_navigation);

        getSupportFragmentManager().beginTransaction().add(R.id.container, new Main()).commit();

        /*if( num > 0){
            getSupportFragmentManager().beginTransaction().add(R.id.container, new List()).commit();
        } else {
            getSupportFragmentManager().beginTransaction().add(R.id.container, new Main()).commit();

        }*/
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
        setNaviPosition(num);

        // floatingActionButton 눌렸을 때
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getSupportFragmentManager().beginTransaction().replace(R.id.container,new Main()).commit();
                bottomNavigationView.setSelectedItemId(R.id.tab1);
            }
        });

    }

    public void setNaviPosition(int number){
        if(number == 1){
            bottomNavigationView.setSelectedItemId(R.id.tab1);
        }else if(number == 2){
            bottomNavigationView.setSelectedItemId(R.id.tab4);
        }else if(number == 3){
            bottomNavigationView.setSelectedItemId(R.id.tab3);
        }else if(number == 4){
            bottomNavigationView.setSelectedItemId(R.id.tab2);
        }else if(number == 5){
            bottomNavigationView.setSelectedItemId(R.id.tab5);
        }
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        int number = intent.getIntExtra("num", 1);

        setNaviPosition(number);
    }
}
