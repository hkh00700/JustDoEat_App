package com.example.empty_can;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.empty_can.ATask.ReviewUpdate;
import com.example.empty_can.ATask.UserReviewSelect;
import com.example.empty_can.Adapter.ReviewAdapter;
import com.example.empty_can.DTO.MemberReviewDTO;

import java.io.Serializable;
import java.util.ArrayList;

import static com.example.empty_can.Common.CommonMethod.isNetworkConnected;

public class List extends Fragment {
    private static final String TAG = "List";
    public static MemberReviewDTO selItem = null;

    UserReviewSelect userReviewSelect;
    ReviewAdapter adapter;
    RecyclerView recyclerView;
    ArrayList<MemberReviewDTO> reviewList;
    ProgressDialog progressDialog;






    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup View = (ViewGroup) inflater.inflate(R.layout.activity_showreview, container,false);
        reviewList = new ArrayList();

        adapter = new ReviewAdapter(reviewList, getActivity());
        recyclerView = View.findViewById(R.id.showreview);


        setLayout();

        //글 추가를 하면

        View.findViewById(R.id.btnadd).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(android.view.View v) {
                if(isNetworkConnected(getActivity()) == true){
                    Intent intent = new Intent(getActivity(), WritingActivity.class);
                    startActivity(intent);
                }else {
                    Toast.makeText(getActivity(), "인터넷이 연결되어 있지 않습니다.",
                            Toast.LENGTH_SHORT).show();
                }
            }

        });



        return View;



    }

    @Override
    public void onResume() {
        super.onResume();
        adapter.notifyDataSetChanged();
    }



    @Override
    public void onStart() {
        super.onStart();
    }


    public void Change(){
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.detach(this);
        ft.attach(this);
        ft.commit();

    }


    public void setLayout() {
        //리사이클뷰 연결

        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(),
                RecyclerView.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);

        if(isNetworkConnected(getActivity()) == true){
            userReviewSelect = new UserReviewSelect(reviewList, adapter, progressDialog);
            userReviewSelect.execute();

        }else {
            Toast.makeText(getActivity(), "인터넷이 연결되어 있지 않습니다.",
                    Toast.LENGTH_SHORT).show();
        }
        recyclerView.setAdapter(adapter);

    }


    //추가
    public void btn1Clicked(View v){
        if(isNetworkConnected(getActivity()) == true){
            Intent intent = new Intent(getActivity(), WritingActivity.class);
            startActivity(intent);
        }else {
            Toast.makeText(getActivity(), "인터넷이 연결되어 있지 않습니다.",
                    Toast.LENGTH_SHORT).show();
        }


    }
    // 수정
    public void btn2Clicked(View v){
        if(isNetworkConnected(getActivity()) == true){

            if(selItem != null){
                Log.d("writing:update1", selItem.getTitle());

                Intent intent = new Intent(getActivity(), ReviewUpdate.class);
                intent.putExtra("selItem", (Serializable) selItem);
                startActivity(intent);

            }else {
                Toast.makeText(getActivity(), "항목 선택을 해 주세요",
                        Toast.LENGTH_SHORT).show();
            }

        }else {
            Toast.makeText(getActivity(), "인터넷이 연결되어 있지 않습니다.",
                    Toast.LENGTH_SHORT).show();
        }

    }

    // 삭제
    public void btn3Clicked(View v){
        if(isNetworkConnected(getActivity()) == true){
            if(selItem != null){
                Log.d("Writing : selImg => ", selItem.getPhoto_path());

                //ReviewDelete listDelete = new ReviewDelete(selItem.getTitle(), selItem.getPhoto_path());
                //listDelete.execute();

                // 화면갱신
                /*Intent refresh = new Intent(getActivity(), ShowReviewActivity.class);
                startActivity(refresh);
                */
                //프래그 먼트 1번 메인으로 돌아가기


                adapter.notifyDataSetChanged(); // adapter 갱신
            }else {
                Toast.makeText(getActivity(), "항목 선택을 해 주세요(항목선택)",
                        Toast.LENGTH_SHORT).show();
            }

        }else {
            Toast.makeText(getActivity(), "인터넷이 연결되어 있지 않습니다.",
                    Toast.LENGTH_SHORT).show(); // 테스트 111
        }

    }

    // 돌아가기
    public void btn4Clicked(View v){
        //프래그 먼트 1번 메인으로 돌아가기
    }

   /* // 이미 화면이 있을때 받는곳
    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        Log.d("Sub1", "onNewIntent() 호출됨");

        // 새로고침하면서 이미지가 겹치는 현상 없애기 위해...
        adapter.removeAllItem();

        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setTitle("데이터 업로딩");
        progressDialog.setMessage("데이터 업로딩 중입니다\n" + "잠시만 기다려주세요 ...");
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();
        processIntent(intent);

    }*/

    private void processIntent(Intent intent){
        if(intent != null){
            userReviewSelect = new UserReviewSelect(reviewList, adapter, progressDialog);
            userReviewSelect.execute();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

    }
}
