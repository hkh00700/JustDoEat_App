package com.example.empty_can;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class MyPage extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup View = (ViewGroup) inflater.inflate(R.layout.mypage, container,false);

        Button btnEatenWeek, btnFoodModify, btnMyModify;

        btnEatenWeek = View.findViewById(R.id.btnEatenWeek);
        btnFoodModify = View.findViewById(R.id.btnFoodModify);
        btnMyModify = View.findViewById(R.id.btnMyModify);

        btnEatenWeek.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(android.view.View v) {
                Intent intent = new Intent(getActivity(), EatenweekActivity.class);
                startActivity(intent);
            }
        });

        btnFoodModify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(android.view.View v) {
                Intent intent = new Intent(getActivity(), FoodmodifyActivity.class);
                startActivity(intent);
            }
        });

        btnMyModify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(android.view.View v) {
                Intent intent = new Intent(getActivity(), MymodifyActivity.class);
                startActivity(intent);
            }
        });

        return View;
    }
}
