package com.example.empty_can;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.empty_can.ATask.UserReviewSelect;
import com.example.empty_can.Adapter.ReviewAdapter;
import com.example.empty_can.DTO.MemberReviewDTO;

import java.util.ArrayList;

import static com.example.empty_can.Common.CommonMethod.isNetworkConnected;

public class ShowReviewActivity extends AppCompatActivity {
    public static MemberReviewDTO selItem = null;

    UserReviewSelect userReviewSelect;
    ReviewAdapter adapter;
    RecyclerView recyclerView;
    ArrayList<MemberReviewDTO> reviewList;
    ProgressDialog progressDialog;




    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_showreview);


        //리사이클뷰 연결
        reviewList = new ArrayList();
        adapter = new ReviewAdapter(reviewList, this);
        recyclerView = findViewById(R.id.showreview);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this,
                RecyclerView.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);

        recyclerView.setAdapter(adapter);

        if(isNetworkConnected(this) == true){
            userReviewSelect = new UserReviewSelect(reviewList, adapter, progressDialog);
            userReviewSelect.execute();
        }else {
            Toast.makeText(this, "인터넷이 연결되어 있지 않습니다.",
                    Toast.LENGTH_SHORT).show();
        }

    }

    //추가
    public void btn1Clicked(View v){
        if(isNetworkConnected(this) == true){
            Intent intent = new Intent(getApplicationContext(), WritingActivity.class);
            startActivity(intent);
        }else {
            Toast.makeText(this, "인터넷이 연결되어 있지 않습니다.",
                    Toast.LENGTH_SHORT).show();
        }


    }

    // 수정
    public void btn2Clicked(View v){
        if(isNetworkConnected(this) == true){

            if(selItem != null){
                Log.d("writing:update1", selItem.getTitle());

                /*Intent intent = new Intent(getApplicationContext(), Sub1Update.class);
                intent.putExtra("selItem", selItem);
                startActivity(intent);*/

            }else {
                Toast.makeText(getApplicationContext(), "항목 선택을 해 주세요",
                        Toast.LENGTH_SHORT).show();
            }

        }else {
            Toast.makeText(this, "인터넷이 연결되어 있지 않습니다.",
                    Toast.LENGTH_SHORT).show();
        }

    }

    // 삭제
    public void btn3Clicked(View v){
        if(isNetworkConnected(this) == true){
            if(selItem != null){
                Log.d("Writing : selImg => ", selItem.getPhoto_path());

               /* ListDelete listDelete = new ListDelete(selItem.getId(), selItem.getImage_path());
                listDelete.execute();*/

                // 화면갱신
                Intent refresh = new Intent(this, WritingActivity.class);
                startActivity(refresh);
                this.finish(); // 화면끝내기

                adapter.notifyDataSetChanged(); // adapter 갱신
            }else {
                Toast.makeText(getApplicationContext(), "항목 선택을 해 주세요(항목선택)",
                        Toast.LENGTH_SHORT).show();
            }

        }else {
            Toast.makeText(this, "인터넷이 연결되어 있지 않습니다.",
                    Toast.LENGTH_SHORT).show(); // 테스트 111
        }

    }

    // 돌아가기
    public void btn4Clicked(View v){
        finish();
    }

    // 이미 화면이 있을때 받는곳
    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        Log.d("Sub1", "onNewIntent() 호출됨");

        // 새로고침하면서 이미지가 겹치는 현상 없애기 위해...
        adapter.removeAllItem();

        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("데이터 업로딩");
        progressDialog.setMessage("데이터 업로딩 중입니다\n" + "잠시만 기다려주세요 ...");
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();
        processIntent(intent);

    }

    private void processIntent(Intent intent){
        if(intent != null){
            userReviewSelect = new UserReviewSelect(reviewList, adapter, progressDialog);
            userReviewSelect.execute();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

    }


}
